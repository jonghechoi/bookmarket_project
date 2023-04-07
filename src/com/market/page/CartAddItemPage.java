package com.market.page;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.market.vo.BookVo;
import com.market.book_market2.CartMgm;
import com.market.bookitem.BookInIt;
import com.market.commons.MakeFont;
import com.market.dao.BookDao;

public class CartAddItemPage extends JPanel {
	ImageIcon imageBook;
	int mSelectRow = 0;
	CartMgm cm;
	
	public CartAddItemPage(JPanel panel, CartMgm cm, BookDao bookDao) {
		this.cm = cm;
		setLayout(null);

		Rectangle rect = panel.getBounds();
		System.out.println(rect);
		setPreferredSize(rect.getSize());

		JPanel imagePanel = new JPanel();
		imagePanel.setBounds(20, 0, 300, 400);
		imageBook = new ImageIcon("./images/ISBN1234.jpg");
		imageBook.setImage(imageBook.getImage().getScaledInstance(250, 300, Image.SCALE_DEFAULT));
		JLabel label = new JLabel(imageBook);
		imagePanel.add(label);
		add(imagePanel);

		JPanel tablePanel = new JPanel();
		tablePanel.setBounds(300, 0, 700, 400);
		add(tablePanel);

//		ArrayList<BookVo> booklist = BookInIt.getmBookList();
		ArrayList<BookVo> booklist = bookDao.select();
		Object[] tableHeader = { "도서ID", "도서명", "가격", "저자", "설명", "분야", "출판일" };
		Object[][] content = new Object[booklist.size()][tableHeader.length];
		for (int i = 0; i < booklist.size(); i++) {
			BookVo bookitem = booklist.get(i);
			content[i][0] = bookitem.getIsbn();
			content[i][1] = bookitem.getTitle();
			content[i][2] = bookitem.getPrice();
			content[i][3] = bookitem.getAuthor();
			content[i][4] = bookitem.getIntro();
			content[i][5] = bookitem.getPart();
			content[i][6] = bookitem.getPdate();
			
		}

		JTable bookTable = new JTable(content, tableHeader);
		bookTable.setRowSelectionInterval(0, 0);
		bookTable.getSelectedColumn();
		JScrollPane jScrollPane = new JScrollPane();
		jScrollPane.setPreferredSize(new Dimension(600, 350));
		jScrollPane.setViewportView(bookTable);
		tablePanel.add(jScrollPane);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setBounds(0, 400, 1000, 400);
		add(buttonPanel);
		JLabel buttonLabel = new JLabel("장바구니 담기");
//		buttonLabel.setFont(ft);
		MakeFont.getFont(buttonLabel);
		JButton addButton = new JButton();
		addButton.add(buttonLabel);
		buttonPanel.add(addButton);

		/* 마우스 이벤트 처리 */
		// 마우스로 책 리스트 클릭하면 이미지 변할 수 있게 함
		bookTable.addMouseListener(new MouseListener() {

			public void mouseClicked(MouseEvent e) {
				int row = bookTable.getSelectedRow();
				int col = bookTable.getSelectedColumn();
				mSelectRow = row;
				Object value = bookTable.getValueAt(row, 0);
				String str = value + ".jpg";

				imageBook = new ImageIcon("./images/" + str);
				imageBook.setImage(imageBook.getImage().getScaledInstance(250, 300, Image.SCALE_DEFAULT));
				JLabel label = new JLabel(imageBook);
				imagePanel.removeAll();
				imagePanel.add(label);
				imagePanel.revalidate();
				imagePanel.repaint();
			}

			@Override
			public void mousePressed(MouseEvent e) {}

			@Override
			public void mouseReleased(MouseEvent e) {}

			@Override
			public void mouseEntered(MouseEvent e) {}

			@Override
			public void mouseExited(MouseEvent e) {}
		});

		/* 마우스 담기 이벤트 처리 */
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				ArrayList<BookVo> booklist = BookInIt.getmBookList();
//
//				int select = JOptionPane.showConfirmDialog(addButton, "장바구니에 추가하시겠습니까?");
//				if (select == 0) { // 확인 버튼 클릭시
//					int idx = mSelectRow; // JTable에 출력된 목록중 마우스로 클릭된 row의 index
//					
//					if(cm.insert(booklist.get(idx))) {
//						JOptionPane.showMessageDialog(addButton, "장바구니에 추가되었습니다.");
//					}
//					System.out.println("size -->"+ cm.getSize());
//					cm.showList();
//				}
			}
		});

	}
}