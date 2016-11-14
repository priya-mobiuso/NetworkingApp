package com.example.mobiuso.networkingapp;

import java.lang.String;

import android.os.Environment;
import android.view.View;
import android.webkit.WebView;
import android.os.Bundle;

import java.io.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;

import java.util.zip.ZipInputStream;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;


public class ShowContentFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_show_content, parent, false);
        WebView webpage=(WebView) v.findViewById(R.id.webview);;
        unpackZip(Environment.getExternalStorageDirectory().toString() + File.separator, "contents.zip");
        webpage.loadUrl("file:///"+ Environment.getExternalStorageDirectory().toString() + File.separator +"contents/" + "contents.html");
        return v;

    }
    private boolean unpackZip(String path, String zipname)
    {
        InputStream is;
        ZipInputStream zis;
        try
        {
            String filename;
            is = new FileInputStream(path + zipname);
            zis = new ZipInputStream(new BufferedInputStream(is));
            ZipEntry ze;
            byte[] buffer = new byte[1024];
            int count;
           System.out.println("try");
            while ((ze = zis.getNextEntry()) != null) {
                filename = ze.getName();
                System.out.println("while");
                if (ze.isDirectory()) {
                    File fmd = new File(path + filename);
                    fmd.mkdirs();
                    continue;
                }

                FileOutputStream fout = new FileOutputStream(path + filename);
                while ((count = zis.read(buffer)) != -1) {
                    fout.write(buffer, 0, count);
                }

                fout.close();
                zis.closeEntry();
            }
            zis.close();

        } catch(IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}























