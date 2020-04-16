package com.paa.allsafeproject

import java.io.File
import java.nio.file.Files

class AttachedFile(val path: String,
                   val fileObj: File=File(path),
                   var name: String=fileObj.name,
                   var size: Int= (fileObj.length().toInt()))