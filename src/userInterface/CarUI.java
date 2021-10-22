package userInterface;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
import model.BeanCar;
import model.BeanCarModel;
import model.BeanNet;
import model.BeanStuff;
import util.BusinessException;

public class CarUI extends JFrame implements ActionListener{
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JToolBar toolBar = new JToolBar();
	private JButton button_add_Car = new JButton("添加车辆");
	private JButton button_delete_Car = new JButton("报废车辆");
	private JButton button_change_Car = new JButton("修改车辆");
	
	private Object tblCarTitle[]=BeanCar.tableTitles;
	private Object CarData[][];
	DefaultTableModel tabCarModel=new DefaultTableModel();
	private JTable dataTableCar=new JTable(tabCarModel);
	
	private String currentStuff = null;
	private BeanCar curCar = null;
	List<BeanCar> allCar = new ArrayList<BeanCar>();
	
	
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
					CarUI frame = new CarUI("stuff12");
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
	public CarUI(String currentStuff) throws BusinessException {
		
		this.currentStuff = currentStuff;
		
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		//buttons
		this.toolBar.add(button_add_Car);this.button_add_Car.addActionListener(this);
		this.toolBar.add(button_delete_Car);this.button_delete_Car.addActionListener(this);
		this.toolBar.add(button_change_Car);this.button_change_Car.addActionListener(this);
		this.contentPane.add(toolBar,BorderLayout.NORTH);
		
		//Title
		this.reloadCarTable();
		this.contentPane.add(new JScrollPane(this.dataTableCar));
		setContentPane(contentPane);
		
		this.dataTableCar.addMouseListener(new MouseAdapter (){
			@Override
			public void mouseClicked(MouseEvent e) {
				int i=CarUI.this.dataTableCar.getSelectedRow();
				if(i<0) {
					return;
				}
				curCar=allCar.get(i);
				System.out.println(i);
				
				//添加新Car,如果离开了选中的添加行将获取信息并且上传数据库并重新加载
				if(dataTableCar.getSelectedRow() != allCar.size()-1 && isAdd==true) {
					try {
						String netid = netList.get(String.valueOf(tabCarModel.getValueAt(allCar.size()-1,1)));
						String modelid = modelList.get(String.valueOf(tabCarModel.getValueAt(allCar.size()-1,2)));
						BeanCar addCar = new BeanCar();
						addCar.setCar_id(String.valueOf(tabCarModel.getValueAt(allCar.size()-1,0)));
						addCar.setNet_id(netid);
						addCar.setModel_id(modelid);
						addCar.setCar_license(String.valueOf(tabCarModel.getValueAt(allCar.size()-1,3)));
						addCar.setCar_state(String.valueOf(tabCarModel.getValueAt(allCar.size()-1,4)));
						new CarManager().addCar(Check.nextID("car_tbl", "car_id", "car"), addCar.getNet_id(), addCar.getModel_id(), addCar.getCar_license(), addCar.getCar_state());
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
					}
				}else if(dataTableCar.getSelectedRow()!=modifyRow && isModify==true) {
					try {
						String netid = netList.get(String.valueOf(tabCarModel.getValueAt(modifyRow,1)));
						String modelid = modelList.get(String.valueOf(tabCarModel.getValueAt(modifyRow,2)));
						BeanCar modifyCar = new BeanCar();
						modifyCar.setCar_id(String.valueOf(tabCarModel.getValueAt(modifyRow,0)));
						modifyCar.setNet_id(netid);
						modifyCar.setModel_id(modelid);
						modifyCar.setCar_license(String.valueOf(tabCarModel.getValueAt(modifyRow,3)));
						modifyCar.setCar_state(String.valueOf(tabCarModel.getValueAt(modifyRow,4)));
						new CarManager().modifyCar(modifyCar.getCar_id(), modifyCar.getNet_id(), modifyCar.getModel_id(), modifyCar.getCar_license(), modifyCar.getCar_state());
						JOptionPane.showMessageDialog(null, "修改成功", "消息",JOptionPane.PLAIN_MESSAGE);
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
	    });
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		try {
			if(e.getSource() == this.button_add_Car && Check.isAdmin(currentStuff)) {
				//添加一行并选中添加行
				isAdd = true;
				Object[] addRow = { "系统自动生成", "net_id", "model_id","license","idle" };
				tabCarModel.insertRow(allCar.size(), addRow);
				allCar.add(new BeanCar());
				int rowNum = allCar.size()-1;
				dataTableCar.setRowSelectionInterval(rowNum, rowNum);
				//之后在鼠标点击的事件监听中运行
			}else if(e.getSource() == this.button_change_Car && Check.isAdmin(currentStuff)) {
				isModify=true;
				modifyRow = dataTableCar.getSelectedRow();
			}else if(e.getSource() == this.button_delete_Car) {
				try {
					Check.isMatch(currentStuff, String.valueOf(tabCarModel.getValueAt(this.dataTableCar.getSelectedRow(),0)));
					new CarManager().discardCar(currentStuff, curCar.getCar_id(), Check.nextID("discard_tbl", "discard_id", "discard"), new Date(), null);
					JOptionPane.showMessageDialog(null, "删除成功", "消息",JOptionPane.PLAIN_MESSAGE);
					refresh();
				} catch (BusinessException e1) {
					refresh();
					throw e1;
				}
				
			}
		} catch (HeadlessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (BusinessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	private void reloadCarTable() throws BusinessException{
		try {
			allCar = new CarManager().loadAllCar();
			// allCar.add(new BeanCar());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
			System.out.println(e.getMessage());
			return;
		}
		CarData =  new Object[allCar.size()][BeanCar.tableTitles.length];
		for(int i=0;i<allCar.size();i++){
			for(int j=0;j<BeanCar.tableTitles.length;j++)
				CarData[i][j]=allCar.get(i).getCell(j);
		}
		tabCarModel.setDataVector(CarData,tblCarTitle);
		
		//net
		List<BeanNet> allNet = new NetManager().loadAllNet();
		for (int i1 = 0; i1 < allNet.size(); i1++) {
			netList.put(allNet.get(i1).getNet_name(), allNet.get(i1).getNet_id());
		}
		JComboBox jboxNet= new JComboBox(netList.keySet().toArray());
		jboxNet.setSelectedIndex(0);
		TableColumnModel colNet = dataTableCar.getColumnModel();
		colNet.getColumn(1).setCellEditor(new DefaultCellEditor(jboxNet));
		
		//model
		List<BeanCarModel> allModel = new CarManager().loadAllModel();
		for (int i1 = 0; i1 < allModel.size(); i1++) {
			modelList.put(allModel.get(i1).getModel_name(), allModel.get(i1).getModel_id());
		}
		JComboBox jboxModel= new JComboBox(modelList.keySet().toArray());
		jboxModel.setSelectedIndex(0);
		TableColumnModel colModel = dataTableCar.getColumnModel();
		colModel.getColumn(2).setCellEditor(new DefaultCellEditor(jboxModel));
		
		this.dataTableCar.validate();
		this.dataTableCar.repaint();
	}
	
	private void refresh() throws BusinessException {
		isAdd=false;
		isModify=false;
		reloadCarTable();
		dataTableCar.updateUI();
	}

}
