import java.util.*;

public class Leetcode3 {
    public int lengthOfLongestSubstring(String s) {
        if(s.equals("")) return 0;
        int[] res = new int[2];
        int left =0;
        Map<Character, Integer> map = new HashMap<>();
        for (int i=0; i<s.length(); i++){
            Character ca = s.charAt(i);
            if (!map.containsKey(ca) || map.get(ca)<left){
                map.put(ca, i);
            }else{
                if(res[1]-res[0]<i-1-left){
                    res[0] = left;
                    res[1] = i-1;
                }
                left = map.get(ca)+1;
                map.put(ca, i);
            }
        }
        if (s.length()-1-left > res[1]-res[0]){
            res[0] = left;
            res[1] = s.length()-1;
        }

        return res[1] - res[0]+1;
    }
}
