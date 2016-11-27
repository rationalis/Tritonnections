package com.ucsdcse110.tritonnections.task;

import android.support.v7.widget.RecyclerView;

import com.ucsdcse110.tritonnections.CourseObj;

import java.util.List;

public class LoadCoursesTaskBuilder {
    private List<CourseObj> courseList;
    private RecyclerView.Adapter adapter;
    private SourceType type;

    public LoadCoursesTaskBuilder() {}

    public enum SourceType {
        SCHEDULE_OF_CLASSES, ENROLLED_CLASSES, LIST
    }

    public LoadCoursesTaskBuilder setType(SourceType type) {
        this.type = type;
        return this;
    }

    public LoadCoursesTaskBuilder setCourseList(List<CourseObj> courseList) {
        this.courseList = courseList;
        return this;
    }

    public LoadCoursesTaskBuilder setAdapter(RecyclerView.Adapter adapter) {
        this.adapter = adapter;
        return this;
    }

    public LoadCoursesTask createLoadCoursesTask() {
        switch (type) {
            case SCHEDULE_OF_CLASSES: return new LoadScheduleCardsTask(courseList, adapter);
            case ENROLLED_CLASSES: return new LoadEnrolledCoursesTask(courseList,adapter);
            default: return null;
        }
    }
}