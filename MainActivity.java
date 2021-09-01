package com.example.test;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.test.R;
import com.example.test.SingleTouchView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {
    private SingleTouchView drawView;
    private ImageButton currPaint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawView = (SingleTouchView) findViewById(R.id.drawing);
        LinearLayout paintLayout = (LinearLayout) findViewById(R.id.paint_colors);
        LinearLayout shapeLayout = (LinearLayout) findViewById(R.id.paint_shapes);
        currPaint = (ImageButton) paintLayout.getChildAt(0);
        paintLayout.setVisibility(View.INVISIBLE);
        shapeLayout.setVisibility(View.INVISIBLE);

        // 이미지버튼 추가
        ImageButton new_btn = findViewById(R.id.new_btn);
        ImageButton bg_btn = findViewById(R.id.bg_btn);
        ImageButton draw_btn = findViewById(R.id.draw_btn);
        ImageButton erase_btn = findViewById(R.id.erase_btn);
        ImageButton shape_btn = findViewById(R.id.shape_btn);
        ImageButton save_btn = findViewById(R.id.save_btn);
        ImageButton line = findViewById(R.id.line);
        ImageButton rect = findViewById(R.id.rect);
        ImageButton circle = findViewById(R.id.circle);
        ImageButton roundrect = findViewById(R.id.roundrect);

        Button.OnClickListener onClickListener = new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    // new 버튼 실행
                    case R.id.new_btn:
                        drawView.setButton(0);
                        paintLayout.setVisibility(View.INVISIBLE);
                        shapeLayout.setVisibility(View.INVISIBLE);
                        break;

                    // img 버튼 실행
                    case R.id.bg_btn:
                        drawView.setButton(2);
                        paintLayout.setVisibility(View.INVISIBLE);
                        shapeLayout.setVisibility(View.INVISIBLE);
                        break;

                    // draw 버튼 실행
                    case R.id.draw_btn:
                        drawView.setButton(3);
                        paintLayout.setVisibility(View.VISIBLE);
                        shapeLayout.setVisibility(View.INVISIBLE);
                        break;

                    // erase 버튼 실행
                    case R.id.erase_btn:
                        drawView.setButton(1);
                        paintLayout.setVisibility(View.INVISIBLE);
                        shapeLayout.setVisibility(View.INVISIBLE);
                        break;

                    // shape 버튼 실행
                    case R.id.shape_btn:
                        drawView.setButton(3);
                        paintLayout.setVisibility(View.INVISIBLE);
                        shapeLayout.setVisibility(View.VISIBLE);
                        break;

                    // save 버튼 실행
                    case R.id.save_btn:
                        drawView.setButton(3);
                        paintLayout.setVisibility(View.INVISIBLE);
                        shapeLayout.setVisibility(View.INVISIBLE);
                        break;

                    // line 버튼 실행
                    case R.id.line:
                        drawView.setButton(4);
                        paintLayout.setVisibility(View.INVISIBLE);
                        shapeLayout.setVisibility(View.VISIBLE);
                        break;

                    // rect 버튼 실행
                    case R.id.rect:
                        drawView.setButton(5);
                        paintLayout.setVisibility(View.INVISIBLE);
                        shapeLayout.setVisibility(View.VISIBLE);
                        break;

                    // circle 버튼 실행
                    case R.id.circle:
                        drawView.setButton(6);
                        paintLayout.setVisibility(View.INVISIBLE);
                        shapeLayout.setVisibility(View.VISIBLE);
                        break;

                    // roundrect 버튼 실행
                    case R.id.roundrect:
                        drawView.setButton(7);
                        paintLayout.setVisibility(View.INVISIBLE);
                        shapeLayout.setVisibility(View.VISIBLE);
                        break;

                    default:
                        break;
                }
            }
        };

        // 버튼 이벤트 지정
        new_btn.setOnClickListener(onClickListener);
        bg_btn.setOnClickListener(onClickListener);
        draw_btn.setOnClickListener(onClickListener);
        erase_btn.setOnClickListener(onClickListener);
        shape_btn.setOnClickListener(onClickListener);
        line.setOnClickListener(onClickListener);
        rect.setOnClickListener(onClickListener);
        circle.setOnClickListener(onClickListener);
        roundrect.setOnClickListener(onClickListener);
        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawView.buildDrawingCache();
                Bitmap bitmap = drawView.getDrawingCache();

                File filepath = Environment.getExternalStorageDirectory();
                File dir = new File(filepath.getAbsolutePath() + "/Demo/");
                dir.mkdir();
                File file = new File(dir, System.currentTimeMillis() + ".jpg");
                try {
                    OutputStream outputStream = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                Toast.makeText(getApplicationContext(), "저장 성공", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void clicked(View view) {
        if (view != currPaint) {
            String color = view.getTag().toString();
            drawView.setColor(color);
            currPaint = (ImageButton) view;
        }
    }
}