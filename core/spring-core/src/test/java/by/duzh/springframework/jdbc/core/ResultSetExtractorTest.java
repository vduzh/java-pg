package by.duzh.springframework.jdbc.core;

import org.junit.jupiter.api.Test;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowCallbackHandler;

import java.sql.ResultSet;
import java.sql.SQLException;

// https://coursehunter.net/course/rukovodstvo-po-sertifikacionnomu-ekzamenu-spring-professional-modul-03
// Q4
public class ResultSetExtractorTest {

    static class AverageSizeResultSetExtractor implements ResultSetExtractor<Float> {

        @Override
        public Float extractData(ResultSet rs) throws SQLException, DataAccessException {
            int sum = 0;
            int count = 0;

            while(rs.next()) {
                sum =+ rs.getInt("size");
                count++;
            }

            if (count == 0)
                return 0.0f;

            return sum/(float)count;
        }
    }

    @Test
    void name() {
        ResultSetExtractor extractor = new AverageSizeResultSetExtractor();
        throw new RuntimeException();
    }
}
