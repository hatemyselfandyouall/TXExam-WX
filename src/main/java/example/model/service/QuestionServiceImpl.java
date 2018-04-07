 
package example.model.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.lang.Integer;

import example.model.dataobject.ChapterExamination;
import example.model.dataobject.Examination;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import example.model.dao.QuestionMapper;
import example.model.dataobject.Question;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class QuestionServiceImpl implements QuestionService{

	@Autowired
	private QuestionMapper dao;
	
	public Integer insert(Object entity){
		return dao.insert((Question)entity);
	}

	public Integer update(Object entity){
		return dao.update((Question)entity);
	}
	
	public Integer deleteByKey( String key){
		return dao.deleteByKey(key);
	}
	
	public Question getByKey( String key){
		return dao.getByKey(key);
	}
	 
	public List<Question> findEntitys(Map<String ,Object>  param){
		return dao.findEntitys(param);
	}
	public Integer getEntitysCount(Map<String ,Object>  param){
		return dao.getEntitysCount(param);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean createTestInfo() {
		for (int i=0;i<30;i++){
			Question question=new Question();
			question.setExaminationId(2);
			question.setQuestionType(1);
			question.setContent("基本建设的最终成果一般表现为固定资产的增加。同时，所有新增加固定资产的经济活动都称为基本建设");
			question.setAnswerChoice("1");
			question.setCreateTime(new Date());
			question.setUpdateTime(new Date());
			dao.insert(question);
		}
		for (int i=0;i<20;i++){
			Question question=new Question();
			question.setExaminationId(2);
			question.setQuestionType(2);
			question.setContent("以下选项，不是一个建设项目应该具有的特点的是");
			question.setChoice("A.按照一个总体设计进行建设|B.经济上与投资主体在建的所有建设项目实行统一核算，行政上统一管理|C.由一个或若干个具有内在联系的工程所组成的总体|D.建成后具有完整的系统，可以独立地形成生产能力");
			question.setAnswerChoice("A");
			question.setCreateTime(new Date());
			question.setUpdateTime(new Date());
			dao.insert(question);
		}
		for (int i=0;i<15;i++){
			Question question=new Question();
			question.setExaminationId(2);
			question.setQuestionType(3);
			question.setContent("按建设项目资金来源和渠道不同分类，建设项目可以分为");
			question.setChoice("A.生产性建设项目|B.非生产性建设项目|C.国家投资建设项目|D.自筹资金建设项目");
			question.setAnswerChoice("A|B");
			question.setCreateTime(new Date());
			question.setUpdateTime(new Date());
			dao.insert(question);
		}
		for (int i=0;i<3;i++){
			Question question=new Question();
			question.setExaminationId(2);
			question.setQuestionType(4);
			question.setContent("根据已知条件，按照工信部通信[2016]451号文颁布的费用定额，编制“建筑安装工程费用预算表(表二)”。计算结果要求精确到小数点后二位。\n" +
					"已知条件：\n" +
					"1.本工程为XX公司新建通信管道单项工程，在城区施工，工程所在地为福建省非特殊地区，施工企业基地距施工现场40公里。\n" +
					"2.本工程技工总工日为300工日，普工总工日为500工日，施工机械使用费为3000元。\n" +
					"3.甲供主要材料费为90000元，增值税税率为17％，乙供主要材料费为70000元，增值税税率为11％。\n" +
					"4.本工程施工用水、电、蒸汽费为3500元，运土费为2000元，大型机械为液压顶管机（5t）。\n" +
					"5.其他不具备计算条件的各项费用不计取。");
			question.setAnswerContent("哇这个问题太难了宝宝不会啊怎么办");
			question.setCreateTime(new Date());
			question.setUpdateTime(new Date());
			dao.insert(question);
		}
		return true;
	}

	@Override
	@Transactional(rollbackFor =Exception.class)
	public boolean saveQuestions(List<Question> questions, String examinationId) {
		dao.deleteQuestionsByExaminationId(examinationId);
		for (Question question:questions){
			question.setIsDelete(0);
			question.setExaminationId(Integer.valueOf(examinationId));
			insert(question);
		}
		return true;
	}



	@Override
	@Transactional
	public Integer deleteQuestion(String key) {
		Question question=getByKey(key);
		if (question==null){
			return 0;
		}else {
			question.setIsDelete(1);
			return  update(question);
		}
	}

	@Override
	public Integer deleteQuestionsByExaminationId(String examinationId) {
		return dao.deleteQuestionsByExaminationId(examinationId);
	}

	@Override
	@Transactional
	public Integer insertQuestion(Question question) {

		Map<String,Object> param=new HashMap<>();
		if (question.getId()==null){
			question.setIsDelete(0);
			return insert(question);
		}else {
			Question questions=getByKey(question.getId().toString());
			question.setId(questions.getId());
			return update(question);
		}
	}
}

 
