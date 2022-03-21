package com.unionman.SARS_control.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * @Description:
 * @author: jinhao.xu
 * @date 2022/3/16 10:33
 */
@Data
@Accessors(chain = true)
@ApiModel("登记人员信息查询 dto")
public class UserInfoFilterDTO extends PageDTO implements Serializable {

    @ApiModelProperty(value = "姓名" )
    private String username;

    @ApiModelProperty(value = "所属中心" )
    private String center;

    @ApiModelProperty(value = "联系电话" )
    private String phone;

    @ApiModelProperty(value = "在惠住址" )
    private String address;

    @ApiModelProperty(value = "来惠日期s，yyyy-MM-dd"  )
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate comeDateStart;

    @ApiModelProperty(value = "来惠日期e，yyyy-MM-dd"  )
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate comeDateEnd;

    @ApiModelProperty(value = "何地来惠信息" )
    private String fromAddressInfo;

    @ApiModelProperty(value = "来惠方式" )
    private String comeVehicle;

    @ApiModelProperty(value = "车次号")
    private String comeVehicleNum;

}
