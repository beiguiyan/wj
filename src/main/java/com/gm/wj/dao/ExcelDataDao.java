package com.gm.wj.dao;

import com.gm.wj.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * author：yunshiyu
 * Date: 2023/7/3120:35
 **/
@Component
public class ExcelDataDao {
    @Autowired
    private BookDAO bookDao;

    public void saveAll(List<Book> list) {
        bookDao.saveAll(list);
    }
}
