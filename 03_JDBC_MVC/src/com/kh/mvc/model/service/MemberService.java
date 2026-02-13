package com.kh.mvc.model.service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static com.kh.mvc.common.template.JDBCTemplate.*;
import com.kh.mvc.dao.MemberDao;
import com.kh.mvc.model.vo.Member;

/* 
 * Service
 *  - 컨트롤러에 의해 호출되는 최초의 메서드
 *  - 여러 dao에 존재하는 메서드들을 호출하여 논리적으로 연관이 있는 비즈니스 로직을 만든다
 *  - 처리결과값을 컨트롤러에게 반환해주는 역할을 한다.
 * */
public class MemberService {

	private MemberDao mDao = new MemberDao();

	public int insertMember(Member m) {
		// Connection 객체 생성
		int result = 0;
		try {
			Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "JDBC", "JDBC");
			conn.setAutoCommit(false);

			result = mDao.insertMember(conn, m);

			if (result > 0) {
				conn.commit();
			} else {
				conn.rollback();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public List<Member> selectAll() {
		Connection conn = getConnection();
		List<Member> list = mDao.selectAll(conn);
		close(conn);
		// -?
		return list;
	}

	public Member selectByUserId(String userId) {
		Connection conn = getConnection();
		Member m = mDao.selectByUserId(conn, userId);
		close(conn);
		return m;
	}

	public List<Member> selectByUserName(String keyword) {
		Connection conn = getConnection();
		List<Member> list = mDao.selectByUserName(conn, keyword);
		close(conn);
		return list;
	}

	public int updateMember(Member m) {
		Connection conn = getConnection();
		int result = mDao.updateMember(conn, m);
		
		if(result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		
		close(conn);
		return result;
	}

	public int deleteMember(String userId) {
		Connection conn = getConnection();
		int result = mDao.deleteMember(conn, userId);
		
		if (result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		close(conn);
		return result;
	}

	
}
