package topInterviewQuestions.easy.arrays;

public class BestTimeToBuyAndSellStocks {
    public static void main(String[] args) {
        System.out.println(buyAndSell(new int[]{7, 1, 5, 3, 6, 4}));
        System.out.println(buyAndSell(new int[]{1, 2, 3, 4, 5}));
    }

    private static int buyAndSell(int[] prices) {
        int profit = 0;

        //starts with 2ns day and till the last day
        for (int i = 1; i < prices.length; i++) {
            //sell stock if today's price greater than yesterdays
            if (prices[i] > prices[i - 1]) {
                profit += prices[i] - prices[i - 1];
            }
        }

        return profit;
    }
}
