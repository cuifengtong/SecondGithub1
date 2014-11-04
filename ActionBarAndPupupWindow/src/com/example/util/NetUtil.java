package com.example.util;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class NetUtil {
	public static InputStream getStringByUrl(String is){
		InputStream result = null;
		HttpGet get = new HttpGet(is);
		HttpParams params = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(params, 5000);
		HttpConnectionParams.setSoTimeout(params, 5000);
		HttpClient client = new DefaultHttpClient();
		HttpResponse resp = null;
		try {
			resp = client.execute(get);
			if(resp.getStatusLine().getStatusCode()==HttpStatus.SC_OK){
				HttpEntity entity = resp.getEntity();
				result = entity.getContent();
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	public static Bitmap getBitmapByUrl(String is){
		Bitmap result = null;
		HttpGet get = new HttpGet(is);
		HttpParams params = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(params, 5000);
		HttpConnectionParams.setSoTimeout(params, 5000);
		HttpClient client = new DefaultHttpClient();
		HttpResponse resp = null;
		try {
			resp = client.execute(get);
			if(resp.getStatusLine().getStatusCode()==HttpStatus.SC_OK){
				HttpEntity entity = resp.getEntity();
				result = BitmapFactory.decodeStream(entity.getContent());
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}