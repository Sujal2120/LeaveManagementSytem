public class PermanentEmployee extends Employee {
    public PermanentEmployee(String employeeId, String employeeName,
                             String department, String email,
                             int availableLeaveBalance) {
        super(employeeId, employeeName, department, email, availableLeaveBalance);
    }

    @Override
    protected String getEmployeeType() {
        return "Permanent";
    }
}
