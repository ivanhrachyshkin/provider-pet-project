package by.hrachyshkin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Criteria {

    @Data
    public static class Sorting {

        public enum Direction {
            ASC,
            DESC
        }

        String column;
        Direction direction;
    }

    @Data
    public static class Filter {

        String column;
        String pattern;
    }

    Sorting sorting;
    Filter filter;
}