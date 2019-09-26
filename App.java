package com.sqlitedb.demo.SqliteToMysqlConv;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.sql.*;

/**
 * Hello world!
 *
 */
public class App 
{
	
	 Connection Sqliteconn[];
	 Connection Mysqlconn=null;
	 Connection Mysqlconn2=null;
	 
	 String fname=null;
	 String att_name[];
	 String dbfname[];
	 String emaildate[];
	 String emailtime[];
	 String parameter[];
		String val[];
		
	 
	 String email_date;
	 String email_time;
	 List<String> file_nameList=new ArrayList<String>();
	 int count=0;
	 
	List<DbData> dbdataList=null;

	static int filecount=0;
	
	
	public  App() throws ClassNotFoundException, SQLException {
		super();

			// TODO Auto-generated constructor stub
			//Sqlite connection
			//String sqliteURL="jdbc:sqlite:/home/ganesh/EmailAttachment/DDCANDatabase.db";
		int i=0;
		String dbfiles;
		File dir = new File("/home/ganesh/EmailAttachment/");
		  File[] directoryListing = dir.listFiles();
		  if (directoryListing != null) {
			  att_name=new String[directoryListing.length];
			emaildate=new String[directoryListing.length];
			emailtime=new String[directoryListing.length];
			dbfname=new String[directoryListing.length];
			
		    for (File child : directoryListing) {
		      // Do something with child
		    	dbfiles=child.getName();
		    	if(dbfiles.contains(".db")) {
			    	filecount++;

		    	att_name[i]=child.getName();
		    	dbfname[i]=att_name[i].substring(39);
		    	emaildate[i]=att_name[i].substring(9, 19);
		    	emailtime[i]=att_name[i].substring(28, 36);
		    	//att_name[i]=child.getName();
		    	fname=child.getName();
		    	file_nameList.add(fname);
		    	//System.out.println("child"+fname);
		    i++;
		    	}
		    	else {
		    		continue;
		    	}
		    }
		  } else {
			  System.out.println("FileCount="+filecount);
		    // Handle the case where dir is not really a directory.
		    // Checking dir.isDirectory() above would not be sufficient
		    // to avoid race conditions with another process that deletes
		    // directories.
		  }
		  count=filecount;
		  Sqliteconn=new Connection[i];

		  for(int fcount=1; fcount<i;fcount++) {
			String dbname=att_name[fcount];
			  //System.out.println(att_name[fcount]);
		  System.out.println(emaildate[fcount]+"&"+emailtime[fcount]);
		  
		  String sqliteURL="jdbc:sqlite:/home/ganesh/EmailAttachment/"+dbname;
		  
			
			try {
				
				Sqliteconn[fcount]=DriverManager.getConnection(sqliteURL);
				System.out.println("Connected to Sqlite "+sqliteURL+fcount);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally {
				//conn.close();
			}
		  }
			//Mysql Connection
			
			String mysqlUrl="jdbc:mysql://localhost:3306/VehicleData";
			String user="root";
			String password="pass";	

			 try {
					Class.forName("com.mysql.cj.jdbc.Driver");
					Mysqlconn=DriverManager.getConnection(mysqlUrl,user,password);
					System.out.println("Connected to Mysql");
					
					
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 finally {
				 //Mysqlconn.close();
			 }
			 
//			 String mysqlUrl2="jdbc:mysql://localhost:3306/VehicleData";
//				String user2="root";
//				String password="pass";	

				 try {
						Class.forName("com.mysql.cj.jdbc.Driver");
						Mysqlconn2=DriverManager.getConnection(mysqlUrl,user,password);
						System.out.println("Connected to Mysql2");
						
						
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
System.out.println(file_nameList.size());

	}

	
	public List<DbData> getDatafromSqlite() throws SQLException {
		
		dbdataList= new ArrayList<DbData>();

		//String query1="select Parameter from t_CANValues LIMIT 0,30";
		String query1="select * from t_CANValues";
		
		Statement stmt[]=new Statement[count];
		ResultSet rs[]=new ResultSet[count];
		int cc=0;
		parameter=new String[count];
		val=new String[count];
		
		for(int j=1; j<count;j++) {
		try {
			stmt[j] = Sqliteconn[j].createStatement();
			rs[j]=stmt[j].executeQuery(query1);
			while(rs[j].next()) {
				DbData data=new DbData();

				//data.setSrNo(rs.getInt(1));
				data.setParameter(rs[j].getString(2));
				if(rs[j].getString(2).contains("UnitID")) {
					parameter[j]="UnitID";
					val[j]=rs[j].getString(3);
					cc++;
				}
				else {}
				data.setValues(rs[j].getString(3));
				data.setInsertTime(rs[j].getString(4));
				data.setModifyTime(rs[j].getString(5));
				data.setEmailDate(emaildate[j]);
				data.setEmailTime(emailtime[j]);
				data.setUnitID(val[j]);
				data.setDbFileName(dbfname[j]);
				dbdataList.add(data);
				//System.out.println(parameter+" : "+val+cc);
				//System.out.println("----------------data from file"+j);
				System.out.println(data);

				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			Sqliteconn[j].close();
			
		}
		}
		System.out.println("Rows in list"+dbdataList.size());
		return dbdataList;
		
	}
	
	public void sendToMysql() throws SQLException {
		String st="processed";
		String file_name;
		String email_date;
		String email_time;
		String query2="insert into t_dragondroidtrackfile(parameter, value, inserttime, modifytime, emaildate, emailtime, unitid, dbfilename)" + "values(?,?,?,?,?,?,?,?)";
		
		try {
			
			
			for(DbData listrow:dbdataList ) {
				
			PreparedStatement preparedstmt= Mysqlconn.prepareStatement(query2);
			preparedstmt.setString(1, listrow.getParameter());
			preparedstmt.setString(2, listrow.getValues());
			preparedstmt.setString(3, listrow.getInsertTime());
			preparedstmt.setString(4, listrow.getModifyTime());
			preparedstmt.setString(5, listrow.getEmailDate());
			preparedstmt.setString(6, listrow.getEmailTime());
			preparedstmt.setString(7, listrow.getUnitID());
			preparedstmt.setString(8, listrow.getDbFileName());
			file_name=listrow.getDbFileName();
			email_date=listrow.getEmailDate();
			email_time=listrow.getEmailTime();
			

			preparedstmt.execute();
			
			try {
				String query3="update email_attachments set status=? where att_name=? and email_date=? and email_time=?";
				PreparedStatement ps = Mysqlconn2.prepareStatement(query3);
			
				ps.setString(1, st);
				ps.setString(2, file_name);
				ps.setString(3, email_date);
				ps.setString(4, email_time);
				ps.execute();
				
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			}
				
							
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			Mysqlconn2.close();
			Mysqlconn.close();
		}
					
	}
	
	
   

	public static void main( String[] args ) throws ClassNotFoundException, SQLException
    {
				
		
		App obj=new App();
    	obj.getDatafromSqlite();
    	obj.sendToMysql();
    	
    }
}
