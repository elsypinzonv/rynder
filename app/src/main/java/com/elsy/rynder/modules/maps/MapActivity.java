package com.elsy.rynder.modules.maps;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.elsy.rynder.R;
import com.elsy.rynder.modules.budget.BudgetActivity;
import com.elsy.rynder.modules.login.LoginActivity;
import com.elsy.rynder.utils.ActivityHelper;
import com.elsy.rynder.utils.Injection;

public class MapActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        initUI();
        initToolbar();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.budget:
                ActivityHelper.begin(this, BudgetActivity.class);
                return true;
            case R.id.logout:
                doLogout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void goBudget(){
       // ActivityHelper.sendTo(this, BudgetActivity.class);
    }

    private void doLogout() {
       // Injection.provideLocationPreferencesManager(this).clearLocation();
       // Injection.provideBudgetPreferencesManager(this).clearBudget();
        Injection.provideUserSessionManager(this).logoutUser();
        ActivityHelper.sendTo(this, LoginActivity.class);
    }

    private void initToolbar(){
       setSupportActionBar(toolbar);
    }

    private void initUI(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
    }
}
