package assignment.demoapplication.com.mvvmarchitecture.base

import assignment.demoapplication.com.mvvmarchitecture.model.ResponseWrapper
import assignment.demoapplication.com.mvvmarchitecture.network.APIinterface
import com.google.gson.Gson
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.json.JSONArray
import org.json.JSONObject
import java.util.HashMap
import javax.inject.Inject

open abstract class BaseRepository {

    @Inject
    lateinit var apIinterface: APIinterface
    internal var disposable: Disposable? = null

    fun networkCall(
        apiMethod: Int,
        url: String,
        resultClass: Class<*>,
        requestObj: Any? = null,
        parameters: Map<String, String>? = null
    ) {
        APIinterface.callBack(
            apIinterface,
            apiMethod,
            url,
            requestObj,
            parameters.takeIf { parameters != null } ?: HashMap()
        )?.subscribeOn(Schedulers.newThread())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(object : Observer<Any> {
                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(response: Any) {
                    handleResponse(getResultObj(response, resultClass))
                }

                override fun onError(e: Throwable) {
                    handleError(e)
                }

            })
    }

    private fun getResultObj(result: Any, resultClass: Class<*>): Any {
        val gson = Gson()
        val str = gson.toJson(result, result.javaClass)
        val jsonObject = JSONObject(str)
        val resultObj: Any = gson.fromJson(jsonObject.toString(), resultClass)
        return resultObj
    }

    abstract fun handleResponse(responseObj: Any)

    abstract fun handleError(error: Throwable)

}