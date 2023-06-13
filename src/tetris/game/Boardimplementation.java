package tetris.game;

import tetris.game.pieces.Piece;
import tetris.game.pieces.Piece.PieceType;

public class Boardimplementation implements Board {
    private PieceType[][] board;
    private int numberOfRows;
    private int numberOfColumns;

    public Boardimplementation(int rows, int columns) {
        board = new PieceType[rows][columns];
        numberOfRows = rows;
        numberOfColumns = columns;
    }

    public Boardimplementation(PieceType[][] p, int rows, int columns) {
        this.board = p;
        numberOfRows = rows;
        numberOfColumns = columns;
    }

    public PieceType[][] getBoard() {
        return board;
    }

    public int getNumberOfRows() {
        return numberOfRows;
    }

    public int getNumberOfColumns() {
        return numberOfColumns;
    }

    public void addPiece(Piece piece, int row, int column) {
        boolean possible = this.canAddPiece(piece, row, column);
        if (piece == null || !possible) {
            throw new IllegalArgumentException();
        }
       /*   if (piece.getRotationPoint().getColumn() == 1) {
            column--;
        }
        if (piece.getRotationPoint().getRow() == 1) {
            row--;
        }*/
        column=column-piece.getRotationPoint().getColumn();
        row=row-piece.getRotationPoint().getRow();
        
    
        boolean[][] pieceBody = piece.getBody();
        for (int i = 0; i < piece.getHeight(); i++) {
            int columnt = column;
            for (int j = 0; j < piece.getWidth(); j++) {
                if (pieceBody[i][j]) {
                    board[row][columnt] = piece.getPieceType();
                } else {
                    if(board[row][columnt]==null){
                    board[row][columnt] = null;}
                }
                columnt++;
            }
            row++;
        }
    }

    public boolean canAddPiece(Piece piece, int row, int column) {
        // immediate false
        if (row <= 0 || row > this.numberOfRows - 1 || column < 0 || column > this.numberOfColumns - 1) {
            return false;
        }
        // other case
        if (piece == null) {
            throw new IllegalArgumentException();
        }
        // conditional false row cases
         if(row ==1){
           if(piece.getRotationPoint().getRow()>=2){
            return false;
           } 
         }
         if (row == this.numberOfRows - 2) {
            if ((piece.getHeight() - 1) - piece.getRotationPoint().getRow() == 2) {
                return false;
            }
        } else if (row == this.numberOfRows - 1) {
            if ((piece.getHeight() - 1) - piece.getRotationPoint().getRow() >= 1) {
                return false;
            }
        }
        
        // conditional false column cases
        if (column == 0) {
            if (piece.getRotationPoint().getColumn() >= 1) {
                return false;
            }
        } else if (column == this.numberOfColumns - 2) {
            if ((piece.getWidth() - 1) - piece.getRotationPoint().getColumn() >= 2) {
                return false;
            }
        } else if (column == this.numberOfColumns - 1) {
            if ((piece.getWidth() - 1) - piece.getRotationPoint().getColumn() >= 1) {
                return false;
            }}
          else if(column == 1){
             if(piece.getRotationPoint().getColumn()>=2){return false;}
         }
         else if(column==2){
            if(piece.getRotationPoint().getColumn()>=3){return false;}
         }
            //where to start adding the piece
            column=column-piece.getRotationPoint().getColumn();
            row=row-piece.getRotationPoint().getRow();
            
        
        boolean[][] pieceBody = piece.getBody();
        for (int i = 0; i < piece.getHeight(); i++) {
            int columnt = column;
            for (int j = 0; j < piece.getWidth(); j++) {
                if (board[row][columnt] != null) {
                    if (pieceBody[i][j]) {
                        return false;
                    }
                }
                columnt++;
            }
            row++;
        }
        return true;

    }

    public void removePiece(Piece piece, int row, int column) {
        boolean possible = !this.canRemovePiece(piece, row, column);
        if (possible ||piece == null) {
            throw new IllegalArgumentException();
        }
       
        column=column-piece.getRotationPoint().getColumn();
        row=row-piece.getRotationPoint().getRow();

        boolean [][] body = piece.getBody();
        for (int i = 0; i < piece.getHeight(); i++) {
            int columnt = column;
            for (int j = 0; j < piece.getWidth(); j++) {
                if (board[row][columnt] != null && board[row][columnt] == piece.getPieceType()) {
                    if(body[i][j]){
                   board[row][columnt] = null;}/*don't remove the same piece. */
                }
                columnt++;
            }
            row++;
        }
    }

    public boolean canRemovePiece(Piece piece, int row, int column) {
        if (row <= 0 || row > this.numberOfRows - 1 || column < 0 || column > this.numberOfColumns - 1) {
            return false;
        }
        if (piece == null) {
            throw new IllegalArgumentException();
        }
        if (row == this.numberOfRows - 1) {
            if ((piece.getHeight() - 1) - piece.getRotationPoint().getRow() > 1) {
                return false;
            }
        }

        boolean [][] body = piece.getBody();
        if (board[row][column] != piece.getPieceType()) {
            return false;
        }

        column=column-piece.getRotationPoint().getColumn();
        row=row-piece.getRotationPoint().getRow();
        for (int i = 0; i < piece.getHeight(); i++) {
            int columnt = column;
            for (int j = 0; j < piece.getWidth(); j++) {
                if (board[row][columnt] != null){
                    if(body[i][j]){
                        if(board[row][columnt] != piece.getPieceType()){
                            return false; 
                        }
                    }

                }
                columnt++;
            }   
            row++;
        }

                

        return true;
    }
    
    private boolean isArrayRowFull(int r){
        for(int i = 0;i<numberOfColumns;i++){
         if(board[r][i] == null){
         return false;}
        }
        return true;
     }
     private boolean isArrayRowEmpty(int r){
        for(int i = 0;i<numberOfColumns;i++){
         if(board[r][i] !=null){
         return false;}
        }
        return true;
     }
     public void clear(int r){ 
       for(int i = 0; i<numberOfColumns;i++){
        board[r][i]= null;
       }
     }
     private void down(int r, int completeRows){
         for(int i=0;i<numberOfColumns;i++){
             board[r+completeRows][i]=board[r][i];
         }
     }

    public int deleteCompleteRows() {
        int numberofCompleterows = 0;
        for (int i = numberOfRows-1; i>=0; i--) {
           if (isArrayRowFull(i))
           {
            clear(i);
            numberofCompleterows++;
          }
           else if(numberofCompleterows>0){
            down(i, numberofCompleterows);
           }
        } 
        return numberofCompleterows;
    }

   



    public Board clone() {

        PieceType[][] other = new PieceType[numberOfRows][numberOfColumns];
        for (int i = 0; i < numberOfRows; i++) {
            for (int j = 0; j < numberOfColumns; j++) {
                other[i][j] = board[i][j];
            }
        }

        Board secondboard = new Boardimplementation(other, numberOfRows, numberOfColumns);
        return secondboard;
    }
    private boolean isboardempty(){

        for(int i = 0;i<numberOfRows;i++){
            for(int j= 0;j<numberOfColumns;j++){
                if(board[i][j] !=null){
                    return false;
                }
            }
        }
        return false;
    }

}
