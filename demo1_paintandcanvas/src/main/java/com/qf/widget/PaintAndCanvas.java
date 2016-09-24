package com.qf.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.qf.demo1_paintandcanvas.R;

/**
 * Created by Ken on 2016/9/21.10:04
 */
public class PaintAndCanvas extends View {

    private Paint paint;

    public PaintAndCanvas(Context context) {
        this(context, null);
    }

    public PaintAndCanvas(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PaintAndCanvas(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);//抗锯齿
        paint.setColor(Color.GRAY);//设置画笔的颜色
        paint.setTextSize(100);//设置字体的大小，只在绘制字体时有效
        paint.setStyle(Paint.Style.STROKE);//设置画笔的样式，Paint.Style.STROKE中空，Paint.Style.FILL 填充
        paint.setStrokeWidth(5);//设置边缘的粗细，只有和Paint.Style.STROKE配合时才有用
        paint.setAlpha(255);//设置透明度 0 ~ 255
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //绘制圆形
        canvas.drawCircle(200, 200, 100, paint);

        //绘制问题 第二个参数第三个参数表示文本绘制坐标位置，以文本左边基线为基准
        canvas.drawText("Hello World!", 400, 100, paint);

        //绘制矩形
        paint.setColor(Color.BLUE);
        Rect rect = new Rect(200, 200, 600, 400);//用于接收int类型
        RectF rectf = new RectF(200, 200, 600, 400);//用于接收float类型
        canvas.drawRect(rectf, paint);

        //绘制椭圆
        canvas.drawOval(rectf, paint);

        //绘制线段
        paint.setColor(Color.RED);
//        canvas.drawLine(300, 300, 600, 600, paint);
        float[] floats = {600, 600, 500, 400, 500, 700, 500, 800};
        canvas.drawLines(floats, paint);

        //绘制圆角矩形
        paint.setColor(Color.BLACK);
        rectf.set(rectf.left + 50, rectf.top + 50, rectf.right - 50, rectf.bottom - 50);
        canvas.drawRoundRect(rectf, 50, 50, paint);

        //绘制路径
        Path path = new Path();
        path.moveTo(200, 1200);
        path.lineTo(400, 1000);
        path.quadTo(600, 1100, 800, 1000);//绘制贝塞尔曲线
        path.lineTo(1000, 1200);
        path.close();//调用该方法，自动闭合
        canvas.drawPath(path,paint);


        //绘制图片
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        //方式一
//        canvas.drawBitmap(bitmap, getWidth()/2, getHeight()/2, null);

        //方式二
//        Rect rect1 = new Rect(0, 0, bitmap.getWidth()/2, bitmap.getHeight()/2);//表示显示的图片的大小
//        Rect rect2 = new Rect(100, 500, 400, 800);//表示摆放的位置
//        canvas.drawBitmap(bitmap, rect1, rect2, null);

        /**
         * x缩放 x倾斜 x平移
         * y倾斜 y缩放 y平移
         * 0    0     0
         *
         *
         */
        Matrix matrix = new Matrix();
        //设置图片的缩放
        matrix.postScale(2, 2, bitmap.getWidth()/2, bitmap.getHeight()/2);
        //控制图片的旋转
        matrix.postRotate(90, bitmap.getWidth(), bitmap.getHeight());
        //控制图片的平移
        matrix.postTranslate(100, 100);
        //设置倾斜
        matrix.postSkew(-0.5f, 0f);
        matrix.setTranslate(300, 300);
        matrix.postRotate(30, 300 + bitmap.getWidth(), 300 + bitmap.getHeight());
        canvas.drawBitmap(bitmap, matrix, null);
    }
}
