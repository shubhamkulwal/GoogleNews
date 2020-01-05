package assignment.demoapplication.com.mvvmarchitecture.detailnewsmodule

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.databinding.DataBindingUtil
import assignment.demoapplication.com.mvvmarchitecture.R
import assignment.demoapplication.com.mvvmarchitecture.databinding.ActivityDetailNewsLayoutBinding
import com.bumptech.glide.Glide.init
import kotlinx.android.synthetic.main.activity_detail_news_layout.*

class DetailNewsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailNewsLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_news_layout)
        init()
    }

    private fun init() {
        val url = intent.extras?.getString("url")
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.webViewClient = object : WebViewClient() {
            override fun onLoadResource(view: WebView?, url: String?) {
             //   binding.progress.visibility = View.VISIBLE

            }

            override fun onPageFinished(view: WebView?, url: String?) {
           //     binding.progress.visibility = View.GONE
            }
        }
        binding.url = url

        binding.imageBack.setOnClickListener(object : View.OnClickListener{
            override fun onClick(view: View?) {
                finish()
            }

        })
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && this.webView.canGoBack()) {
            this.webView.goBack()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}
