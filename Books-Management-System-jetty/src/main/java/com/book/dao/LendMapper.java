package com.book.dao;

import com.book.domain.Lend;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by 10394 on 2017/12/26.
 */
@Component
public interface LendMapper {
    List<Lend> selectAllLend();
    List<Lend> selectLendByReaderId(int readerId);
    List<Lend> selectLendByBookId(Long bookId);
    List<Lend> selectLendByReaderIdAndBookId(Map<String,Object> map);
    Lend selectLendByBookIdAndVaild(Map<String,Object> map);
    Lend selectLendBySernum(int sernum);
    int updateLend(Lend lend);
    int selectMaxId();
    int insertLend(Lend lend);
}
