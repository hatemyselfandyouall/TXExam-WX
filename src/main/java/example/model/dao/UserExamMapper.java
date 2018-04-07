 
package example.model.dao;

import java.util.List;
import java.util.Map;
import java.lang.Integer;
import org.apache.ibatis.annotations.Param;
import example.model.dataobject.UserExam;

 
 
public interface UserExamMapper {

	 
	Integer insert(UserExam object);
	Integer update(UserExam object);
	
	Integer deleteByKey(@Param("key") String key);
	UserExam getByKey(@Param("key") String key);
	
	
	List<UserExam>  findEntitys(@Param("param") Map<String, Object> param);
	Integer getEntitysCount(@Param("param") Map<String, Object> param);


 
}
