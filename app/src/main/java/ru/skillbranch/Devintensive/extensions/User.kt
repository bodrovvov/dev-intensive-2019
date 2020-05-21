package ru.skillbranch.Devintensive.extensions

import ru.skillbranch.Devintensive.models.User
import ru.skillbranch.Devintensive.models.UserView
import ru.skillbranch.Devintensive.utils.Utils


fun User.toUserView(): UserView {
    val nickname = Utils.transliteration("$firstName $lastName")
    val initials = Utils.toInitials(firstName, lastName)
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
