package com.wudidemiao.study.binarysorttree;

/**
 * @author wudidemiaoa
 * @date 2022/1/20
 * @apiNote
 */
public class BinarySortTreeDemo {
    public static void main(String[] args) {
        int[] arr = {7, 3, 10, 12, 5, 1, 9, 0};
        BinarySortTree tree = new BinarySortTree();
        for (int i : arr) {
            tree.add(i);
        }
        tree.infixOrder();
//        tree.delNode(2);
        tree.delNode(10);
        tree.infixOrder();
    }
}

class BinarySortTree {
    private Node head;

    //    查找要删除的节点
    public Node search(int value) {
        if (head == null) {
            return null;
        }
        return head.search(value);
    }

    //    查找要删除的节点的夫节点
    public Node searchParent(int value) {
        if (head == null) {
            return null;
        }
        return head.searchParent(value);
    }

    /**
     * 返回 以node为根节点的二叉排序树的最小节点的值
     * 删除node为根节点的二叉排序树的最小节点
     *
     * @param node 传入的节点（当作二叉排序树的根节点）
     * @return  返回的以node为根节点的二叉排序树的最小节点的值
     */
    public int delRight(Node node){
        Node target = node;
//        循环的查找左节点，就会找到最小值
        while (target.left !=null){
            target = target.left;
        }
//        这时target就指向最小节点
        delNode(target.value);
        return target.value;
    }

    public int delLeft(Node node){
        Node target = node;
//        循环的查找左节点，就会找到最小值
        while (target.right !=null){
            target = target.right;
        }
//        这时target就指向最小节点
        delNode(target.value);
        return target.value;
    }


    //    删除节点
    public void delNode(int value) {
        if (head == null) {
            return;
        }
//        需要先去找要删除的节点targetNode
        Node targetNode = search(value);
        if (targetNode == null) {
            return;
        }
//        如果发现当前这颗二叉排序树只有一个节点，
        if (head.left == null && head.right == null) {
            head = null;
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
                if(parent==null){
                    head = targetNode.left;
                    return;
                }
                if (parent.left.value == value) {
                    parent.left = targetNode.left;
                } else if (parent.right.value == value) {
                    parent.right = targetNode.left;
                }
            } else {  // 要删除的节点有右子节点
                if(parent==null){
                    head = targetNode.right;
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
        if (head != null) {
            head.add(new Node(value));
        } else {
            head = new Node(value);
        }
    }

    public void infixOrder() {
        if (head != null) {
            head.infixOrder();
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
