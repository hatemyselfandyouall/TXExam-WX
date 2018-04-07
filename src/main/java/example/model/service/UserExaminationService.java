 
package example.model.service;

import java.util.List;
import java.util.Map;
import java.lang.Integer;
import org.apache.ibatis.annotations.Param;


import example.model.dao.UserExaminationMapper;
import example.model.dataobject.UserExamination;

 

public interface UserExaminationService{


	
	Integer insert(Object entity);

	Integer update(Object entity);
	
	Integer deleteByKey(String key);
	
	UserExamination getByKey(String key);
	 
	List<UserExamination> findEntitys(Map<String, Object> param);
	Integer getEntitysCount(Map<String, Object> param);
	 

}

 
