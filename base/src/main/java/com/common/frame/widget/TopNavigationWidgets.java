package com.common.frame.widget;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;

import com.common.frame.R;

import java.lang.reflect.Method;

/**
 * 自定义TopBar
 */
public class TopNavigationWidgets extends FrameLayout {
    private static final long TIME_INTERVAL = 500L;
    private long mLastClickTime = 0;

    protected TextView titleView;
    private ImageButton leftBtn;
    private int mWidth, mChildHeight;

    public TopNavigationWidgets(@NonNull Context context) {
        super(context);
        init(context, null, 0);
    }

    public TopNavigationWidgets(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public TopNavigationWidgets(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        int statusBar = 0;
        if (resourceId > 0) {
            statusBar = getResources().getDimensionPixelSize(resourceId);
        }
        if (statusBar == 0) {
            statusBar = getResources().getDimensionPixelSize(R.dimen.dp_23);
        }
        setPadding(0, statusBar, 0, 0);
        setMinimumHeight(statusBar + getResources().getDimensionPixelSize(R.dimen.dp_40));


        mWidth = mChildHeight = getResources().getDimensionPixelSize(R.dimen.dp_40);

        //标题
        titleView = new TextView(getContext());

        titleView.setGravity(Gravity.CENTER);
        titleView.setTextSize(18f);
        titleView.setMaxLines(1);
        titleView.setEllipsize(TextUtils.TruncateAt.END);

        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.leftMargin = layoutParams.rightMargin = mWidth;
        addView(titleView, layoutParams);


        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TopNavigationWidgets, defStyleAttr, 0);

            boolean hideBack = a.getBoolean(R.styleable.TopNavigationWidgets_hide_back, false);
            int resId = a.getResourceId(R.styleable.TopNavigationWidgets_back_drawable, R.mipmap.back);
            if (!hideBack) {
                addLeftButton(resId);
            }
            String title = a.getString(R.styleable.TopNavigationWidgets_title);
            if (title != null && title.length() > 0) {
                setTitle(title);
            }
            titleView.setTextColor(a.getColor(R.styleable.TopNavigationWidgets_title_color, ContextCompat.getColor(context, R.color.white)));

            a.recycle();
        }
    }

    /**
     * 设置标题
     *
     * @param titleId 标题资源id
     */
    public void setTitle(@StringRes int titleId) {
        setTitle(getContext().getText(titleId));
    }

    /**
     * 设置标题
     *
     * @param title 标题字符串
     */
    public void setTitle(@NonNull CharSequence title) {
        titleView.setText(title);
    }


    public ImageButton leftBtn() {
        return leftBtn;
    }

    public TextView title() {
        return titleView;
    }

    /**
     * 添加返回按钮
     */
    private void addLeftButton(@DrawableRes int resId) {
        if (leftBtn == null) {
            leftBtn = new ImageButton(getContext());
            leftBtn.setBackground(null);
            leftBtn.setId(R.id.btn_back);
            leftBtn.setImageResource(resId);

            LayoutParams layoutParams = new LayoutParams(mWidth, mChildHeight);
            layoutParams.leftMargin = getResources().getDimensionPixelSize(R.dimen.dp_8);
            addView(leftBtn, layoutParams);
            leftBtn.setOnClickListener(new DeclaredOnClickListener(leftBtn));
        }
    }


    /**
     * 反射Activity的onClick方法
     */
    private class DeclaredOnClickListener implements OnClickListener {
        private final View mHostView;

        private Method mResolvedMethod;
        private Context mResolvedContext;

        DeclaredOnClickListener(@NonNull View hostView) {
            mHostView = hostView;
        }

        @Override
        public void onClick(@NonNull View v) {

            long nowTime = System.currentTimeMillis();
            if (nowTime - mLastClickTime < TIME_INTERVAL) {
                return;
            }
            mLastClickTime = nowTime;

            if (mResolvedMethod == null) {
                resolveMethod(mHostView.getContext());
            }

            try {
                mResolvedMethod.invoke(mResolvedContext, v);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void resolveMethod(@Nullable Context context) {
            while (context != null) {
                try {
                    if (!context.isRestricted()) {
                        Class<?> myClass = View.class;
                        mResolvedMethod = context.getClass().getMethod("onClick", myClass);
                        mResolvedContext = context;
                        return;
                    }
                } catch (NoSuchMethodException ignored) {

                }

                if (context instanceof ContextWrapper) {
                    context = ((ContextWrapper) context).getBaseContext();
                } else {
                    // Can't search up the hierarchy, null out and fail.
                    context = null;
                }
            }
        }
    }
}
