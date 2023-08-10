package com.gm.wj.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * author：yunshiyu
 * Date: 2023/7/3120:55
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class Chat {
    @ExcelProperty(value = "序号", index = 0)
    @ColumnWidth(10)
    private int id;

    /**
     * Title of the book.
     */
    @ExcelProperty(value = "书籍名称", index = 1)
    @ColumnWidth(20)
    private String title;

    /**
     * Author name.
     */
    @ExcelProperty(value = "书籍作者", index = 2)
    @ColumnWidth(20)
    private String author;

    /**
     * Publication date.
     */
    @ExcelProperty(value = "出版日期", index = 3)
    @ColumnWidth(20)
    private String date;

    /**
     * Press.
     */
    @ExcelProperty(value = "出版社名称", index = 4)
    @ColumnWidth(20)
    private String press;

    /**
     * Abstract of the book.
     */
    @ExcelProperty(value = "书籍简介", index = 5)
    @ColumnWidth(30)
    private String abs;

    /**
     * The url of the book's cover.
     */
    @ExcelProperty(value = "书籍封面", index = 6)
    @ColumnWidth(50)
    private String cover;

    /**
     * Category id.
     */
    @ExcelProperty(value = "分类", index = 7)
    @ColumnWidth(20)
    private String category;
}
