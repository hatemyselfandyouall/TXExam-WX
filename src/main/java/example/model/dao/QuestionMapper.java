 
package example.model.dao;

import java.util.List;
import java.util.Map;
import java.lang.Integer;
import org.apache.ibatis.annotations.Param;
import example.model.dataobject.Question;

 
 
public interface QuestionMapper {

	 
	Integer insert(Question object);
	Integer update(Question object);
	
	Integer deleteByKey(@Param("key") String key);
	Question getByKey(@Param("key") String key);
	
	
	List<Question>  findEntitys(@Param("param") Map<String, Object> param);
	Integer getEntitysCount(@Param("param") Map<String, Object> param);

	Integer deleteQuestionsByExaminationId(@Param("examinationId")String examinationId);
		
 
}
