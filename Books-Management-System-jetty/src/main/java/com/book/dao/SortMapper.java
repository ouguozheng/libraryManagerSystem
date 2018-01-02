package com.book.dao;

import com.book.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by 10394 on 2017/12/28.
 */
@Component
public interface SortMapper {
    List<Sort> selectAllSort();
    int insertSort(Sort sort);
    int updateSort(Sort sort);
    int deleteSortByID(int classId);
    Sort selectById(int classId);
}
