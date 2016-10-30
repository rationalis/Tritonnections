package com.ucsdcse110.tritonnections;

/**
 * Created by Kunpengzhizhi on 2016/10/30.
 */
import requests;
import re;
import HTMLParser;
import BeautifulSoup;

import java.util.*;
public class login (HTMLParser)){


        public  def __init_ (this):
        public HTMLParser.__init__(this);
        public String SAMLResponse=" ";
        public String RelayState=" ";
        def handle_starttag(self,tag,attrs):
        self.SAMLCatched=false;
        self.RelayStateCatched=false;
        for(attr:attrs){
        if(attr[0]=="name"){
        if(attr[1]=="RelayState"){
        self.RelayStateCatched=True;
        }
        }
        }
        if(self.RelayStateCatched==1&&attr[0]=="value")
        self.RelayState=attr[1];
        for(attr:attrs){
        if(attr[0]=="name"){
        if(attr[1]=="SAMLResponse"){
        self.SAMLCatched=true;
        }
        }
        }
        if(self.SAMLCatched==true){
        if(attr[0]=="value"){
        self.SAMLResponse=attr[1];
        }
        }


        HTMLParser.close(self);
        }



class TritonLink {
    public String tritonlink_url="http://mytritonlink.ucsd.edu";
    public String ucsd_sso_saml_url="https://act.ucsd.edu/Shibboleth.sso/SAML/POST";
    public String ucsd_sso_saml2_url="https://act.ucsd.edu/Shibboleth.sso/SAML2/POST";

    def __init__(self, user_id, user_pd);

    private requests_session=requests.Session();
    private boolean loggedin=false;
    private String tritonlink_username=user_id;
    private String tritonlink_password=user_pd;
    private String mytritonlink=None;

    @property
    def requests_session(self)

    :
            return this.requests_session;
}

@property
public mytritonlink(this){
        return this.mytritonlink;

        """
        login(this)
        Return mytritonlink page response
        """
        def login(this):
        if(this.loggedin){
        return True;
        }
        response=this.requests_session.get(this.tritonlink_url);
        student_sso_param={
        'initAuthMethod':'urn:mace:ucsd.edu:sso:studentsso',
        'urn:mace:ucsd.edu:sso:username':self._tritonlink_username,
        'urn:mace:ucsd.edu:sso:password':self._tritonlink_password,
        'submit':'submit',
        'urn:mace:ucsd.edu:sso:authmethod':
        'urn:mace:ucsd.edu:sso:studentsso'
        }
        response=this.requests_session.post(response.url,student_sso_param);
        parser=UCSD_SSO_SAML_Parser();
        parser.feed(response.text);
        SAML_response=parser.unescape(parser.SAMLResponse);
        RelayState=parser.unescape(parser.RelayState);
        SAML_param={
        'RelayState':RelayState,
        'SAMLResponse':SAML_response,
        }
        #update to SAML2,SPRING 2015
        response=self._requests_session.post(
        self.ucsd_sso_saml2_url,SAML_param,allow_redirects=False);
        response=self._requests_session.get(RelayState);
        # ::TODO need to check the validity of login
         this.loggedin=true;
        this.mytritonlink=response.text;
        return true;
        }


}
