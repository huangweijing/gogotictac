package com.weobot.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.weobot.myapplication.util.TestView;

public class SecondActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        TextView txView = new TextView(getApplicationContext());
        String viewId = getIntent().getExtras().get("view").toString();
        txView.setText(viewId);

        LinearLayout ll = (LinearLayout)findViewById(R.id.second_view);
        ll.addView(txView);

        TestView tv = (TestView) findViewById(R.id.testView1);
//        TestView tv = (TestView) findViewById(Integer.parseInt(viewId));
        System.out.println("hahaha: " + tv);
        ll.addView(tv);

        System.out.println("get param:" + getIntent().getExtras().get("view").toString());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_second, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
