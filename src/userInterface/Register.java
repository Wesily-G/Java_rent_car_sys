package userInterface;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import DBManager.UserManager;
import util.BusinessException;

public class Register extends JDialog implements ActionListener {

	private final JPanel contentPanel = new JPanel();
	private JTextField TextUserID;
	private JPasswordField TextPassword;
	private JTextField TextPhone;
	private JTextField TextMail;
	private JTextField TextCity;
	private JButton okButton;
	private JButton cancelButton;
	private JRadioButton RadioButtonSex;
	private JTextField TextName;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Register dialog = new Register(new JDialog(), "Register", true);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Register (Dialog f, String s, boolean b) {
		super(f, s, b);
		setBounds(100, 100, 458, 334);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel LableUserID = new JLabel(" \u7528\u6237ID:");
		LableUserID.setBounds(95, 30, 54, 15);
		contentPanel.add(LableUserID);
		
		JLabel LabelPassword = new JLabel("  \u5BC6\u7801\uFF1A");
		LabelPassword.setBounds(95, 90, 54, 15);
		contentPanel.add(LabelPassword);
		
		JLabel LabelSex = new JLabel("  \u6027\u522B\uFF1A");
		LabelSex.setBounds(95, 120, 54, 15);
		contentPanel.add(LabelSex);
		
		JLabel LabelPhone = new JLabel("  \u7535\u8BDD\uFF1A");
		LabelPhone.setBounds(95, 150, 54, 15);
		contentPanel.add(LabelPhone);
		
		JLabel LabelMail = new JLabel("  \u90AE\u7BB1\uFF1A");
		LabelMail.setBounds(95, 180, 54, 15);
		contentPanel.add(LabelMail);
		
		JLabel LabelCity = new JLabel("  \u57CE\u5E02\uFF1A");
		LabelCity.setBounds(95, 210, 54, 15);
		contentPanel.add(LabelCity);
		
		TextUserID = new JTextField();
		TextUserID.setBounds(170, 27, 150, 21);
		contentPanel.add(TextUserID);
		TextUserID.setColumns(10);
		
		TextPassword = new JPasswordField();
		TextPassword.setBounds(170, 87, 150, 21);
		contentPanel.add(TextPassword);
		
		RadioButtonSex = new JRadioButton("   \u7537\u6027\u5219\u70B9\u6309");
		RadioButtonSex.setBounds(186, 116, 121, 23);
		contentPanel.add(RadioButtonSex);
		
		TextPhone = new JTextField();
		TextPhone.setBounds(170, 147, 150, 21);
		contentPanel.add(TextPhone);
		TextPhone.setColumns(10);
		
		TextMail = new JTextField();
		TextMail.setBounds(170, 177, 150, 21);
		contentPanel.add(TextMail);
		TextMail.setColumns(10);
		
		TextCity = new JTextField();
		TextCity.setBounds(170, 207, 150, 21);
		contentPanel.add(TextCity);
		TextCity.setColumns(10);
		
		TextName = new JTextField();
		TextName.setBounds(170, 56, 150, 21);
		contentPanel.add(TextName);
		TextName.setColumns(10);
		
		JLabel LabelName = new JLabel("  \u59D3\u540D\uFF1A");
		LabelName.setBounds(95, 60, 54, 15);
		contentPanel.add(LabelName);
		
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		okButton = new JButton("OK");
		this.okButton.addActionListener(this);
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);
		cancelButton = new JButton("Cancel");
		this.cancelButton.addActionListener(this);
		buttonPane.add(cancelButton);
		
	}

	@Override
	public void actionPerformed(ActionEvent e){
		if (e.getSource() == this.okButton) {
			//register user to database													+++++funciton_need
			String userid = this.TextUserID.getText();
			String name = this.TextName.getText();
			String pwd = new String(this.TextPassword.getPassword());
			Boolean sex = this.RadioButtonSex.isSelected();
			String phone = this.TextPhone.getText();
			String mail = this.TextMail.getText();
			String city = this.TextCity.getText();
			UserManager usermanager = new UserManager();
			try {
				usermanager.addUser(userid, name, sex, pwd, phone, mail, city);
			} catch (BusinessException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			this.setVisible(false);
		}else if (e.getSource() == this.cancelButton) {
			this.setVisible(false);
		}
	}
}
