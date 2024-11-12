package by.duzh.springframework.expression;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.print.DocFlavor;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringJUnitConfig(ExpressionParserTest.AppConfig.class)
public class ExpressionParserTest {

    @Configuration
    @PropertySource(value = "classpath:by/duzh/springframework/expression/data.properties")
    static class AppConfig {
        @Bean
        public String foo() {return "foo";}

        @Bean
        public User currentUser()  {
            return new User("user1", "password1");
        }

    }

    @Autowired
    ApplicationContext ctx;

    @Test
    void parseExpression() throws Exception {
        // init parser
        ExpressionParser parser = new SpelExpressionParser();

        // evaluate the literal string
        Expression exp = parser.parseExpression("'foo'");
        assertEquals("foo", exp.getValue(String.class));

        // calls function
        assertEquals("FOO", parser.parseExpression("'foo'.toUpperCase()").getValue(String.class));

        // get the property
        assertEquals(3, parser.parseExpression("'foo'.length").getValue(Integer.class).intValue());

        // call a constructor
        assertEquals("foo", parser.parseExpression("new String('foO').toLowerCase()")
                .getValue(String.class));
    }

    static class User {
        public String login;
        public String password;

        public static final String DEFAULT_ROLE = "guest";
        public static String getDefaultRole()  {return "guest";}

        public User(String login, String password) {
            this.login = login;
            this.password = password;
        }

        public String getLogin() {
            return login;
        }
    }

    @Test
    public void evaluateAgainstObject() throws Exception {
        ExpressionParser parser = new SpelExpressionParser();

        User admin = new User("admin", "admin");
        Expression expression = parser.parseExpression("login");
        String login = expression.getValue(admin, String.class);
        assertEquals("admin", login);
    }

    @Test
    public void evaluationContext() throws Exception {
        StandardEvaluationContext evaluationContext = new StandardEvaluationContext();
        evaluationContext.setBeanResolver(new BeanFactoryResolver(ctx));

        ExpressionParser parser = new SpelExpressionParser();

        // read property from the spring bean foo
        assertEquals("foo", parser.parseExpression("@foo").getValue(evaluationContext));

        // static field
        assertEquals("guest", parser.parseExpression(
                "T(by.duzh.springframework.expression.ExpressionParserTest$User).DEFAULT_ROLE")
                .getValue(evaluationContext));
        // static method
        assertEquals("guest", parser.parseExpression(
                "T(by.duzh.springframework.expression.ExpressionParserTest$User).getDefaultRole()")
                .getValue(evaluationContext));

        // Spring bean property
        assertEquals("user1", parser.parseExpression("@currentUser.login").getValue(evaluationContext));
        // Spring bean method
        assertEquals("user1", parser.parseExpression("@currentUser.getLogin()").getValue(evaluationContext));

        // SpEL Variables
        evaluationContext.setVariable("powerUser", new User("admin", "password"));
        // Object property on reference assigned to SpEL variable
        assertEquals("admin", parser.parseExpression("#powerUser.login").getValue(evaluationContext));
        // Object method on reference assigned to SpEL variable
        assertEquals("admin", parser.parseExpression("#powerUser.getLogin()").getValue(evaluationContext));

        // Spring app Env property
        //assertEquals("admin", parser.parseExpression("environment['JAVA_HOME']").getValue(evaluationContext));
        // System property
        assertEquals("admin", parser.parseExpression("systemProperties['JAVA_HOME']").getValue(evaluationContext));
        // System env properties
        assertEquals("admin", parser.parseExpression("systemEnvironment['JAVA_HOME']").getValue(evaluationContext));
    }

}
