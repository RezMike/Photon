package io.github.rezmike.foton.utils

object AppConfig {

    //urls
    const val BASE_URL = "http://skba1.mgbeta.ru/api/v1/"

    //network
    const val MAX_CONNECTION_TIMEOUT : Long = 5000
    const val MAX_READ_TIMEOUT : Long = 5000
    const val MAX_WRITE_TIMEOUT : Long = 5000
    const val RETRY_REQUEST_BASE_DELAY = 500
    const val RETRY_REQUEST_COUNT = 5

    //jobManager
    const val JOB_MIN_CONSUMER_COUNT = 1
    const val JOB_MAX_CONSUMER_COUNT = 3
    const val JOB_KEEP_ALIVE = 120
    const val JOB_UPDATE_DATA_INTERVAL = 30
    const val JOB_LOAD_FACTOR = 3
    const val JOB_INITIAL_BACK_OFF_IN_MS = 1000
}
