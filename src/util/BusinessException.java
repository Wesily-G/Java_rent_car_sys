package util;

import javax.swing.JOptionPane;

public class BusinessException extends Exception {
	public BusinessException(String msg){
		JOptionPane.showMessageDialog(null,msg, "����",JOptionPane.PLAIN_MESSAGE);
	}
}
