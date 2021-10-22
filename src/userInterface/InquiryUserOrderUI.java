package userInterface;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import model.BeanCar;
import model.BeanOrder;
import util.BusinessException;

public class InquiryUserOrderUI extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JTextField TextUserID;
	private JLabel LabelUserID;
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
					InquiryUserOrderUI frame = new InquiryUserOrderUI();
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
	public InquiryUserOrderUI() {
		this.setTitle("查询信息");
		setBounds(100, 100, 509, 423);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		LabelUserID = new JLabel("\u5ba2\u6237ID\uff1a");
		LabelUserID.setBounds(42, 40, 70, 15);
		contentPane.add(LabelUserID);
		
		TextUserID = new JTextField();
		TextUserID.setBounds(126, 37, 107, 21);
		contentPane.add(TextUserID);
		TextUserID.setColumns(10);
		
		ButtonInquiry = new JButton("\u67E5\u8BE2");
		ButtonInquiry.setBounds(344, 25, 74, 23);
		contentPane.add(ButtonInquiry);
		this.ButtonInquiry.addActionListener(this);
		
		ButtonStatic = new JButton("\u7EDF\u8BA1");
		ButtonStatic.setBounds(344, 54, 74, 23);
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
				String user_id=this.TextUserID.getText();
				Check.checkNull(user_id);
				this.reloadOrderTable(user_id);
				this.dataTableOrder.updateUI();
				System.out.println("Inquiry success.");
			}else if (e.getSource() == this.ButtonStatic) {
			 	this.LabelCount.setText("总数量：" + allOrder.size());
			 	this.LabelAmount.setText("总金额："+sum);
			 	this.LabelCount.updateUI();
			 	this.LabelAmount.updateUI();
			 }
		}catch(Exception e1){
			e1.printStackTrace();
		}
			
			
	}
	
	private void reloadOrderTable(String user_id){
		try {
			allOrder = new Inquiry().inquiryUserOrder(user_id);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
			System.out.println(e.getMessage());
			return;
		}
		OrderData =  new Object[allOrder.size()][tblTitle.length];
		for(int i=0;i<allOrder.size();i++){
			for(int j=0;j<tblTitle.length;j++) {
				OrderData[i][j]=allOrder.get(i).getCell(j);
				if(j==6) {
					float t = Float.parseFloat(OrderData[i][j].toString());
					System.out.println(OrderData[i][j]);
					sum  = sum + t;
				}
			}
				
		}
		tabOrderModel.setDataVector(OrderData,tblTitle);
		this.dataTableOrder.validate();
		this.dataTableOrder.repaint();
	}
}
