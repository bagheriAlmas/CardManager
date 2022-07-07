package ir.almasapps.almascardmanager.DB.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import ir.almasapps.almascardmanager.DB.Model.Card;
import ir.almasapps.almascardmanager.DB.Model.Job;
import ir.almasapps.almascardmanager.DB.Model.User;

/**
 * Created by mahdi on 12/17/2017 AD.
 */

public class MyDB extends SQLiteOpenHelper {

    public static final String DB_NAME = "db_cartManager";
    public static final int DB_VERSION = 2;
    public static final String TABLE_NAME_USERS = "tbl_users";
    public static final String KEY_USER_ID = "user_id";
    public static final String KEY_USER_USERNAME = "user_username";
    public static final String KEY_USER_PASSWORD = "user_password";
    //--------------------------------------------------------------------------------------
    public static final String TABLE_NAME_CARD = "tbl_cards";
    public static final String KEY_CARD_ID = "card_id";
    public static final String KEY_CARD_BANK = "card_bank";
    public static final String KEY_CARD_TITLE = "card_title";
    public static final String KEY_CARD_NUMBER = "card_number";
    public static final String KEY_CARD_MONEY = "card_money";
    public static final String KEY_CARD_PASSWORD = "card_password";
    public static final String KEY_CARD_PASSWORD2 = "card_password2";
    public static final String KEY_CARD_CVV2 = "card_cvv2";
    public static final String KEY_CARD_EXPIRE_DATE = "card_expireDate";
    //--------------------------------------------------------------------------------------
    public static final String TABLE_NAME_JOB = "tbl_jobs";
    public static final String KEY_JOB_ID = "job_id";
    public static final String KEY_JOB_CARD_ID = "job_cardID";
    public static final String KEY_JOB_TYPE = "job_type";
    public static final String KEY_JOB_COST = "job_cost";
    public static final String KEY_JOB_LAST_COST = "job_last_cost";
    public static final String KEY_JOB_DESCRIPTION = "job_description";
    public static final String KEY_JOB_DATE = "job_date";


//--------------------------------------------------------------------------------------


    public MyDB(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String strSQL_USER = "CREATE TABLE " + TABLE_NAME_USERS + " ( " +
                KEY_USER_ID + " INTEGER PRIMARY KEY , " +
                KEY_USER_USERNAME + " TEXT , " +
                KEY_USER_PASSWORD + " TEXT ) ; ";
        db.execSQL(strSQL_USER);

        String strSQL_CARD = "CREATE TABLE " + TABLE_NAME_CARD + " ( " +
                KEY_CARD_ID + " INTEGER PRIMARY KEY , " +
                KEY_CARD_BANK + " TEXT , " +
                KEY_CARD_TITLE + " TEXT , " +
                KEY_CARD_NUMBER + " TEXT , " +
                KEY_CARD_MONEY + " TEXT , " +
                KEY_CARD_PASSWORD + " TEXT , " +
                KEY_CARD_PASSWORD2 + " TEXT , " +
                KEY_CARD_CVV2 + " TEXT , " +
                KEY_CARD_EXPIRE_DATE + " TEXT ) ; ";
        db.execSQL(strSQL_CARD);

        String strSQL_JOB = "CREATE TABLE " + TABLE_NAME_JOB + " ( " +
                KEY_JOB_ID + " INTEGER PRIMARY KEY , " +
                KEY_JOB_CARD_ID + " TEXT , " +
                KEY_JOB_TYPE + " TEXT , " +
                KEY_JOB_COST + " TEXT , " +
                KEY_JOB_LAST_COST + " TEXT , " +
                KEY_JOB_DESCRIPTION + " TEXT , " +
                KEY_JOB_DATE + " TEXT ) ; ";
        db.execSQL(strSQL_JOB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
//-------------------------------------------------------------------------------------------------------------
//------------------------------------------------------------------------------------------------------------- USER
//-------------------------------------------------------------------------------------------------------------

    public long insertUser(User user) {
        long result = 0;
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USER_USERNAME, user.getUser_username());
        values.put(KEY_USER_PASSWORD, user.getUser_password());
        result = db.insert(TABLE_NAME_USERS, null, values);
        db.close();
        return result;
    }

    public User selectUserByID(User user) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME_USERS, new String[]{KEY_USER_ID, KEY_USER_USERNAME, KEY_USER_PASSWORD}, KEY_USER_ID + " = ? ", new String[]{String.valueOf(user.getUser_id())}, null, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                user.setUser_id(cursor.getInt(0));
                user.setUser_username(cursor.getString(1));
                user.setUser_password(cursor.getString(2));
            }
        }
        cursor.close();
        db.close();
        return user;
    }


    public int checkUser(User user) {
        int result = 0;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME_USERS, new String[]{KEY_USER_ID, KEY_USER_USERNAME, KEY_USER_PASSWORD}, KEY_USER_USERNAME + " LIKE ? AND " + KEY_USER_PASSWORD + " LIKE ? ", new String[]{user.getUser_username(), user.getUser_password()}, null, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                result = cursor.getCount();
            }
        }
        cursor.close();
        db.close();
        return result;
    }


    public int updateUser(User user) {
        int result = 0;
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USER_USERNAME, user.getUser_username());
        values.put(KEY_USER_PASSWORD, user.getUser_password());
        db.update(TABLE_NAME_USERS, values, KEY_USER_ID + " = ? ", new String[]{String.valueOf(user.getUser_id())});
        db.close();
        return result;
    }

    //User TABLE COUNT
    public int getUserCount() {
        int result = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        String strSQL = "select * from " + TABLE_NAME_USERS;

        Cursor cursor = db.rawQuery(strSQL, null);
        if (cursor != null) {
            result = cursor.getCount();
        }
        cursor.close();
        db.close();
        return result;
    }

