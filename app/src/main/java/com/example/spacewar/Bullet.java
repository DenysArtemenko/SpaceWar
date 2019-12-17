package com.example.spacewar;

import android.content.Context;
import android.graphics.Canvas;

public class Bullet extends SpaceBody {



    public Bullet(Context context, Ship ship) {
        size = 1;
        speed = (float) 0.2;
        bitmapId = R.drawable.bullet;
        y = 16;
        x = ship.x+(float) 0.5;
        init(context);
    }

    @Override
    void update() {
        y -= speed;
    }

    public boolean isCollisionWithAsteroid(float asteroidX, float asteroidY, float asteroidSize) {
        return !(((x + size) < asteroidX) || (x > (asteroidX + asteroidSize)) || ((y + size) < asteroidY) ||
                (y > (asteroidY + asteroidSize)));

    }
}
