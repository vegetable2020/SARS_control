package com.unionman.SARS_control.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.unionman.SARS_control.domain.dto.FromAddressDTO;
import com.unionman.SARS_control.domain.dto.UserInfoDTO;
import com.unionman.SARS_control.domain.entity.FromAddress;
import com.unionman.SARS_control.domain.entity.UserInfo;
import com.unionman.SARS_control.domain.vo.FromAddressVO;
import com.unionman.SARS_control.domain.vo.UserInfoVO;
import com.unionman.SARS_control.mapper.UserInfoMapper;
import com.unionman.SARS_control.service.FromAddressService;
import com.unionman.SARS_control.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Boolean saveOrUpdateUserInfo(UserInfoDTO userInfoDTO, String token) {
        UserInfo userInfo=new UserInfo();
        BeanUtil.copyProperties(userInfoDTO,userInfo );
        //处理登记人员信息
        userInfo.setToken(token);
        if (ObjectUtils.isNotNull(userInfo.getUserId())){
            userInfo.setUpdateTime(LocalDateTime.now());
        }else {
            userInfo.setCreateTime(LocalDateTime.now());
        }
        UserInfo userInfoTemp=this.getOne(Wrappers.<UserInfo>lambdaQuery()
                .eq(UserInfo::getToken, token).eq(UserInfo::getIsDrop, 0));
        if (ObjectUtils.isNotNull(userInfoTemp)){
            userInfo.setUserId(userInfoTemp.getUserId());
        }
        this.saveOrUpdate(userInfo,Wrappers.<UserInfo>lambdaUpdate().eq(UserInfo::getUserId, userInfo.getUserId()));

        //将该人员有关来源地信息暂时删除
        fromAddressService.update( Wrappers.<FromAddress>lambdaUpdate().set(FromAddress::getIsDrop,1)
                .eq(FromAddress::getUserId, userInfo.getUserId()).ne(FromAddress::getIsDrop, 1));
        //处理来惠地址信息
        List<FromAddress> fromAddresses=new ArrayList<>();
        for (FromAddressDTO fromAddressDTO:userInfoDTO.getFromAddressDTOList()){
            FromAddress fromAddress=new FromAddress();
            BeanUtil.copyProperties(fromAddressDTO, fromAddress);
            fromAddress.setUserId(userInfo.getUserId());
            if (ObjectUtils.isNotNull(fromAddress.getAddressId())){
                fromAddress.setUpdateTime(LocalDateTime.now());
            }else {
                fromAddress.setCreateTime(LocalDateTime.now());
            }
            if (ObjectUtils.isNull(fromAddress.getIsDrop())){
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
        if (ObjectUtils.isNull(userInfo)){
            throw new RuntimeException("查询登记信息失败，未进行登记");
        }
        return getUserInfoVO(userInfo);
    }

    @Override
    public List<UserInfoVO> queryUserInfoList() {
        List<UserInfo> userInfoList=this.list(Wrappers.<UserInfo>lambdaQuery().eq(UserInfo::getIsDrop, 0));
        List<UserInfoVO> userInfoVOList=new ArrayList<>();
        for (UserInfo userInfo: userInfoList){
            UserInfoVO userInfoVO=getUserInfoVO(userInfo);
            userInfoVOList.add(userInfoVO);
        }
        return userInfoVOList;
    }

    protected UserInfoVO getUserInfoVO(UserInfo userInfo){
        UserInfoVO userInfoVO=new UserInfoVO();
        BeanUtil.copyProperties(userInfo, userInfoVO);
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
