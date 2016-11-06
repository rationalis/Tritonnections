package com.ucsdcse110.tritonnections;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashMap;

/**
 * Created by Amin on 11/6/2016.
 */

public class LoadQuartersTask extends HTTPRequestTask<HashMap<String,String>> {
    @Override
    protected HashMap<String, String> doInBackground(String... params) {
        String url = "https://act.ucsd.edu/scheduleOfClasses/scheduleOfClassesStudent.htm";
        String html = request(url,null,"GET");

        Document doc = Jsoup.parse(html);
        Element quarterList = doc.select("select#selectedTerm").get(0);
        Elements quarters = quarterList.select("option");

        HashMap<String,String> terms = new HashMap<>();

        for (Element quarter : quarters) {
            terms.put(quarter.text(), quarter.val());
            System.out.println(quarter.val());
        }



        return null;
    }
}
