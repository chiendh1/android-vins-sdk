package com.vinstudio.vinsdks

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class Result(
    @SerializedName("code")
    val code: String,
    @SerializedName("apps")
    val apps: List<App>
) : Parcelable
