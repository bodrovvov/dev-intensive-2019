package ru.skillbranch.Devintensive.models

class UserView(
    val id: String,
    val fullName: String,
    val nickName: String,
    val avatar: String? = null,
    val status: String? = "offline",
    val initials: String?
) {
    fun printMe() {
        println("""
                id: $id
                fullname: $fullName
                nickname: $nickName
                avatar: $avatar
                status: $status
                initials: $initials
            """.trimIndent()
        )
    }
}