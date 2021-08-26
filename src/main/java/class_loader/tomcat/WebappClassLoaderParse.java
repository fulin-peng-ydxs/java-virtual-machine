package class_loader.tomcat;

/**
 * @author PengFuLin
 * @version 1.0
 * @description: WebAppLoader加载机制解析
 * @date 2021/8/21 9:11
 */
public class WebappClassLoaderParse {
 /**
  public Class loadClass(String name, boolean resolve)
            throws ClassNotFoundException {
        Class clazz = null;
        // (0) 先从自己的缓存中查找，有则返回，无则继续
        clazz = findLoadedClass0(name);
        if (clazz != null) {
            if (resolve) resolveClass(clazz);
            return (clazz);
        }
        // (0.1) 再从parent的缓存中查找
        clazz = findLoadedClass(name);
        if (clazz != null) {
            if (resolve) resolveClass(clazz);
            return (clazz);
        }
        // (0.2) 缓存中没有，则首先使用"system类加载器"来加载
        clazz = system.loadClass(name);
        if (clazz != null) {
            if (resolve) resolveClass(clazz);
            return (clazz);
        }
        //判断是否需要先让parent代理
        boolean delegateLoad = delegate || filter(name);
        // (1) 先让parent加载，通常delegateLoad == false，即这一步不会执行

        if (delegateLoad) {
            ClassLoader loader = parent;
            if (loader == null)
                loader = system;
            clazz = loader.loadClass(name);
            if (clazz != null) {
                if (resolve) resolveClass(clazz);
                return (clazz);
            }
        }
        // (2) delegateLoad == false 或者 parent加载失败，调用自身的加载机制
        clazz = findClass(name);
        if (clazz != null) {
            if (resolve) resolveClass(clazz);
            return (clazz);
        }
        // (3) 自己加载失败，则请求parent加载

        if (!delegateLoad) {
            ClassLoader loader = parent;
            if (loader == null)
                loader = system;
            clazz = loader.loadClass(name);
            if (clazz != null) {
                return (clazz);
            }
        }
        throw new ClassNotFoundException(name);
    }
  总结一下：
  首先findLoadedClass0()和findLoadedClass()分别从本地和父类加载器的缓存中查找当前要加载的类是否已经加载过了。
  为了避免安全问题，Tomcat类加载器会将加载请求委派给"系统类加载器"。接下来根据delegate变量的设置，决定是先由自己加载，还是先由父类加载器去加载。
    */
}
