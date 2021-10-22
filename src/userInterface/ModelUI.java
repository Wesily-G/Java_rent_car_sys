package userInterface;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
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
import model.BeanCarModel;
import model.BeanCarType;
import model.BeanNet;
import util.BusinessException;

public class ModelUI extends JFrame implements ActionListener{
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JToolBar toolBar = new JToolBar();
	private JButton button_add_Model = new JButton("添加车型");
	private JButton button_delete_Model = new JButton("删除车型");
	private JButton button_change_Model = new JButton("修改车型");
	
	private Object tblModelTitle[]=BeanCarModel.tableTitles;
	private Object ModelData[][];
	DefaultTableModel tabPlanModel=new DefaultTableModel();
	private JTable dataTableModel=new JTable(tabPlanModel);
	
	private BeanCarModel curModel = null;
	List<BeanCarModel> allModel = new ArrayList<BeanCarModel>();
	
	private HashMap<String, String> typeList = new HashMap<String, String>();
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
					ModelUI frame = new ModelUI();
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
	public ModelUI() throws BusinessException {
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		//buttons
		this.toolBar.add(button_add_Model);this.button_add_Model.addActionListener(this);
		this.toolBar.add(button_delete_Model);this.button_delete_Model.addActionListener(this);
		this.toolBar.add(button_change_Model);this.button_change_Model.addActionListener(this);
		this.contentPane.add(toolBar,BorderLayout.NORTH);
		
		//Title
		this.reloadModelTable();
		this.contentPane.add(new JScrollPane(this.dataTableModel));
		setContentPane(contentPane);
		
		this.dataTableModel.addMouseListener(new MouseAdapter (){
			@Override
			public void mouseClicked(MouseEvent e) {
				int i=ModelUI.this.dataTableModel.getSelectedRow();
				if(i<0) {
					return;
				}
				curModel=allModel.get(i);
				System.out.println(i);
				
				//添加新Model,如果离开了选中的添加行将获取信息并且上传数据库并重新加载
				if(dataTableModel.getSelectedRow() != allModel.size()-1 && isAdd==true) {
					String typeid = typeList.get(String.valueOf(dataTableModel.getValueAt(allModel.size()-1,1)));
					BeanCarModel addModel = new BeanCarModel();
					String auto = String.valueOf(tabPlanModel.getValueAt(allModel.size()-1,5));
					addModel.setModel_id(String.valueOf(tabPlanModel.getValueAt(allModel.size()-1,0)));
					addModel.setType_id(typeid);
					addModel.setModel_name(String.valueOf(tabPlanModel.getValueAt(allModel.size()-1,2)));
					addModel.setModel_brand(String.valueOf(tabPlanModel.getValueAt(allModel.size()-1,3)));
					addModel.setModel_emissions(Integer.parseInt(String.valueOf(tabPlanModel.getValueAt(allModel.size()-1,4))));
					if(auto.equals("true")||auto.equals("1"))
						addModel.setModel_auto(true);
					else
						addModel.setModel_auto(false);
					addModel.setModel_seats(Integer.parseInt(String.valueOf(tabPlanModel.getValueAt(allModel.size()-1,6))));
					addModel.setModel_price(Float.valueOf(String.valueOf(tabPlanModel.getValueAt(allModel.size()-1,7))));
					try {
						new CarManager().addCarModel(Check.nextID("car_model_tbl", "model_id", "model"),addModel.getType_id(),addModel.getModel_name(),addModel.getModel_brand(),addModel.getModel_emissions(),addModel.isModel_auto(),addModel.getModel_seats(),addModel.getModel_price());
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
				}else if(dataTableModel.getSelectedRow()!=modifyRow && isModify==true) {
					try {
						String typeid = typeList.get(String.valueOf(dataTableModel.getValueAt(modifyRow,1)));
						BeanCarModel modifyModel = new BeanCarModel();
						String auto = String.valueOf(tabPlanModel.getValueAt(modifyRow,5));
						modifyModel.setModel_id(String.valueOf(tabPlanModel.getValueAt(modifyRow,0)));
						modifyModel.setType_id(typeid);
						modifyModel.setModel_name(String.valueOf(tabPlanModel.getValueAt(modifyRow,2)));
						modifyModel.setModel_brand(String.valueOf(tabPlanModel.getValueAt(modifyRow,3)));
						modifyModel.setModel_emissions(Integer.parseInt(String.valueOf(tabPlanModel.getValueAt(modifyRow,4))));
						if(auto.equals("true")||auto.equals("1"))
							modifyModel.setModel_auto(true);
						else
							modifyModel.setModel_auto(false);
						modifyModel.setModel_seats(Integer.parseInt(String.valueOf(tabPlanModel.getValueAt(modifyRow,6))));
						modifyModel.setModel_price(Float.valueOf(String.valueOf(tabPlanModel.getValueAt(modifyRow,7))));
						new CarManager().modifyCarModel(modifyModel.getModel_id(),modifyModel.getType_id(),modifyModel.getModel_name(),modifyModel.getModel_brand(),modifyModel.getModel_emissions(),modifyModel.isModel_auto(),modifyModel.getModel_seats(),modifyModel.getModel_price());
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
		if(e.getSource() == this.button_add_Model) {
			//添加一行并选中添加行
			isAdd = true;
			Object[] addRow = { "系统自动生成", "typeid", "name","brand","emissions","auto?","seats","price" };
			tabPlanModel.insertRow(allModel.size(), addRow);
			allModel.add(new BeanCarModel());
			int rowNum = allModel.size()-1;
			dataTableModel.setRowSelectionInterval(rowNum, rowNum);
			//之后在鼠标点击的事件监听中运行
		}else if(e.getSource() == this.button_change_Model) {
			isModify=true;
			modifyRow = dataTableModel.getSelectedRow();
		}else if(e.getSource() == this.button_delete_Model) {
			try {
				new CarManager().deleteCarModel(curModel.getModel_id());
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
	
	private void reloadModelTable() throws BusinessException{
		try {
			allModel = new CarManager().loadAllModel();
			// allModel.add(new BeanCarModel());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
			System.out.println(e.getMessage());
			return;
		}
		ModelData =  new Object[allModel.size()][BeanCarModel.tableTitles.length];
		for(int i=0;i<allModel.size();i++){
			for(int j=0;j<BeanCarModel.tableTitles.length;j++)
				ModelData[i][j]=allModel.get(i).getCell(j);
		}
		tabPlanModel.setDataVector(ModelData,tblModelTitle);
		
		//net
		List<BeanCarType> allType = new CarManager().loadAllType();
		for (int i1 = 0; i1 < allType.size(); i1++) {
			typeList.put(allType.get(i1).getType_name(), allType.get(i1).getType_id());
		}
		JComboBox jboxType= new JComboBox(typeList.keySet().toArray());
		jboxType.setSelectedIndex(0);
		TableColumnModel colType = dataTableModel.getColumnModel();
		colType.getColumn(1).setCellEditor(new DefaultCellEditor(jboxType));
		
		this.dataTableModel.validate();
		this.dataTableModel.repaint();
	}
	
	private void refresh() throws BusinessException {
		isAdd=false;
		isModify=false;
		reloadModelTable();
		dataTableModel.updateUI();
	}

}
