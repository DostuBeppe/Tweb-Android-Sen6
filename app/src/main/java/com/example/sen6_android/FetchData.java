package com.example.sen6_android;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.BreakIterator;

public class FetchData extends AsyncTask<String, Void, String> {
    private TextView tmp;

    public FetchData(TextView tmp) {
        this.tmp = tmp;
    }

    @Override
    protected String doInBackground(String... strings) {
        if (strings.length>0)
            return NetworkUtils.getData(strings[0]);
        else return "";
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }

    public TextView getTmp() {
        return tmp;
    }

    public void setTmp(TextView tmp) {
        this.tmp = tmp;
    }
}
