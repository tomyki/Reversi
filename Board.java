import java.util.HashSet;


public class Board {
	int size = 20;
	Pawn tabpawns[][] = new Pawn[size][size];	
	private int currentPlayer = 1; //1 - black, -1 - white
	
	
		
	void startGame() {
		tabpawns[size/2][size/2-1].setColor(1);
		tabpawns[size/2-1][size/2].setColor(1);
		tabpawns[size/2-1][size/2-1].setColor(-1);
		tabpawns[size/2][size/2].setColor(-1);
	}
	
	void changePlayer() {
		if(currentPlayer == 1) 
			currentPlayer = -1;
		else if(currentPlayer == -1)
			currentPlayer = 1;
		else
			System.out.println("BLAD W OZNACZENIU GRACZA");
	}
	
	int getOpponent(int player) {
		if(player == 1)
			return -1;
		else
			return 1;
	}
	
	int getCurPla() {
		return currentPlayer;
	}
	
	void findPlaceablePositions(int player, HashSet<Point> placeablePositions) {
		int opponent = getOpponent(player);
		for(int i=0;i<size;++i){
            for(int j=0;j<size;++j){
            	if(this.tabpawns[i][j].getColor() == opponent) {
            		int I = i, J = j;  
                    if(i-1>=0 && j-1>=0 && this.tabpawns[i-1][j-1].getColor() == 100){ 
                        i = i+1; j = j+1;
                        while(i<size-1 && j<size-1 && this.tabpawns[i][j].getColor() == opponent){i++;j++;}
                        if(i<=size-1 && j<=size-1 && this.tabpawns[i][j].getColor() == player) 
                        	placeablePositions.add(new Point(I-1, J-1));
                    } 
                    i=I;j=J;
                    if(i-1>=0 && this.tabpawns[i-1][j].getColor() == 100){
                        i = i+1;
                        while(i<size-1 && this.tabpawns[i][j].getColor() == opponent) i++;
                        if(i<=size-1 && this.tabpawns[i][j].getColor() == player) 
                        	placeablePositions.add(new Point(I-1, J));
                    } 
                    i=I;
                    if(i-1>=0 && j+1<=size-1 && this.tabpawns[i-1][j+1].getColor() == 100){
                        i = i+1; j = j-1;
                        while(i<size-1 && j>0 && this.tabpawns[i][j].getColor() == opponent){i++;j--;}
                        if(i<=size-1 && j>=0 && this.tabpawns[i][j].getColor() == player) 
                        	placeablePositions.add(new Point(I-1, J+1));
                    }  
                    i=I;j=J;
                    if(j-1>=0 && this.tabpawns[i][j-1].getColor() == 100){
                        j = j+1;
                        while(j<size-1 && this.tabpawns[i][j].getColor() == opponent)j++;
                        if(j<=size-1 && this.tabpawns[i][j].getColor() == player) 
                        	placeablePositions.add(new Point(I, J-1));
                    }
                    j=J;
                    if(j+1<=size-1 && this.tabpawns[i][j+1].getColor() == 100){
                        j=j-1;
                        while(j>0 && this.tabpawns[i][j].getColor() == opponent)j--;
                        if(j>=0 && this.tabpawns[i][j].getColor() == player) 
                        	placeablePositions.add(new Point(I, J+1));
                    }
                    j=J;
                    if(i+1<=size-1 && j-1>=0 && this.tabpawns[i+1][j-1].getColor() == 100){
                        i=i-1;j=j+1;
                        while(i>0 && j<size-1 && this.tabpawns[i][j].getColor() == opponent){i--;j++;}
                        if(i>=0 && j<=size-1 && this.tabpawns[i][j].getColor() == player) 
                        	placeablePositions.add(new Point(I+1, J-1));
                    }
                    i=I;j=J;
                    if(i+1 <= size-1 && this.tabpawns[i+1][j].getColor() == 100){
                        i=i-1;
                        while(i>0 && this.tabpawns[i][j].getColor() == opponent) i--;
                        if(i>=0 && this.tabpawns[i][j].getColor() == player) 
                        	placeablePositions.add(new Point(I+1, J));
                    }
                    i=I;
                    if(i+1 <= size-1 && j+1 <=size-1 && this.tabpawns[i+1][j+1].getColor() == 100){
                        i=i-1;j=j-1;
                        while(i>0 && j>0 && this.tabpawns[i][j].getColor() == opponent){i--;j--;}
                        if(i>=0 && j>=0 && this.tabpawns[i][j].getColor() == player)
                        	placeablePositions.add(new Point(I+1, J+1));
                    }
                    i=I;j=J;
            	}            	
            }
		}
		
	}
	
	HashSet<Point> getPlaceablePositions(int playerNum) {
		HashSet<Point> placeablePositions = new HashSet<>();
		findPlaceablePositions(playerNum, placeablePositions);
		return placeablePositions;
	}
	
