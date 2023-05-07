package user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {
	
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	// 실제로 mysql에 접속을 하게 해주는 부분
	
	public UserDAO() {
		try {
			String DRIVER = "com.mysql.jdbc.Driver";
			String dbURL = "jdbc:mysql://localhost:3306/real_AML?serverTimezone=UTC";
			String dbID = "root";
			String dbPassword = "0929";
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL, dbID, dbPassword);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 하나의 계정에 대한 로그인 시도를 해주는 함수
	public int login(String userID, String userPassword) {
		String SQL = "SELECT userPassword FROM USER WHRER userID = ?";
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1,  userID);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				if(rs.getString(1).equals(userPassword)) {
					return 1; // 로그인 성공
				}
				else 
					return 0; // 비밀번호 불일치
			}
			return -1; //아이디가 없음
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -2; // 데이터 베이스 오류를 의미
	}
}
