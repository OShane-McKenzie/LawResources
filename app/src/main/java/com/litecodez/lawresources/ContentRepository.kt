package com.litecodez.lawresources

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL

class ContentRepository:ViewModel() {
    private val api = ApiEncode().deBase(ApiEncode().decodeApi(EncodedStrings.MAIN_API.getString))
    private val gson = Gson()
    private var apis = Apis()
    private var lawList = LawList()
    private var policyList = PolicyList()
    val contentReady = mutableStateOf<Boolean?>(null)

    init {
        startInit()
    }
    fun apis():Apis{
        return apis
    }
    fun startInit(callBack: () -> Unit={}){
        CoroutineScope(Dispatchers.IO).launch {
            apis = getApis()
            withContext(Dispatchers.Main){
                callBack()
            }
        }
    }
    fun contentFetcher(url:String?, onComplete:(String)->Unit={}){
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val reader = withContext(Dispatchers.IO) {
                    BufferedReader(InputStreamReader(URL(url).openStream())).readText()
                }
                withContext(Dispatchers.Main){
                    onComplete(reader)
                }
            } catch (e: Exception) {
                println("fetch error")
                e.printStackTrace()
            }
        }
    }
    private suspend fun getApis():Apis {

        var cachedData = Apis()
        return try {
            val url = URL(api)

            val apiData = withContext(Dispatchers.IO) {
                val reader = BufferedReader(InputStreamReader(url.openStream()))
                reader.readText()
            }
            cachedData = gson.fromJson(apiData, Apis::class.java)
            return cachedData
        } catch (e: Exception) {
            //getToast(context, e.message.toString(), long = true)
            println("error 1")
            e.printStackTrace()
            contentReady.value = false
            Apis()
        } finally {
            if (contentReady.value == null && "wording" in cachedData.apis) {
                try{
                    val lawApi = cachedData.apis["law_list"]
                    val policyApi = cachedData.apis["policy_list"]

                    contentFetcher(lawApi){
                        lawList = gson.fromJson(it, LawList::class.java)
                    }

                    contentFetcher(policyApi){
                        policyList = gson.fromJson(it, PolicyList::class.java)
                    }

                }catch (e: Exception){
                    //getToast(context, e.message.toString(), long = true)
                    println("error 2")
                    e.printStackTrace()
                    contentReady.value = false
                }finally{
                    if (contentReady.value==null) {
                        contentReady.value = true
                    }
                }
            }
        }
    }

    fun getPolicies(apis: Apis, callBack:(Boolean,String)->Unit={_,_->}){
        contentProvider.policies.clear()
        val apiList = apis.apis.keys.toList()
        try{
            apiList.forEach {
                if (it in policyList.policies) {
                    val url = apis.apis[it]

                    contentFetcher(url){ result ->
                        contentProvider.policies.add(
                            gson.fromJson(
                                result,
                                Policy::class.java
                            )
                        )
                    }
                }
            }
            callBack(true, "Success")
        }catch (e: Exception){
            callBack(false, "$e")
        }
    }

    fun getLaws(apis: Apis, callBack:(Boolean,String)->Unit={_,_->}){
        contentProvider.laws.clear()
        val apiList = apis.apis.keys.toList()
        try{
            apiList.forEach {
                if(it in lawList.laws){
                    val url = apis.apis[it]

                    contentFetcher(url){ result ->
                        contentProvider.laws.add(gson.fromJson(result, LawSources::class.java))
                    }
                }
            }
            callBack(true, "Success")
        }catch (e:Exception){
            callBack(false, "$e")
        }
    }

    fun getSamples(apis: Apis, callBack:(Boolean,String)->Unit={_,_->}){
        val samplesURL = apis.apis["samples"]
        try{
            contentFetcher(samplesURL){
                contentProvider.samples.value = gson.fromJson(it, Samples::class.java)
                callBack(true, "Success")
            }

        }catch (e:Exception){
            callBack(false, "$e samples")
        }
    }

    fun getMessages(apis: Apis, callBack:(Boolean,String)->Unit={_,_->}){
        val messagesURL = apis.apis["messages"]
        try{
            contentFetcher(messagesURL){
                contentProvider.messages.value = gson.fromJson(it, Messages::class.java)
                callBack(true, "Success")
            }

        }catch (e:Exception){
            callBack(false, "$e messages")
        }
    }
    fun getFines(apis: Apis, callBack:(Boolean,String)->Unit={_,_->}){
        val finesURL = apis.apis["fines"]
        try{
            contentFetcher(finesURL){
                contentProvider.fines.value = gson.fromJson(it, Fines::class.java)
                callBack(true, "Success")
            }

        }catch (e:Exception){
            callBack(false, "$e fines")
        }
    }
    fun getCredits(apis: Apis, callBack:(Boolean,String)->Unit={_,_->}){
        val creditsURL = apis.apis["credits"]
        try{
            contentFetcher(creditsURL){
                contentProvider.credits.value = gson.fromJson(it, Credits::class.java)
                callBack(true, "Success")
            }

        }catch (e:Exception){
            callBack(false, "$e credits")
        }
    }
    fun getWordings(apis: Apis, callBack:(Boolean,String)->Unit={_,_->}){
        val wordingURL = apis.apis["wording"]
        try{
            contentFetcher(wordingURL){
                contentProvider.wordingBook.value = gson.fromJson(it, WordingBook::class.java)
                callBack(true, "Success")
            }

        }catch (e:Exception){
            callBack(false, "$e wording")
        }
    }
    fun getContacts(apis: Apis, callBack:(Boolean,String)->Unit={_,_->}){
        val contactsURL = apis.apis["contacts"]
        try{
            contentFetcher(contactsURL){
                contentProvider.contacts.value = gson.fromJson(it, Contacts::class.java)
                callBack(true, "Success")
            }

        }catch (e:Exception){
            callBack(false, "$e contacts")
        }
    }

    fun getValues(apis: Apis, callBack:(Boolean,String)->Unit={_,_->}){
        val valuesURL = apis.apis["bools"]
        try{
            contentFetcher(valuesURL){
                contentProvider.values.value = gson.fromJson(it, Values::class.java)
                callBack(true, "Success")
            }

        }catch (e:Exception){
            callBack(false, "$e bools")
        }
    }

    fun getVideos(apis: Apis, callBack:(Boolean,String)->Unit={_,_->}){
        val videosURL = apis.apis["videos"]
        try{
            contentFetcher(videosURL){
                contentProvider.videos.value = gson.fromJson(it, Videos::class.java)
                callBack(true, "Success")
            }

        }catch (e:Exception){
            callBack(false, "$e videos")
        }
    }
    fun getFeedbackCredential(apis: Apis, callBack:(Boolean,String)->Unit={_,_->}){
        val credentialURL = apis.apis["feedback"]
        try{
            contentFetcher(credentialURL){
                contentProvider.feedbackCredential.value = it.trim().toDecompressedString()
                callBack(true, "Success")
            }

        }catch (e:Exception){
            callBack(false, "$e feedback: $credentialURL")
        }
    }

    fun updateFeedback(context: Context, content:String){

        CoroutineScope(Dispatchers.IO).launch{
//            var newContent = contentProvider.feedbackStream.value.trim().decompressString()
            var newContent = Feedback().readDocument {  }.trim().toDecompressedString()
            newContent+=content
            withContext(Dispatchers.Main){
                contentProvider.feedbackStream.value = newContent.toCompressedString()
            }

            var msg = ""
            val response = Feedback().writeDocument(content = newContent.toCompressedString().trim()){
                msg = it
            }
            if(!response){
                withContext(Dispatchers.Main){
                    getToast(context,msg,true)
                }
            }
        }
    }
}