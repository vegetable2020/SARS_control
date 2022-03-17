package com.unionman.SARS_control.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.unionman.SARS_control.domain.entity.FileRecord;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


/**
 * @author jinhao.xu
 */
public interface FileService extends IService<FileRecord> {


    /**
     * 上传单个文件
     * @param file
     * @return
     * @throws IOException
     */
    String upload(MultipartFile file) throws IOException;

    /**
     * 上传多个文件
     * @param files
     * @return
     * @throws IOException
     */
    List<String> multiUpload(MultipartFile[] files) throws IOException;

    /**
     *  文件下载
     * @param filePath 文件完整路径
     * @param fileName 下载后的文件名
     */
    void download(String filePath,String fileName, HttpServletResponse response) throws IOException;


    byte[] downloadByte(String filePath);




}
