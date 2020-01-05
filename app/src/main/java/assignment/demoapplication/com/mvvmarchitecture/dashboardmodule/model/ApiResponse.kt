package assignment.demoapplication.com.mvvmarchitecture.dashboardmodule.model

data class ApiResponse(
    var newsResult: NewsResult? = null,
    val responseMessage: String,
    val isSuccess: Boolean
)