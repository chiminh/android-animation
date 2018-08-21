package tech.sky.com.myapplication;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ImageView imvBigHorse;
    RelativeLayout layoutContainer;

    TextView tvSmallTime;
    TextView tvBigTime;

    int smallTime = 0;
    int bigTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvSmallTime = findViewById(R.id.tvSmallTime);
        tvBigTime = findViewById(R.id.tvBigTime);

        imvBigHorse = findViewById(R.id.imvBigHorse);
        layoutContainer = findViewById(R.id.layoutContainer);

        findViewById(R.id.btnStart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                smallTime ++;

                tvSmallTime.setText(String.valueOf(smallTime));
                final int imageSize = getResources().getDimensionPixelSize(R.dimen.base_40);

                final ImageView imageView = new ImageView(MainActivity.this);
                final RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(imageSize, imageSize);
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                layoutParams.rightMargin = imageSize;
                imageView.setLayoutParams(layoutParams);

                imageView.setImageResource(R.drawable.ic_planet);

                Random random = new Random();
                int value = random.nextInt(4);
                Animation animation;
                switch (value) {
                    case 0:
                        animation = AnimationUtils.loadAnimation(getApplicationContext(),
                                R.anim.bubble_1_animation);
                        break;
                    case 1:
                        animation = AnimationUtils.loadAnimation(getApplicationContext(),
                                R.anim.bubble_2_animation);
                        break;

                    case 2:
                        animation = AnimationUtils.loadAnimation(getApplicationContext(),
                                R.anim.bubble_3_animation);
                        break;
                    case 3:
                        animation = AnimationUtils.loadAnimation(getApplicationContext(),
                                R.anim.bubble_4_animation);
                        break;

                        default:
                            animation = AnimationUtils.loadAnimation(getApplicationContext(),
                                    R.anim.bubble_1_animation);
                            break;
                }

                Log.d("test", "value: " + value);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (imageView.getParent() != null) {
                                            layoutContainer.removeView(imageView);
                                        }

                                        smallTime--;
                                        tvSmallTime.setText(String.valueOf(smallTime));
                                    }
                                });
                            }
                        }, 100);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

                layoutContainer.addView(imageView);

                imageView.startAnimation(animation);



            }
        });

        findViewById(R.id.btnSecond).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bigTime ++;
                tvBigTime.setText(String.valueOf(bigTime));

                if (bigTime == 1) {
                    startBigAnimation();
                }
            }
        });

    }

    private void startBigAnimation() {
        if (bigTime > 0) {

            Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),
                    R.anim.bubble_big_animation);

            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    imvBigHorse.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    imvBigHorse.setVisibility(View.GONE);
                    bigTime --;
                    tvBigTime.setText(String.valueOf(bigTime));
                    startBigAnimation();
                }

                @Override
                public void onAnimationRepeat(Animation animation) { }
            });

            imvBigHorse.startAnimation(animation);
        }
    }
}
