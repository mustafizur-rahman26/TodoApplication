package com.application.todo.view.listener;

import com.application.todo.model.Entity.Todo;

/**
 * Created by Mustafizur Rahman on 16/04/2018.
 */

public interface OnRecycleViewClickListener {
    void onClickToEdit(Todo todo, int position);
    void onLongClick(Todo todo, int position);
}
