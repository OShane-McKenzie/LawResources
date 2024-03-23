package com.litecodez.lawresources

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@Composable
fun Splash(setAlpha: Float,isDataReady:Boolean = true,errorText:String = "",task:()->Unit={}){
    val scope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(if (isSystemInDarkTheme()) Color.Black else Color.White)
            .alpha(alpha = setAlpha),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center

        ){
        Text(text = contentProvider.connectionStatus.value,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colors.primary,
            fontStyle = FontStyle.Italic,
            fontSize = 10.sp
        )
        Spacer(modifier = Modifier.height(4.dp))
        Image(
            painter = painterResource(id = R.drawable.law_rec),
            contentDescription ="Logo",
            Modifier
                .size(250.dp)
                .clip(RoundedCornerShape(100)),
            contentScale = ContentScale.FillBounds
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = "Law Resources\nBy: O'Shane McKenzie", textAlign = TextAlign.Center, color = MaterialTheme.colors.primary)
    }
    LaunchedEffect(Unit){
        scope.launch {
            withContext(Dispatchers.IO){
                delay(2000)
                task()
            }
        }
    }
}

@Composable
fun SplashAnimation(){
    var startAnimation by remember{ mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    var isDataReady by remember { mutableStateOf(false) }
    val context = LocalContext.current

    //val netDevicesActive = isConnected(LocalContext.current)

    var connected by rememberSaveable {
        mutableStateOf(false)
    }


    @Composable
    fun startConnectionCheck(){
        if(connected) {

        LaunchedEffect(contentRepository.contentReady.value){
            ContentLoader.loadContent(context = context){
                isDataReady = true
                contentProvider.connectionStatus.value = ""
                appNavigator.setViewState(loading, updateHistory = false, execTask = true){
                    connected = false
                }
            }
        }

        }else{
            isDataReady = false
            contentProvider.connectionStatus.value = "You are not Connected to the internet"
        }
    }
    if(connected){
        startConnectionCheck()
    }
    startAnimation = true
    Splash(1f){
        scope.launch {

            withContext(Dispatchers.IO){
                while (!connected) {
                    val netStatus = isInternetConnected(context)
                    withContext(Dispatchers.Main) {
                        connected = netStatus
                    }
                }
            }
        }
    }
}