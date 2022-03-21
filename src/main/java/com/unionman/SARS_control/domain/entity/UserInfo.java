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
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @Description:
 * @author: jinhao.xu
 * @date 2022/3/16 10:33
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName(value = "user_info")
public class UserInfo extends Model {

    @TableId(value = "user_id", type = IdType.AUTO)
    @ApiModelProperty(value = "user_id")
    private Long userId;

    @ApiModelProperty(value = "凭证")
    private String token;

    @ApiModelProperty(value = "姓名" , required = true)
    private String username;

    @ApiModelProperty(value = "所属中心" , required = true)
    private String center;

    @ApiModelProperty(value = "联系电话" , required = true)
    private String phone;

    @ApiModelProperty(value = "在惠住址" , required = true)
    private String address;

    @ApiModelProperty(value = "来惠日期" , required = true)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate comeDate;

    @ApiModelProperty(value = "来惠方式" , required = true)
    private String comeVehicle;


    @ApiModelProperty(value = "车次号，选择飞机、火车、动车、高铁必填")
    private String comeVehicleNum;

    @ApiModelProperty(value = "粤康码")
    @NotEmpty(message = "不能为空")
    private String yueKangCode;

    @ApiModelProperty(value = "行程码")
    @NotEmpty(message = "不能为空")
    private String travelCode;

    @ApiModelProperty(value = "最新核酸报告")
    @NotEmpty(message = "不能为空")
    private String latestNucleicAcidReport;

    @ApiModelProperty(value = "前缀")
    private String filePath;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private LocalDateTime createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "是否删除，1：删除；0：未删除")
    private Integer isDrop;
}
