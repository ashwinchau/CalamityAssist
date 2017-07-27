package com.calamity_assist.calamityassist;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Introsilder extends Activity {

    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private LinearLayout dotsLayout;
    private TextView[] dots;
    private int[] layouts;
    private Button btnSkip,btnNext;
    private PrefManager prefManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prefManager=new PrefManager(this);
        if (!prefManager.isFirstTimeLaunch()) {
            launchHomeScreen();
            finish();
        }

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_introsilder);


        viewPager=(ViewPager)findViewById(R.id.view_pager);
        dotsLayout=(LinearLayout)findViewById(R.id.layoutDots);
        btnSkip=(Button)findViewById(R.id.btn_skip);
        btnNext=(Button)findViewById(R.id.btn_next);

        layouts=new int[]{

                R.layout.slider1,
                R.layout.slider2,
                R.layout.slider3
        };

        addBottomDots(0);

        viewPagerAdapter=new ViewPagerAdapter();
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageListener);

    }

/*Button Skip and Start*/

    public void btnSkipClick(View v)
    {
        launchHomeScreen();
    }
    public  void btnNextClick(View v)
    {
        int current = getItem(1);
        if (current<layouts.length) {
            viewPager.setCurrentItem(current);
        }
        else
        {
            launchHomeScreen();
        }
    }



    ViewPager.OnPageChangeListener viewPagerPageListener= new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position)
        {
            addBottomDots(position);

            if (position==layouts.length-1)
            {
                btnNext.setText(getString(R.string.start));
                btnSkip.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
        /*Buttom Button show proprety*/


    private void addBottomDots(int currentPage)
    {
        dots=new TextView[layouts.length];
        dotsLayout.removeAllViews();

        for (int i=0;i<dots.length;i++)
        {
            dots[i]=new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.dot_inactive));
            dotsLayout.addView(dots[i]);
        }
        if (dots.length>0)

            dots[currentPage].setTextColor(getResources().getColor(R.color.dot_active));
    }

    private int getItem(int i)
    {
        return viewPager.getCurrentItem()+i;
    }
    private void launchHomeScreen()
    {
        prefManager.setFirstTimeLaunch(false);
        startActivity(new Intent(this,Login_screen.class));
        finish();
    }




    public class ViewPagerAdapter extends PagerAdapter
    {
        private LayoutInflater layoutInflater;

        public ViewPagerAdapter()
        {

        }

        @Override
        public Object instantiateItem(ViewGroup container, int position)
        {
            layoutInflater=(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view=layoutInflater.inflate(layouts[position],container,false);
            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view=(View)object;
            container.removeView(view);
        }
    }

}
