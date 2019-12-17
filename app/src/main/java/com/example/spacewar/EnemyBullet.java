package com.example.spacewar;

import android.content.Context;

public class EnemyBullet extends SpaceBody {
    public EnemyBullet(Context context, EnemyShip enemyShip) {
        size = 1;
        speed = (float) 0.2;
        bitmapId = R.drawable.enemybullet;
        y = enemyShip.y+3;
        x = enemyShip.x+1+(float) 0.5;
        init(context);
    }

    @Override
    void update() {
        y += speed;
    }

    public boolean isCollisionWithAsteroid(float asteroidX, float asteroidY, float asteroidSize) {
        return !(((x + size) < asteroidX) || (x > (asteroidX + asteroidSize)) || ((y + size) < asteroidY) ||
                (y > (asteroidY + asteroidSize)));

    }
}
