package com.litecodez.lawresources

import com.google.gson.annotations.SerializedName

data class Wording(
    @SerializedName("title") @JvmField var title:String = "",
    @SerializedName("body") @JvmField var body:String = ""
)

data class WordingBook(
    @SerializedName("contents") @JvmField var contents:Map<String, Int> = mapOf(),
    @SerializedName("wording_book") @JvmField var wordingBook:List<Wording> = listOf()
)

data class Sample(
    @SerializedName("url") @JvmField var url:String = "",
    @SerializedName("document") @JvmField var document:String = "",
    @SerializedName("title") @JvmField var title:String = "",
    @SerializedName("type") @JvmField var type:String = "",
    @SerializedName("Description") @JvmField var description:String = "",
)

data class Samples(
    @SerializedName("samples") @JvmField var samples:List<Sample> = listOf()
)

data class Policy(
    @SerializedName("title") @JvmField var title:String = "",
    @SerializedName("src") @JvmField var src:String = "",
    @SerializedName("contents") @JvmField var contents:Map<String, Int> = mapOf(),
    @SerializedName("sections") @JvmField var sections:List<Map<String, List<String>>> = listOf()
)

data class Fine(
    @SerializedName("code") @JvmField var code:String = "",
    @SerializedName("description") @JvmField var description:String = "",
    @SerializedName("fine") @JvmField var fine:String = "",
    @SerializedName("points") @JvmField var points:String = "",
    @SerializedName("section") @JvmField var section:String = ""
)

data class LawSource(
    @SerializedName("numberOfSubsections") @JvmField var numberOfSubsections:Int = 0,
    @SerializedName("subsection") @JvmField var subsection:Map<String, String> = mapOf()
)

data class LawSources(
    @SerializedName("sections") @JvmField var sections:Map<String,LawSource> = mapOf(),
    @SerializedName("contents") @JvmField var contents:List<Map<String, String>> = listOf()
)

data class Fines(
    @SerializedName("fines") @JvmField var fines:List<Fine> = listOf(),
)

data class Credit(
    @SerializedName("name") @JvmField var name:String = "",
    @SerializedName("comments") @JvmField var comments:String = ""
)

data class Credits(
    @SerializedName("credits") @JvmField var credits:List<Credit> = listOf()
)

data class Message(
    @SerializedName("priority") @JvmField var priority:String = "",
    @SerializedName("content") @JvmField var content:String = ""
)

data class Messages(
    @SerializedName("message") @JvmField var messages:List<Message> = listOf()
)

data class Apis(
    @SerializedName("data") @JvmField var apis:Map<String, String> = mapOf()
)

data class PolicyList(
    @SerializedName("policies") @JvmField var policies:List<String>  = listOf()
)
data class LawList(
    @SerializedName("laws") @JvmField var laws:List<String>  = listOf()
)

data class Contacts(
    @SerializedName("developer_whatsapp") @JvmField var whatsapp:String = "",
    @SerializedName("developer_email") @JvmField var email:String = "",
    @SerializedName("developer_telegram") @JvmField var telegram:String = ""
)

data class Values(
    @SerializedName("enable_ad") @JvmField var enableAd:Boolean = false,
    @SerializedName("isOnline") @JvmField var isOnline:Boolean = true,
    @SerializedName("version") @JvmField var version:Double = appVersion
)

data class Video(
    @SerializedName("id") @JvmField var id:Int = -1,
    @SerializedName("url") @JvmField var url:String = "",
    @SerializedName("title") @JvmField var title:String = "",
    @SerializedName("description") @JvmField var description:String = "",
    @SerializedName("duration") @JvmField var duration:String = "",
    @SerializedName("thumbnail") @JvmField var thumbnail:String = "",
    @SerializedName("author") @JvmField var author:String = ""
)

data class Videos(
    @SerializedName("videos") @JvmField var videos:List<Video> = listOf()
)











