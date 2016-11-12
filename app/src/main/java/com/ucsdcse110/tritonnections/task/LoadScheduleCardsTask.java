package com.ucsdcse110.tritonnections.task;

import com.ucsdcse110.tritonnections.CourseObj;

import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.*;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class LoadScheduleCardsTask extends HTTPRequestTask<List<CourseObj>> {
    public static final String url = "https://act.ucsd.edu/scheduleOfClasses/scheduleOfClassesStudentResult.htm";

    protected List<CourseObj> doInBackground(String... params) {
        // TODO: Use Builder pattern for search options
        String urlParameters =
                "selectedTerm=FA16&xsoc_term=&" +
                        "loggedIn=false&" +
                        "tabNum=tabs-crs&" +
                        "_selectedSubjects=1&" +
                        "schedOption1=true&" +
                        "_schedOption1=on&" +
                        "_schedOption11=on&" +
                        "_schedOption12=on&" +
                        "schedOption2=true&" +
                        "_schedOption2=on&" +
                        "_schedOption4=on&" +
                        "_schedOption5=on&" +
                        "_schedOption3=on&" +
                        "_schedOption7=on&" +
                        "_schedOption8=on&" +
                        "_schedOption13=on&" +
                        "_schedOption10=on&" +
                        "_schedOption9=on&" +
                        "schDay=M&" +
                        "_schDay=on&" +
                        "schDay=T&" +
                        "_schDay=on&" +
                        "schDay=W&" +
                        "_schDay=on&" +
                        "schDay=R&" +
                        "_schDay=on&" +
                        "schDay=F&" +
                        "_schDay=on&" +
                        "schDay=S&" +
                        "_schDay=on&" +
                        "schStartTime=12%3A00&" +
                        "schStartAmPm=0&" +
                        "schEndTime=12%3A00&" +
                        "schEndAmPm=0&" +
                        "_selectedDepartments=1&" +
                        "schedOption1Dept=true&" +
                        "_schedOption1Dept=on&" +
                        "_schedOption11Dept=on&" +
                        "_schedOption12Dept=on&" +
                        "schedOption2Dept=true&" +
                        "_schedOption2Dept=on&" +
                        "_schedOption4Dept=on&" +
                        "_schedOption5Dept=on&" +
                        "_schedOption3Dept=on&" +
                        "_schedOption7Dept=on&" +
                        "_schedOption8Dept=on&" +
                        "_schedOption13Dept=on&" +
                        "_schedOption10Dept=on&" +
                        "_schedOption9Dept=on&" +
                        "schDayDept=M&" +
                        "_schDayDept=on&" +
                        "schDayDept=T&" +
                        "_schDayDept=on&" +
                        "schDayDept=W&" +
                        "_schDayDept=on&" +
                        "schDayDept=R&" +
                        "_schDayDept=on&" +
                        "schDayDept=F&" +
                        "_schDayDept=on&" +
                        "schDayDept=S&" +
                        "_schDayDept=on&" +
                        "schStartTimeDept=12%3A00&" +
                        "schStartAmPmDept=0&" +
                        "schEndTimeDept=12%3A00&" +
                        "schEndAmPmDept=0&" +
                        "courses=" + params[0]+"&" +
                        "sections=&" +
                        "instructorType=begin&" +
                        "instructor=&" +
                        "titleType=contain&" +
                        "title=&" +
                        "_hideFullSec=on&" +
                        "_showPopup=on";
        String html = request(url, urlParameters, "POST");

        ArrayList<CourseObj> courseList = new ArrayList<CourseObj>();

        Document doc = Jsoup.parse(html);
        Elements courses = doc.select("tr:has(.crsheader)");

        // TODO: Handle TBA
        // TODO: Properly handle missing fields
        // TODO: Treat courses as the primary object, with sections indicated correctly.
        for (Element course : courses) {
            Elements header = course.select(".crsheader");
            String courseCode = header.eq(1).text();
            String catalogLink = header.eq(2).select("a").attr("href");
            Matcher m = Pattern.compile("courses/([A-Z]+?)\\.html").matcher(catalogLink);
            m.find();
            String department = m.group(1);
            String courseName = header.eq(2).select("span").text();
            for (Element cur = course.nextElementSibling();
                 cur != null && cur.tagName().equals("tr") &&
                         cur.className().equals("sectxt") &&
                         !cur.html().contains("Cancelled");
                 cur = cur.nextElementSibling())
            {
                //System.out.println(cur.html()); if (true) continue;
                Elements info = cur.select(".brdr");

                String sectionID = info.eq(2).text();
                CourseObj.MeetingType type = CourseObj.MeetingType.valueOf(info.eq(3).text());
                String section = info.eq(4).text();
                String days = info.eq(5).text();
                String time = info.eq(6).text();
                String[] times = time.split("\\-");
                String startTime = times[0];
                String endTime = times[1];
                String location = info.eq(7).text() + " " + info.eq(8).text();
                String instructor = info.eq(9).text().trim();
                if (instructor == null)
                    instructor = "";
                int seatsAvailable;
                int seatsLimit;
                try {
                    seatsAvailable = Integer.parseInt(info.eq(10).text());
                    seatsLimit = Integer.parseInt(info.eq(11).text());
                } catch (NumberFormatException e) {
                    seatsAvailable = 0;
                    seatsLimit = 0;
                }

                List<CourseObj.DayOfWeek> daysList = new ArrayList<CourseObj.DayOfWeek>();
                for (CourseObj.DayOfWeek day : CourseObj.DayOfWeek.values()) {
                    if (days.contains(day.toString())) {
                        daysList.add(day);
                    }
                }

                courseList.add(new CourseObj(
                        department, courseCode, courseName,
                        sectionID,type,section,
                        daysList.toArray(new CourseObj.DayOfWeek[]{}),
                        startTime,endTime,location,instructor,seatsAvailable,seatsLimit));

            }
        }

        return courseList;
    }
}

