package com.projects.kanade.ui.reusables

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import java.time.LocalTime


@Composable
@RequiresApi(Build.VERSION_CODES.O)
fun GetDateComp() {
    val text = GetDay()
    Text(text = "${text}")
}

@Preview
@Composable
@RequiresApi(Build.VERSION_CODES.O)
fun GetDateCompPrev() {
    GetDateComp()
}

fun GetMonth(): String {
    val calendar = Calendar.getInstance().time
    return SimpleDateFormat("MM").format(calendar)
}

fun GetDay(): String {
    val calendar = Calendar.getInstance().time
    return SimpleDateFormat("EEEE").format(calendar)
}

fun GetDate(): String {
    val calendar = Calendar.getInstance().time
    return SimpleDateFormat("dd/MM/yyyy").format(calendar)
}

@RequiresApi(Build.VERSION_CODES.O)
fun GetTime(): LocalTime {
    val time = LocalTime.now()
    return time
}

@RequiresApi(Build.VERSION_CODES.O)
fun GetTimeString(): String {
    val calendar = Calendar.getInstance().time
    return SimpleDateFormat("HH:mm:ss").format(calendar)
}

@RequiresApi(Build.VERSION_CODES.O)
fun compareTime(limithour: Int, limitminute: Int): Boolean {
    val currenttime = GetTime()
    val limittime = timeLimit(limithour, limitminute)

    return currenttime < limittime
}

@RequiresApi(Build.VERSION_CODES.O)
fun compareTwoTimes(selectedhour: Int, selectedminute: Int, limithour: Int, limitminute: Int): Boolean {
    val selectedtime = timeLimit(selectedhour, selectedminute)
    val limittime = timeLimit(limithour, limitminute)

    return selectedtime < limittime
}


@RequiresApi(Build.VERSION_CODES.O)
fun timeLimit(jam: Int, minute: Int): LocalTime {
    return LocalTime.of(jam, minute, 0)
}

fun serviceTime(time: String, predictedtime: Int): String {
    val predmin = predictedtime/60

    val hourten = time[0].digitToInt()*10
    val hourone = time[1].digitToInt()
    var resulthour = hourten + hourone

    val minuteten = time[3].digitToInt()*10
    val minuteone = time[4].digitToInt()
    val minute = minuteten + minuteone

    var resultmin = minute + predmin
    if (resultmin >= 60) {
        resultmin -= 60
        resulthour += 1
    }
    if (resulthour >= 24) {
        resulthour -= 24
    }

    val stringmin = if (resultmin < 10) {
            "0$resultmin"
        }
        else {
            "$resultmin"
            }

    val stringhour = if (resulthour < 10) {
            "0$resulthour"
        }
        else {
            "$resulthour"
            }
    return "$stringhour:$stringmin"
}

fun timeElapsed(currentTime: String, baseTime: String): Int {
    val timehour = currentTime[0].digitToInt()*10 + currentTime[1].digitToInt()
    val timeminute = currentTime[3].digitToInt()*10 + currentTime[4].digitToInt()

    val basehour = baseTime[0].digitToInt()*10 + baseTime[1].digitToInt()
    val baseminute = baseTime[3].digitToInt()*10 + baseTime[4].digitToInt()

    var resulthour = timehour - basehour
    var resultminute: Int
    if (timeminute - baseminute < 0) {
        resultminute = 60 + (timeminute - baseminute)

        resulthour -= 1
    } else {
        resultminute = timeminute - baseminute
    }

    return resulthour*60*60 + resultminute*60
}

@RequiresApi(Build.VERSION_CODES.O)
fun determineTime(): Int {
    return if (compareTime(9, 0)) {
        1 //"08.00 - 09.00"
    } else if (compareTime(10,0) && !compareTime(9,0)) {
        2 //"09.00 - 10.00"
    } else if (compareTime(11,30) && !compareTime(10,0)) {
        3 //"10.00 - 11.30"
    } else if (compareTime(13,30) && !compareTime(12,30)) {
        4 //"12.30 - 13.30"
    } else if (compareTime(14,30) && !compareTime(13,30)) {
        5 //"13.30 - 14.30"
    } else {
        1 //"08.00 - 09.00"
    }
}

fun desiredToSession(time: Int): String {
    return if (time == 1 || time == 2 || time == 3) {
        "08.00 - 11.30"
    }
    else {
        "12.30 - 14.30"
    }
}



