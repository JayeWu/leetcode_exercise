public class Leetcode19 {
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode res = new ListNode(-1);
        res.next = head;
        int count=0;
        head = res;
        while(head.next!=null){
            head = head.next;
            count++;
        }
        int left = 0;
        head = res;
        while(left<count-n){
            head = head.next;
            left++;
        }
        head.next = head.next.next;
        return res.next;
    }
}
