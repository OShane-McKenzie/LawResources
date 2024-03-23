package com.litecodez.lawresources

enum class EncodedStrings(val getString: String) {
    MAIN_API("&~-&~_-.&-_.&@&~_~&~_-." + "&-_-.&@@@@@@@&~-~.&_" +
            "--~&@@@@@@@@@@&-_~_&~_~&@@@&~_-&~_-&--~&-_-&~_--.&-~~&--~&~.&~_-_.&-_&~--.&~." +
            "&@@@@@@&@@@@@@@&~-&~.&~_--.&~_--&~--.&@@@&~-~&@&~-&~_-.&-_~_.&--&~-~.&-_-&~-~&-_~_&~-~." +
            "&@@@&_-~&~_-&~-__&@@&@@@@@@@@@@&_--~&~--.&_-~.&~_-_.&-_~_&~-__&_-~.&_~~.&_~~&~--.&_-~." +
            "&-_-.&-_~_&--~&-_~.&-~.&@@@@&_-~.&@@@&-_.&~_-&~-__&-_~.&-~_.&~_--&~-&~_-_&~_-_.&-_~_" +
            "&--~&-_~&~__~&__~~"),

    FINES_SOURCE("&~-&~_-.&-_" +
            ".&@&~_~&~_-.&-_-.&@@@@@@@&~-~" +
            ".&_--~&@@@@@@@@@@&~-_-&_--~.&@@@&_--~.&~_--&~--.&@@@&@@@@@@@@@@&@@@&~-~" +
            ".&-_-&-~-&-~~&~-~.&@@@@&~-__&~&~-~.&~.&~_-_.&-_~_&--~&~_-_&-_" +
            ".&~-~&--~&~_-_&~-_-.&-_~_&~-__&_-~.&--~.&~~-&--~&@@@&-~" +
            ".&_--&~_~&_--~&@@@@@@@@@&_--~&-_-.&~-__.&--.&~--&~-~.&~--&~-" +
            ".&_-~&~-~.&@&@@@@@@@@@@&-_-&~--.&-_-&-_~_.&~_--&_--~.&@@@&~_--" +
            ".&-~~&~-_-.&@@@&@@@@@@@@@@&_--&~--.&_-~.&-_-.&-~~&_--~.&~.&@@@@@@&_--&~-~" +
            ".&~_--.&~--.&-~-&--~&-_-&-_~_.&~--&~-~.&-~~.&--.&~&-_-.&_~~&~_-_.&-_~.&~-&~" +
            ".&@@@@@@&~_-&--~&~_~.&@@@@@@&~&~--.&-_~.&_--~.&__~~")
}

enum class PlainStrings(val getString: String){
    CENTERED_PLACE_HOLDER("                       Search"),
    IP_ADDRESS_CHECK("8.8.8.8")
}
const val appName:String = "Law Resources"
const val appVersion = 2.9
const val appLink = "https://play.google.com/store/apps/details?id=com.litecodez.lawresources"
const val feedBackID:String = "1ENqm2q43-qLSgRBtk81GbaAXE2R8xLFLATBM681oiB4"
val id = generateUniqueUserID()
val feedbackUser = id

//screens
const val wording:String = "wording"
const val howTo:String = "howTo"
const val law:String = "law"
const val rta:String = "rta"
const val samples:String = "samples"
const val more:String = "more"
const val splash:String = "splash"
const val loading:String = "loading"
const val offline:String = "offline"
const val update:String = "update"
const val videos:String = "videos"
const val feedback:String = "feedback"
