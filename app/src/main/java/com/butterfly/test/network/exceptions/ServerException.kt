package com.butterfly.test.network.exceptions

import com.butterfly.test.R
import com.butterfly.test.extensions.appString

class ServerException(
    statusCode: Int? = null,
    v: String? = null,
    message: String? = null,
    errors: List<ValidationError>? = null,
    stacktrace: String? = null
) : ApiException(statusCode, v, message, errors, stacktrace) {

    companion object {
        private val ERROR_MESSAGE = appString(R.string.server_error)
        private const val STATUS_CODE = 500
    }

    override val message: String = ERROR_MESSAGE
    override var statusCode: Int? = STATUS_CODE
}
