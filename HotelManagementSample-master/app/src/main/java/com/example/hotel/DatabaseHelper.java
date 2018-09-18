package com.example.hotel;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATA_BASE_NAME = "hotel";
    private SQLiteDatabase db;

    private static final int DATA_BASE_VERSION = 18;

    //SQLiteDatabase db = this.getWritableDatabase();
    public Cursor cursor;

    //table name
    private static final String CUSTOMER_TABLE = "cust_tab";
    private static final String RESERVATION_TABLE = "reservation_tab";
//    private static final String PLACED_ORDER = "placed_order";

    //column name of cust_tab
    private static final String C_ID = "_id";
    private static final String C_NAME = "cust_name";
    private static final String C_PASSWORD = "cust_password";
    private static final String C_EMAIL = "cust_email";

    //column name of reservation_tab
    private static final String R_NAME = "reservation_name";
    private static final String R_NO = "reservation_no";
    private static final String R_ROOMS = "reservation_rooms";
    private static final String R_PEOPLE = "reservation_people";
    private static final String R_DATE_FROM = "reservation_date_from";
    private static final String R_DATE_TO= "reservation_date_to";

    private String CREATE_C_TABLE = "CREATE TABLE " + CUSTOMER_TABLE + "("
            + C_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + C_NAME + " TEXT NOT NULL,"
            + C_PASSWORD + " TEXT," + C_EMAIL + " TEXT );";

    private String CREATE_O_TABLE = "CREATE TABLE " + RESERVATION_TABLE + "("
            + R_NO + " INTEGER PRIMARY KEY, "
            + R_NAME + " TEXT NOT NULL," + C_NAME + " TEXT NOT NULL," + R_ROOMS + " TEXT," + R_PEOPLE  + " TEXT NOT NULL, " + R_DATE_FROM +" TEXT NOT NULL, " +
            R_DATE_TO +" TEXT NOT NULL );";


    public DatabaseHelper(Context context) {
        super(context, DATA_BASE_NAME, null, DATA_BASE_VERSION);

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_C_TABLE);
        db.execSQL(CREATE_O_TABLE);
//        db.execSQL(CREAT_P_TABLE);
        //Log.e("db on create call","called");

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + CUSTOMER_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + RESERVATION_TABLE);

        // Create tables again
        onCreate(db);

    }

    //insert order
    public void addOrder(String name, String customerName, String rooms, String people, String formDate, String toDate) {
        ContentValues ocv = new ContentValues();
        ocv.put(R_NAME, name);
        ocv.put(C_NAME, customerName);
        ocv.put(R_ROOMS, rooms);
        ocv.put(R_DATE_FROM, formDate);
        ocv.put(R_DATE_TO, toDate);
        ocv.put(R_PEOPLE , people);
        db = this.getWritableDatabase();
        db.insert(RESERVATION_TABLE, null, ocv);
        db.close();

    }

    //insert value
    public void addCustomer(Customer customer) {

        ContentValues cv = new ContentValues();
        cv.put(C_NAME, customer.getName());
        cv.put(C_PASSWORD, customer.getPassword());
        cv.put(C_EMAIL, customer.getEmail());

        //insert row
        db = this.getWritableDatabase();
        db.insert(CUSTOMER_TABLE, null, cv);
        db.close();
    }

    public List<Reservation> getReservations() {
        db = this.getWritableDatabase();
        cursor = db.rawQuery("SELECT * FROM " + RESERVATION_TABLE + ";", null);

        int count = cursor.getCount();

        List<Reservation> reservations = new ArrayList<>();

        if (count > 0) {
            while (cursor.moveToNext()) {
                int nameIndex = cursor.getColumnIndex(R_NAME);
                int peopleIndex = cursor.getColumnIndex(R_PEOPLE);
                int roomsIndex = cursor.getColumnIndex(R_ROOMS);
                int dateIndexFrom = cursor.getColumnIndex(R_DATE_FROM);
                int dateToIndex = cursor.getColumnIndex(R_DATE_TO);

                String name = cursor.getString(nameIndex);
                String people = cursor.getString(peopleIndex);
                String rooms = cursor.getString(roomsIndex);
                String dateFrom = cursor.getString(dateIndexFrom);
                String dateTo = cursor.getString(dateToIndex);

                reservations.add(new Reservation(name, rooms, people, dateFrom, dateTo));

            }
        }
        return reservations;

    }

    public boolean validateUser(String userName, String password) {

        db = this.getWritableDatabase();
        cursor = db.rawQuery("SELECT * FROM " + CUSTOMER_TABLE + " WHERE "
                + C_NAME + "='" + userName + "'AND " + C_PASSWORD + "='" + password + "'", null);
        return cursor.getCount() > 0;

    }

    public boolean isUserAlreadyRegistered(String userName) {

        db = this.getWritableDatabase();
        cursor = db.rawQuery("SELECT * FROM " + CUSTOMER_TABLE + " WHERE "
                + C_NAME + "='" + userName + "'", null);
        return cursor.getCount() > 0;

    }

}
