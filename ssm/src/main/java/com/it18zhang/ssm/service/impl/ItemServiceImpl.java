package com.it18zhang.ssm.service.impl;

import com.it18zhang.ssm.dao.BaseDao;
import com.it18zhang.ssm.domain.Item;
import com.it18zhang.ssm.service.ItemService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 *
 */
@Service("itemService")
public class ItemServiceImpl extends BaseServiceImpl<Item> implements ItemService{

    @Resource(name="itemDao")
    public void setDao(BaseDao<Item> dao) {
        super.setDao(dao);
    }
}