package kdtrees;

import java.util.Arrays;

/**
 *
 * @author Isaac Oselukwue
 */
public class KDTrees {

    public static final int k = 2;

    // A method to create a node of K D tree 
    public static Node newNode(int[] arr) {
        Node temp = new Node();

        for (int i = 0; i < k; i++) {
            temp.point[i] = arr[i];
        }

        temp.left = temp.right = null;
        return temp;
    }

    // Inserts a new node and returns root of modified tree 
    // The parameter depth is used to decide axis of comparison 
    public static Node insertRec(Node root, int[] point, int depth) {
        // Tree is empty? 
        if (root == null) {
            return newNode(point);
        }

        // Calculate current dimension (cd) of comparison 
        int currentdimension = depth % k;

        // Compare the new point with root on current dimension 'cd' 
        // and decide the left or right subtree 
        if (point[currentdimension] < (root.point[currentdimension])) {
            root.left = insertRec(root.left, point, depth + 1);
        } else {
            root.right = insertRec(root.right, point, depth + 1);
        }

        return root;
    }

    // Function to insert a new point with given point in 
    // KD Tree and return new root. It mainly uses above recursive 
    // function "insertRec()" 
    public static Node insert(Node root, int[] point) {
        return insertRec(root, point, 0);
    }

    // A utility function to find minimum of three integers 
    public static Node minNode(Node x, Node y, Node z, int d) {
        Node res = x;
        if (y != null && y.point[d] < res.point[d]) {
            res = y;
        }
        if (z != null && z.point[d] < res.point[d]) {
            res = z;
        }
        return res;
    }

    // Recursively finds minimum of d'th dimension in KD tree 
    // The parameter depth is used to determine current axis. 
    public static Node findMinRec(Node root, int d, int depth) {
        // Base cases 
        if (root == null) {
            return null;
        }

        // Current dimension is computed using current depth and total 
        // dimensions (k) 
        int currentdimension = depth % k;

        // Compare point with root with respect to cd (Current dimension) 
        if (currentdimension == d) {
            if (root.left == null) {
                return root;
            }
            return findMinRec(root.left, d, depth + 1);
        }

        // If current dimension is different then minimum can be anywhere 
        // in this subtree 
        return minNode(root, findMinRec(root.left, d, depth + 1), findMinRec(root.right, d, depth + 1), d);
    }

    // A wrapper over findMinRec(). Returns minimum of d'th dimension 
    public static Node findMin(Node root, int d) {
        // Pass current level or depth as 0 
        return findMinRec(root, d, 0);
    }

    // A utility method to determine if two Points are same 
    // in K Dimensional space 
    public static boolean arePointsSame(int[] point1, int[] point2) {
        // Compare individual pointinate values 
        for (int i = 0; i < k; ++i) {
            if (point1[i] != point2[i]) {
                return false;
            }
        }

        return true;
    }

    // Searches a Point represented by "point[]" in the K D tree. 
    // The parameter depth is used to determine current axis. 
    public static boolean searchRec(Node root, int[] point, int depth) {
        // Base cases 
        if (root == null) {
            return false;
        }
        if (arePointsSame(root.point, point)) {
            return true;
        }

        // Current dimension is computed using current depth and total 
        // dimensions (k) 
        int currentdimension = depth % k;

        // Compare point with root with respect to cd (Current dimension) 
        if (point[currentdimension] < root.point[currentdimension]) {
            return searchRec(root.left, point, depth + 1);
        }

        return searchRec(root.right, point, depth + 1);
    }

    // Searches a Point in the K D tree. It mainly uses 
    // searchRec() 
    public static boolean search(Node root, int[] point) {
        // Pass current depth as 0 
        return searchRec(root, point, 0);
    }
    // A utility method to determine if two Points are same 

    // Copies point p2 to p1 
    public static void copyPoint(int[] p1, int[] p2) {
        for (int i = 0; i < k; i++) {
            p1[i] = p2[i];
        }
    }

    // Function to delete a given point 'point[]' from tree with root 
    // as 'root'. depth is current depth and passed as 0 initially. 
    // Returns root of the modified tree. 
    public static Node deleteNodeRec(Node root, int[] point, int depth) {
        // Given point is not present 
        if (root == null) {
            return null;
        }

        // Find dimension of current node 
        int currentdimension = depth % k;

        // If the point to be deleted is present at root 
        if (arePointsSame(root.point, point)) {
            // 2.b) If right child is not NULL 
            if (root.right != null) {
                // Find minimum of root's dimension in right subtree 
                Node min = findMin(root.right, currentdimension);

                // Copy the minimum to root 
                copyPoint(root.point, min.point);

                // Recursively delete the minimum 
                root.right = deleteNodeRec(root.right, min.point, depth + 1);
            } else if (root.left != null) // same as above
            {
                Node min = findMin(root.left, currentdimension);
                copyPoint(root.point, min.point);
                root.right = deleteNodeRec(root.left, min.point, depth + 1);
            } else // If node to be deleted is leaf node
            {
                root = null;
                return null;
            }
            return root;
        }

        // 2) If current node doesn't contain point, search downward 
        if (point[currentdimension] < root.point[currentdimension]) {
            root.left = deleteNodeRec(root.left, point, depth + 1);
        } else {
            root.right = deleteNodeRec(root.right, point, depth + 1);
        }
        return root;
    }

    // Function to delete a given point from K D Tree with 'root' 
    public static Node deleteNode(Node root, int[] point) {
        // Pass depth as 0 
        return deleteNodeRec(root, point, 0);
    }

    public static void print(Node node, int space) {
        if (node != null) {
            space += 10;
            print(node.right, space);
            for (int i = 10; i < space; i++) {
                System.out.print(" ");
            }
            System.out.println(printArr(node.point));
            print(node.left, space);
        }
    }

    public static String printArr(int[] arr) {
        String res = "[" + arr[0];
        for (int i = 1; i < arr.length; i++) {
            res += ", " + arr[i];
        }
        res += "]";
        return res;
    }

    // Main method to call the above methods
    public static void main(String[] args) {
        Node root = null;
        int[][] points
                = {
                    {3, 6},
                    {2, 7},
                    {17, 15},
                    {6, 12},
                    {13, 15},
                    {9, 1},
                    {10, 19}
                };
        int[][] points2
                = {
                    {51, 75},
                    {25, 40},
                    {70, 70},
                    {10, 30},
                    {35, 90},
                    {55, 1},
                    {60, 80},
                    {1, 10},
                    {50, 50}
                };

        int n = points.length;

        for (int i = 0; i < n; i++) {
            root = insert(root, points[i]);

        }
        print(root, 0);
//        System.out.println(root);
        int[] point1 = {10, 19};
        search(root, point1);
        System.out.println("search result is -> " + search(root, point1) + " Node found");
//        
        System.out.println();
        // Delete (30, 40); 
        root = deleteNode(root, points[0]);

//// after deleting root node, show new root
//      System.out.println(root);
        print(root, 0);
//       // System.out.println(Node.displayroot());
        //System.out.println(Arrays.toString(points[0]));
        //System.out.println(points[0]);

    }

}
