package com.risfond.rnss.common.utils;

import android.os.CountDownTimer;
import android.widget.TextView;

/**
 * Created by john on 2016/7/14.
 */
public class CountDownTextViewUtil {

    // 倒计时timer
    private CountDownTimer countDownTimer;

    private TextView textView;

    /**
     * @param textView        需要显示倒计时的textView
     * @param countTime     需要进行倒计时的最大值,单位是秒
     * @param interval      倒计时的间隔，单位是秒
     * @param listener      倒计时完成后的回调
     */
    public CountDownTextViewUtil(final TextView textView, int countTime, int interval, final ICountDownListener listener) {

        this.textView = textView;
        // 由于CountDownTimer并不是准确计时，在onTick方法调用的时候，time会有1-10ms左右的误差，这会导致最后一秒不会调用onTick()
        // 因此，设置间隔的时候，默认减去了10ms，从而减去误差。
        // 经过以上的微调，最后一秒的显示时间会由于10ms延迟的积累，导致显示时间比1s长max*10ms的时间，其他时间的显示正常,总时间正常
        countDownTimer = new CountDownTimer(countTime * 1000, interval * 1000 - 10) {

            @Override
            public void onTick(long time) {
                // 第一次调用会有1-10ms的误差，因此需要+15ms，防止第一个数不显示，第二个数显示2s
                textView.setText(((time + 15) / 1000) + "s");
            }

            @Override
            public void onFinish() {
                listener.onCountDownFinish();
            }
        };
    }

    /**
     * 开始倒计时
     */
    public void start() {
        textView.setEnabled(false);
        countDownTimer.start();
    }

    /**
     * 停止倒计时
     */
    public void cancel() {
        textView.setEnabled(true);
        countDownTimer.cancel();
    }

    public interface ICountDownListener {
        void onCountDownFinish();
    }

}