//-------------------------------------------------------------------------------------------------------------
//------------------------------------------------------------------------------------------------------------- CARD
//-------------------------------------------------------------------------------------------------------------


    public long insertCard(Card card) {
        long result = 0;
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_CARD_BANK, card.getCard_bank());
        values.put(KEY_CARD_TITLE, card.getCard_title());
        values.put(KEY_CARD_NUMBER, card.getCard_number());
        values.put(KEY_CARD_MONEY, card.getCard_money());
        values.put(KEY_CARD_PASSWORD, card.getCard_password());
        values.put(KEY_CARD_PASSWORD2, card.getCard_password2());
        values.put(KEY_CARD_CVV2, card.getCard_cvv2());
        values.put(KEY_CARD_EXPIRE_DATE, card.getCard_expireDate());

        result = db.insert(TABLE_NAME_CARD, null, values);
        db.close();
        return result;
    }


    public List<Card> selectAllCards() {
        List<Card> cardList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String strSQL = "SELECT * FROM " + TABLE_NAME_CARD;
        Cursor cursor = db.rawQuery(strSQL, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    Card tmp = new Card();
                    tmp.setCard_id(cursor.getInt(0));
                    tmp.setCard_bank(cursor.getString(1));
                    tmp.setCard_title(cursor.getString(2));
                    tmp.setCard_number(cursor.getString(3));
                    tmp.setCard_money(cursor.getLong(4));
                    tmp.setCard_password(cursor.getString(5));
                    tmp.setCard_password2(cursor.getString(6));
                    tmp.setCard_cvv2(cursor.getString(7));
                    tmp.setCard_expireDate(cursor.getString(8));

                    cardList.add(tmp);

                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        db.close();
        return cardList;
    }

    /*private int card_id;
        private String card_bank;
        private String card_title;
        private String card_number;
        private String card_password;
        private String card_password2;
        private String card_cvv2;
        private String card_expireDate;*/

    public Card selectCardByID(int ID) {
        Card card = new Card();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME_CARD, new String[]{KEY_CARD_ID, KEY_CARD_BANK, KEY_CARD_TITLE, KEY_CARD_NUMBER, KEY_CARD_MONEY, KEY_CARD_PASSWORD, KEY_CARD_PASSWORD2, KEY_CARD_CVV2, KEY_CARD_EXPIRE_DATE}, KEY_CARD_ID + " = ? ", new String[]{String.valueOf(ID)}, null, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                card.setCard_id(cursor.getInt(0));
                card.setCard_bank(cursor.getString(1));
                card.setCard_title(cursor.getString(2));
                card.setCard_number(cursor.getString(3));
                card.setCard_money(cursor.getLong(4));
                card.setCard_password(cursor.getString(5));
                card.setCard_password2(cursor.getString(6));
                card.setCard_cvv2(cursor.getString(7));
                card.setCard_expireDate(cursor.getString(8));

            }
        }
        cursor.close();
        db.close();
        return card;
    }


    public int updateCardByID(Card card) {
        int result = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_CARD_BANK, card.getCard_bank());
        values.put(KEY_CARD_TITLE, card.getCard_title());
        values.put(KEY_CARD_NUMBER, card.getCard_number());
        values.put(KEY_CARD_MONEY, card.getCard_money());

        result = db.update(TABLE_NAME_CARD, values, KEY_CARD_ID + "=? ", new String[]{card.getCard_id() + ""});
        db.close();
        return result;
    }


    public int updateCardByID_All(Card card) {
        int result = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_CARD_BANK, card.getCard_bank());
        values.put(KEY_CARD_TITLE, card.getCard_title());
        values.put(KEY_CARD_NUMBER, card.getCard_number());
        values.put(KEY_CARD_MONEY, card.getCard_money());
        values.put(KEY_CARD_PASSWORD, card.getCard_password());
        values.put(KEY_CARD_PASSWORD2, card.getCard_password2());
        values.put(KEY_CARD_CVV2, card.getCard_cvv2());
        values.put(KEY_CARD_EXPIRE_DATE, card.getCard_expireDate());
        result = db.update(TABLE_NAME_CARD, values, KEY_CARD_ID + "=? ", new String[]{card.getCard_id() + ""});
        db.close();
        return result;
    }

    public int updateMoney(Card card) {
        int result = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_CARD_MONEY, card.getCard_money());
        result = db.update(TABLE_NAME_CARD, values, KEY_CARD_ID + "=? ", new String[]{String.valueOf(card.getCard_id())});
        db.close();
        return result;
    }

    public int deleteCardByID (int ID){
        int result =0;
        SQLiteDatabase db = this.getWritableDatabase();
        result = db.delete(TABLE_NAME_CARD,KEY_CARD_ID+"= ?",new String[]{String.valueOf(ID)});
        db.close();
        return result;
    }

    //User TABLE COUNT
    public int getCardCount() {
        int result = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        String strSQL = "select * from " + TABLE_NAME_CARD;

        Cursor cursor = db.rawQuery(strSQL, null);
        if (cursor != null) {
            result = cursor.getCount();
        }
        cursor.close();
        db.close();
        return result;
    }
