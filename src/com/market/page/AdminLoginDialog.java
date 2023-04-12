package com.market.page;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.market.book_market2.AdminVo;
import com.market.commons.MakeFont;
import com.market.dao.MemberDao;

public class AdminLoginDialog extends JDialog {

	JTextField idField;
	JPasswordField pwField;
	MemberDao memberDao;
	public boolean isLogin = false;

	public AdminLoginDialog(JFrame frame, String str, MemberDao memberDao) {
		super(frame, "관리자 로그인", true);
		this.memberDao = memberDao;

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((screenSize.width - 400) / 2, (screenSize.height - 300) / 2);
		setSize(400, 300);
		setLayout(null);

		JPanel titlePanel = new JPanel();
		titlePanel.setBounds(0, 20, 400, 50);
		add(titlePanel);
		JLabel titleLabel = new JLabel("관리자 로그인");
		titleLabel.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		titlePanel.add(titleLabel);

		JPanel idPanel = new JPanel();
		idPanel.setBounds(0, 70, 400, 50);
		add(idPanel);
		JLabel idLabel = new JLabel("아 이 디 : ");
		MakeFont.getFont(idLabel);
		idField = new JTextField(10);
		MakeFont.getFont(idField);
		idPanel.add(idLabel);
		idPanel.add(idField);

		JPanel pwPanel = new JPanel();
		pwPanel.setBounds(0, 120, 400, 50);
		add(pwPanel);
		JLabel pwLabel = new JLabel("패스워드 : ");
		MakeFont.getFont(pwLabel);
		pwField = new JPasswordField(10);
		MakeFont.getFont(pwField);
		pwPanel.add(pwLabel);
		pwPanel.add(pwField);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setBounds(0, 170, 400, 50);
		add(buttonPanel);
		JLabel okLabel = new JLabel("확인");
		MakeFont.getFont(okLabel);
		JButton okButton = new JButton();
		okButton.add(okLabel);
		buttonPanel.add(okButton);

		JLabel cancelLabel = new JLabel("취소");
		MakeFont.getFont(cancelLabel);
		JButton cancelBtn = new JButton();
		cancelBtn.add(cancelLabel);
		buttonPanel.add(cancelBtn);

		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminVo admin = new AdminVo();
				String uid = idField.getText().trim().toUpperCase();
				String upass = pwField.getText().trim().toUpperCase();
				
				if (memberDao.select(uid, upass) == 1) {
					isLogin = true;
					dispose();
				} else
					JOptionPane.showMessageDialog(okButton, "관리자 정보가 일치하지 않습니다.");
			}
		});

		cancelBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				isLogin = false;
				dispose();
			}
		});
	}
}