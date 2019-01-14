public class Leetcode28 {
    public static void main(String[] args) {
        System.out.println(new Leetcode28().strStr("misssspi", "pi"));
    }
    public int strStr(String haystack, String needle) {
        if(needle.equals(haystack)) return 0;
        for(int i=0;i<haystack.length()-needle.length()+1;i++){
            int k =0;
            while(k<needle.length() && haystack.charAt(i+k)==needle.charAt(k)){
                k++;
            }
            if (k==needle.length()) {
                return i;
            }
        }
        return -1;
    }
}
