package com.cf.blog.mapper.user;

import java.util.List;
import java.util.Map;

public interface IAlertWorkOrderMapper {
	List<Map<String, Object>> queryAll(Map<String, Object> paramMap) throws Exception;
}
