package com.application.todo.view.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.application.todo.R;
import com.application.todo.model.Entity.Todo;
import com.application.todo.view.listener.OnRecycleViewClickListener;

/**
 * Created by Mustafizur Rahman on 16/04/2018.
 */

public class TodoListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Todo> todos;
    private Context context;
    private OnRecycleViewClickListener onRecycleViewClickListener;

    public TodoListAdapter(Context context, OnRecycleViewClickListener onRecycleViewClickListener) {
        this.context = context;
        //this.todos = todos;
        this.onRecycleViewClickListener = onRecycleViewClickListener;
    }

    public void setTodos(ArrayList<Todo> todos) {
        this.todos = todos;
    }

    public ArrayList<Todo> getTodos() {
        return todos;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View todoView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_todo, parent, false);
        return new TodoViewHolder(todoView);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        TodoViewHolder todoViewHolder = (TodoViewHolder) holder;

        if(todos.size() > 0){
            final Todo todo = todos.get(position);

            // Data
            todoViewHolder.cvItemRow.setVisibility(View.VISIBLE);
            todoViewHolder.tvTitle.setText(todo.getTitle());
            todoViewHolder.tvNote.setText(todo.getDescription());
            todoViewHolder.cbStatus.setChecked(todo.isCompleted());

            // Listeners
            todoViewHolder.cvItemRow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onRecycleViewClickListener.onClickToEdit(todo, holder.getAdapterPosition());
                }
            });
            todoViewHolder.cvItemRow.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onRecycleViewClickListener.onLongClick(todo, holder.getAdapterPosition());
                    return true;
                }
            });
        }else{
            todoViewHolder.cvItemRow.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return todos == null ? 0 : todos.size();
    }

    /**
     * ToDoList ViewHolder
     */
    public static class TodoViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_todo_background) RelativeLayout itemBackground;
        @BindView(R.id.item_todo_row) CardView cvItemRow;
        @BindView(R.id.title) TextView tvTitle;
        @BindView(R.id.note) TextView tvNote;
        @BindView(R.id.todo_status) CheckBox cbStatus;


        public TodoViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

}
