package space.pal.sig.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import space.pal.sig.model.Apod;
import space.pal.sig.model.Facts;

/**
 * Created by icatalin on 11.02.2018.
 */

public class SqlService {

    public static void insertApod(Context context, Apod apodObject) {
        if (apodObject.getMedia_type().equals("movie")) return;
        SqlHelper sql = new SqlHelper(context);
        SQLiteDatabase sqLiteDatabase = sql.getWritableDatabase();
        ContentValues cv = new ContentValues();
        if (!existContentForToday(context, apodObject)) {
            cv.put(SqlStructure.SqlData.date_column, apodObject.getDate());
            cv.put(SqlStructure.SqlData.hdurl_column, apodObject.getHdurl());
            cv.put(SqlStructure.SqlData.url_column, apodObject.getUrl());
            cv.put(SqlStructure.SqlData.title_column, apodObject.getTitle());
            cv.put(SqlStructure.SqlData.description_column, apodObject.getExplanation());
            String author = apodObject.getCopyright() == null ? "" : apodObject.getCopyright();
            cv.put(SqlStructure.SqlData.author_column, author);
            sqLiteDatabase.insert(SqlStructure.SqlData.IMAGE_DATA_TABLE, null, cv);
        }
        sqLiteDatabase.close();
    }

    private static boolean existContentForToday(Context context, Apod apodObject) {
        boolean exist = false;
        SqlHelper sqlHelper = new SqlHelper(context);
        SQLiteDatabase sqLiteDatabase = sqlHelper.getReadableDatabase();
        String date = apodObject.getDate();
        String[] whereArgs = {date};
        Cursor cursor = sqLiteDatabase.query(SqlStructure.SqlData.IMAGE_DATA_TABLE, null,
                SqlStructure.SqlData.date_column + " = ? ", whereArgs, null, null, null);
        if (cursor.getCount() == 1) exist = true;
        cursor.close();
        sqLiteDatabase.close();
        return exist;
    }

    public static void insertApodList(Context context, List<Apod> apodList) {
        SQLiteDatabase sqLiteDatabase = new SqlHelper(context).getWritableDatabase();
        for (Apod apod : apodList) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(SqlStructure.SqlData.date_column, apod.getDate());
            contentValues.put(SqlStructure.SqlData.author_column, apod.getCopyright());
            contentValues.put(SqlStructure.SqlData.hdurl_column, apod.getHdurl());
            contentValues.put(SqlStructure.SqlData.url_column, apod.getUrl());
            contentValues.put(SqlStructure.SqlData.title_column, apod.getTitle());
            contentValues.put(SqlStructure.SqlData.description_column, apod.getExplanation());
            sqLiteDatabase.insert(SqlStructure.SqlData.IMAGE_DATA_TABLE, null, contentValues);
            Log.i("apod", "inserted");
        }
        sqLiteDatabase.close();
    }

    public static void insertFactList(Context context, List<Facts> facts) {
        SQLiteDatabase sqLiteDatabase = new SqlHelper(context).getWritableDatabase();
        for (Facts fact : facts) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(SqlStructure.SqlData.fact_name, fact.getFact());
            sqLiteDatabase.insert(SqlStructure.SqlData.FACTS_TABLE, null, contentValues);
            Log.i("fact", "inserted");
        }
        sqLiteDatabase.close();
    }

    public static List<Facts> getFacts(Context context) {
        List<Facts> factsList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = new SqlHelper(context).getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(SqlStructure.SqlData.FACTS_TABLE, null,
                null, null, null, null, null);
        while (cursor.moveToNext()) {
            Facts facts = new Facts();
            facts.setFact(cursor.getString(cursor.getColumnIndex(SqlStructure.SqlData.fact_name)));
            factsList.add(facts);
        }
        cursor.close();
        sqLiteDatabase.close();
        return factsList;
    }

    public static List<Apod> getApodList(Context context, String limit) {
        List<Apod> apodList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = new SqlHelper(context).getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(SqlStructure.SqlData.IMAGE_DATA_TABLE, null,
                null, null, null, null, "_id desc", limit);
        while (cursor.moveToNext()) {
            Apod apod = new Apod();
            apod.setTitle(cursor.getString(cursor.getColumnIndex(SqlStructure.SqlData.title_column)));
            apod.setExplanation(cursor.getString(cursor.getColumnIndex(SqlStructure.SqlData.description_column)));
//            apod.setCopyright(cursor.getString(cursor.getColumnIndex(SqlStructure.SqlData.author_column)));
            apod.setUrl(cursor.getString(cursor.getColumnIndex(SqlStructure.SqlData.url_column)));
            apod.setHdurl(cursor.getString(cursor.getColumnIndex(SqlStructure.SqlData.hdurl_column)));
            apodList.add(apod);
        }
        cursor.close();
        sqLiteDatabase.close();
        return apodList;
    }

}
