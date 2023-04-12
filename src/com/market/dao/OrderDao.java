package com.market.dao;

import com.market.vo.OrderVo;

public class OrderDao extends DBConn{
	/* 데이터 추가 - PreparedStatement --> 비추 */
	public int insertPrepared(OrderVo orderVo) {
		int result = 0;
		StringBuffer sb = new StringBuffer(100);
		sb.append("INSERT INTO BOOKMARKET_ORDER");
		sb.append(" VALUES(?,?,?,?,?,?,?,?)");
		
		try {
			getPreparedStatement(sb.toString());
			for(int i=0; i<orderVo.getQtyList().length; i++) {
				pstmt.setString(1, orderVo.getOid());
				pstmt.setString(2, orderVo.getOdate());
				pstmt.setInt(3, orderVo.getQtyList()[i]);
				pstmt.setString(4, orderVo.getIsbnList()[i]);
				pstmt.setString(5, orderVo.getMid());
				pstmt.setString(6, orderVo.getName());
				pstmt.setString(7, orderVo.getPhone());
				pstmt.setString(8, orderVo.getAddr());
				pstmt.addBatch();
				pstmt.clearParameters(); // 위에서 입력된 파라미터들을 clear
			}
			// 위에서 preparedStatement로 데이터 쌓고 executeBatch()에서 Statement 리턴함
			// 결국 Statement 이용하는 것. 속도 측면에서는 이게 불리하나 comma에서 자유로움... --> trade-off
			result = pstmt.executeBatch().length;
			pstmt.clearParameters();
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
		return result;
	}
	
	
	
	/* 데이터 추가 - Statement --> 추천 */ 
	public int insert(OrderVo orderVo) {
		int result = 0;
		getStatement();
		
		try {
			for(int i=0; i<orderVo.getQtyList().length; i++) {
				 String sql = "INSERT INTO BOOKMARKET_ORDER"
						+ " VALUES(" 
						+ "'" + orderVo.getOid() + "'," 
						+ "'" + orderVo.getOdate() + "'," 
						+ "'" + orderVo.getQtyList()[i] + "',"
						+ "'" + orderVo.getIsbnList()[i] + "'," 
						+ "'" + orderVo.getMid() + "'," 
						+ "'" + orderVo.getName() + "'," 
						+ "'" + orderVo.getPhone() + "'," 
						+ "'" + orderVo.getAddr() + "')";
				result = stmt.executeUpdate(sql);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}