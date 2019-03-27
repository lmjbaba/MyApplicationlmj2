package com.example.myapplicationlmj;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.Manifest;
import android.graphics.drawable.Drawable;
import android.os.PatternMatcher;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity{
    private Button mbuttonZQ,mbuttonCW,mbuttonXYT,mbuttonDA;
    private TextView mQuestionText;
    private int mQuestionsIndex=0;
    private Question[] mQuestions=new Question[]{
            new Question(R.string.Q1,false),
            new Question(R.string.Q2,true),
            new Question(R.string.Q3,true),
            new Question(R.string.Q4,true),
            new Question(R.string.Q5,true),
            new Question(R.string.Q6,true),
            new Question(R.string.Q7,true),
            new Question(R.string.Q8,true)
    };
    private static final String TAG = "MainActivity";//日记来源
    private static final String KEY_INDEX="index";//索引键
    private static  final int RESULET_CODE_ANSWER=10;//请求代码(发给AnswerActivity)
    private TranslateAnimation mTranslateAnimation; //初始化成员变量
    private AlphaAnimation mAlphaAnimation;
    public void updateQuestion(){
        int i = mQuestions[mQuestionsIndex].getmTextId();//获取题目id
        mQuestionText.setText(i);//设置题目文本
//        mTranslateAnimation = new TranslateAnimation(0, 200, 0, 0); //这四个参数含义分别是当前View x起点坐标、x终点坐标、y起点坐标、y终点坐标
//        mTranslateAnimation.setDuration(2000); //动画持续时间
//        mTranslateAnimation.setRepeatCount(1); //重复次数(不包括第一次)
//        mTranslateAnimation.setRepeatMode(Animation.REVERSE); //动画执行模式，Animation.RESTART:从头开始，Animation.REVERSE:逆序
//        mQuestionText.startAnimation(mTranslateAnimation);
        Animation set = AnimationUtils.loadAnimation(this,R.anim.animation_list);
        mQuestionText.startAnimation(set);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState != null){
            mQuestionsIndex=savedInstanceState.getInt(KEY_INDEX,0);
            Log.d(TAG,"Bundle恢复状态");
        }
        Log.d(TAG,"onCreate创建界面");
        setContentView(R.layout.activity_main);
        mbuttonZQ = findViewById(R.id.Button_True);
        mbuttonCW = findViewById(R.id.Button_False);
        mbuttonXYT=findViewById(R.id.Button_Next);
        mbuttonDA=findViewById(R.id.Button_da);

        mQuestionText = findViewById(R.id.question_text_View);
        updateQuestion();
        mbuttonZQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkQuestion(true);
            }
        });
        mbuttonCW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkQuestion(false);
            }
        });
        mbuttonXYT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mQuestionsIndex = (mQuestionsIndex+1) % mQuestions.length;
                updateQuestion();
                mbuttonXYT.setEnabled(false);
                if (mQuestionsIndex==mQuestions.length-1){
                    Toast.makeText(MainActivity.this,R.string.last,Toast.LENGTH_LONG).show();
                    mbuttonXYT.setText(R.string.tvCXFX);//改按钮文字
                    updateButtonNext(R.drawable.ic_reset);//改按钮图片
                }
                if (mQuestionsIndex==0){

                    mbuttonXYT.setText(R.string.btXYT);//改按钮文字
                    updateButtonNext(R.drawable.ic_jiantouyou);//改按钮图片
                }
            }
        });
        mbuttonDA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ///Intent intent = new Intent(MainActivity.this,AnswerActivity.class);//显示调用
                //startActivity(intent);
//                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
//                    ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.CALL_PHONE},1);
//                }else {
//                    Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:17520439994"));
//                    startActivity(intent);
//                }
                String temp;
                if (mQuestions[mQuestionsIndex].ismAnswer()){
                    temp="正确";
                }else {
                    temp="错误";
                }
                Intent intent=new Intent(MainActivity.this,AnswerActivity.class);
                intent.putExtra("msg",temp);
                startActivityForResult(intent,RESULET_CODE_ANSWER);//需要反回值的启动activity方法
            }
        });
    }
    private void checkQuestion(boolean userAnswer){
        boolean trueAnswer=mQuestions[mQuestionsIndex].ismAnswer();
        int message;
        if(userAnswer==trueAnswer){
            message=R.string.yes;
            //mQuestionsIndex = (mQuestionsIndex+1) % mQuestions.length;
            // updateQuestion();
            mbuttonXYT.setEnabled(true);

        }else {
            message=R.string.no;
            mbuttonXYT.setEnabled(false);
        }
        Toast.makeText(MainActivity.this,message,Toast.LENGTH_LONG).show();
    }
    private void updateButtonNext(int imageID){
        Drawable d = getDrawable(imageID);//获取图片的ID
        d.setBounds(0,0,d.getMinimumWidth(),d.getMinimumHeight());
        mbuttonXYT.setCompoundDrawables(null,null,d,null);//图片位于按钮的位置
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG,"保存状态");
        outState.putInt(KEY_INDEX,mQuestionsIndex);//形成键值对
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {//请求代码，返回代码，返回来的intent对象
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==RESULET_CODE_ANSWER&&resultCode== Activity.RESULT_OK){
            String temp=data.getStringExtra("answer_shown");//取出msg对应的数据
            Toast.makeText(MainActivity.this,temp,Toast.LENGTH_LONG).show();
        }
    }
}



