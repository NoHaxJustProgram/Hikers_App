package com.sictc.cspm.hikers_app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;

import java.util.Map;

public class SettingsFragment extends Fragment
{
    private TextView txtname;
    private TextView txtheight;
    private TextView txtweight;

    private EditText edtname;
    private EditText edtheight;
    private EditText edtweight;

    private BackendlessUser user = UserTableMain.user;
    private Map<String, Object> userTableMain = UserTableMain.userTableMain;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.activity_settings, container, false);
        txtname = (TextView) rootView.findViewById(R.id.Name);
        txtheight = (TextView) rootView.findViewById(R.id.height);
        txtweight = (TextView) rootView.findViewById(R.id.weight);

        edtname = (EditText) rootView.findViewById(R.id.EditName);
        edtheight = (EditText) rootView.findViewById(R.id.EditHeight);
        edtweight = (EditText) rootView.findViewById(R.id.EditWeight);

        Log.i("imma kms", (userTableMain) + " :((((( 2");

        txtname.setText(userTableMain.get("Name").toString());
        txtheight.setText(userTableMain.get("Height").toString());
        txtweight.setText(userTableMain.get("Weight").toString());

        edtname.addTextChangedListener(textWatcher);
        edtheight.addTextChangedListener(textWatcher);
        edtweight.addTextChangedListener(textWatcher);

        return rootView;
    }

    public void updateFields()
    {
        txtname.setText(edtname.getText().toString());
        txtheight.setText(edtheight.getText().toString());
        txtweight.setText(edtweight.getText().toString());

        userTableMain.put("Name", edtname.getText().toString());
        userTableMain.put("Height", edtheight.getText().toString());
        userTableMain.put("Weight", edtweight.getText().toString());

        user.setProperties(userTableMain);
        Backendless.Persistence.of(user.getProperty("username").toString()).save(userTableMain, new AsyncCallback<Map>() {
            @Override
            public void handleResponse(Map response) {
                Log.i("Name is?", "!!! " + response.get("Name"));
            }

            @Override
            public void handleFault(BackendlessFault fault) {

            }
        });

        Log.i("Updated properies", "Successfully updated properties");
    }

    private TextWatcher textWatcher = new TextWatcher() {

        public void afterTextChanged(Editable s)
        {
            updateFields();
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after)
        {

        }

        public void onTextChanged(CharSequence s, int start, int before,
                                  int count)
        {

        }
    };
}
