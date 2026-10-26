public class LeaveRequest {
    public enum LeaveType { CASUAL, SICK, EARNED }
    public enum Status { PENDING, APPROVED, REJECTED }

    private String requestId;
    private String employeeId;
    private LeaveType leaveType;
    private int numberOfDays;
    private String reason;
    private Status status;

    public LeaveRequest(String requestId, String employeeId,
                        LeaveType leaveType, int numberOfDays,
                        String reason) {
        this.requestId = requestId;
        this.employeeId = employeeId;
        this.leaveType = leaveType;
        this.numberOfDays = numberOfDays;
        this.reason = reason;
        this.status = Status.PENDING;
    }

    public String getRequestId() { return requestId; }
    public String getEmployeeId() { return employeeId; }
    public LeaveType getLeaveType() { return leaveType; }
    public int getNumberOfDays() { return numberOfDays; }
    public String getReason() { return reason; }
    public Status getStatus() { return status; }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void displayRequest() {
        System.out.println("Request ID : " + requestId);
        System.out.println("Employee ID : " + employeeId);
        System.out.println("Leave Type : " + leaveType);
        System.out.println("Days : " + numberOfDays);
        System.out.println("Reason : " + reason);
        System.out.println("Status : " + status);
        System.out.println("------------------------------");
    }
}
