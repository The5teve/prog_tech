package com.paa.allsafeproject.data_structs

import android.os.Parcel
import android.os.Parcelable
import java.io.File
import java.nio.file.Files

data class AttachedFile(val path: String,
                   val fileObj: File=File(path),
                   var name: String=fileObj.name,
                   var size: Int= (fileObj.length().toInt())) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString() // todo: Null-проверка
//        TODO("fileObj"),
//        parcel.readString(),
//        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(path)
        // Не нужно передавать имя и размер файла, т.к. конструктору требуется только путь к файлу?
//        parcel.writeString(name)
//        parcel.writeInt(size)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AttachedFile> {
        override fun createFromParcel(parcel: Parcel): AttachedFile {
            return AttachedFile(parcel)
        }

        override fun newArray(size: Int): Array<AttachedFile?> {
            return newArray(size) //?
        }
    }

    override fun toString(): String {
        return "Data struct: $path - $size"
    }
}