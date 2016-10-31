package com.ucsdcse110.tritonnections;

import android.os.AsyncTask;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

abstract class HTTPRequestTask<O> extends AsyncTask<String, Void, O> {
    protected Exception exception;
    protected String lastResponse;
    protected String lastUrl;

    private String readStream(InputStream is) throws IOException {
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        int i = is.read();
        while(i != -1) {
            bo.write(i);
            i = is.read();
        }
        return bo.toString();
    }

    protected String request(String url, String urlParameters, String method) {
        try {
            if (method == null) method = "GET";
            String nextUrl = url;
            HttpURLConnection con;
            int respCode = 300;

            for (int ii = 0; ii < 10 &&
                    (respCode >= 300 && respCode < 400 || ii == 0) && nextUrl != null; ii++) {
                con = (HttpURLConnection) new URL(nextUrl).openConnection();
                con.setInstanceFollowRedirects(false);
                con.setUseCaches(false);
                con.setRequestMethod(method);

                if (urlParameters != null) {
                    con.setDoOutput(true);
                    DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                    wr.writeBytes(urlParameters);
                    wr.flush();
                    wr.close();
                }

                con.connect();

                respCode = con.getResponseCode();
                System.out.println("\nSending '"+method+"' request to URL : " + nextUrl);
                System.out.println("Response Code : " + respCode);
                lastResponse = readStream(con.getInputStream());

                nextUrl = con.getHeaderField("Location");
                if (nextUrl != null) lastUrl = nextUrl;
                method = "GET";
                urlParameters = null;
            }

            return lastResponse;
        } catch (Exception e) {
            this.exception = e;
            e.printStackTrace();
            return "Failed";
        }
    }
}