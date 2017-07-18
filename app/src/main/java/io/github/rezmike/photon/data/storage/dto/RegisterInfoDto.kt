package io.github.rezmike.photon.data.storage.dto

import android.os.Parcel
import android.os.Parcelable

class RegisterInfoDto(var login: String = "",
                      var email: String = "",
                      var name: String = "",
                      var password: String = "") : Parcelable {

    constructor(parcel: Parcel) : this() {
        login = parcel.readString()
        email = parcel.readString()
        name = parcel.readString()
        password = parcel.readString()
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(login)
        dest.writeString(email)
        dest.writeString(name)
        dest.writeString(password)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<RegisterInfoDto> {
        override fun createFromParcel(parcel: Parcel): RegisterInfoDto {
            return RegisterInfoDto(parcel)
        }

        override fun newArray(size: Int): Array<RegisterInfoDto?> {
            return arrayOfNulls(size)
        }
    }
}