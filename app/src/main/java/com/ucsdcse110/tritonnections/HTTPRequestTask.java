package com.ucsdcse110.tritonnections;

import android.os.AsyncTask;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

abstract class HTTPRequestTask<O> extends AsyncTask<String, Void, O> {
    private Exception exception;

    private String readStream(InputStream is) {
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            int i = is.read();
            while(i != -1) {
                bo.write(i);
                i = is.read();
            }
            return bo.toString();
        } catch (IOException e) {
            return "";
        }
    }

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

            String response = readStream(con.getInputStream());

            while (responseCode >= 300 && responseCode < 400) {
                String redirectedUrl = con.getHeaderField("Location");
                if (redirectedUrl == null)
                    break;
                System.out.println("redirected url: " + redirectedUrl);
            }

            //System.out.println(response);
            return response;
        } catch (Exception e) {
            this.exception = e;
            e.printStackTrace();
            return "Failed";
        }
    }
}