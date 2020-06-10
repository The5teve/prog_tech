package com.paa.allsafeproject.fragments
//
//import android.Manifest
//import android.R
//import android.app.Activity
//import android.app.AlertDialog
//import android.content.Context
//import android.content.DialogInterface
//import android.content.pm.PackageManager
//import android.os.Build
//import android.os.Environment
//import android.util.Log
//import android.widget.Toast
//import androidx.core.app.ActivityCompat
//import androidx.core.content.ContextCompat.checkSelfPermission
//import com.paa.allsafeproject.FileManager
//import com.paa.allsafeproject.adapters.FileManagerAdapter
//import java.io.File
//
///**
// * ЗАМЕНЕН FileManagerFragment
// */
//
///** Possible ways to get root directory
// * context.getExternalFilesDir(path:String)
// * Environment.getRootDirectory()
// */
//
//class FilePickerDialog(context: Context) : AlertDialog.Builder(context) {
//
//    private val selectedFiles:MutableList<File> = emptyList<File>().toMutableList()
////    private val fileManager:FileManager = FileManager(Environment.getExternalStorageDirectory().absolutePath) // todo: проверить работу
////    private val adapter:FileManagerAdapter = FileManagerAdapter(context, R.layout.select_dialog_item, fileManager.listFiles())
//
//    private val TAG = "FILE_PICKER_DIALOG"
//
//    override fun setItems(
//        items: Array<out CharSequence>?,
//        listener: DialogInterface.OnClickListener?
//    ): AlertDialog.Builder {
//        return super.setItems(items, listener)
//    }
//    init {
////         Log.d(TAG,
////             checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE).toString())
////         val root = context.filesDir // Получаю external хранилище, а не корень
////         val root = Context.getExternalFilesDirs
//         Log.d(TAG, "dir - ${fileManager.curDirFile.absolutePath}")
//         Log.d(TAG, "dir files - ${fileManager.curDirFile.list()}")
////         val adapter: ArrayAdapter<String> = ArrayAdapter<String>(context, R.layout.select_dialog_item, fileManager.curDirFile.list())
//         setPositiveButton(R.string.ok, null)
//         setNegativeButton(R.string.cancel, null)
//
//         setAdapter(adapter, DialogInterface.OnClickListener { dialog, which ->
//            Log.d(TAG, "onClickItem")
//             val selectedFileItem = adapter.getItem(which)
////            Toast.makeText(context, "click", Toast.LENGTH_LONG).show()
//             if (selectedFileItem != null) {
//                 if (selectedFileItem.isDirectory) {
//                     setOnDismissListener {
//                         Toast.makeText(context, "dismiss", Toast.LENGTH_LONG).show()
//                     }
//                 }
//                 } else {
//                         Log.d(TAG, "DialogInterface.OnClickListener : item ${selectedFileItem?.name} added to selected items")
//
//             }
//        })
////         setSingleChoiceItems(adapter, -1, DialogInterface.OnClickListener { dialogInterface: DialogInterface, i: Int ->
////             val selectedItem: String = flowers.get(i)
////             Log.d(TAG, "SetSingleChoice")
//             // Display the selected item's text on snack bar
//
//             // Display the selected item's text on snack bar
////             Snackbar.make(
////                 mCLayout,
////                 "Checked : $selectedItem",
////                 Snackbar.LENGTH_INDEFINITE
////             ).show()
////         })
////         setAdapter(adapter, )
////         setAdapter()
//     }
////    val dialogClickListener =
////        DialogInterface.OnClickListener { dialog, which ->
////            when (which) {
////                DialogInterface.BUTTON_POSITIVE ->                                 // User clicked the Yes button
////                        tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 35)
////                    DialogInterface.BUTTON_NEGATIVE -> {
////            }
////            }
////        }
//
//
////    private var currentDir: String? = null
////    private val DEFAULT_DIR:String = "/"
//
//    override fun setOnCancelListener(onCancelListener: DialogInterface.OnCancelListener?): AlertDialog.Builder {
//        return super.setOnCancelListener(onCancelListener)
//    }
//
//    fun isReadStoragePermissionGranted(): Boolean {
//        return if (Build.VERSION.SDK_INT >= 23) {
//            if (checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
//                === PackageManager.PERMISSION_GRANTED
//            ) {
//                Log.v(TAG, "Permission is granted1")
//                true
//            } else {
//                Log.v(TAG, "Permission is revoked1")
//                ActivityCompat.requestPermissions(
//                    context as Activity,
//                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
//                    3
//                )
//                false
//            }
//        } else { //permission is automatically granted on sdk<23 upon installation
//            Log.v(TAG, "Permission is granted1")
//            true
//        }
//    }
//}