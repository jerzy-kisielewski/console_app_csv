import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CSVFile {

    private final String FILE_PATH = "src/5TP_Zadanie programistyczne pod INF.04 - Raport obecności 6-09-24 (5TP).csv";
    private final String OUTPUT_FILE_NAME = "5TP_Zadanie-INF04.csv";
    private boolean inMembersSection = false;

    List<Member> members = new ArrayList<>();

    public CSVFile() {
        readFile();
        members.sort(Comparator.comparing(Member::getLastName));
        writeFile();
    }

    private void readFile(){
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while((line = br.readLine()) != null) {
                line = line.trim();
                if(line.replace("\0", "").contains("Imie")){
                    inMembersSection = true;
                    continue;
                }

                if(inMembersSection){
                    if(line.replace("\0", "").contains("3. Dzia")){
                        inMembersSection = false;
                        break;
                    } else {
                        String columns[] = line.replace("\0", "").split("\t");
                        if(columns.length >= 6){
                            String name = columns[0].trim();
                            String[] nameParts = name.split(" ");
                            String firstName = nameParts[1].isEmpty() ?  " - " : nameParts[0];
                            String lastName = nameParts[1].isEmpty() ? nameParts[0] : nameParts[1];

                            String durationString = columns[3].trim();

                            Member member = new Member(firstName, lastName, durationString);
                            members.add(member);
                        }
                    }
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeFile(){
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(OUTPUT_FILE_NAME));
            bw.write("Nazwisko,Imię,Status,Czas_uczestnictwa");
            bw.newLine();
            for(Member member : members){
                String lastName = member.getLastName();
                String firstName = member.getFirstName();
                String certificateStatus = member.getCertificateStatus();
                String courseDuration = member.getCourseDuration();
                bw.write(lastName + "," + firstName + "," + certificateStatus + "," + courseDuration);
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void showSortedData(){
        System.out.println("Dane zostaną zapisane w pliku " + OUTPUT_FILE_NAME + " jako:");
        System.out.println("Nazwisko,Imię,Status,Czas_uczestnictwa");
        for(Member member : members){
            System.out.println(member.getLastName() + "," + member.getFirstName() + "," + member.getCertificateStatus() + "," +  member.getCourseDuration());
        }
    }
}
