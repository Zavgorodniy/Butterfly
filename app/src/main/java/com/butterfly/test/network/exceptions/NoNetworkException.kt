package com.butterfly.test.network.exceptions

import com.butterfly.test.R
import com.butterfly.test.extensions.appString

class NoNetworkException : Exception() {

    companion object {
        private val ERROR_MESSAGE = appString(R.string.no_internet_connection_error)
    }

    override val message: String = ERROR_MESSAGE
}
