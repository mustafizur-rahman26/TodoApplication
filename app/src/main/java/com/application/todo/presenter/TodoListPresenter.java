package com.application.todo.presenter;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;

import com.application.todo.interactor.TodoListInteractor;
import com.application.todo.R;
import com.application.todo.contract.TodoListContract;
import com.application.todo.model.Entity.Todo;
import com.application.todo.model.TodoRepository;

/**
 * Created by Mustafizur Rahman on 16/04/2018.
 */

public class TodoListPresenter implements TodoListContract.ListPresenter {

    private TodoListContract.ListView view;
    private TodoListInteractor interactor;
    private Context context;

    public TodoListPresenter(TodoListContract.ListView view, Context context, TodoRepository todoRepository) {
        this.view = view;
        this.interactor = todoRepository;
        this.context = context;
    }


    @Override
    public void initialize() {
        view.setTodos(interactor.get());
        view.notifyListDataSetChanged();
    }

    @Override
    public void onAddTodoButtonClick() {
        view.showTodoView();
    }

    @Override
    public void onClickTodoItemToEdit(Todo todo) {
        view.showTodoViewToEdit(todo);
    }

    @Override
    public void onLongClickTodoItem(Todo todo) {
        CharSequence items[] = new CharSequence[]{
                context.getString(R.string.edit),
                context.getString(R.string.delete)
        };
        view.showItemDialog(todo, items);
    }

    @Override
    public void updateTodoIsCompleted(Todo todo, boolean completed, int position) {
        todo.setCompleted(completed);
        interactor.update(todo);
        ArrayList<Todo> todoList = interactor.get();
        view.notifyListItemRemoved(position);
        view.setTodos(todoList);
        for (Todo todoObject : todoList) {
            if (todoObject.getId() == todo.getId()) {
                view.notifyListItemInserted(todoList.indexOf(todoObject));
            }
        }
    }

    @Override
    public void delete(Todo todo) {
        interactor.delete(todo);
        view.setTodos(interactor.get());
        view.notifyListDataSetChanged();
        Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
    }
}
