package com.market.dao;

import java.util.ArrayList;

import com.market.vo.BookVo;

// 도서 db dao
public class BookDao extends DBConn {
	/**
	 * 도서 전체 리스트 조회
	 */
	public ArrayList<BookVo> select() {
		ArrayList<BookVo> list = new ArrayList<BookVo>();
		StringBuffer sb = new StringBuffer(100);
		sb.append("SELECT * FROM BOOKMARKET_BOOK ORDER BY BDATE DESC");
		
		try {
			getPreparedStatement(sb.toString());
			rs = pstmt.executeQuery();
			while(rs.next()) {
				BookVo book = new BookVo();
				book.setIsbn(rs.getString(1));
				book.setTitle(rs.getString(2));
				book.setPrice(rs.getInt(3));
				book.setAuthor(rs.getString(4));
				book.setIntro(rs.getString(5));
				book.setPart(rs.getString(6));
				book.setPdate(rs.getString(7));
				book.setImg(rs.getString(8));
				book.setBdate(rs.getString(9));
				list.add(book);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
}
