package com.book.domain;

import java.io.Serializable;

public class Lend implements Serializable {

    private long sernum;
    private long bookId;
    private int readerId;
    private String lendDate;
    private String backDate;
    private int vaild;//是否归还 0 没有
    public int getVaild() {
        return vaild;
    }

    public void setVaild(int vaild) {
        this.vaild = vaild;
    }



    public long getSernum() {
        return sernum;
    }

    public void setSernum(long sernum) {
        this.sernum = sernum;
    }

    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    public int getReaderId() {
        return readerId;
    }

    public void setReaderId(int readerId) {
        this.readerId = readerId;
    }

    public String getLendDate() {
        return lendDate;
    }

    public void setLendDate(String lendDate) {
        this.lendDate = lendDate;
    }

    public String getBackDate() {
        return backDate;
    }

    public void setBackDate(String backDate) {
        this.backDate = backDate;
    }
}
