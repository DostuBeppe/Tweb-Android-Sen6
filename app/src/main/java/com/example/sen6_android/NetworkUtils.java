package com.example.sen6_android;

import android.net.Uri;
import android.util.Log;
import android.webkit.CookieManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.List;

public class NetworkUtils {
    public static final String LOG_TAG=NetworkUtils.class.getSimpleName();
    public static final String BASE_URL="http://192.168.137.1:8080/Sen6___Web_war_exploded/controller?";
    public static final String QUERY_TYPE="type";
    public static final String QUERY_DATA="data";
    public static final String QUERY_ACTION="action";

    public static String getData(String queryString){
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String dataJSONString;
        try{
            Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                    .appendQueryParameter(QUERY_TYPE,"data")
                    .appendQueryParameter(QUERY_DATA,queryString)
                    .build();
            URL requestURL = new URL(builtUri.toString());
            urlConnection = (HttpURLConnection)requestURL.openConnection();
            urlConnection.setRequestMethod("GET");
            CookieManager cm = CookieManager.getInstance();
            String cookie = cm.getCookie(urlConnection.getURL().toString());
            if (cookie!=null){
                urlConnection.setRequestProperty("Cookie",cookie);
            }
            try{
                urlConnection.setConnectTimeout(3000);
                urlConnection.connect();
            } catch (UnknownHostException e){
                e.printStackTrace();
                return null;
            }
            List<String> cookie_list = urlConnection.getHeaderFields().get("Set-Cookie");
            if (cookie_list!=null){
                for (String tmp : cookie_list){
                    cm.setCookie(urlConnection.getURL().toString(),tmp);
                }
            }
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream==null){
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line=reader.readLine())!=null){
                buffer.append(line + "\n");
            }
            if (buffer.length()==0){
                return null;
            }
            dataJSONString = buffer.toString();
        } catch (Exception e){
            e.printStackTrace();
            return null;
        } finally {
            if(urlConnection!=null){
                urlConnection.disconnect();
            }
            if(reader!=null){
                try{
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        Log.d(LOG_TAG,dataJSONString);
        return dataJSONString;
    }

    public static String actionLogin(String... strings){
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String dataJSONString;
        try{
            Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                    .appendQueryParameter(QUERY_TYPE,"action")
                    .appendQueryParameter(QUERY_ACTION,strings[0])
                    .appendQueryParameter("email",strings[1])
                    .appendQueryParameter("password",strings[2])
                    .build();
            URL requestURL = new URL(builtUri.toString());
            urlConnection = (HttpURLConnection)requestURL.openConnection();
            urlConnection.setRequestMethod("GET");
            CookieManager cm = CookieManager.getInstance();
            String cookie = cm.getCookie(urlConnection.getURL().toString());
            if (cookie!=null){
                urlConnection.setRequestProperty("Cookie",cookie);
            }
            try{
                urlConnection.setConnectTimeout(3000);
                urlConnection.connect();
            } catch (UnknownHostException e){
                e.printStackTrace();
                return null;
            }
            List<String> cookie_list = urlConnection.getHeaderFields().get("Set-Cookie");
            if (cookie_list!=null){
                for (String tmp : cookie_list){
                    cm.setCookie(urlConnection.getURL().toString(),tmp);
                }
            }
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream==null){
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line=reader.readLine())!=null){
                buffer.append(line + "\n");
            }
            if (buffer.length()==0){
                return null;
            }
            dataJSONString = buffer.toString();
        } catch (Exception e){
            e.printStackTrace();
            return null;
        } finally {
            if(urlConnection!=null){
                urlConnection.disconnect();
            }
            if(reader!=null){
                try{
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        Log.d(LOG_TAG,dataJSONString);
        return dataJSONString;
    }

    public static String actionLogout(String... strings){
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String dataJSONString;
        try{
            Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                    .appendQueryParameter(QUERY_TYPE,"action")
                    .appendQueryParameter(QUERY_ACTION,strings[0])
                    .build();
            URL requestURL = new URL(builtUri.toString());
            urlConnection = (HttpURLConnection)requestURL.openConnection();
            urlConnection.setRequestMethod("GET");
            try{
                urlConnection.setConnectTimeout(3000);
                urlConnection.connect();
            } catch (UnknownHostException e){
                e.printStackTrace();
                return null;
            }
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream==null){
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line=reader.readLine())!=null){
                buffer.append(line + "\n");
            }
            if (buffer.length()==0){
                return null;
            }
            dataJSONString = buffer.toString();
        } catch (Exception e){
            e.printStackTrace();
            return null;
        } finally {
            if(urlConnection!=null){
                urlConnection.disconnect();
            }
            if(reader!=null){
                try{
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        Log.d(LOG_TAG,dataJSONString);
        return dataJSONString;
    }

    public static String actionInsert(String... strings){
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String dataJSONString;
        try{
            Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                    .appendQueryParameter(QUERY_TYPE,"action")
                    .appendQueryParameter(QUERY_ACTION,strings[0])
                    .appendQueryParameter("idCourse",strings[1])
                    .appendQueryParameter("schedule",strings[2])
                    .build();
            URL requestURL = new URL(builtUri.toString());
            urlConnection = (HttpURLConnection)requestURL.openConnection();
            urlConnection.setRequestMethod("GET");
            CookieManager cm = CookieManager.getInstance();
            String cookie = cm.getCookie(urlConnection.getURL().toString());
            if (cookie!=null){
                urlConnection.setRequestProperty("Cookie",cookie);
            }
            try{
                urlConnection.setConnectTimeout(3000);
                urlConnection.connect();
            } catch (UnknownHostException e){
                e.printStackTrace();
                return null;
            }
            List<String> cookie_list = urlConnection.getHeaderFields().get("Set-Cookie");
            if (cookie_list!=null){
                for (String tmp : cookie_list){
                    cm.setCookie(urlConnection.getURL().toString(),tmp);
                }
            }
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream==null){
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line=reader.readLine())!=null){
                buffer.append(line + "\n");
            }
            if (buffer.length()==0){
                return null;
            }
            dataJSONString = buffer.toString();
        } catch (Exception e){
            e.printStackTrace();
            return null;
        } finally {
            if(urlConnection!=null){
                urlConnection.disconnect();
            }
            if(reader!=null){
                try{
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        Log.d(LOG_TAG,dataJSONString);
        return dataJSONString;
    }

    public static String actionCancel(String... strings){
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String dataJSONString;
        try{
            Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                    .appendQueryParameter(QUERY_TYPE,"action")
                    .appendQueryParameter(QUERY_ACTION,strings[0])
                    .appendQueryParameter("id",strings[1])
                    .build();
            URL requestURL = new URL(builtUri.toString());
            urlConnection = (HttpURLConnection)requestURL.openConnection();
            urlConnection.setRequestMethod("GET");
            try{
                urlConnection.setConnectTimeout(3000);
                urlConnection.connect();
            } catch (UnknownHostException e){
                e.printStackTrace();
                return null;
            }
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream==null){
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line=reader.readLine())!=null){
                buffer.append(line + "\n");
            }
            if (buffer.length()==0){
                return null;
            }
            dataJSONString = buffer.toString();
        } catch (Exception e){
            e.printStackTrace();
            return null;
        } finally {
            if(urlConnection!=null){
                urlConnection.disconnect();
            }
            if(reader!=null){
                try{
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        Log.d(LOG_TAG,dataJSONString);
        return dataJSONString;
    }
}
