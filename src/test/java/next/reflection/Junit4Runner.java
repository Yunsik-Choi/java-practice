package next.reflection;

import java.lang.reflect.Method;
import org.junit.jupiter.api.Test;

public class Junit4Runner {
    @Test
    public void run() throws Exception {
        Class clazz = Junit4Test.class;

        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(MyTest.class)) {
                method.invoke(clazz.getDeclaredConstructor().newInstance());
            }
        }
    }
}


