package com.example.spacewar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainRecords extends AppCompatActivity {
    MyDatabaseHelper mDbHelper;
    SQLiteDatabase dbRead;
    SQLiteDatabase dbWrite;
    EditText etID;
    TextView etNN;
    TextView etT;
    List<String> records=new ArrayList<>();
    RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_records);
         rv=findViewById(R.id.recyclerView);

        mDbHelper = new MyDatabaseHelper(this);

        dbWrite = mDbHelper.getWritableDatabase();

        dbRead = mDbHelper.getReadableDatabase();



        String[] projection = {MyDatabaseContract .TableRecords.COLUMN_NAME_RECORD_ID,
                MyDatabaseContract .TableRecords.COLUMN_NAME_NICKNAME,
                MyDatabaseContract .TableRecords.COLUMN_NAME_TIME       };
        String sortOrder =MyDatabaseContract .TableRecords.COLUMN_NAME_TIME + " DESC";
        Cursor c = dbRead.query(
                MyDatabaseContract.TableRecords.TABLE_NAME, projection, null, null, null, null, sortOrder   );
        String s ="";
        c.moveToFirst();
        for(int i=0;i<c.getCount(); i++)  { s ="";
            s=s+c.getInt(0)+", " + c.getString(1)+", "+c.getString(2);
            records.add(s);

            Log.i("sss", s);  c.moveToNext();     }
       
        Collections.sort(records);



        rv.setAdapter(new RecyclerView.Adapter() {


            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                //get inflater
                Context context = parent.getContext();
                LayoutInflater inflater = LayoutInflater.from(context);

//      //Inflate the custom layout
                View view = inflater.inflate(R.layout.row_layout, parent, false);

                return new SimpleViewHolder(view);



            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

                ((SimpleViewHolder)holder).simpleTextView.setText(records.get(position));

            }

            @Override
            public int getItemCount() {
                return records.size();
            }


                           class SimpleViewHolder extends RecyclerView.ViewHolder {
                              public TextView simpleTextView;

                              public SimpleViewHolder(final View itemView) {
                                  super(itemView);
                                  simpleTextView = (TextView) itemView.findViewById(R.id.textViewRow);
                              }
                          }

        }
             );
           rv.setLayoutManager(new LinearLayoutManager(MainRecords.this));




    }
    public void addRecord(View view) {
        String nickName=etNN.getText().toString();
        String time=etT.getText().toString();

        ContentValues values = new ContentValues();
        values.put(MyDatabaseContract .TableRecords.COLUMN_NAME_NICKNAME, nickName);
        values.put(MyDatabaseContract .TableRecords.COLUMN_NAME_TIME, time);


        long newRowId;


        newRowId = dbWrite.insert(
                MyDatabaseContract.TableRecords.TABLE_NAME,
                null,
                values);
    }
    public void readData(View view) {


    }
    public void updateRecord(View view) {

// New value for one column
        ContentValues values = new ContentValues();
        String nickName=etNN.getText().toString();
        String time=etT.getText().toString();


        values.put( MyDatabaseContract .TableRecords.COLUMN_NAME_NICKNAME, nickName);
        values.put(MyDatabaseContract.TableRecords.COLUMN_NAME_TIME,time);


        String selection = MyDatabaseContract.TableRecords.COLUMN_NAME_RECORD_ID + " LIKE ?";


        String[] selectionArgs = { etID.getText().toString() };

        int count = dbRead.update(
                MyDatabaseContract .TableRecords.TABLE_NAME,
                values,
                selection,
                selectionArgs);    }

    public void deleteRecord(View view) {

        String selection = MyDatabaseContract.TableRecords.COLUMN_NAME_RECORD_ID + " LIKE ?";

        //  int id=Integer.valueOf(etID.getText().toString());
        String[] selectionArgs = { etID.getText().toString() };


        dbRead.delete(MyDatabaseContract.TableRecords.TABLE_NAME, selection, selectionArgs);
    }



}
