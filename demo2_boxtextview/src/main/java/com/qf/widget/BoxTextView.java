package com.qf.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * Created by Ken on 2016/9/21.14:09
 * 带边框的TextView
 */
public class BoxTextView extends TextView {

    private Paint paint, cPaint;

    private int cx, cy;//圆心坐标
    private int radius;//半径

    private boolean isTouch = false;//是否触摸

    public BoxTextView(Context context) {
        this(context, null);
    }

    public BoxTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BoxTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);

        cPaint = new Paint();
        cPaint.setAntiAlias(true);
        cPaint.setColor(Color.parseColor("#44888888"));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //绘制圆形1
        if(isTouch) {
            if (cx != 0 && cy != 0) {
                canvas.drawCircle(cx, cy, radius, cPaint);
            }
        } else {
            //设置背景为空
            canvas.drawARGB(255, 255, 255, 255);
        }

        //绘制边框
        canvas.drawRect(0, 0, getWidth(), getHeight(), paint);

        //该方法不能删除，用于使用父类提供的绘制功能
        super.onDraw(canvas);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                isTouch = true;

                cx = (int) event.getX();
                cy = (int) event.getY();

                drawC();
                break;
            case MotionEvent.ACTION_MOVE:
                int movex = (int) event.getX();
                int movey = (int) event.getY();

                int mx = movex - cx;
                int my = movey - cy;

                //设置TextView的位置
                this.layout(getLeft() + mx, getTop() + my, getRight() + mx, getBottom() + my);

                break;
            case MotionEvent.ACTION_UP:
                isTouch = false;
                radius = 0;

                invalidate();
                break;
        }
        return true;
    }

    private void drawC(){
        new Thread(){
            @Override
            public void run() {
                while(isTouch){

                    if(radius >= Math.sqrt(getWidth() * getWidth() + getHeight() * getHeight())){
                        break;
                    }

                    radius += 10;

                    //重绘
                    postInvalidate();

                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }
}
