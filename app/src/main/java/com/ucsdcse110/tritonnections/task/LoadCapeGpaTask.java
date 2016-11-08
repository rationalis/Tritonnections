package com.ucsdcse110.tritonnections.task;

import com.ucsdcse110.tritonnections.CourseObj;
import com.ucsdcse110.tritonnections.RVAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.HashMap;

public class LoadCapeGpaTask extends HTTPRequestTask<Void> {
    private CourseObj obj;
    private RVAdapter adapter;

    public LoadCapeGpaTask(CourseObj obj, RVAdapter adapter) {
        this.obj = obj;
        this.adapter = adapter;
    }

    @Override
    protected Void doInBackground(String... params) {
        String course = obj.department + "+" + obj.courseCode;
        String instructor = obj.instructor;

        HashMap<String, String> requestProperties = new HashMap<String, String>();
        requestProperties.put("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.87 Safari/537.36");

        String html = request(
                "http://cape.ucsd.edu/responses/Results.aspx?CourseNumber="+course,null,"GET", requestProperties);
        Document doc = Jsoup.parse(html);
        // TODO: Fix case of matching e.g. MUS 95 when searching for MUS 9
        Element tr = doc.select("tr:contains("+instructor+")").first();
        Element gpaReceivedElem = tr.select("span[id*=GradeReceived]").first();
        String gpaReceived = gpaReceivedElem.text();

        // TODO: Maybe handle N/A better?
        if (!gpaReceived.equals("N/A")) obj.setCapeGPA(gpaReceived);
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        adapter.notifyDataSetChanged();
    }
}
