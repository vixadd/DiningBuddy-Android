package net.gravitydevelopment.cnu;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.cengalabs.flatui.FlatUI;

import net.gravitydevelopment.cnu.fragment.LocationFeedbackFragment;
import net.gravitydevelopment.cnu.fragment.LocationGraphFragment;
import net.gravitydevelopment.cnu.fragment.LocationMainFragment;
import net.gravitydevelopment.cnu.fragment.LocationViewFragment;
import net.gravitydevelopment.cnu.geo.CNULocation;
import net.gravitydevelopment.cnu.geo.CNULocationInfo;

import java.io.Serializable;


public class CNULocationView extends FragmentActivity {

    public static final String ARG_TITLE = "title";
    public static final String ARG_NAME = "name";
    public static final String ARG_INFO = "info";
    public static final String ARG_DRAWABLE = "drawable";
    public static final String ARG_INITIAL_COLOR = "initialColor";
    public static final String ARG_SHOULD_OPEN_INFO = "shouldOpenInfo";

    private LocationViewFragment viewFragment;
    private LocationMainFragment mainFragment;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FlatUI.initDefaultValues(this);
        FlatUI.setDefaultTheme(FlatUI.GRASS);
        getActionBar().setBackgroundDrawable(FlatUI.getActionBarDrawable(this, FlatUI.GRASS, false));
        setContentView(R.layout.activity_cnulocation_view);
        if (savedInstanceState == null) {
            Bundle b = getIntent().getExtras();
            String title = b.getString(ARG_TITLE);
            String name = b.getString(ARG_NAME);
            int drawable = b.getInt(ARG_DRAWABLE);
            Serializable obj = b.getSerializable(ARG_INFO);

            if (obj != null) {
                viewFragment = LocationViewFragment.newInstance(title, name, drawable, CNULocationInfo.CrowdedRating.NOT_CROWDED.getColor(), false, (CNULocationInfo)obj);
            } else {
                viewFragment = LocationViewFragment.newInstance(title, name, drawable, CNULocationInfo.CrowdedRating.NOT_CROWDED.getColor(), false);
            }

            mainFragment = LocationMainFragment.newInstance(name);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.location_container, viewFragment)
                    .add(R.id.tab_container, mainFragment)
                    .commit();

            this.name = name;
        }
        CNU.setCurrentLocationView(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        CNU.setCurrentLocationView(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cnuview_location, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void updateLocation(CNULocation location) {
        Log.d(CNU.LOG_TAG, "Updated location");
        mainFragment.updateLocation(location);
    }

    public void updateInfo(CNULocationInfo regattas, CNULocationInfo commons) {
        Log.d(CNU.LOG_TAG, "Updated info");
        CNULocationInfo info = null;
        if (name.equals("Regattas")) {
            info = regattas;
        } else if (name.equals("Commons")) {
            info = commons;
        }
        final CNULocationInfo finalInfo = info;
        viewFragment.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                viewFragment.updateInfo(finalInfo);
            }
        });
    }

}