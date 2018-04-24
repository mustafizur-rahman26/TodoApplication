package com.application.todo.model.ContentProvider;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Mustafizur Rahman on 16/04/2018.
 */

public class TodoContract implements BaseColumns {

    private TodoContract() {
    }

    public static final Uri CONTENT_URI = Uri.parse("content://com.application.todo.model.provider.TodoProvider/todo");

    public static final String TABLE = "todo";
    public static final String TITLE = "title";
    public static final String DESCRIPTION = "description";
    public static final String EDITED = "edited";
    public static final String COMPLETED = "completed";

    public static final String[] PROJECTION_ALL = {_ID, TITLE, DESCRIPTION, EDITED, COMPLETED};

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE
                    + " (" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + TITLE + " TEXT, "
                    + DESCRIPTION + " TEXT, "
                    + EDITED + " INTEGER, "
                    + COMPLETED + " INTEGER DEFAULT 0 "
                    + ");";

    public static final String FIXTURE =
            "INSERT INTO " + TABLE
                    + " (" + TITLE
                    + ", " + DESCRIPTION
                    + ", " + EDITED
                    + ", " + COMPLETED
                    + ") "
                    + "VALUES ('TodoFixture', 'Description', 1434703928, 0);";
}
