package com.book.service;

import com.book.dao.ReaderCardMapper;
import com.book.dao.ReaderInfoMapper;
import com.book.domain.ReaderCard;
import com.book.domain.ReaderInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LoginService {

    @Autowired
    private ReaderCardMapper readerCardMapper;
    @Autowired
    private ReaderInfoMapper readerInfoMapper;


    public ReaderCard findReaderCardByUserId(int readerId) {
        return readerCardMapper.selectReaderCardById(readerId);
    }

    public ReaderInfo findReaderInfoByReaderId(int readerId) {
        return readerInfoMapper.selectByReaderId(readerId);
    }

    public int updateReaderCard(ReaderCard readerCard) {
        if (!readerCard.getPasswd().equals("")) {
            ReaderCard readerCard1 = readerCardMapper.selectReaderCardById(readerCard.getReaderId());
            if (readerCard1 != null) {
                return readerCardMapper.updateReaderCard(readerCard);
            } else {
                return -1;
            }
        } else {
            return -2;
        }
    }

    public ReaderCard findReaderCardByIdPass(int id, String password) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("readerId", id);
        map.put("passwd", password);
        return readerCardMapper.selectReaderCardByIdAndPass(map);
    }

    public List<ReaderCard> getAllReaderCard() {
        List<ReaderCard> li = readerCardMapper.selectAllReaderCard();
        for (int i = 0; i < li.size(); i++) {
            System.out.println(li.get(i).toString());
        }
        return li;
    }
}
