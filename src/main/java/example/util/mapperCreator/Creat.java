package example.util.mapperCreator;

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.mysql.jdbc.StringUtils;

public class Creat {
	//要生成的包的路径名
//	private static String packageName  ="com.ucmed.common.hospitalTable";
////	mysql的数据库名（不区分大小写）或者orcle的用户名（账号）（相当于数据库名）（必须大写）
//	private static String schemaName  = "jypt_sz_test";
//	//账号
//	private static String user = "ucmed";
//	//密码
//	private static String pass = "TiAw0@dae6r4Vnb";
//	//需要导出的表名Map
//	private static Map<String,Object> tableMap=null ;
	//需要导出的表名放这里，全部导出留空保证tableMap 为null
//	static {
//		tableMap = new HashMap<String, Object>();
//		tableMap.put("institution_SMS", 1);
//	}
	private static String packageName  ="example.model";
	private static String schemaName  = "sfxx";
	private static String user = "root";
	private static String pass = "111111";
	private static Map<String,Object> tableMap=null ;
	//需要导出的表名放这里，全部导出留空保证tableMap 为null
	static {
//		tableMap = new HashMap<String, Object>();
//		tableMap.put("calligraphy", 1);
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DatabaseMetaData dbMetaData = null;
		try {
			Properties props = new Properties();
			props.put("remarksReporting","true");//orcale获取字段注释需要添加这个条件。mysql不需要
			props.put("user",user);
			props.put("password",pass);
			//路径和驱动
//			String url = "jdbc:oracle:thin:@192.168.0.110:1521:XE";
			//orcale的驱动
//			Class.forName("oracle.jdbc.driver.OracleDriver");
			
//			String url = "jdbc:mysql://115.159.113.37:3306/"+schemaName;
			String url = "jdbc:mysql://localhost:3306/"+schemaName;
			//mysql的驱动
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(url, props);
//			rs = dbmd.getColumns(null, "OPS$AIMSADM", "AIRCRAFTS", "CODE");
//			if (rs.next()) {
//				System.out.println("Remarks: " + rs.getObject(12));
//			}
			
//			Connection con = DriverManager.getConnection(url, user, pass);
			dbMetaData = con.getMetaData();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String[] types = { "TABLE" };
		ResultSet tableRs;
		
		try {
			tableRs = dbMetaData.getTables(null, schemaName, "%", types);
			List<String> tableNameList = new ArrayList<String>();
			//获取所有表名
			while (tableRs.next()) {
				if(tableMap!=null){
					if(tableMap.get(tableRs.getString("TABLE_NAME"))!=null){
						tableNameList.add(tableRs.getString("TABLE_NAME"));
					}
				}else{
					tableNameList.add(tableRs.getString("TABLE_NAME"));
				}
			}
//			System.out.println(tableNameList);
			//获取所有表的字段和注释
			for(String tableName:tableNameList){
				List<FieldInfo> fieldInfoList = new ArrayList<FieldInfo>();
				ResultSet fieldRs = dbMetaData.getColumns(null, "%",tableName,"%");
				while (fieldRs.next()) {
					FieldInfo fieldInfo = new FieldInfo();
					String fieldSqlName = fieldRs.getString("COLUMN_NAME");//列名
					fieldInfo.setName(Util.getFieldName(fieldSqlName));
					fieldInfo.setSqlName(fieldSqlName.toLowerCase());
					fieldInfo.setType(Util.getBeanType(fieldRs.getString("TYPE_NAME"),fieldRs.getInt("DECIMAL_DIGITS")));
//					fieldInfo.setType(Util.getBeanType(type));
//					fieldInfo.setRemark(fieldRs.getString(12));//orcal获取字段注释使用
//					System.out.println(fieldRs.getString(12));
					//mysql获取字段注释使用
					if(fieldRs.getString("REMARKS")!=null){
//						fieldInfo.setRemark(new String(fieldRs.getString("REMARKS").getBytes("GBK"), "UTF-8"));
						fieldInfo.setRemark(fieldRs.getString("REMARKS"));
					}
					//用于判断是有数据类型遗漏没转换
					if(fieldInfo.getType()==null){
						System.out.println("-----------------------");
						System.out.println(tableName);
						System.out.println(fieldRs.getString("TYPE_NAME"));
						System.out.println(fieldInfo);
						
					}
					fieldInfoList.add(fieldInfo);
				}
				String className = Util.getClassName(tableName);
				//生成表名和字段数量
				System.out.println(className);
				System.out.println(fieldInfoList.size());
				//生成实体文件
				Util.creatModel(packageName,className);
				Util.creatDao(packageName,className);
				Util.creatEntity(packageName,className, fieldInfoList);
				//1：orcale模板，2：mysql模板
				Util.creatMapper(packageName,className,tableName, fieldInfoList,2);
//				break;
			}
			System.out.println("======Creat is over=======");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
}
