package LinkedList;

/**
 * @version 1.0
 * 注意：本内容仅限于西安城市发展资源信息有限公司内部传阅，禁止外泄以及用于其他的商业目
 * @ClassName DoubleLinkedList
 * @Description TODO
 * @Aurhor xu
 * @Ddte 2021/1/2 17:14
 **/
public class DoubleLinkedLists {
    public static void main(String[] args) {
        DoubleLinkedList doubleLinkedList = new DoubleLinkedList();
        HorderNode zhangsan = new HorderNode(1, "zhangsan");
        HorderNode lisi = new HorderNode(4, "李四");
        HorderNode zh =new HorderNode(2,"网五月");
        HorderNode zhangsans = new HorderNode(2, "sss");
        HorderNode sdd = new HorderNode(6, "sss");
        System.out.println(".....................");
        doubleLinkedList.list();
        doubleLinkedList.add(zhangsan);
        doubleLinkedList.add(lisi);
        doubleLinkedList.add(zh);
        doubleLinkedList.add(sdd);
        System.out.println(".....................");
        doubleLinkedList.list();
        doubleLinkedList.del(1);
        doubleLinkedList.list();
        doubleLinkedList.add(zhangsan);
        doubleLinkedList.list();
        doubleLinkedList.update(zhangsans);
        doubleLinkedList.list();

    }

}

class DoubleLinkedList {
    private HorderNode head = new HorderNode(0, "");

    public HorderNode getHead() {
        return head;
    }

    //遍历链表
    public void list() {
        if (head.next == null) {
            System.out.println("该链表没有数据");
            return;
        }
        HorderNode next = head.next;
        while (true) {
            if (next == null) {
                return;
            }
            System.out.println(next);
            next = next.next;
        }
    }

    //    添加数据
    public void add(HorderNode horderNode) {
        if (head.next == null) {
            horderNode.pre = head;
            head.next = horderNode;
            System.out.println("添加成功11");
            return;
        }
        HorderNode adds = head.next;
        while (true) {
            System.out.println(adds.no);
            if (adds.no > horderNode.no) {
                adds.pre.next=horderNode;
                horderNode.pre=adds.pre;
                horderNode.next=adds;
                System.out.println("添加成功33");
                return;
            }else if(adds.no == horderNode.no){
                System.out.println("已存在此编号，添加失败");
                return;
            }
            HorderNode add=adds;
            adds = adds.next;
            if(adds==null){
                horderNode.pre = add;
                add.next = horderNode;
                System.out.println("添加成功22");
                return;
            }
        }
    }

    //    删除数据
    public void del(int no) {
        if (head.next == null) {
            System.out.println("该链表无数据");
            return;
        }
        HorderNode next = head.next;
        while (true) {
            if (next == null) {
                System.out.println("未找到删除的编号");
                return;
            }
            if (next.no == no) {
                next.pre.next = next.next;
                if (next.next != null) {
                    next.next.pre = next.pre;
                }
                return;
            }
            next=next.next;
        }
    }
//    修改方法
    public void update(HorderNode horderNode){
        if (head.next == null) {
            System.out.println("该链表无数据");
            return;
        }
        HorderNode next = head.next;
        while (true) {
            if (next == null) {
                System.out.println("未找到xiugai的编号");
                return;
            }
            if (next.no == horderNode.no) {
                next.name=horderNode.name;
                return;
            }
            next=next.next;
        }
    }
}


class HorderNode {
    //    编号
    public int no;
    //    姓名
    public String name;
    //    指向前面的指针
    public HorderNode pre;
    //    指向后面的指针
    public HorderNode next;

    //构造
    public HorderNode(int no, String name) {
        this.no = no;
        this.name = name;
    }

    @Override
    public String toString() {
        return "HorderNode{" +
                "no=" + no +
                ", name='" + name + '\'' +
                '}';
    }
}
