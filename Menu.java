import java.awt.Color;
import java.awt.Font;
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

public class Menu extends JFrame implements ActionListener  {
	
	JButton newGame;
	JButton settings;
	JButton exit;
	JButton highScor;
	String setData[] = new String[3];
	Semaphore semaphoreMenu = new Semaphore(0);
	int todo;
	
	public void openConfig() throws FileNotFoundException {	
		File config = new File("src/config.txt");
		Scanner read = new Scanner(config);
		for(int i = 0; i < 3; i++) 
			setData[i] = read.nextLine();		
	}
	
	Menu(String username) throws InterruptedException, FileNotFoundException {
		//Ikony
		ImageIcon icon = new ImageIcon("src/pawnblank.jpg");
		
		//Przyciski
		newGame = new JButton();
		newGame.setBounds(0, 0, 100, 20);
		newGame.setText("NOWA GRA");
		newGame.addActionListener(this);
		newGame.setFocusable(false);
		
		highScor = new JButton();
		highScor.setBounds(0, 100, 100, 20);
		highScor.setText("NAJLEPSZE WYNIKI");
		highScor.addActionListener(this);
		highScor.setFocusable(false);
		
		settings = new JButton();
		settings.setBounds(0, 200, 100, 20);
		settings.setText("USTAWIENIE");
		settings.addActionListener(this);
		settings.setFocusable(false);
			
		exit = new JButton();
		exit.setBounds(0, 300, 100, 20);
		exit.setText("WYJDZ");
		exit.addActionListener(this);
		exit.setFocusable(false);
			
		//Napis
		JLabel welcome = new JLabel("MENU");
		welcome.setFont(new Font("Serif", Font.PLAIN, 30));
		//welcome.setAlignmentY(welcome.CENTER);

		//Panel
		JPanel txtPanel = new JPanel();
		txtPanel.setBackground(new Color(255, 255, 255));
		txtPanel.setBounds(0, 0, 500, 200 );
		txtPanel.add(welcome);
		
		JPanel btnPanel = new JPanel();
		btnPanel.setBounds(0, 200, 500, 300);
		btnPanel.add(newGame);
		btnPanel.add(highScor);
		btnPanel.add(settings);
		btnPanel.add(exit);
		btnPanel.setLayout(new GridLayout(4,4,20,20));
		
		//Ogolne okienko
		this.setTitle("Reversi");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setLayout(null);
		this.setSize(500, 535); // width, height
		this.setIconImage(icon.getImage());
		this.setVisible(true);
		this.getContentPane().setBackground(new Color(255, 255, 255));
		this.add(txtPanel);
		this.add(btnPanel);
		
		openConfig();
		semaphoreMenu.acquire();
		if(todo == 1) {
			if(Integer.parseInt(setData[0]) == 1) new Game(6, Integer.parseInt(setData[1]), Integer.parseInt(setData[2]), username);
			else if(Integer.parseInt(setData[0]) == 0) new Game(8, Integer.parseInt(setData[1]), Integer.parseInt(setData[2]), username);
		} else if(todo == 2) {
			new Settings(username);
		}
		else if(todo == 3) {
			new Highscore(username);
		}
			
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == newGame) {
			todo = 1;
			semaphoreMenu.release();
			this.dispose();
		}
		if(e.getSource() == settings) {
			todo = 2;
			semaphoreMenu.release();
			this.dispose();
		}
		if(e.getSource() == highScor) {
			todo = 3;
			semaphoreMenu.release();
			this.dispose();
		}
		if(e.getSource() == exit) {
			System.exit(1);
		}
	}
}
