package com.ucsdcse110.tritonnections;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;

public class SearchActivity extends AppCompatActivity {

    private WebView webView;
    private long exitTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = new WebView(this);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
    }

    public void searchScheduleOfClasses(View view) {
        EditText editText = (EditText) findViewById(R.id.edit_message);

        String query = editText.getText().toString();
        //setContentView(webView);
        try {
            //new LoadScheduleWebviewTask(webView).execute(query);
            Intent intent = new Intent(SearchActivity.this, RecyclerViewActivity.class);
            intent.putExtra("query", query);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}