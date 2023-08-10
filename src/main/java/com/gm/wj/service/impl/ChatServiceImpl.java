package com.gm.wj.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.gm.wj.dao.BookDAO;
import com.gm.wj.dao.CategoryDAO;
import com.gm.wj.entity.Book;
import com.gm.wj.entity.Category;
import com.gm.wj.entity.Chat;
import com.gm.wj.exception.ExcelException;
import com.gm.wj.service.ChatService;
import com.gm.wj.util.ExcelUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.gm.wj.util.ExcelUtil.getPath;

/**
 * author：yunshiyu
 * Date: 2023/7/3121:14
 **/
@Service
@Slf4j
public class ChatServiceImpl implements ChatService {
    @Autowired
    private BookDAO bookDAO;
    @Autowired
    private CategoryDAO categoryDAO;

    // 存储类型配置表信息
    private List<Category> categorys = null;

    @Override
    public void saveAll(List<Book> books) {
        bookDAO.saveAll(books);
    }

    @Override
    public boolean download(HttpServletResponse response) throws ExcelException {
        // 1. 从数据库中查询出所有需要导出的数据
        List<Book> bookList = bookDAO.findAll();
        List<Chat> chatList = new ArrayList<>();
        for (Book book : bookList) {
            Chat chat = Chat.builder()
                    .id(book.getId())
                    .title(book.getTitle())
                    .author(book.getAuthor())
                    .cover(book.getCover())
                    .date(book.getDate())
                    .press(book.getPress())
                    .abs(book.getAbs())
                    .category(book.getCategory().getName()).build();

            chatList.add(chat);
        }
        log.info("==download==select count={}", chatList.size());

        // 2. 拼接导出文件的路径
        String fileName = "chat_" + System.currentTimeMillis(); // 不带后缀
        log.info("==download==filename={}", fileName);

        // 3. 导出数据，生产excel文件
        return ExcelUtil.writeExcel(response, chatList, fileName,"sheet1", Chat.class);
    }

    @Override
    public boolean downloadTemplate(HttpServletResponse response) throws ExcelException {
        String fileName = "ImportTemplate";
        String sheetName = "ImportTemplate";
        List<Chat> userList = new ArrayList<>();

        return ExcelUtil.writeExcel(response, userList, fileName, sheetName, Chat.class);
    }

    @Override
    public boolean importData(MultipartFile file) throws ExcelException {
        List<Chat> chatExcelList = null;
        // 1.excel同步读取数据
        try {
            chatExcelList = EasyExcel.read(new BufferedInputStream(file.getInputStream()))
                    .head(Chat.class)
                    .sheet()
                    .doReadSync();
        } catch (Exception e) {
            throw new ExcelException("读取excel数据时出现了错误", e);
        }

        // 2.检查要导入的数据
        if (chatExcelList.size() <= 0) {
            throw new ExcelException("导入数据为空");
        }

        if (chatExcelList.size() > 1000) {
            throw new ExcelException("超过最多处理条数");
        }
        // 3.将 chatExcelList 转成 bookList
        log.info("====将 chatExcelList 转成 bookList=={}", chatExcelList.size());
        List<Book> bookList = chatExcelList2BookList(chatExcelList);
        // 4.入库操作
        List<Book> result = bookDAO.saveAll(bookList);
        log.info("====同步导入数据集合=={}", result.size());

        return true;
    }

    @Override
    public boolean importAsyData(MultipartFile file) throws ExcelException {
        ChatService chatService = this;
        File newFile = new File(getPath() + "chats/");

        try {
            file.transferTo(newFile);
        } catch (IOException e) {
            throw new ExcelException("数据导入转换文件失败", e);
        }

        EasyExcel.read(newFile, Chat.class, new ReadListener<Chat>() {
            ChatService chatServiceTemp = chatService;
            /**
             * 单次缓存的数据量
             */
            private static final int BATCH_COUNT = 100;
            /**
             *临时存储
             */
            List<Book> cachedBookList = new ArrayList<>(BATCH_COUNT);



            @Override
            public void invoke(Chat chat, AnalysisContext analysisContext) {
                cachedBookList.add(chatExcel2Book(chat));

                if (cachedBookList.size() >= BATCH_COUNT) {
                    saveData();
                    // 存储完成清理 list
                    cachedBookList = new ArrayList<>(BATCH_COUNT);
                }
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext analysisContext) {
                saveData();
            }

            private void saveData() {
                chatServiceTemp.saveAll(cachedBookList);
            }

        }).sheet().doRead();

        return true;
    }

    private List<Book> chatExcelList2BookList(List<Chat> chatExcelList) {
        List<Book> bookList = new ArrayList<>();
        for (Chat chat : chatExcelList) {
            bookList.add(chatExcel2Book(chat));
        }
        return bookList;
    }

    private Book chatExcel2Book(Chat chat) {
        return Book.builder()
                //.id(chat.getId())
                .title(chat.getTitle())
                .author(chat.getAuthor())
                .cover(chat.getCover())
                .date(chat.getDate())
                .press(chat.getPress())
                .abs(chat.getAbs())
                .category(getCategoryId(chat.getCategory())).build();
    }

    private Category getCategoryId(String name) {
        Category category = null;

        if (null == categorys) {
            categorys = categoryDAO.findAll();
            log.info("===查询categorys了==");
        }

        for (Category c : categorys) {
            if (null != name && name.equals(c.getName())) {
                category = c;
                break;
            }
        }

        return category;
    }

}
