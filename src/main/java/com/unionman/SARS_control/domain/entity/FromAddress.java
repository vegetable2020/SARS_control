package com.unionman.SARS_control.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

/**
 * @Description: 来源地址
 * @author: jinhao.xu
 * @date 2022/3/16 11:19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName(value = "from_address")
public class FromAddress extends Model {
    @TableId(value = "address_id", type = IdType.AUTO)
    @ApiModelProperty(value = "addressId")
    private Long addressId;

    @ApiModelProperty(value = "关联的信息id")
    private Long userId;

    @ApiModelProperty(value = "省code" , required = true)
    @NotEmpty(message = "不能为空")
    private Long provinceCode;

    @ApiModelProperty(value = "省" , required = true)
    @NotEmpty(message = "不能为空")
    private String province;

    @ApiModelProperty(value = "市code" , required = true)
    @NotEmpty(message = "不能为空")
    private Long cityCode;

    @ApiModelProperty(value = "市"  , required = true)
    @NotEmpty(message = "不能为空")
    private String city;

    @ApiModelProperty(value = "区code" , required = true)
    @NotEmpty(message = "不能为空")
    private Long areaCode;

    @ApiModelProperty(value = "区"  , required = true)
    @NotEmpty(message = "不能为空")
    private String area;

    @ApiModelProperty(value = "详细地址"  , required = true)
    @NotEmpty(message = "不能为空")
    private String addressDtl;

    @ApiModelProperty(value = "是否删除，1：删除；0：未删除")
    private Integer isDrop;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private LocalDateTime createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private LocalDateTime updateTime;
}
