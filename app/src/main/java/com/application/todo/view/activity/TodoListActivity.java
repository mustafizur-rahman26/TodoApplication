package com.application.todo.view.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.application.todo.contract.TodoListContract;
import com.application.todo.model.TodoRepository;
import com.application.todo.view.adapter.TodoListAdapter;
import com.application.todo.presenter.TodoListPresenter;
import com.application.todo.R;
import com.application.todo.model.Entity.Todo;
import com.application.todo.view.listener.OnRecycleViewClickListener;

/**
 * Created by Mustafizur Rahman on 16/04/2018.
 */

public class TodoListActivity extends AppCompatActivity implements TodoListContract.ListView, OnRecycleViewClickListener {

    @BindView(R.id.todo_list) RecyclerView rvTodoList;
    @BindView(R.id.no_todo_text) TextView noToDoText;
    @BindView(R.id.toolbar) Toolbar toolbar;

    private static final String TAG = TodoListActivity.class.getSimpleName();
    public static final String STATE_LIST = "TodoList";
    private TodoListContract.ListPresenter presenter;
    private TodoRepository todoRepository;
    private TodoListAdapter mListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        // RecyclerView
        rvTodoList.setHasFixedSize(true);
        rvTodoList.setLayoutManager(new LinearLayoutManager(this));
        mListAdapter = new TodoListAdapter(this, this);
        rvTodoList.setAdapter(mListAdapter);

        //Create Repository
        if(todoRepository == null){
            todoRepository = new TodoRepository(this.getContentResolver());
        }

        // Creates presenter
        if (presenter == null) {
            presenter = new TodoListPresenter(this, this, todoRepository);
        }

        presenter.initialize();
    }

    @OnClick(R.id.fab_button)
    public void onClick(View v) {
        presenter.onAddTodoButtonClick();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.initialize();
    }

    @Override
    public void onClickToEdit(Todo todo, int position) {
        presenter.onClickTodoItemToEdit(todo);
    }

    @Override
    public void onLongClick(Todo todo, int position) {
        presenter.onLongClickTodoItem(todo);
    }

    @Override
    public void setTodos(ArrayList<Todo> todos) {
        if(todos.size() == 0){
            noToDoText.setVisibility(View.VISIBLE);
        }else {
            noToDoText.setVisibility(View.GONE);
            mListAdapter.setTodos(todos);
        }
    }

    @Override
    public void notifyListDataSetChanged() {
        mListAdapter.notifyDataSetChanged();
    }

    @Override
    public void notifyListItemRemoved(int position) {
        mListAdapter.notifyItemRemoved(position);
    }

    @Override
    public void notifyListItemInserted(int position) {
        mListAdapter.notifyItemInserted(position);
    }

    @Override
    public void showItemDialog(final Todo todo, final CharSequence items[]) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (items[which] == getString(R.string.edit)) {
                    presenter.onClickTodoItemToEdit(todo);
                }
                if (items[which] == getString(R.string.delete)) {
                    presenter.delete(todo);
                }
            }
        });
        builder.show();
    }

    @Override
    public void showTodoViewToEdit(Todo todo) {
        Intent i = new Intent(this, TodoDetailActivity.class);
        i.putExtra(TodoDetailActivity.EXTRA_EDIT_TODO, todo);
        startActivity(i);
    }

    @Override
    public void showTodoView() {
        Intent intent = new Intent(this, TodoDetailActivity.class);
        startActivity(intent);
    }
}
