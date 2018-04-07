 
package example.model.dao;

import java.util.List;
import java.util.Map;
import java.lang.Integer;
import org.apache.ibatis.annotations.Param;
import example.model.dataobject.UserExamination;

 
 
public interface UserExaminationMapper {

	 
	Integer insert(UserExamination object);
	Integer update(UserExamination object);
	
	Integer deleteByKey(@Param("key") String key);
	UserExamination getByKey(@Param("key") String key);
	
	
	List<UserExamination>  findEntitys(@Param("param") Map<String, Object> param);
	Integer getEntitysCount(@Param("param") Map<String, Object> param);
		
		
 
}
