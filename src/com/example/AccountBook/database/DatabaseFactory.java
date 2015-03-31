package com.example.AccountBook.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by yoshida keisuke on 2015/03/18.
 */
public class DatabaseFactory {

    /*
    データベースの要求は必ずこのクラスから
     */



    /*
    定数
     */
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "database.db";

    private static DatabaseFactory sInstance;

    private AccountDatabaseOpenHelper mOpenHelper;
    private AccountDatabase mAccountDatabase;


    /**
     * コンストラクタ
     * @param context
     */
    private DatabaseFactory(Context context){
        mOpenHelper = new AccountDatabaseOpenHelper(context,DB_NAME,null,DB_VERSION);
        mAccountDatabase = new AccountDatabase(context,mOpenHelper);
    }

    /**
     インスタンスの生成
     複数のインスタンスを持たないようにするため
     */
    public static DatabaseFactory getInstance(Context context){
        if(sInstance == null) {
            sInstance = new DatabaseFactory(context);
        }
        return sInstance;
    }

    public static AccountDatabase getAccountDatabase(Context context){
        return getInstance(context).mAccountDatabase;
    }


    /**
     * オープンヘルパーの継承クラス
     */
    private class AccountDatabaseOpenHelper extends SQLiteOpenHelper{
        //TODO onCreate処理とupgrade処理を各

        /**
        コンストラクタ
         */
        public AccountDatabaseOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(AccountDatabase.CREATE_TABLE_SQL);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }
    }

}
