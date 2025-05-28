public class Subject {
    private Integer Grade;
    private Double Weight;

    public Subject(Double Weight) {
        this.Weight = Weight;
    }

    public Integer getGrade() {
        return Grade;
    }

    public void setGrade(Integer grade) {
        Grade = grade;
    }

    public Double getWeight() {
        return Weight;
    }

    public void setWeight(Double weight) {
        Weight = weight;
    }

    public Double CalculateScore(Integer Grade, Double Weight){
        return Grade.doubleValue() * (Weight * 100) * 10;
    }
}
