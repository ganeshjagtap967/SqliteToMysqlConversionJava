package com.sqlitedb.demo.SqliteToMysqlConv;

public class DbData {
	private int SrNo;
	private String Parameter;
	private String Values;
	private String InsertTime;
	private String ModifyTime;
	private String EmailDate;
	private String EmailTime;
	private String UnitID;
	private String DbFileName;
	
	
	public String getDbFileName() {
		return DbFileName;
	}
	public void setDbFileName(String dbFileName) {
		DbFileName = dbFileName;
	}
	public String getUnitID() {
		return UnitID;
	}
	public void setUnitID(String unitID) {
		UnitID = unitID;
	}
	public String getEmailDate() {
		return EmailDate;
	}
	public void setEmailDate(String emailDate) {
		EmailDate = emailDate;
	}
	public String getEmailTime() {
		return EmailTime;
	}
	public void setEmailTime(String emailTime) {
		EmailTime = emailTime;
	}
	public int getSrNo() {
		return SrNo;
	}
	public void setSrNo(int srNo) {
		SrNo = srNo;
	}
	public String getParameter() {
		return Parameter;
	}
	public void setParameter(String parameter) {
		Parameter = parameter;
	}
	public String getValues() {
		return Values;
	}
	public void setValues(String values) {
		Values = values;
	}
	public String getInsertTime() {
		return InsertTime;
	}
	public void setInsertTime(String insertTime) {
		InsertTime = insertTime;
	}
	public String getModifyTime() {
		return ModifyTime;
	}
	public void setModifyTime(String modifyTime) {
		ModifyTime = modifyTime;
	}
	@Override
	public String toString() {
		return "DbData [SrNo=" + SrNo + ", Parameter=" + Parameter + ", Values=" + Values + ", InsertTime=" + InsertTime
				+ ", ModifyTime=" + ModifyTime + ", EmailDate=" + EmailDate + ", EmailTime=" + EmailTime + ", UnitID="
				+ UnitID + ", DbFileName=" + DbFileName + "]";
	}
	
	

}
