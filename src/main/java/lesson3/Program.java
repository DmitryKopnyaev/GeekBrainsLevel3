package lesson3;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class Program {
   /* 1. Прочитать файл (около 50 байт) в байтовый массив и вывести этот массив в консоль;

    2. Последовательно сшить 5 файлов в один (файлы примерно 100 байт).
     Может пригодиться следующая конструкция: ArayList<InputStream> al = new ArrayList<>();
    ... Enumeration<InputStream> e = Collections.enumeration(al);

    3. Написать консольное приложение, которое умеет постранично читать текстовые файлы (размером > 10 mb).
     Вводим страницу (за страницу можно принять 1800 символов), программа выводит ее в консоль. Контролируем время выполнения: программа не должна
     загружаться дольше 10 секунд, а чтение – занимать свыше 5 секунд.
*/

    public static void main(String[] args) {
        // 1. Прочитать файл (около 50 байт) в байтовый массив и вывести этот массив в консоль;
        try (FileInputStream fis = new FileInputStream("src/main/java/lesson3/files/file1.txt")) {
            System.out.println("Длина считываемого файла = " + fis.available() + " байт");

            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            System.out.println(new String(buffer));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 2. Последовательно сшить 5 файлов в один (файлы примерно 100 байт).

        //создаю 5 файлов с текстом
        /*for (int i = 1; i <= 5; i++) {
            String s = String.format("src/main/java/lesson3/files/file2_%d.txt", i);
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(s));) {
                if (!new File(s).exists())
                    new File(s).createNewFile();
                String text = "";
                for (int j = 1; j <= 10; j++) {
                    text += String.format("This %d row of file № %d\n", j, i);
                }
                writer.write(text);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/

        //добавляем 5 файлов в список
        ArrayList<InputStream> ail = new ArrayList<>();
        File file = new File("src/main/java/lesson3/files");
        File[] listFiles = file.listFiles();
        try {
            for (File f : listFiles) {
                if (f.getName().contains("file2"))
                    ail.add(new FileInputStream(f.getPath()));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //записываем в файл содержимое других пяти файлов
        try (
                SequenceInputStream secInStr = new SequenceInputStream(Collections.enumeration(ail));
                FileOutputStream fos = new FileOutputStream("src/main/java/lesson3/files/file_united1to5.txt");
        ) {
            int x;
            while ((x = secInStr.read()) != -1) {
                fos.write(x);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //3. Написать консольное приложение, которое умеет постранично читать текстовые файлы (размером > 10 mb).
        //     Вводим страницу (за страницу можно принять 1800 символов), программа выводит ее в консоль. Контролируем время выполнения: программа не должна
        //     загружаться дольше 10 секунд, а чтение – занимать свыше 5 секунд.

        //Создадим файл размером 100 Мбайт из файла поменьше
        /*File book = new File("src/main/java/lesson3/files/book.txt");
        try(BufferedReader reader = new BufferedReader(new FileReader("src/main/java/lesson3/files/ex.txt"));
        BufferedWriter writer = new BufferedWriter(new FileWriter(book, true))){
            String s = "";
            while (reader.ready()){
                s += reader.readLine() + "\n";
            }
            for (int i = 0; i < 20000; i++) {
                writer.write("---" + i + "---\n"  + s);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        //Для чтения книги был создан класс BookReader
        BookReader br = new BookReader(1800, "src/main/java/lesson3/files/book.txt");
        try {
            br.load();
            System.out.println(br.getPageWithRandomAccessFile(1));
            System.out.println();
            System.out.println(br.getPage(1));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
