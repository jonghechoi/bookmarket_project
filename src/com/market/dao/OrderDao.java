package com.market.dao;

import com.market.vo.OrderVo;

public class OrderDao extends DBConn{
	public int insert(OrderVo orderVo) {
		int total_count = 0;
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
				int result = stmt.executeUpdate(sql);
				total_count += result;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return total_count;
	}
}