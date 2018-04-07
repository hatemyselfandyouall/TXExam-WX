 
package example.model.service;

import java.util.List;
import java.util.Map;
import java.lang.Integer;
import org.apache.ibatis.annotations.Param;


import example.model.dao.CalligraphyMapper;
import example.model.dataobject.Calligraphy;

 

public interface CalligraphyService{


	
	Integer insert(Object entity);

	Integer update(Object entity);
	
	Integer deleteByKey(String key);
	
	Calligraphy getByKey(String key);
	 
	List<Calligraphy> findEntitys(Map<String, Object> param);
	Integer getEntitysCount(Map<String, Object> param);
	 

}

 
