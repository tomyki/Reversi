import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Login extends JFrame implements ActionListener {
	
	Semaphore canLogin = new Semaphore(0);
	Semaphore canGoFur = new Semaphore(0);
	String accounts[] = new String[100];
	String passwords[] = new String[100];
	int counter;
	JButton loginBut;
	JButton regisBut;
	boolean tryToLogin;
	int toDo;
	public void openAccounts() throws FileNotFoundException {
		File config = new File("src/accounts.txt");
		Scanner read = new Scanner(config);
		counter = Integer.parseInt(read.nextLine());
		for(int i = 0; i < counter; i++) {
			accounts[i] = read.nextLine();
			passwords[i] = read.nextLine(); 
			}
	}
	boolean isAccValid(String user, String password) {
		for(int i = 0; i < counter; i++) {
			if(accounts[i].equals(user) && passwords[i].equals(password))
				return true;
		}
		
		
		return false;
	}
	
	Login() throws FileNotFoundException, InterruptedException {
		
		//Ikony
		ImageIcon icon = new ImageIcon("src/pawnblank.jpg");
		
		//Tekst
		JTextArea didLogin = new JTextArea();
		didLogin.setEditable(false);
		
		JLabel loginLab = new JLabel("LOGIN:");
		loginLab.setAlignmentX(loginLab.CENTER);
		JLabel passLab = new JLabel("HASLO:");
		passLab.setAlignmentX(passLab.CENTER);
		
		//Przyciski
		loginBut = new JButton("Loguj");
		loginBut.addActionListener(this);
		
		regisBut = new JButton("Zarejestruj siê");
		regisBut.addActionListener(this);
		
		//Pola do wpisywania loginu i hasla
		JTextField loginField = new JTextField();
		
		JPasswordField passField = new JPasswordField();
		
		//Panel
		JPanel mainPan = new JPanel();
		mainPan.setLayout(new GridLayout(4,1,0,0));
		mainPan.setBounds(0, 0, 500, 360);
		mainPan.add(loginLab);
		mainPan.add(loginField);
		mainPan.add(passLab);
		mainPan.add(passField);
		
		JPanel outPan = new JPanel();
		outPan.setBounds(0,360, 500, 120);
		outPan.add(didLogin);
		
		JPanel buttPan = new JPanel();
		buttPan.setLayout(new GridLayout(1,2,0,0));
		buttPan.setBounds(0, 480, 500, 160);
		buttPan.add(loginBut);
		buttPan.add(regisBut);
		
		//Okno
		this.setTitle("LOGOWANIE");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setLayout(null);
		this.setSize(500, 640);
		this.setIconImage(icon.getImage());
		this.setVisible(true);
		this.getContentPane().setBackground(new Color(255, 255, 255));
		
		this.add(mainPan);
		this.add(outPan);
		this.add(buttPan);
		
		openAccounts();
		tryToLogin = true;
		while(tryToLogin) {
			canLogin.acquire();
			if(isAccValid(loginField.getText(), passField.getText())) {
				toDo = 1;
				tryToLogin = false;
				didLogin.setText("Pomyslnie zalogowano\nKliknij ponownie przycisk ¿eby przejœæ do menu");
			}
			else {
				didLogin.setText("Zle dane");
			}
		}
		canGoFur.acquire();
		if(toDo == 1) {
			new Menu(loginField.getText());  //DOPISAC USERNAME
		}
		else if(toDo ==2) {
			new Register();
		}
		
		
		
		
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == loginBut ) {
			canLogin.release();
			if(!tryToLogin) {
				canGoFur.release();
				this.dispose();
			}
		}
		if (e.getSource() == regisBut) {
			tryToLogin = false;
			toDo = 2;
			canLogin.release();
			canGoFur.release();
			this.dispose();
			
		}
	}
    
}