package com.testapps.jamrssreader.outer.db;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.os.Build;
import android.util.Log;

import com.testapps.jamrssreader.inner.builders.RSSChannelBuilder;
import com.testapps.jamrssreader.inner.builders.RSSItemBuilder;
import com.testapps.jamrssreader.inner.models.RSSChannel;
import com.testapps.jamrssreader.inner.models.RSSItem;
import com.testapps.jamrssreader.middle.converters.InstanceModelGenerator;
import com.testapps.jamrssreader.outer.listeners.RSSChannelListener;
import com.testapps.jamrssreader.outer.listeners.RSSItemListener;
import com.testapps.jamrssreader.outer.utils.Utils;

import java.util.ArrayList;

/**
 * Created by affy on 08.08.16.
 */
public class DatabaseHandler extends SQLiteOpenHelper implements RSSItemListener, RSSChannelListener {

    private static final String DATABASE_NAME = "rss_reader";
    private static final int DATABASE_VERSION = 1;

    private static final String ORDER_BY_DESCEND = " DESC";
    private static final String ORDER_BY_ASCEND = " ASC";

    private static DatabaseHandler sInstance;

    // Tables name
    private static final String TABLE_ITEMS = "items";
    private static final String TABLE_CHANNELS = "channels";

    // Item Table Columns names
    private static final String KEY_ITEM_ID = "item_id";
    private static final String KEY_ITEM_TITLE = "item_title";
    private static final String KEY_ITEM_DESCRIPTION = "item_desc";
    private static final String KEY_ITEM_LINK = "item_link";
    private static final String KEY_ITEM_GUID = "item_guid";
    private static final String KEY_ITEM_IMAGE_URL = "item_image_url";
    private static final String KEY_ITEM_PUBDATE = "item_pub_date";

    // Channel Table Columns names
    private static final String KEY_CHANNEL_ID = "channel_id";
    private static final String KEY_CHANNEL_TITLE = "channel_title";
    private static final String KEY_CHANNEL_LINK = "channel_link";
    private static final String KEY_CHANNEL_DESCRIPTION = "channel_desc";
    private static final String KEY_CHANNEL_LANGUAGE = "channel_lang";
    private static final String KEY_CHANNEL_PUBDATE = "channel_pub_date";
    private static final String KEY_CHANNEL_LAST_BUILD_DATE = "channel_build_date";
    private static final String KEY_CHANNEL_IMAGE_LINK = "channel_image_link";
    private static final String KEY_CHANNEL_IMAGE_URL = "channel_image_url";
    private static final String KEY_CHANNEL_IMAGE_TITLE = "channel_image_title";

    private static final String KEY_FAVORITE = "favorite";

    private static final String CREATE_ITEMS_TABLE = "CREATE TABLE " + TABLE_ITEMS + "("
            + KEY_ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_ITEM_TITLE + " TEXT,"
            + KEY_ITEM_DESCRIPTION + " TEXT,"
            + KEY_ITEM_LINK + " TEXT,"
            + KEY_ITEM_GUID + " TEXT,"
            + KEY_ITEM_IMAGE_URL + " TEXT,"
            + KEY_ITEM_PUBDATE + " TEXT,"
            + KEY_CHANNEL_ID + " INTEGER,"
            + KEY_CHANNEL_TITLE + " TEXT,"
            + KEY_FAVORITE + " INTEGER DEFAULT 0" + ")";

    private static final String CREATE_CHANNELS_TABLE = "CREATE TABLE " + TABLE_CHANNELS + "("
            + KEY_CHANNEL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_CHANNEL_TITLE + " TEXT,"
            + KEY_CHANNEL_LINK + " TEXT,"
            + KEY_CHANNEL_DESCRIPTION + " TEXT,"
            + KEY_CHANNEL_LANGUAGE + " TEXT,"
            + KEY_CHANNEL_PUBDATE + " TEXT,"
            + KEY_CHANNEL_LAST_BUILD_DATE + " TEXT,"
            + KEY_CHANNEL_IMAGE_LINK + " TEXT,"
            + KEY_CHANNEL_IMAGE_URL + " TEXT,"
            + KEY_CHANNEL_IMAGE_TITLE + " TEXT" + ")";

