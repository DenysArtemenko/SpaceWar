package com.example.spacewar;

import android.content.Context;

public class Ship extends SpaceBody {
    public Ship(Context context) {
        bitmapId = R.drawable.ship;
        size = 2;
        x=9;
        y=GameView.maxY - size -1;
        speed = (float) 0.2;

        init(context);
    }
    @Override
    public void update(){ // перемещаем корабль в зависимости от нажатой кнопки
        if (MainActivity.isLeftPressed && x >= 0){
            x -= speed;
        }
        if (MainActivity.isRightPressed && x <= GameView.maxX-2){
            x += speed;
        }
    }
    public boolean isCollisionWithAsteroid(float asteroidX, float asteroidY, float asteroidSize) {
        return !(((x + size) < asteroidX) || (x > (asteroidX + asteroidSize)) || ((y + size) < asteroidY) ||
                (y > (asteroidY + asteroidSize)));
    }
}
