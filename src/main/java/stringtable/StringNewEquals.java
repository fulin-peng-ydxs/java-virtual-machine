package stringtable;

import org.junit.Test;

/**
 * @author PengFuLin
 * @version 1.0
 * @description: 字符串的创建的过程解析
 * @date 2021/7/31 13:21
 */
public class StringNewEquals {

    /**
     * 01 new String("ab")
     *
     * （1） 字节码：
     *  0 new #2 <java/lang/String> ==》在堆中创建一个String对象，并将其引用压入栈顶
     *  3 dup ==》将 String 对象的引用复制一份并压入栈顶
     *  4 ldc #3 <ab> ==》将 String常量池中的 “ab” 的引用 压入栈顶
     *  6 invokespecial #4 <java/lang/String.<init> : (Ljava/lang/String;)V>
     *      ==》弹出“ab”和String对象引用，调用String有参构造方法 public String(String str)
     *  9 astore_1 ==》弹出最开始的String对象引用，将引用压入局部变量表的“1”位置，即完成对 “demo”变量的赋值。
     * 10 return  ==》方法返回语句
     *
     * （2）总结：
     *  使用 new 关键字创建String对象只会创建一个对象：在堆中创建一个String对象
     *  在创建的过程中，会将保存在字符串常量池中的“ab”字符串的引用赋值给String类中value属性
     *  也就是说new 关键字创建的String对象会在字符串常量池中创建真正的字符串。
     */

    public void test01(){
        String demo = new String("ab");
    }

    /**
     * 02 String demo="ab"
     *
     * （1）字节码：
     * 0 ldc #3 <ab>
     * 2 astore_1
     * 3 return
     *
     *（2）总结：
     * 使用字面量的方式依然会在堆中创建一个String对象，这个将由JVM内部创建
     * 最终"demo"保存的将是JVM内部创建的String对象，再次声明相同的字符串时会直接将
     * 其已经创建的String对象的引用赋值给其声明的变量。
     */
    public void test02(){
        String demo="ab";
    }

    @Test
    public void test03(){
        String demo = new String("ab");
        String demo2 = new String("ab");
        System.out.println(demo==demo2);

        String demo3="ab";
        String demo4="ab";
        System.out.println(demo3==demo4);
        System.out.println(demo == demo3);

    }
    public void test04(){
        String demo = new String("ab");
        int length = demo.length();

        String demo1="ab";
        int length1 = demo1.length();
    }



}
