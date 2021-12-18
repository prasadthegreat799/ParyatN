package com.nappdeveloper.paryatn.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nappdeveloper.paryatn.Adapters.SliderAdapter;
import com.nappdeveloper.paryatn.R;

public class OnBoarding extends AppCompatActivity {

    LinearLayout dotsLayout;
    ViewPager viewPager;
    TextView[] dots;
    Animation animation;

    Button letsGetStartedBtn;
    int currentPos;

    SliderAdapter sliderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_on_boarding);

        dotsLayout = (LinearLayout) findViewById(R.id.dots);
        viewPager = (ViewPager) findViewById(R.id.slider);
        sliderAdapter = new SliderAdapter(this);
        letsGetStartedBtn = (Button) findViewById(R.id.get_started_btn);

        viewPager.setAdapter(sliderAdapter);
        addDots(0);
        viewPager.addOnPageChangeListener(changeListener);
    }

    private void addDots(int position) {

        dots = new TextView[4];
        dotsLayout.removeAllViews();

        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226"));
            dots[i].setTextSize(35);

            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0) {
            dots[position].setTextColor(getResources().getColor(R.color.pale_red));
        }
    }

    public void skip(View view) {
        startActivity(new Intent(getApplicationContext(), signInActivity.class));
        finish();
    }


    public void next(View view) {
        viewPager.setCurrentItem(currentPos + 1);
    }

    public void getStarted(View view) {

        /* Create an Intent that will start the Menu-Activity. */
        Intent mainIntent = new Intent(getApplicationContext(), signInActivity.class);
        startActivity(mainIntent);
        finish();
    }

    ViewPager.OnPageChangeListener changeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDots(position);
            currentPos = position;

            if (position == 0) {
                letsGetStartedBtn.setVisibility(View.INVISIBLE);
            } else if (position == 1) {
                letsGetStartedBtn.setVisibility(View.INVISIBLE);
            } else if (position == 2) {
                letsGetStartedBtn.setVisibility(View.INVISIBLE);
            } else {
                animation = AnimationUtils.loadAnimation(OnBoarding.this, R.anim.bottom_animation);
                letsGetStartedBtn.setAnimation(animation);
                letsGetStartedBtn.setVisibility(View.VISIBLE);
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}