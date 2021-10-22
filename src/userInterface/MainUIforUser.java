package userInterface;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import DBManager.NetManager;
import DBManager.OrderManager;
import model.BeanOrder;
import util.BusinessException;

public class MainUIforUser extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	private String currentUser;
	private JPanel contentPane;
	
	private JMenuBar menubar = new JMenuBar();
    private JMenu menu_info = new JMenu("信息管理");
    private JMenu menu_password = new JMenu("修改密码");
    private JMenu menu_static = new JMenu("查询");
    private JMenu menu_order_operate = new JMenu("订单操作");
    
    private JMenuItem item_carInquiery = new JMenuItem("车辆查询");
    private JMenuItem item_refresh = new JMenuItem("刷新订单");
    private JMenuItem item_password = new JMenuItem("密码修改");
    private JMenuItem item_prefer = new JMenuItem("优惠券查询");
    private JMenuItem item_info = new JMenuItem("个人信息修改");
    private JMenuItem item_intocar = new JMenuItem("上车");
    private JMenuItem item_returncar = new JMenuItem("还车");
    private JMenuItem item_cancelorder = new JMenuItem("取消订单");
    
	
	private Object tblOrderTitle[]=BeanOrder.tableTitles;
	private Object orderData[][];
	DefaultTableModel tabPlanModel=new DefaultTableModel();
	private JTable dataTableOrder=new JTable(tabPlanModel);

	List<BeanOrder> allOrder = new ArrayList<BeanOrder>();
	private BeanOrder curOrder;
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new MainUIforUser("user32").setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws BusinessException 
	 */
	public MainUIforUser(String userid) throws BusinessException {
		currentUser = userid;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setExtendedState(this.MAXIMIZED_BOTH);
		this.setTitle("欢迎使用CC租车系统");
		setBounds(100, 100, 609, 432);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		
		//menu
		this.menu_static.add(item_carInquiery);this.item_carInquiery.addActionListener(this);
		this.menu_static.add(item_refresh);this.item_refresh.addActionListener(this);
		this.menu_static.add(item_prefer);this.item_prefer.addActionListener(this);
		this.menu_info.add(item_info);this.item_info.addActionListener(this);
		this.menu_password.add(item_password);this.item_password.addActionListener(this);
		this.menu_order_operate.add(item_intocar);this.item_intocar.addActionListener(this);
		this.menu_order_operate.add(item_returncar);this.item_returncar.addActionListener(this);
		this.menu_order_operate.add(item_cancelorder);this.item_cancelorder.addActionListener(this);
		
		menubar.add(menu_static);
	    menubar.add(menu_info);
	    menubar.add(menu_password);
	    menubar.add(menu_order_operate);
	    this.setJMenuBar(menubar);
		
		//Title
		this.reloadOrderTable();
		this.contentPane.add(new JScrollPane(this.dataTableOrder));
		setContentPane(contentPane);
		
		this.dataTableOrder.addMouseListener(new MouseAdapter (){
			@Override
			public void mouseClicked(MouseEvent e) {
				int i=MainUIforUser.this.dataTableOrder.getSelectedRow();
				if(i<0) {
					return;
				}
				curOrder=allOrder.get(i);
				System.out.println(i);
			}
	    });
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		try{
			if (e.getSource() == this.item_carInquiery) {
				new InquiryUIForUser(currentUser).setVisible(true);
			}else if (e.getSource() == this.item_prefer) {
				new PreferForUserUI(currentUser).setVisible(true);
			}else if(e.getSource() == this.item_refresh) {
				refresh();
			}else if (e.getSource() == this.item_info) {
				new ModifyUserUI(new JDialog(), "ModifyInfo", true ,currentUser).setVisible(true);
			}else if (e.getSource() == this.item_password) {
				new ModifyPwdUI(new JDialog(), "ModifyPwd", true, true, currentUser).setVisible(true);
			}else if(e.getSource() == this.item_intocar) {
				new OrderManager().intoCar(curOrder.getOrder_id());
				refresh();
			}else if(e.getSource() == this.item_returncar) {
				new OrderManager().outOfCar(curOrder.getOrder_id());
				refresh();
			}else if(e.getSource() == this.item_cancelorder) {
				new OrderManager().cancelOrder(curOrder.getOrder_id());
				refresh();
			}
		}catch(BusinessException e1){
			e1.printStackTrace();
		}
		
		
	}
	
	private void reloadOrderTable() throws BusinessException{
		try {
			allOrder = new OrderManager().loadAllOrderForUser(currentUser);
			// allOrder.add(new BeanOrder());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
			System.out.println(e.getMessage());
			return;
		}
		orderData =  new Object[allOrder.size()][BeanOrder.tableTitles.length];
		for(int i=0;i<allOrder.size();i++){
			for(int j=0;j<BeanOrder.tableTitles.length;j++)
				if(j==2 || j==4)
					orderData[i][j]=new NetManager().findNet(String.valueOf(allOrder.get(i).getCell(j))).getNet_name();
				else
					orderData[i][j]=allOrder.get(i).getCell(j);
		}
		tabPlanModel.setDataVector(orderData,tblOrderTitle);
		this.dataTableOrder.validate();
		this.dataTableOrder.repaint();
	}
	
	private void refresh() throws BusinessException {
		reloadOrderTable();
		dataTableOrder.updateUI();
	}

}
