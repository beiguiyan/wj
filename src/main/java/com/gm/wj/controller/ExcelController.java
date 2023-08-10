package com.gm.wj.controller;

import com.gm.wj.exception.ExcelException;
import com.gm.wj.service.ChatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * author：yunshiyu
 * Date: 2023/7/3121:09
 **/
@RestController
@Slf4j
public class ExcelController {
    @Autowired
    private ChatService chatService;

    /**
     * 导出列表中的文件
     * @return
     */
    @GetMapping("/api/download")
    public boolean download(HttpServletResponse resource) {
        try {
            return chatService.download(resource);
        } catch (ExcelException e) {
            return false;
        }
    }

    @GetMapping("/api/downloadTemple")
    public boolean downloadTemple(HttpServletResponse resource) {
        try {
            return chatService.downloadTemplate(resource);
        } catch (ExcelException e) {
            return false;
        }
    }

    @PostMapping("/api/importData")
    public boolean importData(MultipartFile file) {
        try {
            return chatService.importData(file);
        } catch (ExcelException e) {
            e.printStackTrace();
            return false;
        }
    }

    @PostMapping("/api/importAsyData")
    public boolean importAsyData(MultipartFile file) {
        try {
            log.info("===importAsyData==");
            return chatService.importAsyData(file);
        } catch (ExcelException e) {
            e.printStackTrace();
            return false;
        }
    }

}
