
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
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

public class Register extends JFrame implements ActionListener {
	
	Semaphore canRegis = new Semaphore(0);
	Semaphore canGoFur = new Semaphore(0);
	String accounts[] = new String[100];
	String passwords[] = new String[100];
	String highScores[][] = new String[100][2];
	int i, counter, counterhs;
	JButton regisBut;
	JButton backToLogBut;
	boolean tryToRegister;
	int toDo;
	
	public void openAccounts() throws FileNotFoundException {
		File accountFile = new File("src/accounts.txt");
		Scanner read = new Scanner(accountFile);
		counter = Integer.parseInt(read.nextLine());
		for(i = 0; i < counter; i++) {
			accounts[i] = read.nextLine();
			passwords[i] = read.nextLine(); 
			}
	}
	
	public void saveAccounts() throws FileNotFoundException {
		PrintWriter accountFile = new PrintWriter("src/accounts.txt");
		accountFile.flush();
		accountFile.println(counter);
		for(i = 0; i < counter; i++) { 
			accountFile.println(accounts[i]);
			accountFile.println(passwords[i]);
			}
		accountFile.close();
	}
	
	public void openHighScores() throws FileNotFoundException {
		File highScoresFile = new File("src/highscores.txt");
		Scanner read = new Scanner(highScoresFile);
		counterhs = Integer.parseInt(read.nextLine());
		for(i = 0; i < counterhs; i++) {
			highScores[i][0] = read.nextLine();
			highScores[i][1] = read.nextLine();
		}
	}
	
	public void saveHighScores() throws FileNotFoundException {
		PrintWriter highScoresFile = new PrintWriter("src/highscores.txt");
		highScoresFile.flush();
		highScoresFile.println(counter);
		for(i = 0; i < counter; i++) { 
			highScoresFile.println(highScores[i][0]);
			highScoresFile.println(highScores[i][1]);
			}
		highScoresFile.close();
	}
	
	
	boolean isUsernameValid(String user) {
		for(int i = 0; i < counter; i++) {
			if(accounts[i].equals(user))
				return false;
		}		
		return true;
	}
	
	Register() throws FileNotFoundException, InterruptedException {
		
		//Ikony
		ImageIcon icon = new ImageIcon("src/pawnblank.jpg");
		
		//Tekst
		JTextArea didRegis = new JTextArea();
		didRegis.setEditable(false);
		
		JLabel loginLab = new JLabel("LOGIN:");
		loginLab.setAlignmentX(loginLab.CENTER);
		JLabel passLab = new JLabel("HASLO:");
		passLab.setAlignmentX(passLab.CENTER);
		
		//Przyciski
		regisBut = new JButton("Zarejestruj siê");
		regisBut.addActionListener(this);
		
		backToLogBut = new JButton("Wróæ do ekranu logowania");
		backToLogBut.addActionListener(this);
		
		//Pola do wpisywania loginu i hasla
		JTextField loginField = new JTextField();
		
		JPasswordField passField = new JPasswordField();
		JPasswordField passFieldSec = new JPasswordField();
		
		//Panel
		JPanel mainPan = new JPanel();
		mainPan.setLayout(new GridLayout(5,1,0,0));
		mainPan.setBounds(0, 0, 500, 360);
		mainPan.add(loginLab);
		mainPan.add(loginField);
		mainPan.add(passLab);
		mainPan.add(passField);
		mainPan.add(passFieldSec);
		
		JPanel outPan = new JPanel();
		outPan.setBounds(0,360, 500, 120);
		outPan.add(didRegis);
		
		JPanel buttPan = new JPanel();
		buttPan.setLayout(new GridLayout(1,2,0,0));
		buttPan.setBounds(0, 480, 500, 160);
		buttPan.add(regisBut);
		buttPan.add(backToLogBut);
		
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
		tryToRegister = true;
		while(tryToRegister) {
			canRegis.acquire();
			if(isUsernameValid(loginField.getText())) {
				if(passField.getText().equals(passFieldSec.getText())) {
					didRegis.setText("Pomyslnie zarejestrowano, wróæ do ekranu logowania");
					openHighScores();
					accounts[counter] = highScores[counterhs][0] = loginField.getText();
					passwords[counter] = passField.getText();
					highScores[counterhs][1] = "0";
					counter++;
					saveHighScores();
					tryToRegister = false;
				}
				else {
					didRegis.setText("Podane hasla nie sa takie same");
				}
				
			}
			else {
				didRegis.setText("Podana nazwa uzytkownika jest juz zajeta");
			}
		}
		saveAccounts();
		canGoFur.acquire();
			new Login();

		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == regisBut ) {
			canRegis.release();
		}
		if (e.getSource() == backToLogBut) {
			canGoFur.release();
			this.dispose();
		}
	}
    
}