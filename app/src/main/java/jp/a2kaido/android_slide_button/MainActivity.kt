package jp.a2kaido.android_slide_button

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import android.view.View
import android.widget.HorizontalScrollView
import android.widget.RelativeLayout
import android.widget.TextView

class MainActivity : AppCompatActivity(), View.OnTouchListener {

    val linearLayout: RelativeLayout by lazy {
        findViewById(R.id.linearLayout) as RelativeLayout
    }

    val slideView: RelativeLayout by lazy {
        findViewById(R.id.slideView) as RelativeLayout
    }

    val paytext: TextView by lazy {
        findViewById(R.id.paytext) as TextView
    }

    val slideText: TextView by lazy {
        findViewById(R.id.slideText) as TextView
    }

    val horizon: HorizontalScrollView by lazy {
        (findViewById(R.id.horizontalScrollView) as HorizontalScrollView).apply {
            isSmoothScrollingEnabled = false
        }
    }

    var preDx: Int = 0
    var newDx: Int = 0
    var initialWidth: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        slideView.setOnTouchListener(this)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)

        horizon.fullScroll(HorizontalScrollView.FOCUS_RIGHT)
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        v?.let {
            newDx = event!!.rawX.toInt()

            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    initialWidth = it.width
                }
                MotionEvent.ACTION_MOVE -> {
                    val dx = it.left + newDx - preDx
                    val textDx = slideText.left + newDx - preDx

                    if (textDx >= 0 && it.width + dx < linearLayout.width) {
                        it.layout(0, 0, it.width + dx, it.height)
                        slideText.layout(textDx, 0, textDx + slideText.width, it.height)
                    }
                }
                MotionEvent.ACTION_UP -> {
                    it.layout(0, 0, initialWidth, it.height)
                    slideText.layout(0, 0, initialWidth, it.height)
                }
            }

            preDx = newDx
        }

        return true
    }
}
