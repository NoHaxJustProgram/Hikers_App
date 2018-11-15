package com.sictc.cspm.hikers_app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


public class CaloriesFragment extends Fragment
{
    TextView textView;
    ImageView imageView;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.activity_calories, container, false);
//        textView = (TextView) rootView.findViewById(R.id.Test);
//        Calendar cal = Calendar.getInstance();
//        Date date = cal.getTime();
//        textView.setText(cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE));

        return rootView;
    }

}
