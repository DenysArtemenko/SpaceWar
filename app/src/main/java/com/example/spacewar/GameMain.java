package com.example.spacewar;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class GameMain extends AppCompatActivity implements View.OnClickListener {
    public static final String KEY_MSG1="com.example.myspacewar.denys.msg1";
static EditText editText;
Button btnSave, btnLoad;
 SharedPreferences sharedPreferences;
final String SAVED_TEXT = "saved_text";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_main);
        editText = (EditText)findViewById(R.id.editText);
        editText.setText(getSharedPreferences("MyPref", MODE_PRIVATE).getString(SAVED_TEXT,""));


        btnSave = (Button)findViewById(R.id.btnSave);
        btnSave.setOnClickListener(this);
        btnLoad = (Button)findViewById(R.id.btnLoad);
        btnLoad.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSave:
                saveText();
                break;
            case R.id.btnLoad:
                loadText();
                break;
            default:
                break;
        }


    }



    private void saveText() {
        sharedPreferences = getSharedPreferences("MyPref", MODE_PRIVATE);
        SharedPreferences.Editor ed = sharedPreferences.edit();
        ed.putString(SAVED_TEXT, editText.getText().toString());
        ed.commit();
        Toast.makeText(GameMain.this, "Text saved", Toast.LENGTH_SHORT).show();
    }
    private void loadText() {
        sharedPreferences = getSharedPreferences("MyPref", MODE_PRIVATE);
        String savedText = sharedPreferences.getString(SAVED_TEXT, "");
        editText.setText(savedText);
        Toast.makeText(GameMain.this, "Text loaded", Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveText();
    }


    public  void goStart(View v) {
        Intent i=new Intent(this, MainActivity.class);

        startActivity(i);
    }
    public  void goRecords(View v) {

        Intent j=new Intent(this, MainRecords.class);


        startActivity(j);
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void goClose(View view) {

        finishAffinity();
    }

}
