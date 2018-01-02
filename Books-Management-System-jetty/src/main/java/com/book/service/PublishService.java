package com.book.service;

import com.book.dao.PublishMapper;
import com.book.domain.Publish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublishService {
    @Autowired
    private PublishMapper publishMapper;

    public int addPublish(Publish publish) {
        return publishMapper.insertPublish(publish);
    }

    public List<Publish> selectAllPublish() {
        return publishMapper.selectAllPublish();
    }

    public Publish getPublishById(int id) {
        return publishMapper.selectPublishById(id);
    }

    public int deletePublish(int id) {
        return publishMapper.deletePublish(id);
    }

    public int updatePublish(Publish publish) {
        return publishMapper.updatePublish(publish);
    }

    public int getMaxPublishId(){
        return publishMapper.selectMaxPublishId();
    }
}
