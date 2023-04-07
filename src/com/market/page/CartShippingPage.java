package com.market.page;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import com.market.book_market2.CartMgm;
import com.market.book_market2.OrderUserVo;
import com.market.book_market2.UserVo;
import com.market.commons.MakeFont;
import com.market.main.MainWindow;

public class CartShippingPage extends JPanel {
	OrderUserVo ouser;
	CartMgm cm;
	JPanel shippingPanel;
	JPanel radioPanel;
	MainWindow main;

	public CartShippingPage(JPanel panel, CartMgm cm, UserVo user, MainWindow main) {
		this.cm = cm;
		this.ouser = (OrderUserVo)user;
		this.main = main;
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
		namePanel.setBounds(0, 100, 700, 50);
		JLabel nameLabel = new JLabel("고객명 : ");
		JTextField nameLabel2 = new JTextField(15);
		JPanel phonePanel = new JPanel();
		JLabel phoneLabel = new JLabel("연락처 : ");
		JTextField phoneLabel2 = new JTextField(15);
		
		if (select=="yes") {
			MakeFont.getFont(nameLabel);
			namePanel.add(nameLabel);

			MakeFont.getFont(nameLabel2);
			nameLabel2.setBackground(Color.LIGHT_GRAY);
			nameLabel2.setText(ouser.getName());
			namePanel.add(nameLabel2);
			shippingPanel.add(namePanel);

			phonePanel.setBounds(0, 150, 700, 50);
			MakeFont.getFont(phoneLabel);
			phonePanel.add(phoneLabel);

			MakeFont.getFont(phoneLabel2);
			phoneLabel2.setBackground(Color.LIGHT_GRAY);
			phoneLabel2.setText(String.valueOf(ouser.getPhoneNumber()));
			phonePanel.add(phoneLabel2);
			shippingPanel.add(phonePanel);
		}else {
			MakeFont.getFont(nameLabel);
			namePanel.add(nameLabel);

			MakeFont.getFont(nameLabel2);
			namePanel.add(nameLabel2);
			shippingPanel.add(namePanel);

			phonePanel.setBounds(0, 150, 700, 50);
			MakeFont.getFont(phoneLabel);
			phonePanel.add(phoneLabel);

			MakeFont.getFont(phoneLabel2);
			phonePanel.add(phoneLabel2);
			shippingPanel.add(phonePanel);
		}
		

		JPanel addressPanel = new JPanel();
		addressPanel.setBounds(0, 200, 700, 50);
		JLabel label = new JLabel("배송지 : ");
		MakeFont.getFont(label);
		addressPanel.add(label);

		JTextField addressText = new JTextField(15);
		MakeFont.getFont(addressText);
		addressPanel.add(addressText);
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
				String name = nameLabel2.getText();
				String phone = phoneLabel2.getText();
				String address = addressText.getText();
				System.out.println(name + "," + phone + ","+address);
				
				ouser.setName(name);
				ouser.setPhoneNumber(phone);
				ouser.setAddress(address);
				radioPanel.removeAll();
				radioPanel.revalidate();
				radioPanel.repaint();
				shippingPanel.removeAll();
				shippingPanel.add(new CartOrderBillPage(shippingPanel, cm, ouser, main));

				shippingPanel.revalidate();
				shippingPanel.repaint();
			}
		});
	}

}