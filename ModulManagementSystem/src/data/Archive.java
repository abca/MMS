package data;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import com.mysql.jdbc.Connection;


public class Archive extends KillConnections {

	private static Connection con;
	private static final String SAVEFILE = "INSERT INTO archiv VALUES(?, ?, ?, ?, ?)";
	private static final String GETARCHIDS = "SELECT id FROM archiv WHERE dekID=?";
	private static final String GETARCHBOOKNAMES = "SELECT * FROM archiv WHERE dekID=?";
	private static final String GETARCHYEARS = "SELECT time FROM archiv WHERE id=?";
	private static final String GETBOOK = "SELECT path FROM archiv WHERE id=?";
	
	public Archive(){
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			con =  (Connection) DriverManager.getConnection("jdbc:mysql://localhost/MMS", "root", "root");
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}

	public void saveFile(int local, String path, String time, String name, int userid) {
	
		PreparedStatement psmt = null;

		try {
			con.setAutoCommit(false);
			psmt = con.prepareStatement(SAVEFILE);
			
			psmt.setInt(1, local);
			psmt.setString(2, path);
			psmt.setString(3,  time);
			psmt.setString(4, name);
			psmt.setInt(5, userid);
			psmt.executeUpdate();
			con.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnections(null, psmt);
		}
	}	
	
	public String[] getBookNames(int userid){
		
		LinkedList<String> tmp = new LinkedList<String>();
		PreparedStatement psmt = null;
		ResultSet data = null;

		try {
			con.setAutoCommit(false);

			psmt = con.prepareStatement(GETARCHBOOKNAMES);
			psmt.setInt(1,userid);
			
			data = psmt.executeQuery();

			while (data.next()) {
				String tmp2;
				int id = data.getInt("id");
				String path = data.getString("path");
				String time = data.getString("time");
				String name = data.getString("name");
				int dekID = data.getInt("dekID");
				String year = getBookYears(id);
				tmp2 = name + " " + year;
				tmp.add(tmp2);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnections(data, psmt);
		}
		String[] list = tmp.toArray(new String[0]);
		return list;
	}

	private String getBookYears(int id) {
	
		String locyear = "";
		PreparedStatement psmt = null;
		ResultSet data = null;
		
		try{
			con.setAutoCommit(false);

			psmt = con.prepareStatement(GETARCHYEARS);
			psmt.setInt(1, id);
			
			data = psmt.executeQuery();
			data.next();
			locyear = data.getString("time");
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnections(data, psmt);
		}
		return locyear;
	}
	
	public LinkedList<Integer> getBookID(int user){
		
		LinkedList<Integer> arr = new LinkedList<Integer>();
		PreparedStatement psmt = null;
		ResultSet data = null;

		try {
			con.setAutoCommit(false);
			
			psmt = con.prepareStatement(GETARCHIDS);
			psmt.setInt(1,user);
			
			data = psmt.executeQuery();
			
			while(data.next()){
				int id = data.getInt("id");
				arr.add(id);
			}
			} catch (SQLException e) {
			e.printStackTrace();
			}finally {
				closeConnections(data, psmt);
			}
			return arr;
		}

	public String getBook(int book) {
		
		String path = "";
		PreparedStatement psmt = null;
		ResultSet data = null;
		
		try{
			con.setAutoCommit(false);

			psmt = con.prepareStatement(GETBOOK);
			psmt.setInt(1, book);
			
			data = psmt.executeQuery();
			data.next();
			path = data.getString("path");
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnections(data, psmt);
		}
		return path;
	}
}
