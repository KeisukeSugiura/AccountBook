package com.example.AccountBook.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.AccountBook.model.DayAccountModel;

import java.util.ArrayList;

/**
 * Created by yoshida keisuke on 2015/03/18.
 */
public class AccountDatabase{
    /*
    データベースの各種操作を提供するクラス
    DatabaseFactoryクラスからインスタンス化すること
     */


    /*
    定数
     */
    public static final String TABLE_NAME = "account_data";
    public static final String COL_ID = "_id";
    public static final String COL_YEAR = "year";
    public static final String COL_MONTH = "month";
    public static final String COL_DAY = "day";
    public static final String COL_TYPE = "type";
    public static final String COL_PRICE = "price";

    private String[] m0Param=new String[]{};;
    private String[] m1Param = new String[]{""};
    private String[] m2Param = new String[]{"",""};
    private String[] m3Param = new String[]{"","",""};
    public final String[] ID_COLS = new String[]{COL_ID};

    //CREATE文
    public static final String CREATE_TABLE_SQL =
            "CREATE TABLE "+TABLE_NAME+" ("+
                    " "+COL_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                    " "+COL_YEAR+" INTEGER," +
                    " "+COL_MONTH+" INTEGER," +
                    " "+COL_DAY+" INTEGER," +
                    " "+COL_TYPE+" INTEGER,"+
                    " "+COL_PRICE+" INTEGER"+
            ")";

    //WHERE文
    public static final String WHERE_ID_DATE_YEAR_MONTH_DAY = COL_YEAR+"=? AND "+ COL_MONTH + "=? AND "+ COL_DAY +"=?";
    public static final String WHERE_ID_DATE_YEAR_MONTH =  COL_YEAR+"=? AND "+ COL_MONTH + "=?";
    public static final String WHERE_ID_DATE_YEAR =  COL_YEAR+"=?";
    public static final String WHERE_ID = COL_ID+"=?";

    private SQLiteOpenHelper mOpenHelper;
    protected Context mContext;

    /**
     * コンストラクター
     * @param context
     * @param databaseHelper
     */
    public AccountDatabase(Context context, SQLiteOpenHelper databaseHelper){
        mContext = context;
        mOpenHelper = databaseHelper;
    }


    public SQLiteDatabase getWritableDatabase(){
        return mOpenHelper.getWritableDatabase();
    }

    public SQLiteDatabase getReadableDatabase(){
        return mOpenHelper.getReadableDatabase();
    }



    /*
        コンバートメソッド
     */

    /**
     * 一つのidから一つのDayAccountを生成
     * @param id
     * @return
     */
    public DayAccountModel findDayAccountById(Long id){
        m1Param[0] = String.valueOf(id);

        Cursor cursor = mOpenHelper.getReadableDatabase().query(TABLE_NAME,null,WHERE_ID,m1Param,null,null,"1");


        if(cursor.moveToFirst()) {
            return cursor2DayAccountModel(cursor);
        }else{
            return null;
        }
    }


    /**
     * 要素数が一つのカーソルからDayAccountModelの生成
     * @param cursor
     * @return
     */
    public DayAccountModel cursor2DayAccountModel(Cursor cursor) throws IllegalArgumentException{
        if(cursor.isBeforeFirst() || cursor.isAfterLast()){
            return null;
        }

        return new DayAccountModel(
                cursor.getLong(cursor.getColumnIndexOrThrow(COL_ID)),
                cursor.getInt(cursor.getColumnIndexOrThrow(COL_YEAR)),
                cursor.getInt(cursor.getColumnIndexOrThrow(COL_MONTH)),
                cursor.getInt(cursor.getColumnIndexOrThrow(COL_DAY)),
                cursor.getInt(cursor.getColumnIndexOrThrow(COL_PRICE)),
                cursor.getInt(cursor.getColumnIndexOrThrow(COL_TYPE))
        );
    }

    /**
     * カーソルをIDに変える
     * @param cursor
     * @return
     */
    public Long cursor2Id(Cursor cursor){

        if(cursor.isBeforeFirst() || cursor.isAfterLast()){
            return null;
        }
        return cursor.getLong(cursor.getColumnIndexOrThrow(COL_ID));

    }

    /**
     * データベースに格納するための変換
     * @param account
     * @return
     */
    public ContentValues generateContentValues(DayAccountModel account){
        ContentValues values = new ContentValues();
        values.put(COL_YEAR,account.getYear());
        values.put(COL_MONTH,account.getMonth());
        values.put(COL_DAY,account.getDay());
        values.put(COL_TYPE,account.getType());
        values.put(COL_PRICE,account.getPrice());

        return values;
    }


    /*
    検索メソッド
     */


    /**
     * 年月日からデータを検索する
     * @param year
     * @param month
     * @param day
     * @return
     */
    public Cursor findCursorByDate(int year,int month,int day){
        m3Param[0] = String.valueOf(year);
        m3Param[1] = String.valueOf(month);
        m3Param[2] = String.valueOf(day);
      //  Log.d("parms",m3Param[0]);
       // Log.d("parms",m3Param[1]);
      //  Log.d("parms",m3Param[2]);
        SQLiteDatabase s = mOpenHelper.getWritableDatabase();
        Cursor cursor = s.query(TABLE_NAME,null, WHERE_ID_DATE_YEAR_MONTH_DAY, m3Param, null, null, null, null);
        //Cursor cursor = mOpenHelper.getReadableDatabase().query(TABLE_NAME, ID_COLS, WHERE_ID_DATE_YEAR_MONTH_DAY, m3Param, null, null, null, null);
        return cursor;
    }

