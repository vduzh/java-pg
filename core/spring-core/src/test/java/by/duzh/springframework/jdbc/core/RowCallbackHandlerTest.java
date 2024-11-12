package by.duzh.springframework.jdbc.core;

import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

// https://coursehunter.net/course/rukovodstvo-po-sertifikacionnomu-ekzamenu-spring-professional-modul-03
// Q4
public class RowCallbackHandlerTest {

    static class AverageSizeRowCallbackHandler implements RowCallbackHandler {
        private int sum;

        private int count;

        @Override
        public void processRow(ResultSet rs) throws SQLException {
            sum += rs.getInt("size");
            count++;
        }

        public float getValue() {
            if (count == 0)
                return 0.0f;

            return sum / (float)count;
        }
    }

    @Test
    void name() {
        RowCallbackHandler handler = new AverageSizeRowCallbackHandler();
        throw new RuntimeException();
    }
}
