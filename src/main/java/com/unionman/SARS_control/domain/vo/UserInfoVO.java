package com.unionman.SARS_control.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.unionman.SARS_control.domain.dto.FromAddressDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Description:
 * @author: jinhao.xu
 * @date 2022/3/16 10:33
 */
@Data
@Accessors(chain = true)
@ApiModel("登记人员信息 vo")
public class UserInfoVO implements Serializable {
    @ApiModelProperty(value = "user_id")
    private Integer userId;

    @ApiModelProperty(value = "姓名" )
    private String username;

    @ApiModelProperty(value = "所属中心" )
    private String center;

    @ApiModelProperty(value = "联系电话" )
    private String phone;

    @ApiModelProperty(value = "在惠住址" )
    private String address;

    @ApiModelProperty(value = "离惠日期" )
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate leaveDate;

    @ApiModelProperty(value = "来惠日期" )
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate comeDate;

    @ApiModelProperty(value = "何地来惠信息" )
    private List<FromAddressVO> fromAddressVOList;

    @ApiModelProperty(value = "来惠方式" )
    private String comeVehicle;

    @ApiModelProperty(value = "车次号，选择飞机、火车、动车、高铁必填")
    private String comeVehicleNum;

    @ApiModelProperty(value = "粤康码")
    private String yueKangCode;

    @ApiModelProperty(value = "行程码")
    private String travelCode;

    @ApiModelProperty(value = "最新核酸报告")
    private String latestNucleicAcidReport;

    @ApiModelProperty(value = "是否删除，1：删除；0：未删除")
    private Integer isDrop;
}
