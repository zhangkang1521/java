package apache;

import org.apache.commons.lang3.ArrayUtils;
import org.testng.annotations.Test;

import java.util.Arrays;

/**
 * Created by Administrator on 12/8/2016.
 */
public class ArrayUtilsTest {

    private int[] arr = new int[]{1, 5, 20};

    @Test
    public void contains() {
        System.out.println(ArrayUtils.contains(arr, 5));
        System.out.println(ArrayUtils.contains(arr, 15));
    }

    @Test
    public void addAll() {
        int[] arr2 = new int[]{1, 99};
        System.out.println(Arrays.toString(ArrayUtils.addAll(arr, arr2)));
    }

    @Test
    public void testObjectPrimary() {
        Integer[] arr2 = ArrayUtils.toObject(arr);
        int[] arr3 = ArrayUtils.toPrimitive(arr2);
    }
}
