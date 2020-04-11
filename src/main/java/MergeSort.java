import java.util.Arrays;

public class MergeSort {
    static int[] nums=new int[]{3,7,8,9,2,3,4,6,5,1,0};
    static int len =nums.length;
    public static void main(String[] args) {
        sort(0,nums.length-1);
        System.out.println(Arrays.toString(nums));
    }
    public static void swap(int i, int j){
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
    public static void sort(int left, int right){
        if(left>=right) return;

        int mid = left+right;
        mid/=2;
        sort(left,mid);
        sort(mid+1,right);
        merge(left,mid,right);
    }
    public static void merge(int left,int mid,int right){
        int[] arr = new int[right-left+1];
        int i=0;
        int a = left;
        int b = mid+1;
        while(a<=mid||b<=right){
            if(a>mid ||nums[a]>nums[b]){
                arr[i]=nums[b];
                b++;
            }else{
                arr[i]=nums[a];
                a++;
            }
            i++;
        }
        for (int j = 0; j <arr.length; j++) {
            nums[left+j] = arr[j];
        }
    }
}
