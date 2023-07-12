package next.reflection;

import java.lang.reflect.Method;
import org.junit.jupiter.api.Test;

public class Junit3Runner {
    @Test
    public void runner() throws Exception {
        Class clazz = Junit3Test.class;

        Method[] declaredMethods = clazz.getDeclaredMethods();

        for (Method declaredMethod : clazz.getDeclaredMethods()) {
            declaredMethod.invoke(clazz.getDeclaredConstructor().newInstance());
        }
    }
}
