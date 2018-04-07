 
package example.model.service;

import example.model.dataobject.Examination;

import java.util.List;
import java.util.Map;
import java.lang.Integer;


public interface ExaminationService{


	
	Integer insert(Object entity);

	Integer update(Object entity);
	
	Integer deleteByKey(String key);
	
	Examination getByKey(String key);
	 
	List<Examination> findEntitys(Map<String, Object> param);
	Integer getEntitysCount(Map<String, Object> param);

	Integer deleteExamination(String key);

	Integer insertExamination(Examination examination);
}

 
