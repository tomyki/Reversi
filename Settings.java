import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class Settings extends JFrame implements ActionListener  {

	JButton save;
	JRadioButton table[] = new JRadioButton[6];
	String setData[] = new String[4];
	Semaphore semaphoreSettings = new Semaphore(0);
	
	public void openConfig() throws FileNotFoundException {
		File config = new File("src/config.txt");
		Scanner read = new Scanner(config);
		for(int i = 0; i < 3; i++) 
			setData[i] = read.nextLine();		
	}
	
	public void saveConfig() throws FileNotFoundException {
		PrintWriter config = new PrintWriter("src/config.txt");
		config.flush();
		for(int i = 0; i < 3; i++) 
			config.println(setData[i]);
		config.close();
	}
	
	
	
	
	Settings(String username) throws FileNotFoundException, InterruptedException {
				
		//Ikony
		ImageIcon icon = new ImageIcon("src/pawnblank.jpg");
		
		//Przyciski
		save = new JButton();
		save.setText("SAVE");
		save.setBounds(150, 20, 200, 80);
		save.addActionListener(this);
		save.setFocusable(false);
		
		//Przyciski zaznaczajace
		for(int i = 0; i<6; i++) {
			table[i] = new JRadioButton();
			table[i].setFocusable(false);
			table[i].addActionListener(this);
		}
		table[0].setText("6x6");		
		table[1].setText("8x8");		
		table[2].setText("Black and White Color");		
		table[3].setText("Blue and Red Color");		
		table[4].setText("Player vs Player");		
		table[5].setText("Player vs computer");
		
		//Grupy przyciskow zaznaczajacych
		ButtonGroup size = new ButtonGroup();
		size.add(table[0]);
		size.add(table[1]);
		
		ButtonGroup color = new ButtonGroup();
		color.add(table[2]);
		color.add(table[3]);
		
		ButtonGroup enemy = new ButtonGroup();
		enemy.add(table[4]);
		enemy.add(table[5]);
		
		//Panele
		JPanel checkPanel = new JPanel();
		checkPanel.setBounds(0, 0, 500, 380);
		checkPanel.setLayout(new GridLayout(2,3));
		for(int i = 0; i<2; i++) {
			checkPanel.add(table[i]);
			checkPanel.add(table[i+2]);
			checkPanel.add(table[i+4]);
		}

		JPanel savePanel = new JPanel();
		savePanel.setBounds(0, 380, 500, 120);
		savePanel.add(save);
		
		//Ogolne okienko
		this.setTitle("Settings");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setLayout(null);
		this.setSize(500, 500); // width, height
		this.setIconImage(icon.getImage());
		this.setVisible(true);
		this.getContentPane().setBackground(new Color(255, 255, 255));
		
		this.add(checkPanel);
		this.add(savePanel);
		
		//Zmiana danych na przyciskach
		openConfig();
		for(int i = 0; i<3; i++) {
			if(setData[i].equals("1")) {
				table[i*2].setSelected(true);
			}
			else {
				table[i*2+1].setSelected(true);
			}
		}
		semaphoreSettings.acquire();
		saveConfig();
		new Menu(username);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == save) {
			semaphoreSettings.release();
			this.dispose();
		}
		if(e.getSource() == table[0]) {
			setData[0] = "1";
		}
		if(e.getSource() == table[1]) {
			setData[0] = "0";
		}
		if(e.getSource() == table[2]) {
			setData[1] = "1";
		}
		if(e.getSource() == table[3]) {
			setData[1] = "0";
		}
		if(e.getSource() == table[4]) {
			setData[2] = "1";
		}
		if(e.getSource() == table[5]) {
			setData[2] = "0";
		}

	}
	
}
