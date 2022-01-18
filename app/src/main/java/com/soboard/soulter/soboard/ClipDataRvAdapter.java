package com.soboard.soulter.soboard;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.soboard.soulter.soboard.DB.DBContruct;
import com.soboard.soulter.soboard.DB.DBHelper;
import com.soboard.soulter.soboard.DB.GetLocalTime;

/**
 * Created by Soulter on 2018/7/10.
 * qq 905617992
 */

public class ClipDataRvAdapter extends RecyclerView.Adapter<ClipDataRvAdapter.MyViewHolder>{
    private Context mContext;
    private Cursor mCursor;
    private SQLiteDatabase mDb;

    public ClipDataRvAdapter(Context context,Cursor cursor){
        mContext = context;
        mCursor = cursor;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.member_clip_data_list_layout,parent,false);
        DBHelper dbHelper = new DBHelper(mContext);
        mDb = dbHelper.getWritableDatabase();
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if(!mCursor.moveToPosition(position)){
            return;
        }

            final String clipDataStr= mCursor.getString(mCursor.getColumnIndex(DBContruct.ClipDataEntry.COLUMN_CLIP_DATA_STRING));
            String clipDataTime = GetLocalTime.getLocalTime(mCursor, DBContruct.ClipDataEntry.COLUMN_TIMESTAMP);
            final long id = mCursor.getLong(mCursor.getColumnIndex(DBContruct.ClipDataEntry._ID));//得到id


            holder.clipDataDp.setText(clipDataStr);
            holder.clipDataTimeDp.setText(clipDataTime);



            holder.clipDataCopy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


//                    int countCursorTemp = mCursor.getCount();

//                    mContext.stopService(MainActivity.ClipDataServiceintent);

                    // 获取系统剪贴板
                    ClipboardManager clipboard = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);

// 创建一个剪贴数据集，包含一个普通文本数据条目（需要复制的数据）
                    ClipData clipData = ClipData.newPlainText(null, clipDataStr);

// 把数据集设置（复制）到剪贴板
                    clipboard.setPrimaryClip(clipData);

                    removeClipBoard(id);




//                    Cursor cursorTemp;


                }
            });

    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        private View mView;

        private TextView clipDataDp;
        private TextView clipDataTimeDp;
        private Button clipDataCopy;
        public MyViewHolder(View itemView){
            super(itemView);

            clipDataDp = (TextView)itemView.findViewById(R.id.clip_data_dp);
            clipDataTimeDp = (TextView)itemView.findViewById(R.id.clip_data_time_dp);
            clipDataCopy = (Button)itemView.findViewById(R.id.clip_data_copy);

            mView = itemView;
        }
    }


    public Boolean removeClipBoard(long id){
        return mDb.delete(DBContruct.ClipDataEntry.TABLE_NAME,
                DBContruct.ClipDataEntry._ID + "=" + id,null) > 0;
    }

//    public void swapCursor(Cursor newCursor) {
//        // Always close the previous mCursor first
//        if (mCursor != null) mCursor.close();
//        mCursor = newCursor;
//        if (newCursor != null) {
//            // Force the RecyclerView to refresh
//            this.notifyDataSetChanged();
//        }
//    }
//
//    private Cursor getClipData(){
//        return mDb.query(
//                DBContruct.ClipDataEntry.TABLE_NAME,
//                null,
//                null,
//                null,
//                null,
//                null,
//                DBContruct.ClipDataEntry.COLUMN_TIMESTAMP+" desc");
//    }


}
