package com.mochrasya.doctorrasya.Model

import android.os.Parcel
import android.os.Parcelable

data class DoctorsModel(
    val Address: String = "",
    val Biography: String = "",
    val Id: String = "",
    val Name: String = "",
    val Picture: String = "",
    val Special: String = "",
    val Expriense: String = "",
    val Cost: String = "",
    val Date: String = "",
    val Time: String = "",
    val Location: String = "",
    val Mobile: String = "",
    val Patiens: String = "",
    val Rating: String = "0.0", // Use String here
    val Site: String = ""
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(Address)
        parcel.writeString(Biography)
        parcel.writeString(Id)
        parcel.writeString(Name)
        parcel.writeString(Picture)
        parcel.writeString(Special)
        parcel.writeString(Expriense)
        parcel.writeString(Cost)
        parcel.writeString(Date)
        parcel.writeString(Time)
        parcel.writeString(Location)
        parcel.writeString(Mobile)
        parcel.writeString(Patiens)
        parcel.writeString(Rating) // Corrected: use writeString() for Rating
        parcel.writeString(Site)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DoctorsModel> {
        override fun createFromParcel(parcel: Parcel): DoctorsModel {
            return DoctorsModel(parcel)
        }

        override fun newArray(size: Int): Array<DoctorsModel?> {
            return arrayOfNulls(size)
        }
    }
}
