import java.util.Arrays;

class CRC {
    //metoda przyjmuje jako parametr Stringa, rodziela go na pojedyńcze znaki i przypisuje rzutowane wartości do tablicy, zwraca tablice danych.
    int[] inputMessage(String input){
            String [] intString = input.split(""); //rozdziela stringa na pojedzynczce znaki
            int[] data = new int[intString.length];
        for(int i=0 ; i < data.length ; i++) {
            //wstawianie do talicy
            data[i] = Integer.parseInt(intString[i]);
        }
        return data;
        }
        // to samo tylko że z dividerem
        int[] inputDivisor(String div) {
            String[] intDiv = div.split("");
            int[] divisor = new int[intDiv.length];
            for (int i = 0; i < divisor.length; i++) {
                divisor[i] = Integer.parseInt(intDiv[i]);
            }
            return divisor;
        }

    int[] generateCRC(int[] data, int[] remainder){   // tworzymy kod crc lącząc dwie tablice.
        int [] crc = new int[data.length + remainder.length-1];
        System.arraycopy(data, 0, crc, 0, data.length);
        System.arraycopy(remainder, 0, crc, data.length, remainder.length-1);
        return crc;
    }

    String generateMessage(int[] data, int[] remainder){  //to samo, tylko zwracami Stringa, po to aby móc modyfikować wiadomość.
        int [] sentMessage = new int[data.length + remainder.length-1];
        System.arraycopy(data, 0, sentMessage, 0, data.length);
        System.arraycopy(remainder, 0, sentMessage, data.length, remainder.length-1);
        StringBuilder stringBuffer = new StringBuilder();
        for (int value : sentMessage) {
            stringBuffer.append(value);
        }
        return stringBuffer.toString();
    }



    int[] divide(int[] old_data, int[] divisor) {
        int[] remainder;
        int i;
        int[] data = new int[old_data.length + divisor.length];
        System.arraycopy(old_data, 0, data, 0, old_data.length);
        remainder = new int[divisor.length];
        System.arraycopy(data, 0, remainder, 0, divisor.length);


        for(i=0 ; i < old_data.length ; i++) {

            if(remainder[0] == 1) {
                for(int j=1 ; j < divisor.length ; j++) {
                    remainder[j-1] = exor(remainder[j], divisor[j]);
                }
            }
            else {
                for(int j=1 ; j < divisor.length ; j++) {
                    remainder[j-1] = exor(remainder[j], 0);
                }
            }

            remainder[divisor.length-1] = data[i+divisor.length];

        }
        return remainder;
    }


String printRemainder(int[] remainder){
    int [] remaindernew = new int[remainder.length-1]; // ucina ostatni bit z reszty
    System.arraycopy(remainder, 0, remaindernew, 0, remainder.length - 1);
    return Arrays.toString(remaindernew);
}


    private int exor(int a, int b) {
        if(a == b) {
            return 0;
        }
        return 1;
    }

    boolean receive(int[] data, int[] divisor) {

        int[] remainder = divide(data, divisor);
        for (int value : remainder) {
            if (value != 0) {

                return true;
            }
        }
        return false;
    }

}