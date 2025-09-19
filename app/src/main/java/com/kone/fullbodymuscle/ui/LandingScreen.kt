package com.kone.fullbodymuscle.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import android.content.res.Configuration
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.kone.fullbodymuscle.R
import com.kone.fullbodymuscle.ui.theme.FullbodymuscleTheme
import com.kone.fullbodymuscle.viewModel.TaskViewModel
import kotlinx.coroutines.launch


@Composable
fun LandingScreenRoute(
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.primary,
    shape: Shape = RoundedCornerShape(20.dp),
    animateTouch: Boolean = true,
    shadowColor: Color = lerp(containerColor, Color.Black, 0.2f),
    shadowOffset: DpOffset = DpOffset(0.dp, 6.dp),
    contentDescription: String = "Task Screen Picture",
    resource: Painter = painterResource(id = R.drawable.background_scape),

) {
    val viewModel: TaskViewModel = hiltViewModel()

    var isPushedByUser by remember { mutableStateOf(false) }
    val userLocation = rememberUpdatedState(String)

    val scope = rememberCoroutineScope()

    val animate = remember { Animatable(0f) }
    val animationPercentage = animate.value

    val composition by rememberLottieComposition(
        LottieCompositionSpec.Url(viewModel.taskItemsUiState.taskMessage.emblem_url)
    )
    val buttonAnimation = DpOffset(
        shadowOffset.x * animationPercentage,
        shadowOffset.y * animationPercentage
    )
    LaunchedEffect(Unit) {

    }

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
        buttonAnimation = { buttonAnimation },
        text = viewModel.taskItemsUiState.taskMessage.task_message,
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
    text: String,
    composition: () -> LottieComposition?,
    iterations: Int

    ) {
    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .height(380.dp)
                .width(200.dp)
        ) {
            LottieGif(
                composition = composition,
                itterations = iterations
            )
            TaskMessageText(text)
           // BackgroundImage(resource = resource)

        }
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
fun TaskMessageText(string: String){
    Text(text = string , modifier = Modifier.size(100.dp), )
}

@Composable
fun LottieGif(composition: () -> LottieComposition?, itterations: Int) {

    LottieAnimation(
        composition = composition(),
        iterations =  itterations
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
            .size(width = 160.dp, height = 60.dp)
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
            .clickable(
                onClick = onClickAction,
            )
            .offset(shadowOffset.x, shadowOffset.y)
            .background(shadowColor, shape)
            .offset(-shadowOffset.x, -shadowOffset.y)
            .offset {
                val dp = buttonAnimation()
                IntOffset(dp.x.toPx().toInt(), dp.y.toPx().toInt())
            }
            .background(containerColor, shape)

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
fun ButtonText(onContainerColor: Color = MaterialTheme.colorScheme.onPrimary) {
    Text(color = onContainerColor, text = "Good Deed")
}


@Preview(name = "Light")
@Preview(name = "Light", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun previewResource() {
    FullbodymuscleTheme {

    }
}


