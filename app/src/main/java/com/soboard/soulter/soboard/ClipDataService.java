package com.soboard.soulter.soboard;

import android.app.Notification;
import android.app.Service;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.soboard.soulter.soboard.DB.DBContruct;
import com.soboard.soulter.soboard.DB.DBHelper;

/**
 * Created by Administrator on 2018/7/10.
 */

public class ClipDataService extends Service {
    ClipboardManager clipboardManager;
    private SQLiteDatabase mDb;
    private final static int GRAY_SERVICE_ID = android.os.Process.myPid();

    @Override
    public void onCreate() {
        Log.v("TAG","Service Start!");
        DBHelper dbHelper = new DBHelper(this);
        mDb = dbHelper.getWritableDatabase();
         clipboardManager = (ClipboardManager)this.getSystemService(this.CLIPBOARD_SERVICE);
        clipboardManager.addPrimaryClipChangedListener(new ClipboardManager.OnPrimaryClipChangedListener() {
            @Override
            public void onPrimaryClipChanged() {
                ClipData clipData = clipboardManager.getPrimaryClip();
                if(clipData != null && clipData.getItemCount()>0){
                    String clipBoardFirstText = clipData.getItemAt(0).getText().toString();
                    addClipData(clipBoardFirstText);
                    Toast.makeText(ClipDataService.this,"getTheData："+clipBoardFirstText,Toast.LENGTH_LONG).show();
                }

            }
        });
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

//        if (Build.VERSION.SDK_INT < 18) {

            startForeground(GRAY_SERVICE_ID, new Notification());//API < 18 ，此方法能有效隐藏Notification上的图标

//        } else {
//
//            Intent innerIntent = new Intent(this, GrayInnerService.class);
//
//            startService(innerIntent);
//
//            startForeground(GRAY_SERVICE_ID, new Notification());
//
//        }

        flags = START_STICKY;

        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private long addClipData(String clipDataString){
        ContentValues cv = new ContentValues();
        cv.put(DBContruct.ClipDataEntry.COLUMN_CLIP_DATA_STRING,clipDataString);
        return mDb.insert(DBContruct.ClipDataEntry.TABLE_NAME,null,cv);
    }

}
