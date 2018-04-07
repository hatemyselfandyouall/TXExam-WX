 
package example.model.service;

import java.util.List;
import java.util.Map;
import java.lang.Integer;
import org.apache.ibatis.annotations.Param;


import example.model.dao.UserLearnMapper;
import example.model.dataobject.UserLearn;

 

public interface UserLearnService{


	
	Integer insert(Object entity);

	Integer update(Object entity);
	
	Integer deleteByKey(String key);
	
	UserLearn getByKey(String key);
	 
	List<UserLearn> findEntitys(Map<String, Object> param);
	Integer getEntitysCount(Map<String, Object> param);
	 

}

 
