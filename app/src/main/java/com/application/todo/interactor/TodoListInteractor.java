package com.application.todo.interactor;

import java.util.ArrayList;

import com.application.todo.model.Entity.Todo;

/**
 * Created by Mustafizur Rahman on 16/04/2018.
 */

public interface TodoListInteractor extends TodoActionsInteractor {

    ArrayList<Todo> get();

    void update(Todo todo);

}
