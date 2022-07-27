package com.example.graduatesapp.ui.models

import android.os.Parcel
import android.os.Parcelable

data class Sector(
    val id:Int,
    val name:String,
    val description:String,
    val school:String,
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(description)
        parcel.writeString(school)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Sector> {
        override fun createFromParcel(parcel: Parcel): Sector {
            return Sector(parcel)
        }

        override fun newArray(size: Int): Array<Sector?> {
            return arrayOfNulls(size)
        }
    }
}

