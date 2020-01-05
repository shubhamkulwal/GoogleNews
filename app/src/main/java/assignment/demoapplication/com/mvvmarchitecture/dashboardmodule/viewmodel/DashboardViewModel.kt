package assignment.demoapplication.com.mvvmarchitecture.dashboardmodule.viewmodel

import android.text.Html
import android.text.Spanned
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import assignment.demoapplication.com.mvvmarchitecture.dashboardmodule.model.ApiResponse
import assignment.demoapplication.com.mvvmarchitecture.dashboardmodule.model.Article
import assignment.demoapplication.com.mvvmarchitecture.dashboardmodule.model.NewsResult
import assignment.demoapplication.com.mvvmarchitecture.repository.DashboardRepository
import assignment.demoapplication.com.mvvmarchitecture.util.Constants.ApiParameterKeys.Companion.APIKEY
import assignment.demoapplication.com.mvvmarchitecture.util.Constants.ApiParameterKeys.Companion.SOURCE
import assignment.demoapplication.com.mvvmarchitecture.util.Constants.ApiParameterValue.Companion.APIKEY_VALUE
import assignment.demoapplication.com.mvvmarchitecture.util.Constants.ApiParameterValue.Companion.SOURCE_VALUE
import assignment.demoapplication.com.mvvmarchitecture.util.DATE_TIME_FORMAT_DD_MM_YYYY_SLASH_HH_MM
import assignment.demoapplication.com.mvvmarchitecture.util.convertDateFormat
import assignment.demoapplication.com.mvvmarchitecture.util.yyyy_MM_dd_T_HH_mm_ss
import javax.inject.Inject

class DashboardViewModel @Inject constructor(val repository: DashboardRepository) : ViewModel() {

    private val _response = MutableLiveData<Any>()
    private val _apiResponse = MutableLiveData<ApiResponse>()

    fun getNewsFromServer() {
        if (!_response.hasObservers()) observeResponse()
        val parameters = LinkedHashMap<String, String>()
        parameters.put(SOURCE, SOURCE_VALUE)
        parameters.put(APIKEY, APIKEY_VALUE)
        repository.getNews(parameters, _response)
    }

    private fun observeResponse() {
        _response.observeForever { response ->
            val newsResult = response as NewsResult
            if (newsResult.status.equals("Ok", true)) {
                newsResult.articles = processData(newsResult.articles)
                sendResponseToView(newsResult, "Success", true)
            } else {
                sendResponseToView(responseMessage = "Failed", isSuccess = false)
            }
        }
    }

    private fun processData(articles: ArrayList<Article>): ArrayList<Article> {
        for (article in articles) {
            article.author = article.author?.let { Html.fromHtml(it).toString() }
            article.publishedAt = convertDateFormat(article.publishedAt,yyyy_MM_dd_T_HH_mm_ss,DATE_TIME_FORMAT_DD_MM_YYYY_SLASH_HH_MM)
        }
        return articles
    }

    private fun sendResponseToView(
        newsResult: NewsResult? = null,
        responseMessage: String,
        isSuccess: Boolean
    ) {
        _apiResponse.value = ApiResponse(newsResult, responseMessage, isSuccess)
    }


    fun getApiResponse(): MutableLiveData<ApiResponse> {
        return _apiResponse
    }
}