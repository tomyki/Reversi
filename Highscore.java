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
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Highscore extends JFrame implements ActionListener {
	
	String accounts[][] = new String[100][2];
	int counter;
	String curPlaSco, tempName, tempScore;
	Semaphore backToMenSem = new Semaphore(0);
	JButton backToMenBut;
	
	public void openScores(String username) throws FileNotFoundException {
		File highScores = new File("src/highscores.txt");
		Scanner read = new Scanner(highScores);
		counter = Integer.parseInt(read.nextLine());
		int i,j;
		for(i = 0; i < counter; i++) {
			tempName = read.nextLine();
			tempScore = read.nextLine();
			if(i == 0) {
				accounts[i][0] = tempName;
				accounts[i][1] = tempScore;
				}
			else if(i == 1) {
				if(Integer.parseInt(tempScore) > Integer.parseInt(accounts[0][1])) {
					accounts[1][0] = accounts[0][0];
					accounts[1][1] = accounts[0][1];
					accounts[0][0] = tempName;
					accounts[0][1] = tempScore;
				}
				else {
					accounts[1][0] = tempName;
					accounts[1][1] = tempScore;
				}
			}
			else {
				j = i-1;
				while(j>=0 && Integer.parseInt(tempScore) > Integer.parseInt(accounts[j][1])) {
					accounts[j+1][0] = accounts[j][0];
					accounts[j+1][1] = accounts[j][1];
					j = j - 1;
				}
				accounts[j+1][0] = tempName;
				accounts[j+1][1] = tempScore;
			}
			
			}
		for(i = 0; i < counter; i++) {
			if(accounts[i][0].equals(username)) {
				curPlaSco = String.valueOf(i+1) + ". " +  accounts[i][0] + " " + accounts[i][1];
				}
		}
	}

	Highscore(String user) throws FileNotFoundException, InterruptedException {
		openScores(user);
		String firPla = "1. " + accounts[0][0] + " " + accounts[0][1];
		String secPla = "2. " + accounts[1][0] + " " + accounts[1][1];
		String thiPla = "3. " + accounts[2][0] + " " + accounts[2][1];
		String fouPla = "4. " + accounts[3][0] + " " + accounts[3][1];
		String fifPla = "5. " + accounts[4][0] + " " + accounts[4][1];
		JTextArea firScore = new JTextArea(firPla);
		firScore.setFont(new Font("Serif", Font.PLAIN, 30));
		firScore.setBounds(0, 0, 500, 60);
		firScore.setEditable(false);
		JTextArea secScore = new JTextArea(secPla);
		secScore.setFont(new Font("Serif", Font.PLAIN, 30));
		secScore.setBounds(0, 60, 500, 60);
		secScore.setEditable(false);
		JTextArea thiScore = new JTextArea(thiPla);
		thiScore.setFont(new Font("Serif", Font.PLAIN, 30));
		thiScore.setBounds(0, 120, 500, 60);
		thiScore.setEditable(false);
		JTextArea fouScore = new JTextArea(fouPla);
		fouScore.setFont(new Font("Serif", Font.PLAIN, 30));
		fouScore.setBounds(0, 180, 500, 60);
		fouScore.setEditable(false);
		JTextArea fifScore = new JTextArea(fifPla);
		fifScore.setFont(new Font("Serif", Font.PLAIN, 30));
		fifScore.setBounds(0, 240, 500, 60);
		fifScore.setEditable(false);
		JTextArea noScore = new JTextArea("Twoja pozycja w rankingu:");
		noScore.setFont(new Font("Serif", Font.PLAIN, 30));
		noScore.setBounds(0, 300, 500, 60);
		noScore.setEditable(false);
		JTextArea yourScore = new JTextArea(curPlaSco);
		yourScore.setFont(new Font("Serif", Font.PLAIN, 30));
		yourScore.setBounds(0, 360, 500, 60);
		yourScore.setEditable(false);
		
		backToMenBut = new JButton("WRÓÆ DO MENU");
		backToMenBut.addActionListener(this);
		backToMenBut.setSize(500, 100);
		backToMenBut.setFocusable(false);
		
		JPanel scoresPan = new JPanel();
		scoresPan.setLayout(new GridLayout(7,1,0,0));
		scoresPan.setBounds(0,0,500, 420);
		scoresPan.add(firScore);
		scoresPan.add(secScore);
		scoresPan.add(thiScore);
		scoresPan.add(fouScore);
		scoresPan.add(fifScore);
		scoresPan.add(noScore);
		scoresPan.add(yourScore);

		
		JPanel butPan = new JPanel();
		butPan.setBounds(0,420, 500, 80);
		butPan.add(backToMenBut);
		
		
		ImageIcon icon = new ImageIcon("src/pawnblank.jpg");
		this.setTitle("High Scores");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setLayout(null);
		this.setSize(500, 535); // width, height
		this.setIconImage(icon.getImage());
		this.setVisible(true);
		this.getContentPane().setBackground(new Color(255, 255, 255));
		
		this.add(scoresPan);
		this.add(butPan);
		
		backToMenSem.acquire();
		new Menu(user);
	
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e){
		if(e.getSource() == backToMenBut) {
			backToMenSem.release();
			this.dispose();
		}
		
	}
}
