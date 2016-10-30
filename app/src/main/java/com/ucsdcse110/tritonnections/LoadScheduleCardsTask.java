package com.ucsdcse110.tritonnections;

import java.util.*;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class LoadScheduleCardsTask extends PostRequestTask<List<CourseObj>> {
    public static final String url = "https://act.ucsd.edu/scheduleOfClasses/scheduleOfClassesStudentResult.htm";

    protected List<CourseObj> doInBackground(String... params) {
        String urlParameters =
                "selectedTerm=FA16&xsoc_term=&loggedIn=false&tabNum=tabs-crs&_selectedSubjects=1&schedOption1=true&_schedOption1=on&_schedOption11=on&_schedOption12=on&schedOption2=true&_schedOption2=on&_schedOption4=on&_schedOption5=on&_schedOption3=on&_schedOption7=on&_schedOption8=on&_schedOption13=on&_schedOption10=on&_schedOption9=on&schDay=M&_schDay=on&schDay=T&_schDay=on&schDay=W&_schDay=on&schDay=R&_schDay=on&schDay=F&_schDay=on&schDay=S&_schDay=on&schStartTime=12%3A00&schStartAmPm=0&schEndTime=12%3A00&schEndAmPm=0&_selectedDepartments=1&schedOption1Dept=true&_schedOption1Dept=on&_schedOption11Dept=on&_schedOption12Dept=on&schedOption2Dept=true&_schedOption2Dept=on&_schedOption4Dept=on&_schedOption5Dept=on&_schedOption3Dept=on&_schedOption7Dept=on&_schedOption8Dept=on&_schedOption13Dept=on&_schedOption10Dept=on&_schedOption9Dept=on&schDayDept=M&_schDayDept=on&schDayDept=T&_schDayDept=on&schDayDept=W&_schDayDept=on&schDayDept=R&_schDayDept=on&schDayDept=F&_schDayDept=on&schDayDept=S&_schDayDept=on&schStartTimeDept=12%3A00&schStartAmPmDept=0&schEndTimeDept=12%3A00&schEndAmPmDept=0&courses="+
                        params[0]+"&sections=&instructorType=begin&instructor=&titleType=contain&title=&_hideFullSec=on&_showPopup=on";
        String html = postRequest(url, urlParameters);



        // TODO: parse html into a list of CourseObj's

        //As shown in the tutorial for regex
        Pattern pattern = Pattern.compile("(\\d\\d\\d\\d\\d\\d)\\s*<td class=\"brdr\"><span id=\"insTyp\"\\stitle=\"[a-zA-Z]*\">(\\w\\w)</span></td>\\s*?<td class=\"brdr\">([A-Z]\\d\\d)</td>\\s*?<td class=\"brdr\">([MTuWhF]*)\\s*?</td>\\s*?<td class=\"brdr\">([0-9]*:[0-9]*[ap])-([0-9]*:[0-9]*[ap])</td>\\s*?<td class=\"brdr\">(\\w*)</td>\\s*?<td class=\"brdr\">(\\w*?)\\s*?</td>\\s*?<td class=\"brdr\">\\s*?(<a href='mailto:.*?@.*?'>(\\w*,\\w.*?)\\s*?</a>\\s*?<br>)?\\s*?</td>\\s*?(<td class=\"brdr\">(\\d+)</td>)?\\s*?(<td class=\"brdr\">(\\d+)</td>)?");
        /*
            Notes on this pattern. Group 1 is the section ID. Group 2 is the meeting type. Group 3 is the Section. Group 4 is the days of meeting. Group 5 is the start time. Group 6 is the end time. Groups 7 and 8 together are the location. Group 9 is the instructor's email. Group 10 is the instructor's name. Group 11 is the available seats, group 12 is the seat limit.
         */
        Matcher matcher = pattern.matcher(html);

        ArrayList<CourseComponentObj> objList = new ArrayList<CourseComponentObj>();

        while (matcher.find()) {
            String sectionID = matcher.group(1);
            String type = matcher.group(2); //Convert to MeetingType
            String section = matcher.group(3);
            String days = matcher.group(4); //Convert to DaysOfWeek
            String startTime = matcher.group(5); //Convert to int
            String endTime = matcher.group(6); //Convert to int
            String location = matcher.group(7) + " " + matcher.group(8);
            String instructor = matcher.group(10);
            if (instructor == null)
                instructor = "";
            int seatsAvailable = Integer.parseInt(matcher.group(11));
            int seatsLimit = Integer.parseInt(matcher.group(12));



        }


        return null;
    }

    protected void onPostExecute(List<CourseObj> data) {
        // TODO: convert CourseObj's into cards and display the cards
    }
}
