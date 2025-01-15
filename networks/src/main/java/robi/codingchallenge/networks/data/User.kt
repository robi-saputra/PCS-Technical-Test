package robi.codingchallenge.networks.data

import android.os.Parcel
import android.os.Parcelable

data class User(
    val addressNo: String?,
    val avatar: String?,
    val city: String?,
    val country: String?,
    val county: String?,
    val createdAt: String?,
    val id: String?,
    val name: String?,
    val street: String?,
    val zipCode: String?
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(addressNo)
        parcel.writeString(avatar)
        parcel.writeString(city)
        parcel.writeString(country)
        parcel.writeString(county)
        parcel.writeString(createdAt)
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(street)
        parcel.writeString(zipCode)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }
}