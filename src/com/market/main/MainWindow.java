package com.market.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.market.book_market2.CartMgm;
import com.market.bookitem.BookInIt;
import com.market.commons.MakeFont;
import com.market.dao.BookDao;
import com.market.dao.CartDao;
import com.market.dao.DBConn;
import com.market.dao.MemberDao;
import com.market.page.AdminLoginDialog;
import com.market.page.AdminPage;
import com.market.page.CartAddItemPage;
import com.market.page.CartItemListPage;
import com.market.page.CartShippingPage;
import com.market.page.GuestInfoPage;
import com.market.vo.CartVo;
import com.market.vo.MemberVo;

public class MainWindow extends JFrame {
	static JPanel mMenuPanel, mPagePanel;
	public static MemberVo member;
	MemberDao memberDao;
	BookDao bookDao;
	CartDao cartDao;
	CartVo cartVo;
	CartMgm cm; // 싱글톤 패턴으로 사용하기 위해 main윈도우에서 생성
	MainWindow main = this; // 중요!! MainWindow를 감추기 위해서 사용
	Map<String, DBConn> daoList = new HashMap<String,DBConn>();
	
	public MainWindow(Map param) {
		bookDao = new BookDao();
		cartDao = new CartDao();
		cartVo = new CartVo();
		cm = new CartMgm();
		this.member = (MemberVo)param.get("member");
		this.memberDao = (MemberDao)param.get("memberDao");
		
		daoList.put("memberDao",memberDao);
		daoList.put("bookDao", bookDao);
		daoList.put("cartDao", cartDao);
		
		String title = (String)param.get("title");
		int x = (Integer)param.get("x");
		int y = (Integer)param.get("y");
		int width = (Integer)param.get("width");
		int height = (Integer)param.get("height");
		
		initContainer(title, x, y, width, height);
		setVisible(true);
		setResizable(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setIconImage(new ImageIcon("./images/shop.png").getImage());
	}
	
	public MainWindow(String title, int x, int y, int width, int height, MemberVo member) {
		cm = new CartMgm();
		this.member = member;
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

		mMenuPanel = new JPanel();
		mMenuPanel.setBounds(0, 20, width, 50);
		menuIntroduction();
		add(mMenuPanel);

		mPagePanel = new JPanel();
		mPagePanel.setBounds(0, 100, width, height);
		add(mPagePanel);
		
		mPagePanel.removeAll();
		BookInIt.init();
		mPagePanel.add(new CartAddItemPage(mPagePanel, cm, bookDao, cartDao));
		mPagePanel.revalidate();
		mPagePanel.repaint();

		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}

	public void menuIntroduction() {
		JButton bt1 = new JButton("고객 정보 확인하기", new ImageIcon("./images/1.png"));
		bt1.setBounds(0, 0, 100, 50);
		MakeFont.getFont(bt1);
		mMenuPanel.add(bt1);
		bt1.addActionListener( e -> {
				mPagePanel.removeAll();  
				mPagePanel.add(new GuestInfoPage(mPagePanel, member, memberDao)); 
				mPagePanel.revalidate(); 
				mPagePanel.repaint(); 
		});

		JButton bt2 = new JButton("장바구니 상품 목록보기", new ImageIcon("./images/2.png"));
		bt2.setBounds(0, 0, 100, 30);
		MakeFont.getFont(bt2);
		mMenuPanel.add(bt2);
		bt2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(cartDao.getSize(member.getMid().toUpperCase()) == 0)
					JOptionPane.showMessageDialog(bt2, "장바구니가 비어 있습니다", "message", JOptionPane.ERROR_MESSAGE);
				else {
					mPagePanel.removeAll();
					mPagePanel.add( new CartItemListPage(mPagePanel, daoList));
					mPagePanel.revalidate();
					mPagePanel.repaint();
				}
			}
		});
		
		JButton bt4 = new JButton("장바구니 항목 추가하기", new ImageIcon("./images/3.png"));
		MakeFont.getFont(bt4);
		mMenuPanel.add(bt4);
		bt4.addActionListener( e -> {
			mPagePanel.removeAll();
			BookInIt.init();
			mPagePanel.add(new CartAddItemPage(mPagePanel, cm, bookDao, cartDao));
			mPagePanel.revalidate();
			mPagePanel.repaint();
		});

		JButton bt7 = new JButton("주문하기", new ImageIcon("./images/4.png"));
		MakeFont.getFont(bt7);
		mMenuPanel.add(bt7);
		bt7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (cartDao.getSize(member.getMid().toUpperCase()) == 0)
					JOptionPane.showMessageDialog(bt7, "장바구니가 비어있습니다", "message", JOptionPane.ERROR_MESSAGE);
				else {
					mPagePanel.removeAll();
					mPagePanel.add(new CartShippingPage(mPagePanel, cm, main, daoList));
					mPagePanel.revalidate();
					mPagePanel.repaint();
				}
			}
		});

		JButton bt8 = new JButton("로그아웃", new ImageIcon("./images/5.png"));
		MakeFont.getFont(bt8);
		mMenuPanel.add(bt8);
		bt8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int select = JOptionPane.showConfirmDialog(bt8, "로그아웃을 하시겠습니까? ");
				if (select == 0) {
					setVisible(false);
					new GuestWindow("온라인 서점", 0, 0, 1000, 750);
				}
			}
		});
		
		if(member.getMid().toUpperCase().equals("ADMIN1234")) {
			JButton bt9 = new JButton("관리자", new ImageIcon("./images/6.png"));
			MakeFont.getFont(bt9);
			mMenuPanel.add(bt9);
			bt9.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					AdminLoginDialog adminDialog;
					JFrame frame = new JFrame();
					adminDialog = new AdminLoginDialog(frame, "관리자 화면", memberDao);
					adminDialog.setVisible(true);
					if (adminDialog.isLogin) {
						mPagePanel.removeAll();
						mPagePanel.add(new AdminPage(mPagePanel, bookDao));
						mPagePanel.revalidate();
						mPagePanel.repaint();
					}
				}
			});
		}
	}
}