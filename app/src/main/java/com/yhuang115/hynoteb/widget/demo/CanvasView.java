package com.yhuang115.hynoteb.widget.demo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.yhuang115.hynoteb.R;

import java.lang.reflect.Method;

/**
 * Created by yhuang115 on 2018/2/10.
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class CanvasView extends View {
    private int mDefaultColor = Color.YELLOW;
    private final int START = 50;
    private final int END = 250;
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Path mPath = new Path(); // 初始化 Path 对象
    private String mContent;

    public CanvasView(Context context) {
        super(context);
        init();
    }

    public CanvasView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CanvasView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.FILL);
    }

    public void setDrawContent(String content) {
        mContent = content;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (TextUtils.isEmpty(mContent)) {
            return;
        }
        try {
            Class<?> clazz = Class.forName("com.yhuang115.hynoteb.widget.demo.CanvasView");
            Method method = clazz.getMethod(mContent, Canvas.class);
            method.setAccessible(true);
            method.invoke(this, canvas);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void drawColor(Canvas canvas) {
        canvas.drawColor(mDefaultColor);
    }

    public void drawText(Canvas canvas) {
        canvas.drawText("HELLO WORLD", START, START, mPaint);
    }

    public void drawOval(Canvas canvas) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            canvas.drawOval(50, 100, 100, 300, mPaint);
        }
    }

    public void drawLine(Canvas canvas) {
        canvas.drawLine(50, 50, 200, 200, mPaint);
    }

    public void drawLines(Canvas canvas) {
        float[] lines = {50, 50, 200, 200, 300, 300, 500, 500};
        canvas.drawLines(lines, mPaint);
    }

    public void drawRoundRect(Canvas canvas) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            canvas.drawRoundRect(START, START, END, END, 20, 50, mPaint);
        }
    }


    public void drawArc(Canvas canvas) {
        canvas.drawArc(START, START, END, END, 60, 150, true, mPaint);
        canvas.drawArc(300, 300, 600, 600, 30, 270, false, mPaint);
    }

    public void drawPath(Canvas canvas) {
        mPath.addArc(200, 200, 400, 400, -225, 225);
        mPath.arcTo(400, 200, 600, 400, -180, 225, false);
        mPath.lineTo(400, 542);
        canvas.drawPath(mPath, mPaint);
    }

    public void drawBitmap(Canvas canvas) {
        Bitmap bitmap = BitmapFactory.decodeResource(getContext().getResources(), R.mipmap.ic_launcher_round);
        canvas.drawBitmap(bitmap, 500, 500, mPaint);
    }

    public void drawCircle(Canvas canvas) {
        canvas.drawCircle(100, 100, START, mPaint);
    }

    public void drawRect(Canvas canvas) {
        canvas.drawRect(START, START, END, END, mPaint);
    }

    public void drawPoint(Canvas canvas) {
        //点的大小可以通过 paint.setStrokeWidth(width) 来设置
        mPaint.setStrokeWidth(20);
        mPaint.setStrokeCap(Paint.Cap.BUTT);
        canvas.drawPoint(START, START, mPaint);
    }

    public void drawPoints(Canvas canvas) {
        //点的大小可以通过 paint.setStrokeWidth(width) 来设置
        float[] floats = {START, START, 100, 100, 150, 150, 200, 200};
        mPaint.setStrokeWidth(20);
        mPaint.setStrokeCap(Paint.Cap.BUTT);
        canvas.drawPoints(floats, mPaint);
    }

}
