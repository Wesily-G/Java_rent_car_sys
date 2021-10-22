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
import DBManager.NetManager;
import DBManager.PreferManager;
import model.BeanPrefer;
import util.BusinessException;

public class PreferForUserUI extends JFrame implements ActionListener{
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JToolBar toolBar = new JToolBar();
	private JButton button_use = new JButton("领取并使用");
	
	private Object tblPreferTitle[]=BeanPrefer.tableTitles;
	private Object PreferData[][];
	DefaultTableModel tabPlanModel=new DefaultTableModel();
	private JTable dataTablePrefer=new JTable(tabPlanModel);
	
	private String user_id;
	private BeanPrefer curPrefer = null;
	List<BeanPrefer> allPrefer = new ArrayList<BeanPrefer>();
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PreferForUserUI frame = new PreferForUserUI("user32");
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
	public PreferForUserUI(String user_id) throws BusinessException {
		this.user_id=user_id;
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		//buttons
		this.toolBar.add(button_use);this.button_use.addActionListener(this);
		this.contentPane.add(toolBar,BorderLayout.NORTH);
		
		//Title
		this.reloadPreferTable();
		this.contentPane.add(new JScrollPane(this.dataTablePrefer));
		setContentPane(contentPane);
		
		this.dataTablePrefer.addMouseListener(new MouseAdapter (){
			@Override
			public void mouseClicked(MouseEvent e) {
				int i=PreferForUserUI.this.dataTablePrefer.getSelectedRow();
				if(i<0) {
					return;
				}
				curPrefer=allPrefer.get(i);
				System.out.println(i);
			}
	    });
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
        if(e.getSource() == this.button_use)
			try {
				new PreferManager().usePrefer(curPrefer.getPrefer_id(), user_id);
			} catch (BusinessException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
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
				if(j==1)
					PreferData[i][j]=new NetManager().findNet(String.valueOf(allPrefer.get(i).getCell(j))).getNet_name();
				else if(j==2)
					PreferData[i][j]=new CarManager().findCarModel(String.valueOf(allPrefer.get(i).getCell(j))).getModel_name();
				else
					PreferData[i][j]=allPrefer.get(i).getCell(j);
		}
		tabPlanModel.setDataVector(PreferData,tblPreferTitle);
		this.dataTablePrefer.validate();
		this.dataTablePrefer.repaint();
	}

}
