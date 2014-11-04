package com.example.util;

import java.lang.ref.SoftReference;
import java.util.HashMap;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;

public class AsyncLoadImage {
	private Context mContext;
	private HashMap<String,SoftReference<Bitmap>> mHashMap;
	public AsyncLoadImage(Context mContext) {
		super();
		this.mContext = mContext;
		mHashMap = new HashMap<String, SoftReference<Bitmap>>();
	}
	public Bitmap imageLoad(final String strUrl , final ImageCallBack mImageCallBack){
		Bitmap bitmap = null;
		if(mHashMap.containsKey(strUrl)){
			bitmap = mHashMap.get(strUrl).get();
			if(bitmap!=null){
				return bitmap;
			}
		}
		final Handler mHandler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				
				switch (msg.what) {
				case 1:
					mImageCallBack.onImageLoad(strUrl, (Bitmap)msg.obj);
					break;
				}
				
			}
		};
		new Thread(new Runnable() {		
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Bitmap bit = NetUtil.getBitmapByUrl(strUrl);
				if(bit!=null){
					mHashMap.put(strUrl, new SoftReference<Bitmap>(bit));
					Message msg = mHandler.obtainMessage(1, bit);
					mHandler.sendMessage(msg);
				}
			}
		}).start();
		return bitmap;
	}
	public interface ImageCallBack{
		void onImageLoad(String str,Bitmap bit);
	}
}
