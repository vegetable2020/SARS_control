package com.unionman.SARS_control.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.unionman.SARS_control.domain.entity.FromAddress;
import com.unionman.SARS_control.domain.entity.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @Description:
 * @author: jinhao.xu
 * @date 2022/3/16 14:19
 */
@Mapper
public interface FromAddressMapper extends BaseMapper<FromAddress> {

    /**
     * 查询
     * @param condition
     * @return
     */
    @Select(" select group_concat(user_id) from from_address where is_drop=0 " +
            " and (address_dtl like concat('%',#{condition},'%') or " +
            " province like concat('%',#{condition},'%') or " +
            " city like concat('%',#{condition},'%') or  " +
            " area like concat('%',#{condition},'%') or " +
            " concat(province,city,area,address_dtl) like concat('%',#{condition},'%') ) ")
    String getUserIdConcat(@Param("condition")String condition);
}
