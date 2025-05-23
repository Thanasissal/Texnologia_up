public class Subject {
    public Integer Grade;
    public Integer Weight;

    public Subject() {}

    public Integer CalculateScore(Integer Grade, Integer Weight){
        return Grade * Weight * 10;
    }
}
