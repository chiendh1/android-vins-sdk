package com.vinstudio.vinsdk

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.vinstudio.vinsdk.api.ApiService
import com.vinstudio.vinsdk.model.ClickApp
import com.vinstudio.vinsdk.model.Result
import com.vinstudio.vinsdk.api.ServiceBuilder
import com.vinstudio.vinsdk.model.App
import kotlinx.android.synthetic.main.activity_apps.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AppsActivity : AppCompatActivity() {

    private var serviceBuider: ApiService? = null
    private var callApi: Call<Result>? = null
    private var callApiSend: Call<ClickApp>? = null
    private var appAdapter: AppAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apps)
        initActionbar()
        appAdapter = AppAdapter()
        serviceBuider = ServiceBuilder.buildService(ApiService::class.java)
        val applicationId = intent.getStringExtra("applicationId")
        getAllAppAndroid(applicationId!!)
        recyclerViewApps.adapter = appAdapter
        appAdapter!!.setItemClick {
            val uri =
                Uri.parse("market://details?id=" + it.applicationId)
            val myAppLinkToMarket = Intent(Intent.ACTION_VIEW, uri)
            try {
                startActivity(myAppLinkToMarket)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(this, " Unable to find market app", Toast.LENGTH_LONG).show()
            }
            sendClickApp(it.applicationId, applicationId)
        }
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

    fun getAllAppAndroid(applicationId: String) {
        callApi = serviceBuider!!.getAllApp("android", applicationId)
        callApi?.enqueue(object : Callback<Result> {
            override fun onFailure(call: Call<Result>, t: Throwable) {

            }

            override fun onResponse(call: Call<Result>, response: Response<Result>) {
                var listNew = mutableListOf<App>()
                val pm = packageManager
                val packages = pm.getInstalledApplications(PackageManager.GET_META_DATA)

                for (packageInfo in packages) {
                    Log.d("package", "Installed package :" + packageInfo.packageName)

                }

                var check =0
                for (i in 0..response.body()!!.apps.size - 1){
                    for (j in 0..packages.size-1){
                        if (packages[j].packageName == response.body()!!.apps[i].applicationId){
                            check =1
                        }
                    }
                    if(check==0){
                        listNew.add(response.body()!!.apps[i])
                    }else{
                        check=0
                    }
                }
                appAdapter!!.submitList(listNew)
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
        @JvmStatic fun getIntent(context: Context, applicationId: String) =
            Intent(context, AppsActivity::class.java).putExtra("applicationId", applicationId)
    }
}
