package com.example.myapplicationlmj;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Objects;

public class AnswerActivity extends AppCompatActivity {
    private TextView mAnswerTextView;
    private static final String EXTRA_ANSWER_SHOW="answer_shown";//返回的键值
    private ImageView mImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       Objects.requireNonNull(getSupportActionBar()).hide();//隐藏标题栏
        setContentView(R.layout.activity_answer);

        mAnswerTextView=findViewById(R.id.Anwer_text_View);
        mImageView=findViewById(R.id.imageView);
        Intent intent=getIntent();//获取传递过来的Intent对象
        String answer = intent.getStringExtra("msg");//获取键为msg的值
        mAnswerTextView.setText(answer);//显示到组件
        intent.putExtra(EXTRA_ANSWER_SHOW,"您已经查看了数据");
        setResult(Activity.RESULT_OK,intent);//返回代码和intent对象（包含要返回的数据）


    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
//        mImageView.setImageResource(R.drawable.animation_frame);
//        AnimationDrawable animationDrawable = (AnimationDrawable) mImageView.getDrawable();
//        animationDrawable.start();
        Animator anim = AnimatorInflater.loadAnimator(this,R.animator.animator_alpha);
        anim.setTarget(mImageView);
        anim.start();

        anim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                //TODO 动画开始时
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                //TODO 动画结束时
                ValueAnimator moneyAnimator=ValueAnimator.ofFloat(0f,136789.23f);//offloat浮点估值器方法
                moneyAnimator.setInterpolator(new AccelerateInterpolator());//插值器
                moneyAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {//当动画执行时发生
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float money= (float) animation.getAnimatedValue();//取出动画的值
                        mAnswerTextView.setText(String.format("%.2f$",money));
                    }
                });
                moneyAnimator.setDuration(10000);
                moneyAnimator.start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                //TODO 动画取消时

            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                //TODO 动画重复时
            }
        });
    }
}
