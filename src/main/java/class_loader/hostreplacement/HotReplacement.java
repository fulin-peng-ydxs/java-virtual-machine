package class_loader.hostreplacement;

import java.lang.reflect.Method;

/**
 * @author PengFuLin
 * @version 1.0
 * @description: 热替换研究&代码实现
 * @date 2021/8/20 0:04
 */
public class HotReplacement {


    public static void main(String args[]) {
        while (true) {
            try {
                //1. 创建自定义类加载器的实例
                MyClassLoader loader = new MyClassLoader("D:\\Git\\JavaVirtualMachine\\target\\classes");
                //2. 加载指定的类
                Class clazz = loader.findClass("classloader.host_replacement.HostReplacementClassDemo");
                //3. 创建运行时类的实例
                Object demo = clazz.newInstance();
                //4. 获取运行时类中指定的方法
                Method m = clazz.getMethod("hot");
                //5. 调用指定的方法
                m.invoke(demo);
                Thread.sleep(5000);
            } catch (Exception e) {
                System.out.println("not find");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }

            }
        }
    }








}
