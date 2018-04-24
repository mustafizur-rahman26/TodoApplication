package com.application.todo.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.application.todo.R;
import com.application.todo.contract.TodoDetailContract;
import com.application.todo.presenter.TodoDetailPresenter;
import com.application.todo.model.Entity.Todo;

/**
 * Created by Mustafizur Rahman on 16/04/2018.
 */

public class TodoDetailActivity extends AppCompatActivity implements TodoDetailContract.TodoView {

    public static final String EXTRA_EDIT_TODO = "Todo";

    private TodoDetailContract.TodoPresenter presenter;

    public TodoDetailContract.TodoPresenter getPresenter() {
        if (presenter == null) {
            presenter = new TodoDetailPresenter(this, this);
        }
        return presenter;
    }

    @BindView(R.id.todo_title) EditText etTodoTitle;
    @BindView(R.id.todo_notes) EditText etTodoNote;
    @BindView(R.id.todo_status) CheckBox cbTodoStatus;
    @BindView(R.id.todo_edited) TextView tvTodoEdited;
    @BindView(R.id.toolbar) Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_detail);
        ButterKnife.bind(this);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        // Edit intent
        Intent intent = getIntent();
        Todo editTodo = intent.getParcelableExtra(EXTRA_EDIT_TODO);
        if (editTodo != null) {
            getPresenter().setEditTodo(editTodo);
        }

        // Toolbar navigation
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationOnClickListener(onBackButtonClickListener);



        // Listeners
        etTodoTitle.addTextChangedListener(textWatcherListener);
        etTodoNote.addTextChangedListener(textWatcherListener);

        // Presenter
        getPresenter().updateEditedTime();
    }

    @OnClick(R.id.todo_save)
    public void onClick(View v) {
        getPresenter().create(
                etTodoTitle.getText().toString(),
                etTodoNote.getText().toString(),
                cbTodoStatus.isChecked()
        );
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        getPresenter().create(
                etTodoTitle.getText().toString(),
                etTodoNote.getText().toString(),
                cbTodoStatus.isChecked()
        );
    }

    private TextWatcher textWatcherListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            getPresenter().updateEditedTime();
        }
    };

    private View.OnClickListener onBackButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getPresenter().create(
                    etTodoTitle.getText().toString(),
                    etTodoNote.getText().toString(),
                    cbTodoStatus.isChecked()
            );
        }
    };

    @Override
    public void updateFields(String title, String description, boolean completed) {
        //super.setToolbarTitle(R.string.title_activity_todo_edit);
        etTodoTitle.setText(title);
        etTodoNote.setText(description);
        cbTodoStatus.setChecked(completed);
    }

    @Override
    public void updateEditedField(String date) {
        String message = getString(R.string.edited, date);
        tvTodoEdited.setText(Html.fromHtml(message));
    }

    @Override
    public void finishView() {
        finish();
    }
}
