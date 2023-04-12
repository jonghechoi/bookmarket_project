package com.market.page;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.market.book_market2.CartMgm;
import com.market.commons.MakeFont;
import com.market.dao.CartDao;
import com.market.dao.OrderDao;
import com.market.main.GuestWindow;
import com.market.main.MainWindow;
import com.market.vo.CartVo;
import com.market.vo.MemberVo;
import com.market.vo.OrderVo;

public class CartOrderBillPage extends JPanel {
	CartMgm cm;
	MemberVo orderMember;
	CartDao cartDao;
	
	MainWindow main;
	JPanel shippingPanel;
	JPanel radioPanel;
	JPanel mPagePanel;

	public CartOrderBillPage(JPanel panel, CartMgm cm, MemberVo orderMember, MainWindow main,
							 CartDao cartDao) {
		this.mPagePanel = panel;
		this.cm = cm;
		this.orderMember = orderMember;
		this.cartDao = cartDao;
		this.main = main;
		setLayout(null);

		Rectangle rect = panel.getBounds();
		System.out.println(rect);
		setPreferredSize(rect.getSize());

		shippingPanel = new JPanel(new GridLayout(5, 1));
		shippingPanel.setBounds(0, 0, 700, 500);
		shippingPanel.setLayout(null);
		panel.add(shippingPanel);
		
		printBillInfo(orderMember);
	}

	public void printBillInfo(MemberVo orderMember) {
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		String strDate = formatter.format(date);

		JPanel panel01 = new JPanel();
		panel01.setBounds(0, 0, 500, 30);
		JLabel label01 = new JLabel("------------------- 배송 받을 고객 정보 ---------------------");
		MakeFont.getFont(label01);
		panel01.add(label01);
		shippingPanel.add(panel01);

		JPanel panel02 = new JPanel(new BorderLayout());
		panel02.setBounds(50, 30, 500, 30);
		JLabel label02 = new JLabel("   고객명 : " + orderMember.getName());
		label02.setHorizontalAlignment(JLabel.LEFT);
		MakeFont.getFont(label02);
		panel02.add(label02);
		shippingPanel.add(panel02, BorderLayout.WEST);
		
		JPanel panel03 = new JPanel(new BorderLayout());
		panel03.setBounds(50, 60, 500, 30);
		JLabel label03 = new JLabel("   연락처 : " + orderMember.getPhone());
		label03.setHorizontalAlignment(JLabel.LEFT);
		MakeFont.getFont(label03);
		panel03.add(label03);
		shippingPanel.add(panel03, BorderLayout.WEST);

		JPanel panel04 = new JPanel(new BorderLayout());
		panel04.setBounds(50, 90, 500, 30);
		JLabel label04 = new JLabel("   배송지 : " + orderMember.getAddr());
		label04.setHorizontalAlignment(JLabel.LEFT);
		MakeFont.getFont(label04);
		panel04.add(label04);
		shippingPanel.add(panel04, BorderLayout.WEST);
		
		JPanel panel05 = new JPanel(new BorderLayout());
		panel05.setBounds(50, 120, 500, 30);
		JLabel label05 = new JLabel("   발송일 : " + strDate);
		label05.setHorizontalAlignment(JLabel.LEFT);
		MakeFont.getFont(label05);
		panel05.add(label05);
		shippingPanel.add(panel05, BorderLayout.WEST);
		
		JPanel printPanel = new JPanel(new GridLayout(8, 1));
		printPanel.setBounds(0, 150, 500, 300);
		printCart(printPanel);
		shippingPanel.add(printPanel);
	}

