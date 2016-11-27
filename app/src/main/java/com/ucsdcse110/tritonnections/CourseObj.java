package com.ucsdcse110.tritonnections;

public class CourseObj
{
    public enum MeetingType {
        LE, DI, LA, RE, FI, SE, TU, MI
    }

    public enum DayOfWeek {
        M, Tu, W, Th, F, S, Su
    }

    public final String department;
    public final String courseCode;
    public final String courseName;
    public final String sixDigitSectionID;
    public final MeetingType type;
    public final String sectionID;
    public final DayOfWeek[] dayOfWeek;
    public final String startTime;
    public final String endTime;
    public final String location;
    public final String instructor;
    public final int seatsAvailable;
    public final int seatsLimit;
    public final String color;
    private String capeGpa;

    public CourseObj(String department,
                     String courseCode,
                     String courseName,
                     String sixDigitSectionID,
                     MeetingType type,
                     String sectionID,
                     DayOfWeek[] dayOfWeek,
                     String startTime,
                     String endTime,
                     String location,
                     String instructor,
                     int seatsAvailable,
                     int seatsLimit) {
        this.department = department;
        this.courseCode = courseCode;
        this.courseName = courseName;
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
        this.color = toColor(type);
    }

    public String dayToString() {
        String res = "";
        for (DayOfWeek day : dayOfWeek) {
            res += day.name();
        }
        return res;
    }

    public void setCapeGpa(String s) {
        capeGpa = s;
    }

    public String getCapeGpa() {
        return capeGpa;
    }

    public String toColor(MeetingType type)
    {
        switch (type)
        {
            case DI:
                return "#ff0000";
            case LA:
                return "#00ff00";
            case RE:
                return "#0000ff";
            case FI:
                return "#0000ff";
            case SE:
                return "#0000ff";
            case TU:
                return "#0000ff";
            case MI:
                return "#0000ff";
            default:
                return "#ffffff";
        }
    }

}