package com.application.todo.presenter;

import android.content.Context;
import android.widget.Toast;

import java.util.Date;

import com.application.todo.interactor.TodoDetailInteractor;
import com.application.todo.common.ParseDate;
import com.application.todo.contract.TodoDetailContract;
import com.application.todo.model.Entity.Todo;
import com.application.todo.model.TodoRepository;

/**
 * Created by Mustafizur Rahman on 16/04/2018.
 */

public class TodoDetailPresenter implements TodoDetailContract.TodoPresenter {

    private TodoDetailContract.TodoView view;
    private TodoDetailInteractor interactor;
    private Context context;

    private Todo editTodo;

    public TodoDetailPresenter(TodoDetailContract.TodoView view, Context context) {
        this.view       = view;
        this.interactor = new TodoRepository(context.getContentResolver());
        this.context    = context;
    }

    @Override
    public void setEditTodo(Todo todo) {
        this.editTodo = todo;
        view.updateFields(todo.getTitle(), todo.getDescription(), todo.isCompleted());
        view.updateEditedField(ParseDate.parseDate(todo.getEdited(), ParseDate.HOUR_PATTERN));
    }

    @Override
    public void create(String title, String description, boolean completed) {
        if (!title.equals("") || !description.equals("")) {
            Todo todo = (this.editTodo != null) ? this.editTodo : new Todo();
            todo.setTitle(title);
            todo.setDescription(description);
            todo.setCompleted(completed);
            todo.setEdited(new Date().getTime());
            interactor.create(todo);
            Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show();
        }
        view.finishView();
    }

    @Override
    public void delete() {
        if (editTodo != null) {
            interactor.delete(editTodo);
            Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
        }
        view.finishView();
    }

    @Override
    public void discard() {
        Toast.makeText(context, "Discarded", Toast.LENGTH_SHORT).show();
        view.finishView();
    }

    @Override
    public void updateEditedTime() {
        view.updateEditedField(ParseDate.parseDate(null, ParseDate.HOUR_PATTERN));
    }

}
