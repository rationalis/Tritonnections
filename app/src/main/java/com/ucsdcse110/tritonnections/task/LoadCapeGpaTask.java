package com.ucsdcse110.tritonnections.task;

import android.support.v7.widget.RecyclerView;

import com.ucsdcse110.tritonnections.CourseObj;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class LoadCapeGpaTask extends HTTPRequestTask<CourseObj, Void> {
    private RecyclerView.Adapter adapter;

    private static ConcurrentHashMap<String, String> gpaMap = new ConcurrentHashMap<String, String>();

    public LoadCapeGpaTask(RecyclerView.Adapter adapter) {
        this.adapter = adapter;
    }

    @Override
    protected Void doInBackground(CourseObj... courses) {
        for (CourseObj course : courses) {
            String code = course.department + "+" + course.courseCode;
            String instructor = course.instructor;
            String key = code + "+" + instructor;
            if (gpaMap.containsKey(key)) {
                course.setCapeGpa(gpaMap.get(key));
                continue;
            }
            try {
                System.out.println("Loading CAPE GPA for: "+key);

                HashMap<String, String> requestProperties = new HashMap<String, String>();
                requestProperties.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.87 Safari/537.36");

                String html = request(
                        "http://cape.ucsd.edu/responses/Results.aspx?" +
                                "Name=" + URLEncoder.encode(instructor, "UTF-8") +
                                "&CourseNumber=" + code, null, "GET", requestProperties);
                Document doc = Jsoup.parse(html);
                Element tr =
                        doc.select(
                                "tr:contains(" + instructor + ")" +
                                        ":contains(" +
                                        course.department + " " +
                                        course.courseCode + " )")
                                .first();
                Element gpaReceivedElem = tr.select("span[id*=GradeReceived]").first();
                String gpaReceived = gpaReceivedElem.text();

                if (!gpaReceived.equals("N/A")) course.setCapeGpa(gpaReceived);

                gpaMap.put(key, course.getCapeGpa());
                publishProgress();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        publishProgress();
        return null;
    }

    @Override
    public void onProgressUpdate(Void... values) {
        adapter.notifyDataSetChanged();
    }
}
