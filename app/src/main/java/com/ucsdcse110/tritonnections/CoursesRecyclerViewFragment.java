package com.ucsdcse110.tritonnections;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ucsdcse110.tritonnections.task.LoadCoursesTask;
import com.ucsdcse110.tritonnections.task.LoadCoursesTaskBuilder;
import com.ucsdcse110.tritonnections.task.LoadCoursesTaskBuilder.SourceType;

import java.util.ArrayList;
import java.util.List;

public class CoursesRecyclerViewFragment extends Fragment {
    private List<CourseObj> courseList;
    private CourseOnClickHandler handler;
    private SourceType sourceType;
    private LoadCoursesTask loadCoursesTask;
    private RecyclerView rv;
    private RecyclerView.Adapter adapter;
    private boolean initialized;

    public static class DoNothingCOCH implements CourseOnClickHandler {
        @Override
        public void onClickLecture(View view) {}

        @Override
        public void setTask(LoadCoursesTask task) {}

        @Override
        public CourseOnClickHandler withObj(CourseObj obj) {return this;}
    }

    public static class OpenListCOCH implements CourseOnClickHandler {
        private LoadCoursesTask task;
        private CourseObj obj;

        @Override
        public void onClickLecture(View view) {
            CoursesRecyclerViewFragment rvf = new CoursesRecyclerViewFragment();
            Bundle args = new Bundle();
            args.putSerializable("course source", SourceType.LIST);
            rvf.setArguments(args);
            rvf.setData(getData());
            AppCompatActivity activity = (AppCompatActivity) view.getContext();
            activity.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.flContent, rvf)
                    .addToBackStack(null)
                    .commit();
        }

        public List<CourseObj> getData() {
            return task.getCourseSections(obj);
        }

        @Override
        public void setTask(LoadCoursesTask task) {
            this.task = task;
        }

        @Override
        public CourseOnClickHandler withObj(CourseObj obj) {
            OpenListCOCH copy = new OpenListCOCH();
            copy.task = task;
            copy.obj = obj;
            return copy;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recyclerview, container, false);
        rv = (RecyclerView) view.findViewById(R.id.rv);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);

        sourceType = (SourceType) getArguments().getSerializable("course source");
        if (sourceType == SourceType.LIST) {
            handler = new DoNothingCOCH();
            initializeAdapter();
        } else {
            courseList = new ArrayList<CourseObj>();
            handler = new OpenListCOCH();
            initializeAdapter();
            initializeData();
            handler.setTask(loadCoursesTask);
        }
        return view;
    }

    private void initializeData(){
        System.out.println("Started initializing data for RV");
        loadCoursesTask = new LoadCoursesTaskBuilder()
                .setType(sourceType)
                .setCourseList(courseList)
                .setAdapter(adapter)
                .createLoadCoursesTask();
        loadCoursesTask.execute(getArguments().getString("query"), getArguments().getString("quarter"));
        System.out.println("Finished initializing data for RV");
    }

    private void initializeAdapter(){
        System.out.println("Started initializing adapter for RV");
        adapter = new CourseObjRvAdapter(courseList, handler);
        rv.setAdapter(adapter);
        System.out.println("Finished initializing adapter for RV");
    }

    public void setData(List<CourseObj> data)
    {
        courseList = data;
    }
}
