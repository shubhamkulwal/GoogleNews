package assignment.demoapplication.com.mvvmarchitecture.callbacks

import assignment.demoapplication.com.mvvmarchitecture.dashboardmodule.model.Article

interface OnItemClickListener{
    fun onItemClicked(article: Article)
    fun shareNews(url: String)
}