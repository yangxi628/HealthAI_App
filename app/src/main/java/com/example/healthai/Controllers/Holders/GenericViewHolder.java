package com.example.healthai.Controllers.Holders;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public abstract class GenericViewHolder<T> extends RecyclerView.ViewHolder {

    public GenericViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public abstract void bindData(T data);
}
