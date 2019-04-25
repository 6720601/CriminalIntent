package com.ctech.ingalls.criminalintent.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.ctech.ingalls.criminalintent.Crime;

import java.util.Date;
import java.util.UUID;

public class CrimeCursorWrapper extends CursorWrapper {
    public CrimeCursorWrapper(Cursor cursor) {

        super(cursor);
    }

    public Crime getCrime() {
        String uuidString = getString(getColumnIndex(CrimeDbSchema.CrimeTable.Columns.UUID));
        String title = getString(getColumnIndex((CrimeDbSchema.CrimeTable.Columns.TITLE)));
        long date = getLong(getColumnIndex(CrimeDbSchema.CrimeTable.Columns.DATE));
        int isSolved = getInt(getColumnIndex(CrimeDbSchema.CrimeTable.Columns.SOLVED));
        String suspect = getString(getColumnIndex(CrimeDbSchema.CrimeTable.Columns.SUSPECT));

        Crime crime = new Crime(UUID.fromString(uuidString));
        crime.setTitle(title);
        crime.setDate(new Date(date));
        crime.setSolved(isSolved == 1);
        crime.setSuspect(suspect);

        return crime;
    }
}
