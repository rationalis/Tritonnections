package com.ucsdcse110.tritonnections.task;

import android.support.v7.widget.RecyclerView;

import com.ucsdcse110.tritonnections.CourseObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class LoadCoursesTask extends HTTPRequestTask<String, Void> {
    protected List<CourseObj> courseList;
    protected Map<CourseObj, List<CourseObj>> map;
    protected RecyclerView.Adapter adapter;
    protected LoadCapeGpaTask gpaTask;

    public LoadCoursesTask(List<CourseObj> courseList, RecyclerView.Adapter adapter) {
        this.courseList = courseList;
        this.adapter = adapter;
        this.map = new HashMap<CourseObj, List<CourseObj>>();
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
            gpaTask = new LoadCapeGpaTask(adapter);
            gpaTask.execute(lookupList.toArray(new CourseObj[0]));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<CourseObj> getCourseSections(CourseObj obj) {
        return map.get(obj);
    }
}
