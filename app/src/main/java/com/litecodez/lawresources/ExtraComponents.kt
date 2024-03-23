package com.litecodez.lawresources

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import androidx.compose.material.AlertDialog
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import kotlinx.coroutines.Delay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext


/**
 * A composable function that displays a circular image alongside selectable text.
 *
 * @param imageUrl The resource ID of the image to be displayed.
 * @param modifier The modifier to be applied to the composable, default is Modifier.
 * @param borderStroke The border stroke of the circular image, default is BorderStroke(2.dp, Color.Gray).
 * @param selectableText The selectable text displayed next to the image, default is an empty string.
 */
@Composable
fun CircleImageWithTextField(
    imageUrl: Int,
    modifier: Modifier = Modifier,
    borderStroke: BorderStroke = BorderStroke(2.dp, Color.Gray),
    selectableText: String = "",
) {
    Surface(
        modifier = modifier
            .background(Color.White)
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        border = borderStroke
    ) {
        Row(
            modifier = Modifier
                .background(Color.White)
                .padding(8.dp)
            ,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,

        ) {
            Image(
                painter = painterResource(imageUrl),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(50.dp))
            SelectionContainer {
                Text(
                    text = selectableText,
                    style = TextStyle(color = Color.Gray),
                    modifier = Modifier.padding(end = 8.dp)
                )
            }
        }
    }
}

fun getToast(context: Context, msg:String,long:Boolean = false){
    Toast.makeText(
        context,
        msg,
        if(long){
            Toast.LENGTH_LONG}else{
            Toast.LENGTH_SHORT}
    ).show()
}

/**
 * Function to download an image from a URL and save it to the device's storage.
 *
 * @param context The context used to access the download service.
 * @param documentUrl The URL of the image to be downloaded.
 * @param fileName The name of the file to be saved.
 */
fun downloadImage(context: Context, documentUrl: String, fileName: String) {
    try {
        val request = DownloadManager.Request(Uri.parse(documentUrl)).apply {
            setTitle(fileName)
            setDescription("Downloading document: $fileName")
            setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)
            setRequiresCharging(false)
            setAllowedOverMetered(true)
            setAllowedOverRoaming(true)
        }
        val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        downloadManager.enqueue(request)
    }catch (e: Exception){
        if(!contentProvider.writePermission.value) {
            getToast(context, "Storage permission not granted", long = true)
        }else{
            getToast(context, "An unknown error occurred", long = true)
        }
    }
}

/**
 * A composable function that displays an image and its title and description. Clicking the image opens a download dialog.
 *
 * @param title The title of the sample.
 * @param type The document type.
 * @param description The description of the sample.
 * @param imageUrl The URL of the image to be displayed.
 * @param documentUrl The URL of the document to be downloaded.
 * @param modifier The modifier to be applied to the composable, default is Modifier.
 * @param context The context used to access the download service.
 */
@Composable
fun SamplesComponent(
    title: String,
    type: String,
    description: String,
    imageUrl: String,
    documentUrl: String,
    modifier: Modifier = Modifier,
    context: Context
) {
    val scope = rememberCoroutineScope()
    val (showDownloadDialog, setShowDownloadDialog) = remember { mutableStateOf(false) }
    Column(modifier = modifier) {
        Text(text = title, style = MaterialTheme.typography.h6)
        Text(text = description, style = MaterialTheme.typography.body1)

        val painter = rememberAsyncImagePainter(
            ImageRequest.Builder(LocalContext.current).data(data = imageUrl)
                .apply<ImageRequest.Builder>(block = fun ImageRequest.Builder.() {
                    error(R.drawable.placeholder)
                    placeholder(R.drawable.placeholder)
                }).build()
        )
        Image(
            painter = painter,
            contentDescription = "Image loaded from URL",
            modifier = Modifier
                //.padding(top = 8.dp)
                .border(
                    border = BorderStroke(2.dp, Color.Gray),
                    shape = RoundedCornerShape(4.dp)
                )
                .aspectRatio(0.8f)
                .clickable(onClick = { setShowDownloadDialog(true) })
        )
        if (showDownloadDialog) {
            AlertDialog(
                onDismissRequest = { setShowDownloadDialog(false) },
                title = { Text("Download $title") },
                text = { Text("Do you want to download this document?") },
                confirmButton = {
                    TextButton(onClick = {
                        scope.launch { downloadImage(context, documentUrl, "$title$type") }
                        setShowDownloadDialog(false)
                    }) {
                        Text("Yes")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { setShowDownloadDialog(false) }) {
                        Text("No")
                    }
                }
            )
        }
    }
}

