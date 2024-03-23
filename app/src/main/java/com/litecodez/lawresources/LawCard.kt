package com.litecodez.lawresources


import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.litecodez.lawresources.ui.theme.Shapes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale

/**
 * A composable function that creates an expandable card with a title and description. The card expands or collapses when clicked.
 *
 * @param title The title text to display in the card.
 * @param titleFontSize The font size of the title text. Default is MaterialTheme.typography.subtitle2.fontSize.
 * @param titleFontWeight The font weight of the title text. Default is FontWeight.Bold.
 * @param description The description text to display in the card when expanded.
 * @param descriptionFontSize The font size of the description text. Default is MaterialTheme.typography.caption.fontSize.
 * @param descriptionFontWeight The font weight of the description text. Default is FontWeight.Normal.
 * @param descriptionMaxLines The maximum number of lines for the description text. Default is 500.
 * @param shape The shape of the card. Default is Shapes.medium.
 * @param padding The padding inside the card. Default is 12.dp.
 * @param elevation The elevation of the card. Default is 8.dp.
 * @param mistyRose The starting color for the vertical gradient background of the card. Default is Color(0xFFFDF4F5).
 * @param lightBrown The ending color for the vertical gradient background of the card. Default is Color(0xFFE1BC9A).
 */

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ExpandableCard(
    title: String,
    titleFontSize: TextUnit = MaterialTheme.typography.subtitle2.fontSize,
    titleFontWeight: FontWeight = FontWeight.Bold,
    description: String,
    descriptionFontSize: TextUnit = MaterialTheme.typography.caption.fontSize,
    descriptionFontWeight: FontWeight = FontWeight.Normal,
    descriptionMaxLines: Int = 500,
    shape: CornerBasedShape = Shapes.medium,
    padding: Dp = 12.dp,
    elevation: Dp = 8.dp,
    mistyRose: Color = Color(0xFFFDF4F5),
    lightBrown: Color = Color(0xFFE1BC9A)
) {
    var buttonState by remember { mutableStateOf(false) }
    var progressState by remember { mutableStateOf(false) }
    var buttonText by remember { mutableStateOf("Read aloud") }
    var lawToSpeech: LawToSpeech? by remember { mutableStateOf(null) }
    var expandedState by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    fun String.toSentenceCase(): String {
        if (this.isEmpty()) return this
        val lowerCaseString = this.lowercase(Locale.ROOT)
        return lowerCaseString[0].uppercaseChar() + lowerCaseString.substring(1)
    }
    val extraPadding by animateDpAsState(
        if (expandedState) padding+2.dp else padding,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )
    val rotationState by animateFloatAsState(
        targetValue = if (expandedState) 180f else 0f

    )
    val gradientBackground = Brush.verticalGradient(
        colors = listOf(
            mistyRose,
            lightBrown
        )
    )
    fun isOverWordLimitOf(text: String,wordLimit: Int = 250): Boolean {
        val words = text.split("\\s+".toRegex())
        return words.size > wordLimit
    }
    val shouldAddButton = remember { mutableStateOf(false) }
    val context = LocalContext.current

    LaunchedEffect(Unit){
        scope.launch {
            val overWordLimit = isOverWordLimitOf(description, 1000)
            withContext(Dispatchers.Main) {
                shouldAddButton.value = !overWordLimit
            }
        }
    }
    @Composable
    fun ReadingText() {
        var text by remember { mutableStateOf("Reading.") }

        LaunchedEffect(key1 = true) {
            while (true) {
                delay(500)

                text = when (text) {
                    "Reading." -> "Reading.."
                    "Reading.." -> "Reading..."
                    "Reading..." -> "Reading...."
                    else -> "Reading."
                }
            }
        }

        Text(text = text)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()

            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearOutSlowInEasing
                )
            ),
        shape = shape,
        onClick = {
            expandedState = !expandedState
        },
        border = BorderStroke(width = 1.dp, color = Color.Black),
        elevation = elevation
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(gradientBackground)
                .padding(bottom = 2.dp, top = extraPadding.coerceAtLeast(0.dp),start = padding, end = padding),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.Start
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                    Text(
                        modifier = Modifier
                            .weight(6f),
                        text = title,
                        fontSize = titleFontSize,
                        fontWeight = titleFontWeight,
                        maxLines = 5,
                        color = black
                        //overflow = TextOverflow.Ellipsis
                    )

                IconButton(
                    modifier = Modifier
                        .weight(1f)
                        .alpha(ContentAlpha.medium)
                        .rotate(rotationState),
                    onClick = {
                        expandedState = !expandedState
                    }) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Drop-Down Arrow",
                        tint = black
                    )
                }
            }
            if (expandedState) {
                SelectionContainer(){
                    Text(
                        text = description,
                        fontSize = descriptionFontSize,
                        fontWeight = descriptionFontWeight,
                        maxLines = descriptionMaxLines,
                        overflow = TextOverflow.Ellipsis,
                        color = black
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Divider(thickness = 2.dp)
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically

                ) {

                    DisposableEffect(context) {
                        onDispose {
                            buttonState = false
                            lawToSpeech?.stop()
                        }
                    }
                    if (shouldAddButton.value) {
                        if(progressState){
                            ReadingText()
                        }
                        Text(text = " ")
                        Button(
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFA05A2C), contentColor = Color.White),
                            onClick = {
                            buttonState = !buttonState
                            if (lawToSpeech == null) {
                                //println("Starting Speech")
                                lawToSpeech = CustomApplicationContext.instance.lawToSpeech
                            }

                            buttonText = if (buttonState) {
                                if (!lawToSpeech!!.isLTSReady()) {
                                    //println("not ready")
                                    lawToSpeech!!.initialize()
                                }
                                lawToSpeech!!.getLTS()?.speak("$title.\n${description.toSentenceCase()}", TextToSpeech.QUEUE_FLUSH, null, "LTS")
                                "Stop"

                            } else {
                                progressState = false
                                lawToSpeech!!.stop()
                                "Read aloud"
                            }
                            progressState = true
                            lawToSpeech!!.getLTS()?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                                override fun onStart(utteranceId: String?) {
                                    // I will do nothing
                                }
                                override fun onDone(utteranceId: String?) {
                                    scope.launch {
                                        withContext(Dispatchers.Main) {
                                            buttonText = "Read aloud"
                                            buttonState = false
                                            progressState = false
                                        }
                                    }

                                }
                                @Deprecated("Deprecated in Java",
                                    ReplaceWith("lawToSpeech?.shutdown()")
                                )
                                override fun onError(utteranceId: String?) {
                                    // Handle error, maybe..
                                    lawToSpeech?.shutdown()
                                }

                                override fun onStop(utteranceId: String?, interrupted: Boolean) {
                                    super.onStop(utteranceId, interrupted)
                                    if(interrupted){
                                        buttonState = false
                                        progressState = false
                                    }
                                }
                            })
                        }) {
                            Text(
                                text = buttonText,
                                fontSize = descriptionFontSize,
                                fontWeight = descriptionFontWeight,
                                maxLines = descriptionMaxLines,
                                overflow = TextOverflow.Ellipsis,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}