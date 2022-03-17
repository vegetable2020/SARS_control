package com.unionman.SARS_control.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.unionman.SARS_control.domain.entity.FromAddress;
import com.unionman.SARS_control.domain.entity.UserInfo;
import com.unionman.SARS_control.mapper.FromAddressMapper;
import com.unionman.SARS_control.mapper.UserInfoMapper;
import com.unionman.SARS_control.service.FromAddressService;
import com.unionman.SARS_control.service.UserInfoService;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @author: jinhao.xu
 * @date 2022/3/16 14:18
 */
@Service
public class FromAddressServiceImpl extends ServiceImpl<FromAddressMapper, FromAddress> implements FromAddressService {
}
