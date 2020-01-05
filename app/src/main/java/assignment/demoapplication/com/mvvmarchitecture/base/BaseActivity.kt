package assignment.demoapplication.com.mvvmarchitecture.base

import android.content.Intent
import android.os.Bundle
import androidx.annotation.LayoutRes
import dagger.android.support.DaggerAppCompatActivity

abstract class BaseActivity : DaggerAppCompatActivity() {

   /* @LayoutRes
    abstract fun layoutId(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId())
    }*/

    fun startActivity(cls: Class<*>){
        val intent = Intent(this,cls)
        startActivity(intent
        )
    }
}