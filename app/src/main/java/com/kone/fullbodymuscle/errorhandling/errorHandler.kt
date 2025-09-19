package com.kone.fullbodymuscle.errorhandling

import android.net.http.HttpException
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresExtension
import okio.IOException
import kotlin.math.log

typealias MultiError = Exception

 fun errorMessage(e: MultiError){
   Log.e("ViewModel", "check the Error ", e )
}



