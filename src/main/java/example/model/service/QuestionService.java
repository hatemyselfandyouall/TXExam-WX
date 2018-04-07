 
package example.model.service;

import java.util.List;
import java.util.Map;
import java.lang.Integer;
import org.apache.ibatis.annotations.Param;


import example.model.dao.QuestionMapper;
import example.model.dataobject.Question;

 

public interface QuestionService{


	
	Integer insert(Object entity);

	Integer update(Object entity);
	
	Integer deleteByKey(String key);
	
	Question getByKey(String key);
	 
	List<Question> findEntitys(Map<String, Object> param);
	Integer getEntitysCount(Map<String, Object> param);

	boolean createTestInfo();

	Integer deleteQuestion(String key);

	Integer deleteQuestionsByExaminationId(String examinationId);

	boolean saveQuestions(List<Question> questions,String examinationId);

	Integer insertQuestion(Question questions);

}

 
