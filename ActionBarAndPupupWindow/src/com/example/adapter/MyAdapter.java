package com.example.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.juan_4.R;
import com.example.util.AsyncLoadImage;
import com.example.util.AsyncLoadImage.ImageCallBack;
import com.example.vo.News;

public class MyAdapter extends BaseAdapter{
	private List<News> list;
	private Context mContext;
	private ViewHolder vhode;
	private AsyncLoadImage mAsyncLoadImage;
	private final String URL="http://172.17.67.27:8080/service/";
	public MyAdapter(List<News> list, Context mContext) {
		super();
		this.list = list;
		this.mContext = mContext;
		mAsyncLoadImage = new AsyncLoadImage(mContext);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub

		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, final ViewGroup parent) {
		// TODO Auto-generated method stub
		if(convertView==null){
			convertView=LayoutInflater.from(mContext).inflate(R.layout.item_layout,null);
			vhode = new ViewHolder();
			vhode.image = (ImageView) convertView.findViewById(R.id.image);
			vhode.name = (TextView) convertView.findViewById(R.id.title);
			vhode.author = (TextView) convertView.findViewById(R.id.content);
			vhode.price = (TextView) convertView.findViewById(R.id.flow);
			convertView.setTag(vhode);
		}else{
			vhode = (ViewHolder) convertView.getTag();
		}
		News n = list.get(position);
//		vhode.image.setImageResource(R.drawable.ic_launcher);
		vhode.name.setText(n.getName());
		vhode.author.setText(n.getAuthor());
		vhode.price.setText(n.getPrice());
		
		String imageUrl = URL+n.getImage();
		vhode.image.setTag(imageUrl);
		Bitmap bitmap = mAsyncLoadImage.imageLoad(imageUrl, new ImageCallBack() {
			
			@Override
			public void onImageLoad(String str, Bitmap bit) {
				// TODO Auto-generated method stub
				ImageView image = (ImageView) parent.findViewWithTag(str);
				if(image!=null && str!=null){
					image.setImageBitmap(bit);
				}
			}
		});
		if(bitmap!=null){
			vhode.image.setImageBitmap(bitmap);
		}else{
			vhode.image.setImageResource(R.drawable.ic_launcher);
		}
		return convertView;
	}

}
class ViewHolder{
	ImageView image;
	TextView name,author,price;
}
