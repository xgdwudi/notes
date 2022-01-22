package com.wudidemiao.study.avl;

/**
 * @author wudidemiaoa
 * @date 2022/1/21
 * @apiNote
 */
public class AVLTreeDemo {
    public static void main(String[] args) {
//        int[] arr = {4, 3, 6, 5, 7, 8};
//        int[] arr = {10, 12, 8, 9, 7, 6};
        int[] arr = {10, 11, 7, 6, 8, 9};
//        int[] arr = {2, 1, 6, 5, 7, 3};
//        创建一个avl树对象
        AVLTree avlTree = new AVLTree();
//        添加节点
        for (int i = 0; i < arr.length; i++) {
            avlTree.add(arr[i]);
        }
        System.out.println("中序遍历。。。");
        avlTree.infixOrder();

        System.out.println("无平衡处理之前");
        System.out.println("树高度" + avlTree.root.height());
        System.out.println("左树高度" + avlTree.root.leftHeight());
        System.out.println("右树高度" + avlTree.root.rightHeight());
        System.out.println("根树" + avlTree.root);
    }
}

//创建avl树
class AVLTree {
    Node root;

    //    查找要删除的节点
    public Node search(int value) {
        if (root == null) {
            return null;
        }
        return root.search(value);
    }

    //    查找要删除的节点的夫节点
    public Node searchParent(int value) {
        if (root == null) {
            return null;
        }
        return root.searchParent(value);
    }

    /**
     * 返回 以node为根节点的二叉排序树的最小节点的值
     * 删除node为根节点的二叉排序树的最小节点
     *
     * @param node 传入的节点（当作二叉排序树的根节点）
     * @return 返回的以node为根节点的二叉排序树的最小节点的值
     */
    public int delRight(Node node) {
        Node target = node;
//        循环的查找左节点，就会找到最小值
        while (target.left != null) {
            target = target.left;
        }
//        这时target就指向最小节点
        delNode(target.value);
        return target.value;
    }

    public int delLeft(Node node) {
        Node target = node;
//        循环的查找左节点，就会找到最小值
        while (target.right != null) {
            target = target.right;
        }
//        这时target就指向最小节点
        delNode(target.value);
        return target.value;
    }

    //    删除节点
    public void delNode(int value) {
        if (root == null) {
            return;
        }
//        需要先去找要删除的节点targetNode
        Node targetNode = search(value);
        if (targetNode == null) {
            return;
        }
//        如果发现当前这颗二叉排序树只有一个节点，
        if (root.left == null && root.right == null) {
            root = null;
            return;
        }

//        去查找targetNode的父节点
        Node parent = searchParent(value);
//        如果要删除的是叶子节点
        if (targetNode.left == null && targetNode.right == null) {
            if (parent.left != null && parent.left.value == value) {
                parent.left = null;
            } else if (parent.right != null && parent.right.value == value) {
                parent.right = null;
            }
            return;
        } else if (targetNode.left != null && targetNode.right != null) {  //删除目录下存在左右子树
            int minValue = delRight(targetNode.right);
            targetNode.value = minValue;
        } else { // 删除只有一颗子树的节点
            // 如果要删除的节点有左子节点
            if (targetNode.left != null) {
                if (parent == null) {
                    root = targetNode.left;
                    return;
                }
                if (parent.left.value == value) {
                    parent.left = targetNode.left;
                } else if (parent.right.value == value) {
                    parent.right = targetNode.left;
                }
            } else {  // 要删除的节点有右子节点
                if (parent == null) {
                    root = targetNode.right;
                    return;
                }
                if (parent.left.value == value) {
                    parent.left = targetNode.right;
                } else if (parent.right.value == value) {
                    parent.right = targetNode.right;
                }
            }
        }

    }

    public void add(int value) {
        if (root != null) {
            root.add(new Node(value));
        } else {
            root = new Node(value);
        }
    }

