 
package example.model.dao;

import java.util.List;
import java.util.Map;
import java.lang.Integer;
import org.apache.ibatis.annotations.Param;
import example.model.dataobject.UserWork;

 
 
public interface UserWorkMapper {

	 
	Integer insert(UserWork object);
	Integer update(UserWork object);
	
	Integer deleteByKey(@Param("key") String key);
	UserWork getByKey(@Param("key") String key);
	
	
	List<UserWork>  findEntitys(@Param("param") Map<String, Object> param);
	Integer getEntitysCount(@Param("param") Map<String, Object> param);
		
		
 
}
