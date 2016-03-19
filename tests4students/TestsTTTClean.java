/**
 *
 *
 * @author Ermano Arruda
 */

import static org.junit.Assert.assertArrayEquals;

import org.junit.AfterClass;

import org.junit.runner.RunWith;

import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;

import org.junit.Test;
import org.junit.Before;
import com.pholser.junit.quickcheck.Property;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import java.util.*;


@RunWith(JUnitQuickcheck.class)
public class TestsTTTClean {


	ProxyGameTree solutionGameTree;

	GameTree<TTTMove> studentGameTree;

	static final String TREE_FILE="test_data/treeTTT4x4.pgt";

	/**
	 * Initialise test data
	 *
	 *
	 */
	@Before
	public void init(){

		TTTBoard studentBoard = new TTTBoard();

		solutionGameTree = Utils.load(TREE_FILE);
		studentGameTree = studentBoard.tree();

	}

	/**
	 * Every subtree of the student's game tree should have the same height 
	 * as the pre-computed game tree solution
	 *
	 */
	@Test
	public void heightShouldMatchSolution(){

		heightAssertion(solutionGameTree, studentGameTree);

		System.out.println("TestsTTT: Passed HEIGHT test with board dimensions 4x4!");
	}

	/**
	 * Every subtree of the student's game tree should have the same number of nodes (size) 
	 * as the pre-computed game tree solution
	 *
	 */
	@Test
	public void sizeShouldMatchSolution(){

		sizeAssertion(solutionGameTree, studentGameTree);

		System.out.println("TestsTTT: Passed SIZE test with board dimensions 4x4!");
		
	}


	/**
	 * Traverses trees in pre-order asserting that every subtree has the same height
	 *
	 * @param solutionGameTree
	 *            the solution game tree
	 * @param studentGameTree
	 *            student's game tree
	 */
	public void heightAssertion(ProxyGameTree solutionGameTree, 
								GameTree<TTTMove> studentGameTree) {

		assertEquals(solutionGameTree.height(),studentGameTree.height());


		Set< Map.Entry<Integer,ProxyGameTree> > entrySetSolution = solutionGameTree.children().entrySet();
		Iterator< Map.Entry<Integer,ProxyGameTree> > itSolution = entrySetSolution.iterator();


		Set< Map.Entry<TTTMove,GameTree<TTTMove> > > entrySetStudent = studentGameTree.children().entrySet();
		Iterator< Map.Entry<TTTMove,GameTree<TTTMove>> > itStudent = entrySetStudent.iterator();

		while( itSolution.hasNext() ||  itStudent.hasNext()){

			Map.Entry<Integer,ProxyGameTree> solutionEntry = itSolution.next();
			Map.Entry<TTTMove,GameTree<TTTMove> > studentEntry = itStudent.next();


			heightAssertion(solutionEntry.getValue(), studentEntry.getValue());

		}

	}

	/**
	 * Traverses trees in pre-order asserting that every subtree has the same number of nodes (size)
	 *
	 * @param solutionGameTree
	 *            the solution game tree
	 * @param studentGameTree
	 *            student's game tree
	 */
	public void sizeAssertion(ProxyGameTree solutionGameTree, 
							  GameTree<TTTMove> studentGameTree) {

		assertEquals(solutionGameTree.size(),studentGameTree.size());


		Set< Map.Entry<Integer,ProxyGameTree> > entrySetSolution = solutionGameTree.children().entrySet();
		Iterator< Map.Entry<Integer,ProxyGameTree> > itSolution = entrySetSolution.iterator();


		Set< Map.Entry<TTTMove,GameTree<TTTMove> > > entrySetStudent = studentGameTree.children().entrySet();
		Iterator< Map.Entry<TTTMove,GameTree<TTTMove>> > itStudent = entrySetStudent.iterator();


		while( itSolution.hasNext() ||  itStudent.hasNext()){

			Map.Entry<Integer,ProxyGameTree> solutionEntry = itSolution.next();
			Map.Entry<TTTMove,GameTree<TTTMove> > studentEntry = itStudent.next();


			sizeAssertion(solutionEntry.getValue(), studentEntry.getValue());

		}

	}

}
