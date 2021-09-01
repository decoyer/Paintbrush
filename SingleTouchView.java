package com.example.test;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class SingleTouchView extends View {
    private Paint paint = new Paint();
    private Path path = new Path();
    private int paintColor = 0xFF000000;
    private Paint canvasPaint;
    private Canvas drawCanvas;
    private Bitmap canvasBitmap;
    private float x1, y1, x2, y2;
    private int status = -1;

    public SingleTouchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(15f);
        paint.setColor(paintColor);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        drawCanvas = new Canvas(canvasBitmap);
        canvasPaint = new Paint(Paint.DITHER_FLAG);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(canvasBitmap, 0, 0, canvasPaint);
        switch (status) {
            case 4:
                canvas.drawLine(x1, y1, x2, y2, paint);
                break;
            case 5:
                canvas.drawRect(x1, y1, x2, y2, paint);
                break;
            case 6:
                canvas.drawOval(new RectF(x1, y1, x2, y2), paint);
                break;
            case 7:
                canvas.drawRoundRect(new RectF(x1, y1, x2, y2), 50, 50, paint);
                break;
            default:
                canvas.drawPath(path, paint);
                break;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                y1 = event.getY();
                x2 = event.getX();
                y2 = event.getY();
                path.moveTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_MOVE:
                x2 = event.getX();
                y2 = event.getY();
                path.lineTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_UP:
                switch (status) {
                    case 4:
                        drawCanvas.drawLine(x1, y1, x2, y2, paint);
                        break;
                    case 5:
                        drawCanvas.drawRect(x1, y1, x2, y2, paint);
                        break;
                    case 6:
                        drawCanvas.drawOval(new RectF(x1, y1, x2, y2), paint);
                        break;
                    case 7:
                        drawCanvas.drawRoundRect(new RectF(x1, y1, x2, y2), 50, 50, paint);
                        break;
                    default:
                        drawCanvas.drawPath(path, paint);
                        break;
                }
                path.reset();
                break;
            default:
                return false;
        }
        invalidate();
        return true;
    }

    public void setColor(String newColor) {
        invalidate();
        paintColor = Color.parseColor(newColor);
        paint.setColor(paintColor);
    }

    public void onImg(Canvas canvas) {
        Paint paint = new Paint();
        Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.bg);
        canvas.drawBitmap(b, 0, 0, null);
    }

    public void setButton(int button) {
        this.status = button;
        if (button == 1) {
            paint.setStrokeWidth(30f);
            paint.setColor(0xFFFFFFFF);
        } else {
            paint.setStrokeWidth(15f);
            paint.setColor(paintColor);
            if (button == 0) {
                canvasBitmap.eraseColor(0xFFFFFFFF);
                invalidate();
            } else if (button == 2) {
                onImg(drawCanvas);
            }
        }
    }
}