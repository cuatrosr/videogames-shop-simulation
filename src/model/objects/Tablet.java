package src.model.objects;

public class Tablet {

    public Tablet(){}


    public Object[] mergeSort(Object[] array, Shelf[] shelf){

        if(array.length <= 1){
            return array;

        }else{

            Object[] left;
            Object[] right = new Object[array.length/2];

            if(array.length % 2 == 0){
                left = new Object[array.length/2];

            }else{
                left = new Object[array.length/2 + 1];
            }

            int i;
            for (i = 0; i < left.length; ++i) {
                left[i] = array[i];
            }

            int k = 0;
            for (int j = i; j < array.length; ++j) {
                right[k] = array[j];
                ++k;
            }

            return merge(mergeSort(left, shelf), mergeSort(right, shelf), shelf);
        }

    }

    private Object[] merge(Object[] a, Object[] b, Shelf[] shelf){
        int i = 0;
        int j = 0;
        Object[] c = new Object[a.length + b.length];

        for (int k = 0; k < c.length; ++k) {
            if ((int) a[i] < (int) b[j]) {
                c[k] = a[i];
                ++i;
                
            }else{
                c[k] = b[j];
                ++j;

            }

            if (i == a.length) {
                ++k;

                for (int l = j; l < b.length; l++) {
                    c[k] = b[l];
                    ++k;
                }
                
            }else if(j == b.length){
                ++k;

                for (int l = i; l < b.length; l++) {
                    c[k] = a[l];
                    ++k;
                }
                

            }
        }



        return c;


    }

  
    
}
