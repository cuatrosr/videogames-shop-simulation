package src.model.objects;

public class Tablet {

    public Tablet(){}


    public int[] order(Integer[] array, Shelf[] shelf){
        int j = 0;
        int[] c = new int[array.length];

        for (Shelf var : shelf) {

            for (int i = 0; i < c.length; i++) {
                if (var.getGameHash().search(array[i]) != null && array[i] == var.getGameHash().search(array[i]).getGameCode()) {
                    c[j] = array[i];
                    j++;
                    
                }
                
            }
            
        }

        return c;
       

    }

  
    
}
