package com.hbhs.common.domain.model.paging;


import lombok.*;

@Getter@Setter@ToString
@NoArgsConstructor@AllArgsConstructor
public class SortingCondition {
    private String fieldName;
    private SortingCondition.SortingMethod sortingMethod;

    public static enum SortingMethod {
        ASC,
        DESC;

        private SortingMethod() {
        }

        public static SortingCondition.SortingMethod getSortingMethod(String method) {
            for (SortingMethod value : values()) {
                if (value.name().equalsIgnoreCase(method)){
                    return value;
                }
            }
            return ASC;
        }
    }
}
