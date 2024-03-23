package com.litecodez.lawresources

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import java.lang.Thread.sleep

@Composable
fun CircleShapeWithText(
    circleSize: Float = 85f,
    circleBackground: Color = Color(0xFFA05A2C),
    circleText: String = "Wording",
    destination:String = "",
    circleBorderColor: Color = Color.White,
    elevation: Dp = 4.dp,
    onClicked: () -> Unit
) {
    val circleAltColor by remember { mutableStateOf(Color(0xFFE2925D)) }
    val circleBorder by remember { mutableStateOf(circleBorderColor) }
    var finalBg by remember {
        mutableStateOf(
            circleBackground

        )
    }
    finalBg = if(destination == appNavigator.getView()){
        circleAltColor
    }else{
        circleBackground
    }
    val gradientBackground = Brush.radialGradient(
        colors = listOf(
            finalBg,
            Color(0xFFE1BC9A)
        ),
        radius = 200f
    )

    val interactionSource = remember { MutableInteractionSource() }
    val indication = rememberRipple(bounded = false, radius = circleSize.dp / 2)

    Surface(
        modifier = Modifier
            .size(circleSize.dp)
            .clip(CircleShape)
            .clickable {
                if (contentProvider.values.value.isOnline && !contentProvider.isAppLoading.value) {
                    onClicked()
                }
            },
        shape = CircleShape,
        color = Color.Transparent,
        elevation = elevation
    ) {
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .background(gradientBackground)
                .border(2.dp, circleBorder, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            val boxSize = this.maxHeight
            Text(
                text = circleText,
                color = Color.White,
                fontSize = (boxSize / 6).value.sp,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}
