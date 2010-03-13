package studiodietetico;

import hibernate.Utente;

import java.awt.Frame;
import javax.swing.JDialog;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import java.awt.Rectangle;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JLabel;

import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import command.UtenteDAO;

public class LoginDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel jPanel = null;
	private JButton btnEntra = null;
	private JPasswordField txtPassword = null;
	private JTextField txtUsername = null;
	private JLabel lblUsername = null;
	private JLabel lblPassword = null;
	public int risultato = 0;
	/**
	 * @param owner
	 */
	public LoginDialog(Frame owner) {
		super(owner);
		this.setModal(true);
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(300, 200);
		this.setContentPane(getJPanel());
		this.setTitle("Login");
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			lblPassword = new JLabel();
			lblPassword.setBounds(new Rectangle(68, 66, 58, 16));
			lblPassword.setText("Password");
			lblUsername = new JLabel();
			lblUsername.setBounds(new Rectangle(71, 32, 59, 16));
			lblUsername.setText("Username");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(getBtnEntra(), null);
			jPanel.add(getTxtPassword(), null);
			jPanel.add(getTxtUsername(), null);
			jPanel.add(lblUsername, null);
			jPanel.add(lblPassword, null);
		}
		return jPanel;
	}

	/**
	 * This method initializes btnEntra	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnEntra() {
		if (btnEntra == null) {
			btnEntra = new JButton();
			btnEntra.setBounds(new Rectangle(119, 119, 76, 21));
			btnEntra.setText("Entra");
			btnEntra.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					abilita(txtUsername.getText().trim(), txtPassword.getText().trim());
				}
			});
		}
		return btnEntra;
	}

	/**
	 * This method initializes txtPassword	
	 * 	
	 * @return javax.swing.JPasswordField	
	 */
	private JPasswordField getTxtPassword() {
		if (txtPassword == null) {
			txtPassword = new JPasswordField();
			txtPassword.setBounds(new Rectangle(151, 65, 93, 20));
		}
		return txtPassword;
	}

	/**
	 * This method initializes txtUsername	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTxtUsername() {
		if (txtUsername == null) {
			txtUsername = new JTextField();
			txtUsername.setBounds(new Rectangle(146, 28, 98, 20));
		}
		return txtUsername;
	}
	
	private void abilita(String username, String password){
		Utente ute = UtenteDAO.get(username, password);
		if( ute != null){
			Activator.setUser(ute);
			risultato = 1;
			this.dispose();
		}else{
			MessageBox msg = new MessageBox(new Shell());
			msg.setMessage("Nome utente o password non corrette");
			msg.open();
			risultato = 0;
			this.dispose();
		}
	}

}
