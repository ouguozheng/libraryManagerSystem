package com.book.service;

import com.book.dao.ReaderCardMapper;
import com.book.domain.ReaderCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReaderCardService {
    @Autowired
    private ReaderCardMapper readerCardMapper;

    public boolean addReaderCard(ReaderCard readerCard) {
        return readerCardMapper.insertReaderCard(readerCard) > 0;
    }

    public boolean updateReaderCard(ReaderCard readerCard) {
        return readerCardMapper.updateReaderCard(readerCard) > 0;
    }

    public ReaderCard getReaderCardById(int id) {

        return readerCardMapper.selectReaderCardById(id);
    }

    public int getReaderCardMaxId() {
        return readerCardMapper.selectMaxReaderCardId();
    }

    public List<ReaderCard> getAllReaderCard(){
        return readerCardMapper.selectAllReaderCard();
    }
}
