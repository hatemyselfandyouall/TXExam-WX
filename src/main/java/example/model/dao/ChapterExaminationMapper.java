 
package example.model.dao;

import java.util.List;
import java.util.Map;
import java.lang.Integer;
import org.apache.ibatis.annotations.Param;
import example.model.dataobject.ChapterExamination;

 
 
public interface ChapterExaminationMapper {

	 
	Integer insert(ChapterExamination object);
	Integer update(ChapterExamination object);
	
	Integer deleteByKey(@Param("key") String key);
	ChapterExamination getByKey(@Param("key") String key);
	
	
	List<ChapterExamination>  findEntitys(@Param("param") Map<String, Object> param);
	Integer getEntitysCount(@Param("param") Map<String, Object> param);
		
		
 
}
