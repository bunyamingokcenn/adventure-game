public class ToolStore extends NormalLocation{
    public ToolStore(Player player) {
        super(player, "Eşya Dükkanı");
    }

    @Override
    public boolean onLocation() {
        System.out.println("---------------------------------------------------------------------");
        System.out.println();
        System.out.println("######### Eşya Dükkanına Hoş Geldiniz! #########");
        System.out.println();
        boolean showMenu = true;
        while (showMenu){
            System.out.println();
            System.out.println("1- Silahlar");
            System.out.println("2- Zırhlar");
            System.out.println("3- Çıkış");
            System.out.print("Seçiminiz: ");

            int selectCase = getValidNum();


            while (selectCase < 1 || selectCase > 3){
                invalidNum();
                selectCase = input.nextInt();
            }
            System.out.println();
            switch (selectCase){
                case 1:
                    printWeapon();
                    buyWeapon();
                    break;
                case 2:
                    printArmor();
                    buyArmor();
                    break;
                case 3:
                    System.out.println("Yine bekleriz !");
                    showMenu = false;
                    break;

            }
        }
        return true;
    }

    //Geçerli sayı girişi kontrol
    private int getValidNum(){
        int validNum = -1;
        boolean isValid = false;

        while(!isValid){
            String inputStr = input.next();

            if (isNumeric(inputStr)){
                validNum = Integer.parseInt(inputStr);

                if (validNum >= 1 && validNum <= 3 ){
                    isValid = true;
                }else {
                    invalidNum();
                }
            }else {
                invalidEntry();
            }
        }
        return validNum;
    }

    //Silahları yazdır
    public void printWeapon(){
        System.out.println("######### Silahlar #########");
        System.out.println();
        for (Weapon w : Weapon.weapons()){
            System.out.println(w.getId()+ " - " +
                    w.getName()+ "\t\t" +
                    "Hasar: " + w.getDamage() + "\t"+
                    "Fiyat: " + w.getPrice());
        }
        System.out.println("0 - Çıkış Yap");
        System.out.println();
        System.out.println("Paranız: " + this.getPlayer().getMoney());
    }

    //Silah satın al
    public void buyWeapon(){
        //Silah Seçme
        System.out.print("Bir silah seçiniz: ");

        int selectWeaponID = getValidWeapon();

        while (selectWeaponID < 0 || selectWeaponID > Weapon.weapons().length){
            invalidNum();
            selectWeaponID = input.nextInt();
        }

        if(selectWeaponID != 0 ){
            Weapon selectedWeapon = Weapon.getWeaponObjByID(selectWeaponID);

            if ( selectedWeapon != null){
                if(selectedWeapon.getPrice() > this.getPlayer().getMoney()){
                    System.out.println();
                    System.out.println("Yeterli paranız bulunmamaktadır !");
                }else{
                    //Silah Satın Alma
                    System.out.println(selectedWeapon.getName() + " silahını satın aldınız");
                    int balance = this.getPlayer().getMoney() - selectedWeapon.getPrice();
                    this.getPlayer().setMoney(balance);
                    System.out.println("Kalan paranız: " + this.getPlayer().getMoney());
                    this.getPlayer().getInventory().setWeapon(selectedWeapon);
                }
            }
        } else {
            exitMessage();
        }
    }

    //Silah seçimini kontrol et
    private int getValidWeapon(){
        int selectWeapon = -1;
        boolean isValid = false;

        while (!isValid){
            String inputStr = input.next();

            if (isNumeric(inputStr)){
                selectWeapon = Integer.parseInt(inputStr);

                int lastWeaponID = Weapon.weapons().length;

                if (selectWeapon >=0 && selectWeapon <= lastWeaponID){
                    isValid = true;
                }else {
                    invalidNum();
                }
            }else{
                invalidEntry();
            }
        }
        return selectWeapon;
    }

    //Zırhları yazdır
    public void printArmor(){
        System.out.println("######### Zırhlar #########");
        System.out.println();
        for (Armor a : Armor.armors()){
            System.out.println(a.getId()+ " - " +
                    a.getName()+ "\t" +
                    "Blok: " + a.getBlock() + "\t"+
                    "Fiyat: " + a.getPrice());
        }
        System.out.println("0 - Çıkış Yap");
        System.out.println();
        System.out.println("Paranız: " + this.getPlayer().getMoney());
    }

    //Zırh satın al
    public void buyArmor(){
        //Zırh Seçme
        System.out.print("Bir zırh seçiniz: ");

        int selectArmorID = getValidArmor();

        while (selectArmorID < 0 || selectArmorID > Armor.armors().length){
            invalidNum();
            selectArmorID = input.nextInt();
        }

        if(selectArmorID != 0){
            Armor selectedArmor = Armor.getArmorObjByID(selectArmorID);

            if ( selectedArmor != null){
                if(selectedArmor.getPrice() > this.getPlayer().getMoney()){
                    System.out.println("Yeterli paranız bulunmamaktadır !");
                }else{
                    //Zırh Satın Alma
                    System.out.println(selectedArmor.getName() + " zırhı satın aldınız");
                    int balance = this.getPlayer().getMoney() - selectedArmor.getPrice();
                    this.getPlayer().setMoney(balance);
                    System.out.println("Kalan paranız: " + this.getPlayer().getMoney());
                    this.getPlayer().getInventory().setArmor(selectedArmor);
                }

            }
        } else {
            exitMessage();
        }


    }

    //Zırh seçimini kontrol et
    private int getValidArmor(){
        int selectArmor = -1;
        boolean isVaild = false;

        while (!isVaild){
            String inputStr = input.next();

            if (isNumeric(inputStr)){
                selectArmor = Integer.parseInt(inputStr);

                int lastArmorID = Armor.armors().length;

                if (selectArmor >= 0 && selectArmor <= lastArmorID){
                    isVaild = true;
                }else {
                    invalidNum();
                }
            }else {
                invalidEntry();
            }
        }
        return selectArmor;
    }

    //Girişin sayısal olup olmadığı kontrolü
    private boolean isNumeric(String str){
        return str.matches("\\d+");
    }

    //Geçersiz değer uyarısı
    public void invalidNum(){
        System.out.print("Geçersiz değer, tekrar giriniz:");
    }

    //Geçersiz veri tipi girişi yapılırsa
    public void invalidEntry(){
        System.out.print("Geçersiz giriş, lütfen bir sayı giriniz:");
    }

    public void exitMessage(){
        System.out.println("Çıkış yapıldı...");
    }
}
