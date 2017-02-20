package com.bartronics.timeattendance.model.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.bartronics.timeattendance.model.ReportModel;

import java.util.ArrayList;
import java.util.List;

import static com.bartronics.timeattendance.Util.Constant.EMP_DEPARTMENT;
import static com.bartronics.timeattendance.Util.Constant.EMP_DESCRIPTION;
import static com.bartronics.timeattendance.Util.Constant.EMP_DESIGNATION;
import static com.bartronics.timeattendance.Util.Constant.EMP_ID;
import static com.bartronics.timeattendance.Util.Constant.EMP_IN_DATE;
import static com.bartronics.timeattendance.Util.Constant.EMP_IN_TIME;
import static com.bartronics.timeattendance.Util.Constant.EMP_LOCATION;
import static com.bartronics.timeattendance.Util.Constant.EMP_NAME;
import static com.bartronics.timeattendance.Util.Constant.EMP_NUMBER;
import static com.bartronics.timeattendance.Util.Constant.EMP_OUT_TIME;
import static com.bartronics.timeattendance.Util.Constant.EMP_REPORT_TABLE;
import static com.bartronics.timeattendance.Util.Constant.EMP_TABLE;
import static com.bartronics.timeattendance.Util.Constant.EMP_TABLE_VERSION;
import static com.bartronics.timeattendance.Util.Constant.EMP_WORK_LOCATION;
import static com.bartronics.timeattendance.Util.Constant.KEY_ID;

/**
 * Created by anand.chandaliya on 16-02-2017.
 */

public class DbHandler extends SQLiteOpenHelper {



    public DbHandler(Context context) {
        super(context, EMP_TABLE, null, EMP_TABLE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_EMP_TABLE = "CREATE TABLE " + EMP_TABLE + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + EMP_NAME + " TEXT,"
                + EMP_ID + " INTEGER,"
                + EMP_DESCRIPTION + " TEXT,"
                + EMP_DESIGNATION + " TEXT,"
                + EMP_IN_DATE + " TEXT,"
                + EMP_IN_TIME + " TEXT,"
                + EMP_OUT_TIME + " TEXT,"
                + EMP_LOCATION + " TEXT,"
                + EMP_NUMBER + " INTEGER,"
                + EMP_WORK_LOCATION + " TEXT,"
                + EMP_DEPARTMENT + " TEXT" + ")";

        String CREATE_REPORT_TABLE = "CREATE TABLE " + EMP_REPORT_TABLE + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + EMP_NAME + " TEXT,"
                + EMP_ID + " INTEGER,"
                + EMP_DESCRIPTION + " TEXT,"
                + EMP_DESIGNATION + " TEXT,"
                + EMP_IN_DATE + " TEXT,"
                + EMP_IN_TIME + " TEXT,"
                + EMP_OUT_TIME + " TEXT,"
                + EMP_LOCATION + " TEXT,"
                + EMP_NUMBER + " INTEGER,"
                + EMP_WORK_LOCATION + " TEXT,"
                + EMP_DEPARTMENT + " TEXT" + ")";
        db.execSQL(CREATE_EMP_TABLE);
        db.execSQL(CREATE_REPORT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + EMP_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + EMP_REPORT_TABLE);
        // Creating tables again
        onCreate(db);
    }



    // Adding new shop
    public void addEmp(ReportModel emp) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(EMP_ID, emp.getEmpId()); // Emp ID
        values.put(EMP_NUMBER, emp.getEmpNumber()); // Emp Pin Number
        values.put(EMP_NAME, emp.getEmpName()); // Emp Name
        values.put(EMP_DEPARTMENT, emp.getEmpDepartment()); // Emp Department
        values.put(EMP_DESIGNATION, emp.getEmpDesignaiton()); // Emp Designation
        values.put(EMP_WORK_LOCATION, emp.getEmpWorkLocation()); // Emp Work Location

        // Inserting Row
        db.insert(EMP_TABLE, null, values);
        db.close(); // Closing database connection
    }

