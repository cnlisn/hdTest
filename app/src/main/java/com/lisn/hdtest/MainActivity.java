package com.lisn.hdtest;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.lisn.xhd.BitMapUtils;
import com.lisn.xhd.LsView;

import java.io.File;
import java.util.logging.Logger;

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
    private Button bt4;

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
        bt4 = (Button) findViewById(R.id.bt4);
        bt4.setOnClickListener(this);

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
            case R.id.bt4:
                //Uri uri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + R.getResourcePackageName(R.drawable.图片名称) + "/" + r.getResourceTypeName(R.drawable.图片名称) + "/" + r.getResourceEntryName(R.drawable.图片名称));
//                shareMsg("ActivityTitle","分享标题","msgText",getResources().getResourceName(R.drawable.a1) );
                Uri resourceUri = getResourceUri(MainActivity.this, R.drawable.a1);
                Log.e("-------", "url==" + resourceUri);
                String filePath = getRealFilePath(MainActivity.this, resourceUri);
                Log.e("-------", "filePath==" + filePath);
                break;
        }
    }
    /**
     * 分享功能
     *
     * @param activityTitle Activity的名字
     * @param msgTitle      消息标题
     * @param msgText       消息内容
     * @param imgPath       图片路径，不分享图片则传null
     */
    public void shareMsg(String activityTitle, String msgTitle, String msgText,
                         String imgPath) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        if (imgPath == null || imgPath.equals("")) {
            intent.setType("text/plain"); // 纯文本
        } else {
            File f = new File(imgPath);
            if (f != null && f.exists() && f.isFile()) {
                Uri u = Uri.fromFile(f);
                intent.putExtra(Intent.EXTRA_STREAM, u);
                intent.setType("image/jpg");
            }
        }
        startActivity(Intent.createChooser(intent, activityTitle));
    }

//    Android根据Resource id 拼接Resource uri
    public static Uri getResourceUri(Context context, int res) {
        try {
            Context packageContext = context.createPackageContext(context.getPackageName(),
                    Context.CONTEXT_RESTRICTED);
            Resources resources = packageContext.getResources();
            String appPkg = packageContext.getPackageName();
            String resPkg = resources.getResourcePackageName(res);
            String type = resources.getResourceTypeName(res);
            String name = resources.getResourceEntryName(res);


            Uri.Builder uriBuilder = new Uri.Builder();
            uriBuilder.scheme(ContentResolver.SCHEME_ANDROID_RESOURCE);
            uriBuilder.encodedAuthority(appPkg);
            uriBuilder.appendEncodedPath(type);
            if (!appPkg.equals(resPkg)) {
                uriBuilder.appendEncodedPath(resPkg + ":" + name);
            } else {
                uriBuilder.appendEncodedPath(name);
            }
            return uriBuilder.build();

        } catch (Exception e) {
            return null;
        }
    }

    //    Android根据Resource uri获取文件路径
    public static String getRealFilePath( final Context context, final Uri uri ) {
        if ( null == uri ) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if ( scheme == null )
            data = uri.getPath();
        else if ( ContentResolver.SCHEME_FILE.equals( scheme ) ) {
            data = uri.getPath();
        } else if ( ContentResolver.SCHEME_CONTENT.equals( scheme ) ) {
            Cursor cursor = context.getContentResolver().query( uri, new String[] { MediaStore.Images.ImageColumns.DATA }, null, null, null );
            if ( null != cursor ) {
                if ( cursor.moveToFirst() ) {
                    int index = cursor.getColumnIndex( MediaStore.Images.ImageColumns.DATA );
                    if ( index > -1 ) {
                        data = cursor.getString( index );
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

}
