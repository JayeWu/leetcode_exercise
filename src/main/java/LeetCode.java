import java.lang.reflect.Array;
import java.util.*;

public class LeetCode {

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


    public static void main(String[] args) {
        LeetCode ll = new LeetCode();
        //int[] nums = new int[]{1,0,-1,0,-2,2};
        List<List<Integer>> res1;
        /*String[] strings = new String[4];
        strings[0] = "word";
        strings[1] = "good";
        strings[2] = "best";
        strings[3] = "good";
        */

        String[] strings = new String[2];
        strings[0] = "bar";
        strings[1] = "foo";
        List<Integer> list = new ArrayList<>();
        //list = ll.findSubstring("wordgoodgoodbestwordgoodgoodbestword",strings);
        list = ll.findSubstring("barfoothefoobarman",strings);
        //System.out.println(list);

        int[] nums = new int[]{2,3,1};

        //boolean t = ll.isMatch("abcdede", "ab.*de");
        ll.nextPermutation(nums);
        System.out.println(Arrays.toString(nums));
    }
}