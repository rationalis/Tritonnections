package com.ucsdcse110.tritonnections;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

abstract class HTTPRequestTask<O> extends AsyncTask<String, Void, O> {
    private Exception exception;

    protected String request(String url, String urlParameters, String method) {
        try {
            if (method == null) method = "GET";

            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            con.setRequestMethod(method);
            if (urlParameters != null) {
                con.setDoOutput(true);
                DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                wr.writeBytes(urlParameters);
                wr.flush();
                wr.close();
            }

            int responseCode = con.getResponseCode();
            System.out.println("\nSending '"+method+"' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) response.append(inputLine);
            in.close();

            return response.toString();
        } catch (Exception e) {
            this.exception = e;
            e.printStackTrace();
            return "Failed";
        }
    }
}