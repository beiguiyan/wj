package com.gm.wj.controller;

import com.gm.wj.entity.JotterArticle;
import com.gm.wj.result.Result;
import com.gm.wj.result.ResultFactory;
import com.gm.wj.service.JotterArticleService;
import com.gm.wj.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Shop controller.
 *
 * @author yunshiyu
 * @date 2023/8
 */
@RestController
public class ShopController {
    @Autowired
    ShopService shopService;

    @GetMapping("/api/shop/list")
    public Result listShop() {
        return ResultFactory.buildSuccessResult(shopService.list());
    }

    @PostMapping("/api/shop/{id}")
    public Result getOneArticle(@PathVariable("id") int id) {
        return ResultFactory.buildSuccessResult(shopService.get(id));
    }

}
