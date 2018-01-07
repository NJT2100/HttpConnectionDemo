/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mydomain.myproject;

/**
 *
 * @author nathan
 */
import java.net.URL;
import java.io.File;
import java.io.InputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;

public class HttpUrlReader {
    
    public int sendGet(String url, String filename) {
        URL obj = null;
        HttpURLConnection connection = null;
        try {
            obj = new URL(url);
            connection = (HttpURLConnection)obj.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
        } catch (MalformedURLException mal) {
            return HttpURLConnection.HTTP_NOT_FOUND;
        } catch (ProtocolException proto) {
            return HttpURLConnection.HTTP_FORBIDDEN;
        } catch (IOException io) {
            return HttpURLConnection.HTTP_BAD_REQUEST;
        }
        
        try (
            InputStream in = connection.getInputStream();
            FileOutputStream fout = new FileOutputStream(new File(filename));)
        {         
            byte[] b = new byte[2048];
            int length;         
            while ((length = in.read(b)) != -1) {
                fout.write(b, 0, length);
            }        
            return connection.getResponseCode();
        } catch (IOException io) {
            return -1;
        }
    }
}
