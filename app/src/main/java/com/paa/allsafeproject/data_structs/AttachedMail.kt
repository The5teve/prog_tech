package com.paa.allsafeproject.data_structs

import android.os.Parcel
import android.os.Parcelable

data class AttachedMail(val mail: String?): Parcelable {
    constructor(parcel: Parcel) : this(parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(mail)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AttachedMail> {
        override fun createFromParcel(parcel: Parcel): AttachedMail {
            return AttachedMail(parcel)
        }

        override fun newArray(size: Int): Array<AttachedMail?> {
            return newArray(size) //?
        }
    }
}