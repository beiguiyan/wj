package com.gm.wj.exception;

/**
 * author：yunshiyu
 * Date: 2023/8/111:43
 **/
public class ExcelException extends RuntimeException {
    public ExcelException(String message) {
        super(message);
    }

    public ExcelException(String message, Throwable cause) {
        super(message, cause);
    }
}
