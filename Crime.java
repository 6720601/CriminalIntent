package com.ctech.ingalls.criminalintent;

import java.util.Date;
import java.util.UUID;

public class Crime {

    private UUID mId;
    private String mTitle;
    private Date mDate;
    private boolean mSolved;

    public Crime() {
        mId = UUID.randomUUID();
        mDate = new Date();
    }
    public UUID getmId() {
        return mId;
    }
    public String getmTitle() {
        return mTitle;
    }
    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }
    public Date getmDate() {
        return mDate;
    }
    public boolean isSolved() {
        return mSolved;
    }
    public void setSolved(boolean solved) {
        mSolved = solved;
    }
}
