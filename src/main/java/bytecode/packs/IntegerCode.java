package bytecode.packs;

import org.junit.Test;

/**
 * @author PengFuLin
 * @version 1.0
 * @description: int/Integer字节码研究
 * @date 2021/8/14 10:54
 */
public class IntegerCode {



    /* i++和++i的区别:
     * i++和++i的结果是一样的，但是如果进行赋值，
     * 则i++赋的时运算之前的，而++i赋的运算之后的
     */

    /**
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


    /**
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



    /**
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

    /**
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
}
