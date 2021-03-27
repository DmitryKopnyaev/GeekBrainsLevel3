package lesson3;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class BookReader {
    private int countLetters;
    private String path;
    private List<byte[]> list;

    public BookReader(int countLetters, String path) {
        this.countLetters = countLetters;
        this.path = path;
        list = new LinkedList<>();
    }

    //вариант 1 с помощью метода load() загрузить содержимое страниц в список
    //и доставать их оттуда методом getPage()
    public void load() throws IOException {
        byte[] arr = new byte[countLetters];
        try (FileInputStream fis = new FileInputStream(path)) {
            while ((fis.read(arr)) > 0) {
                list.add(Arrays.copyOf(arr, arr.length));
            }
        }
    }

    public String getPage(int n) {
        if (n < 1) throw new IllegalArgumentException("Number of page mast be > 0");
        return new String(list.get(n - 1));
    }

    //вариант 2 из лекции
    public String getPageWithRandomAccessFile(int n) throws IOException {
        if (n < 1) throw new IllegalArgumentException("Number of page mast be > 0");
        try (RandomAccessFile raf = new RandomAccessFile(path, "r")) {
            byte[] arr = new byte[countLetters];
            raf.seek((long) (n - 1) * countLetters);
            raf.read(arr);
            return new String(arr);
        }
    }
}
