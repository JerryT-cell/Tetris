package tetris.game;

import java.util.HashSet;
import java.util.Set;

import tetris.game.pieces.Piece;
import tetris.game.pieces.PieceFactory;


public class gameimplementation implements TetrisGame {
  private Board board;
  private Piece currentpiece;
 
  private PieceFactory pieceFactory;
  private Piece nextPiece;
  private int row_position;
  private int column_position;
  private int completed_rows;
  private long points;
  private boolean gameOver;
  private Set<GameObserver> observers;


  public gameimplementation(Board board, PieceFactory pieceFactory){
  this.board = board;
  this.pieceFactory = pieceFactory;
  this.currentpiece=null;
  this.nextPiece = pieceFactory.getNextRandomPiece();
  this.points = 0;
  this.gameOver = false;
  this.observers = new HashSet<GameObserver>();

  }

  public Piece getCurrentPiece() {
    return currentpiece;
  }

  public Board getBoard() {
    return board;
  }

  public Piece getNextPiece() {
    return nextPiece;
  }

  public int getNumberOfCompletedRows() {
    return completed_rows;
  }

  public int getPieceColumn() {
    return column_position;
  }

  public int getPieceRow() {
    return row_position;
  }

  public long getPoints() {
    return points;
  }

  public boolean isGameOver() {
    return gameOver;
  }

  public boolean moveDown() {
    board.removePiece(currentpiece, row_position, column_position);
    boolean possible = board.canAddPiece(currentpiece, row_position + 1, column_position);
    if (possible) {
      // remove piece in the current position
      // adding piece down with row+1
      this.row_position = row_position + 1;
      board.addPiece(currentpiece, row_position, column_position);
      // notify observers
      for (GameObserver ob : observers) {
        ob.piecePositionChanged();
      }
      return true;
    } else {
      board.addPiece(currentpiece, row_position, column_position);
      return false;
    }

  }

  public boolean moveLeft() {
    board.removePiece(currentpiece, row_position, column_position);
    boolean possible = board.canAddPiece(currentpiece, row_position, column_position - 1);
    if (possible) {
      // remove piece in the current position
      
      // adding piece to the left with column-1
      this.column_position = column_position - 1;
      board.addPiece(currentpiece, row_position, column_position);
      // notify observers
      for (GameObserver ob : observers) {
        ob.piecePositionChanged();
      }
      return true;
    } else {
      board.addPiece(currentpiece, row_position, column_position);
      return false;
    }
  }

  public boolean moveRight() {
    board.removePiece(currentpiece, row_position, column_position);
    boolean possible = board.canAddPiece(currentpiece, row_position, column_position + 1);
    if (possible) {
      // remove piece in the current position
      
      // adding piece to the right with column+1
      this.column_position = column_position + 1;
      board.addPiece(currentpiece, row_position, column_position);
      // notify observers
      for (GameObserver ob : observers) {
        ob.piecePositionChanged();
      }
      return true;
    } else {
      board.addPiece(currentpiece, row_position, column_position);
      return false;
    }
  }

  public boolean newPiece() {
    // check for complete rows
    int deleterows = board.deleteCompleteRows();
    if (deleterows > 0) {
      // notify observer if the completed row is deleted
      for (GameObserver ob : observers) {
        ob.rowsCompleted();
      }
      switch(deleterows){
        case 1 : points = points+ 100;
                 completed_rows++;
                 break;
        case 2 : points = points+ 300;
                 completed_rows = completed_rows+2;
                 break;
        case 3 : points = points+ 500;
                 completed_rows = completed_rows +3;
                 break;
        case 4 : points =  points + 1000;
                 completed_rows = completed_rows +4;
                 break;
        default : points = points+1000;
                  completed_rows = completed_rows + deleterows;
                  break;
                        
      }
    }
    // is a new piece possible?
    this.row_position = 2;
    this.column_position = (board.getNumberOfColumns()/2);
    boolean possible = board.canAddPiece(nextPiece, row_position,
        column_position);
    if (!possible) {
      setGameOver();
      // notify observer
      for (GameObserver ob : observers) {
        ob.gameOver();
      }
      return false;
     }
    else{ 
     board.addPiece(nextPiece, row_position, column_position);
     currentpiece = nextPiece;
     nextPiece = pieceFactory.getNextRandomPiece();
     
     return true;
  }
}

  public boolean rotatePieceClockwise() {
    // define a clockwise rotated piece
    Piece rotatedPiece = currentpiece.getClockwiseRotation(); 
    board.removePiece(currentpiece, row_position, column_position);
    boolean possible = board.canAddPiece(rotatedPiece, row_position,column_position);
    if(possible){

      currentpiece = rotatedPiece;
    //if possible, remove current and add rotated one
    board.addPiece(rotatedPiece, row_position, column_position);
    //notify observers
    for (GameObserver ob : observers) {
      ob.piecePositionChanged();
    }
    return true;
  } else
  {board.addPiece(currentpiece, row_position, column_position);
  return false;}
  }

  public boolean rotatePieceCounterClockwise() {
    // define a counterclockwise rotated piece
    Piece rotatedPiece = currentpiece.getCounterClockwiseRotation();
    board.removePiece(currentpiece, row_position, column_position); 
    boolean possible = board.canAddPiece(rotatedPiece, row_position,column_position);
    if(possible){
      board.addPiece(rotatedPiece, row_position, column_position);
      currentpiece = rotatedPiece;
      //notify observers
      for (GameObserver ob : observers) {
        ob.piecePositionChanged();
      }
      return true;
    }else{
      board.addPiece(currentpiece, row_position, column_position);
      return false;
    }
    
    
  }

  public void setGameOver() {
    gameOver = true;
  }
@Override
  public void step() {
    if (currentpiece ==null)
    {newPiece();}
    else if(!this.moveDown()){
      for (GameObserver ob : observers) {
        ob.pieceLanded();
      }
      if(!newPiece()){
       setGameOver();
       for (GameObserver ob : observers) {
        ob.gameOver();
      }
     }}

    

  }

  @Override
  public void addObserver(GameObserver observer) {
    observers.add(observer);
    

  }

  @Override
  public void removeObserver(GameObserver observer) {
    
    observers.remove(observer);

  }

  

}
