package com.paa.allsafeproject

import android.content.Context
import android.content.ContextWrapper
import android.nfc.Tag
import android.os.Environment
import android.util.Log
import com.paa.allsafeproject.activities.MainActivity
import com.paa.allsafeproject.data_structs.AttachedMail
import java.io.File
import java.io.OutputStreamWriter
import java.util.ArrayList

class FileUtils {

    private val TAG = "FILE_UTILS"

    companion object {
        fun createMailListFile(mails: ArrayList<AttachedMail>, context: Context): File {
            val file = File(context.filesDir, "mails.txt")
            Log.d("FILE_UTILS", "Mails found - ${mails.size}")
            val outputStreamWriter = OutputStreamWriter(file.outputStream())
            for (str in mails) {
                outputStreamWriter.write("${str.mail}\n")
            }
            outputStreamWriter.close()
            return file
        }
    }
}