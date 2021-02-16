package uk.co.willanthony.quotationapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import net.sqlcipher.Cursor;
import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteOpenHelper;
import net.sqlcipher.database.SQLiteStatement;

import java.util.ArrayList;
import java.util.List;

import uk.co.willanthony.quotationapp.Quote;

public class QuoteSQLiteCypherHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "QuoteDB8";
    private static final String TABLE_NAME = "QuoteTable";
    private static final String PASSWORD = "!?PaSsWoRd?!";

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

    public QuoteSQLiteCypherHelper(Context context) {
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
        SQLiteDatabase db = this.getWritableDatabase(PASSWORD);
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_TITLE, quote.getTitle());
        contentValues.put(KEY_DATE, quote.getDate());
        contentValues.put(KEY_TIME, quote.getTime());

        long ID = db.insert(TABLE_NAME,null,contentValues);
        return ID;
    }

    public Quote getQuote(long id) {

        // Open cipher-database using password, then return cursor pointing at the row with the id requested
        SQLiteDatabase db = this.getReadableDatabase(PASSWORD);
        String[] query = new String[] {KEY_ID,KEY_TITLE,KEY_DATE,KEY_TIME};
        Cursor cursor = db.query(TABLE_NAME,query,KEY_ID + "=?",new String[]{String.valueOf(id)},null,null,null,null);
        if(cursor != null) {
            cursor.moveToFirst();
        }

        // Using cursor, return build Quote object and return it
        Quote quote = new Quote(Long.parseLong(cursor.getString(ID_INDEX)),
                cursor.getString(TITLE_INDEX),
                cursor.getString(DATE_INDEX),
                cursor.getString(TIME_INDEX));
        cursor.close();

        return quote;
    }

    public List<Quote> getAllQuotes() {

        // Open cipher-database using password, then return cursor with query to access all columns
        List<Quote> allQuotes = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase(PASSWORD);
        String[] query = new String[] {KEY_ID,KEY_TITLE,KEY_DATE,KEY_TIME};
        Cursor cursor = db.query(TABLE_NAME,query,null,null,null,null,KEY_ID + " DESC",null);

        if (cursor.moveToFirst()) {

            // Loop through all rows, creating all quotes using data retrieved and add to a list which is then returned
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

        // Open cipher-database using password, populate ContentValues and update columns
        SQLiteDatabase db = this.getWritableDatabase(PASSWORD);
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_TITLE, quote.getTitle());
        contentValues.put(KEY_DATE, quote.getDate());
        contentValues.put(KEY_TIME, quote.getTime());

        // returns number of rows edited
        return db.update(TABLE_NAME, contentValues, KEY_ID + "=?", new String[]{String.valueOf(quote.getID())});
    }

    public void deleteQuote(long id) {
        SQLiteDatabase db = this.getWritableDatabase(PASSWORD);
        db.delete(TABLE_NAME, KEY_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }
}










































