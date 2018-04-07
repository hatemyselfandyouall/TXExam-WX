 
package example.model.dao;

import java.util.List;
import java.util.Map;
import java.lang.Integer;

import example.model.dataobject.Examination;
import org.apache.ibatis.annotations.Param;


public interface ExaminationMapper {

	 
	Integer insert(Examination object);
	Integer update(Examination object);
	
	Integer deleteByKey(@Param("key") String key);
	Examination getByKey(@Param("key") String key);
	
	
	List<Examination>  findEntitys(@Param("param") Map<String, Object> param);
	Integer getEntitysCount(@Param("param") Map<String, Object> param);
		
		
 
}
