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
    public void onBindViewHolder(CourseViewHolder courseViewHolder, int i) {
        courseViewHolder.courseName.setText(courseList.get(i).courseName);
        courseViewHolder.courseCode.setText(courseList.get(i).department + " " + courseList.get(i).courseCode);
        courseViewHolder.courseLocation.setText(courseList.get(i).location);
        courseViewHolder.courseInstructor.setText(courseList.get(i).instructor);
        courseViewHolder.courseType.setText(courseList.get(i).typeToString());
        courseViewHolder.courseWeek.setText(courseList.get(i).dayToString());
        courseViewHolder.courseTime.setText(courseList.get(i).startTime + "-" + courseList.get(i).endTime);
        courseViewHolder.courseSeat.setText(courseList.get(i).seatsAvailable + "/" + courseList.get(i).seatsLimit);
        if (courseList.get(i).getCapeGPA() != null)
            courseViewHolder.courseGPA.setText("Average GPA: " + courseList.get(i).getCapeGPA());
    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }
}
