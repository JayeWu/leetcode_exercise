import java.util.*;
public class Leetcode17 {
    public static void main(String[] args) {
        System.out.println(new Leetcode17().letterCombinations("234"));
    }
    public List<String> letterCombinations(String digits) {
        List<String> res = new ArrayList<>();
        if(digits.equals("")) return res;

        Map<Character, List<Character>> map = new HashMap<>();
        map.put('2', Arrays.asList('a','b','c'));
        map.put('3', Arrays.asList('d','e','f'));
        map.put('4', Arrays.asList('g','h','i'));
        map.put('5', Arrays.asList('j','k','l'));
        map.put('6', Arrays.asList('m','n','o'));
        map.put('7', Arrays.asList('p','q','r', 's'));
        map.put('8', Arrays.asList('t','u','v'));
        map.put('9', Arrays.asList('w','x','y', 'z'));
        addAllComb(digits,res,map,new char[digits.length()],0);
        return res;
    }
    public void addAllComb(String digits, List<String> list, Map<Character, List<Character>> map, char[] ans , int n){
        if (n==digits.length()) {
            list.add(new String(ans));
            return;
        }
        List<Character> list1 = map.get(digits.charAt(n));
        for(Character ca: list1){
            ans[n] = ca;
            addAllComb(digits, list, map, ans, n+1);
        }
    }
}
