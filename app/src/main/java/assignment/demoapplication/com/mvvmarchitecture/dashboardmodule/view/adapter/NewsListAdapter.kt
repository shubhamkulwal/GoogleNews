package assignment.demoapplication.com.mvvmarchitecture.dashboardmodule.view.adapter

import android.content.Context
import android.util.Log
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import assignment.demoapplication.com.mvvmarchitecture.R
import assignment.demoapplication.com.mvvmarchitecture.callbacks.OnItemClickListener
import assignment.demoapplication.com.mvvmarchitecture.dashboardmodule.model.Article
import assignment.demoapplication.com.mvvmarchitecture.databinding.NewsListAdapterLayoutBinding
import com.bumptech.glide.Glide
import java.util.*
import kotlin.collections.ArrayList

class NewsListAdapter(
    var context: Context?,
    var article: ArrayList<Article>?,
    val onItemClickListener: OnItemClickListener,
    val loggedIn: Boolean?
) :
    RecyclerView.Adapter<NewsListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsListAdapter.ViewHolder {
        val binding: NewsListAdapterLayoutBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.news_list_adapter_layout,
            parent,
            false
        )

        return ViewHolder(binding, loggedIn)
    }

    override fun getItemCount(): Int {
        return article!!.size
    }

    fun getItemList(): ArrayList<Article> {
        return article!!
    }

    override fun onBindViewHolder(holder: NewsListAdapter.ViewHolder, position: Int) {
        holder.bindData(article!![position])
        holder.binding.cardView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                onItemClickListener.onItemClicked(article!![position])
            }

        })

        holder.binding.share.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                onItemClickListener.shareNews(article!![position].url)
            }

        })

        holder.binding.delete.setOnClickListener(object :View.OnClickListener{
            override fun onClick(view: View?) {
                article!!.removeAt(position)
                notifyItemRemoved(position)
            }

        })
    }

    fun showDeleteOption(holder: NewsListAdapter.ViewHolder, position: Int) {
        holder.binding.delete.visibility = View.VISIBLE
    }

    class ViewHolder(val itemViewBinding: NewsListAdapterLayoutBinding, val loggedIn: Boolean?) :
        RecyclerView.ViewHolder(itemViewBinding.root) {
        val binding = itemViewBinding
        fun bindData(article: Article) {
            itemViewBinding.data = article
            itemViewBinding.loggedIn = loggedIn
        }
    }
}