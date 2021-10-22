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
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import DBManager.Check;
import DBManager.NetManager;
import DBManager.StuffManager;
import model.BeanStuff;
import util.BusinessException;
import model.BeanNet;
import model.BeanOrder;

import javax.swing.JToolBar;

public class StuffUI extends JFrame implements ActionListener{
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JToolBar toolBar = new JToolBar();
	private JButton button_add_Stuff = new JButton("添加员工");
	private JButton button_delete_Stuff = new JButton("删除员工");
	private JButton button_change_Stuff = new JButton("修改员工");
	
	private Object tblStuffTitle[]=BeanStuff.tableTitles;
	private Object StuffData[][];
	DefaultTableModel tabStuffModel=new DefaultTableModel();
	private JTable dataTableStuff=new JTable(tabStuffModel);
	
	private BeanStuff curStuff = null;
	List<BeanStuff> allStuff = new ArrayList<BeanStuff>();
	
	private HashMap<String, String> netList = new HashMap<String, String>();
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
					StuffUI frame = new StuffUI();
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
	public StuffUI() throws BusinessException {
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		//buttons
		this.toolBar.add(button_add_Stuff);this.button_add_Stuff.addActionListener(this);
		this.toolBar.add(button_delete_Stuff);this.button_delete_Stuff.addActionListener(this);
		this.toolBar.add(button_change_Stuff);this.button_change_Stuff.addActionListener(this);
		this.contentPane.add(toolBar,BorderLayout.NORTH);
		
		//Title
		this.reloadStuffTable();
		this.contentPane.add(new JScrollPane(this.dataTableStuff));
		setContentPane(contentPane);
		
		this.dataTableStuff.addMouseListener(new MouseAdapter (){
			@Override
			public void mouseClicked(MouseEvent e) {
				int i=StuffUI.this.dataTableStuff.getSelectedRow();
				if(i<0) {
					return;
				}
				curStuff=allStuff.get(i);
				System.out.println(i);
			
			//添加新Stuff,如果离开了选中的添加行将获取信息并且上传数据库并重新加载
			if(dataTableStuff.getSelectedRow() != allStuff.size()-1 && isAdd==true) {
				String netid = netList.get(String.valueOf(tabStuffModel.getValueAt(allStuff.size()-1,1)));
				BeanStuff addStuff = new BeanStuff();
				addStuff.setStuff_id(String.valueOf(tabStuffModel.getValueAt(allStuff.size()-1,0)));
				addStuff.setNet_id(netid);
				addStuff.setStuff_name(String.valueOf(tabStuffModel.getValueAt(allStuff.size()-1,2)));
				addStuff.setStuff_password(String.valueOf(tabStuffModel.getValueAt(allStuff.size()-1,3)));
				try {
					new StuffManager().addStuff(Check.nextID("stuff_tbl", "stuff_id", "stuff"), addStuff.getNet_id(),addStuff.getStuff_name(),addStuff.getStuff_password());
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
			}else if(dataTableStuff.getSelectedRow()!=modifyRow && isModify==true) {
				try {
					String netid = netList.get(String.valueOf(tabStuffModel.getValueAt(modifyRow,1)));
					BeanStuff modifyStuff = new BeanStuff();
					modifyStuff.setStuff_id(String.valueOf(tabStuffModel.getValueAt(modifyRow,0)));
					modifyStuff.setNet_id(netid);
					modifyStuff.setStuff_name(String.valueOf(tabStuffModel.getValueAt(modifyRow,2)));
					modifyStuff.setStuff_password(String.valueOf(tabStuffModel.getValueAt(modifyRow,3)));
					new StuffManager().modifyInfo(modifyStuff.getStuff_id(),modifyStuff.getNet_id(),modifyStuff.getStuff_name(),modifyStuff.getStuff_password());
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
		if(e.getSource() == this.button_add_Stuff) {
			//添加一行并选中添加行
			isAdd = true;
			Object[] addRow = { "系统自动生成", "net_id", "name","password"};
			tabStuffModel.insertRow(allStuff.size(), addRow);
			allStuff.add(new BeanStuff());
			int rowNum = allStuff.size()-1;
			dataTableStuff.setRowSelectionInterval(rowNum, rowNum);
			//之后在鼠标点击的事件监听中运行
		}else if(e.getSource() == this.button_change_Stuff) {
			isModify=true;
			modifyRow = dataTableStuff.getSelectedRow();
		}else if(e.getSource() == this.button_delete_Stuff) {
			try {
				new StuffManager().deleteStuff(curStuff.getStuff_id());
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
	
	private void reloadStuffTable() throws BusinessException{
		try {
			allStuff = new StuffManager().loadAllStuff();
//			allStuff.add(new BeanStuff());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
			System.out.println(e.getMessage());
			return;
		}
		StuffData =  new Object[allStuff.size()][BeanStuff.tableTitles.length];
		for(int i=0;i<allStuff.size();i++){
			for(int j=0;j<BeanStuff.tableTitles.length;j++)
				StuffData[i][j]=allStuff.get(i).getCell(j);
		}
		tabStuffModel.setDataVector(StuffData,tblStuffTitle);
		
		//net
		List<BeanNet> allNet = new NetManager().loadAllNet();
		for (int i1 = 0; i1 < allNet.size(); i1++) {
			netList.put(allNet.get(i1).getNet_name(), allNet.get(i1).getNet_id());
		}
		JComboBox jboxNet= new JComboBox(netList.keySet().toArray());
		jboxNet.setSelectedIndex(0);
		TableColumnModel colNet = dataTableStuff.getColumnModel();
		colNet.getColumn(1).setCellEditor(new DefaultCellEditor(jboxNet));
		
		this.dataTableStuff.validate();
		this.dataTableStuff.repaint();
	}
	
	private void refresh() throws BusinessException {
		isAdd=false;
		isModify=false;
		reloadStuffTable();
		dataTableStuff.updateUI();
	}

}
