package assignment.demoapplication.com.mvvmarchitecture.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import assignment.demoapplication.com.mvvmarchitecture.base.BaseRepository
import assignment.demoapplication.com.mvvmarchitecture.dashboardmodule.model.NewsResult
import assignment.demoapplication.com.mvvmarchitecture.model.ResponseWrapper
import assignment.demoapplication.com.mvvmarchitecture.network.APIinterface
import assignment.demoapplication.com.mvvmarchitecture.util.Constants.ApiMethods.Companion.GET_METHOD
import assignment.demoapplication.com.mvvmarchitecture.util.Constants.ApiUrls.Companion.GET_NEWS_URL

import javax.inject.Inject

class DashboardRepository @Inject constructor(val apiService: APIinterface) : BaseRepository() {

    private lateinit var networkStream: MutableLiveData<Any>

    fun getNews(
        parameters: LinkedHashMap<String, String>,
        networkStream: MutableLiveData<Any>
    ) {
        this.networkStream = networkStream
        networkCall(
            GET_METHOD,
            GET_NEWS_URL,
            NewsResult::class.java,
            parameters = parameters
        )
    }

    override fun handleError(error: Throwable) {
        Log.e("ERROR", error.message)
    }

    override fun handleResponse(responseObj: Any) {
        networkStream.value = responseObj
    }

}