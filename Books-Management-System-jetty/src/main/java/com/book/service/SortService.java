package com.book.service;

import com.book.dao.SortMapper;
import com.book.domain.Sort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by 10394 on 2017/12/28.
 */
@Component
public class SortService {

    @Autowired
    private SortMapper sortMapper;

    public List<Sort> getAllSort() {
        return sortMapper.selectAllSort();
    }

    public int addSort(Sort sort) {
        return sortMapper.insertSort(sort);
    }

    public int updateSort(Sort sort) {
        return sortMapper.updateSort(sort);
    }

    public int deleteSortByID(int id) {
        return sortMapper.deleteSortByID(id);
    }

    public Sort getSortByID(int id){
        Sort sort = sortMapper.selectById(id);
        if(sort == null){
            System.out.println("分类为空!");
            return null;
        }
        return sort;
    }
}
