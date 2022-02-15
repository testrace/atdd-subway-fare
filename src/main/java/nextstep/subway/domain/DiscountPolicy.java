package nextstep.subway.domain;

import java.util.Arrays;
import java.util.function.Predicate;

public enum DiscountPolicy {
    CHILD(age -> age >= 6 && age < 13, 0.5),
    YOUTH(age -> age >= 13 && age < 20, 0.8),
    ADULT(age -> age >= 20, 1);

    private static final int DEDUCTION = 350;

    private final Predicate<Integer> predicate;
    private final double discountRate;

    DiscountPolicy(Predicate<Integer> predicate, double discountRate) {
        this.predicate = predicate;
        this.discountRate = discountRate;
    }

    public static DiscountPolicy from(int age) {
        return Arrays.stream(values())
                .filter(discountPolicy -> discountPolicy.predicate.test(age))
                .findFirst()
                .orElse(ADULT);
    }

    public int applyDiscount(int fare) {
        if (this == ADULT) {
            return fare;
        }

        return (int) ((fare - DEDUCTION) * discountRate);
    }


}
