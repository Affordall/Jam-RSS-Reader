package com.testapps.jamrssreader.outer.listeners;

import com.testapps.jamrssreader.inner.models.RSSChannel;

import java.util.ArrayList;

/**
 * Created by affy on 08.08.16.
 */
public interface RSSChannelListener {
    void addRSSChannel(RSSChannel rssChannel);

    ArrayList<RSSChannel> getAllRSSChannels();

    int getRSSChannelCount();
}
