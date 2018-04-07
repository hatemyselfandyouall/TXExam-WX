package example.util.mapperCreator;


public class FieldInfo {
	
	//字段名
	private String name;
	//sql字段名
	private String sqlName;
	//字段类型
	private String type;
	//字段注释
	private String remark;
	
	public FieldInfo(){
	}
	
	public FieldInfo(String name,String sqlName,String type,String remark){
		this.name=name;
		this.sqlName=sqlName;
		this.type=type;
		this.remark=remark;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSqlName() {
		return sqlName;
	}

	public void setSqlName(String sqlName) {
		this.sqlName = sqlName;
	}
	@Override
    public String toString() {
		return "name:"+this.name+",sqlName:"+this.sqlName+",type:"+this.type+",remark:"+this.remark;
    }
	
}
