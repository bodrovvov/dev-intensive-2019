package ru.skillbranch.Devintensive.extensions

import ru.skillbranch.Devintensive.models.User
import ru.skillbranch.Devintensive.models.UserView


fun User.toUserView(): UserView {
    val nickname = ""
    val initials = ""
    val status =
        when {
            lastVisit == null -> "Еще ни разу не был"
            isOnline -> "online"
            else -> "Последний раз был ${lastVisit!!.humanizeDiff()}"
        }

    return UserView(
        id,
        fullName = "$firstName $lastName",
        nickName = nickname,
        initials = initials,
        status = status
    )


}
