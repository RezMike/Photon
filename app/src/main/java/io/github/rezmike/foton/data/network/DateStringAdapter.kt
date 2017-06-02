package io.github.rezmike.foton.data.network

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson

import java.text.ParseException
import java.text.ParsePosition
import java.util.Date

import io.realm.internal.android.ISO8601Utils

class DateStringAdapter {

    @FromJson
    fun dateFromString(dateString: String): Date {
        var date = Date()
        try {
            date = ISO8601Utils.parse(dateString, ParsePosition(0))
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return date
    }

    @ToJson
    fun dateToString(date: Date): String {
        return date.toString()
    }
}
