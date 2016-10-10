package com.example.administrator.happyapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.happyapplication.HomeActivity;
import com.example.administrator.happyapplication.R;

public class AnimationActivity extends AppCompatActivity {
    private ImageView iv_animation;
    private TextView tv_welcome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
        tv_welcome= (TextView) findViewById(R.id.tv_welcome);
        tv_welcome.setVisibility(View.INVISIBLE);
        iv_animation= (ImageView) findViewById(R.id.iv_animation);
        Animation a= AnimationUtils.loadAnimation(this,R.anim.set_a);
        a.setDuration(5000);
        iv_animation.startAnimation(a);
        a.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                tv_welcome.setVisibility(View.VISIBLE);
                Intent intent=new Intent(AnimationActivity.this,HomeActivity.class);
                startActivity(intent);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
//        AlphaAnimation aa=new AlphaAnimation(0,1);
//        aa.setDuration(3000);
//        iv_animation.setAnimation(aa);
//        iv_animation.startAnimation(aa);
    }
}
