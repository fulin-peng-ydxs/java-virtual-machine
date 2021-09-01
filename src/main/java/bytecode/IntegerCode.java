package bytecode;

import org.junit.Test;

/**
 * @author PengFuLin
 * @version 1.0
 * @description: int/Integer字节码研究
 * @date 2021/8/14 10:54
 */
public class IntegerCode {

    //1.i++和++i的区别

    /**总结：
      i++和++i的结果是一样的，但是如果进行赋值，
      则i++赋的时运算之前的，而++i赋的运算之后的*/


    /*
       0 bipush 10
       2 istore_1
       (-- - -- -- - -iload_1)
       3 iinc 1 by 1
       6 getstatic #2 <java/lang/System.out : Ljava/io/PrintStream;>
       9 iload_1
      10 invokevirtual #3 <java/io/PrintStream.println : (I)V>
      13 return
     */
    @Test
    public void test1(){
        int i = 10;
        ++i;
        System.out.println(i); //11
    }


    /*
       0 bipush 10  10 常数10 压入栈
       2 istore_1 常数10 出栈，放入局部变量表1位置
       (-- - -- -- - -iload_1)
       3 iinc 1 by 1  注意：将局部变量表为1的位置的变量自增1 ==11
       6 getstatic #2 <java/lang/System.out : Ljava/io/PrintStream;>
       9 iload_1 常数11 压入操作数栈顶
      10 invokevirtual #3 <java/io/PrintStream.println : (I)V>
         ==》弹出栈顶元素常数11 完成println方法的调用
      13 return
     */
    @Test
    public void test2(){
        int i = 10;
        i++;
        System.out.println(i);
    }



    /*
      0 iconst_2
      1 istore_1
      2 iload_1
      3 iload_1
      4 iinc 1 by 1
      7 imul
       ==》弹出操作数栈压入的两个常数2，对其进行相乘
       ==》并将结果压入操作数栈中。
      8 istore_1 弹出栈顶元素 4，放入局部变量表1的位置
      9 getstatic #2 <java/lang/System.out : Ljava/io/PrintStream;>
     12 iload_1 整数4压栈
     13 invokevirtual #3 <java/io/PrintStream.println : (I)V>
     16 return
    */
    @Test
    public void test3(){
        int i = 2;
        i *= i++; //i = i * i++
        System.out.println(i); //4
    }

    /*
       0 bipush 10 常数10入栈
       2 istore_1  栈顶常数10弹出，放入局部变量表1位置
       3 iload_1   常数10压入栈顶
       4 iload_1   常数10再次压入栈顶
       5 iinc 1 by 1  局部变量表1位置的整数10自增，变为11
       8 iadd 两个常数10出栈，进行相加为20，压入栈顶
       9 iinc 1 by 1 局部变量表1位置的整数10自增，变为12
      12 iload_1 整数12压入栈顶
      13 iadd  整数12和整数20出栈，进行相加为32，并压入栈顶
      14 istore_1 整数32出栈，放入局部变量表1位置
      15 getstatic #2 <java/lang/System.out : Ljava/io/PrintStream;>
      18 iload_1 .....
      19 invokevirtual #3 <java/io/PrintStream.println : (I)V>
      22 return
     */
    @Test
    public void test4(){
        int k = 10;
        k = k + (k++) + (++k);
        System.out.println(k);//32
    }



    //2.包装类对象的数据缓存

    @Test
    public void test5(){
        /*
 生成i1对象
 0 bipush 10 常数10 压栈
 2 invokestatic #4 <java/lang/Integer.valueOf : (I)Ljava/lang/Integer;>
  ==》调用valueOf方法，将10传入创建10的包装类对象，将对象引用压入栈顶
 5 astore_1 对象引用放入局部变量1中
 生成i2对象
 6 bipush 10
 8 invokestatic #4 <java/lang/Integer.valueOf : (I)Ljava/lang/Integer;>
11 astore_2

12 getstatic #2 <java/lang/System.out : Ljava/io/PrintStream;>
15 aload_1
16 aload_2
17 if_acmpne 24 (+7)  判断是否相等
20 iconst_1 true值
21 goto 25 (+4)
24 iconst_0 false值
25 invokevirtual #5 <java/io/PrintStream.println : (Z)V>
28 return

总结：使用直面量的方式对Integer赋值，则jvm内部会使用Integer的valueOf方法进行对象额创建，Integer内部有一个Integer缓存，
存储了关于-128~127Integer对象，如果要创建的的Integer对象的值在此区间内，就直接使用已创建缓存对象，如果不在此区间内则就重新创建一个新对象。
*/
        Integer i1 = 10;
        Integer i2 = 10;
        System.out.println(i1 == i2);//true

        /*

直接new了两个对象，所以是用new方式
创建的对象，对象引用值是可能相等的，
0 new #4 <java/lang/Integer>
 3 dup
 4 bipush 10
 6 invokespecial #5 <java/lang/Integer.<init> : (I)V>
 9 astore_1

10 new #4 <java/lang/Integer>
13 dup
14 bipush 10
16 invokespecial #5 <java/lang/Integer.<init> : (I)V>
19 astore_2

20 getstatic #2 <java/lang/System.out : Ljava/io/PrintStream;>
23 aload_1
24 aload_2
25 if_acmpne 32 (+7)
28 iconst_1
29 goto 33 (+4)
32 iconst_0
33 invokevirtual #6 <java/io/PrintStream.println : (Z)V>
36 return
         */
        Integer integer = new Integer(10);
        Integer integer1 = new Integer(10);
        System.out.println(integer == integer1); //false


        Integer i3 = 128;
        Integer i4 = 128;
        System.out.println(i3 == i4);//false
    }

    /*

 0 iconst_1
 1 invokestatic #8 <java/lang/Boolean.valueOf : (Z)Ljava/lang/Boolean;>
 4 astore_1

 5 iconst_1
 6 invokestatic #8 <java/lang/Boolean.valueOf : (Z)Ljava/lang/Boolean;>
 9 astore_2

10 getstatic #2 <java/lang/System.out : Ljava/io/PrintStream;>
13 aload_1
14 aload_2
15 if_acmpne 22 (+7)
18 iconst_1
19 goto 23 (+4)
22 iconst_0
23 invokevirtual #5 <java/io/PrintStream.println : (Z)V>
26 return

总结：Boolean 对象的字面量赋值也使用的是valueOf方法进行对象的创建，
Boolean内部有两个常量，一个是false的Boolean对象，一个是ture的Boolean对象，
在此方法中，会根据true和false值判断，如果要创建true对象，则直接返回创建好的true常量对象
，如果要创建false对象，则直接返回创建好的false常量对象。
*/

    @Test
    public void test06(){
        Boolean b1 = true;
        Boolean b2 = true;
        System.out.println(b1 == b2);//true
    }
}
