package classloader.consts;

/**
 * @author PengFuLin
 * @version 1.0
 * @description: 探究常量赋值
 * @date 2021/8/29 17:38
 */
public class FinalValue {


    //非静态常量：

/* 第一种：
直接在声明时赋值
    public final  int finalValue=0;
*/


/* 第二种:
可以在构造方法里面进行赋值，
注意，这只适用于非静态常量，因为静态相关数据需要在类加载到内存后
就要为其开辟存储空间，即存储相关数据，所以需要在加载之前就得确定具体数值。

    public final  int finalValue;

    public FinalValue() {
        finalValue = 0;
    }
*/


    // 静态常量：

    /*只能够在声明时进行赋值。 */
    public final static int staticFinalValue = 0;


}
