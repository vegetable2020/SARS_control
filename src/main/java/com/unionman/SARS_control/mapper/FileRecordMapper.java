package com.unionman.SARS_control.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.unionman.SARS_control.domain.entity.FileRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @author: jinhao.xu
 * @date 2022/3/17 15:22
 */
@Mapper
public interface FileRecordMapper extends BaseMapper<FileRecord> {

}
