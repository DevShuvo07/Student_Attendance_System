package com.example.student;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

public class List_View_student extends BaseAdapter {

    public List_View_student(List<DTO_STudent> dto_student, Context ctx) {
        Dto_student = dto_student;
        this.ctx = ctx;
    }

    private java.util.List<DTO_STudent> Dto_student = null;

    private Context ctx = null;


    @Override
    public int getCount() {
        int ret = 0;
        if(Dto_student!=null)
        {
            ret = Dto_student.size();
        }
        return ret;
    }

    @Override
    public Object getItem(int itemIndex) {
        Object ret = null;
        if(Dto_student!=null) {
            ret = Dto_student.get(itemIndex);
        }
        return ret;
    }

    @Override
    public long getItemId(int itemIndex) {
        return itemIndex;
    }

    @Override
    public View getView(int itemIndex, View convertView, ViewGroup viewGroup) {

        List_view_student_holder viewHolder = null;

        if(convertView!=null)
        {
            viewHolder = (List_view_student_holder) convertView.getTag();
        }else
        {
            convertView = View.inflate(ctx, R.layout.student_view, null);

            CheckBox listItemCheckbox = (CheckBox) convertView.findViewById(R.id.checkbox);

            TextView listItemText = (TextView) convertView.findViewById(R.id.student_name);
            TextView listItemText1 = (TextView) convertView.findViewById(R.id.student_id);
            TextView listItemText2 = (TextView) convertView.findViewById(R.id.attendance);

            viewHolder = new List_view_student_holder(convertView);

            viewHolder.setItemCheckbox(listItemCheckbox);

            viewHolder.setItemTextView(listItemText);
            viewHolder.setItemTextView1(listItemText1);
            viewHolder.setItemTextView2(listItemText2);

            convertView.setTag(viewHolder);
        }

        DTO_STudent dto_sTudent = Dto_student.get(itemIndex);
        viewHolder.getItemCheckbox().setChecked(dto_sTudent.isChecked());
        viewHolder.getItemTextView().setText(dto_sTudent.getItemText());
        viewHolder.getItemTextView1().setText(dto_sTudent.getItemText1());
        viewHolder.getItemTextView2().setText(dto_sTudent.getItemText2());

        return convertView;
    }
}

