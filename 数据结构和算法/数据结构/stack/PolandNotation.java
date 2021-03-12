package stack;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @version 1.0
 * 注意：本内容仅限于西安城市发展资源信息有限公司内部传阅，禁止外泄以及用于其他的商业目
 * @ClassName PolandNotation
 * @Description TODO
 * @Aurhor xu
 * @Ddte 2021/1/4 14:06
 **/
public class PolandNotation {

    public static void main(String[] args) {
        String zzbds = "1+( 2 * 3)-5";
        List<String> arraylists = arraylists(zzbds);
        System.out.println(arraylists);
        List<String> strings = ZzZhZz(arraylists);
        System.out.println(strings);
//        String rec = "1 2 3 + 4 * + 5 -";
//        List<String> strings = arrayList(rec);
        String s = calCulate(strings);
        System.out.println(s);
    }

    //    转后缀表达式的转化方法
    public static List<String> ZzZhZz(List<String> strings) {
        List<String> list = new ArrayList();
//        运算符栈 s1
        Stack<String> s1 = new Stack<>();
//        储存中间结果栈
        Stack<String> s2 = new Stack<>();
//  两个操作数，一个运算符 还有一个结果集，还有一个指针
        int index = 0;
        int num1;
        int num2;
        int oper;
        String sum = "";
        do {
            String s = strings.get(index);
            if (s.matches("\\d+")) {
                s2.push(s);
            }else if ("(".equals(s)){
                s1.push(s);
            }else if(")".equals(s)){
                while (true){
                    String pop = s1.pop();
                    if(!"(".equals(pop)){
                        s2.push(pop);
                    }else{
                        break;
                    }
                }
            }else{
//                if(!s1.empty()) {
//                    if (!s1.peek().equals("(")) {
//                        s2.push(s1.pop());
//                    }
//                }else{
//                    s1.push(s);
//                    return;
//                }
                dgff(s1,s2,s);
            }
            index++;
        } while (index < strings.size());
        if (s1.size() != 0) {
            for (int j=0;j<s1.size();j++){
                s2.push(s1.pop());
            }
        }
        for (String s : s2) {
            sum =sum+s;
        }
        for(int i =0;i<sum.length();i++){
            list.add(sum.substring(i,i+1));
        }
        return list;
    }
//    递归调用的方法
    public static void dgff(Stack<String> s1,Stack<String> s2,String s){
        //                s1栈为空
        if(!s1.empty()){
//                    取出栈顶得元素
            String pop = s1.pop();
            if("(".equals(pop)){
                s1.push(pop);
                s1.push(s);
                return;
            }else{
                if(yxj(s)>yxj(pop)){
                    s1.push(pop);
                    s1.push(s);
                    return;
                }else{
                    s2.push(pop);
                    dgff(s1,s2,s);
                }
            }
        }else{
            s1.push(s);
            return;
        }
    }

    //    中缀表达式转后缀表达式  将字符串转为集合
    public static List<String> arraylists(String zznds) {
        List<String> list = new ArrayList<>();
        String stc = "";
//        记此器
        int j = 0;
        while (true) {
            String substring = zznds.charAt(j)+"";
//            判度不为空
            while (true) {
                if (!" ".equals(substring) && !"".equals(substring)) {
//                    是一个数字
                    if (substring.matches("\\d+")) {
                        stc = stc+substring + "";

                    } else {
                        if ("".equals(stc)) {
                            list.add(substring);
                            break;
                        } else {
                            list.add(stc);
                            list.add(substring);
                            stc = "";
                            break;
                        }
                    }
                }
                j++;
                if(j==zznds.length()){
                    if(!stc.isEmpty()){
                        list.add(stc);
                        stc="";
                    }
                    break;
                }
                substring = zznds.charAt(j)+"";
                System.out.println(j+"d");


            }

            System.out.println(zznds.length());
            if (j >= zznds.length()-1) {
                break;
            }
            j++;
        }
        return list;
    }


    public static int yxj(String opers){
        int value=0;
        switch (opers){
            case "-":
            case "+":
                value= 1;
                break;
            case "/":
            case "*":
                value= 2;
                break;
        }
        return value;
    }

    //   将字符串转为list集合
    public static List<String> arrayList(String rec) {
        if (rec.isEmpty()) {
            System.out.println("字符串为空");
            return null;
        }
        String[] s = rec.split(" ");
        List arrayList = new ArrayList();
        for (String s1 : s) {
            arrayList.add(s1);
        }
        return arrayList;
    }

    //    计算方法
    public static String calCulate(List<String> strings) {
//        创建栈区
        Stack<Integer> stack = new Stack();
        int num1;
        int num2;
        int sum;
        for (String string : strings) {
//            如果位数字
            System.out.println(stack);
            if (string.matches("\\d+")) {
                stack.push(Integer.parseInt(string));
            } else {
                num1 = stack.pop();
                num2 = stack.pop();
                switch (string) {
                    case "+":
                        sum = num1 + num2;
                        stack.push(sum);
                        break;
                    case "-":
                        sum = num2 - num1;
                        stack.push(sum);
                        break;
                    case "*":
                        sum = num1 * num2;
                        stack.push(sum);
                        break;
                    case "/":
                        sum = num1 / num2;
                        stack.push(sum);
                        break;
                    default:
                        break;
                }
            }
        }
        return stack.pop() + "";
    }
}
