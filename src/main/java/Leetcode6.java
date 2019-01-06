import java.util.*;

public class Leetcode6 {
    public static void main(String[] args) {
        String s = "123456789abcd";
        System.out.println(new Leetcode6().convert(s, 3));
    }
    public String convert(String s, int numRows) {
        if(numRows==1) return s;
        List<Character> list = new ArrayList<>();
        for (int i = 0;i<numRows; i++){
            int index = i;
            while(index<s.length()){
                list.add(s.charAt(index));
                index = index+2*numRows-2-2*i;
                if (index<s.length() && i!=0 && i!=numRows-1) {
                    list.add(s.charAt(index));

                }
                index = index+2*i;
            }

        }
        Character[] cs = new Character[list.size()];
        cs = list.toArray(cs);
        char[] ccs = new char[list.size()];
        for (int i = 0; i < cs.length; i++) {
            ccs[i] = cs[i];
        }
        return new String(ccs);
    }

}
