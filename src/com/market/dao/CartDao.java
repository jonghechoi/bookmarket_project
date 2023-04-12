package com.market.dao;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.market.vo.CartVo;
import com.market.vo.OrderVo;

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
	
	public void delete(String rno) {
		StringBuffer sb = new StringBuffer();
		sb.append("DELETE FROM BOOKMARKET_CART"
				+ " WHERE RNO=?");
		
		try {
			getPreparedStatement(sb.toString());
			pstmt.setString(1, rno);
			pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void delete(String mid, String clear) {
		StringBuffer sb = new StringBuffer();
		sb.append("DELETE FROM BOOKMARKET_CART"
				+ " WHERE MID=?");
		
		try {
			getPreparedStatement(sb.toString());
			pstmt.setString(1, mid);
			pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/* 선택한 ITEM 수량 수정하기 */
	public void updateQty(String cid, String status) {
		StringBuffer sb = new StringBuffer(100);
		if(status.equals("plus")) {
			sb.append("UPDATE BOOKMARKET_CART"
					+ " SET QTY=(QTY+1)"
					+ " WHERE CID=?");
		}else {
			sb.append("UPDATE BOOKMARKET_CART"
					+ " SET QTY=(QTY-1)"
					+ " WHERE CID=?");
		}
		
		try {
			getPreparedStatement(sb.toString());
			pstmt.setString(1, cid);
			pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/* '주문확정'을 위해 Cart 테이블에서 qty,isbn리스트 추출 후 OrderVo 리턴 */
	// Cart 테이블에는 여러 멤버의 장바구니 아이템이 있음 -> mid로 추출 필요
	public OrderVo getOrderVo(String mid) {
		System.out.println("여기까지 진행2222222222222");
		OrderVo orderVo = new OrderVo();
		StringBuffer sb = new StringBuffer(50);
		sb.append("SELECT QTY, ISBN FROM BOOKMARKET_CART"
				+ " WHERE MID=?"
				+ " ORDER BY CDATE DESC");
		
		try {
			getPreparedStatement(sb.toString());
			pstmt.setString(1, mid);
			// PreparedStatement가 생성될 때 커서를 움직일 수 있는 조건을 미리 걸어줘야함
			// conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, 
			// ResultSet.CONCUR_UPDATABLE)
			
			rs = pstmt.executeQuery(); 

			// ResultSet에서 전체 row수를 반환하는 메소드가 없는 이유??
			// 테이블 내에서 커서이동을 허락하는 메소드의 default는 false이다
			// 왜 default? 자바에서 이뤄지는게 아닌 DB에서 커서를 옮길때마다 rs가 생성되어지므로
			// 데이터 양이 많을때에는 부하가 발생해서
			// 위에 있는 것처럼 조건 걸어서 풀어줘야함 -> 권장 X
			rs.last();
			
			int[] qtyList = new int[rs.getRow()];
			String[] isbnList = new String[rs.getRow()];
			rs.beforeFirst();
			
			int idx = 0;
			while(rs.next()) {
				qtyList[idx] = rs.getInt(1);
				isbnList[idx] = rs.getString(2);
				idx++;
			}
			orderVo.setQtyList(qtyList); 
			orderVo.setIsbnList(isbnList);
		}catch(Exception e) {
			e.printStackTrace();
		}

		
		return orderVo;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}