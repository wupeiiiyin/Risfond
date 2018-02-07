package com.risfond.rnss.entry;

/**
 * Created by Abbott on 2017/5/24.
 */

public class UnReadMessageCountEventBus {
    private String type;
    private int tip;

    public UnReadMessageCountEventBus(String type, int tip) {
        this.tip = tip;
        this.type = type;
    }

    public int getTip() {
        return tip;
    }

    public String getType() {
        return type;
    }
}
