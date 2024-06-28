// Print numbers from 1 .. 100 using three threads.
public class PrintNumbers {
    public static void main(String[] args) {
        PrintNumbersWorker p1 = new PrintNumbersWorker(1);
        PrintNumbersWorker p2 = new PrintNumbersWorker(2);
        PrintNumbersWorker p3 = new PrintNumbersWorker(0);
        Thread t1 = new Thread(p1,"T1");
        Thread t2 = new Thread(p2,"T2");
        Thread t3 = new Thread(p3,"T3");
        t1.start();t2.start();t3.start();
    }
}

class PrintNumbersWorker implements Runnable {
    int remainder;
    public int PRINT_NUMBERS_UPTO=100;
    static int  number=1;
    static Object lock=new Object();
    PrintNumbersWorker(int remainder) {
        this.remainder=remainder;
    }
    @Override
    public void run() {
        while (number < PRINT_NUMBERS_UPTO-1) {
            synchronized (lock) {
                while (number % 3 != remainder) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(Thread.currentThread().getName() + " " + number);
                number++;
                lock.notifyAll();
            }
        }
    }
}
