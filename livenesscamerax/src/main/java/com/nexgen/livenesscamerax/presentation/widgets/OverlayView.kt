package com.nexgen.livenesscamerax.presentation.widgets

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.Rect
import android.graphics.RectF
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.core.content.ContextCompat
import com.nexgen.livenesscamerax.R

private const val WIDTH_FACTOR = 3.5f
private const val HEIGHT_FACTOR = 4f


internal class OverlayView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {
    private var transparentBackground: Paint? = null
    private var eraser: Paint? = null
    private var borderPaint: Paint? = null
    private var horizontalMargin = 0f
    private var verticalMargin = 0f

    private val alphaValue: Int by lazy {
        resources.getInteger(R.integer.liveness_camerax_overlay_alpha_background)
    }

    private val sweepAngle: Float by lazy {
        TypedValue().apply {
            resources.getValue(
                R.dimen.liveness_camerax_overlay_sweep_angle,
                this,
                true
            )
        }.float
    }

    init {
        setLayerType(LAYER_TYPE_SOFTWARE, null)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (horizontalMargin == 0f) {
            horizontalMargin = measuredWidth / WIDTH_FACTOR
        }
        if (verticalMargin == 0f) {
            verticalMargin = measuredHeight / HEIGHT_FACTOR
        }
        val rect = createRect()
        transparentBackground?.let { canvas.drawRect(rect, it) }
        borderPaint?.let {
            canvas.drawArc(
                createRectF(), 0F, sweepAngle, true, it
            )
        }
        eraser?.let {
            canvas.drawArc(
                createRectF(), 0F, sweepAngle, true, it
            )
        }
    }

    private fun createRect() = Rect(0, 0, measuredWidth, measuredHeight)

    private fun createRectF(): RectF {
        val ovalWidth = measuredWidth * 0.85f // 70% of view width
        val ovalHeight = measuredHeight * 0.6f // 50% of view height

        val centerX = measuredWidth / 2f
        val centerY = measuredHeight / 2f

        val halfWidth = ovalWidth / 2f
        val halfHeight = ovalHeight / 2f

        return RectF(
            centerX - halfWidth,
            centerY - halfHeight,
            centerX + halfWidth,
            centerY + halfHeight
        )
    }


    fun init(
        @ColorRes borderColorRes: Int = R.color.liveness_camerax_overlay_border,
        @ColorRes backgroundColorRes: Int = R.color.liveness_camerax_overlay_background,
        @DimenRes strokeWidthRes: Int = R.dimen.liveness_camerax_overlay_border_stroke,
        @DimenRes horizontalMarginRes: Int = R.dimen.liveness_camerax_overlay_horizontal_margin,
        @DimenRes verticalMarginRes: Int = R.dimen.liveness_camerax_overlay_vertical_margin,
    ) {
        this.horizontalMargin = TypedValue().apply {
            resources.getValue(horizontalMarginRes, this, true)
        }.float
        this.verticalMargin = TypedValue().apply {
            resources.getValue(verticalMarginRes, this, true)
        }.float

        transparentBackground = Paint().apply {
            color = ContextCompat.getColor(context, backgroundColorRes)
            alpha = alphaValue
            style = Paint.Style.FILL
        }

        borderPaint = Paint().apply {
            color = ContextCompat.getColor(context, borderColorRes)
            strokeWidth = resources.getDimension(strokeWidthRes)
            style = Paint.Style.STROKE
        }

        eraser = Paint().apply {
            isAntiAlias = true
            xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
            style = Paint.Style.FILL
        }
    }
}
