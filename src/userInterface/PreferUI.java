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

import DBManager.Check;
import DBManager.NetManager;
import DBManager.PreferManager;
import model.BeanNet;
import model.BeanPrefer;
import util.BusinessException;

public class PreferUI extends JFrame implements ActionListener{
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JToolBar toolBar = new JToolBar();
	private JButton button_add_Prefer = new JButton("添加优惠");
	private JButton button_delete_Prefer = new JButton("删除优惠");
	private JButton button_change_Prefer = new JButton("修改优惠");
	
	private Object tblPreferTitle[]=BeanPrefer.tableTitles;
	private Object PreferData[][];
	DefaultTableModel tabPreferModel=new DefaultTableModel();
	private JTable dataTablePrefer=new JTable(tabPreferModel);
	
	private String currentStuff;
	private BeanPrefer curPrefer = null;
	List<BeanPrefer> allPrefer = new ArrayList<BeanPrefer>();
	
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
					PreferUI frame = new PreferUI("stuff1");
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
	public PreferUI(String currentStuff) throws BusinessException {
		this.currentStuff = currentStuff;
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		//buttons
		this.toolBar.add(button_add_Prefer);this.button_add_Prefer.addActionListener(this);
		this.toolBar.add(button_delete_Prefer);this.button_delete_Prefer.addActionListener(this);
		this.toolBar.add(button_change_Prefer);this.button_change_Prefer.addActionListener(this);
		this.contentPane.add(toolBar,BorderLayout.NORTH);
		
		//Title
		this.reloadPreferTable();
		this.contentPane.add(new JScrollPane(this.dataTablePrefer));
		setContentPane(contentPane);
		
		this.dataTablePrefer.addMouseListener(new MouseAdapter (){
			@Override
			public void mouseClicked(MouseEvent e) {
				int i=PreferUI.this.dataTablePrefer.getSelectedRow();
				if(i<0) {
					return;
				}
				curPrefer=allPrefer.get(i);
				System.out.println(i);
				
				//添加新Prefer,如果离开了选中的添加行将获取信息并且上传数据库并重新加载
				if(dataTablePrefer.getSelectedRow() != allPrefer.size()-1 && isAdd==true) {
					try {
						String netid = netList.get(String.valueOf(tabPreferModel.getValueAt(allPrefer.size()-1,1)));
						BeanPrefer addPrefer = new BeanPrefer();
						addPrefer.setPrefer_id(String.valueOf(tabPreferModel.getValueAt(allPrefer.size()-1,0)));
						addPrefer.setPrefer_net(netid);
						addPrefer.setPrefer_model(String.valueOf(tabPreferModel.getValueAt(allPrefer.size()-1,2)));
						addPrefer.setPrefer_amount(Float.parseFloat(String.valueOf(tabPreferModel.getValueAt(allPrefer.size()-1,3))));
						addPrefer.setPrefer_start(new SimpleDateFormat("yyyy-MM-dd").parse(String.valueOf(tabPreferModel.getValueAt(allPrefer.size()-1,4))));
						addPrefer.setPrefer_end(new SimpleDateFormat("yyyy-MM-dd").parse(String.valueOf(tabPreferModel.getValueAt(allPrefer.size()-1,5))));
						Check.isMatch(currentStuff, addPrefer.getPrefer_net());
						new PreferManager().addPrefer(Check.nextID("prefer_tbl", "prefer_id", "prefer"),addPrefer.getPrefer_net(),addPrefer.getPrefer_model(),addPrefer.getPrefer_amount(),addPrefer.getPrefer_start(),addPrefer.getPrefer_end());
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
				}else if(dataTablePrefer.getSelectedRow()!=modifyRow && isModify==true) {
					try {
						String netid = netList.get(String.valueOf(tabPreferModel.getValueAt(modifyRow,1)));
						BeanPrefer modifyPrefer = new BeanPrefer();
						modifyPrefer.setPrefer_id(String.valueOf(tabPreferModel.getValueAt(modifyRow,0)));
						modifyPrefer.setPrefer_net(netid);
						modifyPrefer.setPrefer_model(String.valueOf(tabPreferModel.getValueAt(modifyRow,2)));
						modifyPrefer.setPrefer_amount(Float.parseFloat(tabPreferModel.getValueAt(modifyRow,3).toString()));
						modifyPrefer.setPrefer_start(new Date(Long.valueOf(String.valueOf(tabPreferModel.getValueAt(modifyRow,4)))));
						modifyPrefer.setPrefer_end(new Date(Long.valueOf(String.valueOf(tabPreferModel.getValueAt(modifyRow,5)))));
						Check.isMatch(currentStuff, modifyPrefer.getPrefer_net());
						new PreferManager().addPrefer(modifyPrefer.getPrefer_id(),modifyPrefer.getPrefer_net(),modifyPrefer.getPrefer_model(),modifyPrefer.getPrefer_amount(),modifyPrefer.getPrefer_start(),modifyPrefer.getPrefer_end());
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
		if(e.getSource() == this.button_add_Prefer) {
			//添加一行并选中添加行
			isAdd = true;
			Object[] addRow = { "系统自动生成", "net", "model","amount","start","end" };
			tabPreferModel.insertRow(allPrefer.size(), addRow);
			allPrefer.add(new BeanPrefer());
			int rowNum = allPrefer.size()-1;
			dataTablePrefer.setRowSelectionInterval(rowNum, rowNum);
			//之后在鼠标点击的事件监听中运行
		}else if(e.getSource() == this.button_change_Prefer) {
			isModify=true;
			modifyRow = dataTablePrefer.getSelectedRow();
		}else if(e.getSource() == this.button_delete_Prefer) {
			try {
				new PreferManager().deletePrefer(curPrefer.getPrefer_id());
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
	
	private void reloadPreferTable() throws BusinessException{
		try {
			allPrefer = new PreferManager().loadAllPrefer();
//			allPrefer.add(new BeanPrefer());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
			System.out.println(e.getMessage());
			return;
		}
		PreferData =  new Object[allPrefer.size()][BeanPrefer.tableTitles.length];
		for(int i=0;i<allPrefer.size();i++){
			for(int j=0;j<BeanPrefer.tableTitles.length;j++)
				PreferData[i][j]=allPrefer.get(i).getCell(j);
		}
		tabPreferModel.setDataVector(PreferData,tblPreferTitle);
		
		//net
		List<BeanNet> allNet = new NetManager().loadAllNet();
		for (int i1 = 0; i1 < allNet.size(); i1++) {
			netList.put(allNet.get(i1).getNet_name(), allNet.get(i1).getNet_id());
		}
		JComboBox jboxNet= new JComboBox(netList.keySet().toArray());
		jboxNet.setSelectedIndex(0);
		TableColumnModel colNet = dataTablePrefer.getColumnModel();
		colNet.getColumn(1).setCellEditor(new DefaultCellEditor(jboxNet));
		
		this.dataTablePrefer.validate();
		this.dataTablePrefer.repaint();
	}
	
	private void refresh() throws BusinessException {
		isAdd=false;
		isModify=false;
		reloadPreferTable();
		dataTablePrefer.updateUI();
	}

}
