package com.unionman.SARS_control.domain.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.unionman.SARS_control.utils.MyStringImageConverter;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

/**
 * @Description:
 * @author: jinhao.xu
 * @date 2022/3/17 9:38
 */
@Getter
@Setter
@EqualsAndHashCode
@ContentRowHeight(35)
@ColumnWidth(15)
public class UserInfoExcel {
    @ExcelIgnore
    private Integer userId;

    @ExcelIgnore
    private String token;

    @ExcelProperty("姓名")
    private String username;

    @ExcelProperty("所属中心")
    private String center;

    @ExcelProperty("联系电话")
    private String phone;

    @ExcelProperty("目前在惠住址")
    private String address;

    @ExcelProperty("离惠日期")
    private String leaveDate;

    @ExcelProperty("来惠日期")
    private String comeDate;

    @ExcelProperty(value = "何地来惠")
    @ColumnWidth(50)
    private String fromAddress;

    @ExcelProperty("来惠方式")
    private String comeVehicle;

    @ExcelProperty("车次号")
    private String comeVehicleNum;

    @ExcelProperty(value = "粤康码", converter = MyStringImageConverter.class)
    @ColumnWidth(25)
    private String yueKangCode;

    @ExcelProperty(value = "行程码", converter = MyStringImageConverter.class)
    @ColumnWidth(25)
    private String travelCode;

    @ExcelProperty(value = "最新核酸报告", converter = MyStringImageConverter.class)
    @ColumnWidth(25)
    private String latestNucleicAcidReport;
}
