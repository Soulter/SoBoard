package com.soboard.soulter.soboard.DB;

import android.provider.BaseColumns;

/**
 * Created by Administrator on 2018/7/10.
 */

public class DBContruct {
    public static final class ClipDataEntry implements BaseColumns{
        public static final String TABLE_NAME = "clipdata";
        public static final String COLUMN_CLIP_DATA_STRING = "clipDataString";
        public static final String COLUMN_TIMESTAMP = "timestamp";
    }
}
