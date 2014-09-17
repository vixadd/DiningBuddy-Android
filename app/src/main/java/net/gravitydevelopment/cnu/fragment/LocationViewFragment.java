package net.gravitydevelopment.cnu.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.gravitydevelopment.cnu.CNU;
import net.gravitydevelopment.cnu.CNUViewLocation;
import net.gravitydevelopment.cnu.R;
import net.gravitydevelopment.cnu.Util;
import net.gravitydevelopment.cnu.geo.CNULocationInfo;

import java.io.Serializable;

public class LocationViewFragment extends Fragment {

    private String title;
    private int drawable;
    private CNULocationInfo info;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_location_view, container, false);
        this.title = getArguments().getString("title");
        this.drawable = getArguments().getInt("drawable");
        drawTitle(rootView, title);
        drawPicture(rootView, getArguments().getInt("initialColor"));
        drawInfo(rootView, -1);
        Serializable obj = getArguments().getSerializable("info");
        Log.d(CNU.LOG_TAG, "Des: " + obj);
        if (obj != null) {
            updateInfo(rootView, (CNULocationInfo) obj);
        }
        if(getArguments().getBoolean("shouldOpenInfo")) {
            rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), CNUViewLocation.class);
                    Bundle b = new Bundle();
                    b.putString("title", title);
                    b.putInt("drawable", drawable);
                    if (info != null) {
                        b.putSerializable("info", info);
                    }
                    intent.putExtras(b);
                    startActivity(intent);
                }
            });
        }
        return rootView;
    }

    public void updateInfo(CNULocationInfo info) {
        this.info = info;
        updateInfo(getView(), info.getPeople(), info.getCrowdedRating());
    }

    public void updateInfo(View view, CNULocationInfo info) {
        this.info = info;
        updateInfo(view, info.getPeople(), info.getCrowdedRating());
    }

    public void updateInfo(View view, int people, CNULocationInfo.CrowdedRating crowdedRating) {
        drawPicture(view, Util.getColorForCrowdedRating(crowdedRating));
        drawInfo(view, people);
    }

    private void drawTitle(View view, String title) {
        ((TextView) view.findViewById(R.id.title)).setText(title);
    }

    private void drawPicture(View view, int color) {
        ImageView image = (ImageView) view.findViewById(R.id.image);
        Bitmap bm = BitmapFactory.decodeResource(getResources(), drawable);
        image.setImageBitmap(Util.getRoundedRectBitmap(bm, color));
    }

    private void drawInfo(View view, int people) {
        String s = people < 0 ? "Loading..." : "Currently: " + people + " people.";
        ((TextView) view.findViewById(R.id.info)).setText(s);
    }

    public static LocationViewFragment newInstance(String title, int drawable, int initialColor, boolean shouldOpenInfo) {
        return newInstance(title, drawable, initialColor, shouldOpenInfo, null);
    }

    public static LocationViewFragment newInstance(String title, int drawable, int initialColor, boolean shouldOpenInfo, CNULocationInfo initialInfo) {
        LocationViewFragment frag = new LocationViewFragment();

        Bundle args = new Bundle();
        args.putString("title", title);
        args.putInt("drawable", drawable);
        args.putInt("initialColor", initialColor);
        args.putBoolean("shouldOpenInfo", shouldOpenInfo);
        if (initialInfo != null) {
            Log.d(CNU.LOG_TAG, "Created new frag: " + initialInfo);
            args.putSerializable("info", initialInfo);
        }
        frag.setArguments(args);

        return frag;
    }
}
