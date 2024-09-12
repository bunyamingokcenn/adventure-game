import java.util.Arrays;
import java.util.Random;

public abstract class BattleLoc extends Location {
    private Obstacle obstacle;
    private int maxObstacle;
    private final LocationAward locationAward;



    public BattleLoc(Player player, String name, Obstacle obstacle, int maxObstacle, LocationAward locationAward) {
        super(player, name);
        this.obstacle = obstacle;
        this.maxObstacle = maxObstacle;
        this.locationAward = locationAward;

    }


    @Override
    public boolean onLocation() {
        // locationAward'ın null olup olmadığını kontrol ediyoruz
        if (this.locationAward != null && this.getPlayer().getInventory().checkInventoryStatus(this.locationAward.getId())) {
            System.out.println(this.getName() + " bölgesinde savaştınız ve ödül kazandınız. Tekrar giremezsiniz.");
            return false;
        }

        int obsNumber = this.randomObstacleNumber();
        System.out.println("Şuan buradasınız: " + this.getName());
        System.out.println("Dikkatli ol! Burada " + obsNumber + " tane " + this.getObstacle().getObstacleName() + " yaşıyor!");

        String selectCase = askWarCase();

        if (selectCase.equals("S") && combat(obsNumber)) {
            System.out.println(this.getName() + " bölgesindeki tüm düşmanları yendiniz !!!");

            if (this.locationAward != null) { // Eğer bir ödül varsa
                this.getPlayer().getInventory().setLocationAward(this.locationAward.getId());
                System.out.println(this.getName() + " bölgesinin ödülü " + this.locationAward.getName() + " envanterinize eklendi.");
            } else {
                System.out.println(this.getName() + " bölgesinde ödül bulunmamaktadır.");
            }

            System.out.println("Envanterinizdeki eşyalar:  ");
            this.getPlayer().getInventory().printInventoryInfo();
            return true;

        }

        if (this.getPlayer().getHealth() <= 0) {
            System.out.println("Öldünüz !!!");
            return false;
        }

        return false;
    }


    public boolean combat(int obsNumber){
        Random random = new Random();
        boolean playerStarts = random.nextBoolean();

        for (int i = 1; i <= obsNumber; i++){
            this.getObstacle().setHealth(this.getObstacle().getOriginalHealth());
            if (this.getObstacle().getId() == 4 ){
                //Random Yılan Hasarı
                int min = 3;
                int max = 6;
                int randomDamage = random.nextInt((max - min) + 1) + min;
                this.getObstacle().setDamage(randomDamage);

                //Random Yılan Ödülü
                this.getObstacle().setAward(getLoot());

            }

            System.out.println();
            playerStats();
            obstacleStats(i);
            System.out.println();

            if(playerStarts){//önce oyuncu saldırırsa

                while(this.getPlayer().getHealth() > 0 && this.getObstacle().getHealth() > 0){
                    String selectCase =  askWarCase();
                    System.out.println();

                    if (selectCase.equals("S")){
                        System.out.println("< İlk saldırı hamlesini sen yaptın maceracı ! >");

                        // Oyuncunun sırası
                        playerAttack();

                        if(this.getObstacle().getHealth() > 0){
                        // Canavarın sırası
                            obstacleAttack();
                        }
                    }else {
                        System.out.println("Savaştan kaçıldı ...");
                        return false;
                    }
                }
            }else{//Önce canavar saldırırsa
                while (this.getPlayer().getHealth() >= 0 && this.getObstacle().getHealth() > 0) {
                    //Bilgi
                    System.out.println();
                    System.out.println("< Dikkat ! Önce canavar saldırmaya başladı !! >");
                    // Canavarın sırası
                    obstacleAttack();

                    String selectCase = askWarCase();

                    if (selectCase.equals("S")) {
                        // Oyuncunun Sırası
                        playerAttack();
                    } else {
                        System.out.println("Savaştan kaçıldı ...");
                        return false;
                    }
                }
            }

            //Canavarı yenme durumu
            if (this.getObstacle().getHealth() < this.getPlayer().getHealth()){
                System.out.println("Düşmanı yendiniz !");

                if (this.getObstacle().getId() == 4){
                    int lootId = getLoot();

                    switch (lootId){
                        case 0:
                            System.out.println();
                            System.out.println("Düşmandan hiçbir ödül kazanamadınız !");
                            break;
                        case 1:
                            //Tüfek Kazanma
                            setWeaponFromSnake(3, "Tüfek");
                            break;
                        case 2:
                            //Kılıç Kazanma
                            if (!(this.getPlayer().getInventory().getArmor().getId() == 3 ) ){
                                setWeaponFromSnake(2, "Kılıç");
                            }
                            break;
                        case 3:
                            //Tabanca Kazanma
                            if (!( this.getPlayer().getInventory().getWeapon().getId() == 2 || this.getPlayer().getInventory().getWeapon().getId() == 3 ) ){
                                setWeaponFromSnake(1, "Tabanca");
                            }
                            break;
                        case 4:
                            //Ağır Zırh Kazanma
                            setArmorFromSnake(3, "Ağır Zırh");
                            break;
                        case 5:
                            //Orta Zırh Kazanma
                            if (!(this.getPlayer().getInventory().getWeapon().getId() == 3 ) ){
                                setArmorFromSnake(2, "Orta Zırh");
                            }
                            break;
                        case 6:
                            //Hafif Zırh Kazanma
                            if (!( this.getPlayer().getInventory().getArmor().getId() == 2 || this.getPlayer().getInventory().getArmor().getId() == 3 ) ){
                                setArmorFromSnake(1, "Hafif Zırh");
                            }
                            break;
                        case 7:
                            //10 Para Kazanma
                            addMoneyFromSnake(10);
                            break;
                        case 8:
                            //5 Para Kazanma
                            addMoneyFromSnake(5);
                            break;
                        case 9:
                            //1 Para Kazanma
                            addMoneyFromSnake(1);
                            break;
                    }
                }else {
                    defeatObstacle();
                }

            }else {//Yenilme durumu
                return false;
            }
        }
        return true;
    }

