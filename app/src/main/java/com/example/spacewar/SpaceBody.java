package com.example.spacewar;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

public class SpaceBody {
    Context c;
    protected float x;
    protected float y;
    protected float size; // размер
    protected float speed; // скорость
    protected int bitmapId; // id картинки
    protected Bitmap bitmap; // картинка


    void init(Context context) { // сжимаем картинку

        c=context;
        Bitmap cBitmap = BitmapFactory.decodeResource(c.getResources(), bitmapId);
        bitmap = Bitmap.createScaledBitmap(
                cBitmap, (int)(size*GameView.unitW),(int)(size*GameView.unitH),false);
        cBitmap.recycle();
    }

    void update(){ // тут вычисляются новые координаты

    }

     void draw(Paint paint, Canvas canvas) { // рисуем картинку
        canvas.drawBitmap(bitmap, x*GameView.unitW, y*GameView.unitH, paint);
    }


}
