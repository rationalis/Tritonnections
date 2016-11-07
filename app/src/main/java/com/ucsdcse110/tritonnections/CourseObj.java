package com.ucsdcse110.tritonnections;

class CourseObj
{
    public enum MeetingType {
        LE, DI, LA, RE, FI, SE, TU
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
    private String capeGPA;

    public CourseObj(String department, String courseCode, String courseName, String sixDigitSectionID, MeetingType type, String sectionID, DayOfWeek[] dayOfWeek, String startTime, String endTime, String location, String instructor, int seatsAvailable, int seatsLimit) {
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
    }

    public String dayToString() {
        String res = "";
        for (DayOfWeek day : dayOfWeek) {
            switch (day) {
                case M:
                    res += "M";
                    break;
                case Tu:
                    res += "Tu";
                    break;
                case W:
                    res += "W";
                    break;
                case Th:
                    res += "Th";
                    break;
                case F:
                    res += "F";
                    break;
                case S:
                    res += "S";
                    break;
                case Su:
                    res += "Su";
                    break;
                default:
                    break;
            }
        }
        return res;
    }

    public String typeToString() {
        String res = "";

        switch (type) {
            case LE:
                res += "LE";
                break;
            case DI:
                res += "DI";
                break;
            case LA:
                res += "LA";
                break;
            case RE:
                res += "RE";
                break;
            case FI:
                res += "FI";
                break;
            case SE:
                res += "SE";
                break;
            case TU:
                res += "TU";
                break;
            default:
                break;
        }
        return res;
    }
    
    public void setCapeGPA(String s) {
        capeGPA = s;
    }

    public String getCapeGPA() {
        return capeGPA;
    }

}