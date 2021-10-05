package bytecode.packs;

import org.junit.Test;

/**
 * @author PengFuLin
 * @version 1.0
 * @description: int/Integer字节码研究
 * @date 2021/8/14 10:54
 */
public class BooleanCacheCode {


    /* 包装类对象的数据缓存：
     * Boolean 对象的字面量赋值也使用的是valueOf方法进行对象的创建，
     * Boolean内部有两个常量，一个是false的Boolean对象，一个是ture的Boolean对象，
     * 在此方法中，会根据true和false值判断，如果要创建true对象，则直接返回创建好的true常量对象，
     * 如果要创建false对象，则直接返回创建好的false常量对象。
     */

    /**
     *  0 iconst_1
     *  1 invokestatic #8 <java/lang/Boolean.valueOf : (Z)Ljava/lang/Boolean;>
     *  4 astore_1
     *
     *  5 iconst_1
     *  6 invokestatic #8 <java/lang/Boolean.valueOf : (Z)Ljava/lang/Boolean;>
     *  9 astore_2
     *
     * 10 getstatic #2 <java/lang/System.out : Ljava/io/PrintStream;>
     * 13 aload_1
     * 14 aload_2
     * 15 if_acmpne 22 (+7)
     * 18 iconst_1
     * 19 goto 23 (+4)
     * 22 iconst_0
     * 23 invokevirtual #5 <java/io/PrintStream.println : (Z)V>
     * 26 return
     */

    @Test
    public void test06(){
        Boolean b1 = true;
        Boolean b2 = true;
        System.out.println(b1 == b2); //true
    }
}
