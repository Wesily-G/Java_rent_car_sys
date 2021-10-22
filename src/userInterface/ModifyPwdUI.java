package userInterface;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

import DBManager.StuffManager;
import DBManager.UserManager;
import util.BusinessException;

public class ModifyPwdUI extends JDialog implements ActionListener {

	private final JPanel contentPanel = new JPanel();
	private JPasswordField TextOldPassword;
	private JPasswordField TextNewPassword1;
    private JPasswordField TextNewPassword2;
	private JButton okButton;
	private JButton cancelButton;
	private boolean isUser;
	private String id;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ModifyPwdUI dialog = new ModifyPwdUI(new JDialog(), "ModifyPwd", true, true, "user32");
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ModifyPwdUI (Dialog f, String s, boolean b,boolean isUser, String id) {
		super(f, s, b);
		this.isUser=isUser;
		this.id=id;
		setBounds(100, 100, 458, 334);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel LableOldPassword = new JLabel(" \u65E7\u5BC6\u7801\uFF1A");
		LableOldPassword.setBounds(95, 60, 54, 15);
		contentPanel.add(LableOldPassword);
		
		JLabel LabelNewPassword1 = new JLabel(" \u65B0\u5BC6\u7801\uFF1A");
		LabelNewPassword1.setBounds(95, 100, 54, 15);
		contentPanel.add(LabelNewPassword1);
		
		JLabel LabelNewPassword2 = new JLabel(" \u65B0\u5BC6\u7801\uFF1A");
		LabelNewPassword2.setBounds(95, 140, 54, 15);
		contentPanel.add(LabelNewPassword2);
		
		TextOldPassword = new JPasswordField();
		TextOldPassword.setBounds(170, 57, 150, 21);
		contentPanel.add(TextOldPassword);
		TextOldPassword.setColumns(10);
		
		TextNewPassword1 = new JPasswordField();
		TextNewPassword1.setBounds(170, 97, 150, 21);
		contentPanel.add(TextNewPassword1);

        TextNewPassword2 = new JPasswordField();
		TextNewPassword2.setBounds(170, 137, 150, 21);
		contentPanel.add(TextNewPassword2);
		
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
			String oldPassword = new String(this.TextOldPassword.getPassword());
			String newPassword1 = new String(this.TextNewPassword1.getPassword());
            String newPassword2 = new String(this.TextNewPassword2.getPassword());
			try{
				if(!newPassword1.equals(newPassword2))
					throw new BusinessException("Input new passwords are not same.");
				else if(oldPassword.equals(newPassword1))
					throw new BusinessException("Input new password and old password are same.");
				else if(isUser)
					new UserManager().modifyPwd(id, oldPassword, newPassword1);
				else
					new StuffManager().modifyPwd(id, oldPassword, newPassword1);

				JOptionPane.showMessageDialog(null, "修改成功", "消息",JOptionPane.PLAIN_MESSAGE);
				this.setVisible(false);
			}catch(BusinessException e1){
				e1.printStackTrace();
			}

			

		}else if (e.getSource() == this.cancelButton) {
			this.setVisible(false);
		}
	}
}
