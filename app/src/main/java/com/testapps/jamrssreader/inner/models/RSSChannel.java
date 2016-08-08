package com.testapps.jamrssreader.inner.models;

/**
 * Created by affy on 08.08.16.
 */
public class RSSChannel {

    private int mId;
    private String mTitle;
    private String mLink;
    private String mDescription;
    private String mLanguage;
    private String mPubDate;
    private String mLastBuildDate;
    private String mRSSImageChannelLink;
    private String mRSSImageChannelUrl;
    private String mRSSImageChannelTitle;

    public RSSChannel(int mId, String mTitle, String mLink, String mDescription, String mLanguage, String mPubDate, String mLastBuildDate, String mRSSImageChannelLink, String mRSSImageChannelUrl, String mRSSImageChannelTitle) {
        this.mId = mId;
        this.mTitle = mTitle;
        this.mLink = mLink;
        this.mDescription = mDescription;
        this.mLanguage = mLanguage;
        this.mPubDate = mPubDate;
        this.mLastBuildDate = mLastBuildDate;
        this.mRSSImageChannelLink = mRSSImageChannelLink;
        this.mRSSImageChannelUrl = mRSSImageChannelUrl;
        this.mRSSImageChannelTitle = mRSSImageChannelTitle;
    }

    public int getmId() {
        return mId;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmLink() {
        return mLink;
    }

    public String getmDescription() {
        return mDescription;
    }

    public String getmLanguage() {
        return mLanguage;
    }

    public String getmPubDate() {
        return mPubDate;
    }

    public String getmLastBuildDate() {
        return mLastBuildDate;
    }

    public String getmRSSImageChannelLink() {
        return mRSSImageChannelLink;
    }

    public String getmRSSImageChannelUrl() {
        return mRSSImageChannelUrl;
    }

    public String getmRSSImageChannelTitle() {
        return mRSSImageChannelTitle;
    }
}
