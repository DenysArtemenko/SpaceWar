package com.example.spacewar;

import android.content.Context;

import java.util.Random;

public class EnemyShip extends SpaceBody {
    Ship ship;
    int counter=0;

    public EnemyShip(Context context) {

        size = 4;
        bitmapId = R.drawable.enemyship;
        y=-2;
        x = x;
        speed = (float) 0.1;
        init(context);
    }

    @Override
    void update() {
            y += speed;
            x += speed;
            counter++;

    }
    public boolean isCollisionWithAsteroid(float asteroidX, float asteroidY, float asteroidSize) {
        return !(((x + size) < asteroidX) || (x > (asteroidX + asteroidSize)) || ((y + size) < asteroidY) ||
                (y > (asteroidY + asteroidSize)));

    }
}
