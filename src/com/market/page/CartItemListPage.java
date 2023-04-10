package com.market.page;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import com.market.book_market2.CartItemVo;
import com.market.book_market2.CartMgm;
import com.market.commons.MakeFont;
import com.market.dao.BookDao;
import com.market.dao.CartDao;
import com.market.dao.DBConn;
import com.market.dao.MemberDao;
import com.market.main.MainWindow;
import com.market.vo.CartVo;

public class CartItemListPage extends JPanel {
	JTable cartTable;
	Object[] tableHeader = { "번호", "도서ID", "도서명", "단가", "수량", "총가격" };
	CartMgm cm;
	MemberDao memberDao;
	BookDao bookDao;
	CartDao cartDao;
	
	public static int mSelectRow = -1;

	public CartItemListPage(JPanel panel, Map<String, DBConn> daoList) {
		this.memberDao = (MemberDao)daoList.get("memberDao");
		this.bookDao = (BookDao)daoList.get("bookDao");
		this.cartDao = (CartDao)daoList.get("cartDao");
		
		this.setLayout(null);
		Rectangle rect = panel.getBounds();
		this.setPreferredSize(rect.getSize());

		JPanel bookPanel = new JPanel();
		bookPanel.setBounds(0, 0, 1000, 400);
		add(bookPanel);
		
		ArrayList<CartVo> cartItemList = cartDao.select(MainWindow.member.getMid().toUpperCase());
		Object[][] content = new Object[cartItemList.size()][tableHeader.length];
		Integer totalPrice = 0;
		for (int i = 0; i < cartItemList.size(); i++) {
			CartVo item = cartItemList.get(i);
			content[i][0] = item.getRno();
			content[i][1] = item.getIsbn();
			content[i][2] = item.getTitle();
			content[i][3] = item.getStotal_price();
			content[i][4] = item.getQty();
			content[i][5] = item.getTotal_price() * item.getQty(); 
			totalPrice += item.getTotal_price() * item.getQty();
		}

		cartTable = new JTable(content, tableHeader);
		JScrollPane jScrollPane = new JScrollPane();
		jScrollPane.setPreferredSize(new Dimension(600, 350));
		jScrollPane.setViewportView(cartTable);
		bookPanel.add(jScrollPane);

		JPanel totalPricePanel = new JPanel();
		totalPricePanel.setBounds(0, 400, 1000, 50);
		JLabel totalPricelabel = new JLabel("총금액: " + totalPrice + " 원");
		totalPricelabel.setForeground(Color.red);
//		totalPricelabel.setFont(ft);
		MakeFont.getFont(totalPricelabel);
		totalPricePanel.add(totalPricelabel);
		add(totalPricePanel);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());
		buttonPanel.setBounds(0, 450, 1000, 50);
		add(buttonPanel);

		JLabel buttonLabel = new JLabel("장바구니 비우기");
//		buttonLabel.setFont(ft);
		MakeFont.getFont(buttonLabel);
		JButton clearButton = new JButton();
		clearButton.add(buttonLabel);
		buttonPanel.add(clearButton);
		
		/* 장바구니 비우기 버튼 이벤트 처리 */
		clearButton.addActionListener(new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<CartItemVo> cartItem = cm.getList();
				if (cm.getSize() == 0)
					JOptionPane.showMessageDialog(clearButton, "장바구니가 비어 있습니다");
				else {
					int select = JOptionPane.showConfirmDialog(clearButton, "정말로 삭제하시겠습니까? ");
					if (select == 0) {
						TableModel tableModel = new DefaultTableModel(new Object[0][0], tableHeader);
						cartTable.setModel(tableModel);
						totalPricelabel.setText("총금액: " + 0 + " 원");

//						cart.deleteBook();
						cm.remove();
						
						JOptionPane.showMessageDialog(clearButton, "삭제가 완료되었습니다");

					}
				}
			}
		});

		JLabel removeLabel = new JLabel("장바구니 항목 삭제하기");
