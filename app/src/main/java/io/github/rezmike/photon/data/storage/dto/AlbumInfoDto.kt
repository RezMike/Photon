package io.github.rezmike.photon.data.storage.dto

import android.os.Parcel
import android.os.Parcelable


class AlbumInfoDto(var name: String = "", var description: String = "") : Parcelable {

    constructor(parcel: Parcel) : this() {
        name = parcel.readString()
        description = parcel.readString()
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(name)
        dest.writeString(description)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<AlbumInfoDto> {
        override fun createFromParcel(parcel: Parcel): AlbumInfoDto {
            return AlbumInfoDto(parcel)
        }

        override fun newArray(size: Int): Array<AlbumInfoDto?> {
            return arrayOfNulls(size)
        }
    }
}