	public void printCart(JPanel panel) {
		JPanel panel01 = new JPanel();
		panel01.setBounds(0, 0, 500, 5);
		JLabel label01 = new JLabel("-------------------- 장바구니 상품 목록 --------------------");
		MakeFont.getFont(label01);
		panel01.add(label01);
		panel.add(panel01);

		JPanel panel02 = new JPanel();
		panel02.setBounds(0, 20, 500, 5);
		JLabel label02 = new JLabel("---------------------------------------------------------------");
		MakeFont.getFont(label02);
		MakeFont.getFont(label01);
		panel02.add(label02);
		panel.add(panel02);

		JPanel panel03 = new JPanel();
		panel03.setBounds(0, 25, 500, 5);
		JLabel label03 = new JLabel("              도서 ID         |      수량           |      합계        ");
		MakeFont.getFont(label03);
		panel03.add(label03);
		panel.add(panel03);

		JPanel panel04 = new JPanel();
		panel04.setBounds(0, 30, 500, 5);
		JLabel label04 = new JLabel("---------------------------------------------------------------");
		MakeFont.getFont(label04);
		panel04.add(label04);
		panel.add(panel04);

		JPanel panel05 = new JPanel(new GridLayout(cartDao.getSize(main.member.getMid()),1));
		ArrayList<CartVo> cartList = cartDao.select(main.member.getMid().toUpperCase());
		int sum = 0;
		for (int i = 0; i < cartList.size(); i++) { 
			CartVo item = cartList.get(i);
			panel05.setBounds(50, 25 + (i * 5), 500, 5);

			JLabel label05 = new JLabel("               "+ item.getIsbn() + "                    "
					+ item.getQty() + "                    "
					+ item.getTotal_price());
			MakeFont.getFont(label05);
			panel05.add(label05);
			panel.add(panel05);
			
			// 주문 총금액 합계
			sum += item.getQty() * item.getTotal_price();
		}

		JPanel panel06 = new JPanel();
		panel06.setBounds(0, 35 + (cm.getSize() * 5), 500, 5);
		JLabel label06 = new JLabel("---------------------------------------------------------------");
		MakeFont.getFont(label06);
		panel06.add(label06);
		panel.add(panel06);

		DecimalFormat decimalFormat = new DecimalFormat("###,###");
		String sumString = decimalFormat.format(sum);

		JPanel panel07 = new JPanel();
		panel07.setBounds(0, 40 + (cm.getSize() * 5), 500, 5);
		JLabel label07 = new JLabel("  주문 총금액 : " + sumString + " 원");
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
			switch (select) {
				case 0:
					// BOOKMARKET_ORDER 테이블에 저장할 데이터 생성
					// OID, ODATE, QTY리스트, ISBN리스트, MID, NAME, PHONE, ADDR 
					// -> OrderVo에 넣고 -> DB에 insert()
					// QTY리스트, ISBN리스트 -> CartDao를 통해 생성 후 여기서 사용하는 OrderVo 타입으로 리턴
					OrderVo orderVo = cartDao.getOrderVo(orderMember.getMid().toUpperCase());
					
					// OID, ODATE -> UUID, Calendar 클래스 이용해 여기서 생성
					UUID uuid = UUID.randomUUID();
					Calendar cal = Calendar.getInstance();
					String oid = uuid.toString();
					String odate = cal.get(Calendar.YEAR) + "/" + 
								  (cal.get(Calendar.MONTH)+1) + "/" + 
								   cal.get(Calendar.DATE);
		
					// MID, NAME, PHONE, ADDR -> orderMember에서 데이터 추출
					orderVo.setOid(oid);
					orderVo.setOdate(odate);
					orderVo.setMid(orderMember.getMid());
					orderVo.setName(orderMember.getName());
					orderVo.setPhone(orderMember.getPhone());
					orderVo.setAddr(orderMember.getAddr());
					
					OrderDao orderDao = new OrderDao();
//					int result = orderDao.insert(orderVo); // 둘 중 하나 사용 가능
					int result = orderDao.insertPrepared(orderVo);
					if(result >= 1) {
						// 장바구니 비우기 // Cartdao --> mid 일치하는 사용자의 데이터 삭제
						int result2 = cartDao.deleteAll(main.member.getMid().toUpperCase());
						if(result2 >=1) {
							JOptionPane.showMessageDialog(null, "카트 비우기 완료");
						}
						JOptionPane.showMessageDialog(null, "주문이 완료되었습니다");
					}
					
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