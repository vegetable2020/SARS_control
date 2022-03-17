package com.unionman.SARS_control.domain.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Description:
 * @author: jinhao.xu
 * @date 2022/3/16 14:11
 */
@Data
@Accessors(chain = true)
@ApiModel("何地来惠信息 dto")
public class FromAddressDTO {
    @TableId(value = "address_id", type = IdType.AUTO)
    @ApiModelProperty(value = "addressId")
    private Integer addressId;

    @TableId(value = "user_id")
    @ApiModelProperty(value = "关联的信息id")
    private Integer userId;

    @TableId(value = "province_ode")
    @ApiModelProperty(value = "省code" , required = true)
    @NotNull(message = "不能为空")
    private Long provinceCode;

    @TableId(value = "province")
    @ApiModelProperty(value = "省" , required = true)
    @NotBlank(message = "不能为空")
    private String province;

    @TableId(value = "city_ode")
    @ApiModelProperty(value = "市code" , required = true)
    @NotNull(message = "不能为空")
    private Long cityCode;

    @TableId(value = "city")
    @ApiModelProperty(value = "市"  , required = true)
    @NotBlank(message = "不能为空")
    private String city;

    @TableId(value = "area_ode")
    @ApiModelProperty(value = "区code" , required = true)
    @NotNull(message = "不能为空")
    private Long areaCode;

    @TableId(value = "area")
    @ApiModelProperty(value = "区"  , required = true)
    @NotBlank(message = "不能为空")
    private String area;

    @TableId(value = "address_dtl")
    @ApiModelProperty(value = "详细地址"  , required = true)
    @NotBlank(message = "不能为空")
    private String addressDtl;

    @TableId(value = "is_drop")
    @ApiModelProperty(value = "是否删除，1：删除；0：未删除")
    private Integer isDrop;
}
