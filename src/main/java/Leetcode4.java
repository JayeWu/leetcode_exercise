import java.util.*;

public class Leetcode4 {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int k1 = (nums1.length + nums2.length+1)/2;
        int k2 = (nums1.length + nums2.length+2)/2;
        int a = findKth(nums1, 0, nums2, 0, k1);
        int b = findKth(nums1, 0, nums2, 0, k2);
        return (double)(a+b)/2;

    }
    public int findKth(int[] nums1, int left1, int[] nums2, int left2, int k){
        int a = left1<nums1.length ? nums1[left1] : Integer.MAX_VALUE;
        int b = left2<nums2.length ? nums2[left2] : Integer.MAX_VALUE;
        if (k==1) return Math.min(a,b);
        int mid = k/2;
        int v1 = left1+mid <= nums1.length ? nums1[left1+mid-1] : Integer.MAX_VALUE;
        int v2 = left2+mid <= nums2.length ? nums2[left2+mid-1] : Integer.MAX_VALUE;
        if (v2>v1) return findKth(nums1, left1+mid, nums2, left2, k-mid);
        else return findKth(nums1, left1, nums2, left2+mid, k-mid);
    }
}
