package com.paa.allsafeproject.data_structs

import java.io.File

// todo(Наследование от File)

data class TreeFile(val file: File, val name:String = file.name)