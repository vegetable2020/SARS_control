package com.unionman.SARS_control.domain.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @Description:
 * @author: jinhao.xu
 * @date 2022/3/17 9:06
 */
@Data
@Accessors(chain = true)
@ApiModel("何地来惠信息 vo")
public class FromAddressVO implements Serializable {
    @ApiModelProperty(value = "addressId")
    private Integer addressId;

    @ApiModelProperty(value = "关联的信息id")
    private Integer userId;

    @ApiModelProperty(value = "省code" )
    private Long provinceCode;

    @ApiModelProperty(value = "省" )
    private String province;

    @ApiModelProperty(value = "市code" )
    private Long cityCode;

    @ApiModelProperty(value = "市"  )
    private String city;

    @ApiModelProperty(value = "区code" )
    private Long areaCode;

    @ApiModelProperty(value = "区"  )
    private String area;

    @ApiModelProperty(value = "详细地址"  )
    private String addressDtl;

    @ApiModelProperty(value = "是否删除，1：删除；0：未删除")
    private Integer isDrop;
}
