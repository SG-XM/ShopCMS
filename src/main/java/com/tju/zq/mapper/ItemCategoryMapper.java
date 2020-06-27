package com.tju.zq.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 *  Created by zq on 2020/6/1.
 */
@Mapper
public interface ItemCategoryMapper {

    ItemCategory findById(ItemCategory itemCategory);

    List<ItemCategory> list(ItemCategory itemCategory);

    List<ItemCategory> list1();

    int count(ItemCategory itemCategory);

    int insert(ItemCategory itemCategory);

    int update(ItemCategory itemCategory);

    void delete(ItemCategory itemCategory);

    int updateStatus(ItemCategory itemCategory);

}
