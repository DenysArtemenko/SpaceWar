package com.example.spacewar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

public class GameOver extends SpaceBody {


    public GameOver(Context context) {

        bitmapId = R.drawable.gameover;
        size = 10;
        x=0;
        y=5;
        init(context);
    }

    @Override
    void draw(Paint paint, Canvas canvas) {
        Bitmap cBitmap = BitmapFactory.decodeResource(c.getResources(), R.drawable.gameover);
        bitmap = Bitmap.createScaledBitmap(
                cBitmap, (int)(size+20*GameView.unitW),(int)(size*GameView.unitH),false);
        cBitmap.recycle();
        canvas.drawBitmap(bitmap, x*GameView.unitW, y*GameView.unitH, paint);
    }

}
