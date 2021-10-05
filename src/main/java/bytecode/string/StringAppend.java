package bytecode.string;

import org.junit.Test;

/**
 * @author PengFuLin
 * @version 1.0
 * @description: 字符串的拼接操作解析
 * @date 2021/7/31 22:53
 */
public class StringAppend {


    /**
     * 01 常量与常量之间
     * （1）字节码：
     * 0 ldc #2 <abc>
     *     ==》在前端编译器编译时进行了编译优化，只会在常量池中声明
     *     “abc”，而不会有“a”、“b”、“c”
     * 2 astore_1
     * 3 ldc #2 <abc>
     * 5 astore_2
     * 6 return
     */
    public void test01(){
        String s1 = "a" + "b" + "c";
        String s2 = "abc";
    }

    @Test
    public void test02(){
        String s1 = "a" + "b" + "c";
        String s2 = "abc";
        System.out.println(s1 == s2); //true
        System.out.println(s1.equals(s2)); //true
    }


    /**
     * 02 常量与变量之间
     *
     * (1) 字节码：
     *  ==》"javaEE" 对象赋值var1
     *  0 ldc #6 <javaEE>
     *  2 astore_1
     *
     *  3 new #7 <java/lang/StringBuilder>
     *      ==》创建StringBuilder对象，并将对象引用压入栈中。
     *
     *  6 dup  ==》将创建StringBuilder对象的引用复制一份并压入栈顶
     *  7 invokespecial #8 <java/lang/StringBuilder.<init> : ()V>
     *      ==》弹出复制的StringBuilder对象引用，调用StringBuilder对象的无参构造方法。
     * 10 aload_1 ==》将 “javaEE”对象引用压入栈中
     * 11 invokevirtual #9 <java/lang/StringBuilder.append : (Ljava/lang/String;)Ljava/lang/StringBuilder;>
     *      ==》弹出“javaEE”对象引用和StringBuilder对象引用，调用StringBuilder对象的append方法，返回StringBuilder对象，并压入栈中
     *
     * ==》 “home” 对象压入栈中
     * 14 ldc #10 <home>
     * 16 invokevirtual #9 <java/lang/StringBuilder.append : (Ljava/lang/String;)Ljava/lang/StringBuilder;>
     *     ==》 弹出home对象和StringBuilder对象引用，再次调用StringBuilder对象的append方法，返回StringBuilder对象，并压入栈中
     * 19 invokevirtual #11 <java/lang/StringBuilder.toString : ()Ljava/lang/String;>
     *     ==》弹出StringBuilder对象引用，调用StringBuilder的toString方法，返回String对象，并压入栈中
     * 22 astore_2 ==》弹出String对象引用，赋值给append1。
     * 23 return
     *
     * （2）总结:
     * 非"常量"拼接，如果是相同字面量方式拼接，则只会创建一个String对象，否则就得必须创建两个或以上，
     * 创建一个StringBuilder对象进行StringBuilder的拼接操作,最后再创建一个String对象作为拼接结果返回。
     *
     * 补充：在jdk5.0之后使用的是StringBuilder,在jdk5.0之前使用的是StringBuffer
     */
    public void test03(){
        String var1 = "javaEE";
        String append1=var1+"home";
    }

    /**
     * 03 变量与变量间
     * ==》和变量与常量之间没有区别
     *  0 ldc #6 <javaEE>
     *  2 astore_1
     *  3 ldc #12 <hadoop>
     *  5 astore_2
     *  6 new #7 <java/lang/StringBuilder>
     *  9 dup.......
     */
    public void test05(){
        String var1 = "javaEE";
        String var2 = "hadoop";
        String append1=var2+var1;
    }

    @Test
    public void test04(){
        String var1 = "java";
        String var2 = "home";
        String appendResult = var1 + var2;
        String javaHome = "javahome";
        System.out.println(appendResult);
        System.out.println(javaHome);
        System.out.println(appendResult==javaHome);
    }

    @Test
    public void test06(){
        String var1 = new String("java");
        String var2 = new String("home");
        String appendResult = var1 + var2;
        String javaHome = "javahome";
        System.out.println(appendResult);
        System.out.println(javaHome);
        System.out.println(appendResult==javaHome);
    }

    /**
     * StringBuilder的append方法和String “+”方式的拼接分析：
     *
     * 01 StringBuilder的append方法拼接过程只会
     * 创建一个StringBuilder和一个toString方法生成的String结果对象
     *
     * 02 String "+" 实现一次拼接操作（完成赋值）就需要创建一个
     * StringBuilder对象和至少一个String中间拼接对象以及一个String结果对象。
     *
     * ==》所以，如果进行循环拼接操作的话，append方法的执行性能和内存占用大小远远优于
     * String “+” 拼接方式。
     */

    @Test
    public void test07(){

        this.method1(100000); //3760 ms

        this.method2(100000); //7ms
    }

    public void method1(int highLevel){
        long start = System.currentTimeMillis();
        //每次循环都会创建一个StringBuilder、String
        String src =null;
        for(int i = 0;i < highLevel;i++){
            src = src + "a";
        }
        long end = System.currentTimeMillis();
        long time = end - start;
        System.out.println("String “+” ==》"+time);

    }

    public void method2(int highLevel){
        long start = System.currentTimeMillis();
        //只需要创建一个StringBuilder和一个String对象
        StringBuilder src = new StringBuilder();
        for (int i = 0; i < highLevel; i++) {
            src.append("a");
        }
        String result = src.toString();
        long  end = System.currentTimeMillis();
        long time = end - start;
        System.out.println("StringBuilder “append” ==》"+time);
    }




}
