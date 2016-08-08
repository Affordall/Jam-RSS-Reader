package com.testapps.jamrssreader.inner.builders;

import com.testapps.jamrssreader.inner.models.RSSChannel;

/**
 * Created by affy on 08.08.16.
 */
public class RSSChannelBuilder {

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

    public RSSChannelBuilder() {
    }

    public RSSChannelBuilder withId(int id) {
        this.mId = id;
        return this;
    }

    public RSSChannelBuilder withTitle(String title) {
        this.mTitle= title;
        return this;
    }

    public RSSChannelBuilder withLink(String link) {
        this.mLink = link;
        return this;
    }

    public RSSChannelBuilder withDescription(String description) {
        this.mDescription = description;
        return this;
    }

    public RSSChannelBuilder withLanguage(String language) {
        this.mLanguage = language;
        return this;
    }

    public RSSChannelBuilder withPubDate(String pubDate) {
        this.mPubDate = pubDate;
        return this;
    }

    public RSSChannelBuilder withLastBuildDate(String lastBuildDate) {
        this.mLastBuildDate = lastBuildDate;
        return this;
    }

    public RSSChannelBuilder withImageChannelLink(String imageChannelLink) {
        this.mRSSImageChannelLink = imageChannelLink;
        return this;
    }

    public RSSChannelBuilder withImageChannelUrl(String imageChannelUrl) {
        this.mRSSImageChannelUrl = imageChannelUrl;
        return this;
    }

    public RSSChannelBuilder withImageChannelTitle(String imageChannelTitle) {
        this.mRSSImageChannelTitle = imageChannelTitle;
        return this;
    }

    public RSSChannel build() {
        this.validate();
        return new RSSChannel(mId, mTitle, mLink, mDescription, mLanguage, mPubDate, mLastBuildDate, mRSSImageChannelLink, mRSSImageChannelUrl, mRSSImageChannelTitle);
    }

    private void validate() {
//        if (name == null) {
//            throw new IllegalStateException("name is null");
//        }
    }

}