    //Oyuncu Bilgilerini Yazdırma
    public void playerStats(){
        System.out.println("Oyuncu Değerleri");
        System.out.println("-------------------------");
        System.out.println("Sağlık: " + this.getPlayer().getHealth());
        System.out.println("Silah: " + this.getPlayer().getInventory().getWeapon().getName());
        System.out.println("Hasar: " + this.getPlayer().getTotalDamage());
        System.out.println("Zırh: " + this.getPlayer().getInventory().getArmor().getName());
        System.out.println("Bloklama: " + this.getPlayer().getInventory().getArmor().getBlock());
        System.out.println("Para: " + this.getPlayer().getMoney());
        System.out.println();

    }

    //Oyuncu Saldırısı
    public void playerAttack(){
        System.out.println("Siz vurdunuz !!");
        this.getObstacle().setHealth(this.getObstacle().getHealth() - this.getPlayer().getTotalDamage());
        afterHit();
    }

    //Canavar Bilgilerini Yazdırma
    public void obstacleStats(int i){
        System.out.println(i + ". " + this.getObstacle().getObstacleName() + " Değerleri");
        System.out.println("-------------------------");
        System.out.println("Sağlık: " + this.getObstacle().getHealth());
        System.out.println("Hasar: " + this.getObstacle().getDamage());

        if (this.getObstacle().getId() == 4){
            System.out.println("Ödül: Sürpriz");
        }else {
            System.out.println("Ödül: " + this.getObstacle().getAward());
        }


    }

    //Canavar Saldırısı
    public void obstacleAttack(){
        System.out.println("Canavar size vurdu !");
        int obstacleDamage = this.getObstacle().getDamage() - this.getPlayer().getInventory().getArmor().getBlock();
        if (obstacleDamage < 0){
            System.out.println("Hasar alınmadı !!!");
            System.out.println("Hasar zırh tarafından engellendi !");
            obstacleDamage = 0;
        }
        this.getPlayer().setHealth(this.getPlayer().getHealth() - obstacleDamage);
        afterHit();
    }

    //Saldırı sonrası değerleri ekrana yazdırma
    public void afterHit(){
        System.out.println("Sağlığınız: " + this.getPlayer().getHealth());
        System.out.println(this.getObstacle().getObstacleName() + " Sağlığı: " + this.getObstacle().getHealth());
        System.out.println("-----------------------");
    }

    //Canavarı Yenme Durumu
    public void defeatObstacle(){





            //Diğer Savaş Bölgelerinde Canavar Yenme Durumu
            System.out.println(this.getObstacle().getAward() + " para kazandınız !");
            this.getPlayer().setMoney(this.getPlayer().getMoney() + this.getObstacle().getAward());


        System.out.println("Güncel Paranız: " + this.getPlayer().getMoney());
        System.out.println();

    }

