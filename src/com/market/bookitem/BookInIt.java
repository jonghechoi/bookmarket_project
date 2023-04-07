package com.market.bookitem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import com.market.book_market2.BookVo;

public class BookInIt {
//	private static ArrayList<Book> mBookList;
	private static ArrayList<BookVo> mBookList;
	private static int mTotalBook = 0;

	public static void init() {
		mTotalBook = totalFileToBookList();
//		mBookList = new ArrayList<Book>();
		mBookList = new ArrayList<BookVo>();
		setFileToBookList(mBookList);
	}

	public static int totalFileToBookList() {
		try {
			FileReader fr = new FileReader("book.txt"); // 
			// Reads text from a character-input stream, buffering characters so
			// as to provide for the efficient reading of characters, arrays, and lines.
			BufferedReader reader = new BufferedReader(fr); 

			String str;
			int num = 0;
			while ((str = reader.readLine()) != null) {
				if (str.contains("ISBN"))
					++num;
			}
			reader.close();
			fr.close();
			return num;
		} catch (Exception e) {
			System.out.println(e);
		}
		return 0;
	}

	public static void setFileToBookList(ArrayList<BookVo> booklist) {
		try {
			FileReader fr = new FileReader("book.txt");
			BufferedReader reader = new BufferedReader(fr);

			String str2;
//			String[] readBook = new String[7];

			while ((str2 = reader.readLine()) != null) {
				BookVo book = new BookVo();
				if (str2.contains("ISBN")) {
//					readBook[0] = str2;
//					readBook[1] = reader.readLine();
//					readBook[2] = reader.readLine();
//					readBook[3] = reader.readLine();
//					readBook[4] = reader.readLine();
//					readBook[5] = reader.readLine();
//					readBook[6] = reader.readLine();
					book.setIsbn(str2);
					book.setTitle(reader.readLine());
					book.setPrice(Integer.parseInt(reader.readLine()));
					book.setAuthor(reader.readLine());
					book.setDesc(reader.readLine());
					book.setBfield(reader.readLine());
					book.setPdate(reader.readLine());
				}
				booklist.add(book);
				
//				Book bookitem = new Book(readBook[0], readBook[1], Integer.parseInt(readBook[2]), readBook[3],
//						readBook[4], readBook[5], readBook[6]);
//				booklist.add(bookitem);
			}
			reader.close();
			fr.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static ArrayList<BookVo> getmBookList() {
		return mBookList;
	}

	public static void setmBookList(ArrayList<BookVo> mBookList) {
		BookInIt.mBookList = mBookList;
	}

	public static int getmTotalBook() {
		return mTotalBook;
	}

	public static void setmTotalBook(int mTotalBook) {
		BookInIt.mTotalBook = mTotalBook;
	}
}