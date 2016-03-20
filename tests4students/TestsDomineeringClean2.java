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
import static org.junit.Assert.assertArrayEquals;

import java.util.*;


@RunWith(JUnitQuickcheck.class)
public class TestsDomineeringClean2 {


	ProxyGameTree solutionGameTree;
	GameTree<DomineeringMove> studentGameTree;
	ArrayList<Integer> solutionVals, studentVals;

	static final String TREE_FILE_PREFIX="test_data/treeDomineering";
	static final String TREE_FILE_SUFIX=".pgt";


	/**
	 * Every subtree of the student's game tree should have the same height 
	 * as the pre-computed game tree solution
	 *
	 */
	@Property
	public void heightShouldMatchSolution(@InRange(min = "0", max = "4") Integer m, @InRange(min = "0", max = "4") Integer n){

		solutionVals = new ArrayList<Integer>();
		studentVals = new ArrayList<Integer>();

		DomineeringBoard studentBoard = new DomineeringBoard(m,n);
		
		String treeFile = TREE_FILE_PREFIX + m + "x" + n + TREE_FILE_SUFIX;
		solutionGameTree = Utils.load(treeFile);
		studentGameTree = studentBoard.tree();

		heightTraversal(solutionGameTree, studentGameTree,solutionVals,studentVals);


		Collections.sort(solutionVals);
		Collections.sort(studentVals);

		assertArrayEquals(solutionVals.toArray(),studentVals.toArray());

		//System.out.println("TestsDomineering: Passed HEIGHT test with board dimensions " + m +"x" + n + "!");
	}

	/**
	 * Every subtree of the student's game tree should have the same number of nodes (size) 
	 * as the pre-computed game tree solution
	 *
	 */
	@Property
	public void sizeShouldMatchSolution(@InRange(min = "0", max = "4") Integer m, @InRange(min = "0", max = "4") Integer n){

		solutionVals = new ArrayList<Integer>();
		studentVals = new ArrayList<Integer>();

		DomineeringBoard studentBoard = new DomineeringBoard(m,n);


		String treeFile = TREE_FILE_PREFIX + m + "x" + n + TREE_FILE_SUFIX;
		solutionGameTree = Utils.load(treeFile);

		studentGameTree = studentBoard.tree();

		sizeTraversal(solutionGameTree, studentGameTree,solutionVals,studentVals);

		//System.out.println("TestsDomineering: Passed SIZE test with board dimensions " + m +"x" + n + "!");
	
	}


	/**
	 * Traverses trees in pre-order adding the size of each corresponding subtree in their respective arrays
	 *
	 * @param solutionGameTree
	 *            the solution game tree
	 * @param studentGameTree
	 *            student's game tree
	 * @param solutionVals
	 *            size values for all subtrees of solutionGameTree
	 * @param studentVals
	 *            size values for all subtrees of studentGameTree
	 */
	public void heightTraversal(ProxyGameTree solutionGameTree, 
								GameTree<DomineeringMove> studentGameTree,
								ArrayList<Integer> solutionVals, 
								ArrayList<Integer> studentVals) {

		solutionVals.add(solutionGameTree.height());
		studentVals.add(studentGameTree.height());

		//assertEquals(solutionGameTree.height(),studentGameTree.height());


		Set< Map.Entry<Integer,ProxyGameTree> > entrySetSolution = solutionGameTree.children().entrySet();
		Iterator< Map.Entry<Integer,ProxyGameTree> > itSolution = entrySetSolution.iterator();


		Set< Map.Entry<DomineeringMove,GameTree<DomineeringMove> > > entrySetStudent = studentGameTree.children().entrySet();
		Iterator< Map.Entry<DomineeringMove,GameTree<DomineeringMove>> > itStudent = entrySetStudent.iterator();

		while( itSolution.hasNext() ||  itStudent.hasNext()){

			Map.Entry<Integer,ProxyGameTree> solutionEntry = itSolution.next();
			Map.Entry<DomineeringMove,GameTree<DomineeringMove> > studentEntry = itStudent.next();


			heightTraversal(solutionEntry.getValue(), studentEntry.getValue(),solutionVals,studentVals);

		}

	}

	/**
	 * Traverses trees in pre-order adding the size of each corresponding subtree in their respective arrays
	 *
	 * @param solutionGameTree
	 *            the solution game tree
	 * @param studentGameTree
	 *            student's game tree
	 * @param solutionVals
	 *            size values for all subtrees of solutionGameTree
	 * @param studentVals
	 *            size values for all subtrees of studentGameTree
	 */
	public void sizeTraversal(ProxyGameTree solutionGameTree, 
							  GameTree<DomineeringMove> studentGameTree,
							  ArrayList<Integer> solutionVals, 
							  ArrayList<Integer> studentVals) {

		solutionVals.add(solutionGameTree.size());
		studentVals.add(studentGameTree.size());

		Set< Map.Entry<Integer,ProxyGameTree> > entrySetSolution = solutionGameTree.children().entrySet();
		Iterator< Map.Entry<Integer,ProxyGameTree> > itSolution = entrySetSolution.iterator();

		Set< Map.Entry<DomineeringMove,GameTree<DomineeringMove> > > entrySetStudent = studentGameTree.children().entrySet();
		Iterator< Map.Entry<DomineeringMove,GameTree<DomineeringMove>> > itStudent = entrySetStudent.iterator();

		while( itSolution.hasNext() ||  itStudent.hasNext()){

			Map.Entry<Integer,ProxyGameTree> solutionEntry = itSolution.next();
			Map.Entry<DomineeringMove,GameTree<DomineeringMove> > studentEntry = itStudent.next();


			sizeTraversal(solutionEntry.getValue(), studentEntry.getValue(),solutionVals,studentVals);

		}

	}


}
