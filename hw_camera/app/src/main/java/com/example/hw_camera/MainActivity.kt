package com.example.hw_camera

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Matrix
import android.os.Bundle
import android.util.Size
import android.view.Surface
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import java.io.File
import java.util.*
import java.util.concurrent.Executors

private const val CAMERA_REQUEST = 10
private val ALL_REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)

class MainActivity : AppCompatActivity(), LifecycleOwner {

    private val executor = Executors.newSingleThreadExecutor()
    private lateinit var viewFinder: TextureView
    private lateinit var imageCapture: ImageCapture
    private lateinit var editDir: EditText



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewFinder = findViewById(R.id.view_finder)

        if (permissionsAlreadyGranted()) {
            viewFinder.post {
                startCamera()
            }
        } else {
            ActivityCompat.requestPermissions(this, ALL_REQUIRED_PERMISSIONS, CAMERA_REQUEST)
        }

        viewFinder.addOnLayoutChangeListener {
                _, _, _, _, _, _, _, _, _ -> updateTransform()
        }

        if (externalMediaDirs.isNotEmpty()) {
            editDir = findViewById(R.id.dir)
            editDir.setText(externalMediaDirs.first().absolutePath, TextView.BufferType.EDITABLE)
        }

    }

    fun screenTapped(view: View) {
        fixPath()
        val file = File("${editDir.text}${Calendar.getInstance().time}.jpg")

        imageCapture.takePicture(file, executor,
            object : ImageCapture.OnImageSavedListener {
                override fun onError(
                    imageCaptureError: ImageCapture.ImageCaptureError,
                    message: String,
                    exc: Throwable?
                ) {
                    val msg = "Не удалось сделать фото: $message"
                    viewFinder.post {
                        Toast.makeText(baseContext, msg, Toast.LENGTH_LONG).show()
                    }
                }

                override fun onImageSaved(file: File) {
                    val msg = "Фото сохранено в ${file.absolutePath}"
                    viewFinder.post {
                        Toast.makeText(baseContext, msg, Toast.LENGTH_LONG).show()
                    }
                }
            })
    }



    private fun startCamera() {
        val previewConfig = PreviewConfig.Builder().apply {
            setTargetResolution(Size(viewFinder.width, viewFinder.height))
        }.build()

        val preview = Preview(previewConfig)
        preview.setOnPreviewOutputUpdateListener {
            val parent = viewFinder.parent as ViewGroup
            parent.removeView(viewFinder)
            parent.addView(viewFinder, 0)

            viewFinder.surfaceTexture = it.surfaceTexture
            updateTransform()
        }

        val imageCaptureConfig = ImageCaptureConfig.Builder().apply {
            setCaptureMode(ImageCapture.CaptureMode.MIN_LATENCY)
        }.build()
        imageCapture = ImageCapture(imageCaptureConfig)

        CameraX.bindToLifecycle(this, preview, imageCapture)
    }

    private fun updateTransform() {
        val matrix = Matrix()

        val midW = viewFinder.width.toFloat() / 2f
        val midH = viewFinder.height.toFloat() / 2f

        val rotationDegrees = when(viewFinder.display.rotation) {
            Surface.ROTATION_0 -> 0
            Surface.ROTATION_90 -> 90
            Surface.ROTATION_180 -> 180
            Surface.ROTATION_270 -> 270
            else -> return
        }.toFloat()
        matrix.postRotate(-rotationDegrees, midW, midH)
        if (rotationDegrees == 90f || rotationDegrees == 270f)
            matrix.postScale(midW / midH, midH / midW, midW, midH)

        viewFinder.setTransform(matrix)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == CAMERA_REQUEST) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                viewFinder.post{
                    startCamera()
                }
            } else {
                Toast.makeText(this, "Не выдано разрешение на камеру", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private fun permissionsAlreadyGranted() = ALL_REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    private fun fixPath() {
        var path = editDir.text.toString()
        if (!path.startsWith('/'))
            path = "/$path"
        if (!path.endsWith('/'))
            path = "$path/"
        editDir.setText(path, TextView.BufferType.EDITABLE)
    }

}
