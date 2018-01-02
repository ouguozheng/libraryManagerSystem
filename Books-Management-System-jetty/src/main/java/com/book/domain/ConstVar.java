package com.book.domain;

/**
 * 通用常量
 * Created by 10394 on 2017/12/28.
 */
public class ConstVar {
    /* 普遍用户*/
    public static int CARDTYPE0 = 0;
    /* 教师*/
    public static int CARDTYPE1 = 1;
    /*管理员*/
    public static int CARDTYPE3 = 3;

    /*预定失败*/
    public static int RESERVEFAILED = 0;
    /*预定成功*/
    public static int RESERVESUCCESS = 1;
    /*预定过期*/
    public static int RESERVEOVERTIME = 3;

    /*正常登陆*/
    public static int READERCANLOGIN = 1;
    /*禁止登陆*/
    public static int READERLOGINFORBIN = 0;

    /*普通书籍*/
    public static int COMMBOOK = 0;
    /*教师书籍*/
    public static int TEACHERBOOK = 1;

    /*书籍在馆*/
    public static int BOOKFREE = 1;
    /*书籍借出*/
    public static int BOOKOUT = 0;
    /*书籍被预定*/
    public static int BOOKRESERVE = 3;

    /*读者借书未还*/
    public static int READERNOTRETURNBOOK = 0;
    /*读者借书已还*/
    public static int READERRETURNEDBOOK = 1;
}
