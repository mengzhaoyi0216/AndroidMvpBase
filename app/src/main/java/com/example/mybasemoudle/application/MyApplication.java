package com.example.mybasemoudle.application;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

import com.example.mybasemoudle.db.dbhelp.UpgradeHelper;
import com.example.mybasemoudle.db.greendao.DaoMaster;
import com.example.mybasemoudle.db.greendao.DaoSession;

import org.greenrobot.greendao.database.Database;

import java.io.File;

public class MyApplication extends Application {
    //管理所有的Dao
    private DaoSession mDaoSession;
    //全局上下文
    private static MyApplication application;
    //如果需要保存文件，要用这个路径
    private String cachePath;
    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        setUpDataBase(getApplicationContext());
        initCachePath();
//        initLeakCanary();
    }

    private void initLeakCanary() {
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            // This process is dedicated to LeakCanary for heap analysis.
//            // You should not init your app in this process.
//            return;
//        }
//        LeakCanary.install(this);
    }

    private void initCachePath() {
        File externalFilesDir = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            externalFilesDir = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
            cachePath = externalFilesDir.getPath();
        }else{
            //Environment.getRootDirectory().getAbsolutePath();
            cachePath = Environment.getExternalStorageDirectory().getPath();
        }
    }

    /**
     * 获取MyApplication
     * @return 返回MyApplication
     */
    public static MyApplication getInstance(){
        return application;
    }

    /**
     * 初始化数据库
     * @param context 上下文
     */
    private void setUpDataBase(Context context) {
        UpgradeHelper upgradeHelper = new UpgradeHelper(context, "longT.db", null);
        Database database = upgradeHelper.getWritableDb();
        DaoMaster daoMaster = new DaoMaster(database);
        mDaoSession = daoMaster.newSession();
    }

    public DaoSession getmDaoSession() {
        return mDaoSession;
    }

    public String getCachePath() {
        return cachePath;
    }


}
