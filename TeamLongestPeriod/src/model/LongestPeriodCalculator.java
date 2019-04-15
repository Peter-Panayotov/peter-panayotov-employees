package model;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.DataReader;

public class LongestPeriodCalculator {
	private String path;

	public LongestPeriodCalculator(String path) {
		this.path = path;
	}

	public void calculate() throws FileNotFoundException {
		DataReader reader = new DataReader(path);
		long[] longestWorkingTeam = calculateCommonWorkingDays(readDataFile(reader));
		System.out.printf("Employees: %d, %d - %d days", longestWorkingTeam[0], longestWorkingTeam[1],
				longestWorkingTeam[2]);
	}

	private List<Employee> readDataFile(DataReader reader){
		List<Employee> allEmployees = new ArrayList<>();
		String[] employeeProjectData = reader.readLine();
		
		while (employeeProjectData.length > 0) {
			int employeeId = Integer.parseInt(employeeProjectData[0]);
			int projectId = Integer.parseInt(employeeProjectData[1]);
			LocalDate dateFrom = LocalDate.parse(employeeProjectData[2]);
			LocalDate dateTo = employeeProjectData[3].equals("NULL") ? LocalDate.now() : LocalDate.parse(employeeProjectData[3]);
			Project project = new Project(projectId, dateFrom, dateTo);

			if (allEmployees.stream().anyMatch(e -> e.getEmployeeId() == employeeId)) {
				Employee employee = allEmployees.stream()
						.filter((g -> g.getEmployeeId() == employeeId))
						.collect(Collectors.toList()).get(0);
				employee.addProject(project);
			} else {
				Employee employee = new Employee(employeeId);
				employee.addProject(project);
				allEmployees.add(employee);
			}

			employeeProjectData = reader.readLine();
		}
		
		return allEmployees;
	}
	
	private long[] calculateCommonWorkingDays(List<Employee> allEmployees) {
		Employee teamEmployee1 = new Employee();
		Employee teamEmployee2 = new Employee();
		long longestWorkingPeriod = 0;

		for (int i = 0; i < allEmployees.size() - 1; i++) {
			for (int j = i + 1; j < allEmployees.size(); j++) {
				Employee employee1 = allEmployees.get(i);
				Employee employee2 = allEmployees.get(j);
				long currentTeamWorkingPeriod = calculateCommonProjectsTime(employee1, employee2);

				if (currentTeamWorkingPeriod >= longestWorkingPeriod) {
					longestWorkingPeriod = currentTeamWorkingPeriod;
					teamEmployee1 = employee1;
					teamEmployee2 = employee2;
				}
			}
		}

		long[] longestWorkingTeam = { teamEmployee1.getEmployeeId(), teamEmployee2.getEmployeeId(),
				longestWorkingPeriod };
		return longestWorkingTeam;
	}

	private long calculateCommonProjectsTime(Employee employee1, Employee employee2) {
		long teamWorkingPeriod = 0;
		for (Project projectEmployee1 : employee1.getProjects()) {
			for (Project projectEmployee2 : employee2.getProjects()) {
				if (projectEmployee1.getProjectId() == projectEmployee2.getProjectId()) {
					teamWorkingPeriod += calculateTime(projectEmployee1, projectEmployee2);
				}
			}
		}

		return teamWorkingPeriod;
	}

	private long calculateTime(Project projectEmployee1, Project projectEmployee2) {
		LocalDate startPeriod = projectEmployee2.getDateFrom();
		LocalDate endPeriod = projectEmployee2.getDateTo();

		if (projectEmployee1.getDateFrom().isAfter(projectEmployee2.getDateFrom())) {
			startPeriod = projectEmployee1.getDateFrom();
		}

		if (projectEmployee1.getDateTo().isBefore(projectEmployee2.getDateTo())) {
			endPeriod = projectEmployee1.getDateTo();
		}

		long amountOfWorkingDays = ChronoUnit.DAYS.between(startPeriod, endPeriod);
		return amountOfWorkingDays;
	}
}
