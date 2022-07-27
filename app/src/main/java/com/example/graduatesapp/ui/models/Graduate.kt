package com.example.graduatesapp.ui.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Graduate(
    val id:Int,
    val firstName:String,
    val lastName:String,
    val email:String,
    val tel:String,
    val address:String,
    val bio:String,
    val linkedln:String?,
    val avatar:String?,
   val sectorId:Int,
   val diplomaId:Int,
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(firstName)
        parcel.writeString(lastName)
        parcel.writeString(email)
        parcel.writeString(tel)
        parcel.writeString(address)
        parcel.writeString(bio)
        parcel.writeString(linkedln)
        parcel.writeString(avatar)
        parcel.writeInt(sectorId)
        parcel.writeInt(diplomaId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Graduate> {
        override fun createFromParcel(parcel: Parcel): Graduate {
            return Graduate(parcel)
        }

        override fun newArray(size: Int): Array<Graduate?> {
            return arrayOfNulls(size)
        }
    }
}