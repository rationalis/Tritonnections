package com.ucsdcse110.tritonnections;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Entities;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

class TritonlinkLoginTask extends HTTPRequestTask<String> {
    public static String tritonlink_url="http://mytritonlink.ucsd.edu";
    public static String ucsd_sso_saml2_url="https://act.ucsd.edu/Shibboleth.sso/SAML2/POST";

    protected boolean loggedin = false;
    protected String mytritonlink = "";

    private String username;
    private String pw;

    public TritonlinkLoginTask(String user_id, String user_pd) {
        username = user_id;
        pw = user_pd;
    }

    public static String getFinalRedirectedUrl(String url) {
        HttpURLConnection connection;
        String finalUrl = url;
        try {
            do {
                connection = (HttpURLConnection) new URL(finalUrl).openConnection();
                connection.setInstanceFollowRedirects(false);
                connection.setUseCaches(false);
                connection.setRequestMethod("GET");
                connection.connect();
                int responseCode = connection.getResponseCode();
                if (responseCode >= 300 && responseCode < 400) {
                    String redirectedUrl = connection.getHeaderField("Location");
                    if (null == redirectedUrl)
                        break;
                    finalUrl = redirectedUrl;
                    System.out.println("redirected url: " + finalUrl);
                } else
                    break;
            } while (connection.getResponseCode() != HttpURLConnection.HTTP_OK);
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return finalUrl;
    }

    protected String doInBackground(String... params) {
        if (loggedin) return mytritonlink;

        CookieManager cookieManager = new CookieManager();
        CookieHandler.setDefault(cookieManager);
        ((CookieManager) CookieHandler.getDefault()).setCookiePolicy(CookiePolicy.ACCEPT_ALL);

        String response;

        String student_sso_param =
                "initAuthMethod=urn:mace:ucsd.edu:sso:studentsso&"+
                        "urn:mace:ucsd.edu:sso:username="+username+"&"+
                        "urn:mace:ucsd.edu:sso:password="+pw+"&"+
                        "submit=submit&"+
                        "urn:mace:ucsd.edu:sso:authmethod=urn:mace:ucsd.edu:sso:studentsso";

        response = request(getFinalRedirectedUrl(tritonlink_url), student_sso_param, "POST");

        String SAMLResponse;
        String relayState;

        Document doc = Jsoup.parse(response);

        Document.OutputSettings settings = doc.outputSettings();
        settings.prettyPrint(false);
        settings.escapeMode(Entities.EscapeMode.extended);
        settings.charset("ASCII");

        Element relayStateElement = doc.select("[name=\"RelayState\"]").first();
        relayState = relayStateElement.attr("value");

        Element SAMLResponseElement = doc.select("[name=\"SAMLResponse\"]").first();
        SAMLResponse = SAMLResponseElement.attr("value");

        try {
            String SAML_param =
                    "RelayState=" + URLEncoder.encode(relayState, "UTF-8") + "&" +
                    "SAMLResponse=" + URLEncoder.encode(SAMLResponse, "UTF-8");
            System.out.println(SAML_param);
            response = request(ucsd_sso_saml2_url, SAML_param, "POST");
            response = request(relayState, null, "GET");

            loggedin = true;
            mytritonlink = response;
            return mytritonlink;
        } catch (Exception e) {
            return null;
        }
    }
}




