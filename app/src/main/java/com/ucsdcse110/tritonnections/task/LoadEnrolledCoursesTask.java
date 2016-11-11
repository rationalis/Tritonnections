package com.ucsdcse110.tritonnections.task;

import com.ucsdcse110.tritonnections.CourseObj;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LoadEnrolledCoursesTask extends HTTPRequestTask<List<CourseObj>> {
    private static String getValue(Elements e, String name) {
        Element e0 = e.select("[name=\""+name+"\"]").first().parent();
//        System.out.println(e0.html());
        return e0.text();
    }

    @Override
    protected List<CourseObj> doInBackground(String... params) {
//        String url = "https://act.ucsd.edu/studentEnrolledClasses/print?jlinkevent=Default&termCode=FA16";
        String url = "https://act.ucsd.edu/studentEnrolledClasses/enrolledclasses";
        HashMap<String, String> requestProperties = new HashMap<String, String>();
        requestProperties.put("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.87 Safari/537.36");
        String html = request(url, null, "GET", requestProperties);
//        System.out.println(html);

        ArrayList<CourseObj> courseList = new ArrayList<CourseObj>();

        Document doc = Jsoup.parse(html);
        Elements courses = doc.select("tr:has(td[bgcolor=\"#C0C0C0\"]) + tr");

        //System.out.println("# bg:"+doc.select("td[bgcolor=#C0C0C0]").size());

        // TODO: Properly handle missing fields
        // TODO: Treat courses as the primary object, with sections indicated correctly.
        for (Element course : courses) {
            Elements header = course.select("td");
            String department = header.eq(1).text();
            String courseCode = header.eq(2).text();
            String courseName = header.eq(3).text();
            String instructor = header.eq(6).text();
            for (Element cur = course.nextElementSibling().nextElementSibling();
                 cur != null && cur.tagName().equals("tr") &&
                         (cur.className().equals("white_background") ||
                                 cur.className().equals("white_background_layer"));
                 cur = cur.nextElementSibling())
            {
                //System.out.println(cur.html()); //if (true) continue;
                Elements info = cur.select("input[type=\"hidden\"]");
                //System.out.println("inputs:"+info.size());

                String sectionID = getValue(info, "sectionId");
                sectionID = sectionID.matches("\\d+") ? sectionID : "";
                String meeting = getValue(info, "subjectType");
                //System.out.println("subjecttypes:"+info.select("[name=subjectType]").first().attr("value"));
                CourseObj.MeetingType type = CourseObj.MeetingType.valueOf(meeting.substring(0,2).toUpperCase());
                String section = getValue(info, "courseSection");
                String days = getValue(info, "unitDays");
                String time = getValue(info, "titleTime");
                String[] times = time.split(" \\- ");
                String startTime = times[0];
                String endTime = times[1];
                String location = getValue(info, "gradeBldg") + " " + getValue(info, "instructorRoom");

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
                        startTime,endTime,location,instructor,0,0));

            }
        }

        return courseList;
    }
}
