### 字符串和编码

#### String 

> String是一个引用类型，它本身也是一个class。但是，Java编译器对String有特殊处理，即可以直接用"…"来表示一个字符串

> 实际上字符串在String内部是通过一个char[]数组表示的，因此，按下面的写法也是可以的

`String s2 = new String(new char[] {'H', 'e', 'l', 'l', 'o', '!'});`

- Java字符串的一个重要特点就是字符串不可变。这种不可变性是通过内部的private final char[]字段，以及没有任何修改char[]的方法实现的。

- 我们实际上是想比较字符串的内容是否相同。必须使用equals()方法而不能用==

> 结论：两个字符串比较，必须总是使用equals()方法。要忽略大小写比较，使用equalsIgnoreCase()方法。

```
// 是否包含子串:
"Hello".contains("ll"); // true
注意到contains()方法的参数是CharSequence而不是String，因为CharSequence是String的父类。
"Hello".indexOf("l"); // 2
"Hello".lastIndexOf("l"); // 3
"Hello".startsWith("He"); // true
"Hello".endsWith("lo"); // true
提取子串的例子：
"Hello".substring(2); // "llo"
"Hello".substring(2, 4); "ll"
注意索引号是从0开始的。
```
去除首尾空白字符
```
"  \tHello\r\n ".trim(); // "Hello"

```
另一个strip()方法也可以移除字符串首尾空白字符。它和trim()不同的是，类似中文的空格字符\u3000也会被移除：
```
"\u3000Hello\u3000".strip(); // "Hello"
" Hello ".stripLeading(); // "Hello "
" Hello ".stripTrailing(); // " Hello"
```
String还提供了isEmpty()和isBlank()来判断字符串是否为空和空白字符串：
```
"".isEmpty(); // true，因为字符串长度为0
"  ".isEmpty(); // false，因为字符串长度不为0
"  \n".isBlank(); // true，因为只包含空白字符
" Hello ".isBlank(); // false，因为包含非空白字符
```
##### 替换子串

要在字符串中替换子串，有两种方法。一种是根据字符或字符串替换：
```
String s = "hello";
s.replace('l', 'w'); // "hewwo"，所有字符'l'被替换为'w'
s.replace("ll", "~~"); // "he~~o"，所有子串"ll"被替换为"~~"
另一种是通过正则表达式替换：
String s = "A,,B;C ,D";
s.replaceAll("[\\,\\;\\s]+", ","); // "A,B,C,D"
```

##### 分割字符串

要分割字符串，使用split()方法，并且传入的也是正则表达式：
```
String s = "A,B,C,D";
String[] ss = s.split("\\,"); // {"A", "B", "C", "D"}
```

##### 拼接字符串

拼接字符串使用静态方法join()，它用指定的字符串连接字符串数组：

```
String[] arr = {"A", "B", "C"};
String s = String.join("***", arr); // "A***B***C"
```
##### 类型转换

要把任意基本类型或引用类型转换为字符串，可以使用静态方法valueOf()。这是一个重载方法，编译器会根据参数自动选择合适的方法：
```
String.valueOf(123); // "123"
String.valueOf(45.67); // "45.67"
String.valueOf(true); // "true"
String.valueOf(new Object()); // 类似java.lang.Object@636be97c
```

```
要把字符串转换为其他类型，就需要根据情况。例如，把字符串转换为int类型：

int n1 = Integer.parseInt("123"); // 123
int n2 = Integer.parseInt("ff", 16); // 按十六进制转换，255
把字符串转换为boolean类型：

boolean b1 = Boolean.parseBoolean("true"); // true
boolean b2 = Boolean.parseBoolean("FALSE"); // false
```
##### 转换为char[]
String和char[]类型可以互相转换，方法是：
```
char[] cs = "Hello".toCharArray(); // String -> char[]
String s = new String(cs); // char[] -> String
```

#### StringBuilder

> 虽然可以直接拼接字符串，但是，在循环中，每次循环都会创建新的字符串对象，然后扔掉旧的字符串。这样，绝大部分字符串都是临时对象，不但浪费内存，还会影响GC效率。为了能高效拼接字符串，Java标准库提供了StringBuilder，它是一个可变对象，可以预分配缓冲区，这样，往StringBuilder中新增字符时，不会创建新的临时对象：
```
StringBuilder sb = new StringBuilder(1024);
for (int i = 0; i < 1000; i++) {
    sb.append(',');
    sb.append(i);
}
String s = sb.toString();
```
##### StringBuilder还可以进行链式操作：

#### StringJoiner

要高效拼接字符串，应该使用StringBuilder。
> 类似用分隔符拼接数组的需求很常见，所以Java标准库还提供了一个StringJoiner来干这个事：
https://www.bookstack.cn/read/liaoxuefeng-java/0d74ab96ed669840.md

>> 那么StringJoiner内部是如何拼接字符串的呢？如果查看源码，可以发现，StringJoiner内部实际上就是使用了StringBuilder，所以拼接效率和StringBuilder几乎是一模一样的。

##### String.join()
    String还提供了一个静态方法join()，这个方法在内部使用了StringJoiner来拼接字符串，在不需要指定“开头”和“结尾”的时候，用String.join()更方便：
```
String[] names = {"Bob", "Alice", "Grace"};
var s = String.join(", ", names);
```
#### 包装类型