    /**
     * Use the application context, which will ensure that you
     * don't accidentally leak an Activity's context.
     * See this article for more information: http://bit.ly/6LRzfx
     */
    public static synchronized DatabaseHandler getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DatabaseHandler(context.getApplicationContext());
        }
        return sInstance;
    }

    /**
     * Constructor should be private to prevent direct instantiation.
     * make call to static method "getInstance()" instead.
     */
    private DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_ITEMS_TABLE);
        db.execSQL(CREATE_CHANNELS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHANNELS);
        onCreate(db);
    }

    public boolean isDBlocked() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.isDbLockedByCurrentThread();
    }

    // TODO: 08.08.16 Handle favorites items/channels
    public void deleteAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.delete(TABLE_ITEMS, null, null);
            db.delete(TABLE_CHANNELS, null, null);
        } catch (SQLiteDatabaseLockedException e) {
            e.printStackTrace();
        } finally {
            closeDB(db);
        }
    }

    /**
     * All CRUD Operations
     * */

    @Override
    public void addRSSChannel(RSSChannel rssChannel) {
        String sql = "INSERT OR REPLACE INTO " + TABLE_CHANNELS +
                " ( " + KEY_CHANNEL_TITLE +
                ", " + KEY_CHANNEL_LINK +
                ", " + KEY_CHANNEL_DESCRIPTION +
                ", " + KEY_CHANNEL_LANGUAGE +
                ", " + KEY_CHANNEL_PUBDATE +
                ", " + KEY_CHANNEL_LAST_BUILD_DATE +
                ", " + KEY_CHANNEL_IMAGE_LINK +
                ", " + KEY_CHANNEL_IMAGE_URL +
                ", " + KEY_CHANNEL_IMAGE_TITLE +
                " ) " + "VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransactionNonExclusive();
        runSqlStatementDependingByApiVersion(db, sql, rssChannel);
    }


