package uk.co.willanthony.quotationapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import uk.co.willanthony.quotationapp.Quote;

public class QuoteDatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "QuoteDB7";
    private static final String TABLE_NAME = "QuoteTable";

    // columns name for database table
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_DATE = "date";
    private static final String KEY_TIME = "time";

    // column index's
    private static final int ID_INDEX = 0;
    private static final int TITLE_INDEX = 1;
    private static final int DATE_INDEX = 2;
    private static final int TIME_INDEX = 3;

    // sql commands
    private static final String CREATE_DB = "CREATE TABLE " + TABLE_NAME + " (" +
            KEY_ID + " INTEGER PRIMARY KEY," +
            KEY_TITLE + " TEXT," +
            KEY_DATE + " TEXT," +
            KEY_TIME + " TEXT" + ")";

    private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + DATABASE_NAME;

    public QuoteDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        SQLiteStatement createSTMT = db.compileStatement(CREATE_DB);
        createSTMT.execute();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion >= newVersion) {
            return;
        }
        SQLiteStatement dropSTMT = db.compileStatement(DROP_TABLE);
        dropSTMT.execute();
        onCreate(db);
    }

    public long addQuote(Quote quote) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_TITLE, quote.getTitle());
        contentValues.put(KEY_DATE, quote.getDate());
        contentValues.put(KEY_TIME, quote.getTime());

        // inserting data into db
        long ID = db.insert(TABLE_NAME, null, contentValues);
        return ID;
    }

    public Quote getQuote(long id){
        SQLiteDatabase db = this.getWritableDatabase();

        String[] query = new String[] {KEY_ID,KEY_TITLE,KEY_DATE,KEY_TIME};
        Cursor cursor =  db.query(TABLE_NAME,query,KEY_ID + "=?",new String[]{String.valueOf(id)},null,null,null,null);
        if(cursor != null)
            cursor.moveToFirst();

        assert cursor != null;
        Quote quote = new Quote(
                Long.parseLong(cursor.getString(ID_INDEX)),
                cursor.getString(TITLE_INDEX),
                cursor.getString(DATE_INDEX),
                cursor.getString(TIME_INDEX));
        cursor.close();

        return quote;
    }

    public List<Quote> getAllQuotes() {
        List<Quote> allQuotes = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] query = new String[] {KEY_ID,KEY_TITLE,KEY_DATE,KEY_TIME};

        Cursor cursor = db.query(TABLE_NAME,query,null,null,null,null,KEY_ID + " DESC",null);
        if (cursor.moveToFirst()) {
            do {
                Quote quote = new Quote();
                quote.setID(Long.parseLong(cursor.getString(ID_INDEX)));
                quote.setTitle(cursor.getString(TITLE_INDEX));
                quote.setDate(cursor.getString(DATE_INDEX));
                quote.setTime(cursor.getString(TIME_INDEX));
                allQuotes.add(quote);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return allQuotes;
    }


    public int editQuote(Quote quote) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        Log.d("Edited", "Edited Title: -> " + quote.getTitle() + "\n ID -> " + quote.getID());
        contentValues.put(KEY_TITLE, quote.getTitle());
        contentValues.put(KEY_DATE, quote.getDate());
        contentValues.put(KEY_TIME, quote.getTime());
        return database.update(TABLE_NAME, contentValues, KEY_ID + "=?", new String[]{String.valueOf(quote.getID())});
    }

    public void deleteQuote(long id) {
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(TABLE_NAME, KEY_ID + "=?", new String[]{String.valueOf(id)});
        database.close();
    }
}
