package tetris.tests;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;
import org.junit.Test;

import tetris.game.MyTetrisFactory;
import tetris.game.pieces.PieceFactory;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Random;

import org.junit.Rule;
import org.junit.rules.TestRule;

import prog2.tests.PublicTest;
import prog2.tests.tetris.PieceExercise;
import tetris.game.pieces.Piece;
import tetris.game.TetrisGame;
import tetris.game.GameObserver;

/**
 * Within this class and/or package you can implement your own tests.
 *
 * Note that no classes or interfaces will be available, except those initially
 * provided. Make use of {@link MyTetrisFactory} to get other factory instances.
 */
public class MyTetrisTest {
    public final static long SEED = 34681;
	PieceFactory p=  MyTetrisFactory.createPieceFactory(new Random(SEED));
    TetrisGame g = MyTetrisFactory.createTetrisGame(new Random(SEED));

	
	@Test
	public void test() {
		assertNotNull(MyTetrisFactory.createBoard(MyTetrisFactory.DEFAULT_ROWS, MyTetrisFactory.DEFAULT_COLUMNS));
	}

	@Test
	public void jtest() {

		Piece jpiece = p.getJPiece();
		Piece torot = jpiece;
		for(int i = 0;i<4;i++){
			jpiece = jpiece.getClockwiseRotation();
		}
		assertTrue(torot.equals(jpiece));
		assertArrayEquals(torot.getBody(), jpiece.getBody());
	}
	@Test
	public void itest(){
		Piece ipiece = p.getIPiece();
		boolean [][] body = ipiece.getBody();
		assertTrue(1==body[0].length);
		Piece torot = ipiece;
		for(int i = 0;i<4;i++){
			ipiece = ipiece.getCounterClockwiseRotation();
		}
		assertTrue(torot.equals(ipiece));
		assertArrayEquals(torot.getBody(), ipiece.getBody());
	}
	
   
	
}
