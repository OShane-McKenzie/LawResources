package com.litecodez.lawresources

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * A composable function that displays a message with a gradient background.
 *
 * @param text The text to be displayed, default value is "Hello World".
 * @param textColor The color of the text, default value is Color.Black.
 * @param fontWeight The font weight of the text, default value is FontWeight.Normal.
 * @param height The height of the message box, default value is 50.dp.
 * @param width The width of the message box, default value is 270.dp.
 * @param fontSize The divisor for the font size relative to the box height, default value is 3.
 */
@Composable
fun MessageDisplay(text: String = "Hello World",
                   textColor: Color = Color.Black,fontWeight: FontWeight = FontWeight.Normal,
                   height: Dp = 60.dp, width:Dp = 350.dp, fontSize:Int = 5) {
    val lightBrown = Color(0xFFE1BC9A)
    BoxWithConstraints(
        modifier = Modifier
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(Color.White,Color.LightGray,Color.White),
                    startX = 50f,
                    endX = Float.POSITIVE_INFINITY
                ),
                shape = RoundedCornerShape(4.dp)
            )
            .padding(4.dp)
            .height(height)
            .fillMaxWidth(0.8f)
        ,
        contentAlignment = Alignment.Center
    ) {
        val boxSize = this.maxHeight
        Text(
            text = text,
            style = TextStyle(
                color = textColor,
                fontWeight = FontWeight.Bold,
                fontSize = (boxSize / fontSize).value.sp

            )
        )
    }
}