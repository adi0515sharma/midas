package com.example.midas.room

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "userinfo")
data class UserInfo(
    @PrimaryKey(autoGenerate = true)
    val userId : Int = 0,
    @ColumnInfo(name = "userMobileNo")
    val userMobileNo : String?,
    @ColumnInfo(name = "userName")
    var userName : String?,
    @ColumnInfo(name = "userPassword")
    var userPassword : String?,
    @ColumnInfo(name = "userEmail")
    var userEmail : String?,
    @ColumnInfo(name = "isRegistered")
    var isRegistered : Boolean,
): Parcelable{
    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(userId)
        dest.writeString(userMobileNo)
        dest.writeString(userName)
        dest.writeString(userPassword)
        dest.writeString(userEmail)
        dest.writeByte(if (isRegistered) 1 else 0)
    }

    companion object {
        @JvmField
        val CREATOR = object : Parcelable.Creator<UserInfo> {
            override fun createFromParcel(source: Parcel): UserInfo {
                return UserInfo(
                    source.readInt(),
                    source.readString(),
                    source.readString(),
                    source.readString(),
                    source.readString(),
                    source.readByte() != 0.toByte()
                )
            }

            override fun newArray(size: Int): Array<UserInfo?> {
                return arrayOfNulls(size)
            }
        }
    }
}
