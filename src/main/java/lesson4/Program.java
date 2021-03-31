package lesson4;

public class Program {

    //1. Создать три потока, каждый из которых выводит определенную букву (A, B и C) 5 раз (порядок – ABСABСABС). Используйте wait/notify/notifyAll.

    private volatile char str = 'A';
    private final Object lock = new Object();

    void print(char letter, char nextLetter){
        synchronized (lock){
            try{
                for (int i = 0; i < 5; i++) {
                    while (str != letter){
                        lock.wait();
                    }
                    System.out.println(letter);
                    str = nextLetter;
                    lock.notifyAll();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Program prog = new Program();

        new Thread(new Runnable() {
            @Override
            public void run() {
                prog.print('A', 'B');
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                prog.print('B', 'C');
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                prog.print('C', 'A');
            }
        }).start();
    }
}
