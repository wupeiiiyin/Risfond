package com.hyphenate.easeui.domain;

import com.hyphenate.easeui.R;
import com.hyphenate.easeui.domain.EaseEmojicon.Type;

import java.util.Arrays;

public class EmojiconAddGroupData {

    private static String[] names = new String[]{
            "哭",
            "得瑟",
            "跳舞",
            "耸肩",
            "bye",
            "升天",
            "痴迷",
            "晚安",
            "爱你",
            "摆脱",
            "no",
            "生日快乐",
            "哼",
            "抓狂",
            "扭一扭",
            "尬舞",
    };

    private static int[] icons = new int[]{
            R.drawable.tuzki_1_cover,
            R.drawable.tuzki_2_cover,
            R.drawable.tuzki_3_cover,
            R.drawable.tuzki_4_cover,
            R.drawable.tuzki_5_cover,
            R.drawable.tuzki_6_cover,
            R.drawable.tuzki_7_cover,
            R.drawable.tuzki_8_cover,
            R.drawable.tuzki_9_cover,
            R.drawable.tuzki_10_cover,
            R.drawable.tuzki_11_cover,
            R.drawable.tuzki_12_cover,
            R.drawable.tuzki_13_cover,
            R.drawable.tuzki_14_cover,
            R.drawable.tuzki_15_cover,
            R.drawable.tuzki_16_cover,
    };

    private static int[] bigIcons = new int[]{
            R.drawable.tuzki_1,
            R.drawable.tuzki_2,
            R.drawable.tuzki_3,
            R.drawable.tuzki_4,
            R.drawable.tuzki_5,
            R.drawable.tuzki_6,
            R.drawable.tuzki_7,
            R.drawable.tuzki_8,
            R.drawable.tuzki_9,
            R.drawable.tuzki_10,
            R.drawable.tuzki_11,
            R.drawable.tuzki_12,
            R.drawable.tuzki_13,
            R.drawable.tuzki_14,
            R.drawable.tuzki_15,
            R.drawable.tuzki_16,
    };


    private static final EaseEmojiconGroupEntity DATA = createData();

    private static EaseEmojiconGroupEntity createData() {
        EaseEmojiconGroupEntity emojiconGroupEntity = new EaseEmojiconGroupEntity();
        EaseEmojicon[] datas = new EaseEmojicon[icons.length];
        for (int i = 0; i < icons.length; i++) {
            datas[i] = new EaseEmojicon(icons[i], null, Type.BIG_EXPRESSION);
            datas[i].setBigIcon(bigIcons[i]);
            //you can replace this to any you want
            datas[i].setName(names[i]);
            datas[i].setIdentityCode("" + i);
            datas[i].setExpressioType("tuzki");
        }
        emojiconGroupEntity.setEmojiconList(Arrays.asList(datas));
        emojiconGroupEntity.setIcon(R.drawable.tuzki);
        emojiconGroupEntity.setType(Type.BIG_EXPRESSION);
        return emojiconGroupEntity;
    }


    public static EaseEmojiconGroupEntity getData() {
        return DATA;
    }
}
