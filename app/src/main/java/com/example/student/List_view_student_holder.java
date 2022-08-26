package com.example.student;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class List_view_student_holder extends RecyclerView.ViewHolder {

    private CheckBox itemCheckbox;

    public CheckBox getItemCheckbox() {
        return itemCheckbox;
    }

    public void setItemCheckbox(CheckBox itemCheckbox) {
        this.itemCheckbox = itemCheckbox;
    }

    public TextView getItemTextView() {
        return itemTextView;
    }

    public void setItemTextView(TextView itemTextView) {
        this.itemTextView = itemTextView;
    }

    public TextView getItemTextView1() {
        return itemTextView1;
    }

    public void setItemTextView1(TextView itemTextView1) {
        this.itemTextView1 = itemTextView1;
    }

    public TextView getItemTextView2() {
        return itemTextView2;
    }

    public void setItemTextView2(TextView itemTextView2) {
        this.itemTextView2 = itemTextView2;
    }

    private TextView itemTextView;
    private TextView itemTextView1;
    private TextView itemTextView2;

    public List_view_student_holder(View itemView) {
        super(itemView);
    }




}