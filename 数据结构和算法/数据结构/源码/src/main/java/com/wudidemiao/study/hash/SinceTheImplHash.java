package com.wudidemiao.study.hash;

/**
 * @author wudidemiaoa
 * @date 2022/1/17
 * @apiNote
 */
public class SinceTheImplHash {
    public static void main(String[] args) {
        HashTableImpl hashTable = new HashTableImpl(5);
        hashTable.add(new Emp(1, "张三"));
        hashTable.add(new Emp(2, "李四"));
        hashTable.add(new Emp(3, "王五"));
        hashTable.add(new Emp(6, "炸弹"));
        hashTable.list();
        hashTable.getById(4);
        hashTable.removeByid(6);
        hashTable.removeByid(1);
        hashTable.removeByid(1);
        hashTable.list();
    }
}

class HashTableImpl {
    private EmplinkedList[] emplinkedLists;

    private int size;

    public HashTableImpl(int size) {
        this.size = size;
        ;
        this.emplinkedLists = new EmplinkedList[size];
        for (int i = 0; i < size; i++) {
            this.emplinkedLists[i] = new EmplinkedList();
        }
    }

    public void add(Emp emp) {
//        根据 员工的id，得到员工应该添加到那条链表
        int hash = hash(emp.id);
        emplinkedLists[hash].add(emp);
    }

    public void list() {
        for (int index = 0; index < emplinkedLists.length; index++) {
            emplinkedLists[index].list(index);
        }
    }

    public void getById(int id) {
        int hash = hash(id);
        Emp byId = emplinkedLists[hash].getById(id);
        if (byId == null) {
            System.out.println("没有找到");
        } else {
            System.out.println("找到了" + byId);
        }
    }
    public void removeByid(int id) {
        int hash = hash(id);
        Emp byId = emplinkedLists[hash].removeById(id);
        if (byId == null) {
            System.out.println("没有找到");
        } else {
            System.out.println("找到了" + byId);
        }
    }

    public int hash(int id) {
        return id % size;
    }
}

// 链表
class EmplinkedList {
    private Emp head;  // 头指针，指向当前链表的第一个雇员

    //    添加雇员，id是自增长的
    public void add(Emp emp) {
        if (head == null) {
            head = emp;
            return;
        }
//        如不是第一个，添加辅助指针，到最后一个
        Emp curEmp = head;
        while (true) {
            if (curEmp.next == null) {
                break;
            }

            curEmp = curEmp.next;  // 后移
        }
        curEmp.next = emp;
    }

    public void list(int index) {
        System.out.println();
        if (head == null) {
            System.out.println("第" + index + "条链表为空,编号：");
            return;
        }
        System.out.println("第" + index + "条链表信息为：");
        Emp curEmp = head; //辅助指针
        while (true) {
            System.out.printf("第" + index + "条 =>id=%d链表 name=%s\t", curEmp.id, curEmp.name);
            if (curEmp.next == null) {
                break;
            }
            curEmp = curEmp.next;  // 后移
        }
    }

    public Emp removeById(int id){
        if (head == null) {
            System.out.println("链表为空");
            return null;
        }
        Emp curEmp = head; //辅助指针
        Emp prep = curEmp;
        while (true) {
            if (curEmp == null) {
                break;
            }
            if (curEmp.id == id) {
                if(prep == curEmp){
                    head = curEmp.next;
                }else {
                    prep.next = curEmp.next;
                }
                curEmp.next = null;
                break;
            }
            prep = curEmp;
            curEmp = curEmp.next;  // 后移
        }
        return curEmp;
    }

    public Emp getById(int id) {
        if (head == null) {
            System.out.println("链表为空");
            return null;
        }
        Emp curEmp = head; //辅助指针
        while (true) {
            if (curEmp.id == id) {
                break;
            }
            curEmp = curEmp.next;  // 后移
            if (curEmp == null) {
                break;
            }
        }
        return curEmp;
    }
}

// 表示一个雇员
class Emp {
    public int id;

    public String name;

    public Emp next; // next默认为null

    public Emp(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Emp{" +
                "id=" + id +
                ", name='" + name;
    }
}
