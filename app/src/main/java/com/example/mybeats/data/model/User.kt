package com.example.mybeats.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val name: String,
    val userName: String,
    val password: String
) : Parcelable
