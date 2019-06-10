/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kdtrees;

import static kdtrees.KDTrees.k;

/**
 *
 * @author princ
 */
public class Node {
	public int[] point = new int[k]; // To store k dimensional point
	public Node left;
	public Node right;
    

    @Override
    public String toString() {
        return "Node{" + "Root Node= " + point[0] +", "+ point[1] + '}';
    }
    
}