package userInterface;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import DBManager.Check;
import DBManager.Inquiry;
import DBManager.NetManager;
import DBManager.OrderManager;
import model.BeanCar;
import util.BusinessException;

public class InquiryUIForUser extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JTextField TextCarModel;
	private JTextField TextNet;
	private JTextField TextDestination;
	private JLabel LabelCarModel;
	private JLabel LabelNet;
	private JLabel LabelDestination;
	private JButton ButtonInquiry;
	private JButton ButtonOrder;
	private JScrollPane scrollPane;

	private Object tblTitle[]={"汽车编号","所属网点","车型名称","状态"};
	private Object CarData[][];
	DefaultTableModel tabPlanModel=new DefaultTableModel();
	private JTable dataTableCar=new JTable(tabPlanModel);
	private List<BeanCar> allCar;
	private BeanCar curCar;
	private String userid;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InquiryUIForUser frame = new InquiryUIForUser("user32");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public InquiryUIForUser(String user_id) {
		this.userid=user_id;
		this.setTitle("查询信息");
		setBounds(100, 100, 509, 423);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		LabelCarModel = new JLabel("\u8F66\u578B\u540D\u79F0\uFF1A");
		LabelCarModel.setBounds(42, 16, 70, 15);
		contentPane.add(LabelCarModel);
		
		LabelNet = new JLabel("\u7F51\u70B9\u540D\u79F0\uFF1A");
		LabelNet.setBounds(42, 39, 70, 15);
		contentPane.add(LabelNet);
		
		LabelDestination = new JLabel("\u76ee\u7684\u5730\uff1a");
		LabelDestination.setBounds(42, 65, 70, 15);
		contentPane.add(LabelDestination);
		
		TextCarModel = new JTextField();
		TextCarModel.setBounds(126, 13, 107, 21);
		contentPane.add(TextCarModel);
		TextCarModel.setColumns(10);
		
		TextNet = new JTextField();
		TextNet.setBounds(126, 36, 107, 21);
		contentPane.add(TextNet);
		TextNet.setColumns(10);
		
		TextDestination = new JTextField();
		TextDestination.setBounds(126, 62, 107, 21);
		contentPane.add(TextDestination);
		TextDestination.setColumns(10);
		
		ButtonInquiry = new JButton("\u67E5\u8BE2");
		ButtonInquiry.setBounds(344, 17, 74, 23);
		contentPane.add(ButtonInquiry);
		this.ButtonInquiry.addActionListener(this);
		
		ButtonOrder = new JButton("\u79df\u7528");
		ButtonOrder.setBounds(344, 55, 74, 23);
		contentPane.add(ButtonOrder);
		this.ButtonOrder.addActionListener(this);
		
		scrollPane = new JScrollPane(this.dataTableCar);
		scrollPane.setBounds(10, 91, 473, 253);
		this.contentPane.add(scrollPane);
		setContentPane(contentPane);
		
		this.dataTableCar.addMouseListener(new MouseAdapter (){
			@Override
			public void mouseClicked(MouseEvent e) {
				int i=InquiryUIForUser.this.dataTableCar.getSelectedRow();
				if(i<0) {
					return;
				}
				curCar=allCar.get(i);
				System.out.println(i);
			}
	    });

	}

	public void actionPerformed(ActionEvent e){
		try{
			if(e.getSource() == this.ButtonInquiry) {
				String model_name=this.TextCarModel.getText();
				String net_name=this.TextNet.getText();
				if(Check.checkNullNoException(model_name)&&Check.checkNullNoException(net_name))
					throw new BusinessException("null input");
				this.reloadCarTable(model_name,net_name);
				this.dataTableCar.updateUI();
				System.out.println("Inquiry success.");
			}else if(e.getSource() == this.ButtonOrder) {
				String destination=this.TextDestination.getText();
				if(Check.checkNullNoException(destination))
					throw new BusinessException("null destination");
				new OrderManager().addOrder(userid, curCar.getCar_id(), new NetManager().nameToID(curCar.getNet_id()), new NetManager().nameToID(destination));
//				JOptionPane.showMessageDialog(null, "租车成功", "消息",JOptionPane.ERROR_MESSAGE);
			}
			
		}catch(Exception e1){
			e1.printStackTrace();
		}
			
			
	}
	
	private void reloadCarTable(String modelName,String netName){
		try {
			allCar = new Inquiry().inquiryCarForUser(modelName, netName);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
			System.out.println(e.getMessage());
			return;
		}
		CarData =  new Object[allCar.size()][tblTitle.length];

		//利用id的位置存储name
		for(int i=0;i<allCar.size();i++){
			for(int j=0;j<tblTitle.length;j++)
				switch(j){
					case 0:
						CarData[i][j]=allCar.get(i).getCar_id();
						break;
					case 1:
						CarData[i][j]=allCar.get(i).getNet_id();
						break;
					case 2:
						CarData[i][j]=allCar.get(i).getModel_id();
						break;
					case 3:
						CarData[i][j]=allCar.get(i).getCar_state();
						break;
				}
				// CarData[i][j]=allCar.get(i).getCell(j);
		}
		tabPlanModel.setDataVector(CarData,tblTitle);
		this.dataTableCar.validate();
		this.dataTableCar.repaint();
	}
}
