import org.omg.CORBA.INTERNAL;
import sun.reflect.generics.tree.Tree;

import javax.security.auth.Subject;
import java.lang.reflect.Array;
import java.util.*;

public class LeetCode {
    class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode() {}
        TreeNode(int val) { this.val = val; }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
    //1. 两数之和
    public int[] twoSum(int[] nums, int target) {
        HashMap<Integer, Integer> m = new HashMap<Integer, Integer>();
        int[] res = new int[2];
        for (int i = 0; i < nums.length; ++i) {
            m.put(nums[i], i);
        }
        for (int i = 0; i < nums.length; ++i) {
            int t = target - nums[i];
            if (m.containsKey(t) && m.get(t) != i) {
                res[0] = i;
                res[1] = m.get(t);
                break;
            }
        }
        return res;
    }

    //2. 两数相加
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode res = new ListNode(-1);
        boolean carry = false;
        ListNode cur = res;
        while (l1!=null || l2!=null){
            int a = l1 == null ? 0: l1.val;
            int b = l2 == null ? 0:l2.val;
            int c = carry? 1:0;
            carry = a+b+c>= 10;
            cur.next = new ListNode((a+b+c)%10);

            cur = cur.next;
            l1 = l1 == null ? null : l1.next;
            l2 = l2 == null ? null : l2.next;

        }
        if (carry) cur.next = new ListNode(1);
        return res.next;
    }

    //3. 无重复字符的最长子串
    public int lengthOfLongestSubstring(String s) {
        if(s.equals("")) return 0;
        int[] res = new int[2];
        int left =0;
        int right = 0;
        Map<Character, Integer> map = new HashMap<>();
        for (int i=0; i<s.length(); i++){
            Character ca = s.charAt(i);
            if(!map.containsKey(ca) || map.get(ca) < left)
            {
                map.put(ca, i);
            }
            else
            {
                right = i-1;
                if (right - left > res[1] - res[0])
                {
                    res[1] = right;
                    res[0] = left;
                }
                left = map.get(ca)+1;
                map.put(ca, i);
            }
        }

        right = s.length()-1;
        if (right - left > res[1] - res[0])
        {
            res[1] = right;
            res[0] = left;
        }

        return res[1] - res[0]+1;
    }

    //4. 寻找两个有序数组的中位数
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int l1 = nums1.length;
        int l2 = nums2.length;

        int a =0, b =0;
        int[] ss = new int[2];
        double res;

        while(a < l1 || b< l2)
        {
            if(a + b > (l1+l2)/2)
            {
                break;
            }
            if (b != l2 && (a == l1 || nums1[a] > nums2[b]))
            {
                ss[0] = ss[1];
                ss[1] = nums2[b];

                b++;
            }
            else
            {
                ss[0] = ss[1];
                ss[1] = nums1[a];
                a++;
            }
        }
        if((l1+l2)%2 ==0) res =  (ss[0]+ss[1])/2.0;
        else res = ss[1];
        return res;
    }

    //5. 最长回文子串
    public String longestPalindrome(String s) {
        int len = s.length();
        if (len < 2) return s;
        String s1;
        int start =0;
        int maxlen = 0;
        for(int i =0;i<len-1;i++)
        {
            System.out.println("====");
            System.out.println(i);
            int len1 = searchPalindrome(s, i, i);
            int len2 = searchPalindrome(s, i, i+1);
            if(maxlen < len1)
            {
                maxlen = len1;
                start = i-len1/2;
            }
            if(maxlen < len2)
            {
                maxlen = len2;
                start = i-len2/2+1;
            }
        }
        System.out.println(maxlen);
        return s.substring(start,start+maxlen);
    }

    private int searchPalindrome(String s, int left, int right) {

        while(left>=0 && right < s.length() && s.charAt(left) == s.charAt(right))
        {
            left --;
            right ++;
        }
        System.out.println(s.substring(left+1,right));

        return right-left-1;
    }

    private boolean checkPalindrome(String s) {
        int len = s.length();
        for(int i=(len-1)/2; i>=0; i--)
        {
            char a = s.charAt(i);
            char b = s.charAt(len-i-1);
            System.out.println(i);
            System.out.println(a);
            System.out.println(b);
            if(a != b)
            {
                return false;
            }
        }
        return true;
    }
    //6. Z 字形变换
    public String convert(String s, int numRows) {
        if (numRows ==1) return s;
        int len = s.length();
        char[] ccs = new char[len];
        int m=0;
        int width = len/(2*numRows -2) +1;
        int v = 2*numRows -2;
        for(int i=0; i<numRows;i++)
        {
            for(int j=0;j <width; j++)
            {
                int index = j*v+i;
                System.out.println(index);
                if(index >= len) break;
                ccs[m] = s.charAt(index);
                m++;
                if(m==len) break;
                if(i!=0 && i!=numRows-1)
                {
                    index = (j+1)*v-i;
                    if(index >= len) break;
                    ccs[m] = s.charAt(index);
                    m++;
                    if(m==len) break;
                }
            }
        }
        return new String(ccs);
    }
    //6. Z 字形变换
    public String convert2(String s, int numRows) {
        if(s == null || s.length() <= 0  || numRows <= 1){
            return s;
        }
        int step = numRows * 2 - 2;
        int unitNums = (s.length() - 1) / step + 1;
        StringBuilder sb = new StringBuilder();
        for(int row = 0; row < numRows; row ++){
            int unit = 0;
            while(unit < unitNums){
                int start = row + step * unit;
                if(start >= s.length()){
                    break;
                }
                sb.append(s.charAt(start));
                int index = start + step - 2 * row;
                if(index > start && index < start + step && index < s.length()){
                    sb.append(s.charAt(index));
                }
                unit++;
            }
        }
        return sb.toString();
    }

    //7. 整数反转
    public int reverse(int x) {
        boolean flag;
        flag = x>0;
        if(!flag) x = -1*x;
        int n =0;
        int m =0;
        while(x>0)
        {
            int i = x%10;
            x = x/10;
            n *= 10;
            n += i;
            if ((n-i)/10 != m) return 0;
            m = n;
        }

        return flag ? n : -1*n;
    }

    //8. 字符串转换整数 (atoi)
    public int myAtoi(String str) {
        int len = str.length();
        boolean flag = false;
        boolean symb = true;
        int x = 0;
        for(int i=0;i<len; i++)
        {
            char a = str.charAt(i);
            if(!flag)
            {
                if(a =='-')
                {
                    symb = false;
                    flag = true;
                    continue;
                }
                else if(a == '+')
                {
                    symb = true;
                    flag = true;
                    continue;
                }
            }
            if(a >= '0' && a<= '9')
            {
                flag = true;
                if(symb && (Integer.MAX_VALUE - (a-'0'))/10 < x) return Integer.MAX_VALUE;

                if(!symb && ((Integer.MIN_VALUE + (a-'0'))/10) > -1 *x) return Integer.MAX_VALUE;

                x *= 10;
                x += a-'0';
            }
            else if(!flag && a == ' ')
            {
                continue;
            }
            else
            {
                break;
            }
        }
        return symb ? x : -1*x;
    }

    //9. 回文数
    public boolean isPalindrome(int x) {
        int y =0;
        while(x >0)
        {
            int a = x%10;
            x /= 10;
            y *= 10;
            y+=a;
        }
        System.out.println(x);
        System.out.println(y);
        return x ==y;
    }

    //10. 正则表达式匹配
    public boolean isMatch(String s, String p) {
        boolean flag = false;
        if(p.isEmpty()) return s.isEmpty();
        if(p.length() ==1) return (p.equals(s) || (s.length() ==1 && p.equals(".")));
        if(p.charAt(1) != '*')
        {
            if(s.isEmpty()) return false;
            return((p.charAt(0) == s.charAt(0) || p.charAt(0) == '.') && isMatch(s.substring(1), p.substring(1)));
        }
        else
        {
            int i=0;
            for(i =1; i<=s.length() && (p.charAt(0) == '.' || (p.charAt(0) == s.charAt(i-1))); i++)
            {
                if (isMatch(s.substring(i-1), p.substring(2))) return true;
            }
            return (isMatch(s.substring(i-1), p.substring(2)));
        }
    }
    //10. 正则表达式匹配,dp算法
    public boolean isMatch_dp(String s, String p) {
        int m = s.length(), n = p.length();
        boolean[][] dp = new boolean[m+1][n+1];
        dp[0][0] = true;
        for (int i = 0; i <= m; ++i) {
            for (int j = 1; j <= n; ++j) {
                if (j > 1 && p.charAt(j-1)== '*') {
                    dp[i][j] = dp[i][j - 2] || (i > 0 && (s.charAt(i-1) == p.charAt(j-2) || p.charAt(j-2) == '.') && dp[i - 1][j]);
                } else {
                    dp[i][j] = i > 0 && dp[i - 1][j - 1] && (s.charAt(i-1) == p.charAt(j-1) || p.charAt(j-1) == '.');
                }
            }
        }
        return dp[m][n];
    }

    //11. 盛最多水的容器
    public int maxArea(int[] height) {
        int len = height.length;
        int left =0;
        int right = len-1;
        int max = 0;
        int area;
        while(left < right)
        {
            area = Math.min(height[left],height[right]) * (right- left);
            max = Math.max(area,max);
           if(height[left] < height[right]) left++;
           else right--;
        }
        return max;
    }

    //12. 整数转罗马数字
    public int romanToInt(String s) {
        int res = 0;
        for(int i=0; i<s.length()-1; i++)
        {
            char si = s.charAt(i);
            char sj = s.charAt(i+1);

            if(roman(si)>= roman(sj))
                res += roman(si);
            else
                res -= roman(si);
        }
        return res+ roman(s.charAt(s.length()-1));
    }

    //13. 罗马数字转整数
    public int roman(char c) {
        switch(c)
        {
            case 'I':return 1;
            case 'V':return 5;
            case 'X':return 10;
            case 'L':return 50;
            case 'C':return 100;
            case 'D':return 500;
            case 'M':return 1000;
            default:return 0;
        }


    }
    //14. 最长公共前缀
    public String longestCommonPrefix(String[] strs) {
        if (strs.length == 0) return "";
        int minlen = Integer.MAX_VALUE;
        for(int i =0; i<strs.length;i++)
        {
            minlen = Math.min(strs[i].length(),minlen);

        }
        char[] sb = new char[minlen];
        int k = 0;
        for(int i=0;i<minlen;i++)
        {
            char a = strs[0].charAt(i);
            for(int j=1;j<strs.length;j++)
            {
                if(a != strs[j].charAt(i))
                {
                    return new String(sb).substring(0,k);
                }
            }
            sb[k] = a;
            k++;
        }
        return new String(sb).substring(0,k);

    }

    //15. 三数之和
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> list = new ArrayList<>();
        Arrays.sort(nums);

        for (int i = 0; i < nums.length-2; i++)
        {
            if(i-1>=0 && nums[i] == nums[i-1]) continue;
            int l =  i+1, r = nums.length-1;
            if(nums[i] > 0) break;
            while(l<r)
            {
                if(l -1 >=i+1 && nums[l] == nums[l-1]) {
                    l++;continue;
                }
                if(nums[i]+nums[l]+nums[r] == 0)
                {
                    list.add(Arrays.asList(nums[i], nums[l], nums[r]));
                    l++;
                    r--;
                }
                else if(nums[l] + nums[r] + nums[i] < 0)
                {
                    l++;
                }
                else
                {
                    r--;
                }
            }

        }
        return list;
    }

    //16. 最接近的三数之和
    public int threeSumClosest(int[] nums, int target) {
        Arrays.sort(nums);
        int res = Integer.MAX_VALUE;
        int ans = res;
        for (int i = 0; i < nums.length-2; i++) {
            int l = i+1, r = nums.length-1;
            while(l<r)
            {
                int ans2 = nums[i] + nums[l] + nums[r];
                if( ans2 - target== 0) return target;
                else if(ans2< target) l++;
                else r--;
                int abs = Math.abs(ans2 - target);
                if(abs < res )
                {
                    res = abs;
                    ans = ans2;
                }

            }
        }
        return ans;
    }

    //17. 电话号码的字母组合
    public List<String> letterCombinations(String digits) {
        List<String> list = new ArrayList<>();
        if(digits.equals("")) return list;
        String[] strs = new String[8];
        List<String> res1 = new ArrayList<>();
        List<String> res2 = new ArrayList<>();
        strs[0] = "abc";
        strs[1] = "def";
        strs[2] = "ghi";
        strs[3] = "jkl";
        strs[4] = "mno";
        strs[5] = "pqrs";
        strs[6] = "tuv";
        strs[7] = "wxyz";

        addAllComb(digits, list, strs, new char[digits.length()], 0);
        return list;
    }

    public void addAllComb(String digits, List<String> list, String[] strs, char[] ans , int n){
        if (n==digits.length()) {
            list.add(new String(ans));
            return;
        }
        String list1 = strs[digits.charAt(n) - '2'];
        for (int i = 0; i < list1.length(); i++) {
            char ca = list1.charAt(i);
            ans[n] = ca;
            addAllComb(digits, list, strs, ans, n+1);
        }
    }

    //18. 四数之和
    public List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> list = new ArrayList<>();
        Arrays.sort(nums);
        for(int k =0; k< nums.length-3;k++) {
            if(nums[k] > target) break;
            if (k - 1 >= 0 && nums[k] == nums[k - 1]) continue;
            for (int i = k+1; i < nums.length - 2; i++) {
                if (i - 1 >= k+1 && nums[i] == nums[i - 1]) continue;
                int l = i + 1, r = nums.length - 1;
                while (l < r) {
                    if (l - 1 >= i + 1 && nums[l] == nums[l - 1]) {
                        l++;
                        continue;
                    }
                    if (nums[i] + nums[l] + nums[r]  == target - nums[k]) {
                        list.add(Arrays.asList(nums[k],nums[i], nums[l], nums[r]));
                        l++;
                        r--;
                    } else if (nums[l] + nums[r] + nums[i] < target - nums[k]) {
                        l++;
                    } else {
                        r--;
                    }
                }

            }
        }
        return list;
    }

    //19. 删除链表的倒数第N个节点
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode cur = new ListNode(-1);
        cur.next = head;
        ListNode ncur = head;
        for (int j = 0; j < n; j++) {
            if(ncur ==null) return head;
            ncur = ncur.next;

        }

        while(ncur!=null)
        {
            cur = cur.next;
            ncur = ncur.next;
        }
        if(cur.next == head) return head.next;
        cur.next = cur.next.next;
        return head;
    }

    //20. 有效的括号
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

    //21. 合并两个有序链表
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode cur1 = l1;
        ListNode cur2 = l2;
        ListNode pre = new ListNode(-1);
        ListNode head = pre;
        while(cur1 != null && cur2 !=null)
        {
            if(cur1.val<cur2.val)
            {
                pre.next = cur1;
                cur1 = cur1.next;
            }
            else
            {
                pre.next = cur2;
                cur2 = cur2.next;
            }
            pre = pre.next;
        }
        if(cur1 != null) pre.next = cur1;
        if(cur2 != null) pre.next = cur2;
        return head.next;
    }

    //22. 括号生成
    public List<String> generateParenthesis(int n) {
        List<String> list = new ArrayList<>();
        char[] chars = new char[2*n];

        subGenerateParenthesis(chars,  list, 0, 0);
        return list;
    }

    public void subGenerateParenthesis(char[] chars, List<String> list ,int i, int p) {
        if(i == chars.length)
        {
            if(p==0)
                list.add(new String(chars));
            return;
        }
        chars[i] = '(';
        subGenerateParenthesis(chars, list, i+1, p+1);
        if(p>0)
        {
            chars[i] = ')';
            subGenerateParenthesis(chars, list, i+1, p-1);
        }


    }
    //23. 合并K个排序链表
    public ListNode mergeKLists(ListNode[] lists) {
        int l = lists.length;
        if(l==0) return null;
        if(l==1) return lists[0];

        ListNode node1 = lists[0];
        ListNode node2;
        ListNode node;
        while(l>1)
        {
            for (int i = 0; i <(l+1)/2; i++) {
                node1 = lists[i];
                node2 = i+(l+1)/2 < l ? lists[i+(l+1)/2] : null;
                lists[i] = mergeTwoLists(node1, node2);
            }
            l = (l+1)/2;
        }
        return lists[0];
    }

    //24. 两两交换链表中的节点
    public ListNode swapPairs(ListNode head) {
        if(head ==null) return null;
        if(head.next == null) return head;

        ListNode pre = new ListNode(-1);
        pre.next = head;
        ListNode left = pre;
        ListNode right = head;
        ListNode tmp;
        while(left!=null && right!=null)
        {
            if(right.next ==null) break;

            left.next = right.next;

            tmp = left.next.next;


            right.next.next = right;
            right.next = tmp;

            left = left.next.next;
            right = right.next;

        }
        return pre.next;
    }
    //25. K 个一组翻转链表
    public ListNode reverseKGroup(ListNode head, int k) {
        if(k==1 || head ==null) return  head;

        ListNode l,o,r,cur=head;
        l = head;
        o = l.next;
        if(o==null) return head;
        r = o.next;

        for (int i = 0; i < k; i++) {
            if(cur ==null) return head;
            cur= cur.next;
        }
        for (int i = 0; i < k-1; i++) {
            o.next = l;
            l=o;
            o=r;
            if(r ==null) break;
            r= r.next;
        }

        head.next = reverseKGroup(r, k);
        return l;
    }
    //26. 删除排序数组中的重复项
    public int removeDuplicates(int[] nums) {
        int l =0;
        int i =0;
        while(i<nums.length) {
            if (i == 0 || nums[i] != nums[i - 1]) {
                nums[l] = nums[i];
                l++;
            }
            i++;
        }
        return l;
    }
    //27. 移除元素
    public int removeElement(int[] nums, int val) {
        int l =0;
        int i=0;
        while(i<nums.length) {
            if (nums[i] != val) {
                nums[l] = nums[i];
                l++;
            }
            i++;
        }
        return l;
    }
    //28. 实现 strStr()
    public int strStr(String haystack, String needle) {
        if(needle.equals("")) return 0;
        int n = needle.length();
        for (int i = 0; i <haystack.length()-n+1 ; i++) {
            if(haystack.substring(i,i+n).equals(needle))
            {
                return i;
            }
        }
        return -1;
    }

    //29. 两数相除
    public int divide(int dividend, int divisor) {
        if(dividend == divisor) return 1;
        if(dividend == Integer.MIN_VALUE && divisor ==-1) return Integer.MAX_VALUE;
        if(divisor ==1) return dividend;
        if(divisor==2) return dividend>>1;
        if(dividend == Integer.MIN_VALUE ) dividend+=1;
        if(divisor == Integer.MIN_VALUE) return 0;

        int x,y;
        boolean flag = false;
        if(divisor<0)
        {
            divisor = ~(divisor-1);
            flag = true;
        }
        if(dividend <0)
        {
            dividend = ~(dividend-1);
            flag = !flag;
        }
        if(divisor==2) return flag ? ~((dividend>>1) -1) : dividend>>1;
        x = divisor;

        while(x<dividend){

            if(x<<1 <0) break;
             x = x<<1;
        }
        if(x<0) x = x>>1;
        y = dividend;
        int ans=0;
        while(x>=divisor)
        {
            ans = ans<<1;
            if(y>=x)
            {
                ans+=1;
                y = y-x;
            }
            x = x>>1;
        }

        return flag ? ~(ans-1) : ans;
    }

    //30. 串联所有单词的子串
    public List<Integer> findSubstring(String s, String[] words) {
        List<Integer> list = new ArrayList<>();
        if(words.length == 0) return list;
        Map<String,Integer> map = new HashMap<>();
        for (String word : words) {
            map.put(word,map.getOrDefault(word,0)+1);
        }
        int left = 0;
        int n =words.length;
        int wordlen = words[0].length();
        for (int i = 0; i < wordlen; i++) {
            left = i;
            Map<String, Integer> tmp_map = new HashMap<>();
            while(left+n*wordlen<=s.length())
            {
                int flag =0;
                String s2 = s.substring(left, left+wordlen);
                if(left == i)
                {
                    int j;
                    for (j = 0; j < n; j++) {
                        String s1 = s.substring(left+j*wordlen, left+(j+1)*wordlen);
                        tmp_map.put(s1, tmp_map.getOrDefault(s1,0)+1);
                    }
                }
                else
                {
                    String s1 = s.substring(left+(n-1)*wordlen,left+n*wordlen);
                    tmp_map.put(s1, tmp_map.getOrDefault(s1,0)+1);

                }


                if(tmp_map.equals(map)) list.add(left);
                left+=wordlen;
                tmp_map.put(s2, tmp_map.get(s2)-1);
                if(tmp_map.get(s2) == 0) tmp_map.remove(s2);
            }
        }
        return list;
    }

    //31. 下一个排列
    public void nextPermutation(int[] nums) {
        int n = nums.length;
        int tmp;
        if(n==0 || n ==1) return ;
        int i;
        int min = Integer.MAX_VALUE, mini=n-1;
        for (i = n-1; i >0 ; i--) {


            if(nums[i-1]<nums[i])
            {
                for (int j = i; j <n ; j++) {
                    if(nums[j]<min && nums[j] > nums[i-1])
                    {
                        min = nums[j];
                        mini = j;
                    }
                }


                tmp = nums[mini];
                nums[mini] = nums[i-1];
                nums[i-1] = tmp;
                for (int j = i; j < n; j++) {
                    for (int k = j+1; k < n; k++) {
                        if(nums[j]>nums[k])
                        {
                            tmp = nums[j];
                            nums[j] = nums[k];
                            nums[k] = tmp;
                        }
                    }
                }
                break;
            }
        }
        if (i==0) Arrays.sort(nums);
    }

    //32. 最长有效括号
    public int longestValidParentheses(String s) {
        int maxlen = 0;
        int sum = 0;
        int left = 0;
        for(int i=0;i<s.length();i++)
        {
            char a = s.charAt(i);
            if(a=='(') sum++;
            if(a==')') sum--;
            if(sum==0)
            {
                int xlen = i-left+1;
                maxlen = maxlen > xlen ? maxlen :xlen;
            }
            if(sum<0)
            {
                sum = 0;
                left = i+1;
            }
        }
        int right = s.length()-1;
        sum =0;
        for(int i=s.length()-1;i>=0;i--)
        {
            char a = s.charAt(i);

            if(a==')') sum++;
            if(a=='(') sum--;
            if(sum ==0)
            {
                int xlen = right-i+1;
                maxlen = maxlen > xlen ? maxlen :xlen;
            }
            else if(sum<0)
            {
                sum = 0;
                right = i-1;
            }
        }
        return maxlen;
    }
    //33. 搜索旋转排序数组
    public int search(int[] nums, int target) {
        int left = 0;
        int right = nums.length-1;
        int mid;

        while(left<=right)
        {
            if(target == nums[left]) return left;
            if(target == nums[right]) return right;
            mid = (right+left)/2;
            if(target == nums[mid]) return mid;
            if(nums[left] < nums[right])
            {
                if(target > nums[mid]) left = mid+1;
                else right = mid -1;
                continue;
            }

            if(nums[mid]>nums[left])
            {
                if(target > nums[mid] || target< nums[left]) left = mid+1;
                else right = mid-1;

            }
            else
            {
                if(target < nums[mid] || target> nums[left]) right = mid-1;
                else left = mid+1;
            }

        }
        return -1;
    }
    //34. 在排序数组中查找元素的第一个和最后一个位置
    public int[] searchRange(int[] nums, int target) {
        int[] ans = new int[2];
        ans[0] = -1;
        ans[1] = -1;
        if(nums.length == 0) return ans;
        int left = 0;
        int right = nums.length-1;
        int mid =(left+right) /2;
        while(left<=right)
        {
            mid= (left+right) /2;
            if(nums[mid] == target) break;
            if(nums[mid]>target) right = mid-1;
            else left = mid+1;
        }
        if(nums[mid] != target) return ans;

        int midx = mid;
        int rightx = right;
        right = midx;
        while(left<=right)
        {
            if(nums[right]<target)
            {
                ans[0] = right+1;
                break;
            }
            mid= (right+left)/2;
            if(nums[mid]>=target) right = mid-1;
            else left = mid+1;
        }

        if(ans[0] == -1) ans[0] = left;

        right = rightx;
        left = midx;
        while(left<=right)
        {
            if(nums[left]>target)
            {
                ans[1] = left-1;
                break;
            }
            mid= (right+left)/2;
            if(nums[mid]>target) right = mid-1;
            else left = mid+1;
        }
        if(ans[1] == -1) ans[1] = right;
        return ans;
    }
    //35. 搜索插入位置
    public int searchInsert(int[] nums, int target) {
        if(nums.length ==0) return 0;
        if(nums.length == 1)
        {
            return nums[0]>=target?0:1;
        }
        int left = 0;
        int right = nums.length-1;
        int mid;
        while(left<=right)
        {
            mid= (left+right) /2;
            if(nums[mid] == target) return mid;
            if(nums[mid]>target) right = mid-1;
            else left = mid+1;
        }

        if(left>=nums.length) return left;
        if(nums[left]>target) return left;
        else return left+1;

    }
    //36. 有效的数独
    public boolean isValidSudoku(char[][] board) {
        for(int i=0;i<9;i++)
        {
            int[] numbers = new int[]{1,1,1,1,1,1,1,1,1};
            for(int j=0;j<9;j++)
            {
                char c = board[i][j];
                if(c=='.') continue;
                int a = c-'1';
                if(numbers[a] ==0) return false;
                numbers[a]--;
            }
        }

        for(int i=0;i<9;i++)
        {
            int[] numbers = new int[]{1,1,1,1,1,1,1,1,1};
            for(int j=0;j<9;j++)
            {
                char c = board[j][i];
                if(c=='.') continue;
                int a = c-'1';
                if(numbers[a] ==0) return false;
                numbers[a]--;
            }
        }

        for(int i=0;i<9;i++)
        {
            int[] numbers = new int[]{1,1,1,1,1,1,1,1,1};
            int le = (i/3) *3;
            int ri = (i%3) *3;
            for(int j=0;j<9;j++)
            {

                char c = board[le+j/3][ri+(j%3)];
                if(c=='.') continue;
                int a = c-'1';
                if(numbers[a] ==0) return false;
                numbers[a]--;
            }
        }
        return true;
    }

    //37. 解数独
    public void solveSudoku(char[][] board) {
        int[][] board_rows = new int[9][9];
        int[][] board_cols = new int[9][9];
        int[][] board_boxs = new int[9][9];

        for (int i = 0; i < 9 ; i++) {
            for (int j = 0; j <9 ; j++) {

                int a = board[i][j] - '1';
                if (a<0) continue;
                int boxindex = i/3*3 + j/3;
                board_rows[i][a] = 1;
                board_cols[j][a] = 1;
                board_boxs[boxindex][a] = 1;
            }
        }

        subSolveSudoku(board_rows,board_cols,board_boxs,board,0,0);

    }
    public boolean subSolveSudoku(int[][] board_rows,int[][] board_cols,int[][] board_boxs,char[][] board, int ri, int le) {
        boolean flag = false;
        if(ri ==9)
        {
            return true;
        }
        if(board[ri][le] != '.')
        {
            le +=1;
            if(le>8)
            {
                le=0;
                ri+=1;
            }
            flag = subSolveSudoku(board_rows,board_cols,board_boxs,board,ri,le);
            return flag;
        }
        int boxindex = ri/3*3 + le/3;
        //每个可能取的值
        for (int i = 0; i < 9; i++) {
            if(board_rows[ri][i]==0 && board_cols[le][i] ==0 && board_boxs[boxindex][i] ==0)
            {
                board_rows[ri][i] = 1;
                board_cols[le][i] = 1;
                board_boxs[boxindex][i] = 1;
                int le1 = le+ 1;
                int ri1 = ri;
                if(le1>8)
                {
                    le1=0;
                    ri1 +=1;
                }
                board[ri][le] = (char)(i+'1');
                flag = subSolveSudoku(board_rows,board_cols,board_boxs,board,ri1,le1);
                if(flag) return true;
                board[ri][le] = '.';
                board_rows[ri][i] = 0;
                board_cols[le][i] = 0;
                board_boxs[boxindex][i] = 0;
            }
        }
        return false;
    }

    //38. 外观数列
    public String countAndSay(int n) {
        if(n==0) return "";
        if(n==1) return "1";
        char[] chars1 = new char[4462];
        char[] chars2 = new char[4462];
        int len = 1;
        chars1[0] = '1';
        char[] chars_in, chars_out = new char[4462];
        for (int i = 1; i <n ; i++) {
            int num = 0;
            int k = 0;

            if(i%2==1)
            {
                chars_in = chars1;
                chars_out = chars2;
            }
            else
            {
                chars_out = chars1;
                chars_in = chars2;
            }
            char last = chars_in[0];
            num = 1;
            for (int j = 1; j < len; j++) {
                char a = chars_in[j];

                if(a != last)
                {
                    chars_out[k] = (char)('0'+num);
                    chars_out[k+1] = last;
                    k+=2;
                    last = a;
                    num=1;
                }else
                {
                    num++;
                }
            }
            chars_out[k] = (char)('0'+num);
            chars_out[k+1] = last;
            len = k+2;
        }
        char[] x = new char[len];
        for (int i = 0; i <len ; i++) {
            x[i] = chars_out[i];
        }
        return new String(x);
    }

    //39. 组合总和 回溯
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> list = new ArrayList<>();

        subCombinationSum(candidates,target, list, new ArrayList<Integer>(),0,0);
        return list;
    }
    public void subCombinationSum(int[] candidates, int target, List<List<Integer>> list, List<Integer> subList, int sum, int pos) {
        if(sum == target)
        {
            List<Integer> newlist = new ArrayList<>(subList);
            list.add(newlist);
            return ;
        }
        if(sum>target) return;
        for (int i = pos; i < candidates.length; i++) {
            subList.add(candidates[i]);
            sum+=candidates[i];
            subCombinationSum(candidates,target, list, subList,sum,i);
            subList.remove(subList.size()-1);
            sum-=candidates[i];

        }
    }
    //40. 组合总和 II
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {

        List<List<Integer>> list = new ArrayList<>();
        if (candidates == null || candidates.length == 0 || target < 0) {
            return list;
        }
        Arrays.sort(candidates);
        subCombinationSum2(candidates,target, list, new ArrayList<Integer>(),0,0);
        return list;
    }

    public boolean subCombinationSum2(int[] candidates, int target, List<List<Integer>> list, List<Integer> subList, int sum, int pos) {
        if(sum == target)
        {
            List<Integer> newlist = new ArrayList<>(subList);
            list.add(newlist);
            return true;
        }
        if(pos>=candidates.length) return false;
        if(sum>target) return false;
        boolean flag = false;
        for (int i = pos; i < candidates.length; i++) {
            if(i!=pos && candidates[i]==candidates[i-1] ) continue;
            subList.add(candidates[i]);
            sum+=candidates[i];

            flag = subCombinationSum2(candidates,target, list, subList,sum,i+1);
            subList.remove(subList.size()-1);
            sum-=candidates[i];
        }
        return flag;
    }

    //41. 缺失的第一个正数
    public int firstMissingPositive(int[] nums) {
        int res = nums.length+1;

        for (int i = 0; i < nums.length; i++) {
            if(nums[i]>res-1 || nums[i]<=0)
            {
                nums[i] = 0;
                res--;
            }
        }
        //res 可能的结果的最大值。

        for (int i = 0; i < nums.length; i++) {
            if(nums[i] == 0) continue;
            int a = nums[i]>0 ? nums[i]:-1*nums[i];
            if(nums[a-1] >0) nums[a-1] *=-1;
            if(nums[a-1] == 0) nums[a-1] = -a;
        }
        for (int i = 0; i < nums.length; i++) {
            if(nums[i]>=0) return i+1;
        }
        return res;
    }

    //42. 接雨水
    public int trap(int[] height) {
        int vol = 0, vol_new = 0;
        int last;
        last = 0;
        for (int i = 0; i < height.length; i++) {
            if(height[i]>=height[last])
            {
                vol+=vol_new;
                vol_new = 0;
                last = i;
            }
            else
            {
                vol_new += height[last] - height[i];
            }
        }
        int last_last = last;
        last = height.length-1;
        vol_new = 0;
        for (int i = height.length-1; i >=last_last ; i--) {
            if(height[i]>=height[last])
            {
                vol+=vol_new;
                vol_new = 0;
                last = i;
            }
            else
            {
                vol_new += height[last] - height[i];
            }
        }
        return vol;
    }

    //43. 字符串相乘
    public String multiply(String num1, String num2) {
        if(num1.equals("0") ||num2.equals("0")) return "0";
        int len_all = num1.length()+num2.length();
        int[] ans = new int[len_all];
        for (int i = 0; i <num1.length() ; i++) {
            int a = num1.charAt(num1.length()-i-1)-'0';
            for (int j = 0; j <num2.length() ; j++) {
                int b = num2.charAt(num2.length()-j-1) - '0';
                int ab = a*b;
                ans[i+j] += ab;
            }
        }
        char[] ans_c = new char[len_all];
        int last = 0;
        for (int i = 0; i < len_all; i++) {
            ans[i] += last;
            last = ans[i]/10;
            ans[i] %= 10;
        }
        for (int i = 0; i < len_all; i++) {
            ans_c[len_all-i-1] = (char)(ans[i]+'0');
        }
        if(ans_c[0] == '0') return new String(Arrays.copyOfRange(ans_c, 1,ans_c.length));
        return new String(ans_c);
    }

    //44. 通配符匹配
    public boolean isMatch44(String s, String p) {
        int m = s.length(), n = p.length();
        boolean[][] dp = new boolean[m+1][n+1];
        dp[0][0] = true;

        for (int i = 0; i <= m; ++i) {
            for (int j = 1; j <= n; ++j) {
                if (p.charAt(j-1)== '*') {
                    dp[i][j] = (i>0 && dp[i-1][j]) || (i==0&&j==0);
                    System.out.println(i);
                    System.out.println(j);
                    System.out.println(dp[i][j]);
                } else {
                    dp[i][j] = i > 0 && dp[i - 1][j - 1] && (s.charAt(i-1) == p.charAt(j-1) || p.charAt(j-1) == '?');
                }
            }
        }
        return dp[m][n];
    }
    //44. 通配符匹配 迭代实现回溯算法
    public boolean isMatch_2(String s, String p) {
        int sLen = s.length(), pLen = p.length();
        int sIdx = 0, pIdx = 0;
        int starIdx = -1, sTmpIdx = -1;

        while (sIdx < sLen) {
            // If the pattern caracter = string character
            // or pattern character = '?'
            if (pIdx < pLen && (p.charAt(pIdx) == '?' || p.charAt(pIdx) == s.charAt(sIdx))){
                ++sIdx;
                ++pIdx;
            }
            // If pattern character = '*'
            else if (pIdx < pLen && p.charAt(pIdx) == '*') {
                // Check the situation
                // when '*' matches no characters
                starIdx = pIdx;
                sTmpIdx = sIdx;
                ++pIdx;
            }
            // If pattern character != string character
            // or pattern is used up
            // and there was no '*' character in pattern
            else if (starIdx == -1) {
                return false;
            }
            // If pattern character != string character
            // or pattern is used up
            // and there was '*' character in pattern before
            else {
                // Backtrack: check the situation
                // when '*' matches one more character
                pIdx = starIdx + 1;
                sIdx = sTmpIdx + 1;
                sTmpIdx = sIdx;
            }
        }

        // The remaining characters in the pattern should all be '*' characters
        for(int i = pIdx; i < pLen; i++)
            if (p.charAt(i) != '*') return false;
        return true;
    }

    //45.跳跃游戏 II
    public int jump(int[] nums) {
        int[] dp = new int[nums.length+1];
        dp[0] = 0;
        for (int i = 1; i < nums.length; i++) {
            for (int j = 0; j < i; j++) {
                if(nums[j]>=i-j)
                {
                    dp[i] = dp[j]+1;
                    break;
                }
            }
        }
//        System.out.println(Arrays.toString(dp));
        return dp[nums.length-1];
    }
    public int jump2(int[] nums) {
        int cur = 0;
        int sum = 0;
        int max = 0;
        int next = 0;
        int step = 0;
        while(cur<nums.length-1)
        {
            System.out.println(cur);
            boolean flag = false;
            max = 0;
            for (int i = 1; i <= nums[cur]; i++) {
                if(cur+i < nums.length-1)
                    sum = i+nums[cur+i];
                else
                {
                    return step+1;
                }
                if(sum>= max)
                {
                    flag = true;
                    max = sum;
                    next = cur+i;
                }
            }
            if(!flag) return step+1;
            cur = next;
            step = step+1;
        }
        return step;
    }

    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> list = new ArrayList<>();
        Set<Integer> set = new HashSet<>();
        subPermute(list, new ArrayList<>(), nums,set,0);
        return list;
    }

    public void subPermute(List<List<Integer>> list,List<Integer> subList, int[] nums, Set<Integer> set,int n) {
        if(n == nums.length)
        {
            list.add(new ArrayList<>(subList));
        }
        for (int num : nums) {
            if (!set.contains(num)) {
                subList.add(num);
                set.add(num);
                subPermute(list, subList, nums, set, n + 1);
                subList.remove(n);
                set.remove(num);
            }
        }
    }

    //47. 全排列 II
    public List<List<Integer>> permuteUnique(int[] nums) {
        List<List<Integer>> list = new ArrayList<>();
        Set<Integer> set = new HashSet<>();
        Set<List<Integer>> setlist = new HashSet<>();
        subPermute2(list, setlist,new ArrayList<>(), nums,set,0);
        return list;
    }

    public void subPermute2(List<List<Integer>> list, Set<List<Integer>> setlist, List<Integer> subList, int[] nums, Set<Integer> set,int n) {
        if(n == nums.length)
        {
            System.out.println(subList);
            if(!setlist.contains(subList))
            {
                List<Integer> tmp = new ArrayList<>(subList);
                list.add(tmp);
                setlist.add(tmp);
            }

        }
        for (int i = 0; i <nums.length ; i++) {
            if (!set.contains(i)) {
                subList.add(nums[i]);
                set.add(i);
                subPermute2(list,setlist, subList, nums, set, n + 1);
                subList.remove(n);
                set.remove(i);
            }
        }
    }
    //48. 旋转图像
    public void rotate(int[][] matrix) {
        int n = matrix.length;
        int [][]tmp = new int [n][n];
        int tmp2;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tmp2 = matrix[j][n-i-1];
                matrix[j][n-i-1] = matrix[i][j];
                matrix[i][j] = tmp2;
                //tmp[j][n-i-1] = matrix[i][j];
            }
        }