//		removeLabel.setFont(ft);
		MakeFont.getFont(removeLabel);
		JButton removeButton = new JButton();
		removeButton.add(removeLabel);
		buttonPanel.add(removeButton);
		
		/* 장바구니 항목 삭제하기 이벤트 처리 */
		removeButton.addActionListener(new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				if (cartDao.getSize(MainWindow.member.getMid().toUpperCase()) == 0)
					JOptionPane.showMessageDialog(clearButton, "장바구니가 비어있습니다");
				else if (mSelectRow == -1) // 아무 row도 선택하지 않았을 경우
					JOptionPane.showMessageDialog(clearButton, "삭제할 항목을 선택해주세요");
				else {
					String rno = (String)cartTable.getValueAt(mSelectRow, 1); // RNO 번호로 삭제
					boolean result = cartDao.delete(rno);
					
					if(!result) {
						JOptionPane.showMessageDialog(null, "삭제에 실패했습니다. 재시도해주세요.");
					}else {
						ArrayList<CartVo> cartList = cartDao.select(MainWindow.member.getMid().toUpperCase());
						Object[][] content = new Object[cartList.size()][tableHeader.length];
						Integer totalPrice = 0;
						for (int i = 0; i < cartList.size(); i++) {
							CartVo item = cartList.get(i);
							content[i][0] = item.getRno();
							content[i][1] = item.getIsbn();
							content[i][2] = item.getTitle();
							content[i][3] = item.getStotal_price();
							content[i][4] = item.getQty();
							content[i][5] = item.getTotal_price() * item.getQty(); 
							totalPrice += item.getTotal_price() * item.getQty();
						}
						TableModel tableModel = new DefaultTableModel(content, tableHeader);
						totalPricelabel.setText("총금액: " + totalPrice + " 원");
						cartTable.setModel(tableModel);
					}
					mSelectRow = -1; // 마우스 커서 해제
				}
			}
		});

		cartTable.addMouseListener(new MouseListener() {

			public void mouseClicked(MouseEvent e) {
				int row = cartTable.getSelectedRow();
				mSelectRow = row;
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
			}

		});

		JLabel updateQtyLabel = new JLabel("장바구니 항목 수량 줄이기");
//		refreshLabel.setFont(ft);
		MakeFont.getFont(updateQtyLabel);
		JButton updateQtyButton = new JButton();
		updateQtyButton.add(updateQtyLabel);
		buttonPanel.add(updateQtyButton);

		updateQtyButton.addActionListener(new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				if(cm.getSize() == 0) {
					JOptionPane.showMessageDialog(null, "장바구니가 비어있습니다");
				}else if(mSelectRow == -1) { 
					JOptionPane.showMessageDialog(null, "수정할 항목을 선택해주세요");
				}else {
					ArrayList<CartItemVo> cartItem = cm.getList();
					int qty = cartItem.get(mSelectRow).getQty();
					if(qty > 1) {
						// 수량이 1보다 크므로 수량 -1씩 조절 가능
						cm.updateQty(cartItem.get(mSelectRow).getIsbn());
						
						Object[][] content = new Object[cartItem.size()][tableHeader.length];
						Integer totalPrice = 0;
						for (int i = 0; i < cartItem.size(); i++) {
							CartItemVo item = cartItem.get(i);
							content[i][0] = item.getIsbn();
							content[i][1] = item.getTitle();
							content[i][2] = item.getTotalPrice();
							content[i][3] = item.getQty();
							content[i][4] = item.getTotalPrice() * item.getQty(); 
							totalPrice += item.getTotalPrice() * item.getQty(); 
						}
						TableModel tableModel = new DefaultTableModel(content, tableHeader);
						totalPricelabel.setText("총금액: " + totalPrice + " 원");
						cartTable.setModel(tableModel);
						mSelectRow = -1;
					}else {
						// 수량이 <=1이므로 수량 조절 불가능
						JOptionPane.showMessageDialog(null, "2개 이상인 경우에만 수정 가능합니다");
					}
				}
			}
		});
	}
	
}