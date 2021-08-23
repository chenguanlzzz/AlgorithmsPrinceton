public class MergeSmaller {
    public static void mergeSmall(int[] a) {
        int n = a.length;
        int[] smallArr = new int[n / 2];
        int i = 0;
        int j = n / 2;

        // Copy the first n smaller elements to aux array.
        for (int k = 0; k < n / 2; k++) {
            if (a[i] <= a[j]) {
                smallArr[k] = a[i++];
            }
            else {
                smallArr[k] = a[j++];
            }
        }

        // Move the elements which doesn't copy to aux elements of first half to the last half.
        for (int x = i; x < n / 2; x++) {
            a[x - i + n / 2] = a[x];
        }

        // Copy the aux array to the first half of a
        for (int x = 0; x < n / 2; x++) {
            a[x] = smallArr[x];
        }

        // Copy the last n smaller elements to aux array.
        int iBuffer = n / 2 - i;
        i = n / 2;
        for (int k = 0; k < n / 2; k++) {
            if (a[i] <= a[j] && iBuffer > 0) {
                smallArr[k] = a[i++];
                iBuffer--;
            }
            else {
                smallArr[k] = a[j++];
            }
        }

        // Copy the aux array to the last half of a
        for (int x = 0; x < n / 2; x++) {
            a[x + n / 2] = smallArr[x];
        }
    }


    public static void main(String[] args) {
        int[] a = { 4, 5, 12, 4, 13, 15 };
        mergeSmall(a);
        for (int i : a) {
            System.out.println(i);
        }
    }
}
