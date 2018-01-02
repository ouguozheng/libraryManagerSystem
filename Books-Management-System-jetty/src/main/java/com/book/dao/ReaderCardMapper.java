package com.book.dao;

import com.book.domain.ReaderCard;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by 10394 on 2017/12/26.
 */
@Component
public interface ReaderCardMapper {
    List<ReaderCard> selectAllReaderCard();
    int selectMaxReaderCardId();
    ReaderCard selectReaderCardById(int readerId);
    int insertReaderCard(ReaderCard readercard);
    int updateReaderCard(ReaderCard readerCard);
    int deleteReaderCardById(int readerId);
    ReaderCard selectReaderCardByIdAndPass(Map<String,Object> map);
}
