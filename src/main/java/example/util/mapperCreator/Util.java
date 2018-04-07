package example.util.mapperCreator;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Util {
	private static final Logger logger = Logger
			.getLogger(Util.class);
	//tab的4个空格
	private final static String BLACK = "    ";
	//换行
	private final static String NEWLINE = "\n";
	//文件目录
	private final static String FILENAME = "";
	//生成文件目录 默认code 项目下code文件夹,文件路劲要存在
	/**
	 * 模板路径
	 */
	private final static String MODELPATH="F://mapperCreator"+File.separator;
	private final static String PATH = "E://code";
	//mybatis数据库where语句模板
	private final static String COMPAREPARAMCOLUMNTEMPLATE = BLACK+BLACK+"<if test=\"param.fieldJava!=null\">\n"
					+BLACK+BLACK+BLACK+"AND  fieldSql = #{param.fieldJava}\n"
					+BLACK+BLACK+"</if>";
	//mybatis数据库update语句模板
	private final static String VUPDATETEMPLATE = BLACK+BLACK+BLACK+"<if test=\"fieldJava!=null\">, fieldSql = #{fieldJava} </if>";
	//mybatis数据库resultMap语句模板
	private final static String RESULTMAPTEMPLATE = BLACK+BLACK+"<result property=\"fieldJava\" column=\"fieldSql\" />";

	/**
	 * 生成entityl文件
	 * @param packageName:包名，ClassName:表名
	 * @return
	 */
	public static void creatEntity(String packageName,String className,List<FieldInfo>fieldInfoList){
		String data = readFileByLines(MODELPATH+"entityTemplate");
		data = data.replaceAll("packageName", packageName);
		data = data.replaceAll("ClassName", className);
		StringBuilder properties = new StringBuilder();
		StringBuilder gettersAndSetters = new StringBuilder();
		for(FieldInfo fieldInfo:fieldInfoList){
			String fieldName = fieldInfo.getName();
			properties.append(BLACK+"//"+fieldInfo.getRemark()+NEWLINE)
					.append(BLACK+"private "+ fieldInfo.getType() + " "+fieldName+";"+NEWLINE);

			gettersAndSetters.append(BLACK+"public "+fieldInfo.getType() + " get"+fieldName.substring(0,1).toUpperCase()+fieldName.substring(1,fieldName.length())+"() {"+NEWLINE)
							.append(BLACK+BLACK+"return "+fieldName+";"+NEWLINE+BLACK+"}"+NEWLINE)
							.append(BLACK+"public void set"+fieldName.substring(0,1).toUpperCase()+fieldName.substring(1,fieldName.length())+"("+fieldInfo.getType()+" "+fieldName+") {"+NEWLINE)
							.append(BLACK+BLACK+"this."+fieldName+" = "+fieldName+";"+NEWLINE+BLACK+"}"+NEWLINE);
		}
		data = data.replaceAll("PropertiesForChange", properties.toString());
		data = data.replaceAll("GettersAndSettersForChange", gettersAndSetters.toString());

		String FILENAME = className+".java";
		File file = new File(PATH+"/dataobject/"+FILENAME);
		File filePATH= new File(PATH+"/dataobject/");
		if (!filePATH.exists()){
			filePATH.mkdirs();
		}
		try (BufferedWriter output = new BufferedWriter(new FileWriter(file))){
			file.createNewFile();
            output.write(data);
            output.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// 不存在则创建
	}


	/**
	 * 生成model文件
	 * @param packageName:包名，ClassName:表名
	 * @return
	 */
	public static void creatModel(String packageName,String className){
		String data = readFileByLines(MODELPATH+"modelTemplate");
		data = data.replaceAll("packageName", packageName);
		data = data.replaceAll("ClassName", className);
		String FILENAME = className+"ServiceImpl.java";
		File file = new File(PATH+"/service/"+FILENAME);
		File filePATH= new File(PATH+"/service/");
		createService(packageName,className);
		if (!filePATH.exists()){
			filePATH.mkdirs();
		}
		try (BufferedWriter output = new BufferedWriter(new FileWriter(file))){
			file.createNewFile();
			output.write(data);
			output.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// 不存在则创建
	}

	public static void createService(String packageName,String className){
		String data = readFileByLines(MODELPATH+"serviceTemplate");
		data = data.replaceAll("packageName", packageName);
		data = data.replaceAll("ClassName", className);
		String FILENAME = className+"Service.java";
		File file = new File(PATH+"/service/"+FILENAME);
		File filePATH= new File(PATH+"/service/");
		if (!filePATH.exists()){
			filePATH.mkdirs();
		}
		try (BufferedWriter output = new BufferedWriter(new FileWriter(file))){
			file.createNewFile();
			output.write(data);
			output.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// 不存在则创建
	}

	/**
	 * 生成dao文件
	 * @param packageName:包名，ClassName:表名
	 * @return
	 */
	public static void creatDao(String packageName,String className){
		String data = readFileByLines(MODELPATH+"daoTemplate");
		data = data.replaceAll("packageName", packageName);
		data = data.replaceAll("ClassName", className);
		String FILENAME = className+"Mapper.java";
		File file = new File(PATH+"/dao/"+FILENAME);
		File filePATH= new File(PATH+"/dao/");
		if (!filePATH.exists()){
			filePATH.mkdirs();
		}
		try (BufferedWriter output = new BufferedWriter(new FileWriter(file))){
			file.createNewFile();
			output.write(data);
			output.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// 不存在则创建
	}

	/**
	 * 生成mapper文件
	 * @param packageName:包名，ClassName:类名,tableName：表名
	 * @return
	 */
	public static void creatMapper(String packageName,String className,
			String tableName,List<FieldInfo>fieldInfoList,int type){
		String data = null;
		if(type==1){
			 data = readFileByLines(MODELPATH+"orcaleMapperTemplate.xml");
		}else if(type==2){
			 data = readFileByLines(MODELPATH+"mysqlMapperTemplate.xml");
		}
		data = data.replaceAll("packageName", packageName);
		data = data.replaceAll("ClassName", className);
		data = data.replaceAll("tableName", tableName);
		StringBuilder resultMaps = new StringBuilder();
		StringBuilder insertSql = new StringBuilder();
		StringBuilder insertJava = new StringBuilder();
		StringBuilder compareParamColumns = new StringBuilder();
		StringBuilder updateStr = new StringBuilder();
		for(FieldInfo fieldInfo:fieldInfoList){
			String fieldName = fieldInfo.getName();
			String fieldSqlName = fieldInfo.getSqlName();
			//resultMap替换
			String fieldStr = RESULTMAPTEMPLATE.replaceAll("fieldJava", fieldName);
			fieldStr = fieldStr.replaceAll("fieldSql", fieldSqlName);
			resultMaps.append(fieldStr+"\n");
			//insert语句替换
			//fieldInfoList 中主键如果是自增的需要在insert中去除 默认是id；
			if(!"id".equals(fieldName)){
				if(insertSql.length()==0){
					insertSql.append(fieldSqlName);
					insertJava.append("#{"+fieldName+"}");
				}else{
					insertSql.append(","+fieldSqlName);
					insertJava.append(",#{"+fieldName+"}");
				}
			}
			//update语句替换
			String updateField = "";
			if(updateStr.length()==0){
				updateField = VUPDATETEMPLATE.replaceAll("fieldJava", fieldName);
				updateField = updateField.replaceAll("fieldSql", fieldSqlName);
				updateField = updateField.replaceAll(",", "");
			}else{
				updateField = VUPDATETEMPLATE.replaceAll("fieldJava", fieldName);
				updateField = updateField.replaceAll("fieldSql", fieldSqlName);
			}
			updateStr.append(updateField+"\n");
			//compareParamColumn替换
			String compareParamColumn = COMPAREPARAMCOLUMNTEMPLATE.replaceAll("fieldJava", fieldName);
			compareParamColumn = compareParamColumn.replaceAll("fieldSql", fieldSqlName);
			compareParamColumns.append(compareParamColumn+"\n");
		}
		data = data.replaceAll("resultMapforChange", resultMaps.toString());
		data = data.replaceAll("insertSqlForChange", insertSql.toString());
		data = data.replaceAll("insertJavaForChange", insertJava.toString());
		data = data.replaceAll("updateForChange", updateStr.toString());
		data = data.replaceAll("compareParamColumnsForChange", compareParamColumns.toString());

		String FILENAME = className+"Mapper.xml";
		File file = new File(PATH+"/mapper/"+FILENAME);
		File filePATH= new File(PATH+"/mapper/");
		if (!filePATH.exists()){
			filePATH.mkdirs();
		}
		try (BufferedWriter output = new BufferedWriter(new FileWriter(file))){
			file.createNewFile();
			output.write(data);
			output.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// 不存在则创建
	}


	/**
	 * 根据数据库字段类型获取对应的java变量类型
	 * @param ，  小数位数
	 * @return
	 */
	public static String getBeanType(String type,int decimalDigits){
		type = type.toUpperCase();
		if(type.indexOf("INT")!=-1){
			return "Integer";
		}
		if(type.indexOf("BIT")!=-1){
			return "Integer";
		}
		if(type.indexOf("FLOAT")!=-1){
			return "Float";
		}
		if(type.indexOf("DOUBLE")!=-1){
			return "Double";
		}
		if(type.indexOf("DECIMAL")!=-1){
			return "Double";
		}
		if(type.indexOf("CHAR")!=-1){
			return "String";
		}
		if(type.indexOf("VARCHAR")!=-1){
			return "String";
		}
		if(type.indexOf("TEXT")!=-1){
			return "String";
		}
		if(type.indexOf("BLOB")!=-1){
			return "Byte []";
		}
		if(type.indexOf("VARBINARY")!=-1){
			return "String";
		}
		if(type.indexOf("BINARY")!=-1){
			return "String";
		}
		if(type.indexOf("CLOB")!=-1){
			return "String";
		}
		if(type.indexOf("DATE")!=-1){
			return "Date";
		}
		if(type.indexOf("TIMESTAMP")!=-1){
			return "Date";
		}
		if(type.indexOf("NUMBER")!=-1){
			if(decimalDigits>0){
				return "Double";
			}else{
				return "Integer";
			}
		}
		if(type.indexOf("LONG")!=-1){
			return "Long";
		}

		return null;
	}

	/**
	 * 将sql中的字段名改为驼峰命名
	 * @param
	 * @return
	 */
	public static String getFieldName(String fieldName){
		fieldName = fieldName.toLowerCase();
		while(fieldName.indexOf("_")!=-1){
			String letter = fieldName.substring(fieldName.indexOf("_")+1, fieldName.indexOf("_")+2);
			fieldName = fieldName.replace("_"+letter, letter.toUpperCase());
		}
		return fieldName;
	}

	/**
	 * 将sql中的表名改为类命
	 * @param
	 * @return
	 */
	public static String getClassName(String name){
		name = name.toLowerCase();
		name = name.replace("tb", "");
		while(name.indexOf("_")!=-1){
			String letter = name.substring(name.indexOf("_")+1, name.indexOf("_")+2);
			name = name.replace("_"+letter, letter.toUpperCase());
		}
		while(name.indexOf("$")!=-1){
			String letter = name.substring(name.indexOf("$")+1, name.indexOf("$")+2);
			name = name.replace("$"+letter, letter.toUpperCase());
		}
		name = name.substring(0,1).toUpperCase()+name.substring(1,name.length());
		return name;
	}


	/**
     * 以行为单位读取文件，常用于读面向行的格式化文件
     */
    public static String readFileByLines(String FILENAME) {
        File file = new File(FILENAME);
        BufferedReader reader = null;
        String data = "";
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                // 显示行号
            	data +=  tempString+"\n";
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
					logger.debug(e1);
				}
            }
        }
        return data;
    }
}
