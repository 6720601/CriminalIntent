package com.ctech.ingalls.criminalintent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ctech.ingalls.criminalintent.database.CrimeBaseHelper;
import com.ctech.ingalls.criminalintent.database.CrimeDbSchema;

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
    }

    public Crime getCrime(UUID id) {
    }

    private static ContentValues getContentValues(Crime crime) {
        ContentValues myContentValues = new ContentValues();
        myContentValues.put(CrimeDbSchema.CrimeTable.Columns.UUID, crime.getId().toString());
        myContentValues.put(CrimeDbSchema.CrimeTable.Columns.TITLE, crime.getTitle());
        myContentValues.put(CrimeDbSchema.CrimeTable.Columns.DATE, crime.getDate().getTime());
        myContentValues.put(CrimeDbSchema.CrimeTable.Columns.SOLVED, crime.isSolved() ? 1 : 0);

        return myContentValues;
        }

    public void updateCrime(Crime c) {
        String crimeId = c.getId().toString();
        ContentValues newValues = getContentValues(c);

        String searchString = CrimeDbSchema.CrimeTable.Columns.UUID + " = ?";
        String[] searchArgs = new String[] { crimeId };

        mDatabase.update(CrimeDbSchema.CrimeTable.NAME, newValues, searchString, searchArgs);
    }

    private Cursor queryCrimes(String whereClause, String[] whereArgs) {

        Cursor cursor = mDatabase.query(
                CrimeDbSchema.CrimeTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null);

        return cursor;
    }

        return null;
    }

