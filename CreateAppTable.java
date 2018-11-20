package com.sictc.cspm.hikers_app;

import android.os.AsyncTask;
import android.util.Log;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.property.ObjectProperty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateAppTable
{

    private BackendlessUser user;
    private List<ObjectProperty> appTable;

    private static CreateAppTable instance = new CreateAppTable(UserTableMain.user);

    public static CreateAppTable getInstance() {
        return instance;
    }

    public CreateAppTable(BackendlessUser use) {
        user = use;
    }

    public Map<String, Object> correctMap = new HashMap<>();
    public void createTable()
    {
        Backendless.Persistence.describe( user.getProperty("username").toString(), new AsyncCallback<List<ObjectProperty>>()
        {
            @Override
            public void handleResponse( List<ObjectProperty> objectProperties )
            {
                appTable = objectProperties;
                for (ObjectProperty property : appTable)
                {

                    Log.i("Property", property.getName() + " : " + property.);
                    correctMap.put(property.getName(), property);
                }

                Log.i("Found the table", "Successfully found the table this table");
            }

            @Override
            public void handleFault( BackendlessFault backendlessFault )
            {
                appTable = null;
                Log.i("Couldn't find the table", "Table not found :((");
            }
        });
    }

    public List<ObjectProperty> getAppTable()
    {
        return appTable;
    }

}
