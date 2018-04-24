package com.application.todo.contract;

import com.application.todo.model.Entity.Todo;

/**
 * Created by Mustafizur Rahman on 16/04/2018.
 */

public interface TodoDetailContract {

    public interface TodoView {

        void updateFields(String title, String description, boolean completed);

        void updateEditedField(String date);

        void finishView();
    }

    public interface TodoPresenter {

        void setEditTodo(Todo todo);

        void create(String title, String description, boolean completed);

        void delete();

        void discard();

        void updateEditedTime();
    }
}
