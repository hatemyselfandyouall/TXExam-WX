 
package example.model.service;

import java.util.List;
import java.util.Map;
import java.lang.Integer;

import example.model.dao.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;

import example.model.dataobject.User;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private UserMapper dao;

	@Override
	public Integer insert(Object entity){
		return dao.insert((User)entity);
	}

	@Override
	public Integer update(Object entity){
		return dao.update((User)entity);
	}

	@Override
	public Integer deleteByKey( String key){
		return dao.deleteByKey(key);
	}

	@Override
	public User getByKey( String key){
		return dao.getByKey(key);
	}

	@Override
	public List<User> findEntitys(Map<String ,Object>  param){
		return dao.findEntitys(param);
	}
	@Override
	public Integer getEntitysCount(Map<String ,Object>  param){
		return dao.getEntitysCount(param);
	}


}

 
