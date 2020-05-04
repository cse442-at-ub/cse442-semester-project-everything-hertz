package com.cse_442.ceccarelli.ubeventmanager;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class BackgroundWorker extends AsyncTask<String,Void,String> {


    private AsyncResponse delegate;

    public interface AsyncResponse {
        void processFinish(String output);
    }
    public BackgroundWorker(AsyncResponse delegate){
        this.delegate = delegate;
    }
    @Override
    protected String doInBackground(String... params) {
        String type = params[0];
        String get_points_url = "https://www-student.cse.buffalo.edu/CSE442-542/2020-spring/cse-442k/getPoints.php";
        String log_in_url = "https://www-student.cse.buffalo.edu/CSE442-542/2020-spring/cse-442k/getLogIn.php";
        String check_in_url = "https://www-student.cse.buffalo.edu/CSE442-542/2020-spring/cse-442k/log_attendence.php";
        String get_event_name_url = "https://www-student.cse.buffalo.edu/CSE442-542/2020-spring/cse-442k/getEventName.php";
        final String real_name_url = "https://www-student.cse.buffalo.edu/CSE442-542/2020-spring/cse-442k/getRealName.php";
        final String register_user_url = "https://www-student.cse.buffalo.edu/CSE442-542/2020-spring/cse-442k/register_user.php";
        if(type.equals("get_user_points")) {
            try {
                String user_name = params[1];
                URL url = new URL(get_points_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("user_name","UTF-8")+"="+URLEncoder.encode(user_name,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result="";
                String line= "";
                while((line = bufferedReader.readLine())!= null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(type.equals("get_event_name")) {
            try {
                String event_id = params[1];
                URL url = new URL(get_event_name_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("event_id","UTF-8")+"="+URLEncoder.encode(event_id,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result="";
                String line= "";
                while((line = bufferedReader.readLine())!= null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(type.equals("event_check_in")) {
            try {
                String user_name = params[1];
                String event_id = params[2];
                URL url = new URL(check_in_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("user_name","UTF-8")+"="+URLEncoder.encode(user_name,"UTF-8") + "&" +
                        URLEncoder.encode("event_id","UTF-8")+"="+event_id;
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result="";
                String line= "";
                while((line = bufferedReader.readLine())!= null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(type.equals("log_in")){
            try {
                String user_name = params[1];
                String password = params[2];
                URL url = new URL(log_in_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("username","UTF-8") + "=" + URLEncoder.encode(user_name,"UTF-8") + "&" +
                        URLEncoder.encode("password","UTF-8") + "=" + URLEncoder.encode(password,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result="";
                String line= "";
                while((line = bufferedReader.readLine())!= null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(type.equals("get_real_name")){
            try {
                String user_name = params[1];
                URL url = new URL(real_name_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("username","UTF-8") + "=" + URLEncoder.encode(user_name,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result="";
                String line= "";
                while((line = bufferedReader.readLine())!= null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(type.equals("register")){
            try {
                String user_name = params[1];
                String password = params[2];
                String first_name = params[3];
                String last_name = params[4];
                String school_year = params[5];
                String major = params[6];
                Log.d("BackgroundWorker", user_name);
                Log.d("BackgroundWorker", password);
                Log.d("BackgroundWorker", first_name);
                Log.d("BackgroundWorker", last_name);
                Log.d("BackgroundWorker", school_year);
                Log.d("BackgroundWorker", major);


                URL url = new URL(register_user_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("username","UTF-8") + "=" + URLEncoder.encode(user_name,"UTF-8") + "&" +
                        URLEncoder.encode("password","UTF-8") + "=" + URLEncoder.encode(password,"UTF-8") + "&" +
                        URLEncoder.encode("first_name","UTF-8") + "=" + URLEncoder.encode(first_name,"UTF-8") + "&" +
                        URLEncoder.encode("last_name","UTF-8") + "=" + URLEncoder.encode(last_name,"UTF-8") + "&" +
                        URLEncoder.encode("school_year","UTF-8") + "=" + URLEncoder.encode(school_year,"UTF-8") + "&" +
                        URLEncoder.encode("major","UTF-8") + "=" + URLEncoder.encode(major,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                Log.d("BackgroundWorker", "made it to input stream");
                InputStream inputStream = httpURLConnection.getInputStream();
                Log.d("BackgroundWorker", "passed input stream");
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result="";
                String line= "";
                while((line = bufferedReader.readLine())!= null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Log.d("BackgroundWorker", "try died");
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        delegate.processFinish(result);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}