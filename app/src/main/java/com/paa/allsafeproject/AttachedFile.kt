package com.paa.allsafeproject

class AttachedFile(val path:String, val size:Int) {
    lateinit var fileName:String
    init {
        TODO("Обработка регуляркой пути до файла для получения имени")
        fileName = path
    }
}