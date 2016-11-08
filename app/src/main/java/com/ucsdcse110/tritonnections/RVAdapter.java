package com.ucsdcse110.tritonnections;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.CourseViewHolder> {

    public static class CourseViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView courseName;
        TextView courseCode;
        TextView courseType;
        TextView courseLocation;
        TextView courseInstructor;
        TextView courseWeek;
        TextView courseTime;
        TextView courseSeat;
        TextView courseGPA;
        CourseViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            courseName = (TextView)itemView.findViewById(R.id.cname);
            courseCode = (TextView)itemView.findViewById(R.id.ccode);
            courseType = (TextView)itemView.findViewById(R.id.ctype);
            courseLocation = (TextView)itemView.findViewById(R.id.clocation);
            courseInstructor = (TextView)itemView.findViewById(R.id.cinstructor);
            courseWeek = (TextView)itemView.findViewById(R.id.cweek);
            courseTime = (TextView)itemView.findViewById(R.id.ctime);
            courseSeat = (TextView)itemView.findViewById(R.id.cseat);
            courseGPA = (TextView)itemView.findViewById(R.id.cgpa);
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
    public void onBindViewHolder(CourseViewHolder cvh, int i) {
        CourseObj obj = courseList.get(i);
        cvh.courseName.setText(obj.courseName);
        cvh.courseCode.setText(obj.department + " " + obj.courseCode);
        cvh.courseLocation.setText(obj.location);
        cvh.courseInstructor.setText(obj.instructor);
        cvh.courseType.setText(obj.type.name());
        cvh.courseWeek.setText(obj.dayToString());
        cvh.courseTime.setText(obj.startTime + "-" + obj.endTime);
        cvh.courseSeat.setText(obj.seatsAvailable + "/" + obj.seatsLimit);
        if (obj.getCapeGPA() == null) {
            cvh.courseGPA.setVisibility(View.INVISIBLE);
        } else {
            cvh.courseGPA.setVisibility(View.VISIBLE);
            cvh.courseGPA.setText("Average GPA: " + obj.getCapeGPA());
        }
    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }
}
