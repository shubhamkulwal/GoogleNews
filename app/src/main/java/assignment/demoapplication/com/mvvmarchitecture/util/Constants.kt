package assignment.demoapplication.com.mvvmarchitecture.util

class Constants {

    interface ApiUrls {
        companion object {
            val BASEURL = "https://newsapi.org/v2/"
            val GET_NEWS_URL = "top-headlines"
        }
    }

    interface ApiMethods {
        companion object {
            val GET_METHOD = 0
            val POST_METHOD = 1
        }
    }

    interface ApiParameterKeys {
        companion object {
            val SOURCE = "sources"
            val APIKEY = "apiKey"
        }
    }

    interface ApiParameterValue{
        companion object{
            val SOURCE_VALUE = "google-news"
            val APIKEY_VALUE = "4d2b73b25f544992b337e2e1ccf782ef"
        }
    }

    interface ApiServiceID {
        companion object {
            val GET_NEWS_SERVICE_ID = 1
        }
    }

    interface IntentKeys{
        companion object{
            val ISLOGGEDIN = "isLoggedIn"
            val URL = "url"
        }
    }


}