package com.walkerholic.walkingpet.domain.gacha.function;

import java.util.Random;

public class GachaFunction {
    private static final int GRADE2_APPEAR_PERCENTAGE = 90;
    private static final int GRADE1_APPEAR_PERCENTAGE = 70;
    private static final int GRADE1 = 1;
    private static final int GRADE2 = 2;
    private static final int GRADE3 = 3;

    public static int decideGrade(String boxType){
        int randomNumber = new Random(System.currentTimeMillis()).nextInt(100) + 1;
        int grade;
        if (boxType.equals("luxury")) {
            grade = (randomNumber <= GRADE2_APPEAR_PERCENTAGE) ? GRADE2 : GRADE3;
        } else {
            grade = (randomNumber <= GRADE1_APPEAR_PERCENTAGE) ? GRADE1 : GRADE2;
        }
        return grade;
    }
}
