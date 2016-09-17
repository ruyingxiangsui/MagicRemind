package com.example.leixiaowei.magicremind;

import java.io.Serializable;
import java.util.List;

/**
 * Created by leixiaowei on 16/9/15.
 */
public class RemindTable implements Serializable {
    public RemindTable(List<RemindItem> remindItems) {
        this.remindItems = remindItems;
    }
    private List<RemindItem> remindItems;

    public void setRemindItems(List<RemindItem> remindItems) {
        this.remindItems = remindItems;
    }

    public List<RemindItem> getRemindItems() {
        return remindItems;
    }
}
