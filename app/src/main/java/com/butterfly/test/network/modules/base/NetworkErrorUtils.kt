package com.butterfly.test.network.modules.base

import com.butterfly.test.network.NetworkModule.mapper
import com.butterfly.test.network.errors.ServerErrorBean
import com.butterfly.test.network.exceptions.ApiException
import com.butterfly.test.network.exceptions.NoNetworkException
import com.butterfly.test.network.exceptions.ServerException
import com.butterfly.test.network.exceptions.ValidationError
import com.butterfly.test.utils.printLog
import com.butterfly.test.utils.printLogE
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.SingleTransformer
import io.reactivex.functions.Function
import retrofit2.HttpException
import retrofit2.Response
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.ConnectException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

object NetworkErrorUtils {

    private const val SERVER_ERROR_CODE = 500

    private const val SERVER_ERROR_CODE_1 = 502

    private const val UNPROCESSABLE_ENTITY = 422

    private val TAG = NetworkErrorUtils::class.java.simpleName

    fun <T> rxParseFlowableError() = Function<Throwable, Flowable<T>> {
        Flowable.error<T>(parseError(it))
    }

    fun rxParseCompletableError() = Function<Throwable, Completable> {
        Completable.error(parseError(it))
    }

    fun <T> rxParseSingleError() = Function<Throwable, Single<T>> {
        Single.error<T>(parseError(it))
    }

    fun <T : Response<*>> rxParseError() = SingleTransformer<T, T> { inObservable ->
        inObservable.doOnSuccess {
            if (!it.isSuccessful) throw parseErrorResponseBody(it)
        }
    }

    private fun parseError(throwable: Throwable): Throwable? =
        if (throwable is HttpException) {
// return this exception in case of error with 500 code
            when (throwable.code()) {
                SERVER_ERROR_CODE, SERVER_ERROR_CODE_1 -> ServerException().initCause(throwable)
                else -> throwable.response()?.let { parseErrorResponseBody(it) }
            }
        } else when {
            isConnectionProblem(throwable) -> NoNetworkException()
            isServerConnectionProblem(throwable) -> ServerException()
            else -> throwable
        }

    private fun isServerConnectionProblem(throwable: Throwable): Boolean =
        throwable is SocketException || throwable is SocketTimeoutException

    private fun isConnectionProblem(throwable: Throwable): Boolean =
        throwable is UnknownHostException || throwable is ConnectException


    private fun parseErrorResponseBody(response: Response<*>): Exception {
        var inputStreamReader: InputStreamReader? = null
        var bufferedReader: BufferedReader? = null
        return try {
            inputStreamReader = InputStreamReader(response.errorBody()?.byteStream())
            bufferedReader = BufferedReader(inputStreamReader)
            parseBufferedReader(bufferedReader).run {
                parseError(this@run, response.code())
            }
        } catch (e: IOException) {
            e.apply {
                printLog("$this")
            }
        } finally {
            closeReader(bufferedReader)
            closeStream(inputStreamReader)
        }
    }

    private fun parseError(stringBuilder: StringBuilder, responseCode: Int): Exception {
        // Try to parse ServerErrorBean.class
        val serverError: ServerErrorBean
        try {
            serverError = mapper.readValue(stringBuilder.toString(), ServerErrorBean::class.java)
        } catch (e: IOException) {
            return e.apply {
                printLogE("Couldn't parse error response to ServerErrorBean.class: $message")
            }
        }
        val validationErrors = mutableListOf<ValidationError>()
        serverError.run {
            errors?.forEach { error ->
                validationErrors.add(ValidationError(error.code, error.key, error.message))
            }
            code?.takeIf { responseCode == UNPROCESSABLE_ENTITY }?.let {
                validationErrors.add(ValidationError(it, null, message = message))
            }

            return ApiException(
                responseCode,
                v,
                message,
                validationErrors
            )
        }
    }

    private fun parseBufferedReader(bufferedReader: BufferedReader): StringBuilder =
        StringBuilder().apply {
            var newLine: String? = bufferedReader.readLine()
            while (newLine != null) {
                append(newLine)
                newLine = bufferedReader.readLine()
            }
        }

    private fun closeStream(inputStreamReader: InputStreamReader?) {
        inputStreamReader?.let {
            try {
                it.close()
            } catch (e: IOException) {
                e.printLogE()
            }
        }
    }

    private fun closeReader(bufferedReader: BufferedReader?) {
        bufferedReader?.let {
            try {
                it.close()
            } catch (e: IOException) {
                e.printLogE()
            }
        }
    }
}
