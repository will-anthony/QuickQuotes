package uk.co.willanthony.quotationapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import uk.co.willanthony.quotationapp.Job;

public class JobDatabase extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "JobDB1";
    private static final String TABLE_NAME = "JobTable";

    // columns name for database table
    private static final String KEY_ID = "id";
    private static final String KEY_QUOTE_NUMBER = "quoteNumber";
    private static final String KEY_TITLE = "title";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_COST_MINUS_VAT = "costMinusVAT";
    private static final String KEY_COST_PLUS_VAT = "costPlusVAT";

    // column index's
    private static final int ID_INDEX = 0;
    private static final int QUOTE_NUMBER_INDEX = 1;
    private static final int TITLE_INDEX = 2;
    private static final int DESCRIPTION_INDEX = 3;
    private static final int COST_MINUS_VAT_INDEX = 4;
    private static final int COST_PLUS_VAT_INDEX = 5;


    public JobDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {

        String createDB = "CREATE TABLE " + TABLE_NAME + " (" +
                KEY_ID + " INTEGER PRIMARY KEY," +
                KEY_QUOTE_NUMBER + " TEXT," +
                KEY_TITLE + " TEXT," +
                KEY_DESCRIPTION + " TEXT," +
                KEY_COST_MINUS_VAT + " TEXT," +
                KEY_COST_PLUS_VAT + " TEXT" + ")";

        database.execSQL(createDB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        if (oldVersion >= newVersion) {
            return;
        }
        database.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME);
        onCreate(database);
    }

    public long addJob(Job job) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_TITLE, job.getTitle());
        contentValues.put(KEY_QUOTE_NUMBER, job.getQuoteNumber());
        contentValues.put(KEY_DESCRIPTION, job.getDescription());
        contentValues.put(KEY_COST_MINUS_VAT, job.getCostMinusVAT());
        contentValues.put(KEY_COST_PLUS_VAT, job.getCostPlusVAT());

        // inserting data into db
        long ID = database.insert(TABLE_NAME, null, contentValues);
        Log.d("Insert to job database", "ID -> " + ID);
        return ID;

        // quote needs a field variable - list of job ids
    }

    public Job getJob(long id) {
        SQLiteDatabase database = this.getWritableDatabase();
        String[] query = new String[]{KEY_ID, KEY_QUOTE_NUMBER, KEY_TITLE, KEY_DESCRIPTION, KEY_COST_MINUS_VAT, KEY_COST_PLUS_VAT};
        Cursor cursor = database.query(TABLE_NAME, query, KEY_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Job job = new Job(
                Long.parseLong(cursor.getString(ID_INDEX)),
                Long.parseLong(cursor.getString(QUOTE_NUMBER_INDEX)),
                cursor.getString(TITLE_INDEX),
                cursor.getString(DESCRIPTION_INDEX),
                cursor.getString(COST_MINUS_VAT_INDEX),
                cursor.getString(COST_PLUS_VAT_INDEX));
        cursor.close();
        return job;
    }

    public void updateJobID(long ID) {
        SQLiteDatabase database = getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + KEY_QUOTE_NUMBER +
                "=" + ID + " WHERE " + KEY_QUOTE_NUMBER + "=" + Long.MAX_VALUE;
        database.execSQL(query);
    }



    public List<Job> getAllJobs() {
        List<Job> allJobs = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + KEY_ID + " DESC";
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Job job = new Job();
                job.setID(Long.parseLong(cursor.getString(ID_INDEX)));
                job.setQuoteNumber(Long.parseLong(cursor.getString(QUOTE_NUMBER_INDEX)));
                job.setTitle(cursor.getString(TITLE_INDEX));
                job.setDescription(cursor.getString(DESCRIPTION_INDEX));
                job.setCostMinusVAT(cursor.getString(COST_MINUS_VAT_INDEX));
                job.setCostPlusVAT(cursor.getString(COST_PLUS_VAT_INDEX));

                allJobs.add(job);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return allJobs;
    }

    public List<Job> getQuoteJobList(long quoteID) {
        List<Job> allJobs = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + KEY_QUOTE_NUMBER + "=" + quoteID + " ORDER BY " + KEY_ID + " DESC";
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Job job = new Job();
                job.setID(Long.parseLong(cursor.getString(ID_INDEX)));
                job.setQuoteNumber(Long.parseLong(cursor.getString(QUOTE_NUMBER_INDEX)));
                job.setTitle(cursor.getString(TITLE_INDEX));
                job.setDescription(cursor.getString(DESCRIPTION_INDEX));
                job.setCostMinusVAT(cursor.getString(COST_MINUS_VAT_INDEX));
                job.setCostPlusVAT(cursor.getString(COST_PLUS_VAT_INDEX));

                allJobs.add(job);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return allJobs;
    }

    public int editJob(Job job) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        Log.d("Edited", "Edited Title: -> " + job.getTitle() + "\n ID -> " + job.getID());
        contentValues.put(KEY_TITLE, job.getTitle());
        contentValues.put(KEY_QUOTE_NUMBER, job.getQuoteNumber());
        contentValues.put(KEY_DESCRIPTION, job.getDescription());
        contentValues.put(KEY_COST_MINUS_VAT, job.getCostMinusVAT());
        contentValues.put(KEY_COST_PLUS_VAT, job.getCostMinusVAT());

        return database.update(TABLE_NAME, contentValues, KEY_ID + "=?", new String[]{String.valueOf(job.getID())});
    }

    public void deleteQuote(long id) {
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(TABLE_NAME, KEY_ID + "=?", new String[]{String.valueOf(id)});
        database.close();
    }
}
