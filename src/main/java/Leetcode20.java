import javafx.scene.chart.Chart;

import java.util.*;
public class Leetcode20 {
    public static void main(String[] args) {
        System.out.println(new Leetcode20().isValid("{[]}"));
    }
    public boolean isValid(String s) {
        Map<Character,Character> map = new HashMap<>();
        map.put('(',')');
        map.put('[',']');
        map.put('{','}');
        Stack<Character> stack = new Stack<>();
        for(int i =0;i<s.length();i++){
            char a = s.charAt(i);
            if (a=='(' || a=='['|| a=='{') {
                stack.push(a);
            }else if (stack.empty() || a != map.get(stack.pop())) {
                return false;
            }
        }
        return stack.empty();
    }
}
