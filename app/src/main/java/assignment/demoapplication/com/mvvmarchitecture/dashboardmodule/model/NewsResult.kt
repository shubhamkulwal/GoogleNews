package assignment.demoapplication.com.mvvmarchitecture.dashboardmodule.model

data class NewsResult(
    var articles: ArrayList<Article>,
    val status: String,
    val totalResults: Int
)

data class Article(
    var author: String?,
    val content: String,
    val description: String,
    var publishedAt: String,
    val source: Source,
    val title: String,
    val url: String,
    val urlToImage: String,
    var isSwipe : Boolean =  false
)

data class Source(
    val id: String,
    val name: String
)

