import java.util.*;

public class Leetcode30 {
    public static void main(String[] args) {
        System.out.println(new Leetcode30().findSubstring("barfoofoobarthefoobarman",new String[]{"bar","foo","the"}));
    }
    public List<Integer> findSubstring(String s, String[] words) {
        List<Integer> res = new ArrayList<>();
        if(s.equals("") || words.length==0) return res;
        int n = words[0].length();
        int m = words.length;
        int left=0;
        Map<String, Integer> map = new HashMap<>();
        for (int i = 0; i < m; i++) {
            int value = map.containsKey(words[i]) ? map.get(words[i])+1:1;
            map.put(words[i],value);
        }
        while(left<=s.length()-n){
            if(isMatch(left, s, words, n, (Map<String, Integer>) ((HashMap<String, Integer>) map).clone())){
                res.add(left);
            }
            left+=1;
        }
        return res;
    }
    public boolean isMatch(int left, String s, String[] words, int n, Map<String, Integer> map){
        for (int i = 0; i <words.length ; i++) {
            if(left>= s.length()) break;
            int end = Math.min(s.length(),left+n);
            String tmp = s.substring(left,end);
            if (map.containsKey(tmp)){
                if(map.get(tmp)>1) map.put(tmp, map.get(tmp)-1);
                else map.remove(tmp);
            }else{
                return false;
            }
            left+=n;
        }
        return map.isEmpty();
    }
}
