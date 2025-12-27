package edu;

public class IncrementId {
	int id;
	static int count;

	public IncrementId() {
		count++;
		id = 420 + count;
	}

	public static void main(String[] args) {
		System.out.println("number of objects created==>" + count);
		IncrementId id, id2, id3, id4;
		id = new IncrementId();
		System.out.println("number of objects created==>" + count);
		id2 = new IncrementId();
		System.out.println("number of objects created==>" + count);
		id3 = new IncrementId();
		System.out.println("number of objects created==>" + count);
		id4 = new IncrementId();
		System.out.println("number of objects created==>" + count);
		System.out.println(id.id);
		System.out.println(id2.id);
		System.out.println(id3.id);
		System.out.println(id4.id);
	}
}
