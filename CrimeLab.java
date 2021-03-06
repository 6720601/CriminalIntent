package com.ctech.ingalls.criminalintent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ctech.ingalls.criminalintent.database.CrimeBaseHelper;
import com.ctech.ingalls.criminalintent.database.CrimeCursorWrapper;
import com.ctech.ingalls.criminalintent.database.CrimeDbSchema;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CrimeLab {

    private static CrimeLab sCrimeLab;


    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static CrimeLab get(Context context) {
        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

    private CrimeLab(Context context) {

        mContext = context.getApplicationContext();
        mDatabase = new CrimeBaseHelper(mContext).getWritableDatabase();
        }


    public void addCrime(Crime c) {

        ContentValues newValues = getContentValues(c);
        mDatabase.insert(CrimeDbSchema.CrimeTable.NAME, null, newValues);
    }

    public List<Crime> getCrimes() {

        List<Crime> crimes = new ArrayList<>();

        CrimeCursorWrapper crimeCursor = queryCrimes( null, null);

        try {
            crimeCursor.moveToFirst();
            while (crimeCursor.isAfterLast() != true) {
                Crime thisCrime = crimeCursor.getCrime();
                crimes.add(thisCrime);
                crimeCursor.moveToNext();
            }
        } finally {
            crimeCursor.close();
        }

        return crimes;

    }

    public Crime getCrime(UUID id) {
        String[] searchArgs = new String[] {id.toString()};

        CrimeCursorWrapper crimeCursor = queryCrimes(
                CrimeDbSchema.CrimeTable.Columns.UUID + " = ?", searchArgs);

        try {
            if (crimeCursor.getCount() == 0) {
                return null;
            } else {
                crimeCursor.moveToFirst();
                return crimeCursor.getCrime();
            }
        } finally {
            crimeCursor.close();
        }
    }


    private static ContentValues getContentValues(Crime crime) {
        ContentValues myContentValues = new ContentValues();
        myContentValues.put(CrimeDbSchema.CrimeTable.Columns.UUID, crime.getId().toString());
        myContentValues.put(CrimeDbSchema.CrimeTable.Columns.TITLE, crime.getTitle());
        myContentValues.put(CrimeDbSchema.CrimeTable.Columns.DATE, crime.getDate().getTime());
        myContentValues.put(CrimeDbSchema.CrimeTable.Columns.SOLVED, crime.isSolved() ? 1 : 0);
        myContentValues.put(CrimeDbSchema.CrimeTable.Columns.SUSPECT, crime.getSuspect());

        return myContentValues;
        }

    public void updateCrime(Crime c) {
        String crimeId = c.getId().toString();
        ContentValues newValues = getContentValues(c);

        String searchString = CrimeDbSchema.CrimeTable.Columns.UUID + " = ?";
        String[] searchArgs = new String[] { crimeId };

        mDatabase.update(CrimeDbSchema.CrimeTable.NAME, newValues, searchString, searchArgs);
    }

    public File getPhotoFile(Crime crime) {
        File filesDir = mContext.getFilesDir();
        return new File(filesDir, crime.getPhotoFilename());
    }

    private CrimeCursorWrapper queryCrimes(String whereClause, String[] whereArgs) {

        Cursor cursor = mDatabase.query(
                CrimeDbSchema.CrimeTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null);

        return new CrimeCursorWrapper(cursor);
    }

    }


