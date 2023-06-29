package com.example.roomcrudsimple;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roomcrudsimple.my_interface.IClickItem;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder> {
    List<Student> listStudent;
    public IClickItem iClickItem;
    public void setData(List<Student> list){
        this.listStudent = list;
        notifyDataSetChanged();
    }

    public StudentAdapter(IClickItem iClickItem) {
        this.iClickItem = iClickItem;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Student student = listStudent.get(position);
        if(student == null){
            return;
        }

        holder.tv_id.setText(student.getId());
        holder.tv_fullname.setText(student.getFullName());
        holder.tv_birthday.setText(student.getBirthday());
        holder.tv_average.setText(String.valueOf(student.getAverage()));

        holder.myLinear.setOnClickListener(v -> {
            iClickItem.onClickItem(student);
        });
    }

    @Override
    public int getItemCount() {
        if(listStudent != null){
            return listStudent.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_id, tv_fullname, tv_birthday, tv_average;
        LinearLayout myLinear;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_id = itemView.findViewById(R.id.tv_id);
            tv_fullname = itemView.findViewById(R.id.tv_fullname);
            tv_birthday = itemView.findViewById(R.id.tv_birthday);
            tv_average = itemView.findViewById(R.id.tv_average);
            myLinear = itemView.findViewById(R.id.myLinear);
        }
    }
}
