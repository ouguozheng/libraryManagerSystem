package com.book.service;

import com.book.dao.*;
import com.book.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LendService {

    @Autowired
    private LendMapper lendMapper;
    @Autowired
    private BookMapper bookMapper;
    @Autowired
    private ReaderCardMapper readerCardMapper;
    @Autowired
    private ReserveService reserveService;

    public boolean bookReturn(long bookId) {
        Book book = bookMapper.selectBookByBookId(bookId);
        if (book == null) {
            System.out.println("借阅归还失败：书籍不存在！");
            return false;
        }
        book.setState(1);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("bookId", bookId);
        map.put("vaild", 0);
        Lend lend = lendMapper.selectLendByBookIdAndVaild(map);
        if (lend == null) {
            System.out.println("借阅归还失败：借阅表不存在不存在！");
            return false;
        }
        lend.setBackDate(TimeFm.timeFormat.format(new Date()));
        lend.setVaild(1);
        book.setState(1);
        return lendMapper.updateLend(lend) > 0 && bookMapper.updateBooK(book) > 0;
    }

    public boolean bookLend(long bookId, int readerId) {
        Book book = bookMapper.selectBookByBookId(bookId);
        if (book == null) {
            System.out.println("借阅书籍失败：书籍不存在！");
            return false;
        }
        ReaderCard readerCard = readerCardMapper.selectReaderCardById(readerId);
        if (readerCard == null) {
            System.out.println("借阅书籍失败：读者不存在！");
            return false;
        }
        if (book.getState() == 0) {
            System.out.println("借阅书籍失败：书籍已被借出！");
            return false;
        } else if (book.getState() == 3) {
            Reserve reserve = reserveService.getReserveByReaderIdAndBookIdAndVaild(readerId, bookId, 1);
            if (reserve == null) {
                System.out.println("reserve null");
                return false;
            } else {
                if (readerId == reserve.getReaderId() && reserve.getBookId() == bookId) {
                    System.out.println("预定的读者借书成功！");
                    int sernum = lendMapper.selectMaxId() + 1;
                    Lend lend = new Lend();
                    lend.setSernum(sernum);
                    lend.setLendDate(TimeFm.timeFormat.format(new Date()));
                    lend.setBackDate(null);
                    lend.setVaild(0);
                    lend.setBookId(bookId);
                    lend.setReaderId(readerId);
                    book.setState(0);
                    reserve.setVaild(3);
                    return lendMapper.insertLend(lend) > 0 && bookMapper.updateBooK(book) > 0 && reserveService.updateReserve(reserve);
                }
            }
        }
        int sernum = lendMapper.selectMaxId() + 1;
        Lend lend = new Lend();
        lend.setSernum(sernum);
        lend.setLendDate(TimeFm.timeFormat.format(new Date()));
        lend.setBackDate(null);
        lend.setVaild(0);
        lend.setBookId(bookId);
        lend.setReaderId(readerId);
        book.setState(0);
        return lendMapper.insertLend(lend) > 0 && bookMapper.updateBooK(book) > 0;
    }

    public List<Lend> allLendList() {
        return lendMapper.selectAllLend();
    }

    public List<Lend> myLendList(int readerId) {
        return lendMapper.selectLendByReaderId(readerId);
    }

    public int getMaxLendId() {
        Integer id = lendMapper.selectMaxId();
        if (id == null) {
            return 0;
        } else {
            return id;
        }
    }
}
