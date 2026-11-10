package com.example.mathrpgturnbased;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class QuestionGenerator {

    private Random random = new Random();

    public Questions generate(String topic) {

        if (topic.equals("GCD")) {

            int a = random.nextInt(49) + 12;
            int b = random.nextInt(49) + 12;

            int answer = gcd(a, b);

            return createQuestion(
                    "Find the GCD of " + a + " and " + b,
                    answer
            );
        }

        else if (topic.equals("LCM")) {

            int a = random.nextInt(11) + 2;
            int b = random.nextInt(11) + 2;

            int answer = (a * b) / gcd(a, b);

            return createQuestion(
                    "Find the LCM of " + a + " and " + b,
                    answer
            );
        }

        else if (topic.equals("PYTHAGORAS")) {

            int[][] triples = {
                    {3, 4, 5},
                    {5, 12, 13},
                    {6, 8, 10},
                    {8, 15, 17}
            };

            int index = random.nextInt(triples.length);

            int a = triples[index][0];
            int b = triples[index][1];
            int answer = triples[index][2];

            return createQuestion(
                    "A right triangle has legs " + a +
                            " and " + b + ". Find its hypotenuse.",
                    answer
            );
        }

        return null;
    }

    private int gcd(int a, int b) {

        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }

        return a;
    }

    private Questions createQuestion(String prompt, int answer) {

        ArrayList<Integer> options = new ArrayList<>();

        options.add(answer);

        while (options.size() < 4) {

            int wrongAnswer = answer + random.nextInt(15) - 7;

            if (wrongAnswer > 0 &&
                    wrongAnswer != answer &&
                    !options.contains(wrongAnswer)) {

                options.add(wrongAnswer);
            }
        }

        Collections.shuffle(options);

        String[] stringOptions = new String[4];

        for (int i = 0; i < 4; i++) {
            stringOptions[i] = String.valueOf(options.get(i));
        }

        return new Questions(
                prompt,
                stringOptions,
                String.valueOf(answer)
        );
    }
}