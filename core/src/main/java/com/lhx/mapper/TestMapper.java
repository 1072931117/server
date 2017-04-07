package com.lhx.mapper;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by lhx on 2017/3/24.
 */
@Repository
public interface TestMapper {
    @Select("select * from t_user_info limit 0,2")
    List<Map> testSelect();
}
