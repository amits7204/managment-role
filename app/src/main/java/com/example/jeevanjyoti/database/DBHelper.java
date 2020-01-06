package com.example.jeevanjyoti.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.HashMap;

public class DBHelper extends SQLiteOpenHelper {

    private static final String TAG = "DBHelper";
    public static final String DATABASE_NAME = "jeevanjyoti";
    private static final int DATABASE_VERSION = 1;
    public static DBHelper mDBHelper= null;
    private HashMap hp;
    public static final String USER_DETAILS = "users";
    public static final String mVersion = "version";
    public static final String mId = "id";
    public static final String mUniqueId = "uniqueId";
    public static final String mParent = "parent";
    public static final String mFullName = "fullName";
    public static final String mFatherName = "fatherName";
    public static final String mMotherName = "motherName";
    public static final String mMobile = "mobile";
    public static final String mGender = "gender";
    public static final String mDOB = "dob";
    public static final String mMarital = "marital";
    public static final String mQualification = "qualification";
    public static final String mEducationStatus = "educationStatus";
    public static final String mEducDisc = "educationDisc";
    public static final String mOccupation = "occupation";
    public static final String mOccupationDisc = "occupationDisc";
    public static final String mArea = "area";
    public static final String mFlat = "flat";
    public static final String mBuilding = "building";
    public static final String mRoadStreet = "roadStreet";
    public static final String mPinCode = "pinCode";
    public static final String mState = "state";
    public static final String mDistrict = "district";

    private static String CREATE_TABLE_USER_DATA = "CREATE TABLE "+ USER_DETAILS + "( "+mVersion+" " +
            "INTEGER, " +
            mId+" INTEGER PRIMARY KEY, " +
            mUniqueId+" INTEGER, " +
            mParent+" TEXT, "+
            mFullName+" TEXT," +
            mFatherName+" TEXT,"+
            mMotherName+" TEXT,"+
            mMobile+" TEXT," +
            mGender+" TEXT, " +
            mDOB+" TEXT, " +
            mMarital+" TEXT, " +
            mQualification+" TEXT, " +
            mEducationStatus+" TEXT, " +
            mEducDisc+" TEXT, " +
            mOccupation+" TEXT, " +
            mOccupationDisc+" TEXT, " +
            mArea+" TEXT, " +
            mFlat+" TEXT, " +
            mBuilding+" TEXT, " +
            mRoadStreet+" TEXT, " +
            mPinCode+" TEXT, " +
            mState+" TEXT, " +
            mDistrict+" TEXT)";

    public static String[] mUserColumns =
            new String[]{mId,
                    mUniqueId,
                    mParent,
                    mFullName,
                    mFatherName,
                    mMotherName,
                    mMobile,
                    mGender,
                    mDOB,
                    mMarital,
                    mQualification,
                    mEducationStatus,
                    mEducDisc,
                    mOccupation,
                    mOccupationDisc,
                    mArea,
                    mFlat,
                    mBuilding,
                    mRoadStreet,
                    mPinCode,
                    mState,
                    mDistrict};
    public static DBHelper getInstance(Context aContext)
    {
        if (mDBHelper == null)
        {
            mDBHelper = new DBHelper(aContext);
        }
        return mDBHelper;
    }
    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USER_DATA);
    }

    public void saveUserData(Context aContext, int aId, String aUniqueId, String aParent, String aFullName,
                             String aFatherName, String aMother, String aMobile, String aGender,
                             String aDob, String aMarital, String aQualification, String aEducationStatus,
                             String aEduDic, String aOccupation, String aOccupationDisc,
                             String aArea, String aFlat, String aBuilding, String aRoadStreet,
                             String aPincode, String aState, String aDistrict){
        DBHelper lDatabase = DBHelper.getInstance(aContext);
        SQLiteDatabase lDb = lDatabase.getWritableDatabase();

        ContentValues lValues = new ContentValues();
        lValues.put(mVersion, 1);
        lValues.put(mId, aId);
        lValues.put(mUniqueId, aUniqueId);
        lValues.put(mParent, aParent);
        lValues.put(mFullName, aFullName);
        lValues.put(mFatherName, aFatherName);
        lValues.put(mMotherName, aMother);
        lValues.put(mMobile, aMobile);
        lValues.put(mGender, aGender);
        lValues.put(mDOB, aDob);
        lValues.put(mMarital, aMarital);
        lValues.put(mQualification, aQualification);
        lValues.put(mEducationStatus, aEducationStatus);
        lValues.put(mEducDisc, aEduDic);
        lValues.put(mOccupation, aOccupation);
        lValues.put(mOccupationDisc, aOccupationDisc);
        lValues.put(mOccupationDisc, aOccupationDisc);
        lValues.put(mArea, aArea);
        lValues.put(mFlat, aFlat);
        lValues.put(mBuilding, aBuilding);
        lValues.put(mRoadStreet, aRoadStreet);
        lValues.put(mPinCode, aPincode);
        lValues.put(mState, aState);
        lValues.put(mDistrict, aDistrict);
        long lResult = lDb.insert(USER_DETAILS, null, lValues);
        if (lResult == -1){
            Log.w(TAG,"Failed Data: ");
        }else{
            Log.w(TAG,"Insert Data:");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + USER_DETAILS);

    }

    public Cursor getuser() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + USER_DETAILS + " ",
                null);
        return res;
    }

}
