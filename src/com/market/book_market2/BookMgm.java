package com.market.book_market2;
import java.util.ArrayList;


/*
 * ���� ���/����/������Ʈ �� ������ ���õ� ��� �۾��� BookMgm Ŭ�������� ����
 * 
 */
public class BookMgm {
	// Field
	ArrayList<BookVo> bookList;
	
	// Constructor
	public BookMgm() {
		bookList = new ArrayList<BookVo>();
		createList();
	}
	
	/*
	 * orderIsbn�� ���� �����Ͱ� ���� ��ٱ��Ͽ� �ִ��� �˻�
	 * �޼ҵ�� �׻� �� �����Ͱ� �ִ� ������ ����Ǿ�� ��
	 */
	public BookVo search(String isbn) {
		BookVo book = new BookVo();
		for(BookVo sbook : bookList) {
			if(sbook.getIsbn().equals(isbn)) {
				book = sbook;
				break;
			}else {
				book = null;
			}
		}
		return book; // ������ null �� ��ȯ 
	}
	
	
	/*
	 * ���� ����Ʈ ���
	 */
	public void showList() {
		System.out.println("*****************************************");
		System.out.println("\t���� ���� ���� ����Ʈ");
		System.out.println("*****************************************");
		for(BookVo book : bookList) {
			System.out.print(book.getIsbn() + " | ");
			System.out.print(book.getTitle() + "\t | ");
			System.out.print(book.getAuthor() + " | ");
			System.out.print(book.getPrice() + " | \n");
		}
		System.out.println("*****************************************");
	}
	
	
	/*
	 * ���� �߰�
	 */
	public void createList() {
		String[] titleList = {"Just Java", "����Ŭ SQL ���", "JSP ���α׷���", "������ �� ��ŸƮ"};
		String[] authorList = {"Ȳ����", "ȫ����", "�ֹ���", "ȫ�浿"};
		String[] isbnList = {"ISBN1234", "ISBN5678", "ISBN8901", "ISBN2345"};
		int[] priceList = {10000,20000,30000,40000};
		
		// bookList�� ���� ���� �߰�
		for(int i=0; i<titleList.length; i++) {
			BookVo bookvo = new BookVo();
			
			bookvo.setTitle(titleList[i]);
			bookvo.setAuthor(authorList[i]);
			bookvo.setIsbn(isbnList[i]);
			bookvo.setPrice(priceList[i]);
			bookList.add(bookvo);
		}
		
		System.out.println("��ǰ��� �߰� �Ϸ�");
	}
	
}
