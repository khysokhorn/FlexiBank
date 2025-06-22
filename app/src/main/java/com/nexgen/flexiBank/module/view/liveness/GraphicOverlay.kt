package com.nexgen.flexiBank.module.view.liveness

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import androidx.camera.core.ImageProxy
import androidx.core.util.Preconditions
import com.nexgen.flexiBank.R
import com.nexgen.flexiBank.module.view.liveness.model.FaceResult


/**
 * A view which renders a series of custom graphics to be overlayed on top of an associated preview
 * (i.e., the camera preview). The creator can add graphics objects, update the objects, and remove
 * them, triggering the appropriate drawing and invalidation within the view.
 *
 *
 * Supports scaling and mirroring of the graphics relative the camera's preview properties. The
 * idea is that detection items are expressed in terms of an image size, but need to be scaled up to
 * the full view size, and also mirrored in the case of the front-facing camera.
 *
 *
 * Associated [Graphic] items should use the following methods to convert to view
 * coordinates for the graphics that are drawn:
 *
 *
 *  1. [Graphic.scale] adjusts the size of the supplied value from the image scale to
 * the view scale.
 *  1. [Graphic.translateX] and [Graphic.translateY] adjust the
 * coordinate from the image's coordinate system to the view coordinate system.
 *
 */
class GraphicOverlay(context: Context?, attrs: AttributeSet?) : View(context, attrs) {


    private val lock = Any()
    private val graphics: MutableList<Graphic> = ArrayList()

    // Matrix for transforming from image coordinates to overlay view coordinates.
    private val transformationMatrix = Matrix()
    var imageWidth = 0

    var imageHeight = 0

    // The factor of overlay View size to image size. Anything in the image coordinates need to be
    // scaled by this amount to fit with the area of overlay View.
    private var scaleFactor = 1.0f

    // The number of horizontal pixels needed to be cropped on each side to fit the image with the
    // area of overlay View after scaling.
    private var postScaleWidthOffset = 0f

    // The number of vertical pixels needed to be cropped on each side to fit the image with the
    // area of overlay View after scaling.
    private var postScaleHeightOffset = 0f
    private var isImageFlipped = false
    private var needUpdateTransformation = true

    private var transparentBackground: Paint? = null
    private var eraser: Paint? = null
    private var borderPaint: Paint? = null
    private var circlePaint: Paint? = null
    private var horizontalMargin = 0f
    private var verticalMargin = 0f

    private var faceBoundary: RectF? = null

    private val WIDTH_FACTOR = 2.9f
    private val HEIGHT_FACTOR = 12f

    private val alphaValue: Int by lazy {
        200
    }

    private val sweepAngle: Float by lazy {
        TypedValue().apply {
            resources.getValue(
                R.dimen.liveness_camerax_overlay_sweep_angle, this, true
            )
        }.float
    }

    abstract class Graphic(private val overlay: GraphicOverlay) {
        abstract fun draw(canvas: Canvas?)
        protected fun drawRect(
            canvas: Canvas, left: Float, top: Float, right: Float, bottom: Float, paint: Paint?
        ) {
            canvas.drawRect(left, top, right, bottom, paint!!)
        }

        protected fun drawText(canvas: Canvas, text: String?, x: Float, y: Float, paint: Paint?) {
            canvas.drawText(text!!, x, y, paint!!)
        }

        fun scale(imagePixel: Float): Float {
            return imagePixel * overlay.scaleFactor
        }

        val applicationContext: Context
            /** Returns the application context of the app.  */
            get() = overlay.context.applicationContext

        fun isImageFlipped(): Boolean {
            return overlay.isImageFlipped
        }

        /**
         * Adjusts the x coordinate from the image's coordinate system to the view coordinate system.
         */
        fun translateX(x: Float): Float {
            return if (overlay.isImageFlipped) {
                overlay.width - (scale(x) - overlay.postScaleWidthOffset)
            } else {
                scale(x) - overlay.postScaleWidthOffset
            }
        }

        /**
         * Adjusts the y coordinate from the image's coordinate system to the view coordinate system.
         */
        fun translateY(y: Float): Float {
            return scale(y) - overlay.postScaleHeightOffset
        }

        /**
         * Returns a [Matrix] for transforming from image coordinates to overlay view coordinates.
         */
        fun getTransformationMatrix(): Matrix {
            return overlay.transformationMatrix
        }

        fun postInvalidate() {
            overlay.postInvalidate()
        }

    }

