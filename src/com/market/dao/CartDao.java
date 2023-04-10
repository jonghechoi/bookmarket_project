package com.market.dao;

import java.util.ArrayList;

import com.market.vo.BookVo;
import com.market.vo.CartVo;

public class CartDao extends DBConn{
	// CartAddItemPage에서 ‘장바구니 담기’ 버튼 눌렀을 때 CartVo → CartDao를 이용해서 DB에 저장
	public int insert(CartVo cartVo) {
		int result = 0;
		StringBuffer sb = new StringBuffer(100);
		
		sb.append("INSERT INTO BOOKMARKET_CART" + 
				"  VALUES('C_'||LTRIM(TO_CHAR(SEQU_BOOKMARKET_CART_CID.NEXTVAL,'0000'))," + 
				"          SYSDATE, ?, ?, ?)");
		try {
			getPreparedStatement(sb.toString());
			pstmt.setInt(1, cartVo.getQty()+1);
			pstmt.setString(2, cartVo.getIsbn());
			pstmt.setString(3, cartVo.getMid());
			result = pstmt.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public int insertCheck(CartVo cartVo) {
		int result = 0;
		
		StringBuffer sb = new StringBuffer(100);
		sb.append("Select COUNT(*) FROM BOOKMARKET_CART"
				+ "	WHERE MID = ?"
				+ " AND ISBN = ?");
		
		try {
			getPreparedStatement(sb.toString());
			pstmt.setString(1, cartVo.getMid());
			pstmt.setString(2, cartVo.getIsbn());
			rs = pstmt.executeQuery();
			while(rs.next()) result = rs.getInt(1);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public int getSize(String mid) {
		int result = 0;
		StringBuffer sb = new StringBuffer(100);
		sb.append("Select COUNT(*) FROM BOOKMARKET_CART"
				+ " WHERE MID=?");
		
		try {
			getPreparedStatement(sb.toString());
			pstmt.setString(1, mid);
			rs = pstmt.executeQuery();
			while(rs.next()) result = rs.getInt(1);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public ArrayList<CartVo> select(String mid) {
		ArrayList<CartVo> list = new ArrayList<CartVo>();
		StringBuffer sb = new StringBuffer(100);
		sb.append("SELECT ROWNUM RNO, CID, CDATE, ISBN, MID, TITLE, QTY, PRICE, "
				+ " TO_CHAR(PRICE, 'L999,999') SPRICE, "
				+ " TO_CHAR(PRICE, 'L999,999') STOTAL_PRICE" 
				+ " FROM (SELECT C.CID CID, C.CDATE CDATE, C.ISBN ISBN, C.MID MID, B.TITLE TITLE, "
				+ "		   C.QTY QTY, B.PRICE PRICE, C.QTY*B.PRICE TOTAL_PRICE\r\n" 
				+ "        FROM BOOKMARKET_CART C, BOOKMARKET_MEMBER M, BOOKMARKET_BOOK B\r\n"
				+ "        WHERE C.MID=M.MID AND C.ISBN=B.ISBN)"
				+ " WHERE MID=?");		
		try {
			getPreparedStatement(sb.toString());
			pstmt.setString(1, mid);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				CartVo cart = new CartVo();
				cart.setRno(rs.getInt(1));
				cart.setCid(rs.getString(2));
				cart.setCdate(rs.getString(3));
				cart.setIsbn(rs.getString(4));
				cart.setMid(rs.getString(5));
				cart.setTitle(rs.getString(6));
				cart.setQty(rs.getInt(7));
				cart.setPrice(rs.getInt(8));
				cart.setTotal_price(rs.getInt(7)*rs.getInt(8));
				cart.setSprice(rs.getString(9));
				cart.setStotal_price(rs.getString(10));
				list.add(cart);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public boolean delete(String rno) {
		boolean result = false;
		StringBuffer sb = new StringBuffer();
		sb.append("DELETE FROM BOOKMARKET_CART"
				+ " WHERE ISBN=?");
		
		try {
			getPreparedStatement(sb.toString());
			pstmt.setString(1, rno);
			int num = pstmt.executeUpdate();
			if(num != 0) result = true;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
}