package com.example.thelastworks;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

public class CollectionCP extends ContentProvider {


    public static final int COLLECTION_DIR=0;
    public static final int COLLECTION_ITEM=1;

    public static final String AUTHORITY="com.example.collection.provider";

    private static UriMatcher uriMatcher;

    private CollectionDb collectionDb;
    static{
        uriMatcher=new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY,"collection",COLLECTION_DIR);
        uriMatcher.addURI(AUTHORITY,"collection/*",COLLECTION_ITEM);
    }

    public CollectionCP() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        SQLiteDatabase db=collectionDb.getWritableDatabase();
        int deletedRaws=0;
        switch(uriMatcher.match(uri)){
            case COLLECTION_ITEM:
                String collectionURL=uri.getPathSegments().get(1);
                Log.d("ccccccc", "delete: "+collectionURL);
                deletedRaws=db.delete("collection","imgurl = ?",new String[]{collectionURL});
                break;
            default:
                break;
        }
        return deletedRaws;
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        switch (uriMatcher.match(uri)){
            case COLLECTION_DIR:
                return "vnd.android.cursor.dir/vnd.com.example.collection.provider.collection";
            case COLLECTION_ITEM:
                return "vnd.android.cursor.dir/vnd.com.example.collection.provider.collection";
        }
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        SQLiteDatabase db=collectionDb.getWritableDatabase();
        Uri uriReturn=null;
        switch(uriMatcher.match(uri)){
            case COLLECTION_DIR:
            case COLLECTION_ITEM:
                long newCollectionID=db.insert("collection",null,values);
                uriReturn=Uri.parse("content://"+AUTHORITY+"/collection/"+newCollectionID);
                break;
            default:
                break;
        }
        return uriReturn;
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        collectionDb=new CollectionDb(getContext(),"collect.db",null,1);
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
        SQLiteDatabase db=collectionDb.getReadableDatabase();
        Cursor cursor=null;
        switch(uriMatcher.match(uri)){
            case COLLECTION_DIR:
                cursor=db.query("collection",projection,selection,selectionArgs,null,null,sortOrder);
                break;
            case COLLECTION_ITEM:
                String collectionURL=uri.getPathSegments().get(1);
                cursor=db.query("collection",projection,"imgurl = ?",new String[]{collectionURL},null,null,sortOrder);
            default:
                break;
        }
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