@Composable
fun CreditsCard(title: String, comments: String,modifier: Modifier = Modifier,
                borderStroke: BorderStroke = BorderStroke(2.dp, Color.Gray),
                titleColor:Color = Color.Black,commentsColor:Color = Color.Black
) {
    Surface(
        modifier = modifier
            .background(Color.White)
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        border = borderStroke
    ){
        Column(
            modifier = Modifier
                .background(Color.White)
                .padding(8.dp)
        ) {
            Text(
                text = title,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                ),
                color = titleColor
            )
            Divider(thickness = 2.dp)
            Text(text = comments, style = MaterialTheme.typography.body1, color = commentsColor)
        }
    }
}
@Composable
fun ShareButton(link:String = appLink) {
    var startIntentShare by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .height(62.dp)
            .wrapContentWidth()
            .border(width = 1.dp, color = Color.White, shape = RoundedCornerShape(6)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        IconButton(
            onClick = {
                startIntentShare = !startIntentShare
            }

        ) {
            Icon(imageVector = Icons.Default.Share, contentDescription = "Share")
        }
    }
    if(startIntentShare){
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, link)
            type = "text/plain"
        }

        // Start the sharing activity
        startActivity(LocalContext.current, Intent.createChooser(sendIntent, null),null)
        startIntentShare = !startIntentShare
    }
}

@Composable
fun Loading(){
    setAd.value = contentProvider.values.value.enableAd
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                if (appNavigator.getView() != splash) {
                    OffWhite
                } else {
                    Color(0xFFF5F5F5).copy(alpha = 0.0f)
                }
            )
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center

    ){
        CircularProgressIndicator()
        Spacer(modifier = Modifier.height(5.dp))
        Text("Loading")
        LaunchedEffect(Unit){
            withContext(Dispatchers.IO){
                delay(2000)
                contentProvider.isAppLoading.value = false
                appNavigator.setViewState(wording, updateHistory = false, clearHistory = true)
            }
        }
    }
}

@Composable
fun Offline(){
    setAd.value = contentProvider.values.value.enableAd
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                if (appNavigator.getView() != splash) {
                    OffWhite
                } else {
                    Color(0xFFF5F5F5).copy(alpha = 0.0f)
                }
            )
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center

    ){
        Text("App is currently offline....")
        //contentRepository.contentReady.value = false
    }
}
fun updateActionEvent(context:Context, link:String = appLink){
    contentProvider.startIntentShare.value = !contentProvider.startIntentShare.value
    if (contentProvider.startIntentShare.value && link != "") {
        val sendIntent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(link)
        }

        ContextCompat.startActivity(
            context,
            Intent.createChooser(sendIntent, null),
            null
        )

    }
    if(contentProvider.startIntentShare.value){
        contentProvider.startIntentShare.value = !contentProvider.startIntentShare.value
    }
}

@Composable
fun Update(){
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                if (appNavigator.getView() != splash) {
                    OffWhite
                } else {
                    Color(0xFFF5F5F5).copy(alpha = 0.0f)
                }
            )
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center

    ){
        Text("Your app is outdated!")
        Spacer(modifier = Modifier.height(6.dp))
        Button(onClick = { updateActionEvent(context = context) }) {
            Text(text = "Update now!")
        }
    }
}

@Composable
fun SpinnerDropdown(
    onClicked: (String) -> Unit,
    setItems: List<String>,
    placeholderText: String = "Select: Policy/S.O.P/Guide"
) {
    val selectedItemIndex = remember { mutableStateOf(-1) }
    val expanded = remember { mutableStateOf(false) }
    var newVal by remember { mutableStateOf("") }
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFA05A2C), contentColor = Color.White),
            onClick = { expanded.value = !expanded.value },
            modifier = Modifier.wrapContentWidth()
        ) {
            Text(
                text = if (selectedItemIndex.value == -1) placeholderText else setItems[selectedItemIndex.value]
            )
        }

        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false },
            modifier = Modifier.fillMaxWidth(0.9f)
        ) {
            setItems.forEachIndexed { index, item ->
                DropdownMenuItem(onClick = {
                    selectedItemIndex.value = index
                    newVal = setItems[selectedItemIndex.value]
                    expanded.value = false
                    onClicked(newVal)
                }) {
                    Text(text = item, modifier = Modifier.fillMaxWidth())
                    Divider(Modifier.height(2.dp))
                }
            }
        }
    }
}

@Composable
fun Animator(content: @Composable () -> Unit){
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit){
        visible = true
    }
    AnimatedVisibility(
        visible = visible,
        enter = slideInHorizontally(animationSpec = tween(durationMillis = 200)) { fullWidth ->
            // Offsets the content by 1/3 of its width to the left, and slide towards right
            // Overwrites the default animation with tween for this slide animation.
            if (contentProvider.slideInLeftRight.value){
                contentProvider.slideInLeftRight.value = false
                -fullWidth / 3
            }else{
                contentProvider.slideInLeftRight.value = true
                fullWidth / 3
            }

        } + fadeIn(
            // Overwrites the default animation with tween
            animationSpec = tween(durationMillis = 200)
        ),
        exit = slideOutHorizontally(animationSpec = spring(stiffness = Spring.StiffnessHigh)) {
            // Overwrites the ending position of the slide-out to 200 (pixels) to the right
            200
        } + fadeOut()
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            content()
        }

    }
}



@Composable
fun VideoContent(video:Video){

}