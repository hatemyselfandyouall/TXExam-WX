 
package example.model.service;

import java.util.List;
import java.util.Map;
import java.lang.Integer;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import example.model.dao.UserLearnMapper;
import example.model.dataobject.UserLearn;
import org.springframework.stereotype.Service;


@Service
public class UserLearnServiceImpl implements UserLearnService{

	@Autowired
	private UserLearnMapper dao;
	
	public Integer insert(Object entity){
		return dao.insert((UserLearn)entity);
	}

	public Integer update(Object entity){
		return dao.update((UserLearn)entity);
	}
	
	public Integer deleteByKey( String key){
		return dao.deleteByKey(key);
	}
	
	public UserLearn getByKey( String key){
		return dao.getByKey(key);
	}
	 
	public List<UserLearn> findEntitys(Map<String ,Object>  param){
		return dao.findEntitys(param);
	}
	public Integer getEntitysCount(Map<String ,Object>  param){
		return dao.getEntitysCount(param);
	}
	 

}

 
