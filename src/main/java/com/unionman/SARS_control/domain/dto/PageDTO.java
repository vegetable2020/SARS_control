package com.unionman.SARS_control.domain.dto;

import com.unionman.SARS_control.utils.StringUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 分页数据
 * 
 * @author ruoyi
 */
@ApiModel("分页查询数据参数对象")
public class PageDTO
{
    /** 当前记录起始索引 */
    @ApiModelProperty(value = "页码(开始页:1, 默认1，-1：所有)"
            ,required = true)
    @NotNull(
            message = "参数必传，传-1代表不分页"
    )
    @Min(
            value = -1L,
            message = "参数必须大于-1"
    )
    private Integer pageNum;

    /** 每页显示记录数 */
    @ApiModelProperty(
            value = "每页数据数量，-1查全部",
            required = true
    )
    @NotNull(
            message = "参数必传"
    )
    @Min(
            value = -1L,
            message = "参数必须大于-1"
    )
    private Integer pageSize;

    /** 排序列 */
    private String orderByColumn;

    /** 排序的方向desc或者asc */
    private String isAsc = "asc";

    public String getOrderBy()
    {
        if (StringUtils.isEmpty(orderByColumn))
        {
            return "";
        }
        return StringUtils.toUnderScoreCase(orderByColumn) + " " + isAsc;
    }

    public Integer getPageNum()
    {
        return pageNum;
    }

    public void setPageNum(Integer pageNum)
    {
        this.pageNum = pageNum;
    }

    public Integer getPageSize()
    {
        return pageSize;
    }

    public void setPageSize(Integer pageSize)
    {
        this.pageSize = pageSize;
    }

    public String getOrderByColumn()
    {
        return orderByColumn;
    }

    public void setOrderByColumn(String orderByColumn)
    {
        this.orderByColumn = orderByColumn;
    }

    public String getIsAsc()
    {
        return isAsc;
    }

    public void setIsAsc(String isAsc)
    {
        this.isAsc = isAsc;
    }
}
