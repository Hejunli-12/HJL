package com.jl.tcs;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jl.tcs.adapter.RankAdapter;
import com.jl.tcs.model.RankBean;

import java.util.ArrayList;
import java.util.List;

public class MainActivity
        extends AppCompatActivity
        implements TcsScoreListener, View.OnClickListener {
    private static final String TAG =void.class.getSimpleName();
    private TcsView tcsView;
    private ImageButton restartView;
    private ImageButton pauseButtonView;
    private ImageButton settingButtonView;
    private Button upButtonView;
    private Button downButtonView;
    private Button leftButtonView;
    private ImageButton startButtonView;
    private ImageButton rankButtonView;
    private Button rightButtonView;
    private TextView scoreTextView;
    private Chronometer timer;
    private long mRecordTime;
    private RankAdapter rankAdapter;
    private boolean mStart=false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        scoreTextView = findViewById(R.id.tv_score);
        tcsView = findViewById(R.id.tcs_22);
        settingButtonView = findViewById(R.id.btn_setting);
        startButtonView = findViewById(R.id.btn_start);
        restartView = findViewById(R.id.btn_restart);
        pauseButtonView = findViewById(R.id.btn_pause);
        upButtonView = findViewById(R.id.btn_up);
        downButtonView = findViewById(R.id.btn_down);
        leftButtonView = findViewById(R.id.btn_left);
        rightButtonView = findViewById(R.id.btn_right);
        rankButtonView = findViewById(R.id.btn_rank);
        timer = (Chronometer) findViewById(R.id.timer);

        initEvent();
    }
    /**
     * initial event
     */
    private void initEvent() {
        tcsView.setOnClickListener(this);
        tcsView.setTcsScoreListener(this::onTCSScore);
        settingButtonView.setOnClickListener(this);
        startButtonView.setOnClickListener(this);
        restartView.setOnClickListener(this);
        pauseButtonView.setOnClickListener(this);
        upButtonView.setOnClickListener(this);
        downButtonView.setOnClickListener(this);
        leftButtonView.setOnClickListener(this);
        rightButtonView.setOnClickListener(this);
        rankButtonView.setOnClickListener(this);
        // initial adapter
        rankAdapter = new RankAdapter(this);
    }


    private Handler mHander=new Handler();
    private int mCount=0;

    private Runnable mCounter=new Runnable() {
        @Override
        public void run() {
            timer.setBase(SystemClock.elapsedRealtime());//计时器清零
            timer.start();
        }
    };

    @Override
    public void onTCSScore(int score) {
        if (scoreTextView != null) {
            scoreTextView.setText(String.valueOf(score));
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tcs_22:
                tcsView.invalidate();
                break;
            case R.id.btn_start:
                tcsView.start();
                if (!mStart) {
                    mStart = !mStart;
                    startButtonView.setImageResource(R.drawable.ic_pause);
                    if (mRecordTime != 0) {

                        timer.setBase(timer.getBase() + (SystemClock.elapsedRealtime() - mRecordTime));
                    } else {
                        timer.setBase(SystemClock.elapsedRealtime());
                    }

                    timer.start();
                } else {
                    mStart = !mStart;
                    startButtonView.setImageResource(R.drawable.ic_start);
                    tcsView.pause();
                    timer.stop();
                    mRecordTime = SystemClock.elapsedRealtime();
                }
                break;
            case R.id.btn_pause:
                if(mStart){

                }
                break;
            case R.id.btn_restart:
                tcsView.restart();
                    mHander.post(mCounter);
                break;
            case R.id.btn_up:
                tcsView.changeDirection(0);
                break;
            case R.id.btn_down:
                tcsView.changeDirection(-1);
                break;
            case R.id.btn_left:
                tcsView.changeDirection(1);
                break;
            case R.id.btn_right:
                tcsView.changeDirection(2);
                break;
            case R.id.btn_setting:
                View settingView = getLayoutInflater().inflate(R.layout.activity_setting, null);
                String a = getResources().getString(R.string.easy);
                String b = getResources().getString(R.string.normal);
                String c = getResources().getString(R.string.difficult);
                final String[] list = new String[]{a, b, c};
                AlertDialog.Builder Item = new AlertDialog.Builder(this)
                        //.setView(settingView)
                        .setTitle(R.string.select)
                        .setItems(list, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case 0:
                                        //tcsView.setStart(2100);
                                        tcsView.setSpeed(200);
                                        Toast.makeText(MainActivity.this, "0", Toast.LENGTH_SHORT).show();
                                        break;
                                    case 1:
                                        tcsView.setSpeed(100);
                                        Toast.makeText(MainActivity.this, "1", Toast.LENGTH_SHORT).show();
                                        break;
                                    case 2:
                                        tcsView.setSpeed(50);
                                        Toast.makeText(MainActivity.this, "2", Toast.LENGTH_SHORT).show();
                                        break;
                                }
                            }
                        });
                Item.create();
                Item.show();

                break;

            case R.id.btn_rank:
                View dialogView = getLayoutInflater().inflate(R.layout.activity_rank, null);
                new AlertDialog.Builder(this)
                        .setView(dialogView)
                        .setTitle(R.string.rank)
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(MainActivity.this, "继续游戏吧～", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .create().show();
                //实例化RecyclerView
                RecyclerView rankListView = dialogView.findViewById(R.id.rv_rank_list);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,
                        RecyclerView.VERTICAL, false);
                rankListView.setLayoutManager(layoutManager);
                rankListView.setAdapter(rankAdapter);
                //
                List<RankBean> rankBeans = new ArrayList<>();
                for (int i = 1; i < 11; i++) {
                    //Random random = new Random();
                    // int result = random.nextInt(10) * i;
                    RankBean rankBean = new RankBean();
                    rankBean.setUsername(rankBean.getUsername());
                   rankBean.setScore(String.valueOf(i));
                    rankBeans.add(rankBean);
                }
                rankAdapter.setList(rankBeans);
                break;
            default:
                break;
        }
    }

}
