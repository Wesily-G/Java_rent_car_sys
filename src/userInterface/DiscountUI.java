package userInterface;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import DBManager.CarManager;
import DBManager.Check;
import DBManager.NetManager;
import DBManager.DiscountManager;
import model.BeanNet;
import model.BeanCarModel;
import model.BeanDiscount;
import util.BusinessException;

public class DiscountUI extends JFrame implements ActionListener{
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JToolBar toolBar = new JToolBar();
	private JButton button_add_Discount = new JButton("添加折扣");
	private JButton button_delete_Discount = new JButton("删除折扣");
	private JButton button_change_Discount = new JButton("修改折扣");
	
	private Object tblDiscountTitle[]=BeanDiscount.tableTitles;
	private Object DiscountData[][];
	DefaultTableModel tabDiscountModel=new DefaultTableModel();
	private JTable dataTableDiscount=new JTable(tabDiscountModel);
	
	private String currentStuff;
	private BeanDiscount curDiscount = null;
	List<BeanDiscount> allDiscount = new ArrayList<BeanDiscount>();
	
	private HashMap<String, String> netList = new HashMap<String, String>();
	private HashMap<String, String> modelList = new HashMap<String, String>();
	private Boolean isAdd = false;
	private Boolean isModify = false;
	private int modifyRow = 0;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DiscountUI frame = new DiscountUI("stuff1");
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
	public DiscountUI(String currentStuff) throws BusinessException {
		this.currentStuff = currentStuff;
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		//buttons
		this.toolBar.add(button_add_Discount);this.button_add_Discount.addActionListener(this);
		this.toolBar.add(button_delete_Discount);this.button_delete_Discount.addActionListener(this);
		this.toolBar.add(button_change_Discount);this.button_change_Discount.addActionListener(this);
		this.contentPane.add(toolBar,BorderLayout.NORTH);
		
		//Title
		this.reloadDiscountTable();
		this.contentPane.add(new JScrollPane(this.dataTableDiscount));
		setContentPane(contentPane);
		
		this.dataTableDiscount.addMouseListener(new MouseAdapter (){
			@Override
			public void mouseClicked(MouseEvent e) {
				int i=DiscountUI.this.dataTableDiscount.getSelectedRow();
				if(i<0) {
					return;
				}
				curDiscount=allDiscount.get(i);
				System.out.println(i);
				
				//添加新Discount,如果离开了选中的添加行将获取信息并且上传数据库并重新加载
				if(dataTableDiscount.getSelectedRow() != allDiscount.size()-1 && isAdd==true) {
					try {
						String netid = netList.get(String.valueOf(tabDiscountModel.getValueAt(allDiscount.size()-1,1)));
						String modelid = modelList.get(String.valueOf(tabDiscountModel.getValueAt(allDiscount.size()-1,2)));
						Check.isMatch(currentStuff, netid);
						BeanDiscount addDiscount = new BeanDiscount();
						addDiscount.setDiscount_id(String.valueOf(tabDiscountModel.getValueAt(allDiscount.size()-1,0)));
						addDiscount.setDiscount_net(netid);
						addDiscount.setDiscount_model(modelid);
						addDiscount.setDiscount(Float.parseFloat(String.valueOf(tabDiscountModel.getValueAt(allDiscount.size()-1,3))));
						addDiscount.setDiscount_count(Integer.parseInt(String.valueOf(tabDiscountModel.getValueAt(allDiscount.size()-1,4))));
						addDiscount.setDiscount_start(new SimpleDateFormat("yyyy-MM-dd").parse(String.valueOf(tabDiscountModel.getValueAt(allDiscount.size()-1,5))));
						addDiscount.setDiscount_end(new SimpleDateFormat("yyyy-MM-dd").parse(String.valueOf(tabDiscountModel.getValueAt(allDiscount.size()-1,6))));
						new DiscountManager().addDiscount(Check.nextID("discount_tbl", "discount_id", "discount"),addDiscount.getDiscount_net(),addDiscount.getDiscount_model(),addDiscount.getDiscount(),addDiscount.getDiscount_count(),addDiscount.getDiscount_start(),addDiscount.getDiscount_end());
						JOptionPane.showMessageDialog(null, "添加成功", "消息",JOptionPane.PLAIN_MESSAGE);
						refresh();
					} catch (BusinessException e1) {
						try {
							refresh();
						} catch (BusinessException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
						e1.printStackTrace();
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}else if(dataTableDiscount.getSelectedRow()!=modifyRow && isModify==true) {
					try {
						String netid = netList.get(String.valueOf(tabDiscountModel.getValueAt(modifyRow,1)));
						String modelid = modelList.get(String.valueOf(tabDiscountModel.getValueAt(modifyRow,2)));
						Check.isMatch(currentStuff, netid);
						BeanDiscount modifyDiscount = new BeanDiscount();
						modifyDiscount.setDiscount_id(String.valueOf(tabDiscountModel.getValueAt(modifyRow,0)));
						modifyDiscount.setDiscount_net(netid);
						modifyDiscount.setDiscount_model(netid);
						modifyDiscount.setDiscount(Float.parseFloat(String.valueOf(tabDiscountModel.getValueAt(modifyRow,3))));
						modifyDiscount.setDiscount_count(Integer.parseInt(String.valueOf(tabDiscountModel.getValueAt(modifyRow,4))));
						modifyDiscount.setDiscount_start(new SimpleDateFormat("yyyy-MM-dd").parse(String.valueOf(tabDiscountModel.getValueAt(modifyRow,5))));
						modifyDiscount.setDiscount_end(new SimpleDateFormat("yyyy-MM-dd").parse(String.valueOf(tabDiscountModel.getValueAt(modifyRow,6))));
						new DiscountManager().modifyDiscount(modifyDiscount.getDiscount_id(),modifyDiscount.getDiscount_net(),modifyDiscount.getDiscount_model(),modifyDiscount.getDiscount(),modifyDiscount.getDiscount_count(),modifyDiscount.getDiscount_start(),modifyDiscount.getDiscount_end());
						JOptionPane.showMessageDialog(null, "修改成功", "消息",JOptionPane.PLAIN_MESSAGE);
						refresh();
					} catch (BusinessException | ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						try {
							refresh();
						} catch (BusinessException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
					}
				}
				
			}
	    });
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == this.button_add_Discount) {
			//添加一行并选中添加行
			isAdd = true;
			Object[] addRow = { "系统自动生成", "net", "model","discount","count","start","end" };
			tabDiscountModel.insertRow(allDiscount.size(), addRow);
			allDiscount.add(new BeanDiscount());
			int rowNum = allDiscount.size()-1;
			dataTableDiscount.setRowSelectionInterval(rowNum, rowNum);
			//之后在鼠标点击的事件监听中运行
		}else if(e.getSource() == this.button_change_Discount) {
			isModify=true;
			modifyRow = dataTableDiscount.getSelectedRow();
		}else if(e.getSource() == this.button_delete_Discount) {
			try {
				new DiscountManager().deleteDiscount(curDiscount.getDiscount_id());
				JOptionPane.showMessageDialog(null, "删除成功", "消息",JOptionPane.PLAIN_MESSAGE);
				refresh();
			} catch (BusinessException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				try {
					refresh();
				} catch (BusinessException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			}
			
		}
		
	}
	
	private void reloadDiscountTable() throws BusinessException{
		try {
			allDiscount = new DiscountManager().loadAllDiscount();
//			allDiscount.add(new BeanDiscount());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
			System.out.println(e.getMessage());
			return;
		}
		DiscountData =  new Object[allDiscount.size()][BeanDiscount.tableTitles.length];
		for(int i=0;i<allDiscount.size();i++){
			for(int j=0;j<BeanDiscount.tableTitles.length;j++)
				DiscountData[i][j]=allDiscount.get(i).getCell(j);
		}
		tabDiscountModel.setDataVector(DiscountData,tblDiscountTitle);
		
		//net
		List<BeanNet> allNet = new NetManager().loadAllNet();
		for (int i1 = 0; i1 < allNet.size(); i1++) {
			netList.put(allNet.get(i1).getNet_name(), allNet.get(i1).getNet_id());
		}
		JComboBox jboxNet= new JComboBox(netList.keySet().toArray());
		jboxNet.setSelectedIndex(0);
		TableColumnModel colNet = dataTableDiscount.getColumnModel();
		colNet.getColumn(1).setCellEditor(new DefaultCellEditor(jboxNet));
				
		//model
		List<BeanCarModel> allModel = new CarManager().loadAllModel();
		for (int i1 = 0; i1 < allModel.size(); i1++) {
			modelList.put(allModel.get(i1).getModel_name(), allModel.get(i1).getModel_id());
		}
		JComboBox jboxModel= new JComboBox(modelList.keySet().toArray());
		jboxModel.setSelectedIndex(0);
		TableColumnModel colModel = dataTableDiscount.getColumnModel();
		colModel.getColumn(2).setCellEditor(new DefaultCellEditor(jboxModel));
		
		this.dataTableDiscount.validate();
		this.dataTableDiscount.repaint();
	}
	
	private void refresh() throws BusinessException {
		isAdd=false;
		isModify=false;
		reloadDiscountTable();
		dataTableDiscount.updateUI();
	}

}
