package com.testapps.jamrssreader.inner.models;

/**
 * Created by affy on 08.08.16.
 */
public class RSSItem {

    private int mItemId;
    private String mItemTitle;
    private String mItemDescription;
    private String mItemLink;
    private String mItemGuid;
    private String mItemImageUrl;
    private String mItemPubDate;
    private int mChannelId;
    private String mChannelTitle;
    private int mFavorite;

    public RSSItem(int mItemId, String mItemTitle, String mItemDescription, String mItemLink, String mItemGuid, String mItemImageUrl, String mItemPubDate, int mChannelId, String mChannelTitle, int mFavorite) {
        this.mItemId = mItemId;
        this.mItemTitle = mItemTitle;
        this.mItemDescription = mItemDescription;
        this.mItemLink = mItemLink;
        this.mItemGuid = mItemGuid;
        this.mItemImageUrl = mItemImageUrl;
        this.mItemPubDate = mItemPubDate;
        this.mChannelId = mChannelId;
        this.mChannelTitle = mChannelTitle;
        this.mFavorite = mFavorite;
    }

    public int getmItemId() {
        return mItemId;
    }

    public String getmItemTitle() {
        return mItemTitle;
    }

    public String getmItemDescription() {
        return mItemDescription;
    }

    public String getmItemLink() {
        return mItemLink;
    }

    public String getmItemGuid() {
        return mItemGuid;
    }

    public String getmItemImageUrl() {
        return mItemImageUrl;
    }

    public String getmItemPubDate() {
        return mItemPubDate;
    }

    public int getmChannelId() {
        return mChannelId;
    }

    public String getmChannelTitle() {
        return mChannelTitle;
    }

    public int getmFavorite() {
        return mFavorite;
    }
}
