package com.ucsdcse110.tritonnections;

import java.util.List;

/**
 * Created by Amin on 11/6/2016.
 */

public class CheckEnrollmentTask extends HTTPRequestTask<List<CourseObj>> {
    @Override
    protected List<CourseObj> doInBackground(String... params) {
        String url = "https://act.ucsd.edu/studentEnrolledClasses/enrolledclasses";
        String html = request(url, null, "GET");




        return null;
    }
}
