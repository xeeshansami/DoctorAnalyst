package com.fyp.fragments

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.http.SslError
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.fyp.R
import com.fyp.activities.ActivityDashboard
import com.fyp.activities.LogActivity
import com.fyp.interfaces.iOnBackPressed
import com.fyp.models.videoObjects
import com.fyp.network.models.response.base.BaseResponse
import com.fyp.utils.Constant
import com.fyp.utils.GlobalClass
import com.fyp.utils.SessionManager
import com.fyp.utils.ToastUtils
import com.hbl.hblaccountopeningapp.network.ResponseHandlers.callbacks.RegisterCallBack
import com.hbl.hblaccountopeningapp.network.enums.RetrofitEnums
import com.hbl.hblaccountopeningapp.network.models.request.base.historyRequest
import com.hbl.hblaccountopeningapp.network.store.HBLHRStore
import kotlinx.android.synthetic.main.fragment_video_screen.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import kotlin.collections.set


class FragmentVideoScreen : Fragment(), View.OnClickListener, iOnBackPressed {
    var counter:CountDownTimer?=null
    val globalClass = GlobalClass.applicationContext!!.applicationContext as GlobalClass
    var maxCounter: Long = 1000000
    var diff: Long = 1000
    private val EVENT_DATE_TIME = "2021-12-31 10:30:00"
    private val DATE_FORMAT = "yyyy-MM-dd HH:mm:ss"
    private val handler: Handler = Handler()
    private var runnable: Runnable? = null
    var myView: View? = null
    var sessionManager: SessionManager? = null
    var obj: ArrayList<videoObjects>? = null
    var position = 0
    var next = 0
    var back = 0
    var text = ""
    var finalTime = ""
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

    override fun onResume() {
        super.onResume()
        init()
    }

    override fun onPause() {
//        updateAppTime(
//            obj!![next].videoUrl,
//            obj!![next].heading,
//            obj!![next].text
//        )
        super.onPause()
    }

    override fun onStop() {
        updateAppTime(
            obj!![next].videoUrl,
            obj!![next].heading,
            obj!![next].text
        )
        super.onStop()
    }
    fun convertSeconds(seconds: Int): String? {
        val h = seconds / 3600
        val m = seconds % 3600 / 60
        val s = seconds % 60
        val sh = if (h > 0) "$h h" else ""
        val sm =
            (if (m in 1..9 && h > 0) "0" else "") + if (m > 0) if (h > 0 && s == 0) m.toString() else "$m min" else ""
        val ss =
            if (s == 0 && (h > 0 || m > 0)) "" else (if (s < 10 && (h > 0 || m > 0)) "0" else "") + s.toString() + " " + "sec"
        return sh + (if (h > 0) " " else "") + sm + (if (m > 0) " " else "") + ss
    }

//    fun webview(html: String, heading: String, text: String) {
//        var pDialog = ProgressDialog(activity)
//        // Set progressbar message
//        // Set progressbar message
//        pDialog.setMessage(resources.getString(R.string.buffering))
//        pDialog.setIndeterminate(false)
//        pDialog.setCancelable(false)
//        // Show progressbar
//        // Show progressbar
//        pDialog.show()
//        try {
//            // Start the MediaController
//            val mediacontroller = MediaController(activity)
//            mediacontroller.setAnchorView(videoview)
//            val videoUri =
//                Uri.parse(html)
////            videoview.setMediaController(mediacontroller)
////            videoview.setVideoURI(videoUri)
////            videoview.setBackgroundColor(Color.TRANSPARENT);
////            videoview.setZOrderOnTop(true);
//
//
////            val uri = Uri.parse("http://techslides.com/demos/sample-videos/small.mp4")
//            videoview.setMediaController(MediaController(activity))
//            videoview.setVideoURI(videoUri)
//            videoview.requestFocus()
//            videoview.start()
//
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//
//        videoview.requestFocus()
//        videoview.setOnPreparedListener(OnPreparedListener
//        // Close the progress bar and play the video
//        {
//            pDialog.dismiss()
//            videoview.start()
//        })
//        videoview.setOnCompletionListener(OnCompletionListener {
//            if (pDialog.isShowing()) {
//                pDialog.dismiss()
//            }
//        })
//        subtitleHeader.text = heading
//        subheader.text = heading
//        translationText.text = text
//    }


    @SuppressLint("SetJavaScriptEnabled", "JavascriptInterface")
    private fun webview(html: String, heading: String, text: String) {
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
        subtitleHeader.text = heading
        subheader.text = heading
        translationText.text = text
    }

    private fun init() {
        next = 0
        back = 0
        obj = ArrayList<videoObjects>()
        sessionManager = SessionManager(activity as ActivityDashboard)
        backBtn.setOnClickListener(this)
        nextBtn.setOnClickListener(this)
        try {
            if (requireArguments() != null && requireArguments().containsKey(Constant.WEBVIEW_LINK)) {
                requireArguments().getParcelableArrayList<videoObjects>(Constant.WEBVIEW_LINK)
                    ?.let { obj = it }
                requireArguments().getInt(Constant.POSITION)?.let {
                    next = it
                    position = it
                    back = it
                    Log.i("nextBack", "$next, $back")
                }
                webview(
                    obj!![next].videoUrl,
                    obj!![next].heading,
                    obj!![next].text
                )

                counter= object : CountDownTimer(maxCounter, diff) {
                    override fun onTick(millisUntilFinished: Long) {
                        val diff: Long = maxCounter - millisUntilFinished
                        finalTime=(diff / 1000).toString()
                        Log.i("TickTick",finalTime)
                        //here you can have your logic to set text to edittext
                    }

                    override fun onFinish() {

                    }
                }.start()

            }
        } catch (e: IllegalStateException) {
        }
    }



