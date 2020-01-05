package assignment.demoapplication.com.mvvmarchitecture.loginmodule

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import assignment.demoapplication.com.mvvmarchitecture.R
import assignment.demoapplication.com.mvvmarchitecture.dashboardmodule.view.activity.DashboardActivity
import assignment.demoapplication.com.mvvmarchitecture.databinding.ActivityLoginLayoutBinding
import assignment.demoapplication.com.mvvmarchitecture.util.Constants.IntentKeys.Companion.ISLOGGEDIN
import java.util.*
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.google.android.gms.common.api.ApiException
import java.lang.Exception
import com.facebook.AccessToken


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginLayoutBinding
    private lateinit var callbackManager: CallbackManager
    private val RC_SIGN_IN = 1
    private var isLoggedIn = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            assignment.demoapplication.com.mvvmarchitecture.R.layout.activity_login_layout
        )
        init()
    }

    private fun init() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        val mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        callbackManager = CallbackManager.Factory.create()
        binding.loginButton.setReadPermissions(Arrays.asList(getString(R.string.email)))
        binding.loginButton.registerCallback(callbackManager, facebookCallback)

        binding.guestSignInButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                startDashboardActivity()
            }
        })

        binding.signInButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                val signInIntent = mGoogleSignInClient.getSignInIntent()
                startActivityForResult(signInIntent, RC_SIGN_IN)
            }
        })
    }

    override fun onStart() {
        super.onStart()
        val account = GoogleSignIn.getLastSignedInAccount(this)
        if (account != null) {
            isLoggedIn = true
            startDashboardActivity()
        } else {
            val accessToken = AccessToken.getCurrentAccessToken()
            isLoggedIn = accessToken != null && !accessToken.isExpired
            if (isLoggedIn) startDashboardActivity()
        }
    }

    private fun startDashboardActivity() {
        val intent = Intent(this@LoginActivity, DashboardActivity::class.java)
        intent.putExtra(ISLOGGEDIN, isLoggedIn)
        startActivity(intent)
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                if (task.getResult(ApiException::class.java) != null)
                    isLoggedIn = true
                startDashboardActivity()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            callbackManager.onActivityResult(requestCode, resultCode, data)
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private val facebookCallback = object : FacebookCallback<LoginResult> {
        override fun onSuccess(result: LoginResult?) {
            isLoggedIn = true
            startDashboardActivity()
        }

        override fun onCancel() {
            Log.e("cancel", "cancel")
        }

        override fun onError(error: FacebookException?) {
            Log.e("Error", "error")
        }

    }
}