    public void addEmpSwipeInAndSwipeOut(ReportModel emp) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(EMP_ID, emp.getEmpId()); // Emp ID
        values.put(EMP_NUMBER, emp.getEmpNumber()); // Emp Pin Number
        values.put(EMP_NAME, emp.getEmpName()); // Emp Name
        values.put(EMP_DEPARTMENT, emp.getEmpDepartment()); // Emp Department
        values.put(EMP_DESIGNATION, emp.getEmpDesignaiton()); // Emp Designation
        values.put(EMP_WORK_LOCATION, emp.getEmpWorkLocation()); // Emp Work Location

        values.put(EMP_LOCATION, emp.getEmpLocation()); // Emp Current Location
        values.put(EMP_IN_DATE, emp.getEmpInDate()); // Emp In Date
        values.put(EMP_IN_TIME, emp.getEmpInTime()); // Emp In Time
        values.put(EMP_OUT_TIME, emp.getEmpOutTime()); // Emp Out Time
        values.put(EMP_DESCRIPTION, emp.getEmpDescriton()); // Emp Description

        // Inserting Row
        db.insert(EMP_REPORT_TABLE, null, values);
        db.close(); // Closing database connection
    }
    // Getting one shop
    public ReportModel getEmp(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(EMP_TABLE, new String[]{KEY_ID,
                        EMP_NAME, EMP_DESIGNATION}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();


        ReportModel emp = new ReportModel(cursor.getString(cursor.getColumnIndex(EMP_NAME)),
                Integer.parseInt(cursor.getString(cursor.getColumnIndex(EMP_ID))),
                cursor.getString(cursor.getColumnIndex(EMP_DESCRIPTION)),
                cursor.getString(cursor.getColumnIndex(EMP_NUMBER)),
                cursor.getString(cursor.getColumnIndex(EMP_IN_TIME)),
                cursor.getString(cursor.getColumnIndex(EMP_OUT_TIME)),
                cursor.getString(cursor.getColumnIndex(EMP_IN_DATE)),
                cursor.getString(cursor.getColumnIndex(EMP_LOCATION)),
                cursor.getString(cursor.getColumnIndex(EMP_DEPARTMENT)),
                cursor.getString(cursor.getColumnIndex(EMP_WORK_LOCATION)),
                cursor.getString(cursor.getColumnIndex(EMP_DESIGNATION))

        );
        //emp.setEmpId(Integer.parseInt(cursor.getString(0)));
       /* emp.setEmpName(cursor.getString(1));
        emp.setEmpId(Integer.parseInt(cursor.getString(2)));
        emp.setEmpDescriton(cursor.getString(3));
        emp.setEmpDesignaiton(cursor.getString(4));
        emp.setEmpInDate(cursor.getString(5));
        emp.setEmpInTime(cursor.getString(6));
        emp.setEmpOutTime(cursor.getString(7));
        emp.setEmpLocation(cursor.getString(8));*/
       /* Emp contact = new Emp(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));*/
    // return shop
        return emp;
    }



    public ReportModel getEmpInOutTime(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(EMP_REPORT_TABLE, new String[]{KEY_ID,
                        EMP_NAME, EMP_DESIGNATION}, EMP_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();


        ReportModel emp = new ReportModel(cursor.getString(cursor.getColumnIndex(EMP_NAME)),
                Integer.parseInt(cursor.getString(cursor.getColumnIndex(EMP_ID))),
                cursor.getString(cursor.getColumnIndex(EMP_DESCRIPTION)),
                cursor.getString(cursor.getColumnIndex(EMP_NUMBER)),
                cursor.getString(cursor.getColumnIndex(EMP_IN_TIME)),
                cursor.getString(cursor.getColumnIndex(EMP_OUT_TIME)),
                cursor.getString(cursor.getColumnIndex(EMP_IN_DATE)),
                cursor.getString(cursor.getColumnIndex(EMP_LOCATION)),
                cursor.getString(cursor.getColumnIndex(EMP_DEPARTMENT)),
                cursor.getString(cursor.getColumnIndex(EMP_WORK_LOCATION)),
                cursor.getString(cursor.getColumnIndex(EMP_DESIGNATION))

        );

        return emp;
    }



    // Getting All Emps
    public List<ReportModel> getAllEmps() {
        List<ReportModel> empList = new ArrayList<ReportModel>();
    // Select All Query
        String selectQuery = "SELECT * FROM " + EMP_TABLE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all row123456s and adding to list
        if (cursor.moveToFirst()) {
            do {
                ReportModel emp = new ReportModel();
                emp.setEmpId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(EMP_ID))));
                emp.setEmpName(cursor.getString(cursor.getColumnIndex(EMP_NAME)));
                emp.setEmpDepartment(cursor.getString(cursor.getColumnIndex(EMP_DEPARTMENT)));
                emp.setEmpWorkLocation(cursor.getString(cursor.getColumnIndex(EMP_WORK_LOCATION)));
                emp.setEmpDesignaiton(cursor.getString(cursor.getColumnIndex(EMP_DESIGNATION)));
                // Adding contact to list
                empList.add(emp);
            } while (cursor.moveToNext());
        }

    // return contact list
        return empList;
    }

    // Getting All Emps
    public List<ReportModel> getAllReport() {
        List<ReportModel> empList = new ArrayList<ReportModel>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + EMP_REPORT_TABLE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all row123456s and adding to list
        if (cursor.moveToFirst()) {
            do {
                ReportModel emp = new ReportModel();
                emp.setEmpId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(EMP_ID))));
                emp.setEmpName(cursor.getString(cursor.getColumnIndex(EMP_NAME)));
                emp.setEmpDepartment(cursor.getString(cursor.getColumnIndex(EMP_DEPARTMENT)));
                emp.setEmpWorkLocation(cursor.getString(cursor.getColumnIndex(EMP_WORK_LOCATION)));
                emp.setEmpDesignaiton(cursor.getString(cursor.getColumnIndex(EMP_DESIGNATION)));

                emp.setEmpInDate(cursor.getString(cursor.getColumnIndex(EMP_IN_DATE)));
                emp.setEmpInTime(cursor.getString(cursor.getColumnIndex(EMP_IN_TIME)));
                emp.setEmpOutTime(cursor.getString(cursor.getColumnIndex(EMP_OUT_TIME)));
                emp.setEmpLocation(cursor.getString(cursor.getColumnIndex(EMP_LOCATION)));
                // Adding contact to list
                empList.add(emp);
            } while (cursor.moveToNext());
        }

        // return contact list
        return empList;
    }


    // Getting All Emps
    public List<ReportModel> getEmpsById(int empId) {
        List<ReportModel> empList = new ArrayList<ReportModel>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + EMP_TABLE + " WHERE " + EMP_ID +" = "+ empId;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ReportModel emp = new ReportModel();
                emp.setEmpId(Integer.parseInt(cursor.getString(0)));
                emp.setEmpName(cursor.getString(1));
                emp.setEmpDepartment(cursor.getString(2));
                // Adding contact to list
                empList.add(emp);
            } while (cursor.moveToNext());
        }

        // return contact list
        return empList;
    }

    // Getting shops Count
    public int getEmpsCount() {
        String countQuery = "SELECT * FROM " + EMP_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }
    // Updating a shop
    public int updateEmp(ReportModel emp) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(EMP_ID, emp.getEmpId()); // Emp ID
        values.put(EMP_NUMBER, emp.getEmpNumber()); // Emp Pin Number
        values.put(EMP_NAME, emp.getEmpName()); // Emp Name
        values.put(EMP_DEPARTMENT, emp.getEmpDepartment()); // Emp Department
        values.put(EMP_DESIGNATION, emp.getEmpDesignaiton()); // Emp Designation
        values.put(EMP_WORK_LOCATION, emp.getEmpWorkLocation()); // Emp Work Location

        values.put(EMP_LOCATION, emp.getEmpId()); // Current Location
        values.put(EMP_IN_DATE, emp.getEmpNumber()); // In Date
        values.put(EMP_IN_TIME, emp.getEmpName()); // In Time
        values.put(EMP_OUT_TIME, emp.getEmpDepartment()); // Out Time


        // updating row
        return db.update(EMP_TABLE, values, KEY_ID + " = ?",
                new String[]{String.valueOf(emp.getKeyId())});
    }

    // Deleting a shop
    public void deleteEmp(ReportModel emp) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(EMP_TABLE, KEY_ID + " = ?",
                new String[] { String.valueOf(emp.getKeyId()) });
        db.close();
    }
}
