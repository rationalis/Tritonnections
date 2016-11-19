package com.ucsdcse110.tritonnections.task;

import android.support.v7.widget.RecyclerView;

import com.ucsdcse110.tritonnections.CourseObj;

import java.util.ArrayList;
import java.util.List;

public abstract class LoadCoursesTask extends HTTPRequestTask<String, Void> {
    protected List<CourseObj> courseList;
    protected RecyclerView.Adapter adapter;

    public LoadCoursesTask(List<CourseObj> courseList, RecyclerView.Adapter adapter) {
        this.courseList = courseList;
        this.adapter = adapter;
    }

    @Override
    public void onPostExecute(Void result) {
        System.out.println("finished with course list size: "+courseList.size());
        adapter.notifyDataSetChanged();

        try {
            List<CourseObj> lookupList = new ArrayList<CourseObj>();
            for (CourseObj course : courseList) {
                if (course.department == null ||
                        course.courseCode == null ||
                        course.instructor == null ||
                        course.department.isEmpty() ||
                        course.courseCode.isEmpty() ||
                        course.instructor.isEmpty()) continue;

                lookupList.add(course);
                //new LoadCapeGpaTask(adapter).execute();
            }
            new LoadCapeGpaTask(adapter).execute(lookupList.toArray(new CourseObj[0]));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
