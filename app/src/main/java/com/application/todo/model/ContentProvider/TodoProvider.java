package com.application.todo.model.ContentProvider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import com.application.todo.model.Connection.Database;

/**
 * Created by Mustafizur Rahman on 16/04/2018.
 */

public class TodoProvider extends ContentProvider {

    private Database database;

    @Override
    public boolean onCreate() {
        this.database = new Database(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(TodoContract.TABLE);
        return this.doQuery(builder, projection, selection, selectionArgs, sortOrder);
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Long insertedId = this.doInsert(TodoContract.TABLE, values);
        return Uri.withAppendedPath(uri, String.valueOf(insertedId));
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = this.database.getWritableDatabase();
        return db.delete(TodoContract.TABLE, selection, selectionArgs);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return this.doUpdate(TodoContract.TABLE, values, selection, selectionArgs);
    }

    private Cursor doQuery(SQLiteQueryBuilder builder,
                           String[] projection,
                           String selection,
                           String[] selectionArgs,
                           String sortOrder) {
        SQLiteDatabase db = this.database.getReadableDatabase();
        return builder.query(
                db,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder);
    }

    private Long doInsert(String table, ContentValues values) {
        SQLiteDatabase db = this.database.getWritableDatabase();
        return db.insert(table, null, values);
    }

    private int doUpdate(String table,
                         ContentValues values,
                         String selection,
                         String[] selectionArgs) {
        SQLiteDatabase db = this.database.getWritableDatabase();
        return db.update(table, values, selection, selectionArgs);
    }
}
