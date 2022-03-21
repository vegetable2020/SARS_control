package com.unionman.SARS_control.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.unionman.SARS_control.domain.dto.UserInfoDTO;
import com.unionman.SARS_control.domain.dto.UserInfoFilterDTO;
import com.unionman.SARS_control.domain.entity.UserInfo;
import com.unionman.SARS_control.domain.vo.UserInfoVO;

import java.util.List;

/**
 * @Description:
 * @author: jinhao.xu
 * @date 2022/3/16 14:17
 */
public interface UserInfoService extends IService<UserInfo> {

    /**
     * 新增或更新登记人员信息
     * @param userInfoDTO
     * @param token
     * @return
     */
    Boolean saveOrUpdateUserInfo(UserInfoDTO userInfoDTO,String token);

    /**
     * 根据token查询登记信息
     * @param token
     * @return
     */
    UserInfoVO queryUserInfoByToken(String token);

    /**
     * 根据条件查找登记信息
     *
     * @param userInfoFilterDTO
     * @return
     */
    IPage<UserInfoVO> queryUserInfoByCondition(UserInfoFilterDTO userInfoFilterDTO);

    /**
     * 查询所有登记信息
     * @return
     */
    List<UserInfoVO> queryUserInfoList();
}
