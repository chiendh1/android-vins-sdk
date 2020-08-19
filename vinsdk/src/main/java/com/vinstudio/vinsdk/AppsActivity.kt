package com.vinstudio.vinsdk

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.vinstudio.vinsdks.ApiService
import com.vinstudio.vinsdks.Result
import com.vinstudio.vinsdks.ServiceBuilder
import kotlinx.android.synthetic.main.activity_apps.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AppsActivity : AppCompatActivity() {

    private var serviceBuider: ApiService? = null
    private var callApi: Call<Result>? = null
    private var appAdapter: AppAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apps)
        initActionbar()
        appAdapter = AppAdapter()
        serviceBuider = ServiceBuilder.buildService(ApiService::class.java)
        getAllAppAndroid()
        recyclerViewApps.adapter = appAdapter
        appAdapter!!.setItemClick {
            val uri =
                Uri.parse("market://details?id="+it.applicationId)
            val myAppLinkToMarket = Intent(Intent.ACTION_VIEW, uri)
            try {
                startActivity(myAppLinkToMarket)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(this, " Unable to find market app", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun getAllAppAndroid() {
        callApi = serviceBuider!!.getAllApp("android")
        callApi?.enqueue(object : Callback<Result> {
            override fun onFailure(call: Call<Result>, t: Throwable) {

            }

            override fun onResponse(call: Call<Result>, response: Response<Result>) {
                appAdapter!!.submitList(response.body()?.apps)
            }
        })
    }

    fun initActionbar() {
        setSupportActionBar(toolbarApp)
        imageViewBack.setOnClickListener {
            finish()
        }
    }

    companion object {
        fun getIntent(context: Context) =
            Intent(context, AppsActivity::class.java)
    }
}
