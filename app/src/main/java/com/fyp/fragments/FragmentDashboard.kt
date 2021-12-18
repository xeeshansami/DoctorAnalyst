package com.fyp.fragments

import android.R.attr
import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.http.SslError
import android.os.Build
import android.os.Bundle
import android.telephony.MbmsDownloadSession.RESULT_CANCELLED
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import android.widget.FrameLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.fyp.R
import com.fyp.activities.ActivityDashboard
import com.fyp.activities.ScannerActivity
import com.fyp.interfaces.iOnBackPressed
import com.fyp.interfaces.iOnItemClickListner
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.android.synthetic.main.fragment_dashboard.ivForm
import kotlinx.android.synthetic.main.fragment_dashboard.progressBar
import kotlinx.android.synthetic.main.fragment_video_screen.*


class FragmentDashboard : Fragment(), iOnItemClickListner, iOnBackPressed, View.OnClickListener {
    var list = ArrayList<String>()
    val SCAN_SERIAL_REQUEST = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
    }

    private fun init(view: View) {
        qr_scanner.setOnClickListener(this)
        webview("https://avirtualadvantage.com/sys4/store/info/AVA0003")
    }


    @SuppressLint("SetJavaScriptEnabled", "JavascriptInterface")
    private fun webview(html: String) {
        ivForm.settings.javaScriptEnabled = true;
        ivForm.settings.domStorageEnabled = true;
        ivForm.getSettings().builtInZoomControls = true;
        ivForm.getSettings().displayZoomControls = true;
        ivForm.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY;
        ivForm.getSettings().pluginState = WebSettings.PluginState.ON;
        ivForm.getSettings().allowContentAccess = true;
        ivForm.getSettings().setAppCacheEnabled(true);
        ivForm.getSettings().domStorageEnabled = true;
        ivForm.getSettings().useWideViewPort = true;
        ivForm.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH)
        ivForm.getSettings().setAppCacheEnabled(true)
        ivForm.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
        ivForm.settings.domStorageEnabled = true
        ivForm.settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.NARROW_COLUMNS
        ivForm.settings.useWideViewPort = true
        ivForm.settings.saveFormData = true
        ivForm.settings.savePassword = true
        ivForm.clearCache(true);
        ivForm.clearHistory();
        ivForm.webViewClient = WebViewClient()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            ivForm.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else {
            ivForm.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        ivForm.webChromeClient = object : WebChromeClient() {


            private var mCustomView: View? = null
            private var mCustomViewCallback: CustomViewCallback? = null
            protected var mFullscreenContainer: FrameLayout? = null
            private var mOriginalOrientation = 0
            private var mOriginalSystemUiVisibility = 0

            fun ChromeClient() {}

            override fun getDefaultVideoPoster(): Bitmap? {
                return if (mCustomView == null) {
                    null
                } else BitmapFactory.decodeResource(
                    (activity as ActivityDashboard).getResources(), 2130837573
                )
            }

            override fun onHideCustomView() {
                ((activity as ActivityDashboard).getWindow()
                    .getDecorView() as FrameLayout).removeView(
                    mCustomView
                )
                mCustomView = null
                (activity as ActivityDashboard).getWindow().getDecorView().setSystemUiVisibility(
                    mOriginalSystemUiVisibility
                )
                (activity as ActivityDashboard).setRequestedOrientation(mOriginalOrientation)
                mCustomViewCallback!!.onCustomViewHidden()
                mCustomViewCallback = null
            }

            override fun onShowCustomView(
                paramView: View?,
                paramCustomViewCallback: CustomViewCallback?
            ) {
                if (mCustomView != null) {
                    onHideCustomView()
                    return
                }
                mCustomView = paramView
                mOriginalSystemUiVisibility =
                    (activity as ActivityDashboard).getWindow().getDecorView()
                        .getSystemUiVisibility()
                mOriginalOrientation = (activity as ActivityDashboard).getRequestedOrientation()
                mCustomViewCallback = paramCustomViewCallback
                ((activity as ActivityDashboard).getWindow().getDecorView() as FrameLayout).addView(
                    mCustomView,
                    FrameLayout.LayoutParams(-1, -1)
                )
                (activity as ActivityDashboard).getWindow().getDecorView()
                    .setSystemUiVisibility(3846 or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
            }

            override fun onProgressChanged(view: WebView?, progress: Int) {
                try {
                    if (progressBar != null) {
                        progressBar.progress = progress
                    }
                } catch (e: java.lang.NullPointerException) {
                }
            }


        }
        ivForm.webViewClient = object : WebViewClient() {
            override fun onReceivedSslError(
                view: WebView?,
                handler: SslErrorHandler?,
                error: SslError?
            ) {
                handler!!.proceed();
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)

            }


            override fun onLoadResource(view: WebView?, url: String) {
                super.onLoadResource(view, url);
                Log.i("URLS", url.toString())

            }


            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                try {
                    progressBar.visibility = View.VISIBLE;
                } catch (e: NullPointerException) {
                } catch (e: Exception) {
                }
            }

            override fun onReceivedError(
                view: WebView?,
                request: WebResourceRequest?,
                error: WebResourceError?
            ) {
                super.onReceivedError(view, request, error)
            }
        }
        ivForm.loadUrl(html)
    }

    override fun onItemClick(view: View, question: String, position: Int) {

    }

    private fun onBackPressed(view: View) {
        view.isFocusableInTouchMode = true
        view.requestFocus()
        view.setOnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK && keyCode == KeyEvent.ACTION_UP) {
                val navController = requireActivity().findNavController(R.id.fragment)
                if (navController.currentDestination?.id == R.id.dashboard) {
                    Log.i("onBackPress", "Not Up Finish All Fragment")
                    requireActivity().finish()
                } else {
                    Log.i("onBackPress", "Up")
                    navController.popBackStack()
                }
                true
            } else {
                false
            }
        }
    }

    @SuppressLint("WrongConstant")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode === 0) {
            if (resultCode === RESULT_OK) {
                val contents: String = data!!.getStringExtra("SCAN_RESULT").toString()
                webview(contents)
            }
            if (resultCode === RESULT_CANCELLED) {
                //handle cancel
            }
        }
    }

    override fun onBackPressed(): Boolean {
        val navController = requireActivity().findNavController(R.id.fragment)
        if (navController.currentDestination?.id != R.id.dashboard) {
            Log.i("onBackPress", "Not Up Finish All Fragment")
            requireActivity().finish()
            return true
        } else {
            Log.i("onBackPress", "Up")
            navController.popBackStack()
            return true
        }
    }

    @SuppressLint("WrongConstant")
    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.qr_scanner -> {
                val intent = Intent(activity as ActivityDashboard, ScannerActivity::class.java)
                startActivityForResult(intent, SCAN_SERIAL_REQUEST)
            }
        }
    }
}