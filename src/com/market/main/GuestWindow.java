package com.market.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

//import com.market.book_market2.OrderUserVo;
//import com.market.book_market2.UserVo;
import com.market.commons.MakeFont;
import com.market.dao.MemberDao;
import com.market.vo.MemberVo;

public class GuestWindow extends JFrame implements ActionListener {
	MemberDao memberDao;
	JTextField nameField;
	JPasswordField phoneField;
	
	public GuestWindow(String title, int x, int y, int width, int height) {
		memberDao = new MemberDao();
		initContainer(title, x, y, width, height);
		setVisible(true);
		setResizable(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setIconImage(new ImageIcon("./images/shop.png").getImage());
	}

	private void initContainer(String title, int x, int y, int width, int height) {
		setTitle(title);
		setBounds(x, y, width, height);
		setLayout(null);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((screenSize.width - 1000) / 2, (screenSize.height - 750) / 2);
		JPanel userPanel = new JPanel();
		userPanel.setBounds(0, 100, 1000, 256);
		ImageIcon imageIcon = new ImageIcon("./images/user.png");
		imageIcon.setImage(imageIcon.getImage().getScaledInstance(160, 160, Image.SCALE_SMOOTH));
		JLabel userLabel = new JLabel(imageIcon);
		userPanel.add(userLabel);
		add(userPanel);

		JPanel titlePanel = new JPanel();
		titlePanel.setBounds(0, 350, 1000, 50);
		add(titlePanel);

		JLabel titleLabel = new JLabel("-- 로그인 정보를 입력해주세요 --");
//		titleLabel.setFont(ft);
		MakeFont.getFont(titleLabel);
		
		titleLabel.setForeground(Color.BLUE);
		titlePanel.add(titleLabel);

		JPanel namePanel = new JPanel();
		namePanel.setBounds(0, 400, 1000, 50);
		add(namePanel);

		JLabel nameLabel = new JLabel("아 이 디 : ");
//		nameLabel.setFont(ft);
		MakeFont.getFont(nameLabel);
		namePanel.add(nameLabel);
		nameField = new JTextField(10);
//		nameField.setFont(ft);
		MakeFont.getFont(nameField);
		namePanel.add(nameField);

		JPanel phonePanel = new JPanel();
		phonePanel.setBounds(0, 450, 1000, 50);
		add(phonePanel);

		JLabel phoneLabel = new JLabel("패스워드: ");
//		phoneLabel.setFont(ft);
		MakeFont.getFont(phoneLabel);
		phonePanel.add(phoneLabel);

		phoneField = new JPasswordField(10);
//		phoneField.setFont(ft);
		MakeFont.getFont(phoneField);
		phonePanel.add(phoneField);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setBounds(0, 500, 1000, 100);
		add(buttonPanel);
		JLabel buttonLabel = new JLabel("로그인", new ImageIcon("images/shop.png"), JLabel.LEFT);
//		buttonLabel.setFont(ft);
		MakeFont.getFont(buttonLabel);
		JButton enterButton = new JButton();
		enterButton.add(buttonLabel);
		buttonPanel.add(enterButton);
		
		phoneField.addActionListener(this);
		enterButton.addActionListener(this);
		
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				System.exit(0); 
			}
		});
	}
	
	public void actionPerformed(ActionEvent e) {
			JLabel message = new JLabel("사용자 정보 에러");
			MakeFont.getFont(message);
			if (nameField.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "아이디를 입력해주세요");
				nameField.requestFocus();
			}else if(phoneField.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "패스워드를 입력해주세요");
				phoneField.requestFocus();
			}else {
				String mid = nameField.getText().trim();
				String pass = phoneField.getText().trim();
				if(memberDao.select(mid, pass) == 1) {
//					System.out.println("고르인 성공~~~~~");
					MemberVo member = new MemberVo();
					member.setMid(nameField.getText());
					member.setPass(phoneField.getText());
					setVisible(false);
					
					// interface인 부모 Map은 자식인 HashMap으로 생성 가능!!
					// 자주 사용한다면 아예 vo를 만드는게 나음
					Map param = new HashMap(); 
					param.put("title", "온라인 서점");
					param.put("x", 0);
					param.put("y", 0);
					param.put("width", 1000);
					param.put("height", 750);
					param.put("member", member);
					param.put("memberDao", memberDao);
					new MainWindow(param);
				}else {
					// 로그인 실패 경고창
					JOptionPane.showMessageDialog(null, "로그인에 실패했습니다 \n다시 입력해주세요");
					nameField.setText("");
					phoneField.setText("");
					nameField.requestFocus();
				}
			}
	}
}