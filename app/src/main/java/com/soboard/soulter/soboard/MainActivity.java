package com.soboard.soulter.soboard;

import android.app.ActivityManager;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.soboard.soulter.soboard.DB.DBContruct;
import com.soboard.soulter.soboard.DB.DBHelper;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ClipDataRvAdapter myAdapter;

    private SQLiteDatabase mDb;

    int INIT_DATA_TAG;

    static Intent ClipDataServiceintent;

    public static Boolean isShow = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initData();

        ClipDataServiceintent = new Intent(MainActivity.this,ClipDataService.class);

        //TODO:可以设置startForegroundService，但是要分api<26...
        startService(ClipDataServiceintent);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    public void initData(){
        DBHelper dbHelper = new DBHelper(this);
        mDb = dbHelper.getWritableDatabase();
        Cursor cursor = getClipData();
        recyclerView = (RecyclerView)findViewById(R.id.rv_clip_data_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        myAdapter = new ClipDataRvAdapter(recyclerView.getContext(),cursor);
        recyclerView.setAdapter(myAdapter);
    }

//    @Override
//    protected void onResume() {
//        if (ClipDataServiceintent != null){
//            stopService(ClipDataServiceintent);
//
//        }
//
//
//        super.onResume();
//    }

    @Override
    protected void onRestart() {
        initData();
        Log.v("TAG","MainActivity:OnRestart");
        super.onRestart();

//        if(checkService()){
//            stopService(ClipDataServiceintent);
//        }


    }

//    @Override
//    protected void onPause() {
//        super.onPause();
//        startService(ClipDataServiceintent);
//    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDb.close();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private Cursor getClipData(){
        return mDb.query(
                DBContruct.ClipDataEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                 DBContruct.ClipDataEntry.COLUMN_TIMESTAMP+" desc");
    }

        public boolean checkService(){
        //扫描服务
        ActivityManager manager = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(200)) {
            if ("com.soboard.soulter.soboard.ClipDataService".equals(service.service.getClassName())){
                Toast.makeText(this,"Service is running!",Toast.LENGTH_SHORT).show();
                return true;
            }
        }
        return false;
    }


}

