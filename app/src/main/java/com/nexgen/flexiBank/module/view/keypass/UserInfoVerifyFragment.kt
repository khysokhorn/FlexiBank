package com.nexgen.flexiBank.module.view.keypass

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nexgen.flexiBank.module.view.base.BaseComposeFragment
import com.nexgen.flexiBank.network.ApiInterface
import com.nexgen.flexiBank.repository.AppRepository
import com.nexgen.flexiBank.utils.theme.Black
import com.nexgen.flexiBank.utils.theme.ProgressBarBackgroundGray
import com.nexgen.flexiBank.utils.theme.ProgressBarBlue
import com.nexgen.flexiBank.utils.theme.TextColorPrimary
import com.nexgen.flexiBank.utils.theme.TextColorSecondary
import com.nexgen.flexiBank.utils.theme.White
import com.nexgen.flexiBank.viewmodel.PasscodeViewModel


class UserInfoVerifyFragment : BaseComposeFragment<PasscodeViewModel, AppRepository>() {
    private val GradientStartColor = Color(0xFFA09EFF)
    private val GradientMidColor = Color(0xFF706EFF)
    private val GradientEndColor = Color(0xFF4A4AFF)

    @Composable
    override fun ComposeContent() {
        UserInfoVerify()
    }

    @Composable
    fun UserInfoVerify() {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(White)
                .padding(horizontal = 16.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            ScrollableNumberPickerWithIndicator(0..99, 0)
            Spacer(modifier = Modifier.weight(1f))
            Text(
                "Verifying",
                color = Black,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
            )
        }
    }

    @Composable
    fun ScrollableNumberPickerWithIndicator(
        range: IntRange = 0..99,
        initialValue: Int = 0
    ) {
        // Calculate the current number based on animation progress

        // Starting angle for animation
        var targetAngle by remember { mutableStateOf(0f) }

        // Start animation when composable is first launched
        LaunchedEffect(Unit) {
            targetAngle = 360f
        }

        // The animation will run once to the target value and then stop
        val progressAngle by animateFloatAsState(
            targetValue = targetAngle,
            animationSpec = tween(
                durationMillis = 10000,
                easing = LinearEasing
            ),
            label = "progress arc"
        )
        val progressFraction = progressAngle / 360f
        val currentNumberToDisplay = (1 + (progressFraction * 98).toInt()).coerceIn(1..99)
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(200.dp)
        ) {
            // Background circle
            Canvas(modifier = Modifier.fillMaxSize()) {
                drawArc(
                    color = ProgressBarBackgroundGray,
                    startAngle = -90f,
                    sweepAngle = 360f,
                    useCenter = false,
                    style = Stroke(width = 15.dp.toPx(), cap = StrokeCap.Round)
                )
            }

            // Animated progress circle
            Canvas(modifier = Modifier.fillMaxSize()) {
                drawArc(
                    brush = Brush.sweepGradient(
                        colors = listOf(
                            GradientStartColor,
                            GradientMidColor,
                            GradientEndColor,
                            GradientStartColor
                        )
                    ),
                    startAngle = -90f,
                    sweepAngle = progressAngle,
                    useCenter = false,
                    style = Stroke(width = 15.dp.toPx(), cap = StrokeCap.Round)
                )
            }
            Text(
                text = currentNumberToDisplay.toString(),
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold,
                color = ProgressBarBlue
            )
        }

        // Rest of the UI remains the same...
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "Please wait a moment.",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = TextColorPrimary,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "We are verifying your identity.",
            fontSize = 16.sp,
            color = TextColorSecondary,
            textAlign = TextAlign.Center
        )
    }

    override fun getViewModel(): Class<PasscodeViewModel> = PasscodeViewModel::class.java

    override fun getRepository(): AppRepository = AppRepository(
        remoteDataSource.buildApi(requireActivity(), ApiInterface::class.java)
    )

}

@Preview()
@Composable
fun UserInfoVerifyFragmentContentPreview() {
    UserInfoVerifyFragment().UserInfoVerify()
}