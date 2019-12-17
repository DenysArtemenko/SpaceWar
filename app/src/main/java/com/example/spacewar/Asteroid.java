package com.example.spacewar;

import android.content.Context;

import java.util.Random;

public class Asteroid extends SpaceBody {
    private int radius = 1;
    private float minSpeed = (float) 0.1;
    private float maxSpeed = (float) 0.3;

    public  Asteroid(Context context){
        Random random = new Random();

        bitmapId = R.drawable.asteroid;
        y=-2;
        x = random.nextInt(GameView.maxX)-radius;
        size = radius*2;
        speed = minSpeed + (maxSpeed - minSpeed) * random.nextFloat();

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
