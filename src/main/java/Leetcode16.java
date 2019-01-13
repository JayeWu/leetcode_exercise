import java.util.*;


public class Leetcode16 {
    public static void main(String[] args) {
        System.out.println(Integer.MAX_VALUE);
    }
    public int threeSumClosest(int[] nums, int target) {
        Arrays.sort(nums);
        if(nums.length==0) return 0;
        if(nums.length==1) return nums[0];
        if(nums.length==2) return nums[0]+nums[1];

        int res = nums[0]+nums[1]+nums[2];
        for(int i=0;i<nums.length;i++){
            int left = i+1;
            int right = nums.length-1;
            while(left<right){
                int yu = nums[left]+nums[right]+nums[i]-target;
                if (yu==0) return target;
                else if(yu>0){
                    res = yu<Math.abs(res-target)? nums[left]+nums[right]+nums[i]: res;
                    right--;
                }
                else {
                    res = -1*yu<Math.abs(res-target)? nums[left]+nums[right]+nums[i]: res;
                    left++;
                }
            }
        }
        return res;
    }
}
