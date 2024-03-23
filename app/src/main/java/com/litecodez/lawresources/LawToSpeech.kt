package com.litecodez.lawresources

import android.app.Application
import android.content.Context
import android.speech.tts.TextToSpeech
import java.util.Locale


class CustomApplicationContext : Application() {
    lateinit var lawToSpeech: LawToSpeech
        private set

    companion object {
        lateinit var instance: CustomApplicationContext
            private set
    }


    override fun onCreate() {
        super.onCreate()
        instance = this
        lawToSpeech = LawToSpeech(this)
    }
}

class LawToSpeech(private val context: Context) {
    private var textToSpeech: TextToSpeech? = null
    private var isTTSEngineReady = false

    init {
        initialize()
    }

    fun initialize() {
        if (textToSpeech == null) {
            textToSpeech = TextToSpeech(context) { status ->
                if (status == TextToSpeech.SUCCESS) {
                    textToSpeech?.language = Locale.US
                    isTTSEngineReady = true
                } else {
                    isTTSEngineReady  = false
                }
            }
        }
    }

    fun shutdown() {
        textToSpeech?.stop()
        textToSpeech?.shutdown()
        textToSpeech = null
        isTTSEngineReady  = false
    }
    fun stop(){
        textToSpeech?.stop()
    }
    fun isLTSReady():Boolean{
        return isTTSEngineReady
    }

    fun getLTS(speechRate:Float = 0.9f):TextToSpeech?{
        val tts = textToSpeech
        tts?.setSpeechRate(speechRate)
        return tts
    }

    fun isSpeaking():Boolean?{

        return textToSpeech?.isSpeaking
    }
}