package com.market.book_market2;

import java.util.Calendar;

public class OrderUserVo extends UserVo {
	String address, date;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDate() {
		Calendar cal = Calendar.getInstance();
		date = (cal.get((Calendar.MONTH))+1)
				+"/" + cal.get(Calendar.DAY_OF_MONTH)
				+"/" + cal.get(Calendar.YEAR);
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
}
