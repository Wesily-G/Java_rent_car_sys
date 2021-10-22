package userInterface;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import DBManager.StuffManager;
import DBManager.UserManager;

public class LogIn extends JDialog implements ActionListener {

	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private JButton btnLogin = new JButton("µÇÂ½");
	private JButton btnCancel = new JButton("ÍË³ö");
	private JButton btnRegister = new JButton("×¢²á");
	private JRadioButton RadioButtonStuff = new JRadioButton("\u5458\u5DE5\u7BA1\u7406\u5458\u6A21\u5F0F");
	
	private JLabel labelUser = new JLabel("ÓÃ»§£º");
	private JLabel labelPwd = new JLabel("ÃÜÂë£º");
	private JTextField edtUserId = new JTextField(20);
	private JPasswordField edtPwd = new JPasswordField(20);

	public LogIn(Frame f, String s, boolean b) {
		super(f, s, b);
		setTitle("CC\u79DF\u8F66\u7CFB\u7EDF");
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(this.btnRegister);
		toolBar.add(btnLogin);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.setLayout(null);
		labelUser.setBounds(42, 8, 46, 15);
		workPane.add(labelUser);
		edtUserId.setBounds(98, 5, 126, 21);
		workPane.add(edtUserId);
		labelPwd.setBounds(42, 33, 46, 15);
		workPane.add(labelPwd);
		edtPwd.setBounds(98, 30, 126, 21);
		workPane.add(edtPwd);
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		RadioButtonStuff.setBounds(168, 57, 121, 23);
		workPane.add(RadioButtonStuff);
		this.setSize(311, 153);
		// ÆÁÄ»¾ÓÖÐÏÔÊ¾
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();

		btnLogin.addActionListener(this);
		btnCancel.addActionListener(this);
		this.btnRegister.addActionListener(this);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == this.btnLogin) {
			Boolean ifstuff=this.RadioButtonStuff.isSelected();
			String userid=this.edtUserId.getText();
			String pwd=new String(this.edtPwd.getPassword());
			try {
				//login
				if(ifstuff){
					if(new StuffManager().login(userid, pwd))
						new MainUI(userid).setVisible(true);
						this.setVisible(false);
				}else if(new UserManager().login(userid, pwd)){
					new MainUIforUser(userid).setVisible(true);
					this.setVisible(false);
				}
				
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "´íÎó",JOptionPane.ERROR_MESSAGE);
				return;
			}
			
		} else if (e.getSource() == this.btnCancel) {
			System.exit(0);
		} else if(e.getSource()==this.btnRegister){
			Register dlg=new Register(this,"×¢²á",true);
			dlg.setVisible(true);
		}
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			LogIn dialog = new LogIn(new JFrame(), "LogIn", true);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
