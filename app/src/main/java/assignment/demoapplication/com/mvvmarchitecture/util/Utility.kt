package assignment.demoapplication.com.mvvmarchitecture.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import android.webkit.WebView



class Utility {

    companion object {

        @JvmStatic
        @BindingAdapter("imageUrls")
        fun loadImage(view: ImageView, url: String?) {
            url?.let {  Glide.with(view.context).load(it).into(view) }
        }

        @JvmStatic
        @BindingAdapter("loadUrl")
        fun loadUrl(view: WebView, url: String) {
            view.loadUrl(url)
        }
    }


}