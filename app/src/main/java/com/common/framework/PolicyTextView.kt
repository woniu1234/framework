package com.common.framework

import android.content.Context
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.TextPaint
import android.text.TextUtils
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import java.util.regex.Pattern

class PolicyTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    companion object {
        private var words = arrayOf(
            "《隐私政策》", "《软件许可协议》", "《健康界用户协议》"
        )
    }

    init {
        movementMethod = LinkMovementMethod.getInstance()
    }

    override fun setText(text: CharSequence, type: BufferType) {
        try {
            highlightColor = resources.getColor(R.color.transparent)
            val spannable = SpannableStringBuilder(text)
            for (word in words) {
                val p = Pattern.compile(word, Pattern.LITERAL or Pattern.CASE_INSENSITIVE)
                val m = p.matcher(text)
                while (m.find()) {
                    spannable.setSpan(
                        ForegroundColorSpan(ContextCompat.getColor(context, R.color.purple_500)),
                        m.start(),
                        m.end(),
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                    spannable.setSpan(
                        ClickableSpanListener(word, context),
                        m.start(),
                        m.end(),
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                    break
                }
            }
            super.setText(spannable, type)
        } catch (e: Exception) {
            e.printStackTrace()
            super.setText(text, type)
        }
    }

    class ClickableSpanListener(private val spanText: String, private val context: Context) :
        ClickableSpan() {
        private val mListener: OnClickListener = object : SingleClick() {
            override fun onDoClick(v: View) {
                if (TextUtils.isEmpty(spanText)) return
                if (spanText == words[0]) {//隐私政策

                }
                if (spanText == words[1]) {
                    //用户协议
                }
            }
        }

        override fun onClick(widget: View) {
            mListener.onClick(widget)
        }

        override fun updateDrawState(ds: TextPaint) {
            super.updateDrawState(ds)
            ds.color = ContextCompat.getColor(context, R.color.purple_500)
            ds.isUnderlineText = false
        }

    }
}