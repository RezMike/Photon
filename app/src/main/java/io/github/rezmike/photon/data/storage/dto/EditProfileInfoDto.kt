package io.github.rezmike.photon.data.storage.dto

import android.os.Parcel
import android.os.Parcelable

class EditProfileInfoDto(var name: String = "", var login: String = "") : Parcelable {

    constructor(parcel: Parcel) : this() {
        name = parcel.readString()
        login = parcel.readString()
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(name)
        dest.writeString(login)
    }

    override fun describeContents()=  0

    companion object CREATOR : Parcelable.Creator<EditProfileInfoDto> {
        override fun createFromParcel(parcel: Parcel): EditProfileInfoDto {
            return EditProfileInfoDto(parcel)
        }

        override fun newArray(size: Int): Array<EditProfileInfoDto?> {
            return arrayOfNulls(size)
        }
    }
}