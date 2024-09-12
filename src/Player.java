import java.util.Scanner;

public class Player {
    private int damage;
    private int health;
    private int originalHealth;
    private int money;
    private String charName;
    private String name;
    private Scanner input = new Scanner(System.in);
    private Inventory inventory;


    public Player(String name){
        this.name = name;
        this.inventory = new Inventory();
    }

    public void selectChar(){

        GameChar[] charList = {new Samurai(), new Archer(), new Knight()};

        System.out.println("---------------------------------------------------------------------");
        System.out.println("Karakterler");
        System.out.println("---------------------------------------------------------------------");
        for(GameChar gameChar : charList){
            System.out.println("ID : " + gameChar.getId() + " " +
                    "\tKarakter : "+ gameChar.getName() + " " +
                    "\tHasar : "+ gameChar.getDamage() +" " +
                    "\tSağlık : "+ gameChar.getHealth() + " " +
                    "\tPara : "+ gameChar.getMoney() );

        }
        System.out.println("---------------------------------------------------------------------");
        System.out.print("Lütfen bir karakter seçiniz: ");

        int selectChar = getValidCharacter();

        while((selectChar < 1 || selectChar > 3) && selectChar !=999){
            System.out.print("Geçersiz değer, tekrar giriniz: ");
            selectChar = input.nextInt();
        }
        switch (selectChar){
            case 1:
                initPlayer(new Samurai());
                break;
            case 2:
                initPlayer(new Archer());
                break;
            case 3:
                initPlayer(new Knight());
                break;
            case 999:
                initPlayer(new GoldenEggChar());
                break;
            default:
                initPlayer(new Samurai());
        }

        System.out.println("Seçtiğiniz Karakter:");
        System.out.println("Karakter : " + this.getCharName() + " " +
                "\tHasar : "+ this.getDamage() +" " +
                "\tSağlık : "+ this.getHealth() + " " +
                "\tPara : "+ this.getMoney() );

    }

    public void initPlayer(GameChar gameChar){
        this.setDamage(gameChar.getDamage());
        this.setHealth(gameChar.getHealth());
        this.setOriginalHealth(gameChar.getHealth());
        this.setMoney(gameChar.getMoney());
        this.setCharName(gameChar.getName());
    }

    private int getValidCharacter(){
        int selectChar = -1;
        boolean isValid = false;

        while (!isValid){
            String inputStr = input.nextLine();
            if (isNumeric(inputStr)){

                selectChar = Integer.parseInt(inputStr);

                if ((selectChar >= 1 && selectChar <= 3) || selectChar == 999){
                    isValid = true;
                }else {
                    System.out.print("Geçersiz değer, tekrar giriniz: ");
                }

            } else {
                System.out.print("Geçersiz giriş, lütfen bir sayı giriniz: ");
            }
        }
        return selectChar;
    }

    private boolean isNumeric(String str){
        return str.matches("\\d+");
    }

    public void printInfo(){
        System.out.println("Silahınız : " + this.getInventory().getWeapon().getName() + " " +
                "\tZırhınız : "+ this.getInventory().getArmor().getName() +" " +
                "\tBloklama : "+ this.getInventory().getArmor().getBlock() +" " +
                "\tHasarınız : "+ this.getTotalDamage() +" " +
                "\tSağlığınız : "+ this.getHealth() + " " +
                "\tParanız : "+ this.getMoney() );
    }

    public int getTotalDamage(){
        return damage + this.getInventory().getWeapon().getDamage();

    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        if(health < 0){
            health = 0;
        }
        this.health = health;
    }

    public int getOriginalHealth() {
        return originalHealth;
    }

    public void setOriginalHealth(int originalHealth) {
        this.originalHealth = originalHealth;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getCharName() {
        return charName;
    }

    public void setCharName(String charName) {
        this.charName = charName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }
}
