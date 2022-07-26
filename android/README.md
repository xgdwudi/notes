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

### 1.相对布局管理器（RelativeLayout）

以某个组件作为参考点，其他组件以此为参考点进行布局

![image-20220726213129800](https://raw.githubusercontent.com/xgdwudi/images/master/git-img/image-20220726213129800.png)

```
android:gravity    设置各子组件的摆放方式  
android:ignoreGravity   用于指定那个组件不受前面的印象
```

```xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity"
    android:gravity="center"
    android:ignoreGravity="@id/myid">

    <TextView
        android:id="@+id/myid"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Hello World!"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

</RelativeLayout>
```

RelativeLayout.LayoutParams  提供了一些属性 ，指定组件相对于参考组件的位置

```shell
android:layout_above  前
android:layout_below  后
android:layout_toLeftOf  左
android:layout_toRightOf 右

属性值为布尔类型
android:layout_alignParentBottom  底对齐
android:layout_alignParentLeft  左对齐
android:layout_alignParentRight  右对齐
android:layout_alignParentTop  上对齐

设置组件与哪一个组件的上下左右边界对齐
android:layout_alignBottom
android:layout_alignLeft
android:layout_alignRigth
android:layout_alignTop

设置组件位于布局管理器的那个位置的
android:layout_centerHorizontal  水平居中
android:layout_centerParent 中间位置
android:layout_centerVertical 垂直居中
```

### 2. 线性布局管理器（LinearLayout）

将放入其中的组件以水平或垂直排列下去。

![image-20220726221247679](https://raw.githubusercontent.com/xgdwudi/images/master/git-img/image-20220726221247679.png)

垂直线性布局管理器（每一行只能放置一个组件）

设置组件  android:orientation="vertical"

水平线性布局管理器（每一列只能放置一个组件）

 android:orientation="horizontal"

排到窗体之外时，其他组件也不能被显示



如何定义线性布局管理器

<LinearLayout>标记 

设置组件位置

android:orientation android:gravity

子组件属性

 android:layout_weight 属性  设置组件所占的权重

​	

![image-20220726222752289](https://raw.githubusercontent.com/xgdwudi/images/master/git-img/image-20220726222752289.png)

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="horizontal"
    tools:context=".MainActivity">
<!--    水平-->
    <Button
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_weight="1"
        android:width="100dp"
        android:text="按钮" />
    <Button
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_weight="1"
        android:width="180dp"
        android:text="按钮" />

</LinearLayout>
```

### 3.帧布局管理器（FrameLayout）

![image-20220726224330488](https://raw.githubusercontent.com/xgdwudi/images/master/git-img/image-20220726224330488.png)

用来显示层叠的内容，显示

如何定义帧布局管理器

为当前的帧布局管理器设置一个前景图像(始终位于最上层的图像)

android:foregroup

设置前景图像的位置

android:foregroundGravity

### 4.表格布局管理器

