package org.example.counter;

// 싱글톤 패턴에서 상태를 유지하게 설정하면 안된다.
public class Counter implements Runnable{
    private int count = 0;

    public void increment() {
        count++;
    }
    public void decrement() {
        count--;
    }
    public int getValue() {
        return count;
    }
    @Override
    public void run() {
        this.increment();
        System.out.println("Value for Thread After incrment " + Thread.currentThread().getName() + " " + this.getValue()); // 1
        this.decrement();
        System.out.println("Vakye for Thread at last" + Thread.currentThread().getName() + " " + this.getValue()); // 0
    }
}
