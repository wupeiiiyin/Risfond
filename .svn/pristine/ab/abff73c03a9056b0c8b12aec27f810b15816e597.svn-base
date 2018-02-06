package com.risfond.rnss.common.utils.image;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.risfond.rnss.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.io.File;

/**
 * Created by Abbott on 2017/3/24.
 */

public class ImageUtil {

    public static void loadImage(Context context, String url, ImageView imageView, Transformation transformation) {
        if (!TextUtils.isEmpty(url)) {
            Picasso.with(context)
                    .load(url)
                    .placeholder(R.mipmap.person_default)
                    .error(R.mipmap.person_default)
                    .resizeDimen(R.dimen.img_width, R.dimen.img_height)
                    .centerCrop()
                    .transform(transformation)
                    .into(imageView);
        } else {
            Picasso.with(context)
                    .load(R.mipmap.person_default)
                    .resizeDimen(R.dimen.img_width, R.dimen.img_height)
                    .centerCrop()
                    .transform(transformation)
                    .into(imageView);
        }

    }

    public static void loadFileImage(Context context, String pathUrl, ImageView imageView, Transformation transformation) {
        Picasso.with(context)
                .load(new File(pathUrl))
                .transform(transformation)
                .into(imageView);

    }



}

