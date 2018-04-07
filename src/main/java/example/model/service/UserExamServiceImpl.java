 
package example.model.service;

import java.util.List;
import java.util.Map;
import java.lang.Integer;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import example.model.dao.UserExamMapper;
import example.model.dataobject.UserExam;
import org.springframework.stereotype.Service;


@Service
public class UserExamServiceImpl implements UserExamService{

	@Autowired
	private UserExamMapper dao;
	
	public Integer insert(Object entity){
		return dao.insert((UserExam)entity);
	}

	public Integer update(Object entity){
		return dao.update((UserExam)entity);
	}
	
	public Integer deleteByKey( String key){
		return dao.deleteByKey(key);
	}
	
	public UserExam getByKey( String key){
		return dao.getByKey(key);
	}
	 
	public List<UserExam> findEntitys(Map<String ,Object>  param){
		return dao.findEntitys(param);
	}
	public Integer getEntitysCount(Map<String ,Object>  param){
		return dao.getEntitysCount(param);
	}

	@Override
	public Integer upload(UserExam userExam) {
		if (userExam.getId()==null){
		  return 	insert(userExam);
		}else {
		  return 	update(userExam);
		}
	}


}

 
