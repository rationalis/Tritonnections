package com.ucsdcse110.tritonnections;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;

class TritonlinkLoginTask extends HTTPRequestTask<Boolean> {
    public static String login_url="https://a4.ucsd.edu/tritON/Authn/UserPassword";
    public static String ucsd_sso_saml2_url="https://act.ucsd.edu/Shibboleth.sso/SAML2/POST";

    private boolean loggedin = false;
    private String mytritonlink = "";

    private String username;
    private String pw;

    public TritonlinkLoginTask(String user_id, String user_pd) {
        username = user_id;
        pw = user_pd;
    }

    protected Boolean doInBackground(String... params) {
        if (loggedin) return true;

        String student_sso_param =
                "initAuthMethod=urn:mace:ucsd.edu:sso:studentsso&"+
                        "urn:mace:ucsd.edu:sso:username="+username+"&"+
                        "urn:mace:ucsd.edu:sso:password"+pw+"&"+
                        "submit=submit&"+
                        "urn:mace:ucsd.edu:sso:authmethod=urn:mace:ucsd.edu:sso:studentsso";

        String response = request(login_url, student_sso_param, "POST");

        String SAMLResponse;
        String relayState;

        Document doc = Jsoup.parse(response);

        Element relayStateElement = doc.select("[name=\"RelayState\"]").first();
        relayState = relayStateElement.attr("value");
        System.out.println("RelayState:"+relayState);

        Element SAMLResponseElement = doc.select("[name=\"SAMLResponse\"]").first();
        SAMLResponse = SAMLResponseElement.attr("value");

        SAMLResponse = Parser.unescapeEntities(SAMLResponse, false);
        relayState = Parser.unescapeEntities(relayState, false);

        String SAML_param = "RelayState="+relayState+"&"+"SAMLResponse="+SAMLResponse;
        response = request(ucsd_sso_saml2_url, SAML_param, "POST");
        response = request(relayState, null, "GET");

        loggedin = true;
        mytritonlink = response;
        return true;
    }
}

//public class Parser extends HTMLParser {
//    public String SAMLResponse = "";
//    public String RelayState = "";
//
//    public void handle_starttag(tag, attrs) {
//        SAMLCatched = false;
//        RelayStateCatched = false;
//        for (attr : attrs) {
//            if (attr[0] == "name" && attr[1] == "RelayState")
//                RelayStateCatched = True;
//            if (RelayStateCatched == 1 && attr[0] == "value")
//                RelayState = attr[1];
//        }
//        for (attr : attrs) {
//            if (attr[0] == "name") && attr[1] == "SAMLResponse")
//                SAMLCatched = true;
//
//            if (SAMLCatched == true && attr[0] == "value")
//                SAMLResponse = attr[1];
//        }
//    }
//
//    public void close() {
//        HTMLParser.close(self);
//    }
//}




