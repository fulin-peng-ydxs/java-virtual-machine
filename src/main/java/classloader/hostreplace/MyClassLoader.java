package classloader.hostreplace;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;

/**
 * @author PengFuLin
 * @version 1.0
 * @description: 自定义类加载器
 * @date 2021/8/20 11:19
 */
public class MyClassLoader extends ClassLoader {

    //加载的根路径
    private String rootDir;

    public MyClassLoader(String rootDir) {
        this.rootDir = rootDir;
    }

    //加载指定类
    protected Class<?> findClass(String className) throws ClassNotFoundException {
        Class clazz = this.findLoadedClass(className);
        FileChannel fileChannel = null;
        WritableByteChannel outChannel = null;
        if (null == clazz) {
            try {
                String classFile = getClassFile(className);
                FileInputStream fis = new FileInputStream(classFile);
                fileChannel = fis.getChannel();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                outChannel = Channels.newChannel(baos);
                ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
                while (true) {
                    int i = fileChannel.read(buffer);
                    if (i == 0 || i == -1) {
                        break;
                    }
                    buffer.flip();
                    outChannel.write(buffer);
                    buffer.clear();
                }

                byte[] bytes = baos.toByteArray();
                clazz = defineClass(className, bytes, 0, bytes.length);


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (fileChannel != null)
                        fileChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    if (outChannel != null)
                        outChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return clazz;
    }
    //加载的绝对路径
    private String getClassFile(String className) {
        return rootDir + "\\" + className.replace('.', '\\') + ".class";
    }


}
