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

import uk.co.willanthony.quotationapp.Job;

public class JobSQLiteCipherHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "JobDB8";
    private static final String TABLE_NAME = "JobTable";
    private static final String PASSWORD = "!?JoBpAsSwOrD?!";

    // columns name for database table
    private static final String KEY_ID = "id";
    private static final String KEY_QUOTE_NUMBER = "quoteNumber";
    private static final String KEY_TITLE = "title";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_COST_MINUS_VAT = "costMinusVAT";
    private static final String KEY_COST_PLUS_VAT = "costPlusVAT";
    private static final String KEY_WORKERS_BOOLEANS = "workerBooleans";
    private static final String KEY_HOURS_BOOLEANS = "hoursBooleans";
    private static final String KEY_FREQUENCY_BOOLEANS = "frequencyBooleans";
    private static final String KEY_PERCENTAGE_BOOLEANS = "percentageBooleans";
    private static final String KEY_MACHINERY_ADDED = "machineryAdded";
    private static final String KEY_MATERIALS_ADDED = "materialsAdded";

    // column index's
    private static final int ID_INDEX = 0;
    private static final int QUOTE_NUMBER_INDEX = 1;
    private static final int TITLE_INDEX = 2;
    private static final int DESCRIPTION_INDEX = 3;
    private static final int COST_MINUS_VAT_INDEX = 4;
    private static final int COST_PLUS_VAT_INDEX = 5;
    private static final int WORKER_BOOLEAN_INDEX = 6;
    private static final int HOURS_BOOLEAN_INDEX = 7;
    private static final int FREQUENCY_BOOLEAN_INDEX = 8;
    private static final int PERCENTAGE_BOOLEAN_INDEX = 9;
    private static final int MACHINERY_INDEX = 10;
    private static final int MATERIALS_INDEX = 11;

    // sql commands
    private static final String CREATE_DB = "CREATE TABLE " + TABLE_NAME + " (" +
            KEY_ID + " INTEGER PRIMARY KEY," +
            KEY_QUOTE_NUMBER + " TEXT," +
            KEY_TITLE + " TEXT," +
            KEY_DESCRIPTION + " TEXT," +
            KEY_COST_MINUS_VAT + " TEXT," +
            KEY_COST_PLUS_VAT + " TEXT," +
            KEY_WORKERS_BOOLEANS + " TEXT," +
            KEY_HOURS_BOOLEANS + " TEXT," +
            KEY_FREQUENCY_BOOLEANS + " TEXT," +
            KEY_PERCENTAGE_BOOLEANS + " TEXT," +
            KEY_MACHINERY_ADDED + " TEXT," +
            KEY_MATERIALS_ADDED + " TEXT" + ")";

    private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + DATABASE_NAME;


    public JobSQLiteCipherHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        SQLiteStatement createDB = db.compileStatement(CREATE_DB);
        createDB.execute();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion >= newVersion) {
            return;
        }

        SQLiteStatement dropSTMT = db.compileStatement(DROP_TABLE);
        onCreate(db);
    }

    public long addJob(Job job) {
        SQLiteDatabase db = this.getWritableDatabase(PASSWORD);

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_TITLE, job.getTitle());
        contentValues.put(KEY_QUOTE_NUMBER, job.getQuoteNumber());
        contentValues.put(KEY_DESCRIPTION, job.getDescription());
        contentValues.put(KEY_COST_MINUS_VAT, job.getCostMinusVAT());
        contentValues.put(KEY_COST_PLUS_VAT, job.getCostPlusVAT());
        contentValues.put(KEY_WORKERS_BOOLEANS, job.getWorkersSelectedString());
        contentValues.put(KEY_HOURS_BOOLEANS, job.getHoursSelectedString());
        contentValues.put(KEY_COST_MINUS_VAT, job.getFrequencySelectedString());
        contentValues.put(KEY_COST_PLUS_VAT, job.getPercentageSelectedString());

        // inserting data into db
        long ID = db.insert(TABLE_NAME, null, contentValues);
        return ID;
    }

    public Job getJob(long id) {
        SQLiteDatabase db = this.getReadableDatabase(PASSWORD);

        String[] query = new String[]{KEY_ID, KEY_QUOTE_NUMBER, KEY_TITLE, KEY_DESCRIPTION, KEY_COST_MINUS_VAT, KEY_COST_PLUS_VAT, KEY_WORKERS_BOOLEANS,
                KEY_HOURS_BOOLEANS, KEY_FREQUENCY_BOOLEANS, KEY_PERCENTAGE_BOOLEANS, KEY_MACHINERY_ADDED, KEY_MATERIALS_ADDED};

        Cursor cursor = db.query(TABLE_NAME, query, KEY_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Job job = new Job(
                Long.parseLong(cursor.getString(ID_INDEX)),
                Long.parseLong(cursor.getString(QUOTE_NUMBER_INDEX)),
                cursor.getString(TITLE_INDEX),
                cursor.getString(DESCRIPTION_INDEX),
                cursor.getString(COST_MINUS_VAT_INDEX),
                cursor.getString(COST_PLUS_VAT_INDEX),
                cursor.getString(WORKER_BOOLEAN_INDEX),
                cursor.getString(HOURS_BOOLEAN_INDEX),
                cursor.getString(FREQUENCY_BOOLEAN_INDEX),
                cursor.getString(PERCENTAGE_BOOLEAN_INDEX),
                cursor.getString(MACHINERY_INDEX),
                cursor.getString(MATERIALS_INDEX));
        cursor.close();

        return job;
    }

    public List<Job> getQuoteJobList(long quoteID) {
        List<Job> allJobs = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase(PASSWORD);
        String[] query = new String[]{KEY_ID, KEY_QUOTE_NUMBER, KEY_TITLE, KEY_DESCRIPTION, KEY_COST_MINUS_VAT, KEY_COST_PLUS_VAT, KEY_WORKERS_BOOLEANS,
                KEY_HOURS_BOOLEANS, KEY_FREQUENCY_BOOLEANS, KEY_PERCENTAGE_BOOLEANS, KEY_MACHINERY_ADDED, KEY_MATERIALS_ADDED};

        android.database.Cursor cursor = db.query(TABLE_NAME, query, KEY_QUOTE_NUMBER + "=?",
                new String[]{String.valueOf(quoteID)}, null, null, KEY_QUOTE_NUMBER + " DESC", null);

        if (cursor.moveToFirst()) {
            do {
                Job job = new Job();
                job.setID(Long.parseLong(cursor.getString(ID_INDEX)));
                job.setQuoteNumber(Long.parseLong(cursor.getString(QUOTE_NUMBER_INDEX)));
                job.setTitle(cursor.getString(TITLE_INDEX));
                job.setDescription(cursor.getString(DESCRIPTION_INDEX));
                job.setCostMinusVAT(cursor.getString(COST_MINUS_VAT_INDEX));
                job.setCostPlusVAT(cursor.getString(COST_PLUS_VAT_INDEX));
                job.setWorkersSelectedString(cursor.getString(WORKER_BOOLEAN_INDEX));
                job.setHoursSelectedString(cursor.getString(HOURS_BOOLEAN_INDEX));
                job.setFrequencySelectedString(cursor.getString(FREQUENCY_BOOLEAN_INDEX));
                job.setPercentageSelectedString(cursor.getString(PERCENTAGE_BOOLEAN_INDEX));
                job.setMachinerySelectedString(cursor.getString(MACHINERY_INDEX));
                job.setMaterialsSelectedString(cursor.getString(MATERIALS_INDEX));

                allJobs.add(job);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return allJobs;
    }

    public int editJob(Job job) {
        SQLiteDatabase db = this.getWritableDatabase(PASSWORD);
        ContentValues contentValues = new ContentValues();
        Log.d("Edited", "Edited Title: -> " + job.getTitle() + "\n ID -> " + job.getID());
        contentValues.put(KEY_TITLE, job.getTitle());
        contentValues.put(KEY_QUOTE_NUMBER, job.getQuoteNumber());
        contentValues.put(KEY_DESCRIPTION, job.getDescription());
        contentValues.put(KEY_COST_MINUS_VAT, job.getCostMinusVAT());
        contentValues.put(KEY_COST_PLUS_VAT, job.getCostPlusVAT());
        contentValues.put(KEY_WORKERS_BOOLEANS, job.getWorkersSelectedString());
        contentValues.put(KEY_HOURS_BOOLEANS, job.getHoursSelectedString());
        contentValues.put(KEY_FREQUENCY_BOOLEANS, job.getFrequencySelectedString());
        contentValues.put(KEY_PERCENTAGE_BOOLEANS, job.getPercentageSelectedString());
        contentValues.put(KEY_MACHINERY_ADDED, job.getMachinerySelectedString());
        contentValues.put(KEY_MACHINERY_ADDED, job.getMachinerySelectedString());

        return db.update(TABLE_NAME, contentValues, KEY_ID + "=?", new String[]{String.valueOf(job.getID())});
    }
    public void deleteQuote(long id) {
        SQLiteDatabase db = this.getWritableDatabase(PASSWORD);
        db.delete(TABLE_NAME, KEY_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }
}


















































