package bytecode.packs;

import org.junit.Test;

/**
 * @author PengFuLin
 * @version 1.0
 * @description: int/Integer字节码研究
 * @date 2021/8/14 10:54
 */
public class IntegerCacheCode {


    /* 包装类对象的数据缓存：
     * 使用直面量的方式对Integer赋值，则jvm内部会使用Integer的valueOf方法进行对象额创建，Integer内部有一个Integer缓存，
     * 存储了关于-128~127Integer对象，如果要创建的的Integer对象的值在此区间内，就直接使用已创建缓存对象，如果不在此区间内则就重新创建一个新对象
     */

    @Test
    public void test5(){
        /**
         *  生成i1对象
         *  0 bipush 10 常数10 压栈
         *  2 invokestatic #4 <java/lang/Integer.valueOf : (I)Ljava/lang/Integer;>
         *   ==》调用valueOf方法，将10传入创建10的包装类对象，将对象引用压入栈顶
         *  5 astore_1 对象引用放入局部变量1中
         *  生成i2对象
         *  6 bipush 10
         *  8 invokestatic #4 <java/lang/Integer.valueOf : (I)Ljava/lang/Integer;>
         * 11 astore_2
         *
         * 12 getstatic #2 <java/lang/System.out : Ljava/io/PrintStream;>
         * 15 aload_1
         * 16 aload_2
         * 17 if_acmpne 24 (+7)  判断是否相等
         * 20 iconst_1 true值
         * 21 goto 25 (+4)
         * 24 iconst_0 false值
         * 25 invokevirtual #5 <java/io/PrintStream.println : (Z)V>
         * 28 return
         */


        Integer i1 = 10;
        Integer i2 = 10;
        System.out.println(i1 == i2);//true

        /**
         * 直接new了两个对象，所以是用new方式
         * 创建的对象，对象引用值是可能相等的，
         * 0 new #4 <java/lang/Integer>
         *  3 dup
         *  4 bipush 10
         *  6 invokespecial #5 <java/lang/Integer.<init> : (I)V>
         *  9 astore_1
         *
         * 10 new #4 <java/lang/Integer>
         * 13 dup
         * 14 bipush 10
         * 16 invokespecial #5 <java/lang/Integer.<init> : (I)V>
         * 19 astore_2
         *
         * 20 getstatic #2 <java/lang/System.out : Ljava/io/PrintStream;>
         * 23 aload_1
         * 24 aload_2
         * 25 if_acmpne 32 (+7)
         * 28 iconst_1
         * 29 goto 33 (+4)
         * 32 iconst_0
         * 33 invokevirtual #6 <java/io/PrintStream.println : (Z)V>
         * 36 return
         */
        Integer integer = new Integer(10);
        Integer integer1 = new Integer(10);
        System.out.println(integer == integer1); //false
        Integer i3 = 128;
        Integer i4 = 128;
        System.out.println(i3 == i4);//false
    }
}
