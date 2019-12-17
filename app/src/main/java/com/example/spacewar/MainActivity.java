package com.example.spacewar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {
    public static boolean isLeftPressed = false; // нажата левая кнопка
    public static boolean isRightPressed = false; // нажата правая кнопка
    public static boolean isFirePressed = false; // нажата кнопка огонь
    public static final String KEY_MSG2="com.example.myspacewar.denys.msg2";

    public static Chronometer mChronometer;
    public static final String APP_PREFERENCES = "mysettings";
    private SharedPreferences mSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);


        GameView gameView = new GameView(this, GameMain.editText.getText().toString()); // создаём gameView
        LinearLayout gameLayout = (LinearLayout) findViewById(R.id.gameLayout); // находим gameLayout
        gameLayout.addView(gameView); // и добавляем в него gameView
        Button leftButton = (Button) findViewById(R.id.leftButton); // находим кнопки
        Button rightButton = (Button) findViewById(R.id.rightButton);
        Button fireButton = (Button) findViewById(R.id.fireButton);

        leftButton.setOnTouchListener(this); // и добавляем этот класс как слушателя (при нажатии сработает onTouch)
        rightButton.setOnTouchListener(this);
        fireButton.setOnTouchListener(this);
        mChronometer = findViewById(R.id.mChronometer);

        mChronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                long elapsedMillis = SystemClock.elapsedRealtime()
                        - mChronometer.getBase();


            }
        });
        mChronometer.setBase(SystemClock.elapsedRealtime());
        mChronometer.start();

    }



    public boolean onTouch(View button, MotionEvent motion) {
        switch(button.getId()) { // определяем какая кнопка
            case R.id.leftButton:
                switch (motion.getAction()) { // определяем нажата или отпущена
                    case MotionEvent.ACTION_DOWN:
                        isLeftPressed = true;
                        break;
                    case MotionEvent.ACTION_UP:
                        isLeftPressed = false;
                        break;
                }
                break;
            case R.id.rightButton:
                switch (motion.getAction()) { // определяем нажата или отпущена
                    case MotionEvent.ACTION_DOWN:
                        isRightPressed = true;
                        break;
                    case MotionEvent.ACTION_UP:
                        isRightPressed = false;
                        break;
                }
                break;
            case R.id.fireButton:

                switch (motion.getAction()) { // определяем нажата или отпущена
                    case MotionEvent.ACTION_DOWN:
                        isFirePressed = true;

                        break;
                    case MotionEvent.ACTION_UP:
                        isFirePressed = false;
                        break;
                }
                break;
        }
        return true;
    }
    public  void goRestart(View v) {

        Intent i=new Intent(this, MainActivity.class);
        startActivity(i);
    }
    public  void goBackToMain(View v) {
        Intent i=new Intent(this, GameMain.class);
        startActivity(i);
//        i = new Intent(this, MainRecords.class);
//        String time = mChronometer.getText().toString();
//        i.putExtra(KEY_MSG2, String.valueOf(mChronometer));

    }


}