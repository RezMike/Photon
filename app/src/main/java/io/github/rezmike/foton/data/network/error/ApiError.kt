package io.github.rezmike.foton.data.network.error

class ApiError : Throwable {

    val status: Int

    constructor(message: String) : super(message) {
        status = 0
    }

    constructor(statusCode: Int) : super("status code: " + statusCode) {
        status = statusCode
    }
}
