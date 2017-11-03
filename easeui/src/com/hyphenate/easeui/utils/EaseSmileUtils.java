/**
 * Copyright (C) 2016 Hyphenate Inc. All rights reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hyphenate.easeui.utils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.Spannable.Factory;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.Toast;

import com.hyphenate.easeui.R;
import com.hyphenate.easeui.controller.EaseUI;
import com.hyphenate.easeui.controller.EaseUI.EaseEmojiconInfoProvider;
import com.hyphenate.easeui.domain.EaseEmojicon;
import com.hyphenate.easeui.model.EaseDefaultEmojiconDatas;

public class EaseSmileUtils {
    public static final String DELETE_KEY = "em_delete_delete_expression";

    public static final String expression_1 = "[微笑]";
    public static final String expression_2 = "[撇嘴]";
    public static final String expression_3 = "[色]";
    public static final String expression_4 = "[发呆]";
    public static final String expression_5 = "[得意]";
    public static final String expression_6 = "[流泪]";
    public static final String expression_7 = "[害羞]";
    public static final String expression_8 = "[闭嘴]";
    public static final String expression_9 = "[睡]";
    public static final String expression_10 = "[大哭]";
    public static final String expression_11 = "[尴尬]";
    public static final String expression_12 = "[发怒]";
    public static final String expression_13 = "[调皮]";
    public static final String expression_14 = "[呲牙]";
    public static final String expression_15 = "[惊讶]";
    public static final String expression_16 = "[难过]";
    public static final String expression_17 = "[酷]";
    public static final String expression_18 = "[冷汗]";
    public static final String expression_19 = "[抓狂]";
    public static final String expression_20 = "[吐]";
    public static final String expression_21 = "[偷笑]";
    public static final String expression_22 = "[愉快]";
    public static final String expression_23 = "[白眼]";
    public static final String expression_24 = "[傲慢]";
    public static final String expression_25 = "[饥饿]";
    public static final String expression_26 = "[困]";
    public static final String expression_27 = "[惊恐]";
    public static final String expression_28 = "[流汗]";
    public static final String expression_29 = "[憨笑]";
    public static final String expression_30 = "[悠闲]";
    public static final String expression_31 = "[奋斗]";
    public static final String expression_32 = "[咒骂]";
    public static final String expression_33 = "[疑问]";
    public static final String expression_34 = "[嘘]";
    public static final String expression_35 = "[晕]";
    public static final String expression_36 = "[疯了]";
    public static final String expression_37 = "[衰]";
    public static final String expression_38 = "[骷髅]";
    public static final String expression_39 = "[敲打]";
    public static final String expression_40 = "[再见]";
    public static final String expression_41 = "[擦汗]";
    public static final String expression_42 = "[抠鼻]";
    public static final String expression_43 = "[鼓掌]";
    public static final String expression_44 = "[糗大了]";
    public static final String expression_45 = "[坏笑]";
    public static final String expression_46 = "[左哼哼]";
    public static final String expression_47 = "[右哼哼]";
    public static final String expression_48 = "[哈欠]";
    public static final String expression_49 = "[鄙视]";
    public static final String expression_50 = "[委屈]";
    public static final String expression_51 = "[快哭了]";
    public static final String expression_52 = "[阴险]";
    public static final String expression_53 = "[亲亲]";
    public static final String expression_54 = "[吓]";
    public static final String expression_55 = "[可怜]";
    public static final String expression_56 = "[菜刀]";
    public static final String expression_57 = "[西瓜]";
    public static final String expression_58 = "[啤酒]";
    public static final String expression_59 = "[篮球]";
    public static final String expression_60 = "[乒乓]";
    public static final String expression_61 = "[咖啡]";
    public static final String expression_62 = "[饭]";
    public static final String expression_63 = "[猪头]";
    public static final String expression_64 = "[玫瑰]";
    public static final String expression_65 = "[凋谢]";
    public static final String expression_66 = "[嘴唇]";
    public static final String expression_67 = "[爱心]";
    public static final String expression_68 = "[心碎]";
    public static final String expression_69 = "[蛋糕]";
    public static final String expression_70 = "[闪电]";
    public static final String expression_71 = "[炸弹]";
    public static final String expression_72 = "[刀]";
    public static final String expression_73 = "[足球]";
    public static final String expression_74 = "[瓢虫]";
    public static final String expression_75 = "[便便]";
    public static final String expression_76 = "[月亮]";
    public static final String expression_77 = "[太阳]";
    public static final String expression_78 = "[礼物]";
    public static final String expression_79 = "[拥抱]";
    public static final String expression_80 = "[强]";
    public static final String expression_81 = "[弱]";
    public static final String expression_82 = "[握手]";
    public static final String expression_83 = "[胜利]";
    public static final String expression_84 = "[抱拳]";
    public static final String expression_85 = "[勾引]";
    public static final String expression_86 = "[拳头]";
    public static final String expression_87 = "[差劲]";
    public static final String expression_88 = "[爱你]";
    public static final String expression_89 = "[NO]";
    public static final String expression_90 = "[OK]";
    public static final String expression_91 = "[爱情]";
    public static final String expression_92 = "[飞吻]";
    public static final String expression_93 = "[跳跳]";
    public static final String expression_94 = "[发抖]";
    public static final String expression_95 = "[怄火]";
    public static final String expression_96 = "[转圈]";
    public static final String expression_97 = "[磕头]";
    public static final String expression_98 = "[回头]";
    public static final String expression_99 = "[跳绳]";
    public static final String expression_100 = "[投降]";

    private static final Factory spannableFactory = Spannable.Factory
            .getInstance();

    private static final Map<Pattern, Object> emoticons = new HashMap<Pattern, Object>();


    static {
        EaseEmojicon[] emojicons = EaseDefaultEmojiconDatas.getData();
        for (EaseEmojicon emojicon : emojicons) {
            addPattern(emojicon.getEmojiText(), emojicon.getIcon());
        }
        EaseEmojiconInfoProvider emojiconInfoProvider = EaseUI.getInstance().getEmojiconInfoProvider();
        if (emojiconInfoProvider != null && emojiconInfoProvider.getTextEmojiconMapping() != null) {
            for (Entry<String, Object> entry : emojiconInfoProvider.getTextEmojiconMapping().entrySet()) {
                addPattern(entry.getKey(), entry.getValue());
            }
        }

    }

    /**
     * add text and icon to the map
     *
     * @param emojiText-- text of emoji
     * @param icon        -- resource id or local path
     */
    public static void addPattern(String emojiText, Object icon) {
        emoticons.put(Pattern.compile(Pattern.quote(emojiText)), icon);
    }


    /**
     * replace existing spannable with smiles
     *
     * @param context
     * @param spannable
     * @return
     */
    public static boolean addSmiles(Context context, Spannable spannable) {
        boolean hasChanges = false;
        for (Entry<Pattern, Object> entry : emoticons.entrySet()) {
            Matcher matcher = entry.getKey().matcher(spannable);
            while (matcher.find()) {
                boolean set = true;
                for (ImageSpan span : spannable.getSpans(matcher.start(),
                        matcher.end(), ImageSpan.class))
                    if (spannable.getSpanStart(span) >= matcher.start()
                            && spannable.getSpanEnd(span) <= matcher.end())
                        spannable.removeSpan(span);
                    else {
                        set = false;
                        break;
                    }
                if (set) {
                    hasChanges = true;
                    Object value = entry.getValue();
                    if (value instanceof String && !((String) value).startsWith("http")) {
                        File file = new File((String) value);
                        if (!file.exists() || file.isDirectory()) {
                            return false;
                        }
                        spannable.setSpan(new ImageSpan(context, Uri.fromFile(file)),
                                matcher.start(), matcher.end(),
                                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    } else {
                        Drawable drawable = ContextCompat.getDrawable(context, (Integer) value);
                        drawable.setBounds(0, 0, ScreenUtil.dip2px(context,20), ScreenUtil.dip2px(context,20));
                        VerticalImageSpan imageSpan = new VerticalImageSpan(drawable);
                        spannable.setSpan(/*new VerticalImageSpan(context, (Integer) value)*/imageSpan,
                                matcher.start(), matcher.end(),
                                Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                    }
                }
            }
        }
        handleText(spannable, spannable.toString());
        return hasChanges;
    }

    public static void handleText(Spannable spannable, String content) {
        //又碰上一个坑，在Android中的\p{Alnum}和Java中的\p{Alnum}不是同一个值，非得要我换成[a-zA-Z0-9]才行
        Pattern pattern = Pattern.compile("(http|https|ftp|svn|www)(://|.)([a-zA-Z0-9]+[/?.?])" +
                "+[a-zA-Z0-9]*\\??([a-zA-Z0-9]*=[a-zA-Z0-9]*&?)*");
        Matcher matcher = pattern.matcher(content);

        while (matcher.find()) {
            String url = matcher.group();
            int start = content.indexOf(url);
            if (start >= 0) {
                int end = start + url.length();
                spannable.setSpan(getClickableSpan(url), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
    }

    public static ClickableSpan getClickableSpan(final String url) {
        return new ClickableSpan() {

            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), url, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.rgb(0, 104, 248));
                ds.setUnderlineText(false);
                return;
            }
        };
    }

    public static Spannable getSmiledText(Context context, CharSequence text) {
        Spannable spannable = spannableFactory.newSpannable(text);
        addSmiles(context, spannable);
        return spannable;
    }

    public static boolean containsKey(String key) {
        boolean b = false;
        for (Entry<Pattern, Object> entry : emoticons.entrySet()) {
            Matcher matcher = entry.getKey().matcher(key);
            if (matcher.find()) {
                b = true;
                break;
            }
        }

        return b;
    }

    public static int getSmilesSize() {
        return emoticons.size();
    }


}
