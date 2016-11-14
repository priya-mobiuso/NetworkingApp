package com.example.mobiuso.networkingapp;
import java.net.*;
import java.io.*;
import android.os.AsyncTask;
import android.app.*;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import java.lang.*;

import android.os.Environment;
import android.view.View;
import org.json.JSONObject;
import android.widget.*;
import java.lang.String;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;

public class GetDataFragment extends Fragment {

    private TextView mTextView;

    public void onCreate(Bundle bn) {
        super.onCreate(bn);

        if (isNetworkAvailable()) {
            ConnectionTask task = new ConnectionTask();
            task.execute();
        } else {
            Toast.makeText(getContext(), "Network is not Available", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_get_data, parent, false);
        mTextView=(TextView)v.findViewById(R.id.et_url1);
        return v;
    }


    private boolean isNetworkAvailable() {
        boolean available = false;
        ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isAvailable()) {
            available = true;
        }
        return available;
    }

    private class ConnectionTask extends AsyncTask<Void, Void, String> {

        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog .setMessage( getResources().getString(R.string.download));
            progressDialog .setCancelable(false);
            progressDialog .show();
        }


        protected String doInBackground(Void... params) {
                String jsonurl = Constants.CONTENTS_URL;
                downloadFilesFromURL(jsonurl,"contents.json");
                try {

                String baseDir= Environment.getExternalStorageDirectory().toString() + File.separator;

                File file = new File(baseDir, "contents.json");

                String line = "";
                StringBuilder text = new StringBuilder();

                    FileReader fReader = new FileReader(file);
                    BufferedReader bReader = new BufferedReader(fReader);
                    while( (line = bReader.readLine()) != null  ){
                    text.append(line+"\n");
                    }
                    String data=text.toString();
                    bReader.close();
                    JSONObject obj = new JSONObject(data);
                    String mediaurl = (String) obj.get(Constants.key1);
                    String htmlurl = (String) obj.get(Constants.key2);
                    downloadFilesFromURL(mediaurl,"lab1201.mp4");
                    downloadFilesFromURL(htmlurl,"contents.zip");
                }
            catch (Exception e) {
                e.printStackTrace();
            }

            return null;

        }

        @Override
        protected void onPostExecute(String Hello) {
            super.onPostExecute(Hello);
            mTextView.setText(getResources().getString(R.string.files_downloaded));
            progressDialog.dismiss();
        }
    }

    public void downloadFilesFromURL(String url,String filename){
        try {
            HttpURLConnection urlConnection = null;
            URL url1 = new URL(url);
            urlConnection = (HttpURLConnection) url1.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoOutput(true);
            urlConnection.connect();
            String SDCardRoot = Environment.getExternalStorageDirectory().toString() ;
            File file = new File(SDCardRoot,filename);
            FileOutputStream fileOutput = new FileOutputStream(file);
            InputStream input = new BufferedInputStream(url1.openStream());

            byte[] buffer = new byte[1024];
            int bufferLength = 0;

            while ( (bufferLength = input.read(buffer)) > 0 ) {

                fileOutput.write(buffer, 0, bufferLength);
            }
            fileOutput.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }

    }
}


















