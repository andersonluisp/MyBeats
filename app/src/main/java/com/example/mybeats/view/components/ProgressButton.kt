package com.example.mybeats.view.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.view.isVisible
import com.example.mybeats.R
import com.google.android.material.progressindicator.CircularProgressIndicator

class ProgressButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {

    private val progressBar: CircularProgressIndicator
    private val buttonTextView: TextView
    private var isLoading = false

    init {
        val root = LayoutInflater.from(context).inflate(R.layout.progress_button_layout, this, true)
        buttonTextView = root.findViewById(R.id.buttonText)
        progressBar = root.findViewById(R.id.circularProgressBar)
        loadAttr(attrs, defStyleAttr)
    }

    private fun loadAttr(attrs: AttributeSet?, defStyleAttr: Int) {
        val arr = context.obtainStyledAttributes(
            attrs,
            R.styleable.ProgressButton,
            defStyleAttr,
            0
        )

        val buttonText = arr.getString(R.styleable.ProgressButton_text)
        isLoading = arr.getBoolean(R.styleable.ProgressButton_isLoading, false)
        val enabled = arr.getBoolean(R.styleable.ProgressButton_enabled, true)
        arr.recycle()
        isEnabled = enabled
        buttonTextView.isEnabled = enabled
        setText(buttonText)
        setLoading(isLoading)
    }

    fun setLoading(loading: Boolean) {
        isClickable = !loading
        buttonTextView.isVisible = !loading
        progressBar.isVisible = loading
        isLoading = loading
        invalidate()
        requestLayout()
    }

    private fun setText(text: String?) {
        buttonTextView.text = text
    }

    fun isLoading(): Boolean {
        return isLoading
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        buttonTextView.isEnabled = enabled
    }
}
