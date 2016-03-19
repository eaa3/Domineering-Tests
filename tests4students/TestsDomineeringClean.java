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
import com.pholser.junit.quickcheck.From;
import com.pholser.junit.quickcheck.generator.InRange;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import java.util.*;


@RunWith(JUnitQuickcheck.class)
public class TestsDomineeringClean {


	ProxyGameTree solutionGameTree;
	GameTree<DomineeringMove> studentGameTree;

	static final String TREE_FILE_PREFIX="tests4students/test_data/treeDomineering";
	static final String TREE_FILE_SUFIX=".pgt";

	/**
	 * Every subtree of the student's game tree should have the same height 
	 * as the pre-computed game tree solution
	 *
	 */
	@Property
	public void heightShouldMatchSolution(@InRange(min = "0", max = "4") Integer m, @InRange(min = "0", max = "4") Integer n){

		DomineeringBoard studentBoard = new DomineeringBoard(m,n);
		
		String treeFile = TREE_FILE_PREFIX + m + "x" + n + TREE_FILE_SUFIX;
		solutionGameTree = Utils.load(treeFile);
		studentGameTree = studentBoard.tree();

		heightAssertion(solutionGameTree, studentGameTree);

		System.out.println("TestsDomineering: Passed HEIGHT test with board dimensions " + m +"x" + m + "!");
	}

	/**
	 * Every subtree of the student's game tree should have the same number of nodes (size) 
	 * as the pre-computed game tree solution
	 *
	 */
	@Property
	public void sizeShouldMatchSolution(@InRange(min = "0", max = "4") Integer m, @InRange(min = "0", max = "4") Integer n){

		DomineeringBoard studentBoard = new DomineeringBoard(m,n);


		String treeFile = TREE_FILE_PREFIX + m + "x" + n + TREE_FILE_SUFIX;
		solutionGameTree = Utils.load(treeFile);

		studentGameTree = studentBoard.tree();

		sizeAssertion(solutionGameTree, studentGameTree);

		System.out.println("TestsDomineering: Passed SIZE test with board dimensions " + m +"x" + n + "!");
	
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
								GameTree<DomineeringMove> studentGameTree) {

		assertEquals(solutionGameTree.height(),studentGameTree.height());


		Set< Map.Entry<Integer,ProxyGameTree> > entrySetSolution = solutionGameTree.children().entrySet();
		Iterator< Map.Entry<Integer,ProxyGameTree> > itSolution = entrySetSolution.iterator();


		Set< Map.Entry<DomineeringMove,GameTree<DomineeringMove> > > entrySetStudent = studentGameTree.children().entrySet();
		Iterator< Map.Entry<DomineeringMove,GameTree<DomineeringMove>> > itStudent = entrySetStudent.iterator();

		while( itSolution.hasNext() ||  itStudent.hasNext()){

			Map.Entry<Integer,ProxyGameTree> solutionEntry = itSolution.next();
			Map.Entry<DomineeringMove,GameTree<DomineeringMove> > studentEntry = itStudent.next();


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
							  GameTree<DomineeringMove> studentGameTree) {

		assertEquals(solutionGameTree.size(),studentGameTree.size());


		Set< Map.Entry<Integer,ProxyGameTree> > entrySetSolution = solutionGameTree.children().entrySet();
		Iterator< Map.Entry<Integer,ProxyGameTree> > itSolution = entrySetSolution.iterator();

		Set< Map.Entry<DomineeringMove,GameTree<DomineeringMove> > > entrySetStudent = studentGameTree.children().entrySet();
		Iterator< Map.Entry<DomineeringMove,GameTree<DomineeringMove>> > itStudent = entrySetStudent.iterator();
		itSolution.next();
		while( itSolution.hasNext() ||  itStudent.hasNext()){

			Map.Entry<Integer,ProxyGameTree> solutionEntry = itSolution.next();
			Map.Entry<DomineeringMove,GameTree<DomineeringMove> > studentEntry = itStudent.next();


			sizeAssertion(solutionEntry.getValue(), studentEntry.getValue());

		}

	}


}