//    public RSSChannel getRSSChannel(int id) {
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.query(TABLE_CHANNELS,
//                new String[]{KEY_ID,
//                        KEY_ITEM_NAME,
//                        KEY_CATEGORY_ID,
//                        KEY_CATEGORY_NAME,
//                        KEY_SHOP_ID,
//                        KEY_SHOP_NAME,
//                        KEY_IMAGE_URL,
//                        KEY_TEXT_SHORT,
//                        KEY_TEXT_FULL,
//                        KEY_TEXT_ADD,
//                        KEY_OLD_PRICE,
//                        KEY_NEW_PRICE,
//                        KEY_DATE_START,
//                        KEY_DATE_END,
//                        KEY_IN_CART}, KEY_ID + "=?",
//                new String[]{String.valueOf(id)}, null, null, null, null);
//        if (cursor != null)
//            cursor.moveToFirst();
//
//        RSSChannel single_channel = createNewChannelByCursor(cursor);
//
//        if (cursor != null)
//            cursor.close();
//        closeDB(db);
//        return single_channel;
//    }


    private void runSqlStatementDependingByApiVersion(SQLiteDatabase db, String sql, Object incomeObj) {
        if (Utils.isKitkat()) {
            newApiSqlStatement(db, sql, incomeObj);
        } else {
            oldApiSqlStatement(db, sql, incomeObj);
        }
    }

    private void oldApiSqlStatement(SQLiteDatabase db, String sql, Object incomeObj) {
        SQLiteStatement stmt = db.compileStatement(sql);
        try {
            validateIncomingInstance(db, stmt, incomeObj);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            stmt.close();
            closeDB(db);
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void newApiSqlStatement(SQLiteDatabase db, String sql, Object incomeObj) {
        try (SQLiteStatement stmt = db.compileStatement(sql)) {
            validateIncomingInstance(db, stmt, incomeObj);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            closeDB(db);
        }
    }

    private void validateIncomingInstance(SQLiteDatabase db, SQLiteStatement stmt, Object incomeObj) throws Exception {
        if (incomeObj instanceof RSSItem) {
            commonSqlStatementItem(db, stmt, (RSSItem)incomeObj);
        } else if (incomeObj instanceof RSSChannel) {
            commonSqlStatementChannel(db, stmt, (RSSChannel)incomeObj);
        } else {
            throw new Exception("Unknown incoming instance of class");
        }
    }

    private void commonSqlStatementChannel(SQLiteDatabase db, SQLiteStatement stmt, RSSChannel rssChannel) {
        try {
            stmt.bindString(1, rssChannel.getmTitle());
            stmt.bindString(2, rssChannel.getmLink());
            stmt.bindString(3, rssChannel.getmDescription());
            stmt.bindString(4, rssChannel.getmLanguage());
            stmt.bindString(5, rssChannel.getmPubDate());
            stmt.bindString(6, rssChannel.getmLastBuildDate());
            stmt.bindString(7, rssChannel.getmRSSImageChannelLink());
            stmt.bindString(8, rssChannel.getmRSSImageChannelUrl());
            stmt.bindString(9, rssChannel.getmRSSImageChannelTitle());

            stmt.execute();
            stmt.clearBindings();

            db.setTransactionSuccessful();
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
    }


    @Override
    public ArrayList<RSSChannel> getAllRSSChannels() {
        ArrayList<RSSChannel> resultChannelList = InstanceModelGenerator.newInstanceRSSChannelList();

        String selectQuery =  "SELECT  * FROM " + TABLE_CHANNELS + " ORDER BY "+ KEY_CHANNEL_TITLE + ORDER_BY_ASCEND;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    RSSChannel item = createNewChannelByCursor(cursor);
                    resultChannelList.add(item);
                } while (cursor.moveToNext());
            }
        } catch (IllegalStateException e) {
            Utils.logError(e);
        } finally {
            cursor.close();
            closeDB(db);
        }
        return resultChannelList;
    }

    private RSSChannel createNewChannelByCursor(Cursor cursor) {
        return new RSSChannelBuilder()
                .withId(getChannelIdFromCursor(cursor))
                .withTitle(getChannelTitleFromCursor(cursor))
                .withLink(getChannelLinkFromCursor(cursor))
                .withDescription(getChannelDescriptionFromCursor(cursor))
                .withLanguage(getChannelLanguageFromCursor(cursor))
                .withPubDate(getChannelPubDateFromCursor(cursor))
                .withLastBuildDate(getChannelLastBuildDateFromCursor(cursor))
                .withImageChannelLink(getImageChannelLinkFromCursor(cursor))
                .withImageChannelUrl(getImageChannelUrlFromCursor(cursor))
                .withImageChannelTitle(getImageChannelTitleFromCursor(cursor))
                .build();
    }

    private int getChannelIdFromCursor(Cursor cursor) {
        return getIntCursor(cursor, KEY_CHANNEL_ID);
    }

    private String getChannelTitleFromCursor(Cursor cursor) {
        return getStringCursor(cursor, KEY_CHANNEL_TITLE);
    }

    private String getChannelLinkFromCursor(Cursor cursor) {
        return getStringCursor(cursor, KEY_CHANNEL_LINK);
    }

    private String getChannelDescriptionFromCursor(Cursor cursor) {
        return getStringCursor(cursor, KEY_CHANNEL_DESCRIPTION);
    }

    private String getChannelLanguageFromCursor(Cursor cursor) {
        return getStringCursor(cursor, KEY_CHANNEL_LANGUAGE);
    }

    private String getChannelPubDateFromCursor(Cursor cursor) {
        return getStringCursor(cursor, KEY_CHANNEL_PUBDATE);
    }

    private String getChannelLastBuildDateFromCursor(Cursor cursor) {
        return getStringCursor(cursor, KEY_CHANNEL_LAST_BUILD_DATE);
    }

    private String getImageChannelLinkFromCursor(Cursor cursor) {
        return getStringCursor(cursor, KEY_CHANNEL_IMAGE_LINK);
    }

    private String getImageChannelUrlFromCursor(Cursor cursor) {
        return getStringCursor(cursor, KEY_CHANNEL_IMAGE_URL);
    }

    private String getImageChannelTitleFromCursor(Cursor cursor) {
        return getStringCursor(cursor, KEY_CHANNEL_IMAGE_TITLE);
    }

    @Override
    public int getRSSChannelCount() {
        int num;
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            String QUERY = "SELECT  * FROM " + TABLE_CHANNELS;
            Cursor cursor = db.rawQuery(QUERY, null);
            num = cursor.getCount();
            cursor.close();
            closeDB(db);
            return num;
        } catch (Exception e) {
            Log.e("error", e + "");
        }
        return 0;
    }

    /**
     * ITEM
     * */

    private void commonSqlStatementItem(SQLiteDatabase db, SQLiteStatement stmt, RSSItem item) {
        try {

            stmt.bindString(1, item.getmItemTitle());
            stmt.bindString(2, item.getmItemDescription());
            stmt.bindString(3, item.getmItemLink());
            stmt.bindString(4, item.getmItemGuid());
            stmt.bindString(5, item.getmItemImageUrl());
            stmt.bindString(6, item.getmItemPubDate());
//            if (item.getmLink() != null) {
//                stmt.bindString(6, item.getmLink());
//            } else {
//                stmt.bindNull(6);
//            }
            stmt.bindLong(7, item.getmChannelId());
            stmt.bindString(8, item.getmChannelTitle());
            stmt.bindLong(9, item.getmFavorite());

            stmt.execute();
            stmt.clearBindings();

            db.setTransactionSuccessful();
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addRSSItem(RSSItem rssItem) {
        String sql = "INSERT OR REPLACE INTO " + TABLE_ITEMS +
                " ( " + KEY_ITEM_TITLE +
                ", " + KEY_ITEM_DESCRIPTION +
                ", " + KEY_ITEM_LINK +
                ", " + KEY_ITEM_GUID +
                ", " + KEY_ITEM_IMAGE_URL +
                ", " + KEY_ITEM_PUBDATE +
                ", " + KEY_CHANNEL_ID +
                ", " + KEY_CHANNEL_TITLE +
                ", " + KEY_FAVORITE +
                " ) " + "VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransactionNonExclusive();
        runSqlStatementDependingByApiVersion(db, sql, rssItem);
    }

//    public RSSItem getRSSItem(int id) {
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.query(TABLE_ITEMS,
//                new String[]{KEY_ID,
//                        KEY_ITEM_NAME,
//                        KEY_CATEGORY_ID,
//                        KEY_CATEGORY_NAME,
//                        KEY_SHOP_ID,
//                        KEY_SHOP_NAME,
//                        KEY_IMAGE_URL,
//                        KEY_TEXT_SHORT,
//                        KEY_TEXT_FULL,
//                        KEY_TEXT_ADD,
//                        KEY_OLD_PRICE,
//                        KEY_NEW_PRICE,
//                        KEY_DATE_START,
//                        KEY_DATE_END,
//                        KEY_IN_CART}, KEY_ID + "=?",
//                new String[]{String.valueOf(id)}, null, null, null, null);
//        if (cursor != null)
//            cursor.moveToFirst();
//
//        RSSItem single_item = createNewItemByCursor(cursor);
//
//        if (cursor != null)
//            cursor.close();
//        closeDB(db);
//        return single_item;
//    }

    @Override
    public ArrayList<RSSItem> getAllRSSItems() {
        ArrayList<RSSItem> resultItemsList = InstanceModelGenerator.newInstanceRSSItemList();

        String selectQuery =  "SELECT  * FROM " + TABLE_ITEMS + " ORDER BY "+ KEY_ITEM_PUBDATE + ORDER_BY_ASCEND;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    RSSItem item = createNewItemByCursor(cursor);
                    resultItemsList.add(item);
                } while (cursor.moveToNext());
            }
        } catch (IllegalStateException e) {
            Utils.logError(e);
        } finally {
            cursor.close();
            closeDB(db);
        }
        return resultItemsList;
    }

    private RSSItem createNewItemByCursor(Cursor cursor) {
        return new RSSItemBuilder()
                .withItemId(getItemIdFromCursor(cursor))
                .withItemTitle(getItemTitleFromCursor(cursor))
                .withItemDescription(getItemDescriptionFromCursor(cursor))
                .withItemLink(getItemLinkFromCursor(cursor))
                .withItemGUID(getItemGUIDFromCursor(cursor))
                .withItemImageUrl(getItemImageUrlFromCursor(cursor))
                .withItemPubDate(getItemPubDateFromCursor(cursor))
                .withChannelId(getChannelIdFromCursor(cursor))
                .withChannelTitle(getChannelTitleFromCursor(cursor))
                .withFavorite(getItemFavoriteFromCursor(cursor))
                .build();
    }

    private int getItemIdFromCursor(Cursor cursor) {
        return getIntCursor(cursor, KEY_CHANNEL_ID);
    }

    private String getItemTitleFromCursor(Cursor cursor) {
        return getStringCursor(cursor, KEY_CHANNEL_TITLE);
    }

    private String getItemDescriptionFromCursor(Cursor cursor) {
        return getStringCursor(cursor, KEY_CHANNEL_LINK);
    }

    private String getItemLinkFromCursor(Cursor cursor) {
        return getStringCursor(cursor, KEY_CHANNEL_DESCRIPTION);
    }

    private String getItemGUIDFromCursor(Cursor cursor) {
        return getStringCursor(cursor, KEY_CHANNEL_LANGUAGE);
    }

    private String getItemImageUrlFromCursor(Cursor cursor) {
        return getStringCursor(cursor, KEY_CHANNEL_PUBDATE);
    }

    private String getItemPubDateFromCursor(Cursor cursor) {
        return getStringCursor(cursor, KEY_CHANNEL_LAST_BUILD_DATE);
    }

    private int getItemFavoriteFromCursor(Cursor cursor) {
        return getIntCursor(cursor, KEY_CHANNEL_IMAGE_TITLE);
    }

    private String getStringCursor(Cursor cursor, String columnIndex) {
        return cursor.getString(cursor.getColumnIndex(columnIndex));
    }

    private int getIntCursor(Cursor cursor, String columnIndex) throws CursorIndexOutOfBoundsException {
        return cursor.getInt(cursor.getColumnIndex(columnIndex));
    }

    @Override
    public int getRSSItemCount() {
        int num;
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            String QUERY = "SELECT  * FROM " + TABLE_ITEMS;
            Cursor cursor = db.rawQuery(QUERY, null);
            num = cursor.getCount();
            cursor.close();
            closeDB(db);
            return num;
        } catch (Exception e) {
            Log.e("error", e + "");
        }
        return 0;
    }

    public int addItemToFavorite(int itemID, int added_or_not) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_FAVORITE, added_or_not);
        int addedItem = db.update(TABLE_ITEMS, values, KEY_ITEM_ID + "=" + itemID, null);
        closeDB(db);
        return addedItem;
    }

    public ArrayList<RSSItem> getFavoriteItems() {
        ArrayList<RSSItem> rssFavoriteItems = InstanceModelGenerator.newInstanceRSSItemList();

        String selectQuery = "SELECT  * FROM " + TABLE_ITEMS + " WHERE " + KEY_FAVORITE + "=?";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[] { "1" });
        try {
            if (cursor.moveToFirst()) {
                do {
                    RSSItem item = createNewItemByCursor(cursor);
                    rssFavoriteItems.add(item);
                } while (cursor.moveToNext());
            }
        } catch (IllegalStateException e) {
            Utils.logError(e);
        } finally {
            cursor.close();
            closeDB(db);
        }
        return rssFavoriteItems;
    }

    private void closeDB(SQLiteDatabase db) {
        if (db != null && db.isOpen())
            db.close();
    }
}
