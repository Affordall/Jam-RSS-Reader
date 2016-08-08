package com.testapps.jamrssreader.inner.builders;

import com.testapps.jamrssreader.inner.models.RSSItem;

/**
 * Created by affy on 08.08.16.
 */
public class RSSItemBuilder {

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

    public RSSItemBuilder() {
    }

    public RSSItemBuilder withItemId(int id) {
        this.mItemId = id;
        return this;
    }

    public RSSItemBuilder withItemTitle(String title) {
        this.mItemTitle = title;
        return this;
    }

    public RSSItemBuilder withItemDescription(String itemDescription) {
        this.mItemDescription = itemDescription;
        return this;
    }

    public RSSItemBuilder withItemLink(String itemLink) {
        this.mItemLink = itemLink;
        return this;
    }

    public RSSItemBuilder withItemGUID(String itemGUID) {
        this.mItemGuid = itemGUID;
        return this;
    }

    public RSSItemBuilder withItemImageUrl(String itemImageUrl) {
        this.mItemImageUrl = itemImageUrl;
        return this;
    }

    public RSSItemBuilder withItemPubDate(String itemPubDate) {
        this.mItemPubDate = itemPubDate;
        return this;
    }

    public RSSItemBuilder withChannelId(int channelId) {
        this.mChannelId = channelId;
        return this;
    }

    public RSSItemBuilder withChannelTitle(String channelTitle) {
        this.mChannelTitle = channelTitle;
        return this;
    }

    public RSSItemBuilder withFavorite(int favorite) {
        this.mFavorite = favorite;
        return this;
    }

    public RSSItem build() {
        this.validate();
        return new RSSItem(mItemId, mItemTitle, mItemDescription, mItemLink, mItemGuid, mItemImageUrl, mItemPubDate, mChannelId, mChannelTitle, mFavorite);
    }

    private void validate() {
//        if (name == null) {
//            throw new IllegalStateException("name is null");
//        }
    }

}
