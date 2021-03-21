package lesson1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Program {
    /**
     *1. Написать метод, который меняет два элемента массива местами.(массив может быть любого ссылочного типа);
     */
    public static <T> void swap(T[] arr, int element1, int element2) {
        if (element1 == element2 || element1 < 0 || element2 < 0 || element1 >= arr.length || element2 >= arr.length)
            throw new IllegalArgumentException("не верные индексы");
        T element = arr[element1];
        arr[element1] = arr[element2];
        arr[element2] = element;
    }

    /**
     *2. Написать метод, который преобразует массив в ArrayList;
     */
    public static <T> ArrayList<T> arrayToArrayList(T[] arr){
        ArrayList<T> list = new ArrayList<>();
        Collections.addAll(list, arr);
        return list;
    }

    public static void main(String[] args) {
        //проверка 1 задания
        String[] arrS = {"a", "b", "c", "d", "e"};
        swap(arrS, 0, 1);
        System.out.println(Arrays.toString(arrS));

        Integer[] arrI = {0, 1, 2, 3, 4};
        swap(arrI, 0, 1);
        System.out.println(Arrays.toString(arrI));

        //проверка 2 задания
        System.out.println(arrayToArrayList(arrS));
        System.out.println(arrayToArrayList(arrI));

        //проверка 3 задания
        Box<Orange> orangeBox = new Box<>();
        for (int i = 0; i < 10; i++) {
            orangeBox.add(new Orange());
        }

        Box<Apple> appleBox = new Box<>();
        for (int i = 0; i < 10; i++) {
            appleBox.add(new Apple());
        }

        System.out.println("orangeBox.getWeight() " + orangeBox.getWeight());
        System.out.println("appleBox.getWeight() " + appleBox.getWeight());
        System.out.println(orangeBox.compare(appleBox));

        Box<Apple> appleBox2 = new Box<>();
        for (int i = 0; i < 10; i++) {
            appleBox2.add(new Apple());
        }
        System.out.println("appleBox2.getWeight() " + appleBox2.getWeight());

        appleBox.getAll(appleBox2);
        System.out.println();
        System.out.println("appleBox.getWeight() " + appleBox.getWeight());
        System.out.println("appleBox2.getWeight() " + appleBox2.getWeight());
    }
}
