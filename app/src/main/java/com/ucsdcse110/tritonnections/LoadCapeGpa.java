package com.ucsdcse110.tritonnections;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class LoadCapeGpa extends HTTPRequestTask<Void> {
    private CourseObj obj;

    public LoadCapeGpa(CourseObj obj) {
        this.obj = obj;
    }

    protected Void doInBackground(String... params) {
        String course = obj.department + "+" + obj.courseCode;
        String instructor = obj.instructor;

        String html = request(
                "http://cape.ucsd.edu/responses/Results.aspx?CourseNumber="+course,null,"GET");
        Document doc = Jsoup.parse(html);
        Element e1 = doc.select("tr:contains("+instructor+")").first();
        Element e3 = e1.select("span[id*=GradeReceived)]").first();

        obj.setCapeGPA(e3.text());
        return null;
    }
}
