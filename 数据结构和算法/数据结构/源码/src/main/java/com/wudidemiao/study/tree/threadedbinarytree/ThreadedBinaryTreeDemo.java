package com.wudidemiao.study.tree.threadedbinarytree;

/**
 * @author wudidemiaoa
 * @date 2022/1/18
 * @apiNote
 */
public class ThreadedBinaryTreeDemo {
    public static void main(String[] args) {
//        测试中序线索化二叉树功能
        HeroNode root = new HeroNode(1, "tom");
        HeroNode node2 = new HeroNode(3, "jack");
        HeroNode node3 = new HeroNode(6, "tom");
        HeroNode node4 = new HeroNode(8, "tom");
        HeroNode node5 = new HeroNode(10, "tom");
        HeroNode node6 = new HeroNode(14, "tom");

//        二叉树，手动创建
        root.setLeft(node2);
        node2.setLeft(node4);
        node2.setRight(node5);
        root.setRight(node3);
        node3.setLeft(node6);

        BinaryTree binaryTree = new BinaryTree();
        binaryTree.setHead(root);
        binaryTree.threadedNodes(root);
        System.out.println();

//        当线索化二叉树后，不能在使用原来的遍历方法，
        binaryTree.threadedList();
    }
}


// 定义一个ThreadBinaryTree 实现了线索化功能的二叉树
class BinaryTree {
    private HeroNode root;
    //    为了实现线索化，需要创建一个指向当前节点的前驱节点的引用
//    pre 总是保留保留前一个节点
    private HeroNode pre = null;

    public void setHead(HeroNode root) {
        this.root = root;
    }

    /**
     * 遍历中序线索化的二叉树
     */
    public void threadedList() {
//        定义一个变量，存储当前遍历的节点
        HeroNode node = root;
        while (node != null) {
//            循环的找到leftType=1的节点
//            后面随着遍历而变化，因为当leftType==1时，说明改节点时按照线索化处理后的有效节点，
            while (node.getLeftType() == 0) {
                node = node.getLeft();
            }
//           打印当前节点
            System.out.println(node);
//            如果当前节点的右指针指向的是后置节点
            while (node.getRightType() == 1) {
                node = node.getRight();
                System.out.println(node);
            }
//            替换这个遍历的节点
            node = node.getRight();
        }
    }

    /**
     * 编写对二叉树中序线索化的方法
     *
     * @param node 当前需要线索化的节点
     */
    public void threadedNodes(HeroNode node) {
        if (node == null) {
            return;
        }
//        先线索化左子树
        if (node.getLeft() != null) {
            threadedNodes(node.getLeft());
        }
//        在线索化处理当前节点

//        处理当前结点的前驱节点
        if (node.getLeft() == null) {
//          让当前节点的左执政指向前驱节点
            node.setLeft(pre);
//            修改当前做执政的类型,指向前驱节点
            node.setLeftType(1);
        }

        //        处理当前结点的后驱节点
        if (pre != null && pre.getRight() == null) {
//          让当前节点的左执政指向前驱节点
            pre.setRight(node);
//            修改前驱节点的右执政类型
            pre.setRightType(1);
        }
//        每处理一个节点后，让当前节点是下一个节点的前驱节点
        pre = node;

//        让前驱节点的右指针指向当前的节点

//        在线索化处理右子树
        threadedNodes(node.getRight());
    }

    //    前序遍历
    public void preOrder() {
        if (root != null) {
            root.preOrder();
        } else {
            System.out.println("二叉树为空，无法遍历");
        }
    }

    //    中序遍历
    public void infixOrder() {
        if (root != null) {
            root.inFixOrder();
        } else {
            System.out.println("二叉树为空，无法遍历");
        }
    }

    //    hou序遍历
    public void postOrder() {
        if (root != null) {
            root.afterOrder();
        } else {
            System.out.println("二叉树为空，无法遍历");
        }
    }

    //    前序查找
    public HeroNode preOrderSearch(int no) {
        if (root != null) {
            return root.preOrderSearch(no);
        } else {
            return null;
        }
    }

