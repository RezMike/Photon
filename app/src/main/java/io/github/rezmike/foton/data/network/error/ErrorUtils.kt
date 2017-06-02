package io.github.rezmike.foton.data.network.error

import retrofit2.Response

object ErrorUtils {

    @JvmStatic
    fun parseError(response: Response<*>): ApiError {
        // TODO: 06.04.2017 parse error correctly
        return ApiError(response.code())
    }
}
