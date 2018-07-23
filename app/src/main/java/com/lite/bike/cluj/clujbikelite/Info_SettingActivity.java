package com.lite.bike.cluj.clujbikelite;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;


public class Info_SettingActivity extends AppCompatActivity {
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.radioButton_all) RadioButton rball;
    @BindView(R.id.radioButton_favorit) RadioButton rbfavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_settings);
        ButterKnife.bind(this);

        if (toolbar != null)
        {
            setSupportActionBar(toolbar);
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setTitle("Info/Settings");
        }

        SharedPreferences sharedPref = this.getSharedPreferences("allchecked", MODE_PRIVATE);
        boolean allchecked = sharedPref.getBoolean("allchecked",true);

        if (allchecked)
            rball.setChecked(true);
        else
            rbfavorite.setChecked(true);

    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        SharedPreferences sharedPref = this.getSharedPreferences("allchecked", MODE_PRIVATE);
        boolean allchecked = sharedPref.getBoolean("allchecked",true);
        SharedPreferences.Editor editor = sharedPref.edit();
        switch(view.getId()) {
            case R.id.radioButton_all:
                if (checked)
                    allchecked = true;
                    break;
            case R.id.radioButton_favorit:
                if (checked)
                    allchecked = false;
                    break;
        }
        editor.putBoolean("allchecked", allchecked);
        editor.apply();
    }


    @Override
    public  boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return  super.onOptionsItemSelected(item);
    }
}
