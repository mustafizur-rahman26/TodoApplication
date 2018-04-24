package com.application.todo.interactor;

import com.application.todo.model.Entity.Todo;

/**
 * Created by Mustafizur Rahman on 16/04/2018.
 */

public interface TodoDetailInteractor extends TodoActionsInteractor {

    void create(Todo todo);

}
