package com.example.rmaahmadov.mytask.activitys;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.service.chooser.ChooserTarget;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.example.rmaahmadov.mytask.R;


public class AboutUsActivity extends AppCompatActivity {

    Toolbar toolbar;
    Intent intent = null, chooser = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus);
        toolbar = findViewById(R.id.toolbarHomeActivity);
        setSupportActionBar(toolbar);
        getSupportActionBar()
                .setDisplayHomeAsUpEnabled(true);
    }





    public void findLocation(View view) {
        intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("geo:40.409134, 49.864768"));
        chooser = Intent.createChooser(intent, "Launching Maps");
        startActivity(chooser);
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }
}
