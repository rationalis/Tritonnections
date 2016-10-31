package com.ucsdcse110.tritonnections;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.CourseViewHolder> {

    public static class CourseViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView courseName;
        TextView courseLocation;
        TextView courseInstructor;
        TextView courseTime;

        CourseViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            courseName = (TextView)itemView.findViewById(R.id.cname);
            courseLocation = (TextView)itemView.findViewById(R.id.clocation);
            courseInstructor = (TextView)itemView.findViewById(R.id.cinstructor);
            courseTime = (TextView)itemView.findViewById(R.id.ctime);
        }
    }

    List<CourseObj> courseList;

    RVAdapter(List<CourseObj> courseList){
        this.courseList = courseList;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public CourseViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        CourseViewHolder pvh = new CourseViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(CourseViewHolder courseViewHolder, int i) {
        courseViewHolder.courseName.setText(courseList.get(i).sectionID);
        courseViewHolder.courseLocation.setText(courseList.get(i).location);
        courseViewHolder.courseInstructor.setText(courseList.get(i).instructor);
        courseViewHolder.courseTime.setText(courseList.get(i).startTime + "-" + courseList.get(i).endTime);
    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }
}
