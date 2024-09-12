public class LocationAward {
    private int id;
    private String name;

    public LocationAward(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static LocationAward[] locationAwards(){
        LocationAward[] locationAwardList = new LocationAward[3];
        locationAwardList[0] = new LocationAward(1, "<Food>");
        locationAwardList[1] = new LocationAward(2, "<Firewood>");
        locationAwardList[2] = new LocationAward(3, "<Water>");

        return locationAwardList;
    }

    public static LocationAward getAwardObjByID(int id){
        for (LocationAward l : LocationAward.locationAwards()){
            if (l.getId() == id){
                return l;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
