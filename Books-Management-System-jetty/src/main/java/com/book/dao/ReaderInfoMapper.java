package com.book.dao;

import com.book.domain.ReaderInfo;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by 10394 on 2017/12/26.
 */
@Component
public interface ReaderInfoMapper {
    List<ReaderInfo> selectAllReaderInfo();
    int insertReaderInfo(ReaderInfo readerInfo);
    ReaderInfo selectByReaderId(int readerId);
    int updateReaderInfo(ReaderInfo readerInfo);
    int deleteReaderInfoByReaderId(int readerId);
}
