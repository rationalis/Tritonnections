package com.ucsdcse110.tritonnections;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivity extends Activity {

    private List<CourseObj> courseList;
    private RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.recyclerview_activity);

        rv=(RecyclerView)findViewById(R.id.rv);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);

        initializeData();
        initializeAdapter();
    }

    private void initializeData(){
        courseList = new ArrayList<>();
        courseList.add(new CourseObj("123456",CourseObj.MeetingType.LE, "ABC", CourseObj.DayOfWeek.TUESDAY, 1, 10, "erc", "abc", 10, 100));
        courseList.add(new CourseObj("123456",CourseObj.MeetingType.LE, "ABC", CourseObj.DayOfWeek.TUESDAY, 1, 10, "erc", "abc", 10, 100));
        courseList.add(new CourseObj("123456",CourseObj.MeetingType.LE, "ABC", CourseObj.DayOfWeek.TUESDAY, 1, 10, "erc", "abc", 10, 100));
    }

    private void initializeAdapter(){
        RVAdapter adapter = new RVAdapter(courseList);
        rv.setAdapter(adapter);
    }

    public void setData(List<CourseObj> data)
    {
        courseList = data;
    }
}
