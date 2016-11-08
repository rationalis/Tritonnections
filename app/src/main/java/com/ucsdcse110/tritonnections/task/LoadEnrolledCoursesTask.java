package com.ucsdcse110.tritonnections.task;

import com.ucsdcse110.tritonnections.CourseObj;

import java.util.List;

public class LoadEnrolledCoursesTask extends HTTPRequestTask<List<CourseObj>> {
    @Override
    protected List<CourseObj> doInBackground(String... params) {
        String url = "https://act.ucsd.edu/studentEnrolledClasses/enrolledclasses";
        String html = request(url, null, "GET");




        return null;
    }
}
