package com.example.mybasemoudle.db.dbhelp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.StandardDatabase;
import org.greenrobot.greendao.internal.DaoConfig;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 升级数据库使用的类
 */
public final class MigrationHelper {
    private static MigrationHelper instance;
    public static MigrationHelper getInstance() {
        if(instance == null) {
            instance = new MigrationHelper();
        }
        return instance;
    }

    public static void migrate(SQLiteDatabase sqliteDatabase, Class<? extends AbstractDao<?, ?>>... daoClasses) {
        StandardDatabase db = new StandardDatabase(sqliteDatabase);
        generateNewTablesIfNotExists(db, daoClasses);
        generateTempTables(db, daoClasses);
        dropAllTables(db, true, daoClasses);
        createAllTables(db, false, daoClasses);
        restoreData(db, daoClasses);
    }

    public static void migrate(StandardDatabase db, Class<? extends AbstractDao<?, ?>>... daoClasses) {
        generateNewTablesIfNotExists(db, daoClasses);
        generateTempTables(db, daoClasses);
        dropAllTables(db, true, daoClasses);
        createAllTables(db, false, daoClasses);
        restoreData(db, daoClasses);
    }

    private static void generateNewTablesIfNotExists(StandardDatabase db, Class<? extends AbstractDao<?, ?>>... daoClasses) {
        reflectMethod(db, "createTable", true, daoClasses);
    }

    private static void generateTempTables(StandardDatabase db, Class<? extends AbstractDao<?, ?>>... daoClasses) {
        for (int i = 0; i < daoClasses.length; i++) {
            DaoConfig daoConfig = new DaoConfig(db, daoClasses[i]);
            String tableName = daoConfig.tablename;
            String tempTableName = daoConfig.tablename.concat("_TEMP");
            StringBuilder insertTableStringBuilder = new StringBuilder();
            insertTableStringBuilder.append("CREATE TEMP TABLE ").append(tempTableName);
            insertTableStringBuilder.append(" AS SELECT * FROM ").append(tableName).append(";");
            db.execSQL(insertTableStringBuilder.toString());
        }
    }

    private static void dropAllTables(StandardDatabase db, boolean ifExists, @NonNull Class<? extends AbstractDao<?, ?>>... daoClasses) {
        reflectMethod(db, "dropTable", ifExists, daoClasses);
    }

    private static void createAllTables(StandardDatabase db, boolean ifNotExists, @NonNull Class<? extends AbstractDao<?, ?>>... daoClasses) {
        reflectMethod(db, "createTable", ifNotExists, daoClasses);
    }

    /**
     * dao class already define the sql exec method, so just invoke it
     */
    private static void reflectMethod(StandardDatabase db, String methodName, boolean isExists, @NonNull Class<? extends AbstractDao<?, ?>>... daoClasses) {
        if (daoClasses.length < 1) {
            return;
        }
        try {
            for (Class cls : daoClasses) {
                Method method = cls.getDeclaredMethod(methodName, Database.class, boolean.class);
                method.invoke(null, db, isExists);
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private static void restoreData(StandardDatabase db, Class<? extends AbstractDao<?, ?>>... daoClasses) {
        for (int i = 0; i < daoClasses.length; i++) {
            DaoConfig daoConfig = new DaoConfig(db, daoClasses[i]);
            String tableName = daoConfig.tablename;
            String tempTableName = daoConfig.tablename.concat("_TEMP");
            // get all columns from tempTable, take careful to use the columns list
            List<String> columns = getColumns(db, tempTableName);
            ArrayList<String> properties = new ArrayList<>(columns.size());
            for (int j = 0; j < daoConfig.properties.length; j++) {
//                String columnName = daoConfig.properties[j].columnName;
//                if (columns.contains(columnName)) {
//                    properties.add(columnName);
//                }
                String columnName = daoConfig.properties[j].columnName;
                if (!columns.contains(columnName)) {
                    StringBuilder insertTableStringBuilder = new StringBuilder();
                    insertTableStringBuilder.append("ALTER TABLE "+tempTableName +" ADD COLUMN "+columnName+
                            getTableType(daoConfig.properties[j].type));
                    db.execSQL(insertTableStringBuilder.toString());
                }
                properties.add(columnName);
            }
            if (properties.size() > 0) {
                final String columnSQL = TextUtils.join(",", properties);

                StringBuilder insertTableStringBuilder = new StringBuilder();
                insertTableStringBuilder.append("INSERT INTO ").append(tableName).append(" (");
                insertTableStringBuilder.append(columnSQL);
                insertTableStringBuilder.append(") SELECT ");
                insertTableStringBuilder.append(columnSQL);
                insertTableStringBuilder.append(" FROM ").append(tempTableName).append(";");
                db.execSQL(insertTableStringBuilder.toString());
            }
            StringBuilder dropTableStringBuilder = new StringBuilder();
            dropTableStringBuilder.append("DROP TABLE ").append(tempTableName);
            db.execSQL(dropTableStringBuilder.toString());
        }
    }

    /**
     * 解决数据库升级如果有数字类型报空指针的问题
     * @param type
     * @return
     */
    private static Object getTableType(Class<?> type){
        if(type.equals(int.class)){
            return " INTEGER DEFAULT 0";
        }
        if(type.equals(long.class)){
            return " Long DEFAULT 0";
        }
        if(type.equals(String.class)){
            return " TEXT ";
        }
        if(type.equals(boolean.class)){
            return " NUMERIC DEFAULT 0";
        }
        return " TEXT";
    }
    private static List<String> getColumns(StandardDatabase db, String tableName) {
        List<String> columns = null;
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT * FROM " + tableName + " limit 0", null);
            if (null != cursor && cursor.getColumnCount() > 0) {
                columns = Arrays.asList(cursor.getColumnNames());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
            if (null == columns)
                columns = new ArrayList<>();
        }
        return columns;
    }

}