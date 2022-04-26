package ru.geekbrains.motuzok.geekuniversity.java.lesson5;

import java.util.Arrays;

public class homework5 {

    static final int size = 10000000;
    static final int h = size / 2;

    public static void main(String[] args) {

        letsFirstArray(createArray());
        letsSecondArray(createArray());

    }

    private static float[] createArray () {
        float[] array = new float[size];
        Arrays.fill(array, 1);
        return array;
    }


    private static void letsFirstArray(float[] array) {

        System.out.println(Arrays.toString(array));
        long time = System.currentTimeMillis();
        for (int i = 0; i < array.length; i++) {
            array[i] = (float)(array[i] * Math.sin(0.2f + i / 5f) * Math.cos(0.2f + i / 5f) * Math.cos(0.4f + i / 2f));
        }
        System.out.println(Arrays.toString(array));
        System.out.println("The total time for one array is ");
        System.out.println(System.currentTimeMillis() - time);
    }


    private static void letsSecondArray(float[] array) {

        System.out.println(Arrays.toString(array));
        float[] firstArray = new float[h];
        float[] secondArray = new float[h];
        long b = System.currentTimeMillis();
        System.arraycopy(array, 0, firstArray, 0, h);
        System.arraycopy(array, h, secondArray, 0, h);
        System.out.println(Arrays.toString(firstArray));
        System.out.println(Arrays.toString(secondArray));
        Thread firstCalculate = new Thread(() -> {
            for (int i = 0; i < firstArray.length; i++) {
                firstArray[i] = (float)(firstArray[i] * Math.sin(0.2f + i / 5f) * Math.cos(0.2f + i / 5f) * Math.cos(0.4f + i / 2f));
            }
        });
        firstCalculate.start();

        Thread secondCalculate = new Thread(() -> {
            for (int i = 0; i < secondArray.length; i++) {
                secondArray[i] = (float) (secondArray[i] * Math.sin(0.2f + (i+h) / 5f) * Math.cos(0.2f + (i+h) / 5f) * Math.cos(0.4f + (i+h) / 2f));
            }
        });
        secondCalculate.start();

        try {
            firstCalculate.join();
            secondCalculate.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.arraycopy(firstArray, 0, array, 0, h);
        System.arraycopy(secondArray, 0, array, h, h);
        System.out.println(Arrays.toString(array));
        System.out.println("The total time for two arrays is ");
        System.out.println(System.currentTimeMillis() - b);
    }
}
