package tetris.game.pieces;
import java.util.Random;


import tetris.game.pieces.Piece.PieceType;

public class PieceFactoryImplementation implements PieceFactory{
    Random randomnumber;

    public PieceFactoryImplementation(Random r){
        randomnumber = r;
    }

   public Piece getIPiece(){
    boolean[][] Iarray = {{true},
                          {true},
                          {true},
                          {true}};
    Point rotation = new Point(1,0); 
    Piece ipiece = new pieceimp(Iarray,rotation,PieceType.I);
    return ipiece;
            
    }
    
    public Piece getJPiece(){
    boolean [][] jarray ={{false,true},
                          {false,true},
                          {true,true}};
    
    Point rotation = new Point(1,1);
    Piece jpiece = new pieceimp(jarray,rotation,PieceType.J);
    return jpiece;
    }

    public Piece getLPiece(){
    boolean [][] larray ={{true,false},
                          {true,false},
                          {true,true}};
    
    Point rotation = new Point(1,0);

    Piece lpiece = new pieceimp(larray,rotation,PieceType.L);
    return lpiece;
    }
    
    public Piece getOPiece(){
    boolean [][] oarray ={{true,true},
                          {true,true}};

    
    Point rotation = new Point(1,1);
    Piece opiece = new pieceimp(oarray,rotation,PieceType.O);
    return opiece;
    }
    
    public Piece getSPiece(){
    boolean [][] sarray = {{false,true,true},
                           {true,true,false}};
    
    Point rotation = new Point(1,1);
    Piece spiece = new pieceimp(sarray,rotation,PieceType.S);
    return spiece;
    }

    public Piece getZPiece(){
    boolean [][] zarray = {{true,true,false},
                            {false,true,true}};
    Point rotation = new Point(1, 1);
    Piece zpiece = new pieceimp(zarray,rotation,PieceType.Z);
    return zpiece;

    }

    public Piece getTPiece(){
    boolean[][] tarray ={{true,true,true},
                         {false,true,false}};
    Point rotation = new Point (0,1);
    Piece tpiece = new pieceimp(tarray,rotation,PieceType.T);
    return tpiece;
    }

    public Piece getNextRandomPiece(){
    int r = 0 + randomnumber.nextInt(7);
    switch(r){
        case 1 : return this.getIPiece();
        case 2 : return this.getJPiece();
        case 3 : return this.getLPiece();
        case 4 : return this.getOPiece();
        case 5 : return this.getSPiece();
        case 6 : return this.getTPiece();
        case 7 : return this.getZPiece();
        default: return this.getIPiece();
    }


    }




    }
    

