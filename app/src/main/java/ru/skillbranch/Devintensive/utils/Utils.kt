package ru.skillbranch.Devintensive.utils

object Utils {

    fun parseFullName(fullName: String?): Pair<String?, String?> {

        val parts: List<String>? = fullName?.split(" ")
        val firstName = parts?.getOrNull(0)
        val lastName = parts?.getOrNull(1)
        if (firstName == "") return null to null
        if (lastName == "") return firstName to null
        return firstName to lastName
    }

}