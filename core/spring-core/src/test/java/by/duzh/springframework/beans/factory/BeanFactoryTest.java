package by.duzh.springframework.beans.factory;

import by.duzh.springframework.beans.factory.beans.Foo;
import by.duzh.springframework.beans.factory.beans.InjectionsSample;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.ResolvableType;
import org.springframework.core.io.ClassPathResource;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BeanFactoryTest {
    private BeanFactory beanFactory;

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }

    @BeforeAll
    public void setUp() {
        // Create a default implementation of the BeanFactory
        DefaultListableBeanFactory listableBeanFactory = new DefaultListableBeanFactory();

        // Read a definition from XML file
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(listableBeanFactory);
        reader.loadBeanDefinitions(new ClassPathResource("context/app-context-basic.xml"));

        beanFactory = listableBeanFactory;
    }

    @Test
    void getBeanProvider() throws Exception {
        ObjectProvider<Foo> beanProvider = getBeanFactory().getBeanProvider(Foo.class);
        // TODO: assert!!!
        //beanProvider.
    }

    @Test
    void containsBean() throws Exception {
        Assertions.assertTrue(getBeanFactory().containsBean("foo"));
        Assertions.assertTrue(getBeanFactory().containsBean("foo2"));
        Assertions.assertTrue(getBeanFactory().containsBean("foo3"));
        Assertions.assertFalse(getBeanFactory().containsBean("xxx"));
    }

    @Test
    void getBean() throws Exception {
        getBeanFactory().getBean("foo"); // By name
        getBeanFactory().getBean("foo2"); // By alias
        getBeanFactory().getBean("foo4"); // By alias
        getBeanFactory().getBean("foo", Foo.class); // By id and class
        getBeanFactory().getBean(Foo.class); // By Class

        // TODO:!!!!
        //Foo foo = (Foo) factory.getBean("foo", "test");
        //assertEquals("test", foo.getName());

        // Bean Not Found
        assertThrows(NoSuchBeanDefinitionException.class, () -> getBeanFactory().getBean("none"));
    }

    @Test
    void getAliases() throws Exception {
        // Have an alias
        assertArrayEquals(new String[]{"foo2", "foo3", "foo4"}, getBeanFactory().getAliases("foo"));
        assertArrayEquals(new String[]{"foo", "foo3", "foo4"}, getBeanFactory().getAliases("foo2"));

        // No aliases
        assertEquals(0, getBeanFactory().getAliases("bar").length);
    }

    @Test
    public void isPrototype() throws Exception {
        assertFalse(getBeanFactory().isPrototype("foo"));
        assertTrue(getBeanFactory().isPrototype("bar"));
    }

    @Test
    public void isSingleton() throws Exception {
        assertFalse(getBeanFactory().isSingleton("bar"));
        assertTrue(getBeanFactory().isSingleton("foo"));
    }

    @Test
    void getType() throws Exception {
        Class<?> klass = getBeanFactory().getType("foo");
        assertTrue(Foo.class.isAssignableFrom(klass));

        // TODO: allowFactoryBeanInit not clear
        klass = getBeanFactory().getType("foo", false);
        assertTrue(Foo.class.isAssignableFrom(klass));
    }

    @Test
    void isTypeMatch() throws Exception {
        assertTrue(getBeanFactory().isTypeMatch("foo", Foo.class));
        assertFalse(getBeanFactory().isTypeMatch("bar", Foo.class));

        assertTrue(getBeanFactory().isTypeMatch("foo", ResolvableType.forType(Foo.class)));
        assertFalse(getBeanFactory().isTypeMatch("bar", ResolvableType.forType(Foo.class)));
    }

    @Test
    public void injectViaConstructorAndProperty() throws Exception { // inject a bean
        InjectionsSample sample = getBeanFactory().getBean("injectionsSample", InjectionsSample.class);

        assertNotNull(sample.getConstructorValue());
        assertEquals(123, sample.getConstructorIntValue());
        assertNotNull(sample.getPropertyValue());
        assertEquals("test", sample.getPropertyStringValue());
        assertEquals("Test", sample.getSpelString());

        // inject via a bean method
        assertNotEquals(sample.getBar(), sample.getBar()); //TODO: doesn't work for the Java Config

        // replace method
        assertEquals("replaced", sample.getReplacedValue());
    }
}
