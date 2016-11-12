package com.ucsdcse110.tritonnections.task;

import android.support.v7.widget.RecyclerView;

import com.ucsdcse110.tritonnections.CourseObj;

import java.util.List;

public abstract class LoadCoursesTask extends HTTPRequestTask<Void> {
    protected List<CourseObj> courseList;
    protected RecyclerView.Adapter adapter;

    public LoadCoursesTask(List<CourseObj> courseList, RecyclerView.Adapter adapter) {
        this.courseList = courseList;
        this.adapter = adapter;
    }

    @Override
    public void onPostExecute(Void result) {
        System.out.println("finished with courselistsize:"+courseList.size());
        adapter.notifyDataSetChanged();

        try {
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
    }
}
