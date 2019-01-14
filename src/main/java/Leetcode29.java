public class Leetcode29 {
    public static void main(String[] args) {
        System.out.println(new Leetcode29().divide(-2147483648,-3));
    }
    public int divide(int dividend, int divisor) {
        if(divisor==1) return dividend;
        if(divisor==2) return dividend>>1;
        if(divisor==0 || (dividend==Integer.MIN_VALUE && divisor==-1)) return Integer.MAX_VALUE;
        long m = (long) dividend>0? (long)dividend:(long)dividend*-1;
        long n = (long) divisor>0? (long)divisor:(long)divisor*-1;
        int sig = (dividend<0) ^ (divisor<0)? -1:1;
        int res =0;
        System.out.println(m);
        System.out.println(n);
        while(m>=n){
            long k=1;
            long j=n;
            while(m>=(j<<1)){
                System.out.println(j);
                j<<=1;
                k<<=1;
            }
            res += k;
            m-=j;
        }
        return sig*res;
    }
}