    public void infixOrder() {
        if (root != null) {
            root.infixOrder();
        } else {
            System.out.println("不能遍历");
        }
    }

}


class Node {
    int value;
    Node left;
    Node right;

    public Node(int value) {
        this.value = value;
    }

    //    返回左子树的高度
    public int leftHeight() {
        if (left == null) {
            return 0;
        }
        return left.height();
    }

    //    返回右子树的高度
    public int rightHeight() {
        if (right == null) {
            return 0;
        }
        return right.height();
    }

    //    返回当前节点的高度,以该节点为根节点的树的高度
    public int height() {
        return Math.max(left == null ? 0 : left.height(), right == null ? 0 : right.height()) + 1;
    }

    private void leftRotate() {
//        创建新的节点，当前根节点的值
        Node newNode = new Node(value);
//        把新的节点左子树设置了当前节点的左子树
        newNode.left = left;
//        把新节点的右子树设置为当前节点的右子树的左子树
        newNode.right = right == null ? null : right.left;
//        把当前节点的值替换为右子节点的值
        value = right == null ? null : right.value;
//        把当前节点的右子树设置成右子树的右子树
        right = right == null ? null : right.right;
//        把当前节点的左子树设置为新节点
        left = newNode;
    }

    //    右旋转
    private void rightRotate() {
        //        创建新的节点，当前根节点的值
        Node newNode = new Node(value);
//        把新的节点的右子树设置为当前节点的右子树
        newNode.right = right;
        newNode.left = left.right;
        value = left.value;
        left = left.left;
        right = newNode;
    }

    /**
     * 查找要删除的节点
     *
     * @param value
     */
    public Node search(int value) {
        if (this.value == value) { // 找到该节点
            return this;
        } else if (this.value > value) {  // 如果查找的值小于当前节点，向左子树递归查找
            if (this.left == null) {
                return null;
            }
            return this.left.search(value);
        } else {
            if (this.right == null) {
                return null;
            }
            return this.right.search(value);
        }
    }

    /**
     * 查找要删除的节点的父节点
     *
     * @param value 要找的节点的值
     * @return 返回的是要删除的节点的父节点，如果没有就返回null;
     */
    public Node searchParent(int value) {
        if (this.value == value) {
            return null;
        }
        if ((this.left != null && this.left.value == value) || (this.right != null && this.right.value == value)) {
            return this;
        } else {
//            如果查找的值小于当前节点的值，并且当前节点的左子节点不为空
            if (value < this.value && this.left != null) {
                return this.left.searchParent(value);
            } else if ((value >= this.value && this.right != null)) {
                return this.right.searchParent(value);
            } else {
                return null;
            }
        }
    }

    public void add(Node node) {
        if (node == null) {
            return;
        }
        if (node.value < value) {
            if (left == null) {
                left = node;
            } else {
                this.left.add(node);
            }
        } else {
            if (right == null) {
                right = node;
            } else {
                this.right.add(node);
            }
        }
        int i = leftHeight();
        int i1 = rightHeight();
//        当添加完一个节点后，如果右子树的高度比左子树的高度大于1，发生左旋转
        if (rightHeight() - leftHeight() > 1) {
            if (right != null && right.leftHeight() > right.rightHeight()) {
                right.rightRotate();
            }
//                先对右子树进行
            leftRotate();
            return;
        }

        if (leftHeight() - rightHeight() > 1) {
            if (left != null && left.rightHeight() > left.leftHeight()) {
//                先对当前节点的左节点，进行左旋转
                left.leftRotate();
            }
//                先对右子树进行
            rightRotate();
        }
    }

    public void infixOrder() {
        if (left != null) {
            left.infixOrder();
        }
        System.out.println(this);
        if (right != null) {
            right.infixOrder();
        }
    }

    @Override
    public String toString() {
        return "Node{" +
                "value=" + value +
                '}';
    }
}
