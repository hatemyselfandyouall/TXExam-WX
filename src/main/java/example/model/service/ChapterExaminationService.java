 
package example.model.service;

import java.util.List;
import java.util.Map;
import java.lang.Integer;
import org.apache.ibatis.annotations.Param;


import example.model.dao.ChapterExaminationMapper;
import example.model.dataobject.ChapterExamination;

 

public interface ChapterExaminationService{


	
	Integer insert(Object entity);

	Integer update(Object entity);
	
	Integer deleteByKey(String key);
	
	ChapterExamination getByKey(String key);
	 
	List<ChapterExamination> findEntitys(Map<String, Object> param);
	Integer getEntitysCount(Map<String, Object> param);
	 
	Integer SaveChapSetting(ChapterExamination chapterExamination);

	boolean checkChapSetting(ChapterExamination chapterExamination);
}

 
