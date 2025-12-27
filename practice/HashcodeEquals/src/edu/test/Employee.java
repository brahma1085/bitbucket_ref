package edu.test;

class Student {
	private int age;

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public boolean equals(Object obj) {
		boolean flag = false;
		Student student = (Student) obj;
		if (student.age == age)
			flag = true;
		return flag;
	}

	public int hashCode() {
		return age;
	}
}

public class Employee {

	public static void main(String[] args) {
		Student student = new Student();
		student.setAge(25);
		System.out.println("student age==>" + student.getAge());
		System.out
				.println(student.hashCode() + "==>" + student.equals(student));
		int hash = System.identityHashCode(student);
		System.out.println(hash);
	}
}
