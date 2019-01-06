
import java.util.*;

public class Leetcode5 {
    public String longestPalindrome(String s) {
        if(s.equals("")) return s;
        int[] res = new int[]{0,0};
        for( int i=0; i<s.length(); i++){
            int[] res1 = findPaliFromK(s, i);
            if (res1[1]- res1[0]>= res[1]- res[0]) res = res1;
        }
        return s.substring(res[0], res[1]+1);
    }
    public int[] findPaliFromK(String s, int k){
        if(k==s.length()-1) return new int[]{k,k};
        int left = k;
        int right = k;
        while(left>=0 && right< s.length()){
            if (s.charAt(left) != s.charAt(right)) break;
            left--;
            right++;
        }
        int left1 = k;
        int right1 = k+1;
        while(left1>=0 && right1< s.length()){
            if (s.charAt(left1) != s.charAt(right1)) break;
            left1--;
            right1++;
        }
        System.out.println(right1+'_'+left1);
        return right1-left1>right- left ? new int[]{left1+1, right1-1} : new int[]{left+1, right-1};
    }

    public static void main(String[] args) {
        String s = "bb";
        System.out.println(new Leetcode5().longestPalindrome(s));
    }
}
