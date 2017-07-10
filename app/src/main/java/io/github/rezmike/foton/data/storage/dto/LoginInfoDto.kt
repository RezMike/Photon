package io.github.rezmike.foton.data.storage.dto

import android.os.Parcel
import android.os.Parcelable

class LoginInfoDto(var email: String = "", var password: String = "") : Parcelable {

    constructor(parcel: Parcel) : this() {
        email = parcel.readString()
        password = parcel.readString()
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(email)
        dest.writeString(password)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<LoginInfoDto> {
        override fun createFromParcel(parcel: Parcel): LoginInfoDto {
            return LoginInfoDto(parcel)
        }

        override fun newArray(size: Int): Array<LoginInfoDto?> {
            return arrayOfNulls(size)
        }
    }
}