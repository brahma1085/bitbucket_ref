package com.obj;

class Student {
	int x;
	int y;
}

public class ObjectDisplay {
	public static void main(String[] args) {
		Student student1 = new Student();
		student1.x = 6;
		student1.y = 7;
		Student student2 = new Student();
		student2.x = 12;
		student2.y = 17;
		System.out.println(student1);
		System.out.println(student2);
		System.out.println(student1.x);
		System.out.println(student1.y);
		System.out.println(student2.x);
		System.out.println(student2.y);
		student1 = student2;
		System.out.println(student1);
		System.out.println(student2);
		System.out.println(student1.x);
		System.out.println(student1.y);
		System.out.println(student2.x);
		System.out.println(student2.y);
		student1.x = 534;
		student1.y = 76;
		student2.x = 23423;
		student2.y = 234;
		System.out.println(student1.x + "==>" + student1.y);
	}
}
