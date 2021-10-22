package userInterface;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import DBManager.CarManager;
import DBManager.Check;
import DBManager.NetManager;
import model.BeanCarType;
import model.BeanNet;
import util.BusinessException;

public class TypeUI extends JFrame implements ActionListener{
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JToolBar toolBar = new JToolBar();
	private JButton button_add_type = new JButton("添加类别");
	private JButton button_delete_type = new JButton("删除类别");
	private JButton button_change_type = new JButton("修改类别");
	
	private Object tblTypeTitle[]=BeanCarType.tableTitles;
	private Object TypeData[][];
	DefaultTableModel tabTypeModel=new DefaultTableModel();
	private JTable dataTableType=new JTable(tabTypeModel);
	
	private BeanCarType curType = null;
	List<BeanCarType> allType = new ArrayList<BeanCarType>();
	
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
					TypeUI frame = new TypeUI();
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
	public TypeUI() {
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		//buttons
		this.toolBar.add(button_add_type);this.button_add_type.addActionListener(this);
		this.toolBar.add(button_delete_type);this.button_delete_type.addActionListener(this);
		this.toolBar.add(button_change_type);this.button_change_type.addActionListener(this);
		this.contentPane.add(toolBar,BorderLayout.NORTH);
		
		//Title
		this.reloadTypeTable();
		this.contentPane.add(new JScrollPane(this.dataTableType));
		setContentPane(contentPane);
		
		this.dataTableType.addMouseListener(new MouseAdapter (){
			@Override
			public void mouseClicked(MouseEvent e) {
				int i=TypeUI.this.dataTableType.getSelectedRow();
				if(i<0) {
					return;
				}
				curType=allType.get(i);
				System.out.println(i);
			
			//添加新Type,如果离开了选中的添加行将获取信息并且上传数据库并重新加载
			if(dataTableType.getSelectedRow() != allType.size()-1 && isAdd==true) {
				BeanCarType addType = new BeanCarType();
				addType.setType_id(String.valueOf(tabTypeModel.getValueAt(allType.size()-1,0)));
				addType.setType_name(String.valueOf(tabTypeModel.getValueAt(allType.size()-1,1)));
				addType.setType_describe(String.valueOf(tabTypeModel.getValueAt(allType.size()-1,2)));
				try {
					new CarManager().addCarType(Check.nextID("car_type_tbl", "type_id", "type"), addType.getType_name(),addType.getType_describe());
					JOptionPane.showMessageDialog(null, "添加成功", "消息",JOptionPane.PLAIN_MESSAGE);
					refresh();
				} catch (BusinessException e1) {
					refresh();
					e1.printStackTrace();
				}
			}else if(dataTableType.getSelectedRow()!=modifyRow && isModify==true) {
				try {
					BeanCarType modifyType = new BeanCarType();
					modifyType.setType_id(String.valueOf(tabTypeModel.getValueAt(modifyRow,0)));
					modifyType.setType_name(String.valueOf(tabTypeModel.getValueAt(modifyRow,1)));
					modifyType.setType_describe(String.valueOf(tabTypeModel.getValueAt(modifyRow,2)));
					new CarManager().modifyCarType(modifyType.getType_id(), modifyType.getType_name(),modifyType.getType_describe());
					JOptionPane.showMessageDialog(null, "修改成功", "消息",JOptionPane.PLAIN_MESSAGE);
					refresh();
				} catch (BusinessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					refresh();
				}
			}
			
			}
			
	    });
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == this.button_add_type) {
			//添加一行并选中添加行
			isAdd = true;
			Object[] addRow = { "系统自动生成", "name", "describe"};
			tabTypeModel.insertRow(allType.size(), addRow);
			allType.add(new BeanCarType());
			int rowNum = allType.size()-1;
			dataTableType.setRowSelectionInterval(rowNum, rowNum);
			//之后在鼠标点击的事件监听中运行
		}else if(e.getSource() == this.button_change_type) {
			isModify=true;
			modifyRow = dataTableType.getSelectedRow();
		}else if(e.getSource() == this.button_delete_type) {
			try {
				new CarManager().deleteCarType(curType.getType_id());
				JOptionPane.showMessageDialog(null, "删除成功", "消息",JOptionPane.PLAIN_MESSAGE);
				refresh();
			} catch (BusinessException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				refresh();
			}
			
		}
		
	}
	
	private void reloadTypeTable(){
		try {
			allType = new CarManager().loadAllType();
			// allType.add(new BeanCarType());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
			System.out.println(e.getMessage());
			return;
		}
		TypeData =  new Object[allType.size()][BeanCarType.tableTitles.length];
		for(int i=0;i<allType.size();i++){
			for(int j=0;j<BeanCarType.tableTitles.length;j++)
				TypeData[i][j]=allType.get(i).getCell(j);
		}
		tabTypeModel.setDataVector(TypeData,tblTypeTitle);
		this.dataTableType.validate();
		this.dataTableType.repaint();
	}
	
	private void refresh() {
		isAdd=false;
		isModify=false;
		reloadTypeTable();
		dataTableType.updateUI();
	}

}
