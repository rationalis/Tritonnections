package com.ucsdcse110.tritonnections.task;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.URLEncoder;

public class TritonlinkLoginTask extends HTTPRequestTask<String> {
    public static final String tritonlinkUrl = "http://mytritonlink.ucsd.edu";
    public static final String ucsdSsoSaml2Param = "https://act.ucsd.edu/Shibboleth.sso/SAML2/POST";
    public static final String studentSSOParam =
            "initAuthMethod=urn:mace:ucsd.edu:sso:studentsso&"+
                    "urn:mace:ucsd.edu:sso:username=%s&"+
                    "urn:mace:ucsd.edu:sso:password=%s&"+
                    "submit=submit&"+
                    "urn:mace:ucsd.edu:sso:authmethod=urn:mace:ucsd.edu:sso:studentsso";

    private boolean loggedIn = false;
    private String pid;
    private String pw;

    public TritonlinkLoginTask(String user_id, String user_pd) {
        pid = user_id;
        pw = user_pd;
    }

    protected String doInBackground(String... params) {
        if (loggedIn) return lastResponse;

        exception = null; // reset

        CookieManager cookieManager = new CookieManager();
        CookieHandler.setDefault(cookieManager);
        ((CookieManager) CookieHandler.getDefault()).setCookiePolicy(CookiePolicy.ACCEPT_ALL);

        request(tritonlinkUrl, null, "GET");
        String response = request(lastUrl, String.format(studentSSOParam, pid, pw), "POST");

        if (response.matches("(?s).*Login failed: Please check\\s*user name and password.*")) {
            exception = new LoginFailedException("Please check PID and password.");
            return null;
        }

        Document doc = Jsoup.parse(response);
        Element relayStateElement = doc.select("[name=\"RelayState\"]").first();
        Element SAMLResponseElement = doc.select("[name=\"SAMLResponse\"]").first();
        String relayState = relayStateElement.attr("value");
        String SAMLResponse = SAMLResponseElement.attr("value");

        try {
            String SAML_param =
                    "RelayState=" + URLEncoder.encode(relayState, "UTF-8") + "&" +
                    "SAMLResponse=" + URLEncoder.encode(SAMLResponse, "UTF-8");
            // System.out.println(SAML_param);
            response = request(ucsdSsoSaml2Param, SAML_param, "POST");
            // response = request(relayState, null, "GET");

            loggedIn = true;
            pw = null;
            return response;
        } catch (Exception e) {
            exception = e;
            return null;
        }
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    static class LoginFailedException extends RuntimeException {
        public LoginFailedException(String message) {
            super(message);
        }
    }
}
