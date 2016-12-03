package nyc.c4q.dannylui.weatheralpha.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.RelativeLayout;
import android.widget.TextView;

import nyc.c4q.dannylui.weatheralpha.R;
import nyc.c4q.dannylui.weatheralpha.models.SunModel;

/**
 * Created by dannylui on 11/29/16.
 */

public class SunFragment extends Fragment {
    private View rootView;
    private SunModel sunModel;

    private RelativeLayout cloudIcon;
    private RelativeLayout uvIndex;
    private TextView sunLeft;
    private RelativeLayout sunrise;
    private RelativeLayout sunset;
    private RelativeLayout cloudCoverage;

    private TextView uvIndexTv;
    private TextView sunriseTimeTv;
    private TextView sunsetTimeTv;
    private TextView cloudCoverageTv;

    private static int origWidth;
    private static int origHeight;

    private static boolean animationEnd = false;
    private static boolean isDisabled = true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_sun, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cloudIcon = (RelativeLayout) view.findViewById(R.id.current_cloudicon);
        uvIndex = (RelativeLayout) view.findViewById(R.id.current_uv);
        sunLeft = (TextView) view.findViewById(R.id.current_sunleft);
        sunrise = (RelativeLayout) view.findViewById(R.id.current_sunrise);
        sunset = (RelativeLayout) view.findViewById(R.id.current_sunset);
        cloudCoverage = (RelativeLayout) view.findViewById(R.id.current_cloudcoverage);

        uvIndexTv = (TextView) view.findViewById(R.id.tv_uvindex);
        sunriseTimeTv = (TextView) view.findViewById(R.id.tv_sunrisetime);
        sunsetTimeTv = (TextView) view.findViewById(R.id.tv_sunsettime);
        cloudCoverageTv = (TextView) view.findViewById(R.id.tv_cloudcoverage);


        if (sunModel != null) {
            attachDataToViews(sunModel);
        }

        origWidth = sunLeft.getLayoutParams().width;
        origHeight = sunLeft.getLayoutParams().height;

        disableViews();

        sunLeft.setClickable(true);
        sunLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isDisabled) {
                    enableViews();
                    ViewGroup.LayoutParams params = sunLeft.getLayoutParams();
                    params.height = sunLeft.getHeight() - 100;
                    params.width = sunLeft.getWidth() - 100;
                    sunLeft.requestLayout();
                    isDisabled = false;
                } else {
                    if (animationEnd) {
                        animationEnd = false;
                        disableViews();
                        ViewGroup.LayoutParams params = sunLeft.getLayoutParams();
                        params.height = sunLeft.getHeight() + 100;
                        params.width = sunLeft.getWidth() + 100;

                        sunLeft.requestLayout();
                        isDisabled = true;
                    }
                }
            }
        });
    }

    public void updateData(SunModel sunModel) {
        this.sunModel = sunModel;
        if (rootView != null) {
            attachDataToViews(sunModel);
        }
    }

    public void attachDataToViews(SunModel sunModel) {
        uvIndexTv.setText(sunModel.getUvIndex());
        sunriseTimeTv.setText(sunModel.getSunriseTime());
        sunsetTimeTv.setText(sunModel.getSunsetTime());
        sunLeft.setText(sunModel.getSunTime());
        cloudCoverageTv.setText(sunModel.getCloudCoverage());
    }

    public void disableViews() {
        cloudIcon.setVisibility(View.INVISIBLE);
        uvIndex.setVisibility(View.INVISIBLE);
        sunrise.setVisibility(View.INVISIBLE);
        sunset.setVisibility(View.INVISIBLE);
        cloudCoverage.setVisibility(View.INVISIBLE);
    }

    public void enableViews() {
        cloudIcon.setVisibility(View.VISIBLE);
        uvIndex.setVisibility(View.VISIBLE);
        sunrise.setVisibility(View.VISIBLE);
        sunset.setVisibility(View.VISIBLE);
        cloudCoverage.setVisibility(View.VISIBLE);

        setAnimationStuff(cloudIcon);
        setAnimationStuff(uvIndex);
        setAnimationStuff(sunrise);
        setAnimationStuff(sunset);
        setAnimationStuff(cloudCoverage);
    }

    public void setAnimationStuff(RelativeLayout t) {
        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator()); //add this
        fadeIn.setDuration(500);

        fadeIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                animationEnd = true;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        t.setAnimation(fadeIn);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        animationEnd = false;
        isDisabled = true;
        System.out.println("Destroyed View");
    }
}