 /*   public void defeatSnake(int lootCase){
        int snakeAward = this.getObstacle().getAward();
        switch (snakeAward){
            case 0:
                System.out.println("Düşmandan hiçbir ödül kazanamadınız !");
                break;
            case 1:
                System.out.println();
                System.out.println("Tabanca Kazandınız!");
                setWeaponFromSnake(1);
                break;
            case 2:
                System.out.println();
                System.out.println("Kılıç Kazandınız!");
                setWeaponFromSnake(2);
                break;
            case 3:
                System.out.println();
                System.out.println("Tüfek Kazandınız!");
                setWeaponFromSnake(3);
                break;
            case 4:
                System.out.println();
                System.out.println("Ağır Zırh Kazandınız!");
                setArmorFromSnake(3);
                break;
            case 5:
                System.out.println();
                System.out.println("Orta Zırh Kazandınız!");
                setArmorFromSnake(2);
                break;
            case 6:
                System.out.println();
                System.out.println("Hafif Zırh Kazandınız!");
                setArmorFromSnake(1);
                break;
            case 7:
                System.out.println();
                System.out.println("10 Para Kazandınız!");
                addMoneyFromSnake(10);
                break;
            case 8:
                System.out.println();
                System.out.println("5 Para Kazandınız!");
                addMoneyFromSnake(5);
                break;
            case 9:
                System.out.println();
                System.out.println("1 Para Kazandınız!");
                addMoneyFromSnake(1);
                break;

        }
    }*/

    //Rastgele Canavar Sayısı
    public int randomObstacleNumber(){
        Random r = new Random();
        return r.nextInt(this.getMaxObstacle()) + 1;
    }

    //Savaş ya da Kaç Durumunu Sor
    public String askWarCase(){
        System.out.println("------------------------------------------------");
        System.out.println("|   Savaşmak için < S >  |  Kaçmak için < K >  |");
        System.out.println("------------------------------------------------");
        System.out.print("Seçimini yap maceracı: ");

        return input.next().toUpperCase();
    }

    //Yılandan Silah Düşerse
    public void setWeaponFromSnake(int id , String weaponName){
        Weapon selectedWeapon = Weapon.getWeaponObjByID(id);
        this.getPlayer().getInventory().setWeapon(selectedWeapon);
        System.out.println();
        System.out.println(weaponName +  " Kazandınız!");
        System.out.println();
    }

    //Yılandan Zırh Düşerse
    public void setArmorFromSnake(int id, String armorName){
        Armor selectedArmor = Armor.getArmorObjByID(id);
        this.getPlayer().getInventory().setArmor(selectedArmor);
        System.out.println();
        System.out.println(armorName +  " Kazandınız!");
        System.out.println();
    }

    //Yılandan Para Düşerse
    public void addMoneyFromSnake(int snakeMoney){
        int playerMoney = this.getPlayer().getMoney();
        this.getPlayer().setMoney(playerMoney + snakeMoney);
        System.out.println();
        System.out.println(snakeMoney +  " Para Kazandınız!");
        System.out.println();

    }

    public int getLoot() {
        Random random = new Random();
        int lootChance = random.nextInt(100); // 0 ile 99 arasında rastgele sayı

        if (lootChance < 15) { // Silah kazanma olasılığı %15
            int weaponChance = random.nextInt(100);
            if (weaponChance < 20) { // Tüfek %20

                return 1; // Tüfek
            } else if (weaponChance < 50) { // Kılıç %30
                return 2; // Kılıç
            } else { // Tabanca %50
                return 3; // Tabanca
            }
        } else if (lootChance < 30) { // Zırh kazanma olasılığı %15
            int armorChance = random.nextInt(100);
            if (armorChance < 20) { // Ağır Zırh %20
                return 4; // Ağır Zırh
            } else if (armorChance < 50) { // Orta Zırh %30
                return 5; // Orta Zırh
            } else { // Hafif Zırh %50
                return 6; // Hafif Zırh
            }
        } else if (lootChance < 55) { // Para kazanma olasılığı %25
            int moneyChance = random.nextInt(100);
            if (moneyChance < 20) { // 10 Para %20
                return 7; // 10 Para
            } else if (moneyChance < 50) { // 5 Para %30
                return 8; // 5 Para
            } else { // 1 Para %50
                return 9; // 1 Para
            }
        } else { // Hiçbir şey kazanamama ihtimali %45
            return 0; // Hiçbir şey
        }
    }

    public Obstacle getObstacle() {
        return obstacle;
    }

    public void setObstacle(Obstacle obstacle) {
        this.obstacle = obstacle;
    }

    public int getMaxObstacle() {
        return maxObstacle;
    }

    public void setMaxObstacle(int maxObstacle) {
        this.maxObstacle = maxObstacle;
    }


}