    //    zhong序查找
    public HeroNode infixOrderSearch(int no) {
        if (root != null) {
            return root.infixSearch(no);
        } else {
            return null;
        }
    }

    //    后序
    public HeroNode ipostOrderSearch(int no) {
        if (root != null) {
            return root.postSearch(no);
        } else {
            return null;
        }
    }

    public void remove(int no) {
        if (root.getId() == no) {
            root = null;
            System.out.println("删除成功");
        } else {
            if (root.remove(no)) {
                System.out.println("删除成功");
            } else {
                System.out.println("删除失败");
            }
        }
    }
}


// 创建节点
class HeroNode {
    private int id;
    private String name;
    //    左右默认为空
    private HeroNode left;
    private HeroNode right;
    //    1.弱国leftType == 0，表示指向的是左子树，如果是1则表示前驱结点
//    2.如果rightType == 0，表示指向是右子树，如果是1表示指向后续结点
    private int leftType;

    public int getLeftType() {
        return leftType;
    }

    public void setLeftType(int leftType) {
        this.leftType = leftType;
    }

    public int getRightType() {
        return rightType;
    }

    public void setRightType(int rightType) {
        this.rightType = rightType;
    }

    private int rightType;


    public HeroNode(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HeroNode getLeft() {
        return left;
    }

    public void setLeft(HeroNode left) {
        this.left = left;
    }

    public HeroNode getRight() {
        return right;
    }

    public void setRight(HeroNode right) {
        this.right = right;
    }

    @Override
    public String toString() {
        return "HeroNode{" +
                "id=" + id +
                ", name=" + name +
                '}';
    }

    //     前序遍历
    public void preOrder() {
        System.out.println(this);  // 先输出父节点
//        递归左子树前序遍历
        if (this.left != null) {
            this.left.preOrder();
        }
        if (this.right != null) {
            this.right.preOrder();
        }
    }

    //    中序遍历
    public void inFixOrder() {
//        递归左子树前序遍历
        if (this.left != null) {
            this.left.inFixOrder();
        }
        System.out.println(this);  // 输出父节点
        if (this.right != null) {
            this.right.inFixOrder();
        }
    }

    //    后序遍历
    public void afterOrder() {
        //        递归左子树前序遍历
        if (this.left != null) {
            this.left.afterOrder();
        }
        if (this.right != null) {
            this.right.afterOrder();
        }
        System.out.println(this);  // 输出父节点
    }

    //    前序查找
    public HeroNode preOrderSearch(int id) {
        System.out.println("进入前序遍历");
        if (this.id == id) {
            return this;
        }
        HeroNode node = null;
        if (this.left != null) {
            node = this.left.preOrderSearch(id);

        }
        if (node != null) {
            return node;
        }

        if (this.right != null) {
            node = this.right.preOrderSearch(id);
        }
        return node;
    }


    //   中序查找
    public HeroNode infixSearch(int id) {
        HeroNode node = null;
        if (this.left != null) {
            node = this.left.infixSearch(id);
        }
        if (node != null) {
            return node;
        }
        System.out.println("进入中序查找");
        if (this.id == id) {
            return this;
        }

        if (this.right != null) {
            node = this.right.infixSearch(id);
        }
        return node;
    }

    //     后序查找
    public HeroNode postSearch(int id) {
        HeroNode node = null;
        if (this.left != null) {
            node = this.left.postSearch(id);
        }
        if (node != null) {
            return node;
        }

        if (this.right != null) {
            node = this.right.postSearch(id);
        }
        if (node != null) {
            return node;
        }
        System.out.println("进入后序查找");
        if (this.id == id) {
            return this;
        }

        return null;
    }

    public boolean remove(int id) {
        if (this.left != null) {
            if (this.left.id == id) {
                this.left = null;
                return true;
            }
        }

        if (this.right != null) {
            if (this.right.id == id) {
                this.right = null;
                return true;
            }
        }

        return (this.left != null && this.left.remove(id)) || (this.right != null && this.right.remove(id));
    }
}


