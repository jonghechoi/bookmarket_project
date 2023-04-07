package com.market.book_market2;

public interface CartMgmOperation {
	public boolean insert(BookVo book);
	public void showList();
	public void remove();
	public boolean remove(String isbn);
}
