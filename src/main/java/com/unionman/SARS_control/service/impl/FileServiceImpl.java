package com.unionman.SARS_control.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.domain.proto.storage.DownloadCallback;
import com.github.tobato.fastdfs.domain.proto.storage.DownloadFileStream;
import com.github.tobato.fastdfs.service.AppendFileStorageClient;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.unionman.SARS_control.domain.entity.FileRecord;
import com.unionman.SARS_control.mapper.FileRecordMapper;
import com.unionman.SARS_control.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


/**
 * @author jinhao.xu
 */
@Slf4j
@Service
public class FileServiceImpl extends ServiceImpl<FileRecordMapper, FileRecord> implements FileService {

    @Autowired
    FastFileStorageClient storageClient;

    @Autowired
    AppendFileStorageClient appendStorageClient;


    /**
     * 上传单个文件
     * @param file
     * @return
     * @throws IOException
     */
    @Override
    public String upload(MultipartFile file) throws IOException {

        StorePath storePath = storageClient.uploadFile(file.getInputStream(),file.getSize(),
                    FilenameUtils.getExtension(file.getOriginalFilename()),null);
        FileRecord fileRecord=new FileRecord();
        fileRecord.setFileName(file.getOriginalFilename());
        fileRecord.setFilePath(storePath.getFullPath());
        fileRecord.setFileSize(file.getSize());
        this.save(fileRecord);
        return  storePath.getFullPath();
    }


    /**
     * 上传多个文件
     * @param files
     * @return
     * @throws IOException
     */
    @Override
    public List<String> multiUpload(MultipartFile[] files) throws IOException {

        ArrayList<String> pathList = new ArrayList<>();

        for (MultipartFile file : files) {
            StorePath storePath = storageClient.uploadFile(file.getInputStream(),file.getSize(),
                    FilenameUtils.getExtension(file.getOriginalFilename()),null);
            pathList.add(storePath.getFullPath());
        }
        return pathList;
    }



    @Override
    public void download(String filePath, String fileName, HttpServletResponse response) throws IOException {

        StorePath storePath = StorePath.parseFromUrl(filePath);

        fileName = fileName + filePath.substring(filePath.lastIndexOf("."), filePath.length());

        //配置文件下载
        response.setHeader("content-type", "application/octet-stream");
        response.setContentType("APPLICATION/OCTET-STREAM");

        response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(fileName,"UTF-8"));

        DownloadFileStream downloadFileStream = new DownloadFileStream(response.getOutputStream());

        storageClient.downloadFile(storePath.getGroup(), storePath.getPath(),downloadFileStream);

        response.getOutputStream().flush();

    }


    @Override
    public byte[] downloadByte(String filePath){

        StorePath storePath = StorePath.parseFromUrl(filePath);

        byte[] r = storageClient.downloadFile(storePath.getGroup(), storePath.getPath(), new DownloadCallback<byte[]>() {
            @Override
            public byte[] recv(InputStream ins) throws IOException {
                byte[] reulst = IOUtils.toByteArray(ins);
                log.info("reulst.length",reulst.length);
                return reulst;
            }
        });
        return r;
    }



}
