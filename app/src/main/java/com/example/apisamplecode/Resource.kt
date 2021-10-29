package com.example.apisamplecode

enum class Status { LOADING, SUCCESS, ERROR }
data class Resource<out T>(val state: Status, val data: T?, val errorCode: Int?) {

    companion object {
        fun <T> loading(): Resource<T> {
            return Resource(Status.LOADING, null, null)
        }

        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(errorCode: Int?): Resource<T> {
            return Resource(Status.ERROR, null, errorCode)
        }
    }
}
