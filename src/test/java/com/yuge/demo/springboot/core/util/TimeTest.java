package com.yuge.demo.springboot.core.util;

import java.util.Date;

/**
 * @author: yuge
 * @date: 2021-01-15
 **/
public class TimeTest {

    private Date start;

    private Date end;

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public TimeTest start() {
        this.start = new Date();
        return this;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public TimeTest end() {
        this.end = new Date();
        return this;
    }

    /**
     * 获取执行时间
     *
     * @return
     */
    public long getExecuteTime() {
        return end.getTime() - start.getTime();
    }
}
