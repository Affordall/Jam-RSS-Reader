package com.testapps.jamrssreader.middle.converters;

import org.jetbrains.annotations.Contract;

import java.util.ArrayList;

/**
 * Created by affy on 08.08.16.
 */
public abstract class InstanceModelGenerator {

    @Contract(" -> !null")
    public static synchronized <RSSItem>ArrayList<RSSItem> newInstanceRSSItemList() {
        return new ArrayList<>();
    }


    @Contract(" -> !null")
    public static synchronized <RSSChannel>ArrayList<RSSChannel> newInstanceRSSChannelList() {
        return new ArrayList<>();
    }

}
