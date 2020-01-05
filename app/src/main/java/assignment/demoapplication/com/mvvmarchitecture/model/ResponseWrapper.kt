package assignment.demoapplication.com.mvvmarchitecture.model

import assignment.demoapplication.com.mvvmarchitecture.util.Constants

data class ResponseWrapper(
    var error: Error,
    val serviceId: String
)
