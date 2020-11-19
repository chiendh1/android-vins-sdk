package com.vinstudio.vinsdk.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.vinstudio.vinsdk.model.App
import kotlinx.android.parcel.Parcelize

@Parcelize
class ResultPromo(
    @SerializedName("code")
    val code: String,
    @SerializedName("app")
    val apps: App
) : Parcelable
