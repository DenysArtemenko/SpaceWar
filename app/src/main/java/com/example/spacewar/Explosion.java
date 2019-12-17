package com.example.spacewar;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;



public class Explosion extends SpaceBody {
    int counter=0;


    public Explosion(Context context, Asteroid asteroid){
        size = 2;
        speed=(float)0.1;
        bitmapId=R.drawable.big0;
        x=asteroid.x;
        y=asteroid.y;

        init(context);

    }
    public Explosion(Context context, Bullet bullet){
        size = (float) 1.5;
        speed=(float)0.1;
        bitmapId=R.drawable.big0;
        x=bullet.x;
        y=bullet.y-2;

        init(context);

    }
    public Explosion(Context context, EnemyBullet enemyBullet){
        size = (float) 1.5;
        speed=(float)0.1;
        bitmapId=R.drawable.big0;
        x=enemyBullet.x;
        y=enemyBullet.y+1;

        init(context);

    }
    public Explosion(Context context, EnemyShip enemyShip){
        size = 4;
        speed=(float)0.1;
        bitmapId=R.drawable.big0;
        x=enemyShip.x;
        y=enemyShip.y;

        init(context);

    }
    public Explosion(Context context, Ship ship){
        size = 3;
        speed=(float)0.1;
        bitmapId=R.drawable.big0;
        x=ship.x;
        y=ship.y;

        init(context);

    }


    void update() {

        counter++;
        y+=speed;


    }

    void drawA(Paint paint, Canvas canvas) {
        // рисуем картинку
        if(counter<9) {
            Bitmap cBitmap;
            switch(counter) {
                case 0:
                 cBitmap = BitmapFactory.decodeResource(c.getResources(), R.drawable.big0);
                bitmap = Bitmap.createScaledBitmap(
                        cBitmap, (int) (size * GameView.unitW), (int) (size * GameView.unitH), false);
                cBitmap.recycle();
                canvas.drawBitmap(bitmap, x * GameView.unitW, y * GameView.unitH, paint);

                break;
                case 1:
                    cBitmap = BitmapFactory.decodeResource(c.getResources(), R.drawable.big1);
                    bitmap = Bitmap.createScaledBitmap(
                            cBitmap, (int) (size * GameView.unitW), (int) (size * GameView.unitH), false);
                    cBitmap.recycle();
                    canvas.drawBitmap(bitmap, x * GameView.unitW, y * GameView.unitH, paint);

                    break;
                case 2:
                    cBitmap = BitmapFactory.decodeResource(c.getResources(), R.drawable.big2);
                    bitmap = Bitmap.createScaledBitmap(
                            cBitmap, (int) (size * GameView.unitW), (int) (size * GameView.unitH), false);
                    cBitmap.recycle();
                    canvas.drawBitmap(bitmap, x * GameView.unitW, y * GameView.unitH, paint);

                    break;
                case 3:
                    cBitmap = BitmapFactory.decodeResource(c.getResources(), R.drawable.big3);
                    bitmap = Bitmap.createScaledBitmap(
                            cBitmap, (int) (size * GameView.unitW), (int) (size * GameView.unitH), false);
                    cBitmap.recycle();
                    canvas.drawBitmap(bitmap, x * GameView.unitW, y * GameView.unitH, paint);

                    break;
                case 4:
                    cBitmap = BitmapFactory.decodeResource(c.getResources(), R.drawable.big4);
                    bitmap = Bitmap.createScaledBitmap(
                            cBitmap, (int) (size * GameView.unitW), (int) (size * GameView.unitH), false);
                    cBitmap.recycle();
                    canvas.drawBitmap(bitmap, x * GameView.unitW, y * GameView.unitH, paint);

                    break;
                case 5:
                    cBitmap = BitmapFactory.decodeResource(c.getResources(), R.drawable.big5);
                    bitmap = Bitmap.createScaledBitmap(
                            cBitmap, (int) (size * GameView.unitW), (int) (size * GameView.unitH), false);
                    cBitmap.recycle();
                    canvas.drawBitmap(bitmap, x * GameView.unitW, y * GameView.unitH, paint);

                    break;
                case 6:
                    cBitmap = BitmapFactory.decodeResource(c.getResources(), R.drawable.big6);
                    bitmap = Bitmap.createScaledBitmap(
                            cBitmap, (int) (size * GameView.unitW), (int) (size * GameView.unitH), false);
                    cBitmap.recycle();
                    canvas.drawBitmap(bitmap, x * GameView.unitW, y * GameView.unitH, paint);

                    break;
                case 7:
                    cBitmap = BitmapFactory.decodeResource(c.getResources(), R.drawable.big7);
                    bitmap = Bitmap.createScaledBitmap(
                            cBitmap, (int) (size * GameView.unitW), (int) (size * GameView.unitH), false);
                    cBitmap.recycle();
                    canvas.drawBitmap(bitmap, x * GameView.unitW, y * GameView.unitH, paint);

                    break;
                case 8:
                    cBitmap = BitmapFactory.decodeResource(c.getResources(), R.drawable.big8);
                    bitmap = Bitmap.createScaledBitmap(
                            cBitmap, (int) (size * GameView.unitW), (int) (size * GameView.unitH), false);
                    cBitmap.recycle();
                    canvas.drawBitmap(bitmap, x * GameView.unitW, y * GameView.unitH, paint);

                    break;
                case 9:
                    cBitmap = BitmapFactory.decodeResource(c.getResources(), R.drawable.big9);
                    bitmap = Bitmap.createScaledBitmap(
                            cBitmap, (int) (size * GameView.unitW), (int) (size * GameView.unitH), false);
                    cBitmap.recycle();
                    canvas.drawBitmap(bitmap, x * GameView.unitW, y * GameView.unitH, paint);

                    break;
                case 10:
                    cBitmap = BitmapFactory.decodeResource(c.getResources(), R.drawable.big10);
                    bitmap = Bitmap.createScaledBitmap(
                            cBitmap, (int) (size * GameView.unitW), (int) (size * GameView.unitH), false);
                    cBitmap.recycle();
                    canvas.drawBitmap(bitmap, x * GameView.unitW, y * GameView.unitH, paint);

                    break;
            }
            counter++;
        }
    }
}
