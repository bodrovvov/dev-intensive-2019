package ru.skillbranch.Devintensive.extensions

import java.text.SimpleDateFormat
import java.util.*

const val SECOND = 1000L
const val MINUTE = 60 * SECOND
const val HOUR = 60 * MINUTE
const val DAY = 24 * HOUR


fun Date.format(pattern: String = "HH:mm:ss dd.MM.yy"): String {
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

fun Date.add(value: Int, units: TimeUnits = TimeUnits.SECOND): Date {
    var time = this.time
    time += when (units) {
        TimeUnits.SECOND -> value * SECOND
        TimeUnits.MINUTE -> value * MINUTE
        TimeUnits.HOUR -> value * HOUR
        TimeUnits.DAY -> value * DAY
    }
    this.time = time
    return this
}

enum class TimeUnits {
    SECOND,
    MINUTE,
    HOUR,
    DAY
}

fun secondsConverter(arrayOfTime: Array<Int>, timeValue: Long): Array<Int> {
    for (i in arrayOfTime.indices) {
        arrayOfTime[i] = ((arrayOfTime[i] * timeValue) / 1000).toInt()
    }
    return arrayOfTime
}

fun Date.humanizeDiff(date: Date = Date()): String {
    val differenceSeconds: Int = ((Date().time - this.time) / SECOND).toInt()
    var time: String
    var firstMinutesRange: Array<Int> = arrayOf(3, 4, 22, 23, 24, 32, 33, 34) //минуты
    var secondMinutesRange: Array<Int> = arrayOf(
        5, 6, 7, 8, 9, 10, 11, 25, 26,
        27, 28, 29, 30, 35, 36, 37, 38, 39, 40
    ) //минут
    var thirdMinutesRange: Array<Int> = arrayOf(21, 31, 41) // минуту
    firstMinutesRange = secondsConverter(firstMinutesRange, MINUTE)
    secondMinutesRange = secondsConverter(secondMinutesRange, MINUTE)
    thirdMinutesRange = secondsConverter(thirdMinutesRange, MINUTE)
    var firstHourRange: Array<Int> = arrayOf(1, 21) // час
    var secondHourRange: Array<Int> = arrayOf(2, 3, 4, 22) // часа
    var thirdHourRange: Array<Int> =
        Array(16) { i -> i + 5 } //часов
    firstHourRange = secondsConverter(firstHourRange, HOUR)
    secondHourRange = secondsConverter(secondHourRange, HOUR)
    thirdHourRange = secondsConverter(thirdHourRange, HOUR)
    var daysArray = Array(359) { i -> i + 2 }
    daysArray = secondsConverter(daysArray, DAY)
    val minWord = "минут"
    val hourWord = "час"

    time = when {
        differenceSeconds in 0..1 -> "только что"
        differenceSeconds in 2..45 -> "несколько секунд назад"
        differenceSeconds in 46..75 -> "минуту назад"
        differenceSeconds in 76..120 -> "две минуты назад"
        differenceSeconds in 45 * 60..75 * 60 -> "час назад"
        differenceSeconds in 79201..93601 -> "день назад"
        firstMinutesRange.contains(differenceSeconds) ->
            "${differenceSeconds / 60} ${minWord}ы назад"
        secondMinutesRange.contains(differenceSeconds) ->
            "${differenceSeconds / 60} $minWord назад"
        thirdMinutesRange.contains(differenceSeconds) ->
            "${differenceSeconds / 60} $minWord назад"
        firstHourRange.contains(differenceSeconds) ->
            "${differenceSeconds / 3600} $hourWord назад"
        secondHourRange.contains(differenceSeconds) ->
            "${differenceSeconds / 3600} ${hourWord}а назад"
        thirdHourRange.contains(differenceSeconds) ->
            "${differenceSeconds / 3600} ${hourWord}ов назад"
        else -> "более года назад"
    }

    for (i in daysArray.indices) {
       val daysCount=daysArray[i]/86400
        if (differenceSeconds/86400 == daysCount) {
            time = if (daysArray[i] in 2..4)
                "$daysCount дня назад"
            else if (daysCount in 5..9)
                "$daysCount дней назад"
            else {
                if ((daysCount / 10) % 10 == 1)
                    "$daysCount дней назад"
                else if (daysCount % 10 == 0 || daysCount % 10 >= 5)
                    "$daysCount дней назад"
                else if (daysCount % 10 == 2 || daysCount % 10 == 3 || daysCount % 10 == 4)
                    "$daysCount дня назад"
                else
                    "$daysCount день назад"
            }
        }
    }
    return time
}
