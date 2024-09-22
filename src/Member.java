public class Member {
    private String firstName;
    private String lastName;
    private String courseDuration;
    private String certificateStatus;

    public Member(String firstName, String lastName, String courseDuration) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.courseDuration = courseDuration;
        this.certificateStatus = checkCertificateStatus() ? "Tak" : "Nie";
    }

    public String getLastName(){
        return lastName;
    }

    private boolean checkCertificateStatus(){
        String[] parts = this.courseDuration.replace("\0", "").split(" ");
        return parts.length == 6;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getCourseDuration() {
        return courseDuration;
    }

    public String getCertificateStatus() {
        return certificateStatus;
    }
}
