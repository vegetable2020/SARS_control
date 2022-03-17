package com.unionman.SARS_control.controller;

import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.unionman.SARS_control.SarsControlApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Description:
 * @author: jinhao.xu
 * @date 2022/3/17 15:10
 */
@SpringBootTest(classes = SarsControlApplication.class)
@RunWith(SpringRunner.class)
@Slf4j
public class TestController {
    @Autowired
    private FastFileStorageClient storageClient;

    @Test
    public void deleteFile() {
        storageClient.deleteFile("group1/M00/00/02/wKiIE2Iy35KAaQb9AADmiodwX7c62.jfif");
    }
}
