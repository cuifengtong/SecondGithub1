package com.example.juan_4;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.Xml;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adapter.MyAdapter;
import com.example.util.NetUtil;
import com.example.vo.News;

public class MainActivity extends Activity {
	private ListView mListView;
	private Context mContext;
	private final String URL="http://172.17.67.27:8080/service/xml/data.xml";
	private List<News> lists;
	private Handler mHandler = new Handler(){
		@Override
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
//				ArrayAdapter<News> adapter = new ArrayAdapter<News>
//					(mContext, android.R.layout.simple_list_item_1, lists);
				MyAdapter adapter = new MyAdapter(lists, mContext);
				mListView.setAdapter(adapter);
				break;

			}
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		init();
		mListView = (ListView) findViewById(R.id.listview);
		mContext = this;
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				InputStream is = NetUtil.getStringByUrl(URL);
				if(is!=null){
					lists = parserXml(is);
					mHandler.sendEmptyMessage(1);
					System.out.println("lists");
				}
			}
		}).start();
	}
	private void init(){
		ActionBar bar = getActionBar();
		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		bar.setHomeButtonEnabled(true);
		bar.setDisplayHomeAsUpEnabled(true);
		bar.setDisplayShowHomeEnabled(true);
		bar.setListNavigationCallbacks(new PopAdapter(), new OnNavigationListener() {
			
			@Override
			public boolean onNavigationItemSelected(int itemPosition, long itemId) {
				// TODO Auto-generated method stub
				return false;
			}
		});
	}
	class PopAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 3;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			TextView text = new TextView(MainActivity.this);
			text.setText("ÍøÒ×¿Æ¼¼"+position);
			text.setTextColor(Color.WHITE);
			return text;
		}
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.anims, menu);
		return super.onCreateOptionsMenu(menu);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		 if(item.getItemId() == android.R.id.home)
	        {
	            finish();
	            return true;
	        }
		switch(item.getItemId()){
		case R.id.add:
			Toast.makeText(mContext, "actionbar ok!", 1).show();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private List<News> parserXml(InputStream str){
		List<News> list = new ArrayList<News>();
		XmlPullParser parser = Xml.newPullParser();
		try {
			parser.setInput(str, "utf-8");
			int eventCode = parser.getEventType();
			String tagName = "";
			News news = null;
			while(eventCode!=XmlPullParser.END_DOCUMENT){
				switch(eventCode){
				case XmlPullParser.START_TAG:
					tagName = parser.getName();
					if("book".equals(tagName)){
						news = new News();
						news.setImage(parser.getAttributeValue(0));
					}
					break;
				case XmlPullParser.TEXT:
					String text = parser.getText();
					if("bookName".equals(tagName)){
						news.setName(text);
					}else if("author".equals(tagName)){
						news.setAuthor(text);
					}else if("price".equals(tagName)){
						news.setPrice(text);
					}
					break;
				case XmlPullParser.END_TAG:
					tagName = parser.getName();
					if("book".equals(tagName)){
						list.add(news);
						news = null;
					}
					tagName = "";
					break;
				}
				eventCode = parser.next();
			}
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.e("sssssssssssss", list.toString());
		return list;

	}
}
