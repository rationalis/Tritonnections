package com.ucsdcse110.tritonnections;

import android.webkit.WebView;

class LoadScheduleWebviewTask extends HTTPRequestTask<String> {

    public static final String url = "https://act.ucsd.edu/scheduleOfClasses/scheduleOfClassesStudentResult.htm";
    private final WebView view;

    public LoadScheduleWebviewTask(WebView view) {
        this.view = view;
    }

    protected String doInBackground(String... params) {
        String urlParameters =
                "selectedTerm=FA16&xsoc_term=&loggedIn=false&tabNum=tabs-crs&_selectedSubjects=1&schedOption1=true&_schedOption1=on&_schedOption11=on&_schedOption12=on&schedOption2=true&_schedOption2=on&_schedOption4=on&_schedOption5=on&_schedOption3=on&_schedOption7=on&_schedOption8=on&_schedOption13=on&_schedOption10=on&_schedOption9=on&schDay=M&_schDay=on&schDay=T&_schDay=on&schDay=W&_schDay=on&schDay=R&_schDay=on&schDay=F&_schDay=on&schDay=S&_schDay=on&schStartTime=12%3A00&schStartAmPm=0&schEndTime=12%3A00&schEndAmPm=0&_selectedDepartments=1&schedOption1Dept=true&_schedOption1Dept=on&_schedOption11Dept=on&_schedOption12Dept=on&schedOption2Dept=true&_schedOption2Dept=on&_schedOption4Dept=on&_schedOption5Dept=on&_schedOption3Dept=on&_schedOption7Dept=on&_schedOption8Dept=on&_schedOption13Dept=on&_schedOption10Dept=on&_schedOption9Dept=on&schDayDept=M&_schDayDept=on&schDayDept=T&_schDayDept=on&schDayDept=W&_schDayDept=on&schDayDept=R&_schDayDept=on&schDayDept=F&_schDayDept=on&schDayDept=S&_schDayDept=on&schStartTimeDept=12%3A00&schStartAmPmDept=0&schEndTimeDept=12%3A00&schEndAmPmDept=0&courses="+
                params[0]+"&sections=&instructorType=begin&instructor=&titleType=contain&title=&_hideFullSec=on&_showPopup=on";
        return request(url, urlParameters, "POST");
    }

    protected void onPostExecute(String data) {
        view.loadDataWithBaseURL(url, data, null, "utf-8", null);
    }
}