public class Employee {
    private String employeeId;
    private String employeeName;
    private String department;
    private String email;
    private int availableLeaveBalance;

    public Employee(String employeeId, String employeeName,
                    String department, String email,
                    int availableLeaveBalance) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.department = department;
        this.email = email;
        this.availableLeaveBalance = availableLeaveBalance;
    }

    public String getEmployeeId() { return employeeId; }
    public String getEmployeeName() { return employeeName; }
    public String getDepartment() { return department; }
    public String getEmail() { return email; }
    public int getAvailableLeaveBalance() { return availableLeaveBalance; }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAvailableLeaveBalance(int balance) {
        if (balance < 0) {
            throw new IllegalArgumentException("Leave balance cannot be negative.");
        }
        this.availableLeaveBalance = balance;
    }

    public void deductLeave(int days) {
        if (days <= 0) {
            throw new IllegalArgumentException("Leave days must be greater than 0.");
        }
        if (days > availableLeaveBalance) {
            throw new IllegalArgumentException("Insufficient leave balance.");
        }
        availableLeaveBalance -= days;
    }

    protected String getEmployeeType() {
        return "Employee";
    }

    public void displayDetails() {
        System.out.println("Employee ID : " + employeeId);
        System.out.println("Employee Name : " + employeeName);
        System.out.println("Department : " + department);
        System.out.println("Email : " + email);
        System.out.println("Leave Balance : " + availableLeaveBalance);
        System.out.println("Employee Type : " + getEmployeeType());
    }

    // Method overloading
    public void displayDetails(String heading) {
        System.out.println("\n--- " + heading + " ---");
        displayDetails();
    }
}
