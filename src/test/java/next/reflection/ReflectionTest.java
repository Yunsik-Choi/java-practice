package next.reflection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import next.optional.User;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    @DisplayName("테스트1: 리플렉션을 이용해서 클래스와 메소드의 정보를 정확하게 출력해야 한다.")
    public void showClass() {
        SoftAssertions s = new SoftAssertions();
        Class<Question> clazz = Question.class;
        logger.debug("Classs Name {}", clazz.getName());

        for (Constructor<?> constructor : clazz.getConstructors()) {
            logger.debug(constructor.getName());
        }

        for (Field declaredField : clazz.getDeclaredFields()) {
            logger.debug("class : {}, name : {}", declaredField.getDeclaringClass(), declaredField.getName());
        }

        for (Method method : clazz.getDeclaredMethods()) {
            logger.debug("name : {}", method.getName());
            for (Class<?> parameterType : method.getParameterTypes()) {
                logger.debug("parameterType : {}", parameterType.getName());
            }
            logger.debug("returnType : {}", method.getReturnType());
        }
    }

    @Test
    public void constructor() {
        Class<Question> clazz = Question.class;
        Constructor[] constructors = clazz.getConstructors();
        for (Constructor constructor : constructors) {
            Class[] parameterTypes = constructor.getParameterTypes();
            logger.debug("paramer length : {}", parameterTypes.length);
            for (Class paramType : parameterTypes) {
                logger.debug("param type : {}", paramType);
            }
        }
    }

    @Test
    public void privateFieldAccess() throws Exception {
        Class<Student> clazz = Student.class;
        logger.debug(clazz.getName());

        Student student = clazz.getDeclaredConstructor().newInstance();

        Field name = clazz.getDeclaredField("name");
        name.setAccessible(true);
        name.set(student, "재성");
        name.setAccessible(false);

        Field age = clazz.getDeclaredField("age");
        age.setAccessible(true);
        age.set(student, 25);
        age.setAccessible(false);

        assertAll(
                () -> assertThat(student.getName()).isEqualTo("재성"),
                () -> assertThat(student.getAge()).isEqualTo(25)
        );
    }

    @DisplayName("User 클래스의 인스턴스를 자바 Reflection API를 활용해 User 인스턴스를 생성한다.")
    @Test
    public void create() throws Exception {
        Class<User> clazz = User.class;

        User user = clazz.getDeclaredConstructor(String.class, Integer.class).newInstance("재성", 25);

        assertAll(
                () -> assertThat(user.getName()).isEqualTo("재성"),
                () -> assertThat(user.getAge()).isEqualTo(25)
        );
    }

    @DisplayName("메서드에 @ElapsedTime 애노테이션이 있을 경우 메소드를 실행하고 수행시간을 측정 및 출력하도록 수현한다.")
    @Test
    public void EstimateTime() throws Exception {
        Class<TimeRunner> clazz = TimeRunner.class;

        long start = System.currentTimeMillis();

        for (Method declaredMethod : clazz.getDeclaredMethods()) {
            if (declaredMethod.isAnnotationPresent(ElapsedTime.class)) {
                declaredMethod.invoke(clazz.getDeclaredConstructor().newInstance(), 1000);
            }
        }

        assertThat(System.currentTimeMillis() - start).isCloseTo(1000, Offset.offset(500L));
    }
}
