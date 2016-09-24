package com.qf.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.qf.demo3_mytextview.R;

/**
 * Created by Ken on 2016/9/21.14:47
 * 自定义的TextView
 */
public class MyTextView extends View {

    private String cotent = "Hello Java! Hello Android! Hello PHP!";
    private float textSize;
    private int textColor;
    private Paint paint;

    public MyTextView(Context context) {
        this(context, null);
    }

    public MyTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paresAttr(attrs);
        init();
    }


    /**
     * 初始化
     */
    private void init() {
        paint = new Paint();
        paint.setTextSize(textSize);
        paint.setColor(textColor);
        paint.setAntiAlias(true);
    }


    /**
     * 解析自定义属性
     * @param attrs
     */
    private void paresAttr(AttributeSet attrs) {
        TypedArray typedArray = getResources().obtainAttributes(attrs, R.styleable.MyTextView);
        cotent = typedArray.getString(R.styleable.MyTextView_text);
        textSize = typedArray.getDimension(R.styleable.MyTextView_textSize, 30);
        textColor = typedArray.getColor(R.styleable.MyTextView_textColor, Color.BLACK);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawText(cotent, getPaddingLeft(), getPaddingTop()-paint.ascent(), paint);
    }

    /**
     * 测量方法
     * @param widthMeasureSpec 控件宽度的空间值 -> 系统推荐的最大宽度，控件宽度的模式
     * @param heightMeasureSpec 控件高度的空间值 -> 系统推荐的最大高度，控件高度的模式
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int wSize = MeasureSpec.getSize(widthMeasureSpec);//获得系统推荐的最大宽度
        int wMode = MeasureSpec.getMode(widthMeasureSpec);//获得控件宽度的模式

        int hSize = MeasureSpec.getSize(heightMeasureSpec);//获得系统推荐的最大高度
        int hMode = MeasureSpec.getMode(heightMeasureSpec);//获得控件高度的模式

        int width = 0;
        int height = 0;

        //处理宽度
        switch (wMode){
            case MeasureSpec.EXACTLY:
                //表示精确的模式，当控件宽度在值为match_parent或者一个准确的值时，系统会返回这个模式
                //如果是这个模式，我们只需要把宽度设为系统推荐的最大值
                width = wSize;
                break;
            case MeasureSpec.AT_MOST:
                //表示尽可能多的模式，当控件宽度在值为warp_content时，系统会返回这个模式
                //如果是该模式，则需要测量宽度
                width = (int) paint.measureText(cotent) + getPaddingLeft() + getPaddingRight();//获得content文本的宽度
                break;
            case MeasureSpec.UNSPECIFIED:
                //控件想要多宽就给多宽的模式，这种模式通常出现在scrollview这种对子控件没有宽高限制的情况
                break;
        }

        //处理高度
        switch (hMode){
            case MeasureSpec.EXACTLY:
                //表示精确的模式，当控件宽度在值为match_parent或者一个准确的值时，系统会返回这个模式
                //如果是这个模式，我们只需要把宽度设为系统推荐的最大值
                height = hSize;
                break;
            case MeasureSpec.AT_MOST:
                //表示尽可能多的模式，当控件宽度在值为warp_content时，系统会返回这个模式
                //如果是该模式，则需要测量宽度
                height = (int) (paint.descent() - paint.ascent()) + getPaddingTop() + getPaddingBottom();//获得文本的高度
                break;
            case MeasureSpec.UNSPECIFIED:
                //控件想要多宽就给多宽的模式，这种模式通常出现在scrollview这种对子控件没有宽高限制的情况
                break;
        }

        //保存测量的宽高值
        this.setMeasuredDimension(width, height);
    }
}
