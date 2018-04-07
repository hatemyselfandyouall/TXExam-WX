 
package example.model.service;

import java.util.List;
import java.util.Map;
import java.lang.Integer;
import org.apache.ibatis.annotations.Param;


import example.model.dao.UserWorkMapper;
import example.model.dataobject.UserWork;

 

public interface UserWorkService{


	
	Integer insert(Object entity);

	Integer update(Object entity);
	
	Integer deleteByKey(String key);
	
	UserWork getByKey(String key);
	 
	List<UserWork> findEntitys(Map<String, Object> param);
	Integer getEntitysCount(Map<String, Object> param);
	 

}

 
