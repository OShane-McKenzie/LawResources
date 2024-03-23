package com.litecodez.lawresources

import android.app.Activity
import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_USER
import androidx.annotation.OptIn
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Divider
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.util.UnstableApi
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.ui.layout.ContentScale
import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_USER_LANDSCAPE
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.filled.Send
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Locale

class Components {
    @Composable
    fun Wording(){
        var wordingSearch by rememberSaveable { mutableStateOf("") }
//        val filteredData = wordingData.filter { wording ->
//            listOf(wording.title, wording.body).any { it?.contains(wordingSearch,
//                ignoreCase = true) == true }
//        }
        val filteredData = contentProvider.wordingBook.value.wordingBook.filter {
            it.title.lowercase(Locale.ROOT).contains(wordingSearch.lowercase(Locale.ROOT), ignoreCase = true)||
            it.body.lowercase(Locale.ROOT).contains(wordingSearch, ignoreCase = true)
        }.sortedBy { it.title }


        OutlinedTextField(

            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color(0xFFA05A2C),
                unfocusedIndicatorColor = Color(0xFFE1BC9A),
                backgroundColor = Color(0xFFF5F5F5),
                textColor = Color.Black
            ),

            value = wordingSearch,

            onValueChange = { newText ->

                wordingSearch = newText

            },
            placeholder = { Text(text = PlainStrings.CENTERED_PLACE_HOLDER.getString, textAlign = TextAlign.Center, color = Color.Black) },
            textStyle = TextStyle(
                textAlign = TextAlign.Center
            ),
            modifier = Modifier.height(50.dp),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            )
        )
        Spacer(modifier = Modifier.height(10.dp))

        LazyColumn(
            contentPadding = PaddingValues(8.dp)
        ) {

            items(items = filteredData) { wording ->
                ExpandableCard(title = wording.title, description = wording.body)
                Spacer(modifier = Modifier.height(4.dp))
            }

        }
    }

    @Composable
    fun NavCircles(){
        val scrollState = rememberScrollState()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(scrollState),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,

            ) {

            CircleShapeWithText(circleText = "Wording",  destination= wording, onClicked = { appNavigator.setViewState(
                    wording
                )
            })
            Spacer(modifier = Modifier.width(4.dp))
            CircleShapeWithText(
                circleText = "How to?",
                destination= howTo,
                onClicked = {
                    appNavigator.setViewState(howTo)
                }
            )
            Spacer(modifier = Modifier.width(4.dp))
            CircleShapeWithText(circleText = "Samples", destination=samples, onClicked = { appNavigator.setViewState(samples)  })
            Spacer(modifier = Modifier.width(4.dp))
            CircleShapeWithText(circleText = "Laws", destination=law, onClicked = { appNavigator.setViewState(law) })
            Spacer(modifier = Modifier.width(4.dp))
            CircleShapeWithText(circleText = "RTA Fines",  destination=rta, onClicked = { appNavigator.setViewState(rta) })
            Spacer(modifier = Modifier.width(4.dp))
            CircleShapeWithText(circleText = "Videos",  destination= videos, onClicked = { appNavigator.setViewState(
                videos) })
            Spacer(modifier = Modifier.width(4.dp))
            CircleShapeWithText(circleText = "Feedback",  destination= feedback, onClicked = { appNavigator.setViewState(
                feedback) })
            Spacer(modifier = Modifier.width(4.dp))
            CircleShapeWithText(circleText = "More",  destination=more, onClicked = { appNavigator.setViewState(more)  })
        }
        LaunchedEffect(Unit) {
            // Keep running the loop as long as the effect is active.
            while (isActive) {
                delay(4000)
                // Animate the horizontal scroll to the maximum scroll value.
                scrollState.animateScrollTo(
                    value = scrollState.maxValue,
                    animationSpec = tween(durationMillis = 1000, easing = LinearOutSlowInEasing)
                )
                // Wait for 4 seconds.
                delay(4000)

                // Animate the horizontal scroll back to the initial position.
                scrollState.animateScrollTo(
                    value = 0,
                    animationSpec = tween(durationMillis = 1000, easing = LinearOutSlowInEasing)
                )
            }
        }
    }

    @Composable
    fun DisplayMessages(){
        var displayMessageColor by remember{ mutableStateOf(Color.Black) }

        Row(modifier = Modifier.wrapContentSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ){
            if(!contentProvider.values.value.isOnline){
                displayMessageColor = Color.Red
            }
            MessageDisplay(text = if(contentProvider.values.value.isOnline){ contentProvider.displayMessage.value }else{"The app will be online as soon as possible."}, textColor = displayMessageColor)
            Spacer(modifier = Modifier.width(2.dp))
            ShareButton()
        }
        if(contentProvider.messages.value.messages.isNotEmpty() && contentProvider.values.value.isOnline) {
            LaunchedEffect(Unit) {
                // Calculate the maximum message index.
                val maxMessage = contentProvider.messages.value.messages.size - 1
                // Initialize a counter variable.
                var counter = 0

                // Keep running the loop as long as the effect is active.
                while (isActive) {
                    // If the counter is within the range of the message list.
                    if (counter <= maxMessage) {
                        // Determine the message priority and set the displayMessage and displayMessageColor accordingly.
                        when (contentProvider.messages.value.messages[counter].priority) {
                            "high" -> {
                                contentProvider.displayMessage.value =
                                    contentProvider.messages.value.messages[counter].content
                                displayMessageColor = Color.Red
                            }

                            "medium" -> {
                                contentProvider.displayMessage.value =
                                    contentProvider.messages.value.messages[counter].content
                                displayMessageColor = Color.Magenta
                            }

                            "low" -> {
                                contentProvider.displayMessage.value =
                                    contentProvider.messages.value.messages[counter].content
                                displayMessageColor = Color.Black
                            }
                        }
                    } else {
                        // Reset the counter if it goes beyond the maximum message index.
                        counter = -1
                    }
                    // Increment the counter.
                    counter += 1
                    // Wait for 4 seconds.
                    delay(4000)
                }
            }
        }
    }

    @Composable
    fun Samples(){
        val context = LocalContext.current
        var samplesSearch by rememberSaveable { mutableStateOf("") }

        val filteredData = contentProvider.samples.value.samples
        OutlinedTextField(
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color(0xFFA05A2C),
                unfocusedIndicatorColor = Color(0xFFE1BC9A),
                backgroundColor = Color(0xFFF5F5F5),
                textColor = Color.Black
            ),
            value = samplesSearch,
            onValueChange = { newText ->
                samplesSearch = newText
            },
            placeholder = { Text(text = PlainStrings.CENTERED_PLACE_HOLDER.getString,
                textAlign = TextAlign.Center, color = Color.Black) },
            textStyle = TextStyle(
                textAlign = TextAlign.Center
            ),
            modifier = Modifier.height(50.dp),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text
            )
        )
        Spacer(modifier = Modifier.height(10.dp))

        LazyColumn(
            contentPadding = PaddingValues(8.dp)
        ) {
            items(items = filteredData) { samples ->
                if(samplesSearch=="" || samples.title.contains(samplesSearch) || samples.description.contains(samplesSearch)) {
                    SamplesComponent(
                        title = samples.title,
                        description = samples.description,
                        imageUrl = samples.url,
                        documentUrl = samples.document,
                        type = samples.type,
                        context = context
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Divider(thickness = 2.dp)
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }

    @Composable
    fun PolicyComponent() {

        var showContent by rememberSaveable {
            mutableStateOf(false)
        }
        var policyTitles by rememberSaveable {
            mutableStateOf(listOf<String>())
        }

        var policySource by rememberSaveable {
            mutableStateOf(listOf<String>())
        }
        var selectedItem by rememberSaveable { mutableStateOf("") }

        var loaded by rememberSaveable {
            mutableStateOf(false)
        }

        LaunchedEffect(Unit){
            if(!loaded) {

                val localPolicyTitles = mutableListOf<String>()
                val localPolicySources = mutableListOf<String>()
                contentProvider.policies.forEachIndexed {index, policy->
                    localPolicyTitles.add(policy.title)
                    contentProvider.policyMap[policy.title] = policy
                }
                policyTitles = localPolicyTitles.toList()
                contentProvider.policies.forEach {
                    localPolicySources.add(it.src)
                }
                policySource = localPolicySources.toList()

                loaded = true
            }

        }

        Text(
            text = contentProvider.policyMap.getOrDefault(selectedItem,Policy()).title,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Source: ${contentProvider.policyMap.getOrDefault(selectedItem,Policy()).src}",
            fontStyle = FontStyle.Italic,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontSize = 9.sp
        )

        Spacer(modifier = Modifier.height(1.dp))
        SpinnerDropdown ({
            clickedItem -> selectedItem = clickedItem; showContent = !showContent },
            policyTitles
        )

        if(showContent) {
            PolicyDataItems(contentProvider.policyMap.getOrDefault(selectedItem, Policy()))
        }else{
            PolicyDataItems(contentProvider.policyMap.getOrDefault(selectedItem, Policy()))
        }
    }

    @Composable
    fun Laws(){

        var showContent by rememberSaveable {
            mutableStateOf(false)
        }
        var contentLoaded by rememberSaveable {
            mutableStateOf(false)
        }
        var selectedItem by rememberSaveable { mutableStateOf("") }

        LaunchedEffect(Unit){
            contentProvider.laws.forEach {
                contentProvider.lawMap[it.contents[0]["title"]!!] = it
            }
            contentLoaded = true
        }

        if(contentLoaded) {
            SpinnerDropdown(
                { clickedItem -> selectedItem = clickedItem; showContent = !showContent },
                contentProvider.lawMap.toMap().keys.toList(),
                placeholderText = "Choose a Law"
            )
        }

        if(showContent && selectedItem.isNotEmpty()) {
            LawItems(law = contentProvider.lawMap.getOrDefault(selectedItem, LawSources()))
        }else if(!showContent && selectedItem.isNotEmpty()){
            LawItems(law = contentProvider.lawMap.getOrDefault(selectedItem, LawSources()))
        }
    }

    @Composable
    fun More(){

        val whatsapp = contentProvider.contacts.value.whatsapp
        val email  = contentProvider.contacts.value.email
        val telegram = contentProvider.contacts.value.telegram
        val acknowledgements = contentProvider.credits.value.credits

        @Composable
        fun ShowInfo(){
            LazyColumn(
                contentPadding = PaddingValues(8.dp)
            ) {
                item {
                    // Display the header for the developer's contact section.
                    Text(text = "Developer Contact:", fontWeight = FontWeight.Medium)
                    Spacer(modifier = Modifier.height(4.dp))
                }
                // Display the developer's WhatsApp number with the icon.
                item {
                    CircleImageWithTextField(
                        imageUrl = R.drawable.whatsapp_icon,
                        selectableText = whatsapp
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                }

                // Add a divider to separate the contact items.
                item {
                    Divider(thickness = 2.dp)
                    Spacer(modifier = Modifier.height(2.dp))
                }

                // Display the developer's email address with the icon.
                item {
                    CircleImageWithTextField(
                        imageUrl = R.drawable.email_icon,
                        selectableText = email
                    )
                }
                // Add a divider to separate the contact items.
                item {
                    Divider(thickness = 2.dp)
                    Spacer(modifier = Modifier.height(2.dp))
                }

                // Display the developer's email address with the icon.
                item {
                    CircleImageWithTextField(
                        imageUrl = R.drawable.telegram_icon,
                        selectableText = telegram
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(text = "Acknowledgements:", fontWeight = FontWeight.Medium)
                    Spacer(modifier = Modifier.height(4.dp))
                }
                items(items = acknowledgements) { credits ->
                    CreditsCard(title = credits.name, comments = credits.comments)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
        ShowInfo()

    }

    @Composable
    fun Rta(){
        var finesSearch by rememberSaveable { mutableStateOf("") }
        val source = ApiEncode().deBase(ApiEncode().decodeApi(EncodedStrings.FINES_SOURCE.getString))
        val scope = rememberCoroutineScope()
        //val (filteredData, setFilteredData) = remember { mutableStateOf(finesData) }
        val filteredData = contentProvider.fines.value.fines
        OutlinedTextField(
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color(0xFFA05A2C),
                unfocusedIndicatorColor = Color(0xFFE1BC9A),
                backgroundColor = Color(0xFFF5F5F5),
                textColor = Color.Black
            ),
            value = finesSearch,
            onValueChange = { newText -> finesSearch = newText
            },
            placeholder = { Text(text = PlainStrings.CENTERED_PLACE_HOLDER.getString,
                textAlign = TextAlign.Center, color = Color.Black) },
            textStyle = TextStyle(
                textAlign = TextAlign.Center
            ),
            modifier = Modifier.height(50.dp),
        )
        Spacer(modifier = Modifier.height(2.dp))

        Text(text = "Source: $source", textAlign = TextAlign.Center, fontSize = 8.sp)
        Spacer(modifier = Modifier.height(10.dp))

        LazyColumn(
            contentPadding = PaddingValues(8.dp)
        ) {
            items(items = filteredData) { fines ->
                if(finesSearch==""||
                    fines.description.contains(finesSearch, ignoreCase = true)||
                    fines.fine.contains(finesSearch, ignoreCase = true)||
                    fines.code.contains(finesSearch, ignoreCase = true)||
                    fines.section.contains(finesSearch, ignoreCase = true)||
                    fines.points.contains(finesSearch, ignoreCase = true)
                ) {
                    ExpandableCard(
                        title = fines.description,
                        description = "Code: ${fines.code}\nFine: " + "${fines.fine}\nPoints: ${fines.points}\nSection: ${fines.section}"
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }
        }
    }

    @OptIn(UnstableApi::class) @Composable
    fun VideoDisplay(){
        val context = LocalContext.current
        val activity = remember {
            context as Activity
        }
        Column(modifier = Modifier
            .fillMaxSize()
            .background(
                if (activity.requestedOrientation == SCREEN_ORIENTATION_USER_LANDSCAPE) {
                    Color(0xFF070707).copy(alpha = 0.8f)
                } else {
                    Color(0xFF070707).copy(alpha = 0.0f)
                }
            ),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
            ) {
            Box(modifier = Modifier.wrapContentSize()){
                Row(
                    modifier = Modifier
                        .fillMaxWidth(
                            if (activity.requestedOrientation ==
                                SCREEN_ORIENTATION_USER_LANDSCAPE
                            ) {
                                1.0f
                            } else {
                                0.96f
                            }
                        )
                        .fillMaxHeight(
                            if (activity.requestedOrientation ==
                                SCREEN_ORIENTATION_USER_LANDSCAPE
                            ) {
                                1.0f
                            } else {
                                0.35f
                            }
                        )
                        .padding(5.dp)
                        .clip(RoundedCornerShape(8)),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ){
                    AndroidView(
                        modifier = Modifier.fillMaxSize(),
                        factory = {
                            VideoViewModel.playerViewBuilder(it)
                        }
                    )
                }
                IconButton(
                    onClick = {
                        contentProvider.startVideo.value = false
                        VideoViewModel.releasePlayer()
                        activity.requestedOrientation = SCREEN_ORIENTATION_USER
                  },
                    modifier = Modifier.align(
                        Alignment.TopEnd
                    )) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "", tint = Color.Magenta
                    )
                }
            }
        }

    }

    @OptIn(UnstableApi::class) @Composable
    fun VideoItem(video:Video){
        val context = LocalContext.current
        val painter = rememberAsyncImagePainter(
            ImageRequest.Builder(LocalContext.current).data(data = video.thumbnail)
                .apply<ImageRequest.Builder>(block = fun ImageRequest.Builder.() {
                    error(R.drawable.placeholder)
                    placeholder(R.drawable.placeholder)
                }).build()
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .border(
                    shape = RoundedCornerShape(8),
                    width = 1.dp,
                    color = Color(0xFFA05A2C)
                )
                .padding(2.dp)
                .clickable {
                    VideoViewModel.releasePlayer()
                    VideoViewModel.initializePlayer(context)
                    contentProvider.currentVideo.value = video
                    contentProvider.startVideo.value = !contentProvider.startVideo.value
                    if (contentProvider.startVideo.value) {
                        VideoViewModel.playVideo(contentProvider.currentVideo.value.url)
                    }
                },
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(3.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top
            ) {
                Image(
                    painter = painter,
                    contentDescription = video.title,
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12)),
                    contentScale = ContentScale.FillBounds
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    "Title: ${video.title}",
                    maxLines = 10,
                    fontWeight = FontWeight.ExtraBold,
                )
                Divider(thickness = 2.dp)
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    "Description: \n${video.description}",
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 10,
                    fontWeight = FontWeight.Normal,
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    "Duration: \n${video.duration}",
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Normal,
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    "Author: \n${video.author}",
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 10,
                    fontWeight = FontWeight.Normal,
                )
                Spacer(modifier = Modifier.height(5.dp))
            }
        }
    }

    @Composable
    fun VideoScreen(){
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(items = contentProvider.videos.value.videos.sortedByDescending { it.id }) {
                components.VideoItem(video = it)
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }

    @Composable
    fun FeedbackScreen(){
        var feedbackText by rememberSaveable {
            mutableStateOf("")
        }
        val maxText = 50
        val scrollState = rememberScrollState()
        val context = LocalContext.current
        val focusManager = LocalFocusManager.current
        val scope = rememberCoroutineScope()
        LaunchedEffect(Unit){
            withContext(Dispatchers.IO){
                while(isActive){
                    delay(10000)
                    val updatedFeedback = Feedback().readDocument {  }
                    withContext(Dispatchers.Main){
                        contentProvider.feedbackStream.value = updatedFeedback
                    }
                }
            }
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(2.dp, color = Color(0xFFE1BC9A), shape = RoundedCornerShape(3))
                    .fillMaxHeight()
                    .weight(9f)
                    .padding(5.dp)
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text(
                    contentProvider.feedbackStream.value.trim().toDecompressedString(), modifier = Modifier
                        .fillMaxSize(),
                    maxLines = 3000
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .weight(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .focusRequester(FocusRequester.Default)
                        .padding(1.dp)
                        .fillMaxWidth(0.8f),
                    value = feedbackText, onValueChange =
                    {
                        if (it.length <= maxText) {
                            feedbackText = it
                        }
                    },
                    label = {Text(feedbackUser)},
                    singleLine = false,
                    maxLines = 200
                )
                Spacer(modifier = Modifier.width(5.dp))
                IconButton(onClick =
                {
                    if(!containsBadWord(feedbackText)){
                        val addToFeedbackStream = "${getCurrentDate()}@${getCurrentTime()} $feedbackUser:\n${feedbackText.replace("\n"," ")}\n______\n"
                        contentRepository.updateFeedback(context,addToFeedbackStream)
                        feedbackText = ""
                        focusManager.clearFocus()
                    }else{
                        getToast(context,"Please clean up your language", true)
                    }
                },
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(1.dp)
                        .weight(1f)) {
                    Icon(imageVector = Icons.Default.Send,
                        contentDescription = "Send feedback",
                        tint = Color(0xFFA05A2C),
                        modifier = Modifier
                            .size(50.dp)
                            .padding(1.dp)
                    )
                }
            }
        }

    }
}