package by.duzh.springframework.web.bind.annotation;

import by.duzh.springframework.web.MockMvcUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class InitBinderTest {

    @Controller
    public static class CustomEditorFormController {

        @InitBinder
        public void initBinder(WebDataBinder binder) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dateFormat.setLenient(false);
            binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
        }

        @PostMapping("/add")
        public String add(Foo foo) {
            var c = Calendar.getInstance();
            c.setTime(foo.date);

            return "index-%d-%d-%d-%d".formatted(foo.id, c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, c.get(Calendar.DAY_OF_MONTH));
        }
    }

    @Test
    void addPostWitCustomEditor() throws Exception {
        MockMvcUtil.buildMockMvc(CustomEditorFormController.class)
                .perform(post("/add").param("id", "1").param("date", "2021-02-18"))
                .andExpect(status().isOk())
                .andExpect(view().name("index-1-2021-2-18"));
    }

    @Controller
    public static class CustomFormatterFormController {

        @InitBinder
        public void initBinder(WebDataBinder binder) {
            binder.addCustomFormatter(new DateFormatter("yyyy-MM-dd"));
        }

        @PostMapping("/add")
        public String add(Foo foo) {
            var c = Calendar.getInstance();
            c.setTime(foo.date);

            return "index-%d-%d-%d-%d".formatted(foo.id, c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, c.get(Calendar.DAY_OF_MONTH));
        }
    }

    @Test
    void addPostWitCustomFormatter() throws Exception {
        MockMvcUtil.buildMockMvc(CustomFormatterFormController.class)
                .perform(post("/add").param("id", "2").param("date", "2021-01-18"))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("index-2-2021-1-18"));
    }

    private static class Foo {
        int id;
        Date date;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        @Override
        public String toString() {
            return "Foo{" +
                    "id=" + id +
                    ", date=" + date +
                    '}';
        }
    }
}
