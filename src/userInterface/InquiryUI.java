package userInterface;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
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

import DBManager.CarManager;
import DBManager.Check;
import DBManager.Inquiry;
import DBManager.NetManager;
import model.BeanCar;
import model.BeanOrder;
import util.BusinessException;

public class InquiryUI extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JTextField TextCarModel;
	private JTextField TextNet;
	private JLabel LabelCarModel;
	private JLabel LabelNet;
	private JLabel LabelCount;
	private JLabel LabelAmount;
	private JButton ButtonInquiry;
	private JButton ButtonStatic;
	private JScrollPane scrollPane;

	private Object tblTitle[]=BeanOrder.tableTitles;
	private Object OrderData[][];
	DefaultTableModel tabOrderModel=new DefaultTableModel();
	private JTable dataTableOrder=new JTable(tabOrderModel);
	private List<BeanOrder> allOrder;
	
	private float sum=0;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InquiryUI frame = new InquiryUI();
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
	public InquiryUI() {
		this.setTitle("查询信息");
		setBounds(100, 100, 509, 423);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		LabelCarModel = new JLabel("\u8F66\u578B\u540d\u79f0\uFF1A");
		LabelCarModel.setBounds(42, 22, 70, 15);
		contentPane.add(LabelCarModel);
		
		LabelNet = new JLabel("\u7F51\u70B9\u540d\u79f0\uFF1A");
		LabelNet.setBounds(42, 59, 70, 15);
		contentPane.add(LabelNet);
		
		TextCarModel = new JTextField();
		TextCarModel.setBounds(126, 19, 107, 21);
		contentPane.add(TextCarModel);
		TextCarModel.setColumns(10);
		
		TextNet = new JTextField();
		TextNet.setBounds(126, 56, 107, 21);
		contentPane.add(TextNet);
		TextNet.setColumns(10);
		
		ButtonInquiry = new JButton("\u67E5\u8BE2");
		ButtonInquiry.setBounds(344, 19, 74, 23);
		contentPane.add(ButtonInquiry);
		this.ButtonInquiry.addActionListener(this);
		
		ButtonStatic = new JButton("\u7EDF\u8BA1");
		ButtonStatic.setBounds(344, 56, 74, 23);
		contentPane.add(ButtonStatic);
		this.ButtonStatic.addActionListener(this);
		
		LabelCount = new JLabel("\u603B\u6570\u91CF\uFF1A");
		LabelCount.setBounds(42, 359, 123, 15);
		contentPane.add(LabelCount);
		
		LabelAmount = new JLabel("\u603B\u91D1\u989D\uFF1A");
		LabelAmount.setBounds(280, 359, 123, 15);
		contentPane.add(LabelAmount);
		
		scrollPane = new JScrollPane(this.dataTableOrder);
		scrollPane.setBounds(10, 91, 473, 253);
		this.contentPane.add(scrollPane);
		setContentPane(contentPane);

	}

	public void actionPerformed(ActionEvent e){
		try{
			if (e.getSource() == this.ButtonInquiry) {
				sum=0;
				String model_name=new CarManager().nameToID(this.TextCarModel.getText());
				String net_name=new NetManager().nameToID(this.TextNet.getText());
				System.out.print(model_name+net_name);
				if(Check.checkNullNoException(model_name)&&Check.checkNullNoException(net_name))
					throw new BusinessException("null input");
				this.reloadOrderTable(model_name,net_name);
				this.dataTableOrder.updateUI();
				System.out.println("Inquiry success.");
			}else if (e.getSource() == this.ButtonStatic) {
			 	this.LabelCount.setText("总数量：" + allOrder.size());
			 	this.LabelAmount.setText("总金额："+sum);
			}
		}catch(Exception e1){
			e1.printStackTrace();
		}
			
			
	}
	
	private void reloadOrderTable(String modelName,String netName){
		try {
			allOrder = new Inquiry().inquiryOrder(modelName, netName);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
			System.out.println(e.getMessage());
			return;
		}
		OrderData =  new Object[allOrder.size()][tblTitle.length];
		for(int i=0;i<allOrder.size();i++){
			for(int j=0;j<tblTitle.length;j++) {
				OrderData[i][j]=allOrder.get(i).getCell(j);
				if(j==6)
					sum+=Float.parseFloat(OrderData[i][j].toString());
			}
				 
		}
		tabOrderModel.setDataVector(OrderData,tblTitle);
		this.dataTableOrder.validate();
		this.dataTableOrder.repaint();
	}
}
