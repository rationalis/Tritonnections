package com.ucsdcse110.tritonnections;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Button;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    private WebView webView;
    private long exitTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = new WebView(this);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);

        Button btn = (Button)findViewById(R.id.open_activity_button);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });
    }

    public void searchScheduleOfClasses(View view) {
        EditText editText = (EditText) findViewById(R.id.edit_message);

        String query = editText.getText().toString();
        //setContentView(webView);
        try {
            //new LoadScheduleWebviewTask(webView).execute(query);
            Intent intent = new Intent(MainActivity.this, RecyclerViewActivity.class);
            intent.putExtra("query", query);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序",
                        Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                super.onBackPressed();
            }

        }
    }
}