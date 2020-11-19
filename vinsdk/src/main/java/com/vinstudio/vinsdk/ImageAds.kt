package com.vinstudio.vinsdk

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.os.Handler
import android.util.AttributeSet
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.RelativeLayout
import com.bumptech.glide.Glide
import com.vinstudio.vinsdk.api.ApiService
import com.vinstudio.vinsdk.api.ServiceBuilder
import com.vinstudio.vinsdk.callback.VinOnClickListener
import com.vinstudio.vinsdk.model.ResultPromo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ImageAds(context: Context, attrs: AttributeSet) : RelativeLayout(context, attrs) {

    private var serviceBuider: ApiService? = null
    private var callApi: Call<ResultPromo>? = null
    var mImgSource : ImageView? = null
    var mImgSourceBackground : ImageView? = null
    var mImgSourceAdsNative : ImageView? = null
    var mImgSourceCancle : ImageView? = null
    var onClickListener : VinOnClickListener?=null


    init {
        init(attrs)
    }

    fun registerEventListener(mListener: VinOnClickListener) {
        onClickListener = mListener
    }

    private fun init(attrs: AttributeSet) {
        mImgSource = ImageView(context)
        mImgSourceCancle = ImageView(context)

        mImgSourceCancle!!.setImageDrawable(resources.getDrawable(R.drawable.ic_error))
        val imgSrcParamCancel = LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        )
        imgSrcParamCancel.addRule(ALIGN_PARENT_RIGHT)
        imgSrcParamCancel.setMargins(20, 20, 20, 20)

        mImgSourceAdsNative = ImageView(context)
        mImgSourceAdsNative!!.setBackgroundColor(Color.parseColor("#a8dda8"))
        mImgSourceAdsNative!!.scaleType = ImageView.ScaleType.FIT_CENTER

        mImgSourceBackground = ImageView(context)
        mImgSourceBackground!!.scaleType = ImageView.ScaleType.CENTER_CROP

        val imgSrcParam = LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        )
        imgSrcParam.addRule(CENTER_IN_PARENT, TRUE)

        val imgSrcParamBackground = LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
        )

        val height = Resources.getSystem().getDisplayMetrics().heightPixels;
        val width = Resources.getSystem().getDisplayMetrics().widthPixels;

        val imgSrcParamAdsNative = LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, (width / 0.75).toInt()
        )

        addView(mImgSourceBackground, imgSrcParamBackground)
        addView(mImgSource, imgSrcParam)

        if(attrs != null){
            val a = context.obtainStyledAttributes(attrs, R.styleable.ImageAds)
            val imgSrcDrawableBackground = a.getDrawable(R.styleable.ImageAds_backgroundSplash)
            if (imgSrcDrawableBackground != null) {
                mImgSourceBackground!!.setImageDrawable(imgSrcDrawableBackground)
            }
            val imgSrcDrawableLogo = a.getDrawable(R.styleable.ImageAds_logo_app)
            if (imgSrcDrawableLogo != null) {
                mImgSource!!.setImageDrawable(imgSrcDrawableLogo)
            }
            val imgLogoHeight = a.getLayoutDimension(R.styleable.ImageAds_height_logo, 0)
            if(imgLogoHeight!=0){
                mImgSource!!.getLayoutParams().height = imgLogoHeight
            }else{
                mImgSource!!.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT
            }
            val imgLogoWidth = a.getLayoutDimension(R.styleable.ImageAds_width_logo, 0)
            if(imgLogoWidth!=0){
                mImgSource!!.getLayoutParams().width = imgLogoWidth
            }else{
                mImgSource!!.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT
            }
        }



        serviceBuider = ServiceBuilder.buildService(ApiService::class.java)
        callApi = serviceBuider!!.getPromo("android", "com.vinstudio.contactcleaner")
        callApi?.enqueue(object : Callback<ResultPromo> {
            override fun onFailure(call: Call<ResultPromo>, t: Throwable) {

            }

            override fun onResponse(call: Call<ResultPromo>, response: Response<ResultPromo>) {
                val bottomUp: Animation = AnimationUtils.loadAnimation(
                    context,
                    R.anim.bottom_up
                )
                bottomUp.duration = 1000
                bottomUp.fillAfter = true
                mImgSource!!.startAnimation(bottomUp)
                mImgSource!!.visibility = VISIBLE
                bottomUp.setAnimationListener(object : Animation.AnimationListener {
                    override fun onAnimationStart(p0: Animation?) {

                    }

                    override fun onAnimationEnd(p0: Animation?) {
                        addView(mImgSourceAdsNative, imgSrcParamAdsNative)
                        addView(mImgSourceCancle, imgSrcParamCancel)
                        Glide.with(context).load(response.body()!!.apps.banner).into(
                            mImgSourceAdsNative!!
                        )
                        Handler().postDelayed(Runnable { onClickListener!!.startActivity() }, 4000)
                    }

                    override fun onAnimationRepeat(p0: Animation?) {

                    }

                })
            }
        })
    }
}