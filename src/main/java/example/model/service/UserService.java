 
package example.model.service;

import java.util.List;
import java.util.Map;
import java.lang.Integer;


import example.model.dataobject.User;


public interface UserService{


	
	Integer insert(Object entity);

	Integer update(Object entity);
	
	Integer deleteByKey(String key);
	
	User getByKey(String key);
	 
	List<User> findEntitys(Map<String, Object> param);
	Integer getEntitysCount(Map<String, Object> param);
	 

}

 
