package tetris.autoplay;

import java.util.ArrayList;
import java.util.List;

import tetris.game.Board;


import tetris.game.TetrisGameView;
import tetris.game.pieces.Piece;
import tetris.game.pieces.Piece.PieceType;

public class Autoplayimp implements AutoPlayer {
    private Move move;
    private TetrisGameView gameView;
    private Board board;
    private Piece currentPiece;

    private int pieceRow;
    private int pieceColumn;
    private boolean gameOver;
    private boolean rowsCompleted;
    private boolean piecePosChanged;
    private boolean piecelanded;
    private Position position;

    public Autoplayimp(TetrisGameView game) {
        this.gameView = game;
        board = game.getBoardCopy();
        position = null;

    }

    @Override
    public void rowsCompleted() {

        rowsCompleted = true;
        piecePosChanged = false;
        piecelanded = false;
    }

    @Override
    public void piecePositionChanged() {

        rowsCompleted = false;
        piecePosChanged = true;
        piecelanded = false;
    }

    @Override
    public void pieceLanded() {

        rowsCompleted = false;
        piecePosChanged = false;
        piecelanded = true;
    }

    @Override
    public void gameOver() {

        gameOver = true;
    }

    @Override
    public Move getMove() {
        // TODO Auto-generated method
        Board board1 = gameView.getBoardCopy();
        boolean boardempty = isboardempty(board1);
        if (boardempty) {
            this.move = Move.DOWN;
            return move;
        }
        pieceColumn = gameView.getPieceColumn();
        pieceRow = gameView.getPieceRow();
        currentPiece = gameView.getCurrentPieceCopy();
        if (piecelanded) {
            if (!gameOver) {
                performbestMove(board1, currentPiece);
            }
        }
        if (position == null) {
            performbestMove(board1, currentPiece);
        }

        if (position.getColumnPosition() < pieceColumn) {
            board1.removePiece(currentPiece, pieceRow, pieceColumn);
            if (board1.canAddPiece(currentPiece, pieceRow, pieceColumn - 1))
                return Move.LEFT;
            else
                return Move.DOWN;
        } else if (position.getColumnPosition() > pieceColumn) {
            board1.removePiece(currentPiece, pieceRow, pieceColumn);
            if (board1.canAddPiece(currentPiece, pieceRow, pieceColumn + 1))
                return Move.RIGHT;
            else
                return Move.DOWN;
        }

        if (position.getRotation() > 0) {
            board1.removePiece(currentPiece, pieceRow, pieceColumn);

            if (position.getRotation() == 3) {
                Piece rotatep = currentPiece.getCounterClockwiseRotation();
                if (board1.canAddPiece(rotatep, pieceRow, pieceColumn)) {
                    position.setRotation(0);
                    return Move.ROTATE_CCW;
                }
                return Move.DOWN;
            }
            Piece rotatep = currentPiece.getClockwiseRotation();
            if (board1.canAddPiece(rotatep, pieceRow, pieceColumn)) {
                position.setRotation(position.getRotation() - 1);
                return Move.ROTATE_CW;
            }
            return Move.DOWN;
        }

        return Move.DOWN;

    }

    private void performbestMove(Board board, Piece piece) {
        this.position = new Position(1, 0, -10000);


        for (int k = 0; k < 4; k++) {
            for (int i = 0; i < board.getNumberOfColumns(); i++) {
                boolean hasAdded = false;
                for (int j = 2; j < board.getNumberOfRows(); j++) {

                    Board board2 = gameView.getBoardCopy();
                    // boolean rem = board2.canRemovePiece(gameView.getCurrentPieceCopy(), pieceRow,
                    // pieceColumn);
                    // if(rem){
                    board2.removePiece(gameView.getCurrentPieceCopy(), pieceRow, pieceColumn);
                    
                    boolean canadd = board2.canAddPiece(piece, j, i);
                    if (canadd) {
                        board2.addPiece(piece, j, i);
                        hasAdded = true;
                    } else {
                        if (hasAdded)
                            board2.addPiece(piece, j - 1, i);
                    }

                    if (j == 19 || !canadd) {
                        if (hasAdded) {
                            double height = aggregateHeight(board2);
                            double holes = holes(board2);
                            double bumps = bumpiness(board2);
                            double completelines = completelines(board2);
                            double total = (-10 * height) + (10 * completelines) + (-8 * holes) + (-2 * bumps);
                            Position newpos = new Position(i, k, total);
                            if (newpos.getScore() > this.position.getScore()) {
                                this.position.setRotation(newpos.getRotation());
                                this.position.setColumnPosition(newpos.getColumnPosition());
                                this.position.setScore(newpos.getScore());
                            }
                            break;
                        }
                        break;

                    }
                }
            }
            piece = piece.getClockwiseRotation();

        }

    }

    // aggregate height
    private int aggregateHeight(Board board) {
        PieceType[][] body = board.getBoard();
        int highColumns = 0;
        for (int i = 0; i < board.getNumberOfColumns(); i++) {
            for (int j = 0; j < board.getNumberOfRows(); j++) {
                int count = 0;
                if (body[j][i] != null) {
                    count = board.getNumberOfRows() - j;
                    highColumns += count;
                    break;
                }
            }
        }
        return highColumns;
    }

    // complete line
    private int completelines(Board board2) {

        int complete = board2.deleteCompleteRows();

        return complete;
    }

    // holes between lines
    private int holes(Board board2) {
        PieceType[][] body = board2.getBoard();
        int holes = 0;

        for (int j = 0; j < board.getNumberOfColumns(); j++) {
            for (int i = 0; i < board.getNumberOfRows(); i++) {
                if (body[i][j] != null) {
                    int holescheck = columncheck(body, i, j);
                    if (holescheck > 0) {
                        holes = holes + holescheck;
                        break;
                    } else {
                        break;
                    }

                }
            }
        }
        return holes;
    }

    private int columncheck(PieceType[][] body, int rowindex, int columnindex) {
        int holes = 0;
        for (int i = rowindex; i < board.getNumberOfRows(); i++) {
            if (body[i][columnindex] == null) {
                holes++;
            }
        }
        return holes;
    }

    private int bumpiness(Board board2) {
        PieceType[][] body = board2.getBoard();
        List<Integer> bumpnum = new ArrayList<Integer>();

        int bump = 0;
        
        for (int i = 0; i < board.getNumberOfColumns(); i++) {
            for (int j = 0; j < board.getNumberOfRows(); j++) {
                if (body[j][i] != null) {
                    bumpnum.add(board2.getNumberOfRows() - j);
                    break;
                } else if (j == 19 && body[j][i] == null) {
                    bumpnum.add(0);
                    break;
                }

            }
        }

        for (int i = 1; i < bumpnum.size(); i++) {
            bump += Math.abs(bumpnum.get(i - 1) - bumpnum.get(i));
        }
        return bump;

    }

    // helping functions
    public boolean isboardempty(Board board) {
        PieceType[][] body = board.getBoard();
        int numberOfRows = board.getNumberOfRows();
        int numberOfColumns = board.getNumberOfColumns();
        for (int i = 0; i < numberOfRows; i++) {
            for (int j = 0; j < numberOfColumns; j++) {
                if (body[i][j] != null) {
                    return false;
                }
            }
        }
        return true;
    }

}
