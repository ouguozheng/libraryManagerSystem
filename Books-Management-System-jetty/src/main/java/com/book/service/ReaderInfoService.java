package com.book.service;

import com.book.dao.ReaderInfoMapper;
import com.book.domain.ReaderInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReaderInfoService {

    @Autowired
    private ReaderInfoMapper readerInfoMapper;

    public List<ReaderInfo> getAllReaderInfos(){
        return readerInfoMapper.selectAllReaderInfo();
    }

    public boolean deleteReaderInfo(int readerId){
        return readerInfoMapper.deleteReaderInfoByReaderId(readerId) > 0;
    }

    public ReaderInfo getReaderInfo(int readerId){
        return readerInfoMapper.selectByReaderId(readerId);
    }

    public boolean updateReaderInfo(ReaderInfo readerInfo){
        return readerInfoMapper.updateReaderInfo(readerInfo) > 0;
    }

    public boolean addReaderInfo(ReaderInfo readerInfo){
        return  readerInfoMapper.insertReaderInfo(readerInfo) > 0;
    }
}