    init {
        addOnLayoutChangeListener { view: View?, left: Int, top: Int, right: Int, bottom: Int, oldLeft: Int, oldTop: Int, oldRight: Int, oldBottom: Int ->
            needUpdateTransformation = true
        }
    }

    /** Removes all graphics from the overlay.  */
    fun clear() {
        synchronized(lock) { graphics.clear() }
        postInvalidate()
    }

    /** Adds a graphic to the overlay.  */
    fun add(graphic: Graphic) {
        synchronized(lock) { graphics.add(graphic) }
    }

    /** Removes a graphic from the overlay.  */
    fun remove(graphic: Graphic) {
        synchronized(lock) { graphics.remove(graphic) }
        postInvalidate()
    }

    @SuppressLint("RestrictedApi")
    fun setImageSourceInfo(imageWidth: Int, imageHeight: Int, isFlipped: Boolean) {
        Preconditions.checkState(imageWidth > 0, "image width must be positive")
        Preconditions.checkState(imageHeight > 0, "image height must be positive")
        synchronized(lock) {
            this.imageWidth = (imageWidth * 1)
            this.imageHeight = (imageHeight * 1)
            isImageFlipped = isFlipped
            needUpdateTransformation = true
        }
        postInvalidate()
    }

    private fun updateTransformationIfNeeded() {
        val viewAspectRatio = width.toFloat() / height
        val imageAspectRatio = imageWidth.toFloat() / imageHeight
        postScaleWidthOffset = 0f
        postScaleHeightOffset = 0f
        if (viewAspectRatio > imageAspectRatio) {
            // The image needs to be vertically cropped to be displayed in this view.
            scaleFactor = width.toFloat() / imageWidth
            postScaleHeightOffset = (width.toFloat() / imageAspectRatio - height) / 2
        } else {
            // The image needs to be horizontally cropped to be displayed in this view.
            scaleFactor = height.toFloat() / imageHeight
            postScaleWidthOffset = (height.toFloat() * imageAspectRatio - width) / 2
        }
        transformationMatrix.reset()
        transformationMatrix.setScale(scaleFactor, scaleFactor)
        transformationMatrix.postTranslate(-postScaleWidthOffset, -postScaleHeightOffset)
        if (isImageFlipped) {
            transformationMatrix.postScale(-1f, 1f, width / 2f, height / 2f)
        }
        needUpdateTransformation = false
    }

    /** Draws the overlay with its associated graphic objects.  */
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
        synchronized(lock) {
            updateTransformationIfNeeded()
            for (graphic in graphics) {
                graphic.draw(canvas)
            }
        }
    }

    private var isFaceInCircle = false
    fun onDrawBoundary(
        results: FaceResult, imageProxy: ImageProxy?
    ): Boolean {
        this.clear()
        val it = results.face
        if (it != null) {
            val faceRect = faceBound(results, imageProxy)
            return faceRect?.let { it1 -> createRectF().contains(it1) } ?: true
        }
        postInvalidate()
        return isFaceInCircle
    }

    private fun faceBound(results: FaceResult, imageProxy: ImageProxy?): RectF? {
        val it = results.face
        if (it != null && imageProxy != null) {
            val rotationDegrees = imageProxy.imageInfo.rotationDegrees
            val width = imageProxy.width
            val height = imageProxy.height
            if (rotationDegrees == 0 || rotationDegrees == 180) {
                setImageSourceInfo(
                    width, height, true
                )
            } else {
                setImageSourceInfo(
                    height, width, true
                )
            }
            val facegraphic = FaceGraphic(this, it)
            val x = facegraphic.translateX(it.boundingBox.centerX().toFloat())
            val y = facegraphic.translateY(it.boundingBox.centerY().toFloat())
            var removeBound = 0.4f
            // Calculate positions.
            val left = x - facegraphic.scale(it.boundingBox.width() * 0.40f / 2.0f)
            val top = y - facegraphic.scale(it.boundingBox.height() * 0.40f / 2.0f)
            val right = x + facegraphic.scale(it.boundingBox.width() * 0.40f / 2.0f)
            val bottom = y + facegraphic.scale(it.boundingBox.height() * 0.40f / 2.0f)
            val faceRect = RectF(left, top, right, bottom)
            return faceRect
//            return RectF(results.bounds)
        }
        return null
    }

    fun createRectF() = RectF(
        horizontalMargin - 50,
        verticalMargin - 200,
        measuredWidth - (horizontalMargin - 50),
        (measuredHeight - (measuredHeight * (40 / 100.0)).toFloat())
    )

    private fun createRect() = Rect(0, 0, measuredWidth, measuredHeight)

}