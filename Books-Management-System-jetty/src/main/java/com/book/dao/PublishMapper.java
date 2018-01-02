package com.book.dao;

import com.book.domain.Publish;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by 10394 on 2017/12/26.
 */
@Component
public interface PublishMapper {
    List<Publish> selectAllPublish();
    Publish selectPublishById(int id);
    int insertPublish(Publish publish);
    int updatePublish(Publish publish);
    int deletePublish(int id);
    int selectMaxPublishId();
}
