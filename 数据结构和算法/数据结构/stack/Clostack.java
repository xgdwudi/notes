package stack;


import java.util.Scanner;

/**
 * @version 1.0
 * 注意：本内容仅限于西安城市发展资源信息有限公司内部传阅，禁止外泄以及用于其他的商业目
 * @ClassName Clostack
 * @Description TODO
 * @Aurhor xu
 * @Ddte 2021/1/3 10:23
 **/
public class Clostack {
    public static void main(String[] args) {
        StackDemo num = new StackDemo(10);
        StackDemo opers = new StackDemo(10);
        String rec = "9+0*0-0";
        int index = 0;  // rec指针位置
        int num1;  // 数栈取出得第一个数字
        int num2; // 数栈取出得第二个数字
        int sum;   // 两者的乘机
        int oper;  //符号栈取出的符号
        char ch = ' ';  // 扫描出的rec
        while (true) {
            ch = rec.charAt(index);
// 判度是否为运算符
            if (opers.isoper(ch)) {
//                判度当前符号栈是否为空
                if (opers.isEmpty()) {
                        opers.push(ch);
                } else {
                    //   如果操作栈有操作符，就进行比较，如果当前操作符小于等于栈顶的操作符优先级，则需要从数栈中pop出2个数（优先级为后面取得操作前面取得），
                    //   并从符号栈中pop出一个符号，惊醒运算，并将得到得结果放入数栈，然后将当前得操作符入符号栈，如果当前得操作符优先级大于栈中得操作符，就直接入符号栈
                    int priotery = num.priotery(ch);
                    if (priotery <= num.priotery(opers.stackTop())) {
                        num1 = num.pop();
                        num2 = num.pop();
                        oper = opers.pop();
                        sum = num.cal(num2,num1,oper);
                        num.push(sum);
                        opers.push(ch);
                    }else {
                        opers.push(ch);
                    }
                }

            }else {
                num.push(ch-'0');
            }
            index++;
            if(index>=rec.length()){
                break;
            }
        }
        while (true){
//            如果符号栈为空
            if(opers.isEmpty()){
                break;
            }
            num1 = num.pop();
            num2 = num.pop();
            oper = opers.pop();
            sum = num.cal(num2,num1,oper);
            num.push(sum);
        }
        int pop = num.pop();
        System.out.printf("表达式%s=%d",rec,pop);
    }
}

class StackDemo {
    private int maxSize;
    private int top;
    private int[] stack;

    public StackDemo(int maxSize) {
        this.top = -1;
        this.maxSize = maxSize;
        this.stack = new int[maxSize];
    }

    //    判断是否为空
    public Boolean isEmpty() {
        return top == -1;
    }

    //    判度栈是否满
    public Boolean isFull() {
        return top == maxSize - 1;
    }

    //    出栈
    public int pop() {
        if (isEmpty()) {
            throw new RuntimeException("栈为空~~");
        }
        int value = stack[top];
        top--;
        return value;
    }

    //    入栈
    public void push(int value) {
        if (isFull()) {
            System.out.println("栈已满~~");
            return;
        }
        top++;
        stack[top] = value;
    }

    //    遍历数据
    public void list() {
        if (isEmpty()) {
            System.out.println("没有数据~~");
            return;
        }
        for (int i = top; i >= 0; i--) {
            System.out.printf("stack[%d]=%d\n", i, stack[i]);
        }
    }

    //    取出栈顶元素
    public int stackTop() {
        return stack[top];
    }

    //    返回运算符得优先级,根据数字，数字越大优先级越大
    public int priotery(int oper) {
        if (oper == '*' || oper == '/') {
            return 1;
        } else {
            return 0;
        }
    }

    //    判度器是否为运算符
    public Boolean isoper(int oper) {
        return oper == '*' || oper == '-' || oper == '/' || oper == '+';
    }

    //    运算方法
    public int cal(int num1, int num2, int oper) {
        int value = 0;
        switch (oper) {
            case '+':
                value = num1 + num2;
                break;
            case '-':
                value = num1 - num2;
                break;
            case '*':
                value = num1 * num2;
                break;
            case '/':
                value = num1 / num2;
                break;
            default:
                break;
        }
        return value;
    }
}


