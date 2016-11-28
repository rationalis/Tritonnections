package com.ucsdcse110.tritonnections.task;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class LoadQuartersTask extends HTTPRequestTask<String, Map<String,String>> {
    @Override
    protected Map<String, String> doInBackground(String... params) {
        String url = "https://act.ucsd.edu/scheduleOfClasses/scheduleOfClassesStudent.htm";
        String html = request(url,null,"GET");

        Document doc = Jsoup.parse(html);
        Element quarterList = doc.select("select#selectedTerm").get(0);
        Elements quarters = quarterList.select("option");

        Map<String,String> terms = new LinkedHashMap<>();

        for (Element quarter : quarters) {
            terms.put(quarter.text(), quarter.val());
//            System.out.println(quarter.val());
        }

        return terms;
    }

    @Override
    public abstract void onPostExecute(Map<String, String> map);
}
