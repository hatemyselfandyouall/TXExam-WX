 
package example.model.service;

import java.util.List;
import java.util.Map;
import java.lang.Integer;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import example.model.dao.UserWorkMapper;
import example.model.dataobject.UserWork;
import org.springframework.stereotype.Service;


@Service
public class UserWorkServiceImpl implements UserWorkService{

	@Autowired
	private UserWorkMapper dao;
	
	public Integer insert(Object entity){
		return dao.insert((UserWork)entity);
	}

	public Integer update(Object entity){
		return dao.update((UserWork)entity);
	}
	
	public Integer deleteByKey( String key){
		return dao.deleteByKey(key);
	}
	
	public UserWork getByKey( String key){
		return dao.getByKey(key);
	}
	 
	public List<UserWork> findEntitys(Map<String ,Object>  param){
		return dao.findEntitys(param);
	}
	public Integer getEntitysCount(Map<String ,Object>  param){
		return dao.getEntitysCount(param);
	}
	 

}

 