	void showPlaceablePositions(HashSet<Point> locations){
		for(Point p: locations)
			this.tabpawns[p.x][p.y].setColor(0); 			
	}
	
	void showPlaceablePositionsResume(Point poi, HashSet<Point> locations) {
		for(Point p:locations)
			this.tabpawns[p.x][p.y].setColor(100);	
		this.tabpawns[poi.x][poi.y].setColor(currentPlayer);
	
		}
	
	void placeMove(Point p, int player, int opponent){
        int i = p.x, j = p.y;
        this.tabpawns[i][j].setColor(player);
        int I = i, J = j;  
        
        if(i-1>=0 && j-1>=0 && this.tabpawns[i-1][j-1].getColor() == opponent){ 
            i = i-1; j = j-1;
            while(i>0 && j>0 && this.tabpawns[i][j].getColor() == opponent){i--;j--;}
            if(i>=0 && j>=0 && this.tabpawns[i][j].getColor() == player) {while(i!=I-1 && j!=J-1)this.tabpawns[++i][++j].setColor(player);}
        } 
        i=I;j=J; 
        if(i-1>=0 && this.tabpawns[i-1][j].getColor() == opponent){
            i = i-1;
            while(i>0 && this.tabpawns[i][j].getColor() == opponent) i--;
            if(i>=0 && this.tabpawns[i][j].getColor() == player) {while(i!=I-1)this.tabpawns[++i][j].setColor(player);}
        } 
        i=I; 
        if(i-1>=0 && j+1<=size-1 && this.tabpawns[i-1][j+1].getColor() == opponent){
            i = i-1; j = j+1;
            while(i>0 && j<size-1 && this.tabpawns[i][j].getColor() == opponent){i--;j++;}
            if(i>=0 && j<=size-1 && this.tabpawns[i][j].getColor() == player) {while(i!=I-1 && j!=J+1)this.tabpawns[++i][--j].setColor(player);}
        }   
        i=I;j=J;
        if(j-1>=0 && this.tabpawns[i][j-1].getColor() == opponent){
            j = j-1;
            while(j>0 && this.tabpawns[i][j].getColor() == opponent)j--;
            if(j>=0 && this.tabpawns[i][j].getColor() == player) {while(j!=J-1)this.tabpawns[i][++j].setColor(player);}
        }
        j=J; 
        if(j+1<=size-1 && this.tabpawns[i][j+1].getColor() == opponent){
            j=j+1;
            while(j<size-1 && this.tabpawns[i][j].getColor() == opponent)j++;
            if(j<=size-1 && this.tabpawns[i][j].getColor() == player) {while(j!=J+1)this.tabpawns[i][--j].setColor(player);}
        }
        j=J; 
        if(i+1<=size-1 && j-1>=0 && this.tabpawns[i+1][j-1].getColor() == opponent){ 
            i=i+1;j=j-1;
            while(i<size-1 && j>0 && this.tabpawns[i][j].getColor() == opponent){i++;j--;}
            if(i<=size-1 && j>=0 && this.tabpawns[i][j].getColor() == player) {while(i!=I+1 && j!=J-1)this.tabpawns[--i][++j].setColor(player);}
        }
        i=I;j=J; 
        if(i+1 <= size-1 && this.tabpawns[i+1][j].getColor() == opponent){ 
            i=i+1;
            while(i<size-1 && this.tabpawns[i][j].getColor() == opponent) i++;
            if(i<=size-1 && this.tabpawns[i][j].getColor() == player) {while(i!=I+1)this.tabpawns[--i][j].setColor(player);}
        }
        i=I;

        if(i+1 <= size-1 && j+1 <=size-1 && this.tabpawns[i+1][j+1].getColor() == opponent){
            i=i+1;j=j+1;
            while(i<size-1 && j<size-1 && this.tabpawns[i][j].getColor() == opponent){i++;j++;}
            if(i<=size-1 && j<=size-1 && this.tabpawns[i][j].getColor() == player)while(i!=I+1 && j!=J+1)this.tabpawns[--i][--j].setColor(player);}
	}
	
	int[] whoWon(Pawn tabpawns[][]) {
		int table[] = {0, 0, 0};
		for(int i = 0; i < size; i++) {
			for(int j =0; j < size; j++) {
				if(tabpawns[i][j].getColor() == 1) {
					table[1]++;
				}
				else if(tabpawns[i][j].getColor() == -1) {
					table[2]++;
				}
			}
		}
		if(table[1] > table[2])
			table[0] = 1;
		else if(table[2] > table[1])
			table[0] = -1;
		else
			table[0] = 0;
		return table;		
	}
	
	
	Board(int size_, int color)  {
		this.size = size_;
		for(int row = 0; row < size; row++) 
			for(int col = 0; col < size; col++) 
				tabpawns[row][col] = new Pawn(color, row, col);	
	}
	

}
