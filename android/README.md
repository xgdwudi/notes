<center style="center;font-size:36px">android</center>

# 1. 创建android程序

![image-20220724191238253](https://raw.githubusercontent.com/xgdwudi/images/master/git-img/image-20220724191238253.png)

![image-20220724202041507](https://raw.githubusercontent.com/xgdwudi/images/master/git-img/image-20220724202041507.png)

![image-20220724202111394](https://raw.githubusercontent.com/xgdwudi/images/master/git-img/image-20220724202111394.png)

> view类常用的属性
>
> android:id 属性，唯一标识
>
> ```shell
> android:id="@+id/user"
> ```
>
> android:background属性 设置背景
>
> ```shell
> android:background="@mipmap/bg" 设置背景图片
> android:background="#FF6600"
> ```
>
> android:padding属性  设置内边距
>
> ```shell
> android:padding="16dp"  
> android:padding="@dimen/activity_margin"  先定义一个属性
> 也可单独设置
> android:paddingLeft
> android:paddingTop
> android:paddingRight
> android:paddingBotton
> android api17以后
> android:paddingStart    android:paddingEnd
> ```
>
> 

>ViewGroup  骨架
>
>继承之View
>
>经常使用的是ViewGroup控制其子组件分布时依赖的内部类
>
>1. ViewGroup.LayoutParams类
>   1. android:layout_height
>   2. android:layout_width
>      1. 共有三个常量  FILL_PARENT 设置的组件的宽度父容器是一样的 
>      2. MATCH_PARENT   设置的组件的宽度父容器是一样的  sdk版本>8
>      3. WRAP_CONTENT  包裹其自身的内容
>2. ViewGroup.MarginLayoutParams  控制外边距
>   1. android:layout_marginTop 顶外边距
>   2. android:layout_marginLeft 左外边距
>   3. android:layout_marginStart 左外边距
>   4. android:layout_marginRight 右外边距
>   5. android:layout_marginEnd 右外边距
>   6. android:layout_marginBottom 下外边距

# 2. 控制ui界面的方法

## 1.使用xml布局文件控制Ui界面

1. 在Android应用的res/layout目录下编写xml布局文件![image-20220725214216541](https://raw.githubusercontent.com/xgdwudi/images/master/git-img/image-20220725214216541.png)

2. 在Activity中使用一下的代码显示XML文件中布局的内容

   ```java
       setContentView(R.layout.activity_main);
   ```

   

## 2.在java代码中控制UI界面

![image-20220725215409439](https://raw.githubusercontent.com/xgdwudi/images/master/git-img/image-20220725215409439.png)

## 3.使用XML和java代码混合控制UI界面

![image-20220725221508034](https://raw.githubusercontent.com/xgdwudi/images/master/git-img/image-20220725221508034.png)

## 4.开发自定义的VIEW

![image-20220725223428055](https://raw.githubusercontent.com/xgdwudi/images/master/git-img/image-20220725223428055.png)

## 5.什么是布局管理器

用于控制组件如何摆放的

常用的布局管理器

- RelativeLayout  相对布局管理器
- LinearLayout
- FrameLayout
- TableLayout
- AbsoluteLayout 绝对位置管理器（不会使用）
- GridLayout 网格布局管理器