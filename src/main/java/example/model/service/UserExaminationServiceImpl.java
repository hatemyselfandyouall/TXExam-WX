 
package example.model.service;

import java.util.List;
import java.util.Map;
import java.lang.Integer;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import example.model.dao.UserExaminationMapper;
import example.model.dataobject.UserExamination;
import org.springframework.stereotype.Service;


@Service
public class UserExaminationServiceImpl implements UserExaminationService{

	@Autowired
	private UserExaminationMapper dao;
	
	public Integer insert(Object entity){
		return dao.insert((UserExamination)entity);
	}

	public Integer update(Object entity){
		return dao.update((UserExamination)entity);
	}
	
	public Integer deleteByKey( String key){
		return dao.deleteByKey(key);
	}
	
	public UserExamination getByKey( String key){
		return dao.getByKey(key);
	}
	 
	public List<UserExamination> findEntitys(Map<String ,Object>  param){
		return dao.findEntitys(param);
	}
	public Integer getEntitysCount(Map<String ,Object>  param){
		return dao.getEntitysCount(param);
	}
	 

}

 
