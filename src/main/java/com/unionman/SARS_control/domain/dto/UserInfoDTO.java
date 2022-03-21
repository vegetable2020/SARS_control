package com.unionman.SARS_control.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.unionman.SARS_control.annotation.IsPhone;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
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
@ApiModel("登记人员信息 dto")
public class UserInfoDTO implements Serializable {
    @ApiModelProperty(value = "user_id")
    private Long userId;

    @ApiModelProperty(value = "姓名" , required = true)
    @NotBlank(message = "不能为空")
    private String username;

    @ApiModelProperty(value = "所属中心" , required = true)
    @NotBlank(message = "不能为空")
    private String center;

    @ApiModelProperty(value = "联系电话" , required = true)
    @NotBlank(message = "不能为空")
    @IsPhone
    private String phone;

    @ApiModelProperty(value = "在惠住址" , required = true)
    @NotBlank(message = "不能为空")
    private String address;

    @ApiModelProperty(value = "来惠日期，yyyy-MM-dd" , required = true )
    @NotNull(message = "不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate comeDate;

    @ApiModelProperty(value = "何地来惠信息" , required = true)
    @NotNull(message = "至少有一条")
    @Size(min = 1)
    private List<FromAddressDTO> fromAddressDTOList;

    @ApiModelProperty(value = "来惠方式" , required = true)
    @NotBlank(message = "不能为空")
    private String comeVehicle;

    @ApiModelProperty(value = "车次号，选择飞机、火车、动车、高铁必填")
    private String comeVehicleNum;

    @ApiModelProperty(value = "粤康码", required = true)
    @NotBlank(message = "不能为空")
    private String yueKangCode;

    @ApiModelProperty(value = "行程码", required = true)
    @NotBlank(message = "不能为空")
    private String travelCode;

    @ApiModelProperty(value = "最新核酸报告", required = true)
    @NotBlank(message = "不能为空")
    private String latestNucleicAcidReport;

    @ApiModelProperty(value = "是否删除，1：删除；0：未删除")
    private Integer isDrop;
}
