package com.tju.zq.mapper;

import com.tju.zq.model.Item;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 *  Created by zq on 2020/6/1.
 */
@Mapper
public interface ItemMapper {

    Item findById(Item item);

    void delete(Item item);

    List<Item> list(Item item);

    List<Item> listS(Item item);

    int count(Item item);

    int insert(Item item);

    int update (Item item);


    List<Item> selectAll();
}
