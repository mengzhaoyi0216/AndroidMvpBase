package com.example.androidmvpbase.db.dbhelp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.androidmvpbase.db.greendao.DaoMaster;
import com.example.androidmvpbase.db.greendao.DbPersonDao;


/**
 * 升级数据库使用的类
 * 解决数据库升级数据丢失问题
 */
public class UpgradeHelper extends DaoMaster.OpenHelper {

    public UpgradeHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        Log.i("greenDAO", "Upgrading schema from version " + oldVersion + " to " + newVersion + " by migrating all tables data");

        MigrationHelper.getInstance().migrate(db,
                DbPersonDao.class);

    }
}