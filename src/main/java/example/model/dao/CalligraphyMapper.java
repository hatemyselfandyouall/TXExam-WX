 
package example.model.dao;

import java.util.List;
import java.util.Map;
import java.lang.Integer;
import org.apache.ibatis.annotations.Param;
import example.model.dataobject.Calligraphy;

 
 
public interface CalligraphyMapper {

	 
	Integer insert(Calligraphy object);
	Integer update(Calligraphy object);
	
	Integer deleteByKey(@Param("key") String key);
	Calligraphy getByKey(@Param("key") String key);
	
	
	List<Calligraphy>  findEntitys(@Param("param") Map<String, Object> param);
	Integer getEntitysCount(@Param("param") Map<String, Object> param);
		
		
 
}
