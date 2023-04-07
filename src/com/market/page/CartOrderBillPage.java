package com.market.page;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.market.book_market2.CartItemVo;
import com.market.book_market2.CartMgm;
import com.market.book_market2.OrderUserVo;
import com.market.commons.MakeFont;
import com.market.main.GuestWindow;
import com.market.main.MainWindow;

public class CartOrderBillPage extends JPanel {
	CartMgm cm;
	OrderUserVo ouser;
	MainWindow main;
	JPanel shippingPanel;
	JPanel radioPanel;
	JPanel mPagePanel;

	public CartOrderBillPage(JPanel panel, CartMgm cm, OrderUserVo ouser, MainWindow main) {
		this.mPagePanel = panel;
		this.cm = cm;
		this.ouser = ouser;
		this.main = main;
		setLayout(null);

		Rectangle rect = panel.getBounds();
		System.out.println(rect);
		setPreferredSize(rect.getSize());

		shippingPanel = new JPanel();
		shippingPanel.setBounds(0, 0, 700, 500);
		shippingPanel.setLayout(null);
		panel.add(shippingPanel);
		
		printBillInfo(ouser);
	}

	public void printBillInfo(OrderUserVo ouser) {

		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		String strDate = formatter.format(date);

		JPanel panel01 = new JPanel();
		panel01.setBounds(0, 0, 500, 30);
		JLabel label01 = new JLabel("--------------------- 배송 받을 고객 정보 -----------------------");
		MakeFont.getFont(label01);
		panel01.add(label01);
		shippingPanel.add(panel01);

		JPanel panel02 = new JPanel();
		panel02.setBounds(0, 30, 500, 30);
		JLabel label02 = new JLabel("고객명 : " + ouser.getName() + "             연락처 :      " + ouser.getPhoneNumber());
		label02.setHorizontalAlignment(JLabel.LEFT);
		MakeFont.getFont(label02);
		panel02.add(label02);
		shippingPanel.add(panel02);

		JPanel panel03 = new JPanel();
		panel03.setBounds(0, 60, 500, 30);
		JLabel label03 = new JLabel("배송지 : " + ouser.getAddress() + "                 발송일 :       " + strDate);
		label03.setHorizontalAlignment(JLabel.LEFT);
		MakeFont.getFont(label03);
		panel03.add(label03);
		shippingPanel.add(panel03);

		JPanel printPanel = new JPanel();
		printPanel.setBounds(0, 100, 500, 300);
		printCart(printPanel);
		shippingPanel.add(printPanel);
	}

	public void printCart(JPanel panel) {

		Font ft;
		ft = new Font("맑은 고딕", Font.BOLD, 12);

		JPanel panel01 = new JPanel();
		panel01.setBounds(0, 0, 500, 5);
		JLabel label01 = new JLabel("      장바구니 상품 목록 :");
		label01.setFont(ft);
		panel01.add(label01);
		panel.add(panel01);

		JPanel panel02 = new JPanel();
		panel02.setBounds(0, 20, 500, 5);
		JLabel label02 = new JLabel("---------------------------------------------------------------");
		label02.setFont(ft);
		panel02.add(label02);
		panel.add(panel02);

		JPanel panel03 = new JPanel();
		panel03.setBounds(0, 25, 500, 5);
		JLabel label03 = new JLabel("                        도서 ID           |        수량           |      합계        ");
		label03.setFont(ft);
		panel03.add(label03);
		panel.add(panel03);

		JPanel panel04 = new JPanel();
		panel04.setBounds(0, 30, 500, 5);
		JLabel label04 = new JLabel("--------------------------------------");
		MakeFont.getFont(label04);
		panel04.add(label04);
		panel.add(panel04);

		JPanel panel05 = new JPanel(new GridLayout(cm.getSize(),1));
		ArrayList<CartItemVo> cartList = cm.getList();
		int sum = 0;
		for (int i = 0; i < cm.getList().size(); i++) { // 13
			CartItemVo item = cartList.get(i);
			panel05.setBounds(50, 25 + (i * 5), 500, 5);
			panel05.setBackground(Color.GRAY);

			JLabel label05 = new JLabel("               "+ item.getIsbn() + "                    "
					+ item.getQty() + "                    "
					+ item.getTotalPrice());
			label05.setFont(ft);
			panel05.add(label05);
			panel.add(panel05);
			
			// 주문 총금액 합계
			sum += item.getQty() * item.getTotalPrice();
		}

		JPanel panel06 = new JPanel();
		panel06.setBounds(0, 35 + (cm.getSize() * 5), 500, 5);
		JLabel label06 = new JLabel("--------------------------------------");
		MakeFont.getFont(label06);
		panel06.add(label06);
		panel.add(panel06);


		JPanel panel07 = new JPanel();
		panel07.setBounds(0, 40 + (cm.getSize() * 5), 500, 5);
		JLabel label07 = new JLabel("      주문 총금액 : " + sum + " 원");
		MakeFont.getFont(label07);
		panel07.add(label07);
		panel.add(panel07);
		
		// 주문 확정 버튼
		JPanel panel08 = new JPanel();
		panel08.setBounds(0, 40 + (cm.getSize() * 5), 500, 5);
		JButton btnOrderFinish = new JButton("주문 확정");
		MakeFont.getFont(btnOrderFinish);
		panel08.add(btnOrderFinish);
		panel.add(panel08);
		
		btnOrderFinish.addActionListener( e -> {
			int select = JOptionPane.showConfirmDialog(null, "주문 확정을 하시겠습니까?");
			System.out.println("select => "+select);
			switch (select) {
				case 0:
					// 장바구니 비우기
					cm.remove(); 
					JOptionPane.showMessageDialog(null, "주문이 완료되었습니다");
					
					// MainWindow 감추기
					main.setVisible(false);
					main.dispose();
					
					// GuestWindow 열기
					new GuestWindow("온라인 서점2", 0, 0, 1000, 750);
					break;
				case 1:
					JOptionPane.showMessageDialog(null, "주문이 취소되었습니다");
					panel.removeAll();
					panel.revalidate();
					panel.repaint();
					break;
			}
		});
	}
}