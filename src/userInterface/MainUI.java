package userInterface;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

import DBManager.Check;
import DBManager.NetManager;
import DBManager.OrderManager;
import model.BeanOrder;
import util.BusinessException;

public class MainUI extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	private String currentStuff;
	private JPanel contentPane;
	
	private JMenuBar menubar = new JMenuBar();
    private JMenu menu_car = new JMenu("车辆管理");
    private JMenu menu_net = new JMenu("网点管理");
    private JMenu menu_stuff = new JMenu("员工管理");
    private JMenu menu_prefer = new JMenu("优惠管理");
    private JMenu menu_password = new JMenu("修改密码");
    private JMenu menu_static = new JMenu("查询");
    
    private JMenuItem item_type = new JMenuItem("类别管理");
    private JMenuItem item_model = new JMenuItem("车型管理");
    private JMenuItem item_car = new JMenuItem("车辆管理");
    private JMenuItem item_net = new JMenuItem("网点管理");
    private JMenuItem item_stuff = new JMenuItem("员工管理");
    private JMenuItem item_prefer = new JMenuItem("优惠券管理");
    private JMenuItem item_discount = new JMenuItem("限时折扣管理");
    private JMenuItem item_password = new JMenuItem("密码管理");
    private JMenuItem item_user = new JMenuItem("客户查询");
    private JMenuItem item_state = new JMenuItem("车辆状态查询");
    
	
	private Object tblOrderTitle[]=BeanOrder.tableTitles;
	private Object orderData[][];
	DefaultTableModel tabPlanModel=new DefaultTableModel();
	private JTable dataTableOrder=new JTable(tabPlanModel);
	
	List<BeanOrder> allOrder = new ArrayList<BeanOrder>();
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainUI frame = new MainUI("stuff12");
					frame.setVisible(true);
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
	public MainUI(String stuff_id) throws BusinessException {
		currentStuff = stuff_id;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setExtendedState(this.MAXIMIZED_BOTH);
		this.setTitle("欢迎使用CC租车系统");
		setBounds(100, 100, 609, 432);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		
		//menu
		this.menu_car.add(item_type);this.item_type.addActionListener(this);
		this.menu_car.add(item_model);this.item_model.addActionListener(this);
		this.menu_car.add(item_car);this.item_car.addActionListener(this);
		this.menu_net.add(item_net);this.item_net.addActionListener(this);
		this.menu_stuff.add(item_stuff);this.item_stuff.addActionListener(this);
		this.menu_prefer.add(item_prefer);this.item_prefer.addActionListener(this);
		this.menu_prefer.add(item_discount);this.item_discount.addActionListener(this);
		this.menu_password.add(item_password);this.item_password.addActionListener(this);
		this.menu_static.add(item_user);this.item_user.addActionListener(this);
		this.menu_static.add(item_state);this.item_state.addActionListener(this);
		
		menubar.add(menu_car);
	    menubar.add(menu_net);
	    menubar.add(menu_stuff);
	    menubar.add(menu_prefer);
	    menubar.add(menu_password);
	    menubar.add(menu_static);
	    this.setJMenuBar(menubar);
		
		//Title
		this.reloadOrderTable();
		this.contentPane.add(new JScrollPane(this.dataTableOrder));
		setContentPane(contentPane);
	}

	@Override
	public void actionPerformed(ActionEvent e){
		// TODO Auto-generated method stub
		try{
			if (e.getSource() == this.item_type && Check.isAdmin(currentStuff)) {
				new TypeUI().setVisible(true);
			}else if (e.getSource() == this.item_model && Check.isAdmin(currentStuff)) {
				new ModelUI().setVisible(true);
			}else if (e.getSource() == this.item_car) {
				new CarUI(currentStuff).setVisible(true);
			}else if (e.getSource() == this.item_net && Check.isAdmin(currentStuff)) {
				new NetUI().setVisible(true);
			}else if (e.getSource() == this.item_stuff && Check.isAdmin(currentStuff)) {
				new StuffUI().setVisible(true);
			}else if (e.getSource() == this.item_prefer) {
				new PreferUI(currentStuff).setVisible(true);
			}else if(e.getSource() == this.item_discount) {
				new DiscountUI(currentStuff).setVisible(true);
			}else if (e.getSource() == this.item_password) {
				new ModifyPwdUI(new JDialog(), "ModifyPwd", true, false, currentStuff).setVisible(true);
			}else if (e.getSource() == this.item_user && Check.isAdmin(currentStuff)) {
				new InquiryUserOrderUI().setVisible(true);
			}else if (e.getSource() == this.item_state && Check.isAdmin(currentStuff)) {
				new InquiryUI().setVisible(true);
			}
		}catch(BusinessException e1){
			e1.printStackTrace();
		}
			
			
	}
	
	private void reloadOrderTable() throws BusinessException{
		try {
			allOrder = new OrderManager().loadAllOrder();
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

}
