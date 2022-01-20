package com.wudidemiao.study.tree;

/**
 * @author wudidemiaoa
 * @date 2022/1/17
 * @apiNote
 */
public class BinaryTreeDemo {
    public static void main(String[] args) {
//         创建二叉树
        BinaryTree binaryTree = new BinaryTree();
//         创建需要的节点
        HeroNode node1 = new HeroNode(1, "zhangsan");
        HeroNode node2 = new HeroNode(2, "lisi");
        HeroNode node3 = new HeroNode(3, "wujunyi");
        HeroNode node4 = new HeroNode(4, "linchong");

        node2.setLeft(node1);
        node2.setRight(node3);
        node3.setRight(node4);
        binaryTree.setHead(node2);
//        前序遍历
//        System.out.println("前序遍历..");
//        binaryTree.preOrder();
////        中序遍历
//        System.out.println("中序遍历..");
//        binaryTree.infixOrder();
////        后序遍历
//        System.out.println("后序遍历..");
//        binaryTree.postOrder();
//        System.out.println("前序查找");
//        System.out.println(binaryTree.preOrderSearch(1));
//        System.out.println("中序查找");
//        System.out.println(binaryTree.infixOrderSearch(1));
//        System.out.println("后序查找");
//        System.out.println(binaryTree.ipostOrderSearch(1));
        binaryTree.remove(3);
        binaryTree.remove(5);
        binaryTree.preOrder();
    }
}

// 定义一个BinaryTree 数
class BinaryTree {
    private HeroNode root;

    public void setHead(HeroNode root) {
        this.root = root;
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

    public void remove(int no){
        if(root.getId()==no){
            root =null;
            System.out.println("删除成功");
        }else {
            if (root.remove(no)) {
                System.out.println("删除成功");
            }else {
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

        return  (this.left !=null&&this.left.remove(id)) || (this.right !=null && this.right.remove(id));
    }
}
