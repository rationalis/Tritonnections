package com.ucsdcse110.tritonnections;

import java.util.List;

public class CourseObj {
    public final List<CourseComponentObj> components;
    public final String sectionID;

    public CourseObj(List<CourseComponentObj> components, String sectionID) {
        this.components = components;
        this.sectionID = sectionID;
    }

}

class CourseComponentObj {
    public enum MeetingType {
        LE, DI, LA, RE, FI
    }

    public enum DayOfWeek {
        MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
    }

    public final MeetingType type;
    public final String sectionID;
    public final DayOfWeek dayOfWeek;
    public final int startTime;
    public final int endTime;
    public final String location;
    public final String instructor;
    public final int seatsAvailable;
    public final int seatsLimit;

    public CourseComponentObj(MeetingType type, String sectionID, DayOfWeek dayOfWeek, int startTime, int endTime, String location, String instructor, int seatsAvailable, int seatsLimit) {
        this.type = type;
        this.sectionID = sectionID;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = location;
        this.instructor = instructor;
        this.seatsAvailable = seatsAvailable;
        this.seatsLimit = seatsLimit;
    }
}
