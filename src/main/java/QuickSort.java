import com.sun.deploy.net.HttpResponse;

import javax.xml.ws.Response;
import java.util.Arrays;
import java.util.concurrent.locks.ReentrantLock;

public class QuickSort {
    static int[] nums=new int[]{10,3,4,23,12,34,3,7,8,9,2,3,4,6,5,1,0};
    static ReentrantLock lock=new ReentrantLock(true);
    static HttpResponse response;
    public static void main(String[] args) {
        sort(0,nums.length-1);
        System.out.println(Arrays.toString(nums));
    }
    public static void sort(int left, int right){
        if(right<=left) return;
        int pivot = nums[left];
        int j = left+1;
        for(int i =left+1;i<=right;i++){
            if(nums[i]<pivot){
                swap(i,j);
                j++;
            }
        }
        swap(left,j-1);
//        System.out.println(Arrays.toString(nums));
        sort(left,j-2);
        sort(j,right);
    }
    public static void swap(int i, int j){
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
