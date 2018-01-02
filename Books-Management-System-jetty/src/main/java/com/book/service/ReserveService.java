package com.book.service;

import com.book.dao.ReserveMapper;
import com.book.domain.Reserve;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 10394 on 2017/12/20.
 */
@Component
public class ReserveService {

    @Autowired
    private ReserveMapper reserveMapper;

    public List<Reserve> getAllReserves(){
        return reserveMapper.selectAllReserve();
    }

    public boolean addNewReserve(Reserve reserve){
        int reserveId = reserveMapper.selectMaxReserveId();
        System.out.println("last id" + reserveId);
        reserve.setReserveId(reserveId + 1);
        return reserveMapper.insertReserve(reserve) > 0;
    }
    public boolean updateReserve(Reserve reserve){
        return reserveMapper.updateReserve(reserve) > 0;
    }

    public List<Reserve> getReservesByReaderId(int id){
        return reserveMapper.selectReserveByReaderId(id);
    }

    public List<Reserve> getResveBySuccessVaild(int vaild){
        return reserveMapper.selectReserveByReaderId(vaild);
    }
    public Reserve getReserveByReaderIdAndBookIdAndVaild(int readerId,Long bookId, int vaild){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("readerId",readerId);
        map.put("vaild",vaild);
        map.put("bookId",bookId);
        return reserveMapper.selectReserveByReaderIdAndBookIdAndVaild(map);
    }
}
