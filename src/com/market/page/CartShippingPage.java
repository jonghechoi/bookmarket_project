package com.market.page;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import com.market.book_market2.CartMgm;
import com.market.commons.MakeFont;
import com.market.dao.CartDao;
import com.market.dao.DBConn;
import com.market.dao.MemberDao;
import com.market.main.MainWindow;
import com.market.vo.MemberVo;

public class CartShippingPage extends JPanel {
//	OrderUserVo ouser;
	MemberVo orderMember;
	CartMgm cm;
	JPanel shippingPanel;
	JPanel radioPanel;
	MainWindow main;
	CartDao cartDao;
	MemberDao memberDao;

	public CartShippingPage(JPanel panel, CartMgm cm, MainWindow main, Map<String, DBConn> daoList) {
		this.main = main;
		this.cm = cm;
		this.cartDao = (CartDao)daoList.get("cartDao");
		this.memberDao = (MemberDao)daoList.get("memberDao");
		orderMember = memberDao.select(main.member.getMid());

		setLayout(null);
		Rectangle rect = panel.getBounds();
		System.out.println(rect);
		setPreferredSize(rect.getSize());

		radioPanel = new JPanel();
		radioPanel.setBounds(300, 0, 700, 50);
		radioPanel.setLayout(new FlowLayout());
		add(radioPanel);
		JLabel radioLabel = new JLabel("배송받을 분의 고객정보와 동일합니까?");
		MakeFont.getFont(radioLabel);
		ButtonGroup group = new ButtonGroup();
		JRadioButton radioOk = new JRadioButton("예");
		MakeFont.getFont(radioOk);
		JRadioButton radioNo = new JRadioButton("아니오");
		MakeFont.getFont(radioNo);
		group.add(radioOk);
		group.add(radioNo);
		radioPanel.add(radioLabel);
		radioPanel.add(radioOk);
		radioPanel.add(radioNo);

		shippingPanel = new JPanel();
		shippingPanel.setBounds(200, 50, 700, 500);
		shippingPanel.setLayout(null);
		add(shippingPanel);

		radioOk.setSelected(true);
		radioNo.setSelected(false);
		UserShippingInfo("yes");

		radioOk.addActionListener(new AbstractAction() {
			public void actionPerformed(ActionEvent e) {

				if (radioOk.isSelected()) {
					shippingPanel.removeAll();
					UserShippingInfo("yes");
					shippingPanel.revalidate();
					shippingPanel.repaint();
					radioNo.setSelected(false);
				}
			}
		});

		radioNo.addActionListener(new AbstractAction() {
			public void actionPerformed(ActionEvent e) {

				if (radioNo.isSelected()) {
					shippingPanel.removeAll();
					UserShippingInfo("no");
					shippingPanel.revalidate();
					shippingPanel.repaint();
					radioOk.setSelected(false);
				}
			}
		});
	}

	public void UserShippingInfo(String select) {
		JPanel namePanel = new JPanel();
		JLabel nameLabel = new JLabel("고객명 : ");
		JTextField nameLabel2 = new JTextField(20);
		namePanel.setBounds(0, 100, 700, 50);
		MakeFont.getFont(nameLabel);
		MakeFont.getFont(nameLabel2);
		namePanel.add(nameLabel);
		namePanel.add(nameLabel2);
		
		JPanel phonePanel = new JPanel();
		JLabel phoneLabel = new JLabel("연락처 : ");
		JTextField phoneLabel2 = new JTextField(20);
		phonePanel.setBounds(0, 150, 700, 50);
		MakeFont.getFont(phoneLabel);
		MakeFont.getFont(phoneLabel2);
		phonePanel.add(phoneLabel);
		phonePanel.add(phoneLabel2);
		
		JPanel addressPanel = new JPanel();
		JLabel label = new JLabel("배송지 : ");
		JTextField addressText = new JTextField(20);
		addressPanel.setBounds(0, 200, 700, 50);
		MakeFont.getFont(label);
		MakeFont.getFont(addressText);
		addressPanel.add(label);
		addressPanel.add(addressText);
		
		if (select=="yes") {
			nameLabel2.setBackground(Color.LIGHT_GRAY);
			nameLabel2.setText(orderMember.getName());

			phoneLabel2.setBackground(Color.LIGHT_GRAY);
			phoneLabel2.setText(orderMember.getPhone());

			addressText.setBackground(Color.LIGHT_GRAY);
			addressText.setText(orderMember.getAddr());
		}
		shippingPanel.add(namePanel);
		shippingPanel.add(phonePanel);
		shippingPanel.add(addressPanel);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setBounds(0, 300, 700, 100);

		JLabel buttonLabel = new JLabel("주문완료");
		MakeFont.getFont(buttonLabel);
		JButton orderButton = new JButton();
		orderButton.add(buttonLabel);
		buttonPanel.add(orderButton);
		shippingPanel.add(buttonPanel);

		orderButton.addActionListener(new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				orderMember.setName(nameLabel2.getText());
				orderMember.setPhone(phoneLabel2.getText());
				orderMember.setAddr(addressText.getText());
				
				radioPanel.removeAll();
				radioPanel.revalidate();
				radioPanel.repaint();
				shippingPanel.removeAll();
				shippingPanel.add(new CartOrderBillPage(shippingPanel, cm, orderMember, main,
														cartDao));

				shippingPanel.revalidate();
				shippingPanel.repaint();
			}
		});
	}

}