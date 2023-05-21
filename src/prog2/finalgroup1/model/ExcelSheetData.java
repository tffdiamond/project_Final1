package prog2.finalgroup1.model;

public class ExcelSheetData {
    private int year;
    private int term;
    private String courseNumber;
    private String descriptiveTitle;
    private double units;
    private String grades;

    public ExcelSheetData(int year, int term, String courseNumber, String descriptiveTitle, double units,
                          String grade) {
        this.year = year;
        this.term = term;
        this.courseNumber = courseNumber;
        this.descriptiveTitle = descriptiveTitle;
        this.units = units;
        this.grades = grade;

}

    public void setYear(int year) {
        this.year = year;
    }

    public void setTerm(int term) {
        this.term = term;
    }

    public void setCourseNumber(String courseNumber) {
        this.courseNumber = courseNumber;
    }

    public void setDescriptiveTitle(String descriptiveTitle) {
        this.descriptiveTitle = descriptiveTitle;
    }

    public void setUnits(double units) {
        this.units = units;
    }

    public int getYear() {
        return year;
    }

    public int getTerm() {
        return term;
    }

    public String getCourseNumber() {
        return courseNumber;
    }

    public String getDescriptiveTitle() {
        return descriptiveTitle;
    }

    public double getUnits() {
        return units;
    }

    public String getGrades() {
        return grades;
    }

    public void setGrades(String grades) {
        this.grades = grades;
    }

    @Override
    public String toString() {
        return "ExcelSheetPureData{" +
                "year=" + year +
                ", term=" + term +
                ", courseNumber='" + courseNumber + '\'' +
                ", descriptiveTitle='" + descriptiveTitle + '\'' +
                ", units=" + units +
                '}';
    }
}
