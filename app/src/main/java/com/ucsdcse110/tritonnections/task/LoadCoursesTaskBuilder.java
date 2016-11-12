package com.ucsdcse110.tritonnections.task;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;

import com.ucsdcse110.tritonnections.CourseObj;

import java.util.ArrayList;
import java.util.List;

public class LoadCoursesTaskBuilder implements Parcelable {
    private List<CourseObj> courseList;
    private RecyclerView.Adapter adapter;
    private SourceType type;

    public LoadCoursesTaskBuilder() {}

    public enum SourceType {
        SCHEDULE_OF_CLASSES, ENROLLED_CLASSES
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

    protected LoadCoursesTaskBuilder(Parcel in) {
        if (in.readByte() == 0x01) {
            courseList = new ArrayList<CourseObj>();
            in.readList(courseList, CourseObj.class.getClassLoader());
        } else {
            courseList = null;
        }
        adapter = (RecyclerView.Adapter) in.readValue(RecyclerView.Adapter.class.getClassLoader());
        type = (SourceType) in.readValue(SourceType.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (courseList == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(courseList);
        }
        dest.writeValue(adapter);
        dest.writeValue(type);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<LoadCoursesTaskBuilder> CREATOR = new Parcelable.Creator<LoadCoursesTaskBuilder>() {
        @Override
        public LoadCoursesTaskBuilder createFromParcel(Parcel in) {
            return new LoadCoursesTaskBuilder(in);
        }

        @Override
        public LoadCoursesTaskBuilder[] newArray(int size) {
            return new LoadCoursesTaskBuilder[size];
        }
    };
}