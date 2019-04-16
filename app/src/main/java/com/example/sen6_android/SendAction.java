package com.example.sen6_android;

import android.os.AsyncTask;
import android.widget.TextView;

public class SendAction extends AsyncTask<String,Void,String> {

    private TextView tmp;

    public SendAction(TextView tmp) {
        this.tmp = tmp;
    }

    @Override
    protected String doInBackground(String... strings) {
        if (strings.length>0) {
            switch (strings[0]) {
                case "user-login":
                    return NetworkUtils.actionLogin(strings);
                case "user-logout":
                    return NetworkUtils.actionLogout(strings);
                case "lesson-insert":
                    return NetworkUtils.actionInsert(strings);
                case "lesson-cancel":
                    return NetworkUtils.actionCancel(strings);
            }
        }
        return "";
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
