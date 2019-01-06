import java.util.*;

class ListNode {
  int val;
  ListNode next;
  ListNode(int x) { val = x; }
}
public class Leetcode2 {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode res = new ListNode(-1);
        int carry = 0;
        ListNode cur = res;
        while (l1!=null || l2!=null){
            int a = l1==null ? 0: l1.val;
            int b = l2==null ? 0: l2.val;
            int sum = a+b+carry;
            carry = sum>=10 ? 1:0;
            cur.next = new ListNode(sum % 10);
            cur = cur.next;
            if(l1!=null) l1 = l1.next;
            if(l2!=null) l2 = l2.next;
        }
        if (carry >0) cur.next = new ListNode(carry);
        return res.next;
    }
}
