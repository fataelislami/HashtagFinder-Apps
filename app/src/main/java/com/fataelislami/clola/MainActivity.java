package com.fataelislami.clola;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.widget.TextView;

import com.fataelislami.clola.Views.AboutFragment;
import com.fataelislami.clola.Views.HashtagFinderFragment;
import com.fataelislami.clola.Views.SavedCaptionFragment;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    loadFragment(new SavedCaptionFragment());
                    return true;
                case R.id.navigation_dashboard:
                    loadFragment(new HashtagFinderFragment());
                    return true;
                case R.id.navigation_notifications:
                    loadFragment(new AboutFragment());
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.message);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navView.setSelectedItemId(R.id.navigation_dashboard);
        ButterKnife.bind(this);
        loadFragment(new HashtagFinderFragment());
    }

    void loadFragment(Fragment fragment){
        FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayout,fragment);
        ft.commit();
    }

}
