package com.testapps.jamrssreader.outer.listeners;

import com.testapps.jamrssreader.inner.models.RSSItem;

import java.util.ArrayList;

/**
 * Created by affy on 08.08.16.
 */
public interface RSSItemListener {

    void addRSSItem(RSSItem rssItem);

    ArrayList<RSSItem> getAllRSSItems();

    int getRSSItemCount();

}
