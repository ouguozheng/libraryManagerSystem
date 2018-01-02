package com.book.dao;

import com.book.domain.Book;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by 10394 on 2017/12/26.
 */
@Component
public interface BookMapper {
    List<Book> selectAllBook();
    Book selectBookByBookId(@Param(value = "bookId")Long bookId);
    int updateBooK(Book book);
    int deleteBookByBookId(@Param(value = "bookId") Long bookId);
    int insertBook(Book book);
    Long selectMaxBookId();
    List<Book> selectBookByWord(String word);
}
