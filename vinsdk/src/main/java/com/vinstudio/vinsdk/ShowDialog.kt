package com.vinstudio.vinsdk

import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import com.bumptech.glide.Glide
import com.vinstudio.vinsdks.ApiService
import com.vinstudio.vinsdks.App
import com.vinstudio.vinsdks.ServiceBuilder
import kotlinx.android.synthetic.main.dialog_promo.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


object ShowDialog {
    private var serviceBuider: ApiService? = null
    private var callApi: Call<ResultPromo>? = null
    private var sharedPreferences: SharedPreferences? = null
    private var numberOpenAppOld: Int = 0
    private var numberOpenAppNew: Int = 0
    private var callApiSend: Call<ClickApp>? = null

    fun promo(context: Context, applicationId: String) {
        serviceBuider = ServiceBuilder.buildService(ApiService::class.java)
        sharedPreferences = context.getSharedPreferences("NumberOpenApp", Context.MODE_PRIVATE)
        numberOpenAppOld = sharedPreferences!!.getInt("Number", 0)
        val editor = sharedPreferences!!.edit()
        numberOpenAppNew = numberOpenAppOld + 1
        editor.putInt("Number", numberOpenAppNew)
        editor.apply()
        if (numberOpenAppNew % 3 == 0) {
            getPromoAndroid(context, applicationId)
        }
    }

    private fun getPromoAndroid(context: Context, applicationId: String) {
        callApi = serviceBuider!!.getPromo("android", applicationId)
        callApi?.enqueue(object : Callback<ResultPromo> {
            override fun onFailure(call: Call<ResultPromo>, t: Throwable) {

            }

            override fun onResponse(call: Call<ResultPromo>, response: Response<ResultPromo>) {
                showDialog(context, response.body()!!.apps, applicationId)
            }
        })
    }

    fun sendClickApp(targetAppId: String, appClientId: String) {
        callApiSend = serviceBuider!!.postClickApp(targetAppId, appClientId)
        callApiSend?.enqueue(object : Callback<ClickApp> {
            override fun onFailure(call: Call<ClickApp>, t: Throwable) {

            }

            override fun onResponse(call: Call<ClickApp>, response: Response<ClickApp>) {
                Log.i("LOG", "post submitted to API." + response.body().toString());
            }

        })
    }

    private fun showDialog(context: Context, app: App, applicationId: String) {
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_promo, null)
        val dialog = Dialog(context, R.style.DialogCustomTheme)
        dialog.setContentView(view)
        dialog.show()
        val displayMetrics = DisplayMetrics()
        dialog.window?.windowManager?.getDefaultDisplay()?.getMetrics(displayMetrics)
        val width = displayMetrics.widthPixels
        dialog.imageViewAppDialog.layoutParams.width = width
        dialog.imageViewAppDialog.layoutParams.height = (width * 500) / 1024
        dialog.linerLayoutText.layoutParams.width = width
        Glide.with(context).load(app.banner).into(dialog.imageViewAppDialog)
        dialog.textViewNameAppDialog.text = app.name
        dialog.textViewDescriptionAppDialog.text = app.description
        dialog.imageCancelDialog.setOnClickListener {
            dialog.dismiss()
        }
        dialog.linearLayoutDialog.setOnClickListener {
            val uri = Uri.parse("market://details?id=" + app.applicationId)
            val myAppLinkToMarket = Intent(Intent.ACTION_VIEW, uri)
            try {
                context.startActivity(myAppLinkToMarket)
                sendClickApp(app.applicationId, applicationId)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(context, " Unable to find market app", Toast.LENGTH_LONG).show()
            }
        }
    }
}
