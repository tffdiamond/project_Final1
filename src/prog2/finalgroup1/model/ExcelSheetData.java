package prog2.finalgroup1.model;

public class ExcelSheetData {
    private int year;
    private int term;
    private String courseNumber;
    private String descriptiveTitle;
    private double units;
    private String grades;

    /**
     *
     * @param year
     * @param term
     * @param courseNumber
     * @param descriptiveTitle
     * @param units
     * @param grade
     */
    public ExcelSheetData(int year, int term, String courseNumber, String descriptiveTitle, double units,
                          String grade)
    {
        this.year = year;
        this.term = term;
        this.courseNumber = courseNumber;
        this.descriptiveTitle = descriptiveTitle;
        this.units = units;
        this.grades = grade;
    }

    /**
     *
     * @param year
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     *
     * @param term
     */

    public void setTerm(int term) {
        this.term = term;
    }

    /**
     *
     * @param courseNumber
     */
    public void setCourseNumber(String courseNumber) {
        this.courseNumber = courseNumber;
    }

    /**
     *
     * @param descriptiveTitle
     */
    public void setDescriptiveTitle(String descriptiveTitle) {
        this.descriptiveTitle = descriptiveTitle;
    }

    /**
     *
     * @param units
     */
    public void setUnits(double units) {
        this.units = units;
    }

    /**
     *
     * @return
     */
    public int getYear() {
        return year;
    }

    /**
     *
     * @return
     */
    public int getTerm() {
        return term;
    }

    /**
     *
     * @return
     */
    public String getCourseNumber() {
        return courseNumber;
    }

    /**
     *
     * @return
     */
    public String getDescriptiveTitle() {
        return descriptiveTitle;
    }

    /**
     *
     * @return
     */
    public double getUnits() {
        return units;
    }

    /**
     *
     * @return
     */
    public String getGrades() {
        return grades;
    }

    /**
     *
     * @param grades
     */
    public void setGrades(String grades) {
        this.grades = grades;
    }

    /**
     *
     * @return
     */
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
