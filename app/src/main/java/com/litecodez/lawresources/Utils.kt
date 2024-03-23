package com.litecodez.lawresources

import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import java.util.Random

fun generateUniqueUserID(): String {
    val random = Random()
    val chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    val idLength = 6

    val randomID: String

    // Generate a random ID of the specified length
    val idBuilder = StringBuilder()
    for (i in 0 until idLength) {
        val randomChar = chars[random.nextInt(chars.length)]
        idBuilder.append(randomChar)
    }
    randomID = "Anonymous-$idBuilder"

    return randomID
}

val bw:List<String> = listOf("murder", "killer", "kill", "bombo", "claat", "bomboclaat", "fuck",
    "fuckoff", "pussyhole", "pussy claat", "pusssyclaat", "battyman", "rass hole", "whore",
    "faggot", "cockey", "battyhole", "batty hole", "batty man", "batty-hole", "batty-man",
    "fuck-off", "rass claat", "bloodclaat", "bombo hole", "blood claat", "shit", "nigger",
    "nigga","2g1c", "2 girls 1 cup", "acrotomophilia", "alabama hot pocket", "alaskan pipeline",
    "anal", "anilingus", "anus", "apeshit", "arsehole", "asshole", "assmunch",
    "auto erotic", "autoerotic", "babeland", "baby batter", "baby juice", "ball gag",
    "ball gravy", "ball kicking", "ball licking", "ball sack", "ball sucking", "bangbros",
    "bangbus", "bareback", "barely legal", "barenaked", "bastard", "bastardo", "bastinado",
    "bdsm", "beaner", "beaners", "beaver cleaver", "beaver lips", "beastiality", "bestiality",
    "big black", "big breasts", "big knockers", "big tits", "bimbos", "birdlock", "bitch",
    "bitches", "black cock", "blonde action", "blonde on blonde action", "blowjob", "blow job",
    "blow your load", "blue waffle", "blumpkin", "bollocks", "bondage", "boner", "boob",
    "boobs", "booty call", "brown showers", "brunette action", "bukkake", "bulldyke",
    "bullet vibe", "bullshit", "bung hole", "bunghole", "busty", "butt", "buttcheeks",
    "butthole", "camel toe", "camgirl", "camslut", "camwhore", "carpet muncher",
    "carpetmuncher", "chocolate rosebuds", "cialis", "circlejerk", "cleveland steamer",
    "clit", "clitoris", "clover clamps", "clusterfuck", "cock", "cocks", "coprolagnia",
    "coprophilia", "cornhole", "coon", "coons", "creampie", "cum", "cumming", "cumshot",
    "cumshots", "cunnilingus", "cunt", "darkie", "date rape", "daterape", "deep throat",
    "deepthroat", "dendrophilia", "dick", "dildo", "dingleberry", "dingleberries",
    "dirty pillows", "dirty sanchez", "doggie style", "doggiestyle", "doggy style",
    "doggystyle", "dog style", "dolcett", "domination", "dominatrix", "dommes",
    "donkey punch", "double dong", "double penetration", "dp action", "dry hump", "dvda",
    "eat my ass", "ecchi", "ejaculation", "erotic", "erotism", "escort", "eunuch", "fag",
    "faggot", "fecal", "felch", "fellatio", "feltch", "female squirting", "femdom",
    "figging", "fingerbang", "fingering", "fisting", "foot fetish", "footjob", "frotting",
    "fuck", "fuck buttons", "fuckin", "fucking", "fucktards", "fudge packer", "fudgepacker",
    "futanari", "gangbang", "gang bang", "gay sex", "genitals", "giant cock", "girl on",
    "girl on top", "girls gone wild", "goatcx", "goatse", "god damn", "gokkun", "golden shower",
    "goodpoop", "goo girl", "goregasm", "grope", "group sex", "g-spot", "guro", "hand job",
    "handjob", "hard core", "hardcore", "hentai", "homoerotic", "honkey", "hooker", "horny",
    "hot carl", "hot chick", "how to kill", "how to murder", "huge fat", "humping", "incest",
    "intercourse", "jack off", "jail bait", "jailbait", "jelly donut", "jerk off", "jigaboo",
    "jiggaboo", "jiggerboo", "jizz", "juggs", "kike", "kinbaku", "kinkster", "kinky",
    "knobbing", "leather restraint", "leather straight jacket", "lemon party", "livesex",
    "lolita", "lovemaking", "make me come", "male squirting", "masturbate", "masturbating",
    "masturbation", "menage a trois", "milf", "missionary position", "mong", "motherfucker",
    "mound of venus", "mr hands", "muff diver", "muffdiving", "nambla", "nawashi", "negro",
    "neonazi", "nigga", "nigger", "nig nog", "nimphomania", "nipple", "nipples", "nsfw",
    "nsfw images", "nude", "nudity", "nutten", "nympho", "nymphomania", "octopussy", "omorashi",
    "one cup two girls", "one guy one jar", "orgasm", "orgy", "paedophile", "paki", "panties",
    "panty", "pedobear", "pedophile", "pegging", "penis", "phone sex", "piece of shit", "pikey",
    "pissing", "piss pig", "pisspig", "playboy", "pleasure chest", "pole smoker", "ponyplay",
    "poof", "poon", "poontang", "punany", "poop chute", "poopchute", "porn", "porno",
    "pornography", "prince albert piercing", "pthc", "pubes", "pussy", "queaf", "queef",
    "quim", "raghead", "raging boner", "rape", "raping", "rapist", "rectum", "reverse cowgirl",
    "rimjob", "rimming", "rosy palm", "rosy palm and her 5 sisters", "rusty trombone", "sadism",
    "santorum", "scat", "schlong", "scissoring", "semen", "sexcam", "sexo", "sexy", "sexual",
    "sexually", "sexuality", "shaved beaver", "shaved pussy", "shemale", "shibari", "shit",
    "shitblimp", "shitty", "shota", "shrimping", "skeet", "slanteye", "slut", "s&m", "smut",
    "snatch", "snowballing", "sodomize", "sodomy", "spastic", "spic", "splooge",
    "splooge moose", "spooge", "spread legs", "spunk", "strap on", "strapon", "strappado",
    "strip club", "style doggy", "suck", "sucks", "suicide girls", "sultry women", "swastika",
    "swinger", "tainted love", "taste my", "tea bagging", "threesome", "throating",
    "thumbzilla", "tied up", "tight white", "tit", "tits", "titties", "titty", "tongue in a",
    "topless", "tosser", "towelhead", "tranny", "tribadism", "tub girl", "tubgirl", "tushy",
    "twat", "twink", "twinkie", "two girls one cup", "undressing", "upskirt", "urethra play",
    "urophilia", "vagina", "venus mound", "viagra", "vibrator", "violet wand", "vorarephilia",
    "voyeur", "voyeurweb", "voyuer", "vulva", "wank", "wetback", "wet dream", "white power",
    "whore", "worldsex", "wrapping men", "wrinkled starfish", "xx", "xxx", "yaoi",
    "yellow showers", "yiffy", "zoophilia", "🖕" )

fun containsBadWord(phrase:String):Boolean{
    var bad = false
    bw.forEach outer@{
        if(phrase.trim().lowercase(Locale.ROOT).contains(it)){
            bad = true
            return@outer
        }
    }
    return bad
}

fun getCurrentDate(): String {
    val currentDateTime = LocalDateTime.now()
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    return currentDateTime.format(formatter)
}

fun getCurrentTime(): String {
    val currentTime = LocalTime.now()
    val formatter = DateTimeFormatter.ofPattern("HH:mm")
    return currentTime.format(formatter)
}

fun String.toLowerAndContains():String{
    return this.lowercase(Locale.ROOT)
}