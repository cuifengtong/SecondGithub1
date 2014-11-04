package com.example.juan_4;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

public class AnimsActivity extends Activity {
	private ImageView image;
	private ScaleAnimation scale;
	private RotateAnimation rotate;
	private AnimationSet set;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anims);
        image = (ImageView) findViewById(R.id.animsImage);
        scale = new ScaleAnimation(3, 0, 3, 0);
        rotate = new RotateAnimation(-180, 180);
        set = new AnimationSet(true);
        set.addAnimation(scale);
        set.addAnimation(rotate);
        set.setDuration(2000);
        image.startAnimation(set);
        set.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				Intent it = new Intent(AnimsActivity.this,MainActivity.class);
				startActivity(it);
				finish();
			}
		});
    }
}
