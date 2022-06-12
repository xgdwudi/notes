# StringTable

## String 的基本特性

![image-20211231172355944](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20211231172355944.png)

![image-20211231180237519](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20211231180237519.png)

![image-20211231180347501](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20211231180347501.png)

![image-20211231184055037](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20211231184055037.png)



## String的内存分配

![image-20211231184124270](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20211231184124270.png)

![image-20211231184405520](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20211231184405520.png)

![image-20211231184512921](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20211231184512921.png)

![image-20211231184525152](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20211231184525152.png)

![image-20211231200633978](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20211231200633978.png)

![image-20211231200649823](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20211231200649823.png)

![image-20220101111758919](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220101111758919.png)

## 字符串拼接操作

![image-20220101112518205](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220101112518205.png)

![image-20220101115339912](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220101115339912.png)

![image-20220101115924410](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220101115924410.png)

## intern()的使用

![image-20220101122005729](C:%5CUsers%5C86155%5CAppData%5CRoaming%5CTypora%5Ctypora-user-images%5Cimage-20220101122005729.png)





![image-20220101122127795](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220101122127795.png)

![image-20220101122623540](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220101122623540.png)

![image-20220101123949969](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220101123949969.png)

![image-20220101124110969](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220101124110969.png)

![image-20220101124022856](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220101124034318.png)

![image-20220101124852707](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220101124852707.png)

 ![image-20220101125634006](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220101125634006.png)

![image-20220101125652213](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220101125652213.png)

![image-20220101144259785](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220101144259785.png)

![image-20220101170434778](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220101170434778.png)

## StringTable 的垃圾回收

![image-20220101170730238](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220101170730238.png)

## G1中的String去重操作

[官方说明](http://openjdk.java.net/jeps/192)

![ ](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220101171315759.png)

String str1 = new String("hello");

String str2 = new String("hello");

此处的去重是对堆中对象的去重，常量池中的字符串肯定是唯一的，

![image-20220101172210659](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220101172210659.png)

![image-20220101172628171](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220101172628171.png)

![image-20220101173118566](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220101173118566.png)