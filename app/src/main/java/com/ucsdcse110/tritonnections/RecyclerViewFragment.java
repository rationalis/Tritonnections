package com.ucsdcse110.tritonnections;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ucsdcse110.tritonnections.task.LoadCapeGpaTask;
import com.ucsdcse110.tritonnections.task.LoadScheduleCardsTask;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewFragment extends Fragment {
    private List<CourseObj> courseList = new ArrayList<CourseObj>();
    private RecyclerView rv;
    private RecyclerView.Adapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_recyclerview, container, false);
        rv = (RecyclerView) view.findViewById(R.id.rv);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);
        initializeData();
        return view;
    }

    private void initializeData(){
        System.out.println("Started initializing data for RV");
        LoadScheduleCardsTask task = new LoadScheduleCardsTask();
        task.execute(getArguments().getString("query"));
        try {
            courseList = task.get();
            initializeAdapter();
            for (CourseObj course : courseList) {
                if (course.department == null ||
                        course.courseCode == null ||
                        course.instructor == null ||
                        course.department.isEmpty() ||
                        course.courseCode.isEmpty() ||
                        course.instructor.isEmpty()) continue;

                new LoadCapeGpaTask(course, adapter).execute();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Finished initializing data for RV");
    }

    private void initializeAdapter(){
        System.out.println("Started initializing adapter for RV");
        adapter = new CourseObjRvAdapter(courseList);
        rv.setAdapter(adapter);
        System.out.println("Finished initializing adapter for RV");
    }

    public void setData(List<CourseObj> data)
    {
        courseList = data;
    }
}
