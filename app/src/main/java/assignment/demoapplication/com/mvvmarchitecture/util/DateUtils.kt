package assignment.demoapplication.com.mvvmarchitecture.util

import java.text.SimpleDateFormat

val DD_MMMM_format = "dd MMMM"
val DD_MMMM_YYYY_format = "dd MMMM yyyy"
val yyyy_MM_dd_T_HH_mm_ss = "yyyy-MM-dd'T'HH:mm:ssXXX"
val DATE_TIME_FORMAT_DD_MM_YYYY_SLASH_HH_MM = "dd/MM/yyyy hh:mm aa"

fun convertDateFormat(date: String, inputFormat: String, outputFormat: String): String {
    var _date = ""
    if (!date.equals("null", ignoreCase = true)) {
        try {
            val inputFormatter = SimpleDateFormat(inputFormat)//"yyyy-MM-dd"
            val date1 = inputFormatter.parse(date)

            val outputFormatter = SimpleDateFormat(outputFormat)//"dd-MM-yyyy"
            _date = outputFormatter.format(date1) //


            //          // createLogger(information = "convertDateFormat: "+date.toString())

        } catch (e: Exception) {

            //          // createLogger(error = e.toString())

            //Crashlytics.logException(e);
        }

        return _date
    } else {
        return _date
    }
}