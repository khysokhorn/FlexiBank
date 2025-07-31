package com.nexgen.flexiBank.module.view.utils.customView

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import com.nexgen.flexiBank.module.view.utils.toPx

/**
 * Custom QR Scanner Cutout View with Rounded Corners and Animation.
 *
 * Based on work by naahac:
 * https://github.com/naahac/qr-cutout
 *
 * Modified by Sokhorn to include pulsing animation.
 */

class CustomRectangleWithRoundedCornersCutoutView
@JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var cornerAlpha = 255

    private var scaleFactor = 1f

    private val pulseAnimator = ValueAnimator.ofFloat(1f, 0.94f, 1f).apply {
        duration = 1000L
        repeatCount = ValueAnimator.INFINITE
        repeatMode = ValueAnimator.RESTART
        interpolator = LinearInterpolator()
        addUpdateListener {
            scaleFactor = it.animatedValue as Float
            invalidate()
        }
    }

    init {
        pulseAnimator.start()
    }

    private val cornerRadius = 24.toPx
    private val qrScannerHeight = 300.toPx
    private val qrScannerWidth = (qrScannerHeight)

    /*Determines vertical position of the center point in the scanner cutout shape
      0f -> Center of scanner cutout shape will be at the top of the Canvas
     0.5f -> Center of scanner cutout shape will be at the middle of the Canvas
     1f -> Center of scanner cutout shape will be at the bottom of the Canvas */
    private val verticalOffset = 0.5f

    /*Determines horizontal position of the center point in the scanner cutout shape
  0f -> Center of scanner cutout shape will be at the top of the Canvas
 0.5f -> Center of scanner cutout shape will be at the middle of the Canvas
 1f -> Center of scanner cutout shape will be at the bottom of the Canvas */
    private val horizontalOffset = 0.5f

    // Edges of QR scanner
    private var xAxisLeftEdge = 0f
    private var xAxisRightEdge = 0f
    private var yAxisTopEdge = 0f
    private var yAxisBottomEdge = 0f

    private val frameStrokeWidth = 4.toPx.toFloat()

    private val backgroundPaint = Paint().apply {
        color = Color.TRANSPARENT
    }

    private val transparentPaint = Paint().apply {
        color = Color.TRANSPARENT
    }

    private val framePaint = Paint().apply {
        isAntiAlias = true
        color = Color.WHITE
        strokeWidth = frameStrokeWidth
        style = Paint.Style.STROKE
    }

    private lateinit var backgroundShape: Path
    private lateinit var qrScannerShape: Path
    private lateinit var qrScannerCornersShape: Path

    private fun createBackgroundPath() = Path().apply {
        lineTo(right.toFloat(), 0f)
        lineTo(right.toFloat(), bottom.toFloat())
        lineTo(0f, bottom.toFloat())
        lineTo(0f, 0f)
        fillType = Path.FillType.EVEN_ODD
    }

    private fun createQrPath() = Path().apply {
        moveTo(xAxisLeftEdge, yAxisTopEdge + cornerRadius)
        quadTo(xAxisLeftEdge, yAxisTopEdge, xAxisLeftEdge + cornerRadius, yAxisTopEdge)

        lineTo(xAxisRightEdge - cornerRadius, yAxisTopEdge)
        quadTo(xAxisRightEdge, yAxisTopEdge, xAxisRightEdge, yAxisTopEdge + cornerRadius)

        lineTo(xAxisRightEdge, yAxisBottomEdge - cornerRadius)
        quadTo(xAxisRightEdge, yAxisBottomEdge, xAxisRightEdge - cornerRadius, yAxisBottomEdge)

        lineTo(xAxisLeftEdge + cornerRadius, yAxisBottomEdge)
        quadTo(xAxisLeftEdge, yAxisBottomEdge, xAxisLeftEdge, yAxisBottomEdge - cornerRadius)
        lineTo(xAxisLeftEdge, yAxisTopEdge + cornerRadius)
        fillType = Path.FillType.EVEN_ODD
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.apply {
            val scaledWidth = qrScannerWidth * scaleFactor
            val scaledHeight = qrScannerHeight * scaleFactor

            xAxisLeftEdge = width * horizontalOffset - scaledWidth / 2f
            xAxisRightEdge = width * horizontalOffset + scaledWidth / 2f
            yAxisTopEdge = height * verticalOffset - scaledHeight / 2f
            yAxisBottomEdge = height * verticalOffset + scaledHeight / 2f

            backgroundShape = createBackgroundPath()
            qrScannerShape = createQrPath()
            qrScannerCornersShape = createCutoutWithCorners()
            backgroundShape.addPath(qrScannerShape)

            framePaint.alpha = cornerAlpha
            drawPath(backgroundShape, backgroundPaint)
            drawPath(qrScannerShape, transparentPaint)
            drawPath(qrScannerCornersShape, framePaint)
        }
    }

    private fun createCutoutWithCorners() = Path().apply {
        val lineLength = 40.toPx
        val radius = 32.toPx // smaller corner radius for ABA-style

        // Top-left
        moveTo(xAxisLeftEdge, yAxisTopEdge + radius + lineLength)
        lineTo(xAxisLeftEdge, yAxisTopEdge + radius)
        quadTo(xAxisLeftEdge, yAxisTopEdge, xAxisLeftEdge + radius, yAxisTopEdge)
        lineTo(xAxisLeftEdge + radius + lineLength, yAxisTopEdge)

        // Top-right
        moveTo(xAxisRightEdge - radius - lineLength, yAxisTopEdge)
        lineTo(xAxisRightEdge - radius, yAxisTopEdge)
        quadTo(xAxisRightEdge, yAxisTopEdge, xAxisRightEdge, yAxisTopEdge + radius)
        lineTo(xAxisRightEdge, yAxisTopEdge + radius + lineLength)

        // Bottom-right
        moveTo(xAxisRightEdge, yAxisBottomEdge - radius - lineLength)
        lineTo(xAxisRightEdge, yAxisBottomEdge - radius)
        quadTo(xAxisRightEdge, yAxisBottomEdge, xAxisRightEdge - radius, yAxisBottomEdge)
        lineTo(xAxisRightEdge - radius - lineLength, yAxisBottomEdge)

        // Bottom-left
        moveTo(xAxisLeftEdge + radius + lineLength, yAxisBottomEdge)
        lineTo(xAxisLeftEdge + radius, yAxisBottomEdge)
        quadTo(xAxisLeftEdge, yAxisBottomEdge, xAxisLeftEdge, yAxisBottomEdge - radius)
        lineTo(xAxisLeftEdge, yAxisBottomEdge - radius - lineLength)
    }


    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        pulseAnimator.cancel()
    }

}
// Credit
