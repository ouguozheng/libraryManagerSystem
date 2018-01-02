package com.book.dao;

import com.book.domain.Reserve;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by 10394 on 2017/12/26.
 */
@Component
public interface ReserveMapper {
    List<Reserve> selectAllReserve();
    int insertReserve(Reserve reserve);
    List<Reserve> selectReserveByReaderId(int readerId);
    int selectMaxReserveId();
    int updateReserve(Reserve reserve);
    List<Reserve> selectResveBySuccessVaild(int vaild);
    Reserve selectReserveByReaderIdAndBookIdAndVaild(Map<String,Object> map);
}