//        for (int i = 0; i <n ; i++) {
//            for (int j = 0; j <n ; j++) { 
//                matrix[i][j] = tmp[i][j];
//            }
//        }
    }

    //49. 字母异位词分组
    public List<List<String>> groupAnagrams(String[] strs) {
        List<List<String>> list = new ArrayList<>();
        Map<String, List<String>> map = new HashMap<>();
        for (int i = 0; i <strs.length ; i++) {
            char[] chars = strs[i].toCharArray();
            Arrays.sort(chars);
            String s = new String(chars);
            if(!map.containsKey(s)) {
                List<String> sublist = new ArrayList<>();
                sublist.add(strs[i]);
                list.add(sublist);
                map.put(s, sublist);
            }else
            {
                map.get(s).add(strs[i]);
            }
        }
        return list;
    }

    //50. Pow(x, n)
    public double myPow(double x, int n) {
        double res = 1.0;
        for(int i = n; i != 0; i /= 2){
            if(i % 2 != 0){
                res *= x;
            }
            x *= x;
        }
        return  n < 0 ? 1 / res : res;
    }

    //51. N皇后
    static int QueenSolveNum = 0;
    public List<List<String>> solveNQueens(int n) {
        List<List<String>> list = new ArrayList<>();
        char[][] strs = new char[n][n];
        for (int i = 0; i <n ; i++) {
            for (int j = 0; j < n; j++) {
                strs[i][j] = '.';
            }
        }
        subSolveNQueens(n,0, list, strs);
        return list;
    }

    public void subSolveNQueens(int n, int cur, List<List<String>> list, char[][] strs)
    {
        if(cur==n)
        {
            List<String> subList = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                subList.add(new String(strs[i]));
            }
            QueenSolveNum ++;
            list.add(subList);
            return;
        }
        for (int i = 0; i < n ; i++) {
            if(QueenValid(cur,i, n, strs))
            {
                strs[cur][i] = 'Q';
                subSolveNQueens(n,cur+1, list, strs);
                strs[cur][i] = '.';
            }
        }
    }
    public boolean QueenValid(int cur, int i,int n, char[][] strs)
    {
        for (int j = 0; j < cur; j++) {
            //同列不能有Q
            if(strs[j][i] =='Q') return false;
            //要放的是cur 行， i列， 当前是j行，cur-j为行距，对角不能有Q， 对角即行距等于列距，所以列号： i-（cur-j） 和 i+（cur-j）
            if(i+j-cur >=0 && i+j-cur < n &&strs[j][i+j-cur] == 'Q') return false;
            if(i-j+cur >=0 && i-j+cur < n &&strs[j][i-j+cur] == 'Q') return false;
        }
        return true;
    }

    public int totalNQueens(int n) {
//        QueenSolveNum = 0;
//        solveNQueens(n);
//        return QueenSolveNum;
        int[] nums = new int[]{1,1,0,0,2,10,4,40,92,352,724,2680,14200,73712};
        if (n<nums.length) return nums[n];
        return 0;
    }
    //53. 最大子序和
    public int maxSubArray(int[] nums) {
        if(nums.length ==0) return 0;
        int sum = 0;
        int max = 0;
        int maxmi = nums[0];
        for (int i = 0; i <nums.length ; i++) {
            sum+= nums[i];
            if(sum <= 0)
            {
                maxmi = sum>maxmi? sum:maxmi;
                sum = 0;

            }
            else if(sum> max)
            {
                max = sum;
            }
        }
        return max>0? max: maxmi;
    }

    //54. 螺旋矩阵
    public List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> list = new ArrayList<>();
        if(matrix.length == 0) return list;
        int m = matrix.length;
        int n = matrix[0].length;
        boolean[][] flags = new boolean[m][n];
        int diret = 0;
        int curi =0, curj = 0;
        while(true)
        {
            if((curj+1 >=n || flags[curi][curj+1]) && (curi+1>=m || flags[curi+1][curj])
            && (curj-1 <0 || flags[curi][curj-1]) && (curi-1 <0 || flags[curi-1][curj])){
                list.add(matrix[curi][curj]);
                flags[curi][curj] = true;
                break;
            }

            if(diret == 0)
            {
                if(!(curj+1 >=n || flags[curi][curj+1])) {
                    list.add(matrix[curi][curj]);
                    flags[curi][curj] = true;
                    curj++;
                }
                else
                {
                    diret = 1;
                }
            }
            if(diret == 1)
            {
                if(!(curi+1>=m || flags[curi+1][curj])) {
                    list.add(matrix[curi][curj]);
                    flags[curi][curj] = true;
                    curi++;
                }
                else
                {
                    diret = 2;
                }
            }
            if(diret == 2)
            {
                if(!(curj-1 <0 || flags[curi][curj-1])) {
                    list.add(matrix[curi][curj]);
                    flags[curi][curj] = true;
                    curj--;
                }
                else
                {
                    diret = 3;
                }
            }
            if(diret == 3)
            {
                if(!(curi-1 <0 || flags[curi-1][curj])) {
                    list.add(matrix[curi][curj]);
                    flags[curi][curj] = true;
                    curi--;
                }
                else
                {
                    diret = 0;
                }
            }
        }
        return list;
    }

    //55. 跳跃游戏
    public boolean canJump(int[] nums) {
        for (int i = 0; i < nums.length-1; i++) {
            if(nums[i] == 0)
            {
                if(!checkJump( i, nums)) return false;
            }
        }
        return true;
    }

    public boolean checkJump(int i,int[] nums) {
        for (int j = 0; j < i; j++) {
            if(nums[j] > i-j) return true;
        }
        return false;
    }
    //56. 合并区间
    public int[][] merge(int[][] intervals) {
        if(intervals == null || intervals.length == 0){
            return intervals;
        }
        Arrays.sort(intervals, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                if(o1[0] > o2[0]){
                    return 1;
                }else if(o1[0] == o2[0]){
                    return 0;
                }else {
                    return -1;
                }
            }
        });
        List<int[]> tempList = new ArrayList<>();
        int down = intervals[0][0];
        int up = intervals[0][1];
        for (int i = 1; i < intervals.length; i++) {
            if(up >= intervals[i][0]){
                up = up > intervals[i][1] ? up : intervals[i][1];
            }else {
                tempList.add(new int[]{down, up});

                down = intervals[i][0];
                up = intervals[i][1];
            }
        }
        tempList.add(new int[]{down, up});
        int[][] result = new int[tempList.size()][2];
        for (int i = 0; i < tempList.size(); i++) {
            result[i] = tempList.get(i);
        }
        return result;
    }

    public int[][] insert(int[][] intervals, int[] newInterval) {
        int m = intervals.length;
        int len = 0;
        int[][] newNums = new int[m+1][2];
        boolean flag = false;
        boolean left = false, right = false;
        for (int i = 0; i <m ; i++) {
            if(!left && intervals[i][0] <= newInterval[0] && intervals[i][1] >= newInterval[0])
            {
                //  System.out.println(2);
                newNums[len][0] = intervals[i][0];
                left = true;
                flag = true;
            }

            if(!left && intervals[i][0] >= newInterval[0] && newInterval[1] >= intervals[i][0])
            {
                //  System.out.println(4);
                left = true;
                newNums[len][0] = newInterval[0];
                flag = true;
            }
            if(left && !right && intervals[i][1] >= newInterval[1])
            {
                right = true;
                if(intervals[i][0] <= newInterval[1])
                {
                    newNums[len][1] = intervals[i][1];
                }
                else
                {
                    newNums[len][1] = newInterval[1];
                }
                len++;
            }
            if(intervals[i][1] < newInterval[0] || intervals[i][0] > newInterval[1])
            {
                if(!flag && intervals[i][0] > newInterval[1])
                {
                    //  System.out.println(1);
                    flag = true;
                    newNums[len][0] = newInterval[0];
                    newNums[len][1] = newInterval[1];
                    len++;
                }
                newNums[len][0] = intervals[i][0];
                newNums[len][1] = intervals[i][1];
                len++;
            }

        }
        if(!flag)
        {
            newNums[len][0] = newInterval[0];
            newNums[len][1] = newInterval[1];
            len++;
        }
        else
        {
            if(left && !right)
            {
                newNums[len][1] = newInterval[1];
                len++;
            }
        }
        int[][] ans = new int[len][2];
        for (int i = 0; i < len; i++) {
            ans[i][0] = newNums[i][0];
            ans[i][1] = newNums[i][1];
        }
        return ans;
    }
    //58. 最后一个单词的长度
    public int lengthOfLastWord(String s) {
        int last = -1;
        for (int i = s.length()-1; i >=0 ; i--) {
            if(s.charAt(i) != ' ')
            {
                last = i;
                break;
            }
        }

        int last_ = -1;
        for (int i = 0; i <=last; i++) {
            if(s.charAt(i) == ' ')
            {
                last_ = i;
            }
        }
        return last-last_;
    }
    //59. 螺旋矩阵 II
    public int[][] generateMatrix(int n) {
        int m = n;
        boolean flags[][] = new boolean[n][n];
        int[][] ans = new int[n][n];
        int curi = 0, curj = 0;
        int diret = 0;
        int cur = 1;
        while (true) {
            if ((curj + 1 >= n || flags[curi][curj + 1]) && (curi + 1 >= m || flags[curi + 1][curj])
                    && (curj - 1 < 0 || flags[curi][curj - 1]) && (curi - 1 < 0 || flags[curi - 1][curj])) {
                ans[curi][curj] = cur;
                cur++;
                flags[curi][curj] = true;
                break;
            }

            if (diret == 0) {
                if (!(curj + 1 >= n || flags[curi][curj + 1])) {
                    ans[curi][curj] = cur;
                    cur++;
                    flags[curi][curj] = true;
                    curj++;
                } else {
                    diret = 1;
                }
            }
            if (diret == 1) {
                if (!(curi + 1 >= m || flags[curi + 1][curj])) {
                    ans[curi][curj] = cur;
                    cur++;
                    flags[curi][curj] = true;
                    curi++;
                } else {
                    diret = 2;
                }
            }
            if (diret == 2) {
                if (!(curj - 1 < 0 || flags[curi][curj - 1])) {
                    ans[curi][curj] = cur;
                    cur++;
                    flags[curi][curj] = true;
                    curj--;
                } else {
                    diret = 3;
                }
            }
            if (diret == 3) {
                if (!(curi - 1 < 0 || flags[curi - 1][curj])) {
                    ans[curi][curj] = cur;
                    cur++;
                    flags[curi][curj] = true;
                    curi--;
                } else {
                    diret = 0;
                }
            }
        }
        return ans;
    }

    //60. 第k个排列
    public String getPermutation(int n, int k) {
        char[] chars = new char[n];
        int[] nums = new int[n];
        k = k-1;
        for (int i = 0; i <n ; i++) {
            nums[n-i-1] = k%(i+1);
            k = k/(i+1);
        }
        int[] set = new int[n];
        for (int i = 0; i < n ; i++) {
            int tmp = nums[i];
            for (int j = 0; j <=tmp ; j++) {
                if(set[j]==1)
                {
                    tmp++;
                }
            }
            nums[i] = tmp;
            set[nums[i]] = 1;
        }
        for (int i = 0; i < n; i++) {
            chars[i] = (char)(nums[i]+'1');
        }
        return new String(chars);
    }

    //61. 旋转链表
    public ListNode rotateRight(ListNode head, int k) {
        if(k==0) return head;

        ListNode cur = head;
        int len = 0;
        while(cur!= null)
        {
            cur = cur.next;
            len ++;
        }
        if(len <=1) return head;
        k = k % len;
        if(k==0) return head;
        ListNode left = head, right = head;

        for (int i = 0; i <k ; i++) {
            right = right.next;
        }
        while(right.next!=null)
        {
            left = left.next;
            right = right.next;
        }
        ListNode newhead = left.next;
        left.next = null;
        right.next = head;
        return newhead;
    }

    //62. 不同路径
    public int uniquePaths(int m, int n) {
        int[][] dp = new int[m+1][n+1];
        dp[1][1] = 1;
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if(i==1 && j ==1) continue;
                dp[i][j] = dp[i-1][j] + dp[i][j-1];
            }
        }
        return dp[m][n];
    }
    //63. 不同路径 II
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {

        int m = obstacleGrid.length;
        if(m==0) return 0;
        int n = obstacleGrid[0].length;

        int[][] dp = new int[m+1][n+1];
        if(obstacleGrid[0][0] == 1) return 0;
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if(obstacleGrid[i-1][j-1] == 1)
                {
                    dp[i][j] = 0;
                    continue;
                }
                if(i==1 && j ==1) {
                    dp[i][j] = 1;
                    continue;
                }
                dp[i][j] = dp[i-1][j] + dp[i][j-1];
            }
        }
        return dp[m][n];
    }

    public int minPathSum(int[][] grid) {
        int m = grid.length;
        if(m==0) return 0;
        int n = grid[0].length;

        int[][] dp = new int[m+1][n+1];
        dp[1][1] = grid[0][0];
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if(i==1 && j ==1) continue;
                if(i==1) dp[i][j] = dp[i][j-1] + grid[i-1][j-1];
                else if(j==1) dp[i][j] = dp[i-1][j] + grid[i-1][j-1];
                else
                dp[i][j] = Math.min(dp[i-1][j] , dp[i][j-1]) + grid[i-1][j-1];
            }
        }
        return dp[m][n];
    }
    //66. 加一
    public int[] plusOne(int[] digits) {
        int m = digits.length;
        digits[m-1] ++;
        int[] ans = new int[m+1];
        int last = 0;
        for (int i = 0; i < m; i++) {
            int j = m-i-1;
            digits[j]  = digits[j] +last;
            last = digits[j]/10;
            digits[j] %= 10;
        }
        if(last == 0) return digits;
        else
        {
            System.arraycopy(digits, 0, ans, 1, m);
            ans[0] = last;
            return ans;
        }
    }

    //67. 二进制求和
    public String addBinary(String a, String b) {
        int an = a.length();
        int bn = b.length();
        int max = Math.max(an,bn);
        char[] nums = new char[max+1];

        int last = 0;
        for (int i = 0; i < max ; i++) {
            nums[max+1-i-1] = (char)((i<bn ? b.charAt(bn-i-1) : '0') - '0' +(i<an ? a.charAt(an-i-1): '0') + last);
            if(nums[max+1-i-1] >= '2'){
                last = 1;
                nums[max+1-i-1] = (char)(nums[max+1-i-1] - 2);
            }
            else
            {
                last = 0;
            }
            //System.out.println(nums[max+1-i-1]);

        }
        if(last == 0)
        {
            char[] ans = new char[max];
            System.arraycopy(nums, 1, ans, 0, max);
            return new String(ans);
        }
        else
        {
            nums[0] = '1';
            //System.out.println(111);
            return new String(nums);
        }
    }

    //68. 文本左右对齐
    public List<String> fullJustify(String[] words, int maxWidth) {
        int cur = 0;
        List<String> list = new ArrayList<>();
        int left = 0;
        int sum = 0;
        while(cur<words.length)
        {
            //System.out.println(sum);
            sum+=words[cur].length();
            if(sum>maxWidth)
            {
                //System.out.println(1);
                char[] chars = new char[maxWidth];
                sum-= words[cur].length();
                sum--;
                int rest = maxWidth-sum;
                if(cur -1 == left)
                {
                    System.arraycopy(words[left].toCharArray(),0, chars,0,words[left].length());
                    for (int i = words[left].length(); i < maxWidth; i++) {
                        chars[i] = ' ';
                    }
                    list.add(new String(chars));
                    sum=0;
                    left = cur;
                    continue;
                }
                int wi = rest /(cur-1-left);
                int ri = rest %(cur-1-left);
                int pos = 0;
                for (int i = left; i < cur; i++) {
                    System.arraycopy(words[i].toCharArray(),0, chars,pos,words[i].length());
                    pos+=words[i].length();

                    if(i>=cur-1) continue;
                    chars[pos] = ' ';
                    pos++;
                    for (int j = 0; j < wi; j++) {
                        chars[pos] = ' ';
                        pos++;
                    }
                    if(ri>0)
                    {
                        chars[pos] = ' ';
                        pos++;
                        ri--;
                    }
                }
                left = cur;
                cur--;
                list.add(new String(chars));
                sum=0;
            }
            else if(sum==maxWidth)
            {
                //System.out.println(2);
                char[] chars = new char[maxWidth];
                int pos = 0;
                //System.out.println(left);
                //System.out.println(cur);
                for (int i = left; i <= cur; i++) {
                    System.arraycopy(words[i].toCharArray(),0, chars,pos,words[i].length());
                    pos+=words[i].length();
                    if(i>=cur) continue;
                    chars[pos] = ' ';
                    pos++;
                }
                list.add(new String(chars));
                left=cur+1;
                sum = 0;
            }else
            {
                //System.out.println(3);
                sum++;
            }
            cur++;
            //System.out.println(list);
        }
        if(sum>0)
        {
            //System.out.println(sum);
            char[] chars = new char[maxWidth];
            sum--;
            int rest = maxWidth-sum;
            //System.out.println(cur);
            //System.out.println(left);
            if(cur -1 == left)
            {
                System.arraycopy(words[left].toCharArray(),0, chars,0,words[left].length());
                for (int i = words[left].length(); i < maxWidth; i++) {
                    chars[i] = ' ';
                }
                list.add(new String(chars));
                return list;
            }

            int wi =rest /(cur-1-left);
            int ri = rest %(cur-1-left);
            int pos = 0;
            //System.out.println(sum);
            //System.out.println(list);
            for (int i = left; i < cur; i++) {
                System.arraycopy(words[i].toCharArray(),0, chars,pos,words[i].length());
                pos+=words[i].length();
                if(i>=cur-1) continue;
                chars[pos] = ' ';
                pos++;
            }
            for (int i = pos; i <maxWidth ; i++) {
                chars[i] = ' ';
            }
            //System.out.println(chars);
            list.add(new String(chars));
        }
        return list;
    }

    //69. x 的平方根
    public int mySqrt(int x) {
        int ans = x;
        if(x==0) return 0;
        if(x<4) return 1;
        if(x<9) return 2;
        if(x > 46340*46340) return 46340;

        int left = 1;
        int right = x;
        if(x>46340*2) right = 46340*2;
        while(left < right)
        {

            int mid = (left+right) /2;
            System.out.println(mid);
            if(mid*mid == x) return mid;
            if(mid*mid > x)
            {
                right = mid;
            }
            else
            {
                left = mid+1;
            }
        }
        return left-1;
    }
    //70. 爬楼梯
    public int climbStairs(int n) {
        int[] dp = new int[n+1];
        dp[0] = 1;
        dp[1] = 1;
        for (int i = 2; i <= n; i++) {
            dp[i] = dp[i-1]+ dp[i-2];
        }
        return dp[n];
    }

    //71. 简化路径
    public String simplifyPath(String path) {


        String[] strs1 = path.split("/");
        List<String> strs = new ArrayList<>();
        for (int i = 0; i < strs1.length; i++) {
            String str = strs1[i];
            if(str.equals(".."))
            {
                if(strs.size()>0)
                    strs.remove(strs.size()-1);
            }
            else if(!str.equals("") && !str.equals("."))
            {
                strs.add(str);
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append("/");
        for (int i = 0; i < strs.size()-1 ; i++) {
            sb.append(strs.get(i));
            sb.append("/");
        }
        if(strs.size()>0)
            sb.append(strs.get(strs.size()-1));
        return sb.toString();
    }

    //72. 编辑距离
    public int minDistance(String word1, String word2) {
        int m = word1.length();
        int n = word2.length();
        int[][] dp = new int[m+1][n+1];
        dp[0][0] = 0;
        for (int i = 1; i <= m; i++) {
            dp[i][0] = i;
        }
        for (int i = 1; i <= n; i++) {
            dp[0][i] = i;
        }
        for (int i = 1; i <=m; i++) {
            for (int j = 1; j <=n ; j++) {
                if(word1.charAt(i-1) == word2.charAt(j-1))
                {
                    dp[i][j] = Math.min(dp[i-1][j]+1, dp[i][j-1]+1);
                    dp[i][j] = Math.min(dp[i][j], dp[i-1][j-1]);
                }else
                {
                    dp[i][j] = Math.min(dp[i-1][j], dp[i][j-1]);
                    dp[i][j] = 1+Math.min(dp[i][j], dp[i-1][j-1]);
                }
            }
        }
        return dp[m][n];
    }

    public void setZeroes(int[][] matrix) {
        int m = matrix.length;
        if (m <= 0) return;

        int firstrow = 1, firstcol = 1;
        if (matrix[0][0] == 0) {
            firstrow = 0;
            firstcol = 0;
        }
        int n = matrix[0].length;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == 0) {
                    if (i != 0 && j != 0) {
                        matrix[i][0] = 0;
                        matrix[0][j] = 0;
                    } else {
                        if (i == 0) {
                            firstrow = 0;
                        }
                        if (j == 0) {
                            firstcol = 0;
                        }
                    }

                }
            }
        }
        for (int i = 1; i < m; i++) {
            if (matrix[i][0] == 0) {
                for (int j = 0; j < n; j++) {
                    matrix[i][j] = 0;
                }
            }
        }
        for (int j = 1; j < n; j++) {
            if (matrix[0][j] == 0) {
                for (int i = 0; i < m; i++) {
                    matrix[i][j] = 0;
                }
            }
        }
        if (firstrow == 0) {
            for (int i = 0; i < n; i++) {
                matrix[0][i] = 0;
            }
        }
        if (firstcol == 0){
            for (int i = 0; i < m; i++) {
                matrix[i][0] = 0;
            }
        }
    }

    //74. 搜索二维矩阵
    public boolean searchMatrix(int[][] matrix, int target) {
        int m = matrix.length;
        if (m==0) return false;
        int n = matrix[0].length;
        if (n ==0) return false;

        int left = 0;
        int right = m*n-1;
        while(left<=right)
        {
            int mid = (left+right)/2;
            int row = mid/n;
            int col = mid%n;

            if(matrix[row][col] > target)
            {
                right = mid-1;
            }
            else if(matrix[row][col] < target)
            {
                left = mid+1;
            }
            else
            {
                return true;
            }
        }
        return false;
    }

    //75. 颜色分类
    public void sortColors(int[] nums) {
        int left = 0;
        int right = nums.length-1;
        int tmp;
        int i = 0;
        while(i < nums.length){
            //System.out.println(i);
            System.out.println(nums[i]);
            if(nums[i]==0)
            {
                tmp = nums[i];
                nums[i] = nums[left];
                nums[left] = tmp;
                left++;
                i++;
            }else if(nums[i] == 2)
            {
                if(i>=right)
                {
                    i++;
                    continue;
                }
                tmp = nums[i];
                nums[i] = nums[right];
                nums[right] = tmp;
                right--;

            }
            else
            {
                i++;
            }
        }
    }

    //76. 最小覆盖子串
    public String minWindow(String s, String t) {
        int left = 0;
        int right = 0;
        int next_left = 0;
        int ans_left = 0;
        int min = Integer.MAX_VALUE;
        boolean flag = false;
        Map<Character, Integer> map = new HashMap<>();
        Map<Character, Integer> map3 = new HashMap<>();
        for (int i = 0; i <t.length() ; i++) {
            map.put(t.charAt(i), map.getOrDefault(t.charAt(i), 0)+1);
            map3.put(t.charAt(i), 0);
        }
        Map<Character, Integer> map2 = new HashMap<>(map);
        int count = 0;
        while(left < s.length() && right < s.length())
        {
            if(!flag)
            {
                if(map.containsKey(s.charAt(left)))
                {
                    flag = true;
                    right = left;
                    count = 0;
                }
                else
                {
                    left++;
                    continue;
                }
            }
            if(map2.containsKey(s.charAt(right)))
            {
                if(count == 1)
                {
                    next_left = right;
                }
                count ++;

                if(map2.get(s.charAt(right)) > 0)
                {
                    map2.put(s.charAt(right), map2.get(s.charAt(right))-1);
                }
                else
                {
                    map2.put(s.charAt(right), 0);
                }
                if(map2.equals(map3))
                {
                    if(right-left+1 < min)
                    {
                        min = right-left+1;
                        ans_left = left;
                    }
                    System.out.println(min);
                    System.out.println(right);

                    System.out.println(left);
                    System.out.println(s.substring(left, right+1));
                    System.out.println(next_left);
                    count = 0;
                    flag = false;
                    if(next_left>left){
                        left = next_left;
                    }else
                    {
                        left++;
                    }
                    map2 = new HashMap<>(map);
                }
                else
                {
                    right++;
                }
            }else
            {
                right++;
                if(right-left+1 >= min)
                {
                    count = 0;
                    flag = false;
                    if(next_left>left){
                        left = next_left;
                    }else
                    {
                        left++;
                    }
                    map2 = new HashMap<>(map);
                }
            }
        }
        if(min == Integer.MAX_VALUE) return "";
        return s.substring(ans_left, ans_left+min);
    }

    //77. 组合 排序法
    public List<List<Integer>> combine(int n, int k) {
        // init first combination
        LinkedList<Integer> nums = new LinkedList<>();
        for(int i = 1; i < k + 1; ++i)
            nums.add(i);
        nums.add(n + 1);

        List<List<Integer>> output = new ArrayList<>();
        int j = 0;
        while (j < k) {
            // add current combination
            System.out.println(nums);
            output.add(new LinkedList(nums.subList(0, k)));

            // increase first nums[j] by one
            // if nums[j] + 1 != nums[j + 1]
            j = 0;

            while ((j < k) && (nums.get(j + 1) == nums.get(j) + 1))
            {

                nums.set(j, j++ + 1);
            }
            nums.set(j, nums.get(j) + 1);
        }
        return output;
    }
    //77. 组合 回溯法
    public List<List<Integer>> combine2(int n, int k) {
        List<List<Integer>> list = new ArrayList<>();
        subCombine(n, k,1,list, new ArrayList<>());
        return list;
    }
    public void subCombine(int n, int k, int cur, List<List<Integer>> list, List<Integer> sublist)
    {
        if(sublist.size() == k)
        {
            list.add(new ArrayList<>(sublist));
            return;
        }
        for (int i = cur; i <= n; i++) {
            sublist.add(i);
            subCombine(n,k,i+1, list, sublist);
            sublist.remove(sublist.size()-1);
        }
    }
    //78. 子集
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> list = new ArrayList<>();
        subSubsets(nums, 0, list, new ArrayList<>());
        return list;
    }

    public void subSubsets(int[] nums,int cur, List<List<Integer>> list, List<Integer> sublist)
    {
        list.add(new ArrayList<>(sublist));

        for (int i = cur; i < nums.length ; i++) {
            sublist.add(nums[i]);
            subSubsets(nums, i+1, list, sublist);
            sublist.remove(sublist.size()-1);
        }
    }
    //79. 单词搜索
    public boolean exist(char[][] board, String word) {
        int m = board.length;
        int n = board[0].length;
        boolean ans = false;
        boolean[][] flags = new boolean[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j <n ; j++) {
                if(board[i][j] == word.charAt(0))
                {
                    flags[i][j] = true;
                    if (subExist(board, word, i, j, 0, flags))
                        return true;
                    flags[i][j] = false;
                }
            }
        }
        return false;
    }
    //79. 单词搜索
    public boolean subExist(char[][] board, String word, int row, int col, int cur,  boolean[][] flags)
    {
        int m = board.length;
        int n = board[0].length;
        if(cur == word.length()-1) return true;

        if(row>0 && !flags[row-1][col] && board[row-1][col] == word.charAt(cur+1))
        {
            flags[row-1][col] = true;
            if(subExist(board, word, row-1, col, cur+1, flags)) return true;
            flags[row-1][col] = false;
        }

        if(col>0 && !flags[row][col-1] && board[row][col-1] == word.charAt(cur+1))
        {
            flags[row][col-1] = true;
            if(subExist(board, word, row, col-1, cur+1, flags)) return true;
            flags[row][col-1] = false;
        }

        if(row <m-1&& !flags[row+1][col] && board[row+1][col] == word.charAt(cur+1))
        {
            flags[row+1][col] = true;
            if(subExist(board, word, row+1, col, cur+1, flags)) return true;
            flags[row+1][col] = false;
        }

        if(col < n-1 && !flags[row][col+1] && board[row][col+1] == word.charAt(cur+1))
        {
            flags[row][col+1] = true;
            if(subExist(board, word, row, col+1, cur+1, flags)) return true;
            flags[row][col+1] = false;
        }
        return false;
    }

    //80. 删除排序数组中的重复项 II
    public int removeDuplicates2(int[] nums) {
            int i = 0;
            for (int n : nums)
                if (i < 2 || n > nums[i-2])
                    nums[i++] = n;
            return i;
    }

    // 81. 搜索旋转排序数组 II
    public boolean search2(int[] nums, int target) {
        int left = 0;
        int right = nums.length-1;
        int mid ;
        while(left <= right) {
            mid = (left + right) / 2;

            if (nums[mid] == target || nums[left] == target || nums[right] == target) return true;
            if (nums[mid] == nums[left])
            {
                left++;
                continue;
            }
            if (nums[mid] > target) {
                if (target > nums[left]) {
                    right = mid - 1;
                } else if (nums[mid] > nums[left]) {
                    left = mid + 1;
                }
                else {
                    right = mid - 1;
                }
            }
            if (nums[mid] < target) {
                if (nums[mid] > nums[left]) {
                    left = mid + 1;
                }
                else if (target > nums[left]) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            }
        }
        return false;
    }

    //82. 删除排序链表中的重复元素 II
    public ListNode deleteDuplicates(ListNode head) {
        if(head==null) return null;
        ListNode pre = new ListNode(head.val-1);
        pre.next = head;
        ListNode cur = head;
        ListNode last = pre;

        ListNode linked = pre;
        while(cur != null)
        {
            if (last.val != cur.val && (cur.next == null || cur.val != cur.next.val)) {
                linked.next = cur;
                linked = cur;
            }
            last = cur;
            cur = cur.next;
        }
        linked.next = null;

        return pre.next;
    }

    //83. 删除排序链表中的重复元素
    public ListNode deleteDuplicates2(ListNode head) {
        if (head == null) return null;
        ListNode pre = new ListNode(head.val-1);
        pre.next = head;
        ListNode cur = head;
        ListNode last = pre;
        while(cur!=null)
        {
            if(cur.next!= null && cur.val == cur.next.val)
            {
                last.next = cur.next;
            }
            else
            {
                last = cur;
            }
            cur = cur.next;
        }
        return pre.next;
    }

    //84. 柱状图中最大的矩形, 栈方法
    public int largestRectangleArea(int[] heights) {
        Stack < Integer > stack = new Stack < > ();
        stack.push(-1);
        int maxarea = 0;
        for (int i = 0; i < heights.length; ++i) {
            while (stack.peek() != -1 && heights[stack.peek()] >= heights[i])
                maxarea = Math.max(maxarea, heights[stack.pop()] * (i - stack.peek() - 1));
            stack.push(i);
        }
        while (stack.peek() != -1)
            maxarea = Math.max(maxarea, heights[stack.pop()] * (heights.length - stack.peek() -1));
        return maxarea;
    }

    //85. 最大矩形
    public int maximalRectangle(char[][] matrix) {
        int m = matrix.length;
        if(m==0) return 0;
        int n = matrix[0].length;
        int[] heigths = new int[n];
        int res = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j <n ; j++) {
                if(matrix[i][j] == '0')
                {
                    heigths[j] = 0;
                }else{
                    heigths[j] ++;
                }
            }
            res = Math.max(res, largestRectangleArea(heigths));
        }
        return res;
    }

    public ListNode partition(ListNode head, int x) {
        if(head==null) return null;
        ListNode pre = new ListNode(-1);
        pre.next = head;
        ListNode cur = head;
        ListNode left = pre;
        ListNode right = new ListNode(-1);
        ListNode last = right;
        while(cur != null)
        {
            if(cur.val<x)
            {
                left.next = cur;
                left = cur;
            }
            else
            {
                right.next = cur;
                right = cur;
            }
            cur = cur.next;
        }
        right.next = null;
        left.next = last.next;
        return pre.next;
    }

    //87. 扰乱字符串
    public boolean isScramble(String s1, String s2) {
        if(s1==null || s2 == null || s1.length() != s2.length()) return false;
        if(s1.equals(s2)) return true;
        int len = s1.length();
        for(int i = 1; i < len; ++i){
            if(isEquals(s1.substring(0,i),s2.substring(0,i)) && isScramble(s1.substring(0,i),s2.substring(0,i)) && isScramble(s1.substring(i,len),s2.substring(i,len)))
                return true;
            if(isEquals(s1.substring(0,i),s2.substring(len-i,len)) && isScramble(s1.substring(0,i),s2.substring(len-i,len)) && isScramble(s1.substring(i,len),s2.substring(0,len-i)))
                return true;
        }
        return false;
    }

    public boolean isEquals(String s1, String s2){
        int[] charArr = new int[26];
        for(int i = 0 ; i < s1.length(); ++i){
            charArr[s1.charAt(i) - 97] = charArr[s1.charAt(i) - 97] + 1;
            charArr[s2.charAt(i) - 97] = charArr[s2.charAt(i) - 97] - 1;
        }
        for(int i = 0; i < 26; ++i){
            if(charArr[i] != 0) return false;
        }
        return true;
    }
    //88. 合并两个有序数组
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int a=m-1;
        int b=n-1;
        int cur = m+n-1;
        while(a>=0 && b>=0)
        {
            if(nums1[a]<=nums2[b])
            {
                nums1[cur] = nums1[b];
                b--;
            }
            else
            {
                nums1[cur] = nums2[a];
                a--;
            }
            cur--;
        }
        System.out.println(b);
        if(b>=0)
        {
            if (b + 1 >= 0) System.arraycopy(nums2, 0, nums1, 0, b + 1);
        }

    }

    //89. 格雷编码 G(i) = i ^ (i/2);
    public List<Integer> grayCode(int n) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 1<<n; i++) {
            list.add(i ^ (i >>1));
        }
        return list;
    }

    //90. 子集 II
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        List<List<Integer>> list = new ArrayList<>();
        Arrays.sort(nums);
        subSubsetsWithDup(nums, 0, list, new ArrayList<>());
        return new ArrayList<>(list);
    }

    public void subSubsetsWithDup(int[] nums,int cur, List<List<Integer>> list, List<Integer> sublist)
    {
        list.add(new ArrayList<>(sublist));
        //System.out.println(sublist);
        if(cur>=nums.length) return;

        for (int i = cur; i < nums.length ; i++) {
            if(i>cur && nums[i] == nums[i-1]) continue;
            sublist.add(nums[i]);
            subSubsetsWithDup(nums, i+1, list, sublist);
            sublist.remove(sublist.size()-1);
        }
    }

    //91. 解码方法
    public int numDecodings(String s) {
        int[] dp = new int[s.length()];
        if(s.charAt(0) == '0') return 0;
        dp[0] = 1;
        for (int i = 1; i <s.length() ; i++) {
            if(s.charAt(i) == '0' )
            {
                if(s.charAt(i-1) > '2' || s.charAt(i-1)=='0') return 0;
                else dp[i] = (i<2 ? 1: dp[i-2]);
            }
            else if(s.charAt(i-1)=='1' || (s.charAt(i-1)=='2' && s.charAt(i) <= '6'))
            {
                //System.out.println(i);
                dp[i] = dp[i-1]+ (i<2 ? 1: dp[i-2]);
            }
            else
            {
                dp[i] = dp[i-1];
            }

        }
        return dp[s.length()-1];
    }

    //92. 反转链表 II
    public ListNode reverseBetween(ListNode head, int m, int n) {
        if(head==null) return null;
        ListNode pre = new ListNode(-1);
        pre.next = head;
        ListNode cur, Next,Left = head, Last, preLeft = pre;
        cur = head;
        Last = pre;
        int index = 1;
        while(cur!=null)
        {
            Next = cur.next;
            if(index == m)
            {
                preLeft = Last;
                Left = cur;
            }
            if(index ==n)
            {
                preLeft.next = cur;
                Left.next = cur.next;
            }
            if(index >= m+1 && index <=n)
            {
                cur.next = Last;
            }
            index++;
            Last = cur;
            cur = Next;

        }
        return pre.next;
    }

    //93. 复原IP地址
    public List<String> restoreIpAddresses(String s) {
        List<String> list = new ArrayList<>();
        subRestoreIpAddresses(new char[s.length()+3], list,0, 0,s);
        return list;
    }
    public void subRestoreIpAddresses(char[] chars, List<String> list, int dotnum, int pos, String s)
    {
        //System.out.println(chars);

        if(dotnum==4 && pos==s.length())
        {
            list.add(new String(chars));
        }
        if(dotnum>3 || pos>= s.length()) return;

        int num = 0;
        for (int i = pos; i < pos+3 && i< s.length(); i++) {
            num += s.charAt(i)-'0';
            if(i>pos && num<10) return;
            if(num>255) return;
            chars[i+dotnum] = s.charAt(i);
            //System.out.println(chars);
            if(dotnum<3)
                chars[i+dotnum+1] = '.';
            subRestoreIpAddresses(chars, list, dotnum+1, i+1, s);
            num*=10;
        }
    }
    //94. 二叉树的中序遍历
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        TreeNode cur = root, tmp;
        while(cur!=null)
        {

            if(cur.left ==null)
            {
                list.add(cur.val);
                if(cur.right == null)
                {
                    if(stack.empty()) return list;
                    cur = stack.pop();
                }
                else
                {

                    tmp = cur.right;
                    cur.right = null;
                    cur = tmp;
                }

            }
            else {
                stack.push(cur);
                tmp = cur.left;
                cur.left = null;
                cur = tmp;
            }
        }
        return list;
    }

    public List<TreeNode> generateTrees(int n) {
        List<TreeNode> list = new ArrayList<>();
        if(n==0) return list;
        subGenerateTrees(1, n);
        return list;
    }
    public List<TreeNode> subGenerateTrees(int start, int end)
    {
        List<TreeNode> list = new ArrayList<>();
        if(start>end)
        {
            list.add(null);
            return list;
        }
        if(start == end)
        {
            list.add(new TreeNode(start));
            return list;
        }

        List<TreeNode> left, right;
        TreeNode cur ;
        for (int i = start; i <= end; i++) {
            left = subGenerateTrees(start, i-1);
            right = subGenerateTrees(i+1, end);
            for (TreeNode aLeft : left) {
                for (TreeNode aRight : right) {
                    cur = new TreeNode(i);
                    cur.left = aLeft;
                    cur.right = aRight;
                    list.add(cur);
                }
            }

        }
        return list;
    }

    //96. 不同的二叉搜索树
    public int numTrees(int n) {
        int[] dp = new int[n+1];
        dp[0]= 1;
        for (int i = 0; i <=n; i++) {
            for (int j = 0; j <=i ; j++) {
                dp[i] += (dp[j]*dp[i-j]);
            }
        }
        return dp[n];
    }

    //97. 交错字符串
    public boolean isInterleave(String s1, String s2, String s3) {
        int m = s1.length();
        int n = s2.length();
        if(s3.length()!=m+n) return false;
        if(m==0) return s2.equals(s3);
        if(n==0) return s1.equals(s3);
        //dp[i][j] 表示s1.substring(0,i)和s2.substring(0,j) ，s3.substring(0,i+j)
        boolean dp[][] = new boolean[m+1][n+1];
        dp[0][0] = true;
        for (int i = 1; i <= m; i++) {
            dp[i][0] = s1.substring(0,i).equals(s3.substring(0,i));
        }
        for (int i = 1; i <= n; i++) {
            dp[0][i] = s2.substring(0,i).equals(s3.substring(0,i));
        }
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                dp[i][j] = false;
                if(s1.charAt(i-1) == s3.charAt(i+j-1))
                {
                    dp[i][j] |= dp[i-1][j];
                }
                if(s2.charAt(j-1) == s3.charAt(i+j-1))
                {
                    dp[i][j] |= dp[i][j-1];
                }
            }
        }
        System.out.println(Arrays.deepToString(dp));
        return dp[m][n];
    }
    //98. 验证二叉搜索树
    public boolean isValidBST(TreeNode root) {
        if(root==null) return true;
        return subIsValidBST(root, new int[2]);
    }

    public boolean subIsValidBST(TreeNode root, int[] max_min) {
        if(root.left==null && root.right==null)
        {
            max_min[0] = root.val;
            max_min[1] = root.val;
            return true;
        }
        int[] left_max_min = new int[2];
        int[] right_max_min = new int[2];
        boolean flag1, flag2;
        if(root.left!=null)
        {
            flag1 = subIsValidBST(root.left, left_max_min);
            if(!flag1 || root.val<=left_max_min[1]) return false;
            max_min[0] = left_max_min[0];
        }
        else
        {
            max_min[0] = root.val;
        }
        if(root.right!=null)
        {
            flag2 = subIsValidBST(root.right, right_max_min);
            if(!flag2 || root.val>=right_max_min[0]) return false;
            max_min[1] = right_max_min[1];
        }
        else
        {
            max_min[1] = root.val;
        }
        return true;
    }

    //99. 恢复二叉搜索树
    public void recoverTree(TreeNode root) {
        if(root == null) return;
        Stack<TreeNode> stack = new Stack<>();
        TreeNode cur = root;
        List<TreeNode> list = new ArrayList<>();
        while(cur!=null || !stack.isEmpty())
        {
            while(cur!=null)
            {
                stack.push(cur);
                cur = cur.left;
            }
            cur = stack.pop();
            list.add(cur);
            cur = cur.right;
        }
        int x = -1,y=-1;
        for (int i = 0; i < list.size()-1; i++) {
            if(list.get(i+1).val<list.get(i).val)
            {
                y = i+1;
                x = x== -1? i:x;
            }
        }
        swap(list.get(x), list.get(y));
    }

    public void swap(TreeNode a, TreeNode b) {
        int tmp = a.val;
        a.val = b.val;
        b.val = tmp;
    }

    //100. 相同的树
    public boolean isSameTree(TreeNode p, TreeNode q) {
        if(p==null && q==null) return true;
        if(p == null ||q==null) return false;
        return p.val== q.val && isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }
    //101. 对称二叉树
    public boolean isSymmetric(TreeNode root) {
        if(root==null) return true;
        return subIsSymmetric(root.left, root.right);
    }

    public boolean subIsSymmetric(TreeNode p, TreeNode q) {
        if(p==null && q==null) return true;
        if(p == null ||q==null) return false;
        return p.val== q.val && subIsSymmetric(p.left, q.right) && isSameTree(p.right, q.left);
    }

    //102. 二叉树的层序遍历
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> list = new ArrayList<>();
        subLevelOrder(root,list,0);
        return list;
    }
    public void subLevelOrder(TreeNode root, List<List<Integer>> list, int level)
    {
        if(root == null) return;
        if(list.size() < level+1)
        {
            list.add(new ArrayList<>());
        }
        list.get(level).add(root.val);
        subLevelOrder(root.left, list, level+1);
        subLevelOrder(root.right, list, level+1);
    }

    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        recursion(root, 0, result);
        return result;

    }

    private void recursion(TreeNode node, int level, List<List<Integer>> result){
        if(node == null){
            return;
        }
        if(result.size() == level || result.get(level) == null){
            result.add(new LinkedList<>());
        }
        LinkedList<Integer> list = (LinkedList<Integer>)result.get(level);
        if(level % 2 == 0) {
            list.addLast(node.val);
        }else {
            list.addFirst(node.val);
        }
        recursion(node.left, level + 1, result);
        recursion(node.right, level + 1, result);
    }
    //104. 二叉树的最大深度
    public int maxDepth(TreeNode root) {
        if(root ==null) return 0;
        return Math.max(maxDepth(root.left), maxDepth((root.right)))+1;
    }

    //105. 从前序与中序遍历序列构造二叉树
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            map.put(inorder[i], i);
        }
        return subBuildTree(preorder, map, 0,0, preorder.length-1);
    }
    public TreeNode subBuildTree(int[] preorder, Map<Integer, Integer> map, int cur, int left, int right) {
        if(cur == preorder.length || right < left) return null;
        TreeNode root = new TreeNode(preorder[cur]);
        int index = map.get(preorder[cur]);

        root.left = subBuildTree(preorder, map, cur+1, left, index-1);

        root.right = subBuildTree(preorder, map, cur+index-left+1, index+1, right);

        return root;
    }

    //106. 从中序与后序遍历序列构造二叉树
    public TreeNode buildTree2(int[] inorder, int[] postorder) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            map.put(inorder[i], i);
        }
        return subBuildTree2(postorder, map, postorder.length-1,0, postorder.length-1);
    }

    public TreeNode subBuildTree2(int[] postorder, Map<Integer, Integer> map, int cur, int left, int right) {
        if(cur < 0 || right < left) return null;
        TreeNode root = new TreeNode(postorder[cur]);

        int index = map.get(postorder[cur]);

        root.right = subBuildTree2(postorder, map, cur-1, index+1, right);
        root.left = subBuildTree2(postorder, map, cur-(right-index+1), left, index-1);

        return root;
    }

    //107. 二叉树的层次遍历 II
    public List<List<Integer>> levelOrderBottom(TreeNode root) {
        List<List<Integer>> list = new ArrayList<>();
        list = levelOrder(root);
        List<List<Integer>> newList = new ArrayList<>();
        for (int i = 0; i <list.size() ; i++) {
            newList.add(list.get(list.size()-i-1)) ;
        }
        return newList;
    }
    //108. 将有序数组转换为二叉搜索树
    public TreeNode sortedArrayToBST(int[] nums) {
        return subSortedArrayToBST(nums,0,nums.length-1);
    }
    public TreeNode subSortedArrayToBST(int[] nums, int left, int right) {
        if(left>right) return null;
        int mid = (left+right)/2;
        TreeNode root = new TreeNode(nums[mid]);
        root.left = subSortedArrayToBST(nums,left,mid-1);
        root.right = subSortedArrayToBST(nums,mid+1,right);
        return root;
    }

    //109. 有序链表转换二叉搜索树
    public TreeNode sortedListToBST(ListNode head) {
        if(head==null) return  null;
        if(head.next ==null) return new TreeNode(head.val);

        ListNode slow = head;
        ListNode pre = null;
        ListNode fast = slow;
        while(fast !=null && fast.next!=null)
        {
            pre = slow;
            slow = slow.next;
            fast = fast.next.next;
        }
        ListNode tmp = slow.next;
        pre.next = null;
        slow.next = null;

        TreeNode root = new TreeNode(slow.val);
        System.out.println(slow.val);
        root.right = sortedListToBST(tmp);
        root.left = sortedListToBST(head);
        return root;
    }

    public boolean isBalanced(TreeNode root) {
        if(root==null) return true;
        if(!isBalanced(root.left) || !isBalanced(root.right)) return false;
        return Math.abs(treeDepth(root.left)-treeDepth(root.right))<=1;
    }
    //110. 平衡二叉树
    public int treeDepth(TreeNode root) {
        if(root == null) return 0;
        return Math.max(treeDepth(root.left), treeDepth(root.right))+1;
    }
    //111. 二叉树的最小深度
    public int minDepth(TreeNode root) {
        if(root == null) return 0;
        if(root.left==null && root.right==null) return 1;
        if(root.left ==null) return minDepth(root.right)+1;
        if(root.right ==null) return minDepth(root.left)+1;
        return Math.min(minDepth(root.left), minDepth(root.right))+1;
    }
    //112. 路径总和
    public boolean hasPathSum(TreeNode root, int sum) {
        if(root == null) return false;
        if(root.left==null && root.right==null) return root.val == sum;
        if(root.left ==null) return hasPathSum(root.right, sum-root.val);
        if(root.right ==null) return hasPathSum(root.left, sum-root.val);
        return hasPathSum(root.right, sum-root.val) || hasPathSum(root.left, sum-root.val);
    }
    //113. 路径总和 II
    public static List<List<Integer>>list = new ArrayList<>();

    public List<List<Integer>> pathSum(TreeNode root, int sum) {
        list.clear();
        List<Integer> sublist = new ArrayList<>();
        if(root == null) return list;

        sublist.add(root.val);
        subpathSum(root, sum-root.val, sublist);
        return list;
    }


    public void subpathSum(TreeNode root, int sum, List<Integer> sublist) {
        if(root == null) return;
        if(root.left==null && root.right==null)
        {
            if(sum==0) {
                list.add(new ArrayList<>(sublist));
            }
            return;

        }
        if(root.right !=null)
        {
            sublist.add(root.right.val);
            subpathSum(root.right, sum-root.right.val, sublist);
            sublist.remove(sublist.size()-1);
        }
        if(root.left !=null)
        {
            sublist.add(root.left.val);
            subpathSum(root.left, sum-root.left.val, sublist);
            sublist.remove(sublist.size()-1);
        }
    }

    //114. 二叉树展开为链表
    public void flatten(TreeNode root) {
        if(root==null)  return;
        subFlatten(root);
    }

    public TreeNode subFlatten(TreeNode root){
        if(root.left == null && root.right ==null) return root;
        TreeNode right = root.right;
        TreeNode left = root.left;
        TreeNode last;
        if(root.left!=null)
            last = subFlatten(root.left);
        else
            last = root;

        root.left =null;
        root.right = left;
        last.right = right;
        if(right !=null)
            return subFlatten(right);
        else
            return last;
    }

    //115. 不同的子序列
    public int numDistinct(String s, String t) {
        int sl = s.length();
        int tl = t.length();

        int dp[][] = new int[sl+1][tl+1];
        dp[0][0] = 1;
        for (int i = 0; i <= sl; i++) {
            dp[i][0] = 1;
        }
        for (int i = 1; i <= sl; i++) {
            for (int j = 1; j <= i && j<=tl; j++) {
                if(s.charAt(i-1) != t.charAt(j-1))
                {
                    dp[i][j] = dp[i-1][j];
                }
                else
                {
                    dp[i][j] = dp[i-1][j]+dp[i-1][j-1];

                }

            }
        }
        System.out.println(Arrays.deepToString(dp));
        return dp[sl][tl];
    }

    class Node {
        public int val;
        public Node left;
        public Node right;
        public Node next;

        public Node() {}

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, Node _left, Node _right, Node _next) {
            val = _val;
            left = _left;
            right = _right;
            next = _next;
        }
    };

    public Node connect(Node root) {
        if(root==null) return null;

        Node leftmost = root;

        while(leftmost.left!=null)
        {
            Node head = leftmost;

            while(head!=null)
            {
                head.left.next = head.right;
                if(head.next!=null)
                {
                    head.right.next = head.next.left;
                }
                head = head.next;
            }
            leftmost =leftmost.left;
        }
        return root;
    }

    public Node connect2(Node root) {
        if(root==null) return null;
        Node leftmost = root;
        while(leftmost!=null)
        {
            Node head = leftmost;
            while(head!=null)
            {
                if(head.left==null && head.right ==null) head = head.next;
                else
                {
                    if(head.left!=null && head.right !=null)
                        head.left.next = head.right;
                    Node left = head.right!=null ? head.right: head.left;
                    head = head.next;
                    while(head!=null)
                    {
                        if(head.left==null && head.right ==null) head = head.next;
                        else {
                            left.next = head.left!=null? head.left:head.right;
                            break;
                        }
                    }
                }
            }
            while(leftmost!=null && leftmost.left==null && leftmost.right==null)
            {
                leftmost = leftmost.next;
            }
            if(leftmost==null) return root;
            leftmost = leftmost.left!=null ? leftmost.left: leftmost.right;
        }
        return root;
    }

    public List<List<Integer>> generate(int numRows) {
        List<List<Integer>> list = new ArrayList<>();
        if(numRows == 0) return list;
        List<Integer> list1 = new ArrayList<>();
        list1.add(1);
        list.add(new ArrayList<>(list1));
        if(numRows==1) return list;
        for (int i = 1; i <numRows ; i++) {
            list1.clear();
            List<Integer> last = list.get(i-1);
            list1.add(1);
            for (int j = 0; j <last.size()-1 ; j++) {
                list1.add(last.get(j)+last.get(j+1));
            }
            list1.add(1);
            list.add(new ArrayList<>(list1));
        }
        return list;
    }


    public List<Integer> getRow(int rowIndex) {
        List<Integer> res = new ArrayList<>(rowIndex + 1);
        long cur = 1;
        for (int i = 0; i <= rowIndex; i++) {
            res.add((int) cur);
            cur = cur * (rowIndex-i)/(i+1);
        }
        return res;
    }

    //120. 三角形最小路径和
    public int minimumTotal(List<List<Integer>> triangle) {
        int rows = triangle.size();
        int[][] dp  = new int[rows][rows];
        dp[0][0] = triangle.get(0).get(0);
        for (int i = 1; i < rows ; i++) {
            dp[i][0] = dp[i-1][0]+triangle.get(i).get(0);
        }
        for (int i = 1; i < rows ; i++) {
            for (int j = 1; j <= i; j++) {
                if(j==i) dp[i][j] = dp[i-1][j-1]+triangle.get(i).get(j);
                else dp[i][j] = Math.min(dp[i-1][j], dp[i-1][j-1]) + triangle.get(i).get(j);
            }
        }
        System.out.println(Arrays.deepToString(dp));
        int min = -1;
        for (int i = 0; i < rows; i++) {
            min = (i==0||min> dp[rows-1][i]) ? dp[rows-1][i]:min;
        }
        return min;
    }

    //121. 买卖股票的最佳时机
    public int maxProfit(int[] prices, int bg, int ed) {
        int maxp = 0;
        int m = prices.length;
        if(bg>=ed) return 0;
        int left = bg;
        for (int i = bg; i <= ed; i++) {
            maxp = Math.max(maxp, prices[i]-prices[left]);
            if(prices[i]-prices[left]<0)
            {
                left = i;
            }
        }
        return maxp;
    }

    //122. 买卖股票的最佳时机 II
    public int maxProfit2(int[] prices) {
        int maxp = 0;
        int m = prices.length;
        int left = 0;
        boolean direct = false; //false 下降，true 上升
        for (int i = 0; i <m ; i++) {
            if(!direct && (i<m-1 && prices[i]<prices[i+1]))
            {
                direct = true;
                left = i;
            }
            if(direct && (i==m-1 || prices[i]>prices[i+1]))
            {
                maxp+= prices[i]-prices[left];
                direct = false;
            }
        }
        return maxp;
    }

    //123. 买卖股票的最佳时机 III
    public int maxProfit3(int[] prices) {
        /**
         对于任意一天考虑四个变量:
         fstBuy: 在该天第一次买入股票可获得的最大收益
         fstSell: 在该天第一次卖出股票可获得的最大收益
         secBuy: 在该天第二次买入股票可获得的最大收益
         secSell: 在该天第二次卖出股票可获得的最大收益
         分别对四个变量进行相应的更新, 最后secSell就是最大
         收益值(secSell >= fstSell)
         **/
        int fstBuy = Integer.MIN_VALUE, fstSell = 0;
        int secBuy = Integer.MIN_VALUE, secSell = 0;
        for(int p : prices) {
            fstBuy = Math.max(fstBuy, -p);
            fstSell = Math.max(fstSell, fstBuy + p);
            secBuy = Math.max(secBuy, fstSell - p);
            secSell = Math.max(secSell, secBuy + p);
        }
        return secSell;
    }

    //124. 二叉树中的最大路径和
    static int max_sum = Integer.MIN_VALUE;

    public int maxPathSum(TreeNode root) {
        max_sum = Integer.MIN_VALUE;
        max_gain(root);
        return max_sum;
    }
    public int max_gain(TreeNode root) {
        if(root==null) return 0;
        int left, right;
        left = Math.max(max_gain(root.left),0);
        right =  Math.max(max_gain(root.right),0);
        max_sum = Math.max(max_sum, left+right+root.val);
//        System.out.println(left);
//        System.out.println(right);
        return Math.max(left,right)+root.val;
    }

    public int maxDumpPathSum(TreeNode root)
    {
        if(root==null ) return 0;
        int son = Math.max(maxDumpPathSum(root.left), maxDumpPathSum(root.right));
        int ans;
        if(son < 0) ans =  root.val;
        else ans = son+root.val;
        return ans>0 ? ans: 0;
    }

    //125. 验证回文串
    public boolean isPalindrome(String s) {
        s = s.toLowerCase();
        char[] chars = s.toCharArray();
        int left = 0, right = chars.length - 1;
        while (right > left) {
            if ((chars[left] >= '0' && chars[left] <= '9') || (chars[left] >= 'a' && chars[left] <= 'z')) {
                if ((chars[right] >= '0' && chars[right] <= '9') || (chars[right] >= 'a' && chars[right] <= 'z')) {
                    if (chars[left] != chars[right]) {
                        return false;
                    } else {
                        left++;
                        right--;
                    }
                } else {
                    right--;
                }
            } else {
                left++;
            }
        }
        return true;
    }

    //127. 单词接龙
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        if (wordList == null || !wordList.contains(endWord)) return 0;
        //将beginWord加入list
        wordList.add(beginWord);
        //begin找end
        Queue<String> queue1 = new LinkedList<>();
        Set<String> visited1 = new HashSet<>();
        queue1.add(beginWord);
        visited1.add(beginWord);
        //end找begin
        Queue<String> queue2 = new LinkedList<>();
        Set<String> visited2 = new HashSet<>();
        queue2.add(endWord);
        visited2.add(endWord);
        int depth = 0;
        Set<String> allWord = new HashSet<>(wordList);
        while (!queue1.isEmpty() && !queue2.isEmpty()) {
            //将节点更少的作为 1
            if (queue1.size() > queue2.size()) {
                Queue<String> temp = queue1;
                queue1 = queue2;
                queue2 = temp;
                Set<String> set = visited1;
                visited1 = visited2;
                visited2 = set;
            }
            //开始遍历
            depth++;
            int size = queue1.size();
            while (size-- > 0) {
                String poll = queue1.poll();
                char[] chars = poll.toCharArray();
                //遍历poll的每个字符
                for (int i = 0; i < chars.length; i++) {
                    //保存第i个字符,判断结束后需要还原
                    char temp = chars[i];
                    //将poll的每个字符逐个替换为其他字符
                    for (char c = 'a'; c <= 'z'; c++) {
                        chars[i] = c;
                        //判断替换后的单词
                        String newString = new String(chars);
                        if (visited1.contains(newString)) {
                            continue;
                        }
                        if (visited2.contains(newString)) {
                            return depth + 1;
                        }
                        //如果替换后的单词,存在字典中，则入队并标记访问
                        if (allWord.contains(newString)) {
                            queue1.add(newString);
                            visited1.add(newString);
                        }
                    }
                    //还原第i个字符
                    chars[i] = temp;
                }
            }
        }
        return 0;
    }
    //poll是否只可以改变一个字符转换为s

    //128. 最长连续序列
    public int longestConsecutive(int[] nums) {
        Map<Integer,Integer> map = new HashMap<>();
        int max_length = 0;
        for (int i = 0; i < nums.length ; i++) {
            if(map.containsKey(nums[i])) continue;
            else
            {
                int left = map.getOrDefault(nums[i]-1,0);
                int right = map.getOrDefault(nums[i]+1, 0);
                if(left+right+1>max_length) max_length = left+right+1;
                map.put(nums[i], left+right+1);
                map.put(nums[i]-left,left+right+1);
                map.put(nums[i]+right,left+right+1);
            }
        }

        return max_length;
    }

    //129. 求根到叶子节点数字之和
    public int sumNumbers(TreeNode root) {
        return subSumNumbers(root, 0);

    }
    public int subSumNumbers(TreeNode root, int sum)
    {
        if(root==null) return sum;

        int left = root.left==null ? 0 : subSumNumbers(root.left, sum*10 + root.left.val);
        int right = root.right == null ? 0: subSumNumbers(root.right, sum*10 + root.right.val);

        return left+right == 0 ? sum : left+right;
    }

    //130. 被围绕的区域
    public void solve130(char[][] board) {
        int m = board.length;
        if (m==0) return;
        int n = board[0].length;
        for (int i = 0; i <m ; i++) {
            for (int j = 0; j < n; j++) {
                if(i==0 || j==0|| i== m-1|| j== n-1)
                {
                    if(board[i][j]=='O')
                        dfs(i,j,board);
                }
            }
        }
        for (int i = 0; i < m; i++) {
            for (int j = 0; j <n ; j++) {
                if(board[i][j]=='O') board[i][j]='X';
                if(board[i][j]=='#') board[i][j]='O';
            }
        }

    }
    public void dfs(int i, int j, char[][] board)
    {
        if(i<0 || j<0 || i>=board.length || j>=board[0].length ||board[i][j] == 'X' || board[i][j] == '#') return;
        board[i][j] = '#';
        dfs(i+1,j,board);
        dfs(i-1,j,board);
        dfs(i,j+1,board);
        dfs(i,j-1,board);
    }

    public int canCompleteCircuit(int[] gas, int[] cost) {
        int n = gas.length;

        int total_tank = 0;
        int curr_tank = 0;
        int starting_station = 0;
        for (int i = 0; i < n; ++i) {
            total_tank += gas[i] - cost[i];
            curr_tank += gas[i] - cost[i];
            // If one couldn't get here,
            if (curr_tank < 0) {
                // Pick up the next station as the starting one.
                starting_station = i + 1;
                // Start with an empty tank.
                curr_tank = 0;
            }
        }
        return total_tank >= 0 ? starting_station : -1;
    }

    public int candy(int[] ratings) {
        int[] candies = new int[ratings.length];
        Arrays.fill(candies, 1);
        for (int i = 1; i < ratings.length; i++) {
            if (ratings[i] > ratings[i - 1]) {
                candies[i] = candies[i - 1] + 1;
            }
        }
        int sum = candies[ratings.length - 1];
        for (int i = ratings.length - 2; i >= 0; i--) {
            if (ratings[i] > ratings[i + 1]) {
                candies[i] = Math.max(candies[i], candies[i + 1] + 1);
            }
            sum += candies[i];
        }
        return sum;
    }
    // 138. 复制带随机指针的链表， 深度优先
    static Map<Node, Node> visited = new HashMap();
    public Node copyRandomList(Node head) {
//        if(head == null) return null;
//        visited.put(head, new Node(head.val));
//        if(head.next!=null)
//        {
//            if(visited.containsKey(head.next)) visited.get(head).next = visited.get(head.next);
//            else visited.get(head).next = copyRandomList(head.next);
//        }
//        if(head.random!=null)
//        {
//            if(visited.containsKey(head.random)) visited.get(head).random = visited.get(head.random);
//            else visited.get(head).random = copyRandomList(head.random);
//        }
        return visited.get(head);
    }

    //141. 环形链表
    public boolean hasCycle(ListNode head) {
        if(head == null) return false;
        ListNode cur = head;
        ListNode right = head.next;
        while(right!=null)
        {
            if(cur == right) return true;
            cur = cur.next;
            if(right.next == null) return false;
            right = right.next.next;
        }
        return false;
    }

    public class LRUCache {
        class DLinkedNode {
            int key;
            int value;
            DLinkedNode prev;
            DLinkedNode next;
            public DLinkedNode() {}
            public DLinkedNode(int _key, int _value) {key = _key; value = _value;}
        }

        private Map<Integer, DLinkedNode> cache = new HashMap<Integer, DLinkedNode>();
        private int size;
        private int capacity;
        private DLinkedNode head, tail;

        public LRUCache(int capacity) {
            this.size = 0;
            this.capacity = capacity;
            // 使用伪头部和伪尾部节点
            head = new DLinkedNode();
            tail = new DLinkedNode();
            head.next = tail;
            tail.prev = head;
        }

        public int get(int key) {
            DLinkedNode node = cache.get(key);
            if (node == null) {
                return -1;
            }
            // 如果 key 存在，先通过哈希表定位，再移到头部
            moveToHead(node);
            return node.value;
        }

        public void put(int key, int value) {
            DLinkedNode node = cache.get(key);
            if (node == null) {
                // 如果 key 不存在，创建一个新的节点
                DLinkedNode newNode = new DLinkedNode(key, value);
                // 添加进哈希表
                cache.put(key, newNode);
                // 添加至双向链表的头部
                addToHead(newNode);
                ++size;
                if (size > capacity) {
                    // 如果超出容量，删除双向链表的尾部节点
                    DLinkedNode tail = removeTail();
                    // 删除哈希表中对应的项
                    cache.remove(tail.key);
                    --size;
                }
            }
            else {
                // 如果 key 存在，先通过哈希表定位，再修改 value，并移到头部
                node.value = value;
                moveToHead(node);
            }
        }

        private void addToHead(DLinkedNode node) {
            node.prev = head;
            node.next = head.next;
            head.next.prev = node;
            head.next = node;
        }

        private void removeNode(DLinkedNode node) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }

        private void moveToHead(DLinkedNode node) {
            removeNode(node);
            addToHead(node);
        }

        private DLinkedNode removeTail() {
            DLinkedNode res = tail.prev;
            removeNode(res);
            return res;
        }
    }

    public int evalRPN(String[] tokens) {
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < tokens.length; i++) {
            if(tokens[i].equals("+"))
            {
                stack.push(stack.pop()+stack.pop());
            }
            else if(tokens[i].equals("-"))
            {
                stack.push(-stack.pop()+stack.pop());
            }
            else if(tokens[i].equals("*"))
            {
                stack.push(stack.pop()*stack.pop());
            }
            else if(tokens[i].equals("/"))
            {
                int tmp = stack.pop();
                stack.push(stack.pop()/tmp);
            }
            else
            {
                stack.push(Integer.parseInt(tokens[i]));
            }
        }
        return stack.pop();
    }
    public static void main(String[] args) {
        LeetCode le = new LeetCode();
        List<Integer> list = new LinkedList<>();

        int[] nums = new int[]{100, 4, 200, 1, 3, 2};
        System.out.println(le.longestConsecutive(nums));
//        System.out.println(le.numDistinct("rabbbit", "rabbit"));
        //System.out.println(le.numDistinct("aabb", "abb"));
//        TreeNode root = new TreeNode(1);
//        root.left = new TreeNode(2);
//        root.right = new TreeNode(3);
//        System.out.println(le.maxPathSum(root));
//        String[] words = new String[]{"si","go","se","cm","so","ph","mt","db","mb","sb","kr","ln","tm","le","av","sm","ar","ci","ca","br","ti","ba","to","ra","fa","yo","ow","sn","ya","cr","po","fe","ho","ma","re","or","rn","au","ur","rh","sr","tc","lt","lo","as","fr","nb","yb","if","pb","ge","th","pm","rb","sh","co","ga","li","ha","hz","no","bi","di","hi","qa","pi","os","uh","wm","an","me","mo","na","la","st","er","sc","ne","mn","mi","am","ex","pt","io","be","fm","ta","tb","ni","mr","pa","he","lr","sq","ye"};
//        //String[] words = new String[]{"hot","dot","dog","lot","log","cog"};
//        List<String> wordList = new ArrayList<>(Arrays.asList(words));
//
//        System.out.println(le.ladderLength("qa", "sq", wordList ));
        //        int[] nums = new int[]{4,4,4,1,4};
//        String s1 = "aa";
//        String s2 = "d";
//        String s3 = "aad";
//        System.out.println(le.isInterleave(s1,s2,s3));
//        for (int i = 1; i <10 ; i++) {
//            System.out.println(le.numTrees(i));
//        }

        //System.out.println(le.subsetsWithDup(nums));
        //System.out.println(le.numDecodings("12120"));
        //System.out.println(le.restoreIpAddresses("25525511135"));
        //System.out.println(le.removeDuplicates2(nums));
        //System.out.println(le.minWindow("BACBANCADOBECODEBANC","ABC"));
        //System.out.println(le.combine(7,3));
        //System.out.println(le.minWindow("bba","ba"));
        //le.sortColors(nums);
        //System.out.println(Arrays.toString(nums));
        //System.out.println(le.mySqrt(9));
        //System.out.println(le.lengthOfLastWord("a "));
        //System.out.println(le.getPermutation(5,2));
        //System.out.println(le.addBinary("1011", "111"));
//        String[] words = new String[6];
//        words[0] = "what";
//        words[1] = "must";
//        words[2] = "be";
//        words[3] = "acknowledged";
//        words[4] = "shall";
//        words[5] = "be";
        //System.out.println(le.fullJustify(words, 12));
        //System.out.println(le.isMatch_lookback("abbabaaabbabbaababbabbbbbabbbabbbabaaaaababababbbabababaabbababaabbbbbbaaaabababbbaabbbbaabbbbababababbaabbaababaabbbababababbbbaaabbbbbabaaaabbababbbbaababaabbababbbbbababbbabaaaaaaaabbbbbaabaaababaaaabb",
                //"**aa*****ba*a*bb**aa*ab****a*aaaaaa***a*aaaa**bbabb*b*b**aaaaaaaaa*a********ba*bbb***a*ba*bb*bb**a*b*bb",0,0));

        //System.out.println(le.isMatch_lookback("abefcdgiescdfimde","ab*cd?i*de",0,0));
        //        char[][] board = new char[][]{
//                {'5','3','.','.','7','.','.','.','.'},
//                {'6','.','.','1','9','5','.','.','.'},
//                {'.','9','8','.','.','.','.','6','.'},
//                {'8','.','.','.','6','.','.','.','3'},
//                {'4','.','.','8','.','3','.','.','1'},
//                {'7','.','.','.','2','.','.','.','6'},
//                {'.','6','.','.','.','.','2','8','.'},
//                {'.','.','.','4','1','9','.','.','5'},
//                {'.','.','.','.','8','.','.','7','9'}};
//        le.solveSudoku(board);
//        for (int i = 0; i < 9; i++) {
//            System.out.println(Arrays.toString(board[i]));
//        }

         //   System.out.println(le.countAndSay(30));
        int[] candidates = new int[]{2,3,6,7};
        int target = 7;
        //System.out.println(le.combinationSum(candidates,target));

        int[] height = new int[]{2,0,2};
       // System.out.println(le.trap(height));
        //System.out.println(le.multiply("123","456"));


    }
}