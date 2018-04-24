package com.application.todo.model;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;
import java.util.Date;

import com.application.todo.interactor.TodoListInteractor;
import com.application.todo.interactor.TodoDetailInteractor;
import com.application.todo.model.ContentProvider.TodoContract;
import com.application.todo.model.Entity.Todo;

/**
 * Created by Mustafizur Rahman on 16/04/2018.
 */

public class TodoRepository implements TodoListInteractor, TodoDetailInteractor {

    private ContentResolver contentResolver;

    public ContentResolver getContentResolver() {
        return contentResolver;
    }

    public TodoRepository(ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
    }

    @Override
    public ArrayList<Todo> get() {
        ArrayList<Todo> todoList = new ArrayList<>();
        Cursor cursor = getContentResolver().query(
                TodoContract.CONTENT_URI,
                TodoContract.PROJECTION_ALL,
                null,
                null,
                TodoContract.COMPLETED + " ASC, " +
                TodoContract.EDITED + " DESC");

        if (null == cursor || !cursor.moveToNext()) {
            return todoList;
        }

        do {
            Todo todo = new Todo(cursor);
            todoList.add(todo);
        } while (cursor.moveToNext());

        cursor.close();
        return todoList;
    }

    @Override
    public void update(Todo todo) {
        todo.setEdited(new Date().getTime());
        save(todo);
    }

    @Override
    public void create(Todo todo) {
        save(todo);
    }

    @Override
    public void delete(Todo todo) {
        String[] selectionArgs = {String.valueOf(todo.getId())};
        getContentResolver().delete(
                TodoContract.CONTENT_URI,
                TodoContract._ID + " = ?",
                selectionArgs);
    }

    private void save(Todo todo) {
        ContentValues values = new ContentValues();
        values.put(TodoContract.TITLE, todo.getTitle());
        values.put(TodoContract.DESCRIPTION, todo.getDescription());
        values.put(TodoContract.EDITED, todo.getEdited());
        values.put(TodoContract.COMPLETED, todo.isCompleted());

        if (todo.getId() == Todo.UNSAVED_ID) {
            Uri insertUri = getContentResolver().insert(TodoContract.CONTENT_URI, values);
            todo.setId(Long.valueOf(insertUri.getLastPathSegment()));
        } else {
            String[] selectionArgs = {String.valueOf(todo.getId())};
            getContentResolver().update(
                    TodoContract.CONTENT_URI,
                    values,
                    TodoContract._ID + " = ?",
                    selectionArgs);
        }
    }
}
