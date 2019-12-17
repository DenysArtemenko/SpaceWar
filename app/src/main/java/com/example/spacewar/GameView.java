package com.example.spacewar;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.SystemClock;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;


import java.util.ConcurrentModificationException;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameView extends SurfaceView implements Runnable {

    public static int maxX = 20;
    public static int maxY = 20;
    public static float unitW = 0;
    public static float unitH = 0;
    public static SQLiteOpenHelper mDbHelper;
    private boolean firstTime = true;
    private boolean gameRunning = true;
    private GameOver gameOver;
    private EnemyShip enemyShip;
    private Ship ship;
    private Asteroid asteroid;
    private Explosion explosion;
    private Thread gameThread = null;
    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;
    private CopyOnWriteArrayList<Asteroid> asteroids = new CopyOnWriteArrayList <>();
    private CopyOnWriteArrayList<Bullet> bullets = new CopyOnWriteArrayList<>();
    private CopyOnWriteArrayList<Explosion> explosions = new CopyOnWriteArrayList<>();
    private CopyOnWriteArrayList<EnemyShip> enemyShips = new CopyOnWriteArrayList<>();
    private CopyOnWriteArrayList<EnemyBullet> enemyBullets = new CopyOnWriteArrayList<>();
    private final int ASTEROID_INTERVAL=50; // // время через которое появляются астероиды (в итерациях)
    private final int BULLET_INTERVAL=10;
    private final int ENEMY_INTERVAL=200;
    private final int ENEMY_BULLET_INTERVAL=40;
    private int currentTime = 0;
    private int bulletTime = 0;
    private int enemyTime = 0;
    private int countAsteroidOrEnemyBulletShipCollision = 0;
    private int countBulletEnemyShipCollision = 0;
    private boolean isStop=false;
    private int countertodeath=10;
 Context context;
 String uname;

    public GameView(Context context, String u) {
       super(context);
       uname=GameMain.editText.getText().toString();
       this.context=context;
       surfaceHolder = getHolder();
       paint = new Paint();
       gameThread = new Thread(this);
       gameThread.start();

    }

    @Override
    public void run() throws ConcurrentModificationException {
        while (gameRunning   ){




            if(isStop)
            {
                gameOver = new GameOver(getContext());
                countertodeath--;

            if (countertodeath<0)
            {
                gameRunning=false;

                MainActivity.mChronometer.stop();
                long elapsedMillis = SystemClock.elapsedRealtime() - MainActivity.mChronometer.getBase();
                 mDbHelper = new MyDatabaseHelper(context);

                SQLiteDatabase dbWrite = mDbHelper.getWritableDatabase();
              //  SQLiteDatabase dbRead = mDbHelper.getReadableDatabase();

              //  String nickName=etNN.getText().toString();
               // String time=etT.getText().toString();

                ContentValues values = new ContentValues();
                values.put(MyDatabaseContract .TableRecords.COLUMN_NAME_NICKNAME, uname);
                values.put(MyDatabaseContract .TableRecords.COLUMN_NAME_TIME, elapsedMillis/1000);


                long newRowId;


                newRowId = dbWrite.insert(
                        MyDatabaseContract.TableRecords.TABLE_NAME,
                        null,
                        values);

            }
            }
            update();
            draw();
            if (MainActivity.isFirePressed) {
                if (ship!=null) {
                    checkIfNewBullet();
                }
            }
            try {
                checkCollision();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
            checkIfNewAsteroid();
            checkIfBulletCollision();
            checkIfNewEnemyShip();
            checkIfEnemyShipCollision();
            checkIfEnemyBulletCollision();
            checkIfBulletCollisionEnemyBullet();
            removeSpaceBodyElements();
            control();
        }
    }
    private void update(){
        if(!firstTime){
            if (ship!=null) {
                ship.update();
            }
            for (Asteroid asteroid : asteroids){
                asteroid.update();
            }
            for (Bullet bullet : bullets){
                bullet.update();
            }
            for (Explosion explosion : explosions) {
                explosion.update();

            }
            for (EnemyShip enemyShip : enemyShips) {
                enemyShip.update();
                checkIfNewEnemyBullet(enemyShip);
                checkIfBulletCollisionEnemyShip(enemyShip);

            }
            for (EnemyBullet enemyBullet : enemyBullets){
                enemyBullet.update();
            }
        }
    }

    private void draw(){
        if (surfaceHolder.getSurface().isValid())
        {

            if (firstTime){
                firstTime = false;
                unitW = surfaceHolder.getSurfaceFrame().width()/maxX;
                unitH = surfaceHolder.getSurfaceFrame().height()/maxY;

                    ship = new Ship(getContext());

            }

            canvas = surfaceHolder.lockCanvas(); // закрывем canvas
            canvas.drawColor(Color.BLACK);

            for (Asteroid asteroid : asteroids){
                asteroid.draw(paint,canvas);
            }

                for (Bullet bullet : bullets){
                    bullet.draw(paint,canvas);
                }
                for (Explosion explosion : explosions){
                    explosion.drawA(paint,canvas);

                }
                for (EnemyShip enemyShip : enemyShips){
                    enemyShip.draw(paint,canvas);

                }
            for (EnemyBullet enemyBullet : enemyBullets){
                enemyBullet.draw(paint,canvas);
            }

            if(ship!=null)
            {ship.draw(paint, canvas);}
            if(gameOver!=null){
                gameOver.draw(paint,canvas);
            }

                surfaceHolder.unlockCanvasAndPost(canvas); // открываем canvas

        }

    }

    private void control(){

        try {
            gameThread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void checkCollision()  {

        for (Asteroid asteroid : asteroids) { // проверка на столкновение корабля с астероидами

            if (ship.isCollisionWithAsteroid(asteroid.x, asteroid.y, asteroid.size)) {
                asteroids.remove(asteroid);
                explosion = new Explosion(getContext(), asteroid);
                explosions.add(explosion);
                countAsteroidOrEnemyBulletShipCollision++;
                if (countAsteroidOrEnemyBulletShipCollision == 3){
                    asteroids.remove(asteroid);
                    explosion = new Explosion(getContext(), asteroid);
                    explosions.add(explosion);

                    explosion = new Explosion(getContext(), ship);
                    explosions.add(explosion);
                    ship=null;
                    isStop = true;
                }

            }
        }
        for (EnemyShip enemyShip : enemyShips){// проверка на столкновение корабля с вражеским кораблем

            if (ship.isCollisionWithAsteroid(enemyShip.x,enemyShip.y,enemyShip.size)){
                enemyShips.remove(enemyShip);
                explosion = new Explosion(getContext(), enemyShip);
                explosions.add(explosion);

                explosion = new Explosion(getContext(), ship);
                explosions.add(explosion);
                ship=null;
                isStop = true;
            }
        }
        for (EnemyBullet enemyBullet : enemyBullets){ // проверка на столкновение корабля свражескими пулями
            if (ship.isCollisionWithAsteroid(enemyBullet.x, enemyBullet.y, enemyBullet.size)) {
                enemyBullets.remove(enemyBullet);
                explosion = new Explosion(getContext(), enemyBullet);
                explosions.add(explosion);
                countAsteroidOrEnemyBulletShipCollision++;
                if (countAsteroidOrEnemyBulletShipCollision == 3){
                    enemyBullets.remove(enemyBullet);
                    explosion = new Explosion(getContext(), enemyBullet);
                    explosions.add(explosion);

                    explosion = new Explosion(getContext(), ship);
                    explosions.add(explosion);
                    ship=null;
                    isStop = true;
                }
            }
        }


    }

    private void checkIfNewAsteroid(){ // каждые 50 добавляем новый астероид
        if (currentTime >= ASTEROID_INTERVAL){
            asteroid = new Asteroid(getContext());
            asteroids.add(asteroid);
            currentTime = 0;
        }else {
            currentTime++;
        }

    }

    private void checkIfNewBullet() { // создаем пули при зажатии кнопки с интервалом 10
       if (MainActivity.isFirePressed && bulletTime>=BULLET_INTERVAL )
        {
            Bullet bullet = new Bullet(getContext(), ship);
            bullets.add(bullet);
            bulletTime = 0;
        }else {
           bulletTime++;
       }
    }
    private void checkIfBulletCollision(){ // удаляем пулю и астероид которые столкнулись и создаем взрыв
        for (Bullet bullet : bullets) {
            for (Asteroid asteroid : asteroids) {
                if (bullet.isCollisionWithAsteroid(asteroid.x, asteroid.y, asteroid.size)) {
                    explosion = new Explosion(getContext(),asteroid);
                    explosions.add(explosion);
                    asteroids.remove(asteroid);
                    bullets.remove(bullet);

                }
            }
        }
    }

    private void checkIfNewEnemyShip() { // каждые 200 добавляем новый вражеский корабль
        if (enemyTime >= ENEMY_INTERVAL) {
            enemyShip = new EnemyShip(getContext());
            countBulletEnemyShipCollision=0;
            enemyShips.add(enemyShip);

            enemyTime = 0;
        } else {
            enemyTime++;
        }
    }
    private void checkIfEnemyShipCollision(){ // удаляем пулю и астероид которые столкнулись и создаем взрыв
        for (EnemyShip enemyShip : enemyShips) {
            for (Asteroid asteroid : asteroids) {
                if (enemyShip.isCollisionWithAsteroid(asteroid.x, asteroid.y, asteroid.size)) {
                    asteroids.remove(asteroid);
                    explosion = new Explosion(getContext(),asteroid);
                    explosions.add(explosion);


                }
            }
        }
    }
    private void checkIfNewEnemyBullet(EnemyShip es) { // создаем пули при создании вражеского корабля с интервалом 40

        if (es.counter>=ENEMY_BULLET_INTERVAL )
        {
           EnemyBullet enemyBullet = new EnemyBullet(getContext(), es);
            enemyBullets.add(enemyBullet);
            es.counter = 0;
        }else {

        }
    }
    private void checkIfEnemyBulletCollision(){ // удаляем пулю и астероид которые столкнулись и создаем взрыв
        for (EnemyBullet enemyBullet : enemyBullets) {
            for (Asteroid asteroid : asteroids) {
                if (enemyBullet.isCollisionWithAsteroid(asteroid.x, asteroid.y, asteroid.size)) {
                    asteroids.remove(asteroid);
                    enemyBullets.remove(enemyBullet);
                    explosion = new Explosion(getContext(),asteroid);
                    explosions.add(explosion);


                }
            }
        }
    }
    private void checkIfBulletCollisionEnemyShip(EnemyShip es){ // удаляем пулю  которая столкнулась и создаем взрыв

        for (Bullet bullet : bullets) {
            for (EnemyShip enemyShip : enemyShips) {
                if (bullet.isCollisionWithAsteroid(enemyShip.x, enemyShip.y, enemyShip.size)) {
                    explosion = new Explosion(getContext(),bullet);
                    explosions.add(explosion);
                    bullets.remove(bullet);
                    countBulletEnemyShipCollision++;
                    if (countBulletEnemyShipCollision==3){ // если пули попали по вражускому кораблю 3 раза он взрывается
                        explosion = new Explosion(getContext(),enemyShip);
                        explosions.add(explosion);
                        enemyShips.remove(enemyShip);
                        bullets.remove(bullet);
                    }
                }
            }
        }
    }
    private void checkIfBulletCollisionEnemyBullet(){ // проверка попадания пуль друг с другом
        for (Bullet bullet : bullets){
            for (EnemyBullet enemyBullet : enemyBullets){
                if (bullet.isCollisionWithAsteroid(enemyBullet.x,enemyBullet.y,enemyBullet.size)){
                    explosion = new Explosion(getContext(),enemyBullet);
                    explosions.add(explosion);
                    bullets.remove(bullet);
                    enemyBullets.remove(enemyBullet);
                }
            }
        }
    }



    private void removeSpaceBodyElements(){ //удаляю космические тела если они вышли за экран
        for (EnemyShip enemyShip : enemyShips) {
            if (enemyShip.y > maxY || enemyShip.x > maxX) {
                enemyShips.remove(enemyShip);
            }
        }
        for (Asteroid asteroid : asteroids) {
            if (asteroid.x > maxX || asteroid.y > maxY) {
                asteroids.remove(asteroid);
            }
        }
        for (Bullet bullet : bullets){
            if (bullet.y<0||bullet.x>maxX){
                bullets.remove(bullet);
            }
        }
        for (EnemyBullet enemyBullet : enemyBullets){
            if (enemyBullet.y>maxY||enemyBullet.x>maxX){
                enemyBullets.remove(enemyBullet);
            }
        }
    }


}
