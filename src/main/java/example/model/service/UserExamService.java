 
package example.model.service;

import java.util.List;
import java.util.Map;
import java.lang.Integer;
import org.apache.ibatis.annotations.Param;


import example.model.dao.UserExamMapper;
import example.model.dataobject.UserExam;

 

public interface UserExamService{


	
	Integer insert(Object entity);

	Integer update(Object entity);
	
	Integer deleteByKey(String key);
	
	UserExam getByKey(String key);
	 
	List<UserExam> findEntitys(Map<String, Object> param);
	Integer getEntitysCount(Map<String, Object> param);

	Integer upload(UserExam userExam);

}

 
