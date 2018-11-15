package com.sictc.cspm.hikers_app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class SurvivalGuideFragment extends Fragment
{
    private TextView Advice;
    private EditText Symptoms;
    private CheckBox checkBox;
    private CheckBox checkBox2;
    private CheckBox checkBox3;
    private CheckBox checkBox4;
    private String msg;
    private String msg2;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.survival_guide, container, false);
        Advice = (TextView) rootView.findViewById(R.id.Advice);
        Symptoms = (EditText) rootView.findViewById(R.id.Symptoms);
        checkBox = (CheckBox) rootView.findViewById(R.id.checkBox);
        checkBox2 = (CheckBox) rootView.findViewById(R.id.checkBox2);
        checkBox3 = (CheckBox) rootView.findViewById(R.id.checkBox3);
        checkBox4 = (CheckBox) rootView.findViewById(R.id.checkBox4);

        survivalGuide();
        return rootView;
    }

    public void survivalGuide() {
        String Text = " ";
        if(Symptoms.getText().toString().equals("mushroom poisoning"))
            msg = "Mushroom Poisoning: contact authorities and seek medical attention";
        if(Symptoms.getText().toString().equals("berry poisoning"))
            msg = "Berry Poinsoning: seek medical assistance immediately or call the National Capital Poison Center at (800) 222-1222.";
        if(Symptoms.getText().toString().equals("bear attack"))
            msg = "Bear attack: Stand your ground and make lots of noise. Black bears often bluff when attacking.";
        if(Symptoms.getText().toString().equals("cougar attack"))
            msg = "Cougar attack: Ensure that kids are protected, and back up slowly, keeping your eye on the wildcat. You want to make yourself look as large as possible.";
        if(Symptoms.getText().toString().equals("help"))
            msg = "Keywords: mushroom poisoning, berry poisoning, bear attack, cougar attack";
        if(checkBox.isChecked())
            msg2 = "Cuts: Stop the bleeding, clean the wound, use antibiotic oinntment";
        if(checkBox2.isChecked())
            msg2 = msg2 + "\n" + " Broken bone: If you have supplies, attempt to make a splint for the arm or leg, contant authorities, seek medical help";
        if(checkBox4.isChecked())
            msg2 =  msg2 + "\n" + " Aches and Pains: Check if any scrapes or cuts, make sure to stretch or take a break";
        if(checkBox3.isChecked())
            msg2 = msg2 + "\n" + " Upset Stomach: Check what food you have eaten, make sure you are hydrated, and take a rest. If condition does not improve, seek medical help";
        Text = msg + "\n" + msg2;
        Advice.setText(Text);
    }
}