    /**
     * 年月からデータを検索する
     * @param year
     * @param month
     * @return
     */
    public Cursor findCursorByDate(int year,int month){
        m2Param[0] = String.valueOf(year);
        m2Param[1] = String.valueOf(month);
        Cursor cursor = mOpenHelper.getReadableDatabase().query(TABLE_NAME,null,WHERE_ID_DATE_YEAR_MONTH,m2Param,null,null,null,null);
        return cursor;
    }

    /**
     * 年からデータを検索する
     * @param year
     * @return
     */
    public Cursor findCursorByDate(int year){
        m1Param[0] = String.valueOf(year);
        Cursor cursor = mOpenHelper.getReadableDatabase().query(TABLE_NAME,null,WHERE_ID_DATE_YEAR,m1Param,null,null,null,null);
        return cursor;
    }


    /**
     * 年月日から検索して結果をカーソルで返却する
     * @param year
     * @param month
     * @param day
     * @return
     */
    public Long findIdByDate(int year,int month, int day){
        m3Param[0] = String.valueOf(year);
        m3Param[1] = String.valueOf(month);
        m3Param[2] = String.valueOf(day);

        Cursor cursor = mOpenHelper.getReadableDatabase().query(TABLE_NAME,ID_COLS,WHERE_ID_DATE_YEAR_MONTH_DAY,m3Param,null,null,null,"1");

        if(cursor.moveToFirst()){
            return cursor2Id(cursor);
        }

        return null;
    }



    /*
        外部で呼び出すメソッド群
     */


    /**
     * 一日単位のAccountDataを返却する
     * @param year
     * @param month
     * @param day
     * @return
     */
    public ArrayList<DayAccountModel> getOneDayAccount(int year,int month,int day){
        ArrayList<DayAccountModel> result = new ArrayList<DayAccountModel>();

        Cursor cursor = findCursorByDate(year,month,day);
        boolean isEOF = cursor.moveToFirst();
        DayAccountModel accountModel;
        while(isEOF){
            accountModel= cursor2DayAccountModel(cursor);
            result.add(accountModel);
            isEOF = cursor.moveToNext();
        }
        return result;
    }
    /**
     * 一月単位のAccountDataを返却する
     * @param year
     * @param month
     * @return
     */
    public ArrayList<DayAccountModel> getOneMonthAccount(int year,int month){
        ArrayList<DayAccountModel> result = new ArrayList<DayAccountModel>();

        Cursor cursor = findCursorByDate(year,month);
        boolean isEOF = cursor.moveToFirst();
        DayAccountModel accountModel;
        while(isEOF){
            accountModel= cursor2DayAccountModel(cursor);
            result.add(accountModel);
            isEOF = cursor.moveToNext();
        }
        return result;
    }

    /**
     * 一年単位のAccountDataを返却する
     * @param year
     * @return
     */
    public ArrayList<DayAccountModel> getOneYearAccount(int year){
        ArrayList<DayAccountModel> result = new ArrayList<DayAccountModel>();

        Cursor cursor = findCursorByDate(year);
        boolean isEOF = cursor.moveToFirst();
        DayAccountModel accountModel;
        while(isEOF){
            accountModel= cursor2DayAccountModel(cursor);
            result.add(accountModel);
            isEOF = cursor.moveToNext();
        }
        return result;
    }

    /**
     * 家計簿データをデータベースに登録する
     * @param account
     */
    public void insertData(DayAccountModel account){
        SQLiteDatabase database = mOpenHelper.getWritableDatabase();
       // Log.d("step2", "click ok---");

        database.beginTransaction();
        try{
            insertData(database,account);
            database.setTransactionSuccessful();
        }finally {
            database.endTransaction();
        }

    }

    /**
     * 家計簿データをデータベースに登録するwithoutトランザクション
     * @param database
     * @param account
     */
    public void insertData(SQLiteDatabase database,DayAccountModel account){
        Long a = database.insertOrThrow(TABLE_NAME, null, generateContentValues(account));
        account.setId(a);
      //  Log.d("step3",String.valueOf(a));
    }

    /**
     * データを消す
     * @param accountModel
     */
    public void deleteData(DayAccountModel accountModel){

        SQLiteDatabase database = mOpenHelper.getWritableDatabase();
        database.beginTransaction();
        try{
            deleteData(database,accountModel);
            database.setTransactionSuccessful();
        }finally{
            database.endTransaction();
        }

    }


    /**
     * データを消すwithoutトランザクション
     * @param database
     * @param account
     */
    public void deleteData(SQLiteDatabase database,DayAccountModel account){
        if(account != null){
            m1Param[0] = String.valueOf(account.getId());
            database.delete(TABLE_NAME,WHERE_ID,m1Param);
        }
    }





}



