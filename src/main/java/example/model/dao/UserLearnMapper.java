 
package example.model.dao;

import java.util.List;
import java.util.Map;
import java.lang.Integer;
import org.apache.ibatis.annotations.Param;
import example.model.dataobject.UserLearn;

 
 
public interface UserLearnMapper {

	 
	Integer insert(UserLearn object);
	Integer update(UserLearn object);
	
	Integer deleteByKey(@Param("key") String key);
	UserLearn getByKey(@Param("key") String key);
	
	
	List<UserLearn>  findEntitys(@Param("param") Map<String, Object> param);
	Integer getEntitysCount(@Param("param") Map<String, Object> param);
		
		
 
}
