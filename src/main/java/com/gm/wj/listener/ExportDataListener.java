package com.gm.wj.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.fastjson.JSON;
import com.gm.wj.dao.ExcelDataDao;
import com.gm.wj.entity.Book;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * author：yunshiyu
 * Date: 2023/7/3120:31
 **/
@Slf4j
public class ExportDataListener implements ReadListener<Book> {
    /**
     * 每批次缓存的记录数
     */
    private static final int BATCH_COUNT = 100;

    /**
     * 缓存的数据
     */
    private List<Book> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

    private ExcelDataDao excelDataDao;

    public ExportDataListener() {
        excelDataDao = new ExcelDataDao();
    }

    public ExportDataListener(ExcelDataDao excelDataDao) {
        this.excelDataDao = excelDataDao;
    }

    @Override
    public void invoke(Book data, AnalysisContext analysisContext) {
        log.info("解析到一条数据:{}", JSON.toJSONString(data));
        cachedDataList.add(data);
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (cachedDataList.size() >= BATCH_COUNT) {
            saveData();
            // 存储完成清理 list
            cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        }

    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        saveData();
        log.info("所有数据解析完成！");

    }

    private void saveData() {
        log.info("{}条数据，开始存储数据库！", cachedDataList.size());
        excelDataDao.saveAll(cachedDataList);
        log.info("存储数据库成功！");
    }
}
