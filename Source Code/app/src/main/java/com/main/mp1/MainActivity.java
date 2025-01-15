package com.main.mp1;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import com.main.mp1.openmiba.MIBAActivity;
import com.main.mp1.openmiba.MIBASettingsActivity;
import com.main.mp1.openpassgo.PassGoActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startMIBA(View view) {
        startActivity(new Intent(this, MIBAActivity.class));
    }

    public void startPassGo(View view) {
        startActivity(new Intent(this, PassGoActivity.class));
    }
}