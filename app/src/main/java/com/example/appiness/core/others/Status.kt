package com.example.appiness.core.others

sealed class Status {
    object Error : Status()
    object Loading : Status()
    object Success : Status()
    object Fail : Status()
    object Failed : Status()
    object AuthFail : Status()
    object DefaultError : Status()
    object ErrorUserName : Status()
    object ErrorPassword : Status()
    object ShowProgress : Status()
    object HideProgress : Status()
}