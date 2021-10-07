package test2.thread;

public class Test {
	public static void main(String[] args) {
		A o1 = new A();
		o1.setName("o1-thread");
		o1.start();
		
		A o2 = new A();
		o2.setName("o2-thread");
		o2.start();
	}
}

class A extends Thread{
	public void run() {
		for(int i=0;i<100;i++) {
			System.out.println(getName()+" : "+i+"+1="+(i+1));
			try {
				sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}