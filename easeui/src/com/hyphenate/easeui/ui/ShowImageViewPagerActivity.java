package com.hyphenate.easeui.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.target.Target;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.widget.myphotoview.PhotoView;
import com.hyphenate.easeui.widget.photoview.SaveCallback;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class ShowImageViewPagerActivity extends AppCompatActivity implements SaveCallback {
    private Context context;
    private ArrayList<String> imageList;
    private String url;
    private int currentPage;
    private boolean error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        context = ShowImageViewPagerActivity.this;

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            if (bundle.containsKey("images")) {
                imageList = bundle.getStringArrayList("images");
            }
            if (bundle.containsKey("clickedIndex")) {
                currentPage = bundle.getInt("clickedIndex");
            }
        }
        url = imageList.get(currentPage);
        findView();
    }


    protected void findView() {
        final ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);

        viewPager.setAdapter(new SamplePagerAdapter(context, this));
        viewPager.setCurrentItem(currentPage);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                url = imageList.get(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    class SamplePagerAdapter extends PagerAdapter {
        Context context;
        SaveCallback callback;

        private void show1() {
            final Dialog bottomDialog = new Dialog(context, R.style.BottomDialog);
            View contentView = LayoutInflater.from(context).inflate(R.layout.dialog_content_normal, null);
            contentView.findViewById(R.id.save_picture).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bottomDialog.dismiss();
                    callback.onSave();
                }
            });
            bottomDialog.setContentView(contentView);
            ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
            layoutParams.width = context.getResources().getDisplayMetrics().widthPixels;
            contentView.setLayoutParams(layoutParams);
            bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
            bottomDialog.setCanceledOnTouchOutside(true);
            bottomDialog.show();
        }

        public SamplePagerAdapter(Context context, SaveCallback callback) {
            this.context = context;
            this.callback = callback;
        }


        @Override
        public int getCount() {
            return imageList.size();
        }

        @Override
        public View instantiateItem(ViewGroup container, int position) {
            final PhotoView photoView = new PhotoView(container.getContext());

            Glide.with(context)
                    .load(imageList.get(position))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .dontAnimate()
                    .into(photoView);
            container.addView(photoView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            photoView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    show1();
                    return false;
                }
            });
            photoView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callback.onFinish();
                }
            });
            return photoView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

    }

    // 保存图片到手机
    public void download(final String url) {

        new AsyncTask<Void, Integer, File>() {

            @Override
            protected File doInBackground(Void... params) {
                File file = null;
                int byteread = 0;
                try {
                    FutureTarget<File> future = Glide
                            .with(context)
                            .load(url)
                            .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);

                    file = future.get();

                    // 首先保存图片
                    File pictureFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsoluteFile();

                    File appDir = new File(pictureFolder, "RNSS");
                    if (!appDir.exists()) {
                        appDir.mkdirs();
                    }
                    String fileName = System.currentTimeMillis() + ".jpg";
                    File destFile = new File(appDir, fileName);

                    InputStream inStream = new FileInputStream(file); //读入原文件
                    FileOutputStream fos = new FileOutputStream(destFile);
                    byte[] buffer = new byte[1024];
                    while ((byteread = inStream.read(buffer)) != -1) {
                        fos.write(buffer, 0, byteread);
                    }
                    inStream.close();
                    fos.close();

                    // 最后通知图库更新
                    sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                            Uri.fromFile(new File(destFile.getPath()))));

                } catch (Exception e) {
                    Log.e("save file error", e.getMessage());
                    error = true;
                }
                return file;
            }

            @Override
            protected void onPostExecute(File file) {
                if (error) {
                    Toast.makeText(context, "保存失败", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "已保存到 Pictures/RNSS", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                super.onProgressUpdate(values);
            }
        }.execute();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onSave() {
        error = false;
        download(url);
    }

    @Override
    public void onFinish() {
        finish();
    }
}
