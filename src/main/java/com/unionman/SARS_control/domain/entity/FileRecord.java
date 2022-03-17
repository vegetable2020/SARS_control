package com.unionman.SARS_control.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description:
 * @author: jinhao.xu
 * @date 2022/3/17 15:15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName(value = "file_record")
public class FileRecord extends Model {
    @TableId(value = "file_id")
    private Long fileId;
    private String filePath;
    private String fileName;
    private Long fileSize;
    private Integer isDrop;
}
