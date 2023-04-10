package com.market.dao;

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
}