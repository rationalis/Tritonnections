package com.ucsdcse110.tritonnections.task;

import android.support.v7.widget.RecyclerView;

import com.ucsdcse110.tritonnections.CourseObj;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LoadEnrolledCoursesTask extends LoadCoursesTask {
    public LoadEnrolledCoursesTask(List<CourseObj> courseList, RecyclerView.Adapter adapter) {
        super(courseList, adapter);
    }

    private static String getValue(Elements e, String name) {
        Element e0 = e.select("[name=\""+name+"\"]").first().parent();
//        System.out.println(e0.html());
        return e0.text();
    }

    @Override
    protected Void doInBackground(String... params) {
//        String url = "https://act.ucsd.edu/studentEnrolledClasses/print?jlinkevent=Default&termCode=FA16";
        String url = "https://act.ucsd.edu/studentEnrolledClasses/enrolledclasses";
        HashMap<String, String> requestProperties = new HashMap<String, String>();
        requestProperties.put("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.87 Safari/537.36");
        String html = request(url, null, "GET", requestProperties);

        Document doc = Jsoup.parse(html);
        Elements courses = doc.select("tr:has(td[bgcolor=\"#C0C0C0\"]) + tr");

        System.out.println("# of headers: "+courses.size());

        // TODO: Properly handle missing fields
        // TODO: Treat courses as the primary object, with sections indicated correctly.
        for (Element course : courses) {
            Elements header = course.select("td");
            String department = header.eq(1).text();
            String courseCode = header.eq(2).text();
            String courseName = header.eq(3).text();
            String instructor = header.eq(6).text();

            CourseObj primary = null;
            List<CourseObj> sectionList = null;
            for (Element cur = course.nextElementSibling();; cur = cur.nextElementSibling())
            {
                if (courses.contains(cur) || cur == null)
                    break;
                if (!"white_background".equals(cur.className()) && !"white_background_layer".equals(cur.className()))
                    continue;

                Elements info = cur.select("input[type=\"hidden\"]");

                String sectionID = getValue(info, "sectionId");
                sectionID = sectionID.matches("\\d+") ? sectionID : "";
                String meeting = getValue(info, "subjectType");
                CourseObj.MeetingType type = CourseObj.MeetingType.valueOf(meeting.substring(0,2).toUpperCase());
                String section = getValue(info, "courseSection");
                String days = getValue(info, "unitDays");
                String time = getValue(info, "titleTime");
                String[] times = time.split(" \\- ");
                String startTime = times[0];
                String endTime = times.length > 1 ? times[1] : " ";
                String location = getValue(info, "gradeBldg") + " " + getValue(info, "instructorRoom");

                List<CourseObj.DayOfWeek> daysList = new ArrayList<CourseObj.DayOfWeek>();
                for (CourseObj.DayOfWeek day : CourseObj.DayOfWeek.values()) {
                    if (days.contains(day.toString())) {
                        daysList.add(day);
                    }
                }

                CourseObj obj = new CourseObj(
                        department, courseCode, courseName,
                        sectionID,type,section,
                        daysList.toArray(new CourseObj.DayOfWeek[]{}),
                        startTime,endTime,location,instructor,0,0);

                if (primary == null) {
                    courseList.add(obj);
                    primary = obj;
                    sectionList = new ArrayList<CourseObj>();
                    sectionList.add(primary);
                    map.put(primary, sectionList);
                } else {
                    sectionList.add(obj);
                }

            }
            System.out.println("Course finished with courselistsize: "+courseList.size());
        }
        return null;
    }
}
