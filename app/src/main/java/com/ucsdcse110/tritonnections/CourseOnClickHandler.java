package com.ucsdcse110.tritonnections;

import android.view.View;

import com.ucsdcse110.tritonnections.task.LoadCoursesTask;

public interface CourseOnClickHandler {
    void onClickLecture(View view);
    void setTask(LoadCoursesTask task);
    CourseOnClickHandler withObj(CourseObj obj);
}
