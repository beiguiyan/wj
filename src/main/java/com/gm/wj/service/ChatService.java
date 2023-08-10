package com.gm.wj.service;

import com.gm.wj.entity.Book;
import com.gm.wj.exception.ExcelException;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * author：yunshiyu
 * Date: 2023/7/3121:12
 **/
public interface ChatService {

    /**
     * 保存Book对象集合
     *
     * @param books
     */
    void saveAll(List<Book> books);

    /**
     * 导出数据
     *
     * @param response
     * @return 导出文件路径
     */
    boolean download(HttpServletResponse response) throws ExcelException;

    /**
     * 下载导入数据的模版
     *
     * @param response
     * @return 导出文件路径
     */
    boolean downloadTemplate(HttpServletResponse response) throws ExcelException;

    /**
     * 导入数据(同步导入)
     *
     * @param file
     * @return 插入的记录数
     * @throws ExcelException
     */
    boolean importData(MultipartFile file) throws ExcelException;

    /**
     * 导入数据(异步导入)
     *
     * @param file
     * @return
     * @throws ExcelException
     */
    boolean importAsyData(MultipartFile file) throws ExcelException;
}
