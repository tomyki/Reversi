import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;


public class Game extends JFrame implements ActionListener {
	
	Semaphore semaphore = new Semaphore(0);
	Semaphore start = new Semaphore(0);
	Semaphore backtomenu = new Semaphore(0);
	Point toRem;
	JButton startgamebut;
	JButton menubut;
	int i, counter;
	String highScores[][] = new String[100][2];
	Thread currentThread = Thread.currentThread();
	
	static String getNameOfPlayer(int color, int which) {
		if(color == 0) { 
			if(which == 1) return "niebieski ";
			else if(which == -1) return "czerwony ";
		}
		else if(color == 1) {
			if(which == 1) return "czarny ";
			else if(which == -1) return "bialy ";
		}
		return null;
	}
	
	public void openHighScores() throws FileNotFoundException {
		File highScoresFile = new File("src/highscores.txt");
		Scanner read = new Scanner(highScoresFile);
		counter = Integer.parseInt(read.nextLine());
		for(i = 0; i < counter; i++) {
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
	
	int searchForIndex(String user) {
		for(i = 0; i < counter; i++) {
			if(highScores[i][0].equals(user)) {
				return i;
			}
		}
		return -1;
	}
	
	
	Game(int size, int color, int pv, String username) throws InterruptedException, FileNotFoundException {
		//color 1 - czarny, bialy i szary, 0 - niebieski, czerwony i kremowy
		//pv 1 - na gracza, 0 - na komputer
		toRem = new Point(-1,-1);
		
		//ikony
		ImageIcon blankP = new ImageIcon("src/pawnblank.jpg");
		
		//Tworzenie tablicy
		Board board = new Board(size, color);
		
		//Panel do "szachownicy"
		JPanel boardPan = new JPanel();
		boardPan.setBounds(0, 0, size*60, size*60);
		boardPan.setLayout(new GridLayout(size,size,0,0));
		for(int row = 0; row < size; row++) 
			for(int col = 0; col < size; col++) {
				boardPan.add(board.tabpawns[row][col]);		
				board.tabpawns[row][col].addActionListener(this);
			}

		
		//Czas
		JLabel time = new JLabel("0");
		time.setBounds(size*20, size*15, 0, 0 );
		time.setSize(size*20,  size*15);
		time.setFont(new Font("Serif", Font.PLAIN, 25));
		time.setHorizontalAlignment(time.CENTER);
		Timer t = new Timer(time);
		Thread timer = new Thread(t);
		
		//Wynik
		JTextArea score = new JTextArea();
		score.setBounds(size*20, size*15, 0, 0 );
		score.setSize(size*20,  size*15);
		score.setFont(new Font("Serif", Font.PLAIN, 10));
		score.setLineWrap(true);
		score.setEnabled(false);
	
		
		startgamebut = new JButton("Start Game");
		startgamebut.setBounds(size*20, size*15, 0, 0 );
		startgamebut.setSize(size*20,  size*15);
		startgamebut.addActionListener(this);
		
		menubut = new JButton("Back to MENU");
		menubut.setBounds(size*20, size*15, 0, 0 );
		menubut.setSize(size*20,  size*15);
		menubut.addActionListener(this);
		menubut.setEnabled(false);
		
		//Panel do reszty
		JPanel infoPan = new JPanel();
		infoPan.setBounds(size*60, 0, size*26, size*60);
		infoPan.setLayout(new GridLayout(4,1,0,0));
		infoPan.add(time);
		infoPan.add(score);
		infoPan.add(startgamebut);
		infoPan.add(menubut);
		
		
		//glowne
		this.setTitle("Reversi");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setLayout(null);
		this.setSize(size*90, size*67); // width, height
		this.setIconImage(blankP.getImage());
		this.setVisible(true);
		this.getContentPane().setBackground(new Color(255, 255, 255));
		this.add(boardPan);
		this.add(infoPan);
		//System.out.println("TEST");
		
		//Gra
		start.acquire();
		timer.start();
		board.startGame();
		boolean stop = false;
		while(!stop) {
			HashSet<Point> placPosCur = board.getPlaceablePositions(board.getCurPla());
			HashSet<Point> placPosEne = board.getPlaceablePositions(board.getOpponent(board.getCurPla()));
			if(placPosCur.isEmpty() && placPosEne.isEmpty()) {
				stop = !stop;
				timer.stop();
				int[] whoW = board.whoWon(board.tabpawns);
				String winningPlayer = getNameOfPlayer(color, whoW[0]);				
				if(whoW[0] == 1) {
					score.setText("Wygra³ gracz " + winningPlayer + whoW[1] + " do " + whoW[2]);
					openHighScores();
					highScores[searchForIndex(username)][1] = String.valueOf(Integer.parseInt(highScores[searchForIndex(username)][1])+1);
					saveHighScores();
				}
				else if(whoW[0] == -1)
					score.setText("Wygra³ gracz " + winningPlayer + whoW[2] + " do " + whoW[1]);
				else if(whoW[0] == 0)
					score.setText("Nastêpi³ remis " + whoW[1] + " do " + whoW[2]);					
			}
			else {
				if(!placPosCur.isEmpty()) {
					if(board.getCurPla() == 1 || pv == 1) {
						board.showPlaceablePositions(placPosCur);
						semaphore.acquire();
						board.placeMove(toRem, board.getCurPla(), board.getOpponent(board.getCurPla()));
						board.showPlaceablePositionsResume(toRem, placPosCur);
					}		
					else {
						board.showPlaceablePositions(placPosCur);
						currentThread.sleep(250);
						List<Point> positionList = new ArrayList<>(placPosCur);
						Collections.shuffle(positionList);
						toRem = positionList.get(0);
						board.placeMove(toRem, board.getCurPla(), board.getOpponent(board.getCurPla()));
						board.showPlaceablePositionsResume(toRem, placPosCur);
					}
				}
				
				else {
					score.setText("Pominieto ture gracza " + getNameOfPlayer(color, board.getCurPla()));
				}
				board.changePlayer(); 
				}
		}
		menubut.setEnabled(true);
		backtomenu.acquire();
		new Menu(username);
		
		
		
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof Pawn) {
			toRem = ((Pawn) e.getSource()).getPoint();
			semaphore.release();
			}
		if (e.getSource() == startgamebut) {
			start.release();
		}
		if (e.getSource() == menubut) {
			backtomenu.release();
			this.dispose();
		}
		
		} 
	

	
	
	

}
