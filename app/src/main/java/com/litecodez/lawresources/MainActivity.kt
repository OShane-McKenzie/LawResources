package com.litecodez.lawresources

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.content.ContextCompat
import com.litecodez.lawresources.ui.theme.LawResourcesTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


val appNavigator = AppNavigator(initialScreen = splash)
val OffWhite = Color(0xFFF5F5F5)
val black =  Color(0xFF000000)

val contentProvider = ContentProvider()
val contentRepository = ContentRepository()
val setAd = mutableStateOf(contentProvider.values.value.enableAd)
val firstLoad = mutableStateOf(true)
class MainActivity : ComponentActivity() {

    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        contentProvider.contentReady.value = contentRepository.contentReady.value

        requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            contentProvider.writePermission.value = isGranted
        }

        //println("H4sIAAAAAAAA/wtPzUnOz01VKMlX8EksVwhKLc4vLUpOLVZwS01NSUpMzlYIyC8qSczhetQwRZdIwAUAK/WrSVUAAAA=".decompressString())

        if (ContextCompat.checkSelfPermission(
                applicationContext,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ){
            requestPermissionLauncher.launch(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)

            requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
                contentProvider.writePermission.value = isGranted
            }
        }

        setContent {
            var online by rememberSaveable {
                mutableStateOf(isConnected(applicationContext))
            }
            LawResourcesTheme {

                if(online){
                    if(contentRepository.contentReady.value!=null && firstLoad.value){
                        if(contentRepository.contentReady.value!!){
                            //getToast(applicationContext,contentRepository.contentReady.value.toString()+" 1")
                            Views()
                        }
                    }else if(!firstLoad.value){
                        //getToast(applicationContext,contentRepository.contentReady.value.toString()+" 2")
                        contentRepository.contentReady.value = null
                        contentRepository.startInit {
                            firstLoad.value = true
                        }
                    }else if(contentRepository.contentReady.value!=null && !firstLoad.value){
                        //getToast(applicationContext,contentRepository.contentReady.value.toString()+" 3")
                        firstLoad.value = true
                    }
                }else{
                    //getToast(applicationContext,contentRepository.contentReady.value.toString()+" 4")
                    firstLoad.value = false
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(if (isSystemInDarkTheme()) Color.Black else Color.White),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center

                    ){
                        Text(text = "You are not connected to the internet.")
                    }
                }

            }
            LaunchedEffect(Unit){
                withContext(Dispatchers.IO){
                    while (!online){
                        online = isConnected(applicationContext)
                    }
                }
            }
        }
    }
}

