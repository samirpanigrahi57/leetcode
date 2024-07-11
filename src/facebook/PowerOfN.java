package facebook;

public class PowerOfN {
    public static void main(String[] args) {
        System.out.println(myPow(-1.00000, -2147483648));
    }

    public static double myPow(double x, int n) {
        double result = 1;
        double tempx = x;
        int count = n;
        if(n == Integer.MIN_VALUE){
            count = Integer.MAX_VALUE;
        }
        else if(n < 0){
            count = -1*count;

        }

        while(count > 0){
            if(count%2==0){
                x = x*x;
                count = count/2;
            }else{
                result = result * x;
                count--;
            }
        }
        if(n == Integer.MIN_VALUE){
            result = result * (tempx);
        }
        return n < 0 ? (double)1/result : result;


    }
}
