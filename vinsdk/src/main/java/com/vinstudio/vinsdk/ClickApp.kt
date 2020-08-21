package com.vinstudio.vinsdk

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class ClickApp(
    @SerializedName("targetAppId")
    val targetAppId: String,
    @SerializedName("appClientId")
    val appClientId: String
) : Parcelable