    fun updateAppTime(videoUrl: String, heading: String, text: String) {
        (activity as ActivityDashboard).globalClass?.showDialog(activity)
        val requestBody: RequestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("pageName",heading)
            .addFormDataPart("phone",sessionManager!!.getStringVal(Constant.MOBILE)!!)
            .addFormDataPart("exerciseName", heading)
            .addFormDataPart("videoScreenTime", finalTime)
            .addFormDataPart("videoUrl",videoUrl)
            .build()
        HBLHRStore.instance?.history(
            RetrofitEnums.URL_HBL,
            requestBody, object : RegisterCallBack {
                @SuppressLint("WrongConstant")
                override fun Success(response: BaseResponse) {
//                    ToastUtils.showToastWith(activity as ActivityDashboard, "His", "")
                    (activity as ActivityDashboard).globalClass?.hideLoader()
                }

                override fun Failure(response: BaseResponse) {
                    ToastUtils.showToastWith(activity as ActivityDashboard, response.message, "")
                    (activity as ActivityDashboard).globalClass?.hideLoader()
                }
            })
    }

    private fun setData(videoUrl: String, heading: String, text: String) {
//        val rootRef = FirebaseDatabase.getInstance().reference
//        val dbRef = rootRef.child("upwork-f2a18-default-rtdb").child("RegisteredUsers")
//        dbRef.addListenerForSingleValueEvent(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                if (dataSnapshot.exists()) {
//                    for (d in dataSnapshot.children) {
//                        if (d.child("mobile").value == sessionManager!!.getStringVal(Constant.MOBILE)) {
//                            val result: HashMap<String, Any> = HashMap()
//                            result["VideoScreentime"] = convertSeconds(finalTime.toInt())+""
//                            result["pageName"] = heading
//                            result["exerciseName"] = heading
//                            result["videoUrl"] = videoUrl
//                            result["translation points"] = text
//                            d.key?.let {
//                                FirebaseDatabase.getInstance().reference.child("upwork-f2a18-default-rtdb")
//                                    .child("RegisteredUsers")
//                                    .child(it).child("Records").push().setValue(result)
//                            }
//                            break
//                        }
//                    }
//                }
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//            } //onCancelled
//        })
    }



    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.backBtn -> {
                if (back in 1..8 &&
                    obj!!.size == 9
                ) {
                    back--
                    next = back
                    updateAppTime(
                        obj!![next].videoUrl,
                        obj!![next].heading,
                        obj!![next].text
                    )
                    Log.i(
                        "btnClick", "$back, ${
                            obj!!.size
                        }"
                    )
                    webview(
                        obj!![next].videoUrl,
                        obj!![next].heading,
                        obj!![next].text
                    )
                } else if (back > 0 && back < 2 &&
                    obj!!.size == 2
                ) {
                    updateAppTime(
                        obj!![next].videoUrl,
                        obj!![next].heading,
                        obj!![next].text
                    )
                    back--
                    next = back
                    webview(
                        obj!![next].videoUrl,
                        obj!![next].heading,
                        obj!![next].text
                    )
                    Log.i(
                        "btnClick", "$back, ${
                            obj!!.size
                        }"
                    )
                } else if (back in 1..13 &&
                    obj!!.size == 14
                ) {
                    updateAppTime(
                        obj!![next].videoUrl,
                        obj!![next].heading,
                        obj!![next].text
                    )
                    back--
                    next = back
                    webview(
                        obj!![next].videoUrl,
                        obj!![next].heading,
                        obj!![next].text
                    )
                    Log.i(
                        "btnClick", "$back, ${
                            obj!!.size
                        }"
                    )
                }
            }
            R.id.nextBtn -> {
                if (next in 0..7 &&
                    obj!!.size == 9
                ) {
                    updateAppTime(
                        obj!![next].videoUrl,
                        obj!![next].heading,
                        obj!![next].text
                    )
                    next++
                    back = next
                    webview(
                        obj!![next].videoUrl,
                        obj!![next].heading,
                        obj!![next].text
                    )
                    Log.i(
                        "btnClick", "$back, ${
                            obj!!.size
                        }"
                    )
                } else if (next >= 0 && next < 1 &&
                    obj!!.size == 2
                ) {
                    updateAppTime(
                        obj!![next].videoUrl,
                        obj!![next].heading,
                        obj!![next].text
                    )
                    next++
                    back = next
                    webview(
                        obj!![next].videoUrl,
                        obj!![next].heading,
                        obj!![next].text
                    )
                    Log.i(
                        "btnClick", "$back, ${
                            obj!!.size
                        }"
                    )
                } else if (next in 0..12 &&
                    obj!!.size == 14
                ) {
                    updateAppTime(
                        obj!![next].videoUrl,
                        obj!![next].heading,
                        obj!![next].text
                    )
                    next++
                    back = next
                    webview(
                        obj!![next].videoUrl,
                        obj!![next].heading,
                        obj!![next].text
                    )
                    Log.i(
                        "btnClick", "$back, ${
                            obj!!.size
                        }"
                    )
                }

            }
        }
    }

    override fun onDestroy() {
        counter!!.cancel()
        var request=historyRequest()
        request.exerciseName= obj!![next].heading
        request.phone=  sessionManager!!.getStringVal(Constant.MOBILE)!!
        request.pageName=  obj!![next].heading
        request.videoScreenTime=  finalTime
        request.videoUrl=  obj!![next].videoUrl
        sessionManager!!.setHistory(request)
        super.onDestroy()
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