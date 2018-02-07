package com.risfond.rnss.entry.eventBusVo;

/**
 * Created by Abbott on 2017/9/1.
 */

public class WorkOrderEventBus {
    private String type;

    public WorkOrderEventBus(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
