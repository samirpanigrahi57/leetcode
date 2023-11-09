package topInterviewQuestions.easy.math;

import topInterviewQuestions.utils.LeetUtils;

import java.util.ArrayList;
import java.util.List;

public class FizzBuzz {
    public static void main(String[] args) {
        LeetUtils.printListOfStrings(fizzBuzz(15));

    }

    private static List<String> fizzBuzz(int n) {
        List<String> fizzBuzz = new ArrayList<>();
        for(int i = 1; i <= n ; i++){
            if(i%3==0 && i%5==0){
                fizzBuzz.add("FizzBuzz");
            }else if(i%5==0){
                fizzBuzz.add("Buzz");

            }else if(i%3==0){
                fizzBuzz.add("Fizz");

            }else{
                fizzBuzz.add(Integer.toString(i));
            }
        }
        return fizzBuzz;
    }
}
