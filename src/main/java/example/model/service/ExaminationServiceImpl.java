 
package example.model.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.lang.Integer;

import example.model.dataobject.Examination;
import org.springframework.beans.factory.annotation.Autowired;

import example.model.dao.ExaminationMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class ExaminationServiceImpl implements ExaminationService{

	@Autowired
	private ExaminationMapper dao;
	
	public Integer insert(Object entity){
		return dao.insert((Examination)entity);
	}

	public Integer update(Object entity){
		return dao.update((Examination)entity);
	}
	
	public Integer deleteByKey( String key){
		return dao.deleteByKey(key);
	}
	
	public Examination getByKey( String key){
		return dao.getByKey(key);
	}
	 
	public List<Examination> findEntitys(Map<String ,Object>  param){
		return dao.findEntitys(param);
	}
	public Integer getEntitysCount(Map<String ,Object>  param){
		return dao.getEntitysCount(param);
	}

	@Override
	@Transactional
	public Integer deleteExamination(String key) {
		Examination examination=getByKey(key);
		if (examination==null){
			return 0;
		}else {
			examination.setIsDelete(1);
			return  update(examination);
		}
	}

	public  Integer insertExamination(Examination examination){
		if (examination==null){
			return 0;
		}
		examination.setUpdateTime(new Date());
		if (examination.getId().equals(0)){
			examination.setCreateTime(new Date());
			examination.setIsDelete(0);
			Integer flag=insert(examination);;
			return examination.getId();
		}else {
			return update(examination);
		}
	}


}

 
