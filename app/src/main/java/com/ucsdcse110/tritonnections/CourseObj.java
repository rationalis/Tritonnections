package com.ucsdcse110.tritonnections;

class CourseObj {
    public enum MeetingType {
        LE, DI, LA, RE, FI, SE, TU
    }

    public enum DayOfWeek {
        MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
    }

    public final String sixDigitSectionID;
    public final MeetingType type;
    public final String sectionID;
    public final DayOfWeek dayOfWeek;
    public final int startTime;
    public final int endTime;
    public final String location;
    public final String instructor;
    public final int seatsAvailable;
    public final int seatsLimit;

    public CourseObj(String sixDigitSectionID, MeetingType type, String sectionID, DayOfWeek dayOfWeek, int startTime, int endTime, String location, String instructor, int seatsAvailable, int seatsLimit) {
        this.sixDigitSectionID = sixDigitSectionID;
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
