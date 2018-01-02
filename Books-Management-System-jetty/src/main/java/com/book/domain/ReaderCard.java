package com.book.domain;

import org.omg.CORBA.PUBLIC_MEMBER;

import java.io.Serializable;

public class ReaderCard implements Serializable{

    private int readerId;
    private String name;
    private String passwd;
    private int cardState;
    private int cardType;

    public int getCardType() {
        return cardType;
    }

    public void setCardType(int cardType) {
        this.cardType = cardType;
    }

    public void setCardState(int cardState) {
        this.cardState = cardState;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public void setReaderId(int readerId) {
        this.readerId = readerId;
    }

    public int getReaderId() {
        return readerId;
    }

    public int getCardState() {
        return cardState;
    }

    public String getName() {
        return name;
    }

    public String getPasswd() {
        return passwd;
    }
    @Override
    public String toString(){
        return "id "+ this.readerId + " name" + this.name + " pass" + this.passwd + " state" + this.cardState + " type" + this.cardType;
    }
}
