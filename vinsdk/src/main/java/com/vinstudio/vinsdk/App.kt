package com.vinstudio.vinsdks

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class App(
    @SerializedName("platform")
    val platform: String,
    @SerializedName("icon")
    val icon: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("banner")
    val banner: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("applicationId")
    val applicationId: String,
    @SerializedName("status")
    val status: String
) : Parcelable
