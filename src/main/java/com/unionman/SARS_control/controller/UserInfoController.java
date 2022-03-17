package com.unionman.SARS_control.controller;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.unionman.SARS_control.constant.Constants;
import com.unionman.SARS_control.constant.ResultEnum;
import com.unionman.SARS_control.domain.dto.UserInfoDTO;
import com.unionman.SARS_control.domain.entity.UserInfoExcel;
import com.unionman.SARS_control.domain.vo.FromAddressVO;
import com.unionman.SARS_control.domain.vo.ResultVO;
import com.unionman.SARS_control.domain.vo.UserInfoVO;
import com.unionman.SARS_control.service.UserInfoService;
import com.unionman.SARS_control.utils.IdUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @author: jinhao.xu
 * @date 2022/3/16 14:15
 */
@RequestMapping("/userInfo")
@Slf4j
@RestController
@Api(value = "人员信息登记", tags = "人员信息登记")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @Value("${fdfs.visit.url}")
    private String fvu = "";
    @Value("${fdfs.visit.port}")
    private String fport = "";

    @ResponseBody
    @ApiOperation(value = "信息登记，返回user-token放入会话")
    @PostMapping("/saveOrUpdate")
    public ResultVO<String> saveOrUpdateUserInfo(@Validated @RequestBody UserInfoDTO userInfoDTO, HttpServletRequest request) {

        try {
            HttpSession session = request.getSession();
            String token = ObjectUtils.isNotNull(session.getAttribute(Constants.TOKEN_FLAG)) ? (String) session.getAttribute(Constants.TOKEN_FLAG) : "";
            if (StringUtils.isEmpty(token)) {
                token = IdUtils.fastUUID();
            }
            userInfoService.saveOrUpdateUserInfo(userInfoDTO, token);
            session.setAttribute(Constants.TOKEN_FLAG, token);
            return ResultVO.success(token);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("信息登记失败：" + e.getMessage());
            return ResultVO.error(ResultEnum.ERROR);
        }
    }

    @ResponseBody
    @ApiOperation(value = "登记信息查询，通过会话中user-token")
    @GetMapping("/queryByToken")
    public ResultVO<UserInfoVO> queryByToken(HttpServletRequest request) {

        try {
            HttpSession session = request.getSession();
            String token= (String) session.getAttribute(Constants.TOKEN_FLAG);
            if (StringUtils.isEmpty(token)) {
                return ResultVO.error(ResultEnum.AUTHENTICATION_FAILED);
            }
            UserInfoVO userInfoVO = userInfoService.queryUserInfoByToken(token);
            return ResultVO.success(userInfoVO);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return ResultVO.error(-1, e.getMessage());
        }
    }

    /**
     * 导出excel表格
     *
     * @param response
     */
    @GetMapping("/exportToExcel")
    @ApiOperation("导出excel表格")
    public void exportToExcel(HttpServletResponse response) {
        ServletOutputStream out = null;
        try {
            //文件名
            String filename = "返惠员工信息" + ".xlsx";

            List<UserInfoExcel> userInfoExcels = new ArrayList<>();
            List<UserInfoVO> userInfoVOList = userInfoService.queryUserInfoList();
            for (UserInfoVO userInfoVO : userInfoVOList) {
                UserInfoExcel userInfoExcel = new UserInfoExcel();
                BeanUtil.copyProperties(userInfoVO, userInfoExcel);
                userInfoExcel.setLeaveDate(userInfoVO.getLeaveDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                userInfoExcel.setComeDate(userInfoVO.getComeDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                userInfoExcel.setYueKangCode(fvu + ":" + fport + "/" + userInfoExcel.getYueKangCode());
                userInfoExcel.setTravelCode(fvu + ":" + fport + "/" + userInfoExcel.getTravelCode());
                userInfoExcel.setLatestNucleicAcidReport(fvu + ":" + fport + "/" + userInfoExcel.getLatestNucleicAcidReport());
                StringBuilder fromAddressStr = new StringBuilder();
                List<FromAddressVO> fromAddressVOList=userInfoVO.getFromAddressVOList();
                for (int i=1;i<=fromAddressVOList.size();i++) {
                    FromAddressVO fromAddressVO = fromAddressVOList.get(i-1);
                    fromAddressStr.append(i+"."+fromAddressVO.getProvince() + fromAddressVO.getCity()
                            + fromAddressVO.getArea() + fromAddressVO.getAddressDtl() + "；" + (char) 10 );
                }
                userInfoExcel.setFromAddress(fromAddressStr.toString());
                userInfoExcels.add(userInfoExcel);
            }


            //导出到excel表中
            out = response.getOutputStream();
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
//            filename = URLEncoder.encode(filename, "UTF-8");
            filename =URLEncoder.encode(filename, "UTF-8").replaceAll("\\+", "%20");;
            response.setHeader("Content-Disposition", "attachment; filename=" + filename + "");
            EasyExcel.write(out, UserInfoExcel.class).sheet("模板").doWrite(userInfoExcels);
            out.flush();
        } catch (Exception ex) {
            log.error(ex.getMessage());
            ex.printStackTrace();
        } finally {
            // 千万别忘记finish 会帮忙关闭流
            if (out != null){
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
