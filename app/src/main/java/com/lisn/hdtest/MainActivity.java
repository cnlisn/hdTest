package com.lisn.hdtest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.lisn.xhd.BitMapUtils;
import com.lisn.xhd.LsView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv1;
    private TextView tv2;
    private TextView tv3;
    private Context mContext;
    private Button bt1;
    private Button bt2;
    private Button bt3;
    private LsView lsView11;
    private LsView lsView22;
    private LsView lsView33;
    private ImageView iv1;
    private ImageView iv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        initView();
    }

    private void initView() {
        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);
        tv3 = (TextView) findViewById(R.id.tv3);
        iv1 = (ImageView) findViewById(R.id.IV1);
        iv2 = (ImageView) findViewById(R.id.IV2);

        bt1 = (Button) findViewById(R.id.bt1);
        bt1.setOnClickListener(this);
        bt2 = (Button) findViewById(R.id.bt2);
        bt2.setOnClickListener(this);
        bt3 = (Button) findViewById(R.id.bt3);
        bt3.setOnClickListener(this);

        LsView lsView1 = new LsView(mContext);
        lsView1.setTargetView(tv1);
        lsView1.setBadgeCount(1);

        LsView lsView2 = new LsView(mContext);
        lsView2.setTargetView(tv2);
        lsView2.setBadgeCount(2);
        LsView lsView3 = new LsView(mContext);
        lsView3.setTargetView(tv3);
        lsView3.setBadgeCount(3);

        lsView11 = new LsView(mContext);
        lsView11.setTargetView(bt1);
        lsView11.setBadgeCount(3);

        lsView22 = new LsView(mContext);
        lsView22.setTargetView(bt2);
        lsView22.setBadgeCount(3);

        lsView33 = new LsView(mContext);
        lsView33.setTargetView(bt3);
        lsView33.setBadgeCount(3);



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt1:
                lsView11.incrementBadgeCount(1);
                Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.a1);
                iv1.setImageBitmap(bm);
                Bitmap bitmap = BitMapUtils.GetRoundedCornerBitmap(bm);
                iv2.setImageBitmap(bitmap);

                break;
            case R.id.bt2:
                lsView22.incrementBadgeCount(2);
                break;
            case R.id.bt3:
                lsView22.decrementBadgeCount(1);
                break;
        }
    }
}
