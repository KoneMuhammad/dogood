package com.eyesgood.dogood.ui.screen


import android.R
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.eyesgood.dogood.ui.viewmodel.TaskViewModel
import kotlinx.coroutines.launch


@Composable
fun LandingScreenRoute(
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.primary,
    shape: Shape = RoundedCornerShape(50.dp),
    animateTouch: Boolean = true,
    shadowColor: Color = lerp(containerColor, Color.Black, 0.2f),
    shadowOffset: DpOffset = DpOffset(0.dp, 6.dp),
    contentDescription: String = "Task Screen Picture",

    ) {
    val viewModel: TaskViewModel = hiltViewModel()

    var isPushedByUser by remember { mutableStateOf(false) }
    val userLocation = rememberUpdatedState(String)

    val scope = rememberCoroutineScope()

    val animate = remember { Animatable(0f) }

    val composition by rememberLottieComposition(
        LottieCompositionSpec.Url(viewModel.taskItemsUiState.taskMessage.emblem_url)
    )

    LaunchedEffect(isPushedByUser) {
        if (isPushedByUser) {
            animate.animateTo(
                targetValue = 1f,
                tween(durationMillis = 100, easing = CubicBezierEasing(0.3f, 0.6f, 0.6f, 0.8f))
            )
        }
        if (!isPushedByUser) {
            animate.animateTo(
                0f,
                tween(
                    durationMillis = 100,
                    delayMillis = 150,
                    easing = CubicBezierEasing(0.3f, 0.1f, 0.6f, 0.2f)
                )
            )
        } else if (animate.value == 1f) {
            animate.animateTo(
                0f,
                tween(
                    durationMillis = 100,
                    delayMillis = 150,
                    easing = CubicBezierEasing(0.3f, 0.1f, 0.6f, 0.2f)
                )
            )
        }
    }

    val context = LocalContext.current


    LayoutOwner(
        modifier = modifier,
        composition = { composition },
        onClickAction = {
            scope.launch {
                viewModel.getTaskMessage()
            }
        },
        contentDescription = contentDescription,
        containerColor = containerColor,
        animateTouch = animateTouch,
        shadowColor = shadowColor,
        shadowOffset = shadowOffset,
        shape = shape,
        onPushChange = { isPushedByUser = it },
        content = { ButtonText() },
        buttonAnimation = {
            DpOffset(
                shadowOffset.x * animate.value,
                shadowOffset.y * animate.value
            )
        },
        text = { viewModel.taskItemsUiState.taskMessage.task_message },
        iterations = LottieConstants.IterateForever,

        )

}


@Composable
fun LayoutOwner(
    modifier: Modifier,
    onClickAction: () -> Unit,
    contentDescription: String,
    containerColor: Color,
    animateTouch: Boolean,
    shadowColor: Color,
    shadowOffset: DpOffset,
    shape: Shape,
    onPushChange: (Boolean) -> Unit,
    content: @Composable BoxScope.() -> Unit,
    buttonAnimation: () -> DpOffset,
    text: () -> String,
    composition: () -> LottieComposition?,
    iterations: Int,

    ) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.onSurfaceVariant),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {


        TaskMessageText(text)
        LottieGif(
            composition = composition,
            itterations = iterations,
        )
        // BackgroundImage(resource = resource)

        HugeButton(
            containerColor = containerColor,
            onClickAction = onClickAction,
            animateTouch = animateTouch,
            shadowColor = shadowColor,
            shadowOffset = shadowOffset,
            shape = shape,
            onPushChange = onPushChange,
            content = content,
            buttonAnimation = buttonAnimation
        )

    }
}


@Composable
fun TaskMessageText(string: () -> String) {
    BasicText(
        modifier = Modifier
            .height(60.dp)
            .widthIn(max = 265.dp),
        text = string(),
        style = TextStyle(textAlign = TextAlign.Center),
        autoSize = TextAutoSize.StepBased(
            minFontSize = 26.sp,
            maxFontSize = 30.sp,
            stepSize = 2.sp
        )
    )
}

@Composable
fun LottieGif(composition: () -> LottieComposition?, itterations: Int) {

    LottieAnimation(
        modifier = Modifier.size(75.dp),
        composition = composition(),
        iterations = itterations
    )
}

@Composable
fun HugeButton(
    containerColor: Color,
    onClickAction: () -> Unit,
    animateTouch: Boolean,
    shadowColor: Color,
    shadowOffset: DpOffset,
    buttonAnimation: () -> DpOffset,
    shape: Shape,
    content: @Composable BoxScope.() -> Unit,
    onPushChange: (Boolean) -> Unit,
) {
    Box(
        modifier = Modifier
            .padding(18.dp)
            .size(width = 220.dp, height = 80.dp)
            .pointerInput(animateTouch) {
                if (animateTouch)
                    awaitPointerEventScope {
                        while (true) {
                            awaitPointerEvent()
                            when (this.currentEvent.type) {
                                PointerEventType.Press -> {
                                    onPushChange(true)
                                }

                                PointerEventType.Release, PointerEventType.Exit -> {
                                    onPushChange(
                                        false
                                    )
                                }
                            }
                        }
                    }
            }

            .offset(shadowOffset.x, shadowOffset.y)
            .background(shadowColor, shape)
            .offset(-shadowOffset.x, -shadowOffset.y)
            .offset {
                val dp = buttonAnimation()
                IntOffset(dp.x.toPx().toInt(), dp.y.toPx().toInt())
            }
            .background(containerColor, shape)
            .clip(CircleShape)
            .clickable(
                onClick = onClickAction,
            )

    ) {
        content()
    }
}

@Composable
fun BackgroundImage(resource: Painter) {
    Image(
        painter = resource,
        contentDescription = "Background Image"
    )
}

@Composable
fun BoxScope.ButtonText(onContainerColor: Color = MaterialTheme.colorScheme.onTertiary) {
    Text(
        color = onContainerColor,
        text = "Do Good",
        fontSize = 24.sp,
        modifier = Modifier.align(Alignment.Center)
    )
}