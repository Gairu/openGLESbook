package com.example.openglesbook

import android.content.res.AssetManager
import android.opengl.EGLConfig
import android.opengl.GLSurfaceView
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.openglesbook.databinding.ActivityMainBinding
import javax.microedition.khronos.opengles.GL10

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    /**
     * A native method that is implemented by the 'openglesbook' native library,
     * which is packaged with this application.
     */
    external fun surfaceCreated(assetManager: AssetManager, id: Int)
    external fun surfaceChanged(width: Int, height: Int)
    external fun drawFrame(deltaTime: Float)
    external fun touchEvent(motion: Int, x: Float, y: Float)

    private inner class SurfaceRenderer : GLSurfaceView.Renderer {
        private var lastTime = System.currentTimeMillis()
        override fun onSurfaceCreated(gl10: GL10, eglConfig: EGLConfig) {
            surfaceCreated(assets, intent.getIntExtra("id", 0)
        }

        override fun onSurfaceChanged(gl10: GL10, width: Int, height: Int) {
            surfaceChanged(width, height)
        }

        override fun onDrawFrame(gl10: GL10) {
            val currentTime = System.currentTimeMillis()
            val deltaTime =(currentTime - lastTime) / 1000.0f
            lastTime = currentTime

            drawFrame(deltaTime)
        }
    }

    private inner class SurfaceView(context: Context) : GLSurfaceView(context) {
        private val renderer: SurfaceRenderer

        init {
            setEGLContextClientVersion(2)
            renderer = SurfaceRenderer()
            setRenderer(renderer)
        }
    }

    companion object {
        // Used to load the 'openglesbook' library on application startup.
        init {
            System.loadLibrary("openglesbook")
        }
    }
}