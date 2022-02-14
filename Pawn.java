import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Pawn extends JButton{
	private int gameColor;
	private int color;
	private Point coords;
	
	
	//ikony
	ImageIcon blankP = new ImageIcon("src/pawnblank.jpg");
	ImageIcon blackP = new ImageIcon("src/pawnBlack.jpg");
	ImageIcon whiteP = new ImageIcon("src/pawnWhite.jpg");
	ImageIcon blueP = new ImageIcon("src/pawnBlue.jpg");
	ImageIcon greyP = new ImageIcon("src/pawnGrey.jpg");
	ImageIcon creamP = new ImageIcon("src/pawnCream.jpg");
	ImageIcon redP = new ImageIcon("src/pawnRed.jpg");
	
	void setColor(int col) {
		if(this.color == 0 || this.color == 100 || this.color == 1 || this.color == -1)
		{	this.color = col;
			this.setIconOfPawn();
		}
	} 
	
	Point getPoint() {
		return coords;
	}
	
	int getColor() {
		return this.color;
	}
	
	void setIconOfPawn() {
		if(gameColor == 1) {
			if(color == 100)	{
				this.setIcon(blankP);
				this.setEnabled(false);
				this.setDisabledIcon(blankP);}
			else if(color == -1)	{
				this.setIcon(whiteP);
				this.setEnabled(false);
				this.setDisabledIcon(whiteP);}
			else if(color == 0)	{
				this.setIcon(greyP);
				this.setEnabled(true);}
			else if(color == 1)	{
				this.setIcon(blackP);
				this.setEnabled(false);
				this.setDisabledIcon(blackP);}
			else 
				System.out.println("BLAD W NUMERZE KOLORU");			
		}
		else if(gameColor == 0) {
			if(color == 100)	{
				this.setIcon(blankP);
				this.setEnabled(false);
				this.setDisabledIcon(blankP);}
			else if(color == -1)	{
				this.setIcon(redP);
				this.setEnabled(false);
				this.setDisabledIcon(redP);}
			else if(color == 0)	{
				this.setIcon(creamP);
				this.setEnabled(true);
				this.setDisabledIcon(creamP);}
			else if(color == 1)	{
				this.setIcon(blueP);
				this.setEnabled(false);
				this.setDisabledIcon(blueP);}
			else 
				System.out.println("BLAD W NUMERZE KOLORU");
		}
		else
			System.out.println("BLAD W NUMERZE KOLORU GRY");
	}
	
	
	Pawn(int gamCol, int x_, int y_) {
		coords = new Point(x_, y_);
		gameColor = gamCol;
		color = 100; // 100-null, -1 - white/red, 0 - grey/cream, 1 - black/blue
		JButton newPawn = new JButton();
		newPawn.setBounds(0, 0, 60, 60);
		setIconOfPawn();
	}

}
