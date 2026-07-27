package com.wipro.entity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="students")
public class Student {
	
		@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    private String name;
	    private String dept;
	    private String location;
	    private String email;
	    
	    private String username;
	    private String password;
	    
		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getDept() {
			return dept;
		}

		public void setDept(String dept) {
			this.dept = dept;
		}

		public String getLocation() {
			return location;
		}

		public void setLocation(String location) {
			this.location = location;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public Student(Long id, String name, String dept, String location, String email ,String username ,String password) {
			super();
			this.id = id;
			this.name = name;
			this.dept = dept;
			this.location = location;
			this.email = email;
			this.username=username;
			this.password=password;
		}

		public Student() {
			super();
			// TODO Auto-generated constructor stub
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}
		
		
		
}
