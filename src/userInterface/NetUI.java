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
import javax.swing.table.TableColumn;

import DBManager.Check;
import DBManager.NetManager;
import model.BeanNet;
import util.BusinessException;

public class NetUI extends JFrame implements ActionListener{
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JToolBar toolBar = new JToolBar();
	private JButton button_add_Net = new JButton("�������");
	private JButton button_delete_Net = new JButton("ɾ������");
	private JButton button_change_Net = new JButton("�޸�����");
	
	private Object tblNetTitle[]=BeanNet.tableTitles;
	private Object NetData[][];
	DefaultTableModel tabNetModel=new DefaultTableModel();
	private JTable dataTableNet=new JTable(tabNetModel) {
		public boolean isCellEditable(int rowindex,int colindex){
			if (colindex==0) return false;
			return true;
		}
	};
	
	private BeanNet curNet = null;
	List<BeanNet> allNet = new ArrayList<BeanNet>();
	
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
					NetUI frame = new NetUI();
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
	public NetUI() {
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		//buttons
		this.toolBar.add(button_add_Net);this.button_add_Net.addActionListener(this);
		this.toolBar.add(button_delete_Net);this.button_delete_Net.addActionListener(this);
		this.toolBar.add(button_change_Net);this.button_change_Net.addActionListener(this);
		this.contentPane.add(toolBar,BorderLayout.NORTH);
		
		//Title
		this.reloadNetTable();
		this.contentPane.add(new JScrollPane(this.dataTableNet));
		setContentPane(contentPane);
		
		this.dataTableNet.addMouseListener(new MouseAdapter (){
			@Override
			public void mouseClicked(MouseEvent e) {
				int i=NetUI.this.dataTableNet.getSelectedRow();
				if(i<0) {
					return;
				}
				curNet=allNet.get(i);
				System.out.println(i);
				
				//�����Net,����뿪��ѡ�е�����н���ȡ��Ϣ�����ϴ����ݿⲢ���¼���
				if(dataTableNet.getSelectedRow() != allNet.size()-1 && isAdd==true) {
					BeanNet addNet = new BeanNet();
					addNet.setNet_id(String.valueOf(tabNetModel.getValueAt(allNet.size()-1,0)));
					addNet.setNet_name(String.valueOf(tabNetModel.getValueAt(allNet.size()-1,1)));
					addNet.setNet_city(String.valueOf(tabNetModel.getValueAt(allNet.size()-1,2)));
					addNet.setNet_address(String.valueOf(tabNetModel.getValueAt(allNet.size()-1,3)));
					addNet.setNet_phone(String.valueOf(tabNetModel.getValueAt(allNet.size()-1,4)));
					try {
						new NetManager().addNet(Check.nextID("net_tbl", "net_id", "net"), addNet.getNet_name(), addNet.getNet_city(), addNet.getNet_address(), addNet.getNet_phone());
						JOptionPane.showMessageDialog(null, "��ӳɹ�", "��Ϣ",JOptionPane.PLAIN_MESSAGE);
						refresh();
					} catch (BusinessException e1) {
						refresh();
						e1.printStackTrace();
					}
				}else if(dataTableNet.getSelectedRow()!=modifyRow && isModify==true) {
					try {
						BeanNet modifyNet = new BeanNet();
						modifyNet.setNet_id(String.valueOf(tabNetModel.getValueAt(modifyRow,0)));
						modifyNet.setNet_name(String.valueOf(tabNetModel.getValueAt(modifyRow,1)));
						modifyNet.setNet_city(String.valueOf(tabNetModel.getValueAt(modifyRow,2)));
						modifyNet.setNet_address(String.valueOf(tabNetModel.getValueAt(modifyRow,3)));
						modifyNet.setNet_phone(String.valueOf(tabNetModel.getValueAt(modifyRow,4)));
						new NetManager().modifyNet(modifyNet.getNet_id(),modifyNet.getNet_name(),modifyNet.getNet_city(),modifyNet.getNet_address(),modifyNet.getNet_phone());
						JOptionPane.showMessageDialog(null, "�޸ĳɹ�", "��Ϣ",JOptionPane.PLAIN_MESSAGE);
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
		if(e.getSource() == this.button_add_Net) {
			//���һ�в�ѡ�������
			isAdd = true;
			Object[] addRow = { "ϵͳ�Զ�����", "name", "city","address","phone" };
			tabNetModel.insertRow(allNet.size(), addRow);
			allNet.add(new BeanNet());
			int rowNum = allNet.size()-1;
			dataTableNet.setRowSelectionInterval(rowNum, rowNum);
			//֮������������¼�����������
		}else if(e.getSource() == this.button_change_Net) {
			isModify=true;
			modifyRow = dataTableNet.getSelectedRow();
		}else if(e.getSource() == this.button_delete_Net) {
			try {
				new NetManager().deleteNet(curNet.getNet_id());
				JOptionPane.showMessageDialog(null, "ɾ���ɹ�", "��Ϣ",JOptionPane.PLAIN_MESSAGE);
				refresh();
			} catch (BusinessException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				refresh();
			}
			
		}
		
	}
	
	private void reloadNetTable(){
		try {
			allNet = new NetManager().loadAllNet();
			// allNet.add(new BeanNet());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
			System.out.println(e.getMessage());
			return;
		}
		NetData =  new Object[allNet.size()][BeanNet.tableTitles.length];
		for(int i=0;i<allNet.size();i++){
			for(int j=0;j<BeanNet.tableTitles.length;j++)
				NetData[i][j]=allNet.get(i).getCell(j);
		}
		tabNetModel.setDataVector(NetData,tblNetTitle);
		this.dataTableNet.validate();
		this.dataTableNet.repaint();
	}
	
	private void refresh() {
		isAdd=false;
		isModify=false;
		reloadNetTable();
		dataTableNet.updateUI();
	}

}
