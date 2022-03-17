package com.unionman.SARS_control.controller;

import com.unionman.SARS_control.constant.ResultEnum;
import com.unionman.SARS_control.domain.vo.ResultVO;
import com.unionman.SARS_control.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;


@RequestMapping("/file")
@Slf4j
@RestController
@Api(value = "文件上传和下载",tags = "文件上传")
public class FileController {

    @Autowired
    FileService fileService;



    @ResponseBody
    @ApiOperation(value = "单文件上传" )
    @PostMapping("/upload")
    public ResultVO<String> upload(@RequestParam("file") MultipartFile file){

        if (file.isEmpty()) {
           return ResultVO.error(ResultEnum.FILE_CAN_NOT_BE_NULL);
        }

        try {
            String url = fileService.upload(file);
            if (null != url) {
                log.info("单文件上传成功");
                return ResultVO.success(url);
            } else {
                log.error("单文件上传失败");
                return ResultVO.error(ResultEnum.ERROR);
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error("单文件上传失败：" + e.toString());
            return ResultVO.error(-1,e.getMessage());
        }

    }

    @ApiOperation("多文件上传")
    @PostMapping("/uploads")
    public ResultVO<List<String>> multiUpload(@RequestParam("files") MultipartFile[] files){

        if (files[0]==null) {
            return ResultVO.error(ResultEnum.FILE_CAN_NOT_BE_NULL);
        }

        try {
            List<String> list = fileService.multiUpload(files);
            if (!CollectionUtils.isEmpty(list)) {
                log.info("多文件上传成功");
                return  ResultVO.success(list);
            }else {
                log.error("多文件上传失败");
                return ResultVO.error(ResultEnum.ERROR);
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error("多文件上传失败：" + e.toString());
            return ResultVO.error(ResultEnum.ERROR);
        }
    }


    @GetMapping("/public/download")
    @ApiOperation("文件下载(以附录形式)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "filePath", dataType = "String", value = "文件路径"),
            @ApiImplicitParam(name = "fileName", dataType = "String", value = "文件下载名称")
    })
    public ResultVO download(@RequestParam("filePath") String filePath, @RequestParam("fileName") String fileName, HttpServletResponse response) {

        try {
            fileService.download(filePath,fileName,response);
            log.info("单文件下载成功");
            return null;
        }catch (Exception e){
            e.printStackTrace();
            log.error("单文件下载失败：" + e.toString());
            return ResultVO.error(ResultEnum.SYSTEM_ERROR);
        }
    }

    @GetMapping("/public/downloadByte")
    @ApiOperation("文件下载(返回字节数组)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "filePath", dataType = "String", value = "文件路径"),
            @ApiImplicitParam(name = "fileName", dataType = "String", value = "文件下载名称")
    })
    public ResultVO<byte[]> downloadByte(@RequestParam("filePath") String filePath, @RequestParam("fileName") String fileName) {

        try {
            byte [] bytes=fileService.downloadByte(filePath);
            log.info("单文件下载成功");
            return ResultVO.success(bytes);
        }catch (Exception e){
            e.printStackTrace();
            log.error("单文件下载失败：" + e.toString());
            return ResultVO.error(ResultEnum.SYSTEM_ERROR);
        }

    }

}
