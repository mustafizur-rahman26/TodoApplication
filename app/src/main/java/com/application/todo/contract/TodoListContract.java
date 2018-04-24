package com.application.todo.contract;

import java.util.ArrayList;

import com.application.todo.model.Entity.Todo;

/**
 * Created by Mustafizur Rahman on 3/24/2018.
 */

public interface TodoListContract {

    public interface ListView {

        void setTodos(ArrayList<Todo> todos);

        void notifyListDataSetChanged();

        void notifyListItemRemoved(int position);

        void notifyListItemInserted(int position);

        void showItemDialog(Todo todo, CharSequence items[]);

        void showTodoViewToEdit(Todo todo);

        void showTodoView();
    }

    public interface ListPresenter {

        void initialize();

        void onAddTodoButtonClick();

        void onClickTodoItemToEdit(Todo todo);

        void onLongClickTodoItem(Todo todo);

        void updateTodoIsCompleted(Todo todo, boolean completed, int position);

        void delete(Todo todo);
    }
}
