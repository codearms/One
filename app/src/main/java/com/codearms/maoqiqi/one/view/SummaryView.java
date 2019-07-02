package com.codearms.maoqiqi.one.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Transformation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.codearms.maoqiqi.one.R;

/**
 * Link: https://github.com/maoqiqi/AndroidUtils
 * Author: fengqi.mao.march@gmail.com
 * Date: 2019-07-01 14:23
 */
public class SummaryView extends FrameLayout implements View.OnClickListener {

    // TextView 默认最大展示行数
    private static final int MAX_LINE = 3;
    // 动画持续时间
    private static final long DURATION_MILLIS = 350L;

    private ImageView ivSummary;
    private TextView tvSummaryContent;

    private String title;

    private int maxLine = MAX_LINE;
    private boolean summaryExpand;

    public SummaryView(Context context) {
        this(context, null);
    }

    public SummaryView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SummaryView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        // 加载自定义的属性
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SummaryView);
        if (a.getString(R.styleable.SummaryView_title) != null)
            title = a.getString(R.styleable.SummaryView_title);
        // 回收资源
        a.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        View view = View.inflate(getContext(), R.layout.summary_view, this);
        TextView tvSummary = view.findViewById(R.id.tv_summary);
        ivSummary = view.findViewById(R.id.iv_summary);
        tvSummaryContent = view.findViewById(R.id.tv_summary_content);

        tvSummary.setText(title);
        ivSummary.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        summaryExpand = !summaryExpand;
        // 清除动画效果
        ivSummary.clearAnimation();
        tvSummaryContent.clearAnimation();
        int differ;// 差值
        int startValue = tvSummaryContent.getHeight(); // 起始高度

        if (summaryExpand) {
            // 展开动画 --> 从起始高度增长至实际高度
            differ = tvSummaryContent.getLineHeight() * tvSummaryContent.getLineCount() - startValue;
            Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.summary_view_1);
            ivSummary.startAnimation(animation);
        } else {
            // 折叠动画 --> 从实际高度缩回起始高度
            differ = tvSummaryContent.getLineHeight() * maxLine - startValue;
            Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.summary_view_2);
            ivSummary.startAnimation(animation);
        }

        Animation animation = new Animation() {
            // 根据ImageView旋转动画的百分比来显示TextView高度,达到动画效果
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                super.applyTransformation(interpolatedTime, t);
                tvSummaryContent.setHeight((int) (startValue + differ * interpolatedTime));
            }
        };
        animation.setDuration(DURATION_MILLIS);
        tvSummaryContent.startAnimation(animation);
    }

    public void setMaxLine(int maxLine) {
        this.maxLine = maxLine;
    }

    public void setText(String text) {
        if (text.equals("")) {
            tvSummaryContent.setVisibility(View.GONE);
        } else {
            tvSummaryContent.setVisibility(View.VISIBLE);
            tvSummaryContent.setText(text);
            tvSummaryContent.post(() -> {
                if (!summaryExpand) {
                    int line = tvSummaryContent.getLineCount() > maxLine ? maxLine : tvSummaryContent.getLineCount();
                    int contentHeight = tvSummaryContent.getLineHeight() * line;
                    tvSummaryContent.setHeight(contentHeight);
                    ivSummary.setVisibility(tvSummaryContent.getLineCount() > maxLine ? View.VISIBLE : View.GONE);
                }
            });
        }
    }
}