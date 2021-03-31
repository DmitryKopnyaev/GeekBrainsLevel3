package lesson4;

//2. Создать MFU c функциями, сканирования, печати и ксерокопирования

public class MFU {
    final Object printObj = new Object();
    final Object scanObj = new Object();

    public void print(String doc) {
        synchronized (printObj) {
            System.out.printf("Print %s begin\n", doc);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.printf("Print %s end\n", doc);
        }
    }

    public void scan(String doc, Variants v) {
        synchronized (scanObj) {
            switch (v) {
                case Scan:
                    System.out.printf("Scan %s begin\n", doc);
                    try {
                        Thread.sleep(4000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.printf("Scan %s end\n", doc);
                    break;
                case Copy:
                    synchronized (printObj) {
                        System.out.printf("Copy %s begin\n", doc);
                        try {
                            Thread.sleep(6000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.printf("Copy %s end\n", doc);
                        break;
                    }
            }
        }
    }

    public static void main(String[] args) {
        MFU mfu = new MFU();

        new Thread(new Runnable() {
            @Override
            public void run() {
                mfu.print("Doc 1");
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                mfu.print("Doc 2");
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                mfu.scan("Doc 2", Variants.Scan);
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                mfu.scan("Doc 2", Variants.Copy);
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                mfu.scan("Doc 3", Variants.Copy);
            }
        }).start();

    }
}
