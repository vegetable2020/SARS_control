package com.unionman.SARS_control.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.unionman.SARS_control.domain.dto.FromAddressDTO;
import com.unionman.SARS_control.domain.dto.UserInfoDTO;
import com.unionman.SARS_control.domain.dto.UserInfoFilterDTO;
import com.unionman.SARS_control.domain.entity.FileRecord;
import com.unionman.SARS_control.domain.entity.FromAddress;
import com.unionman.SARS_control.domain.entity.UserInfo;
import com.unionman.SARS_control.domain.vo.FromAddressVO;
import com.unionman.SARS_control.domain.vo.UserInfoVO;
import com.unionman.SARS_control.mapper.FileRecordMapper;
import com.unionman.SARS_control.mapper.FromAddressMapper;
import com.unionman.SARS_control.mapper.UserInfoMapper;
import com.unionman.SARS_control.service.FileService;
import com.unionman.SARS_control.service.FromAddressService;
import com.unionman.SARS_control.service.UserInfoService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @author: jinhao.xu
 * @date 2022/3/16 14:18
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {
    @Autowired
    private FromAddressService fromAddressService;
    @Autowired
    private FromAddressMapper fromAddressMapper;
    @Autowired
    private FileService fileService;

    @Autowired
    private UserInfoMapper userInfoMapper;
    @Value("${fdfs.visit.url}")
    private String fvu = "";
    @Value("${fdfs.visit.port}")
    private String fport = "";

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Boolean saveOrUpdateUserInfo(UserInfoDTO userInfoDTO, String token) {
        UserInfo userInfo=new UserInfo();
        BeanUtil.copyProperties(userInfoDTO,userInfo );
        //处理登记人员信息
        userInfo.setToken(token);
        if (ObjectUtils.isNotEmpty(userInfo.getUserId())){
            userInfo.setUpdateTime(LocalDateTime.now());
        }else {
            userInfo.setCreateTime(LocalDateTime.now());
        }
        UserInfo userInfoTemp=this.getOne(Wrappers.<UserInfo>lambdaQuery()
                .eq(UserInfo::getToken, token).eq(UserInfo::getIsDrop, 0));
        if (ObjectUtils.isNotEmpty(userInfoTemp)){
            userInfo.setUserId(userInfoTemp.getUserId());
        }
        userInfo.setFilePath(fvu+":"+fport+"/");
        this.saveOrUpdate(userInfo,Wrappers.<UserInfo>lambdaUpdate().eq(UserInfo::getUserId, userInfo.getUserId()));

        fileService.update(Wrappers.<FileRecord>lambdaUpdate().set(FileRecord::getUserId,userInfo.getUserId()).eq(FileRecord::getIsDrop, 0));

        //将该人员有关来源地信息暂时删除
        fromAddressService.update( Wrappers.<FromAddress>lambdaUpdate().set(FromAddress::getIsDrop,1)
                .eq(FromAddress::getUserId, userInfo.getUserId()).ne(FromAddress::getIsDrop, 1));
        //处理来惠地址信息
        List<FromAddress> fromAddresses=new ArrayList<>();
        for (FromAddressDTO fromAddressDTO:userInfoDTO.getFromAddressDTOList()){
            FromAddress fromAddress=new FromAddress();
            BeanUtil.copyProperties(fromAddressDTO, fromAddress);
            fromAddress.setUserId(userInfo.getUserId());
            if (ObjectUtils.isNotEmpty(fromAddress.getAddressId())){
                fromAddress.setUpdateTime(LocalDateTime.now());
            }else {
                fromAddress.setCreateTime(LocalDateTime.now());
            }
            if (ObjectUtils.isEmpty(fromAddress.getIsDrop())){
                fromAddress.setIsDrop(0);
            }
            fromAddresses.add(fromAddress);
        }
        fromAddressService.saveOrUpdateBatch(fromAddresses);
        return true;
    }

    @Override
    public UserInfoVO queryUserInfoByToken(String token) {
        UserInfo userInfo=this.getOne(Wrappers.<UserInfo>lambdaQuery()
                .eq(UserInfo::getIsDrop, 0).eq(UserInfo::getToken, token));
        if (ObjectUtils.isEmpty(userInfo)){
            throw new RuntimeException("查询登记信息失败，未进行登记");
        }
        return getUserInfoVO(userInfo);
    }

    @Override
    public IPage<UserInfoVO> queryUserInfoByCondition(UserInfoFilterDTO userInfoFilterDTO) {
        String userIdConcat="";
        if (StringUtils.isNotBlank(userInfoFilterDTO.getFromAddressInfo())){
            userIdConcat=fromAddressMapper.getUserIdConcat(userInfoFilterDTO.getFromAddressInfo());
        }

        Page page=new Page<>(userInfoFilterDTO.getPageNum(), userInfoFilterDTO.getPageSize());
        page.setSearchCount(true);
        IPage<UserInfo> userInfoIPage=this.userInfoMapper.selectPage(page
                , Wrappers.<UserInfo>lambdaQuery().eq(UserInfo::getIsDrop, 0)
                        .like(StringUtils.isNotBlank(userInfoFilterDTO.getUsername()), UserInfo::getUsername, userInfoFilterDTO.getUsername())
                        .like(StringUtils.isNotBlank(userInfoFilterDTO.getCenter()), UserInfo::getCenter, userInfoFilterDTO.getCenter())
                        .like(StringUtils.isNotBlank(userInfoFilterDTO.getAddress()), UserInfo::getAddress, userInfoFilterDTO.getAddress())
                        .like(StringUtils.isNotBlank(userInfoFilterDTO.getPhone()), UserInfo::getPhone, userInfoFilterDTO.getPhone())
                        .like(StringUtils.isNotBlank(userInfoFilterDTO.getComeVehicle()), UserInfo::getComeVehicle, userInfoFilterDTO.getComeVehicle())
                        .like(StringUtils.isNotBlank(userInfoFilterDTO.getComeVehicleNum()), UserInfo::getComeVehicleNum, userInfoFilterDTO.getComeVehicleNum())
                        .like(StringUtils.isNotBlank(userInfoFilterDTO.getComeVehicle()), UserInfo::getComeVehicle, userInfoFilterDTO.getComeVehicle())
                        .le(ObjectUtils.isNotEmpty(userInfoFilterDTO.getComeDateEnd()), UserInfo::getComeDate, userInfoFilterDTO.getComeDateEnd())
                        .ge(ObjectUtils.isNotEmpty(userInfoFilterDTO.getComeDateStart()), UserInfo::getComeDate, userInfoFilterDTO.getComeDateStart())
                        .inSql(StringUtils.isNotBlank(userInfoFilterDTO.getFromAddressInfo()), UserInfo::getUserId, userIdConcat));

        List<UserInfoVO> userInfoVOList=new ArrayList<>();
        for (UserInfo userInfo:userInfoIPage.getRecords()){
            userInfoVOList.add(getUserInfoVO(userInfo));
        }
        IPage<UserInfoVO> userInfoVOIPage=new Page<>();
        BeanUtil.copyProperties(userInfoIPage, userInfoVOIPage);
        userInfoVOIPage.setTotal(userInfoIPage.getTotal());
        userInfoVOIPage.setRecords(userInfoVOList);
        return userInfoVOIPage;
    }

    @Override
    public List<UserInfoVO> queryUserInfoList() {
        List<UserInfo> userInfoList=this.list(Wrappers.<UserInfo>lambdaQuery().eq(UserInfo::getIsDrop, 0));
        List<UserInfoVO> userInfoVOList=new ArrayList<>();
        for (UserInfo userInfo: userInfoList){
            UserInfoVO userInfoVO=getUserInfoVO(userInfo);
            userInfoVO.setTravelCode(userInfo.getFilePath()+userInfo.getTravelCode());
            userInfoVO.setLatestNucleicAcidReport(userInfo.getFilePath()+userInfo.getLatestNucleicAcidReport());
            userInfoVO.setYueKangCode(userInfo.getFilePath()+userInfo.getYueKangCode());
            userInfoVOList.add(userInfoVO);
        }
        return userInfoVOList;
    }

    protected UserInfoVO getUserInfoVO(UserInfo userInfo){
        UserInfoVO userInfoVO=new UserInfoVO();
        BeanUtil.copyProperties(userInfo, userInfoVO);
        userInfoVO.setTravelCode(userInfo.getFilePath()+userInfo.getTravelCode());
        userInfoVO.setLatestNucleicAcidReport(userInfo.getFilePath()+userInfo.getLatestNucleicAcidReport());
        userInfoVO.setYueKangCode(userInfo.getFilePath()+userInfo.getYueKangCode());
        List<FromAddress> fromAddressList=fromAddressService.list(Wrappers.<FromAddress>lambdaQuery()
                .eq(FromAddress::getIsDrop,0).eq(FromAddress::getUserId, userInfo.getUserId()));
        List<FromAddressVO> fromAddressVOList =new ArrayList<>();
        for (FromAddress fromAddress:fromAddressList){
            FromAddressVO fromAddressVO=new FromAddressVO();
            BeanUtil.copyProperties(fromAddress, fromAddressVO);
            fromAddressVOList.add(fromAddressVO);
        }
        userInfoVO.setFromAddressVOList(fromAddressVOList);
        return userInfoVO;
    }

}
