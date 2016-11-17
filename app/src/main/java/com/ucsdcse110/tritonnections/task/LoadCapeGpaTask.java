package com.ucsdcse110.tritonnections.task;

import android.support.v7.widget.RecyclerView;

import com.ucsdcse110.tritonnections.CourseObj;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.HashMap;
import java.util.Map;

// TODO: Cache results and/or batch to prevent duplicates
// TODO: Handle edge cases
// TODO: Kill/Pause on Fragment change

public class LoadCapeGpaTask extends HTTPRequestTask<CourseObj, Void> {
    private RecyclerView.Adapter adapter;

    public LoadCapeGpaTask(RecyclerView.Adapter adapter) {
        this.adapter = adapter;
    }

    @Override
    protected Void doInBackground(CourseObj... courses) {
        Map<String, String> gpaMap = new HashMap<String, String>();
        for (CourseObj course : courses) {
            String code = course.department + "+" + course.courseCode;
            String instructor = course.instructor;
            String key = code + "+" + instructor;
            if (gpaMap.containsKey(key)) {
                course.setCapeGpa(gpaMap.get(key));
                continue;
            }
            try {

                HashMap<String, String> requestProperties = new HashMap<String, String>();
                requestProperties.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.87 Safari/537.36");

                String html = request(
                        "http://cape.ucsd.edu/responses/Results.aspx?CourseNumber=" + code, null, "GET", requestProperties);
                Document doc = Jsoup.parse(html);
                // TODO: Fix case of matching e.g. MUS 95 when searching for MUS 9
                Element tr = doc.select("tr:contains(" + instructor + ")").first();
                Element gpaReceivedElem = tr.select("span[id*=GradeReceived]").first();
                String gpaReceived = gpaReceivedElem.text();

                // TODO: Maybe handle N/A better?
                if (!gpaReceived.equals("N/A")) course.setCapeGpa(gpaReceived);

                gpaMap.put(key, course.getCapeGpa());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        adapter.notifyDataSetChanged();
    }
}
