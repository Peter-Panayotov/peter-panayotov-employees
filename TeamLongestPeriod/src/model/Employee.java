package model;

import java.util.ArrayList;
import java.util.List;

public class Employee {
	private int employeeId;
	private List<Project> projects;

	public Employee(int employeeId) {
		this.employeeId = employeeId;
		this.projects = new ArrayList<>();
	}

	public Employee() {
	}

	public int getEmployeeId() {
		return this.employeeId;
	}

	public List<Project> getProjects() {
		return this.projects;
	}

	public void addProject(Project newProject) {
		this.projects.add(newProject);
	}
}
