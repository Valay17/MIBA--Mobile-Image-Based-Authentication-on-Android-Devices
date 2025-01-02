package com.main.mp1.openpassgo;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.core.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import com.main.mp1.R;


public class PassGoActivity extends Activity{
    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_go);
        setupActionBar();
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        Button login = (Button) findViewById(R.id.btn_login);
        if (PreferenceManager.getDefaultSharedPreferences(this).getString("passgo_pw", "").equals("")) {
            login.setEnabled(false);
        } else {
            login.setEnabled(true);
        }
    }

    @TargetApi(11)
    private void setupActionBar() {
        if (Build.VERSION.SDK_INT >= 11) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.pass_go, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 16908332:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.action_settings:
                startActivity(new Intent(this, PassGoSettingsActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void startCreatePassword(View view) {
        startActivity(new Intent(this, PassGoCreatePasswordActivity.class));
    }

    public void startEnterPassword(View view) {
        startActivity(new Intent(this, PassGoLoginActivity.class));
    }

}
