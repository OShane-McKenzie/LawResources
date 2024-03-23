package com.litecodez.lawresources

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

val components = Components()

@Composable
fun Views(){
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    val unitId = stringResource(R.string.ad_mob_banner_id)
    LaunchedEffect(true){
        withContext(Dispatchers.IO){
            while(true){
                if(!contentProvider.values.value.isOnline){
                    appNavigator.setViewState(offline, updateHistory = false)
                }else if(contentProvider.values.value.version > appVersion){
                    appNavigator.setViewState(update, updateHistory = false)
                }
            }
        }
    }
    LaunchedEffect(contentProvider.feedbackCredential.value){
        if(contentProvider.feedbackCredential.value.isNotEmpty() && !contentProvider.feedbackLoaded.value){
            try {
                withContext(Dispatchers.IO){
                    contentProvider.feedbackStream.value = Feedback().readDocument {
                        contentProvider.feedbackLoaded.value = true
                    }
                }
            }catch (e:Exception){
                getToast(context,"$e",true)
            }

        }
    }

    appNavigator.GetBackHandler(context = context, lifecycleOwner = lifecycleOwner)
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ){
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
            verticalArrangement = Arrangement.Top

        ) {
            if (appNavigator.getView() != splash) {
                Spacer(modifier = Modifier.height(2.dp))
                if (setAd.value) {
                    AndroidView(
                        factory = { context ->
                            AdView(context).apply {
                                setAdSize(AdSize.BANNER)
                                adUnitId = unitId
                                loadAd(AdRequest.Builder().build())
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                }
                components.NavCircles()
                Spacer(modifier = Modifier.height(2.dp))
                components.DisplayMessages()
                Spacer(modifier = Modifier.height(10.dp))
            }
            when (appNavigator.getView()) {
                splash -> {
                    Animator {
                        SplashAnimation()
                    }
                }

                wording -> {

                    Animator {
                        components.Wording()
                    }
                }

                howTo -> {
                    Animator {
                        components.PolicyComponent()
                    }
                }

                law -> {
                    Animator {
                        components.Laws()
                    }
                }

                rta -> {
                    Animator {
                        components.Rta()
                    }
                }

                samples -> {
                    Animator {
                        components.Samples()
                    }
                }

                videos -> {
                    Animator {
                        components.VideoScreen()
                    }
                }

                feedback -> {
                    Animator {
                        components.FeedbackScreen()
                    }
                }

                more -> {
                    Animator {
                        components.More()
                    }
                }

                loading -> {
                    Animator {
                        Loading()
                    }
                }

                offline -> {
                    Animator {
                        Offline()
                    }
                }
                update -> {
                    Animator {
                        Update()
                    }
                }
            }
        }

        if(contentProvider.startVideo.value){
            Animator {
                components.VideoDisplay()
            }
        }
    }
}