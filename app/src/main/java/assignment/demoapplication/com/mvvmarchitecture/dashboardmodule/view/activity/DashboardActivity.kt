package assignment.demoapplication.com.mvvmarchitecture.dashboardmodule.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import assignment.demoapplication.com.mvvmarchitecture.R
import assignment.demoapplication.com.mvvmarchitecture.base.BaseActivity
import assignment.demoapplication.com.mvvmarchitecture.callbacks.OnItemClickListener
import assignment.demoapplication.com.mvvmarchitecture.dashboardmodule.model.Article
import assignment.demoapplication.com.mvvmarchitecture.dashboardmodule.view.adapter.NewsListAdapter
import assignment.demoapplication.com.mvvmarchitecture.dashboardmodule.viewmodel.DashboardViewModel
import assignment.demoapplication.com.mvvmarchitecture.databinding.ActivityDashboardLayoutBinding
import assignment.demoapplication.com.mvvmarchitecture.detailnewsmodule.DetailNewsActivity
import assignment.demoapplication.com.mvvmarchitecture.util.Constants.IntentKeys.Companion.ISLOGGEDIN
import assignment.demoapplication.com.mvvmarchitecture.util.Constants.IntentKeys.Companion.URL
import javax.inject.Inject


class DashboardActivity : BaseActivity() {

    private lateinit var binding: ActivityDashboardLayoutBinding
    private lateinit var newsListAdapter: NewsListAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val dashboardViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(DashboardViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_dashboard_layout
        )
        init()
    }

    private fun init() {
        val isLoggedIn = intent.extras?.getBoolean(ISLOGGEDIN)
        if (!dashboardViewModel.getApiResponse().hasObservers()) observeApiResponse(isLoggedIn)
        binding.progress.visibility = View.VISIBLE
        dashboardViewModel.getNewsFromServer()
    }

    private fun observeApiResponse(loggedIn: Boolean?) {
        dashboardViewModel.getApiResponse().observe(this, Observer { apiResponse ->
            binding.progress.visibility = View.GONE
            newsListAdapter =
                NewsListAdapter(this, apiResponse.newsResult?.articles, callBack, loggedIn)
            binding.recyclerView.apply {
                adapter = newsListAdapter
                layoutManager =
                    LinearLayoutManager(this@DashboardActivity, LinearLayoutManager.VERTICAL, false)
            }

            val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
            itemTouchHelper.attachToRecyclerView(binding.recyclerView);
        })
    }

    val simpleItemTouchCallback =
        object : ItemTouchHelper.SimpleCallback(
            ANIMATION_TYPE_SWIPE_CANCEL,
            ItemTouchHelper.LEFT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                for (i in 0 until newsListAdapter?.getItemList().size) {
                    if (i == position) {
                        newsListAdapter?.getItemList()[i].isSwipe = true
                    } else {
                        newsListAdapter?.getItemList()[i].isSwipe = false
                    }
                }
                newsListAdapter.notifyDataSetChanged()
            }
        }

    val callBack = object : OnItemClickListener {

        override fun onItemClicked(article: Article) {
            val intent = Intent(this@DashboardActivity, DetailNewsActivity::class.java)
            intent.putExtra(URL, article.url)
            startActivity(intent)
        }

        override fun shareNews(url: String) {
            val sharingIntent = Intent(android.content.Intent.ACTION_SEND)
            sharingIntent.setType("text/plain")
            sharingIntent.putExtra(Intent.EXTRA_TEXT, url)
            startActivity(Intent.createChooser(sharingIntent, "Share via"))
        }
    }
}
