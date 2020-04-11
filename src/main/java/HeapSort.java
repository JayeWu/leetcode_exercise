import java.util.Arrays;

public class HeapSort {
    static int[] nums=new int[]{3,7,8,9,2,3,4,6,5,1,0};
    static int len =nums.length;
    public static void main(String[] args) {
        sort(0,nums.length-1);
        System.out.println(Arrays.toString(nums));
    }
    public static void sort(int left, int right){
        firstHeap();
        for (int i = nums.length - 1; i > 0; i--) {
            swap( 0, i);
            len--;
            heaplify(0);
        }
    }
    public static void firstHeap(){
        for(int i = len/2;i>=0;i--){
            heaplify(i);
        }
        System.out.println(Arrays.toString(nums));
    }
    public static void heaplify(int i){
        int left = 2 * i + 1,
                right = 2 * i + 2,
                largest = i;

        if (left < len && nums[left] > nums[largest]) {
            largest = left;
        }

        if (right < len && nums[right] > nums[largest]) {
            largest = right;
        }

        if (largest != i) {
            swap(i, largest);
            heaplify(largest);
        }
//        System.out.println(Arrays.toString(nums));

    }
    public static void swap(int i, int j){
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
