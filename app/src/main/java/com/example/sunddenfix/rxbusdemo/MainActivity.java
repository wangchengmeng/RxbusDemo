package com.example.sunddenfix.rxbusdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.sunddenfix.rxbusdemo.config.Constant;
import com.example.sunddenfix.rxbusdemo.model.StudentModel;
import com.example.sunddenfix.rxbusdemo.rx.EventBusModel;
import com.example.sunddenfix.rxbusdemo.rx.RxBus;
import com.example.sunddenfix.rxbusdemo.rx.Subscribe;
import com.example.sunddenfix.rxbusdemo.rx.ThreadMode;

public class MainActivity extends AppCompatActivity {

    private TextView mTvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTvResult = (TextView) findViewById(R.id.tv_result);
        Button btnPost_1 = (Button) findViewById(R.id.btn_post_1);
        Button btnPost_2 = (Button) findViewById(R.id.btn_post_2);
        btnPost_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //发送通知
                RxBus.getDefault().post(new EventBusModel(Constant.KEY_POST_MESSAGE_1, new StudentModel("wangchengmeng", "man")));
            }
        });

        btnPost_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //发送通知
                RxBus.getDefault().post(new EventBusModel(Constant.KEY_POST_MESSAGE_2, "this is message that post 2"));
            }
        });
    }

    //接受并处理通知的方法  可以指定线程
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void event(EventBusModel busModel) {
        String action = busModel.getEventBusAction();
        switch (action) {
            case Constant.KEY_POST_MESSAGE_1:
                StudentModel studentModel = (StudentModel) busModel.getEventBusObject();
                mTvResult.setText(studentModel.getName() + ":" + studentModel.getSex());
                break;
            case Constant.KEY_POST_MESSAGE_2:
                mTvResult.setText((String) busModel.getEventBusObject());
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        RxBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getDefault().unRegister(this);
    }
}
