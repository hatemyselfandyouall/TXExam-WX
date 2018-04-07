 
package example.model.dao;

import java.util.List;
import java.util.Map;
import java.lang.Integer;
import org.apache.ibatis.annotations.Param;
import example.model.dataobject.User;

 
 
public interface UserMapper {

	 
	Integer insert(User object);
	Integer update(User object);
	
	Integer deleteByKey(@Param("key")String key);
	User getByKey (@Param("key") String key);
	
	
	List<User>  findEntitys(@Param("param")Map<String ,Object>  param);
	Integer getEntitysCount(@Param("param")Map<String ,Object>  param);
		
		
 
}