//-------------------------------------------------------------------------------------------------------------
//------------------------------------------------------------------------------------------------------------- JOB
//-------------------------------------------------------------------------------------------------------------

    public long insertJob(Job job) {
        long result = 0;
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_JOB_CARD_ID, job.getJob_cardID());
        values.put(KEY_JOB_TYPE, job.getJob_type());
        values.put(KEY_JOB_COST, job.getJob_cost());
        values.put(KEY_JOB_LAST_COST, job.getJob_last_cost());
        values.put(KEY_JOB_DESCRIPTION, job.getJob_description());
        values.put(KEY_JOB_DATE, job.getJob_date());
        result = db.insert(TABLE_NAME_JOB, null, values);
        db.close();
        return result;
    }


    public List<Job> selectAllJobs() {
        List<Job> jobList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String strSQL = "SELECT * FROM " + TABLE_NAME_JOB;
        Cursor cursor = db.rawQuery(strSQL, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    Job tmp = new Job();
                    tmp.setJob_id(cursor.getInt(0));
                    tmp.setJob_cardID(cursor.getString(1));
                    tmp.setJob_type(cursor.getString(2));
                    tmp.setJob_cost(cursor.getLong(3));
                    tmp.setJob_last_cost(cursor.getLong(4));
                    tmp.setJob_description(cursor.getString(5));
                    tmp.setJob_date(cursor.getString(6));

                    jobList.add(tmp);

                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        db.close();
        return jobList;
    }


    public List<Job> selectAllJobsByDateAndType(int ID, int _viewType, String StartDate, String EndDate) {
        List<Job> jobList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String strSQL="";
        switch (_viewType) {
            case 0:
                strSQL = "SELECT * FROM " + TABLE_NAME_JOB + " WHERE "
                        + KEY_JOB_CARD_ID + " = " + ID + " AND "
                        + KEY_JOB_DATE + " BETWEEN '" + StartDate + "' AND '" + EndDate + "'";
                break;
            case 1:
                //Receive
                strSQL = "SELECT * FROM " + TABLE_NAME_JOB + " WHERE ("
                        + KEY_JOB_CARD_ID + " = " + ID + ") AND ("
                        + KEY_JOB_DATE + " BETWEEN '" + StartDate + "' AND '" + EndDate + "') AND ("
                        + KEY_JOB_TYPE + " LIKE 'برداشت' )";
                break;
            case 2:
                // Spend
                strSQL = "SELECT * FROM " + TABLE_NAME_JOB + " WHERE ("
                        + KEY_JOB_CARD_ID + " = " + ID + ") AND ("
                        + KEY_JOB_DATE + " BETWEEN '" + StartDate + "' AND '" + EndDate + "') AND ("
                        + KEY_JOB_TYPE + " LIKE 'واریز' )";
                break;
        }


        Cursor cursor = db.rawQuery(strSQL, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    Job tmp = new Job();
                    tmp.setJob_id(cursor.getInt(0));
                    tmp.setJob_cardID(cursor.getString(1));
                    tmp.setJob_type(cursor.getString(2));
                    tmp.setJob_cost(cursor.getLong(3));
                    tmp.setJob_last_cost(cursor.getLong(4));
                    tmp.setJob_description(cursor.getString(5));
                    tmp.setJob_date(cursor.getString(6));

                    jobList.add(tmp);

                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        db.close();
        return jobList;
    }

    /*
private int job_id;
private String job_cardID;
private String job_type;
private String job_cost;
private String job_description;
private Time job_time;
private Date job_date;
*/
    public Job selectJobByID(Job job) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME_JOB, new String[]{KEY_JOB_ID, KEY_JOB_CARD_ID, KEY_JOB_TYPE, KEY_JOB_COST, KEY_JOB_LAST_COST, KEY_JOB_DESCRIPTION, KEY_JOB_DATE}, KEY_JOB_ID + " = ? ", new String[]{String.valueOf(job.getJob_id())}, null, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                job.setJob_id(cursor.getInt(0));
                job.setJob_cardID(cursor.getString(1));
                job.setJob_type(cursor.getString(2));
                job.setJob_cost(cursor.getLong(3));
                job.setJob_last_cost(cursor.getLong(4));
                job.setJob_description(cursor.getString(5));
                job.setJob_date(cursor.getString(6));
            }
        }
        cursor.close();
        db.close();
        return job;
    }


    public List<Job> selectJobListByID(int ID , int viewType) {
        List<Job> jobList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String strSQL="";
        switch (viewType){
            case 0:
                strSQL = "SELECT * FROM " + TABLE_NAME_JOB + " WHERE " + KEY_JOB_CARD_ID + " = " + ID;
                break;
            case 1:
                strSQL = "SELECT * FROM " + TABLE_NAME_JOB + " WHERE " + KEY_JOB_CARD_ID + " = " + ID + " AND " + KEY_JOB_TYPE + " LIKE 'برداشت'";
                break;
            case 2:
                strSQL = "SELECT * FROM " + TABLE_NAME_JOB + " WHERE " + KEY_JOB_CARD_ID + " = " + ID + " AND " + KEY_JOB_TYPE + " LIKE 'واریز'";
                break;
        }

        Cursor cursor = db.rawQuery(strSQL, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    Job tmp = new Job();
                    tmp.setJob_id(cursor.getInt(0));
                    tmp.setJob_cardID(cursor.getString(1));
                    tmp.setJob_type(cursor.getString(2));
                    tmp.setJob_cost(cursor.getLong(3));
                    tmp.setJob_last_cost(cursor.getLong(4));
                    tmp.setJob_description(cursor.getString(5));
                    tmp.setJob_date(cursor.getString(6));

                    jobList.add(tmp);

                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        db.close();
        return jobList;
    }


    public int updateJob(Job job) {
        int result = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_JOB_TYPE, job.getJob_type());
        values.put(KEY_JOB_COST, job.getJob_cost());
        values.put(KEY_JOB_LAST_COST, job.getJob_last_cost());
        values.put(KEY_JOB_DESCRIPTION, job.getJob_description());
        values.put(KEY_JOB_DATE, job.getJob_date());
        result = db.update(TABLE_NAME_JOB, values, KEY_JOB_ID + "=? ", new String[]{String.valueOf(job.getJob_id())});
        db.close();
        return result;
    }

    public int deleteJob(Job job) {
        int result = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        result = db.delete(TABLE_NAME_JOB, KEY_JOB_ID + "=? ", new String[]{String.valueOf(job.getJob_id())});
        db.close();
        return result;
    }

    public int deleteJobByCardID(int ID) {
        int result = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        int x = ID;
        result = db.delete(TABLE_NAME_JOB, KEY_JOB_CARD_ID + "=? ", new String[]{ID+""});
        db.close();
        return result;
    }


    //User TABLE COUNT
    public int getJobCount() {
        int result = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        String strSQL = "select * from " + TABLE_NAME_JOB;

        Cursor cursor = db.rawQuery(strSQL, null);
        if (cursor != null) {
            result = cursor.getCount();
        }
        cursor.close();
        db.close();
        return result;
    }


}
