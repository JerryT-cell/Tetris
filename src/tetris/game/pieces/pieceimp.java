package tetris.game.pieces;




public class pieceimp implements Piece {

    private boolean[][] piece;
    private Point rotation;
    private PieceType type;
    
  
    public pieceimp(boolean[][] piece, Point rotation, PieceType type){
        this.piece = piece;
        this.rotation = rotation;
        this.type = type;
    }
    
    public int getWidth(){
      if (piece ==null){
          return 0;
      }
      return piece[0].length;
    }

    public int getHeight(){
        if (piece ==null){
            return 0;
        }
        return piece.length;
      }
    
    public boolean[][] getBody(){
        return piece;
    }
    
    public Piece getClockwiseRotation(){
    boolean[][] newpiecearray = new boolean[this.getWidth()][this.getHeight()];
    Point newrotation = new Point(rotation.getColumn(), this.getHeight()-1-rotation.getRow());
   
    for(int i = 0;i<this.getWidth();i++){
        int k = this.getHeight()-1;
        for(int j = 0;j<this.getHeight();j++){
            newpiecearray[i][k] = piece[j][i];
            k--;
        }
    }
    Piece newpiece = new pieceimp(newpiecearray, newrotation,type);

    return newpiece;

    }

    public Piece getCounterClockwiseRotation(){
    boolean[][] newpiecearray = new boolean[this.getWidth()][this.getHeight()];
    Point newrotation = new Point(this.getWidth()-1-rotation.getColumn(),rotation.getRow());
    int k = this.getWidth()-1;
    for(int i = 0;i<this.getWidth();i++){
        for(int j = 0;j<this.getHeight();j++){
            newpiecearray[i][j] = piece[j][k];  
        }
        k--;
    }
    Piece newpiece = new pieceimp(newpiecearray, newrotation,type);
    return newpiece;

    }

    public Point getRotationPoint(){
        return rotation;
    }

    public PieceType getPieceType(){
        return type;
    }

    public Piece clone(){
    boolean[][] newpiecearray = new boolean[this.getHeight()][this.getWidth()];
    for(int i = 0;i<this.getHeight();i++){
        for(int j = 0;j<this.getWidth();j++){
            newpiecearray[i][j] = piece[i][j];  
        }
    }
     Point newpoint = new Point(rotation.getRow(),rotation.getColumn());
    Piece newpiece = new pieceimp(newpiecearray, newpoint,type);
    return newpiece;

    }

    @Override
    public boolean equals(Object other){
        Piece otherpiece = (Piece) other;
        if(otherpiece.getHeight() == this.getHeight()&& otherpiece.getWidth() == this.getWidth()
           && rotation.getColumn() == otherpiece.getRotationPoint().getColumn()
           && rotation.getRow() == otherpiece.getRotationPoint().getRow()
           && type == otherpiece.getPieceType())
           {
               return true;
           }
        else { return false;}

        }
    }

    
    

