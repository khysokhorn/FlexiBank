package com.nexgen.flexiBank.module.view.liveness

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.google.mlkit.vision.face.Face
import com.google.mlkit.vision.face.FaceLandmark.LandmarkType


class FaceGraphic(
  overlay: GraphicOverlay,
  private val face: Face
) : GraphicOverlay.Graphic(overlay) {
  private val facePositionPaint: Paint
  private val numColors = COLORS.size

  private val boxPaints = Array(numColors) { Paint() }
  private val labelPaints = Array(numColors) { Paint() }

  init {
    val selectedColor = Color.WHITE
    facePositionPaint = Paint()
    facePositionPaint.color = selectedColor
    for (i in 0 until numColors) {
      boxPaints[i] = Paint()
      boxPaints[i].color = COLORS[i][1]
      boxPaints[i].style = Paint.Style.STROKE
      boxPaints[i].strokeWidth = BOX_STROKE_WIDTH
      labelPaints[i] = Paint()
      labelPaints[i].color = COLORS[i][1]
      labelPaints[i].style = Paint.Style.FILL
    }
  }

  /** Draws the face annotations for position on the supplied canvas. */
  override fun draw(canvas: Canvas?) {
  }

  private fun drawFaceLandmark(canvas: Canvas, @LandmarkType landmarkType: Int) {
    val faceLandmark = face.getLandmark(landmarkType)
    if (faceLandmark != null) {
      canvas.drawCircle(
        translateX(faceLandmark.position.x),
        translateY(faceLandmark.position.y),
        FACE_POSITION_RADIUS,
        facePositionPaint
      )
    }
  }

  companion object {
    private const val FACE_POSITION_RADIUS = 8.0f
    private const val ID_TEXT_SIZE = 30.0f
    private const val ID_Y_OFFSET = 40.0f
    private const val BOX_STROKE_WIDTH = 5.0f
    private const val NUM_COLORS = 10
    private val COLORS = arrayOf(
      intArrayOf(Color.BLACK, Color.WHITE),
      intArrayOf(Color.WHITE, Color.MAGENTA),
      intArrayOf(Color.BLACK, Color.LTGRAY),
      intArrayOf(Color.WHITE, Color.RED),
      intArrayOf(Color.WHITE, Color.BLUE),
      intArrayOf(Color.WHITE, Color.DKGRAY),
      intArrayOf(Color.BLACK, Color.CYAN),
      intArrayOf(Color.BLACK, Color.YELLOW),
      intArrayOf(Color.WHITE, Color.BLACK),
      intArrayOf(Color.BLACK, Color.GREEN)
    )
  }
}
