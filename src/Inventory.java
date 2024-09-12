public class Inventory {
    private Weapon weapon;
    private Armor armor;
    private final LocationAward[] locationAward = new LocationAward[3];

    public Inventory() {
        this.weapon = new Weapon(-1, "Yumruk", 0, 0);
        this.armor = new Armor(-1, "Paçavra", 0, 0);

    }

    public void printInventoryInfo(){
        for(LocationAward l : locationAward){
            if (l == null){
                continue;
            }
            System.out.print(l.getId() + "- " + l.getName());
            System.out.println();
        }
    }

    public boolean checkInventoryStatus(int id){
        boolean stats= false;

        for(LocationAward award : locationAward){
            if (award == null){
                break;
            }
            if (award.getId() == id){
                stats = true;
                break;
            }
        }
        return stats;
    }

    public boolean checkWinCase(){
        int awardCount = 0;
        for (LocationAward award : locationAward){
            if (award != null){
                awardCount++;
            }
        }
        if (awardCount == locationAward.length){
            System.out.println("Bütün materyalleri toplayarak güvenli eve döndün ve oyunu kazandın !!!");
            System.out.println("Bu tehlikeli diyarlardan kurtulmayı başardın maceracı !!!");
            System.out.println("########################################################");
            System.out.println("################## !!! TEBRİKLER !!!  ##################");
            System.out.println("########################################################");
            return true;
        }
        return false;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public Armor getArmor() {
        return armor;
    }

    public void setArmor(Armor armor) {
        this.armor = armor;
    }

    public LocationAward[] getLocationAward() {
        return locationAward;
    }

    public void setLocationAward(int id) {
        this.locationAward[id -1 ] = LocationAward.getAwardObjByID(id);
    }


}
