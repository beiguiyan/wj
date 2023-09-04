package com.gm.wj.service;

import com.gm.wj.dao.ShopDAO;
import com.gm.wj.entity.Shop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yunshiyu
 * @date 2023/8
 */
@Service
public class ShopService {
    @Autowired
    ShopDAO shopDAO;

    public List<Shop> list() {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        return shopDAO.findAll(sort);
    }

    public Shop get(int id) {
        return shopDAO.findById(id).orElse(null);
    }
}
