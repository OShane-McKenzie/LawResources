package com.litecodez.lawresources

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class ContentProvider:ViewModel() {
    val contentReady = mutableStateOf<Boolean?>(null)
    val policies = mutableStateListOf(Policy())
    val laws = mutableStateListOf<LawSources>()
    val samples = mutableStateOf(Samples())
    val messages = mutableStateOf(Messages())
    val contacts = mutableStateOf(Contacts())
    val values = mutableStateOf(Values())
    val fines = mutableStateOf(Fines())
    val credits = mutableStateOf(Credits())
    val wordingBook = mutableStateOf(WordingBook())
    val policyMap = mutableStateMapOf<String, Policy>()
    val lawMap = mutableStateMapOf<String, LawSources>()
    val displayMessage = mutableStateOf("")
    val writePermission = mutableStateOf(false)
    val connectionStatus = mutableStateOf("")
    val startVideo = mutableStateOf(false)
    val startIntentShare = mutableStateOf(false)
    val slideInLeftRight = mutableStateOf(false)
    val videos = mutableStateOf(Videos())
    val currentVideo = mutableStateOf(Video())
    val feedbackCredential = mutableStateOf("")
    val feedbackStream = mutableStateOf("")
    val feedbackLoaded = mutableStateOf(false)
    val isAppLoading = mutableStateOf(true)
}