package com.fyp.fragments

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.net.http.SslError
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.fyp.R
import com.fyp.activities.ActivityDashboard
import com.fyp.interfaces.iOnBackPressed
import com.fyp.models.videoObjects
import com.fyp.utils.Constant
import kotlinx.android.synthetic.main.fragment_video_screen.*


class FragmentVideoScreen : Fragment(), View.OnClickListener ,iOnBackPressed{
    var list = ArrayList<String>()
    var myView: View? = null
    var obj = ArrayList<videoObjects>()
    var next=0
    var back=0
    var text=""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.fragment_video_screen, container, false)
        return myView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }




    @SuppressLint("SetJavaScriptEnabled", "JavascriptInterface")
    private fun webview(html: String,heading:String,text:String) {
        ivForm.settings.javaScriptEnabled = true;
        ivForm.settings.setDomStorageEnabled(true);
        ivForm.getSettings().builtInZoomControls = true;
        ivForm.getSettings().displayZoomControls = true;
        ivForm.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY;
        ivForm.getSettings().setPluginState(WebSettings.PluginState.ON);
        ivForm.getSettings().setAllowContentAccess(true);
        ivForm.getSettings().setAppCacheEnabled(true);
        ivForm.getSettings().setDomStorageEnabled(true);
        ivForm.getSettings().setUseWideViewPort(true);
        ivForm.setWebViewClient(WebViewClient())
        ivForm.webChromeClient = object : WebChromeClient() {
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
                progressBar.visibility = View.VISIBLE;
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
        subtitleHeader.text=heading
        translationText.text=text
    }
    private fun init() {
        addQuestInRv()
        backBtn.setOnClickListener(this)
        nextBtn.setOnClickListener(this)
        try {
            if (requireArguments() != null && requireArguments().containsKey(Constant.WEBVIEW_LINK)) {
                requireArguments().getParcelableArrayList<videoObjects>(Constant.WEBVIEW_LINK)?.let {obj=it }
                requireArguments().getInt(Constant.POSITION)?.let {next=it }
                requireArguments().getInt(Constant.POSITION)?.let {back=it }
                webview(obj[next].videoUrl,obj[next].heading,obj[next].text)
            }
        } catch (e: IllegalStateException) {
        }

    }

    private fun addQuestInRv() {
        val questions =
            (activity as ActivityDashboard).resources!!.getStringArray(R.array.questions_desc_array)
        for (element in questions) {
            list?.add(element)
        }

    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.backBtn -> {
                if(back in 1..8){
                    back--
                    next=back
                    webview(obj[next].videoUrl,obj[next].heading,obj[next].text)
                }
            }
            R.id.nextBtn -> {
                if(next in 0..7){
                    next++
                    back=next
                    webview(obj[next].videoUrl,obj[next].heading,obj[next].text)
                }
            }
        }
    }
    override fun onBackPressed(): Boolean {
        val navController = requireActivity().findNavController(R.id.fragment)
        return if (navController.currentDestination?.id != R.id.fragmentVideo) {
            Log.i("onBackPress", "Not Up Finish All Fragment")
            requireActivity().finish()
            true
        } else {
            Log.i("onBackPress", "Up")
            navController.popBackStack()
            true
        }
    }
}