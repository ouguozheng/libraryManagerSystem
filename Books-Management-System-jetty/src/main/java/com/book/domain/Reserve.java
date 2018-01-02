package com.book.domain;

import java.util.Date;

/**
 * Created by 10394 on 2017/12/20.
 */

public class Reserve {

    private int reserveId;
    private int readerId;
    private long bookId;
    private String reserveTime;
    private int vaild;

    public int getReserveId() {
        return reserveId;
    }

    public void setReserveId(int reserveId) {
        this.reserveId = reserveId;
    }

    public int getReaderId() {
        return readerId;
    }

    public void setReaderId(int readerId) {
        this.readerId = readerId;
    }

    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    public String getReserveTime() {
        return reserveTime;
    }

    public void setReserveTime(String reserveTime) {
        this.reserveTime = reserveTime;
    }

    public int getVaild() {
        return vaild;
    }

    public void setVaild(int vaild) {
        this.vaild = vaild;
    }

    @Override
    public String toString(){
        return "预定id:" + this.getReserveId() + "读者：" + this.getReaderId() + "书籍：" + this.getBookId() + "日期：" + this.getReserveTime() + "vaild" + this.getVaild();
    }
}
