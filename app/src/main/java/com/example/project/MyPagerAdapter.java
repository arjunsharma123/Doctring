package com.example.project;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class MyPagerAdapter extends FragmentStatePagerAdapter
{
    public static int pages=3;
    public MyPagerAdapter(FragmentManager fm)
    {
        super(fm);
    }

    @Override
    public Fragment getItem(int i)
    {
        Fragment f=null;
        switch (i)
        {
            case 0:
                f=new HomeFragment();
                break;
            case 1:
                f=new AppointmentFragment();
                break;
            case 2:
                f=new PrescriptionFragment();
                break;

        }
         return f;
    }

    @Override
    public int getCount()
    {
        return pages;
    }
}
