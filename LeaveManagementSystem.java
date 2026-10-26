import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class LeaveManagementSystem {
    private final java.util.Scanner sc = new java.util.Scanner(System.in);

    // Collections required by the assessment
    private final List<Employee> employees = new ArrayList<>();
    private final Set<String> emails = new HashSet<>();
    private final Map<String, Employee> employeeMap = new HashMap<>();
    private final List<LeaveRequest> leaveRequests = new ArrayList<>();

    private int requestCounter = 1001;

    public static void main(String[] args) {
        new LeaveManagementSystem().start();
    }

    public void start() {
        boolean running = true;

        while (running) {
            System.out.println("\n===== EMPLOYEE LEAVE MANAGEMENT SYSTEM =====");
            System.out.println("1. Add Employee");
            System.out.println("2. Display All Employees");
            System.out.println("3. Search Employee");
            System.out.println("4. Apply Leave");
            System.out.println("5. Approve Leave");
            System.out.println("6. Reject Leave");
            System.out.println("7. Display All Leave Requests");
            System.out.println("8. Display Employee Leave Balance");
            System.out.println("9. Exit");

            int choice = readInt("Enter your choice: ");

            try {
                switch (choice) {
                    case 1: addEmployee(); break;
                    case 2: displayAllEmployees(); break;
                    case 3: searchEmployee(); break;
                    case 4: applyLeave(); break;
                    case 5: approveLeave(); break;
                    case 6: rejectLeave(); break;
                    case 7: displayAllLeaveRequests(); break;
                    case 8: displayLeaveBalance(); break;
                    case 9:
                        running = false;
                        System.out.println("Thank you for using the system.");
                        break;
                    default:
                        System.out.println("Invalid menu choice.");
                }
            } catch (EmployeeNotFoundException |
                     InsufficientLeaveException |
                     IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Operation failed: " + e.getMessage());
            }
        }
        sc.close();
    }

    private int readInt(String message) {
        while (true) {
            System.out.print(message);
            String input = sc.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer.");
            }
        }
    }

    private Employee findEmployee(String employeeId)
            throws EmployeeNotFoundException {
        Employee employee = employeeMap.get(employeeId);
        if (employee == null) {
            throw new EmployeeNotFoundException(
                "Employee " + employeeId + " does not exist."
            );
        }
        return employee;
    }

    // Option 1
    private void addEmployee() {
        System.out.println("\n--- Add Employee ---");
        System.out.print("Enter Employee ID: ");
        String id = sc.nextLine().trim();
        System.out.print("Enter Employee Name: ");
        String name = sc.nextLine().trim();
        System.out.print("Enter Department: ");
        String department = sc.nextLine().trim();
        System.out.print("Enter Email: ");
        String email = sc.nextLine().trim();
        int balance = readInt("Enter Leave Balance: ");
        System.out.print("Employee Type (Permanent/Contract): ");
        String type = sc.nextLine().trim();

        if (id.isEmpty() || name.isEmpty() || department.isEmpty() || email.isEmpty()) {
            throw new IllegalArgumentException("Employee fields cannot be empty.");
        }
        if (employeeMap.containsKey(id)) {
            throw new IllegalArgumentException("Duplicate employee ID: " + id);
        }
        String normalizedEmail = email.toLowerCase(Locale.ROOT);
        if (emails.contains(normalizedEmail)) {
            throw new IllegalArgumentException("Duplicate email: " + email);
        }
        if (balance < 0) {
            throw new IllegalArgumentException("Leave balance cannot be negative.");
        }

        Employee employee;
        if (type.equalsIgnoreCase("Permanent")) {
            employee = new PermanentEmployee(id, name, department, email, balance);
        } else if (type.equalsIgnoreCase("Contract")) {
            employee = new ContractEmployee(id, name, department, email, balance);
        } else {
            throw new IllegalArgumentException(
                "Employee type must be Permanent or Contract."
            );
        }

        employees.add(employee);
        employeeMap.put(id, employee);
        emails.add(normalizedEmail);
        System.out.println("Employee added successfully.");
    }

    // Option 2: Iterator is explicitly used as required.
    private void displayAllEmployees() {
        System.out.println("\n--- All Employees ---");
        if (employees.isEmpty()) {
            System.out.println("No employees found.");
            return;
        }
        Iterator<Employee> iterator = employees.iterator();
        while (iterator.hasNext()) {
            iterator.next().displayDetails();
            System.out.println("------------------------------");
        }
    }

    // Option 3
    private void searchEmployee() throws EmployeeNotFoundException {
        System.out.print("Enter Employee ID: ");
        Employee employee = findEmployee(sc.nextLine().trim());
        employee.displayDetails("Employee Details");
    }

    // Option 4
    private void applyLeave()
            throws EmployeeNotFoundException, InsufficientLeaveException {
        System.out.println("\n--- Apply Leave ---");
        System.out.print("Enter Employee ID: ");
        String id = sc.nextLine().trim();
        Employee employee = findEmployee(id);

        System.out.print("Enter Leave Type (CASUAL/SICK/EARNED): ");
        String typeInput = sc.nextLine().trim();
        LeaveRequest.LeaveType leaveType;
        try {
            leaveType = LeaveRequest.LeaveType.valueOf(
                typeInput.toUpperCase(Locale.ROOT)
            );
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                "Leave type must be CASUAL, SICK, or EARNED."
            );
        }

        int days = readInt("Enter Number of Days: ");
        if (days <= 0) {
            throw new IllegalArgumentException("Leave days must be greater than 0.");
        }
        if (days > employee.getAvailableLeaveBalance()) {
            throw new InsufficientLeaveException(
                "Available Leave: " + employee.getAvailableLeaveBalance()
                + ", Requested Leave: " + days
            );
        }

        System.out.print("Enter Reason: ");
        String reason = sc.nextLine().trim();
        if (reason.isEmpty()) {
            throw new IllegalArgumentException("Leave reason cannot be empty.");
        }

        String requestId = "LR" + requestCounter++;
        LeaveRequest request = new LeaveRequest(
            requestId, id, leaveType, days, reason
        );
        leaveRequests.add(request);
        System.out.println("Leave request created successfully.");
        System.out.println("Request ID: " + requestId);
        System.out.println("Status: " + request.getStatus());
    }

    private LeaveRequest findRequest(String requestId) {
        for (LeaveRequest request : leaveRequests) {
            if (request.getRequestId().equalsIgnoreCase(requestId)) {
                return request;
            }
        }
        throw new IllegalArgumentException(
            "Leave request " + requestId + " does not exist."
        );
    }

    // Option 5
    private void approveLeave()
            throws EmployeeNotFoundException, InsufficientLeaveException {
        System.out.print("Enter Request ID: ");
        LeaveRequest request = findRequest(sc.nextLine().trim());

        if (request.getStatus() != LeaveRequest.Status.PENDING) {
            throw new IllegalArgumentException(
                "Only pending requests can be approved."
            );
        }

        Employee employee = findEmployee(request.getEmployeeId());
        int days = request.getNumberOfDays();
        int previousBalance = employee.getAvailableLeaveBalance();

        // Recheck balance when approving, not only when applying.
        if (days > previousBalance) {
            throw new InsufficientLeaveException(
                "Available Leave: " + previousBalance
                + ", Requested Leave: " + days
            );
        }

        employee.deductLeave(days);
        request.setStatus(LeaveRequest.Status.APPROVED);
        System.out.println("\nLeave Request Approved.");
        System.out.println("Employee ID : " + employee.getEmployeeId());
        System.out.println("Previous Balance: " + previousBalance);
        System.out.println("Leave Used : " + days);
        System.out.println("Remaining Leave : "
                           + employee.getAvailableLeaveBalance());
    }

    // Option 6
    private void rejectLeave() {
        System.out.print("Enter Request ID: ");
        LeaveRequest request = findRequest(sc.nextLine().trim());

        if (request.getStatus() != LeaveRequest.Status.PENDING) {
            throw new IllegalArgumentException(
                "Only pending requests can be rejected."
            );
        }
        request.setStatus(LeaveRequest.Status.REJECTED);
        System.out.println("Leave request rejected successfully.");
        System.out.println("Employee leave balance remains unchanged.");
    }

    // Option 7
    private void displayAllLeaveRequests() {
        System.out.println("\n--- All Leave Requests ---");
        if (leaveRequests.isEmpty()) {
            System.out.println("No leave requests found.");
            return;
        }
        for (LeaveRequest request : leaveRequests) {
            request.displayRequest();
        }
    }

    // Option 8
    private void displayLeaveBalance() throws EmployeeNotFoundException {
        System.out.print("Enter Employee ID: ");
        Employee employee = findEmployee(sc.nextLine().trim());
        System.out.println("Employee ID : " + employee.getEmployeeId());
        System.out.println("Employee Name : " + employee.getEmployeeName());
        System.out.println("Available Leave Balance : "
                           + employee.getAvailableLeaveBalance());
    }
}
