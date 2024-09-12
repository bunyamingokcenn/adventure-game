import java.util.Scanner;

public class Game {
    private Scanner input = new Scanner(System.in);

    public void start() {
        System.out.println("--------- Macera Oyununa Hoş Geldiniz ! ---------");
        System.out.println("-------------------------------------------------");
        System.out.print("Lütfen bir isim giriniz: ");
        String playerName = input.nextLine();
        Player player = new Player(playerName);
        System.out.println( "Maceracı " + player.getName() + ". Bu diyarlara hoş geldin." +
                "\nHayatta kalıp buradan kaçabilmek için sana gereken 3 materyal var." +
                "\nTehlikeli bölgelerde canavarlar ile savaşarak 3 materyali elde edebilirsen bu tehlikeli diyardan kaçabilirsin." +
                "\nElde ettiğin 3 materyal ile güvenli eve dön ve oyunu kazan." +
                "\nUnutma, cesur ol ama bir o kadar akıllı...");
        player.selectChar();

        Location location = null;

        while (true) {
            player.printInfo();
            System.out.println("----------------------------------------------------------------------------------------------------------");
            System.out.println();
            System.out.println("######### Bölgeler #########");
            System.out.println();
            System.out.println("1- Güvenli Ev --> Burası sizin için güvenli bir ev. Düşman yoktur !");
            System.out.println("2- Eşya Dükkanı --> Silah veya zırh satın alabilirsiniz !");
            System.out.println("3- Mağara --> Ödül <Yemek>, dikkatli ol  karşına zombi çıkabilir !");
            System.out.println("4- Orman --> Ödül <Odun>, dikkatli ol  karşına vampir çıkabilir !");
            System.out.println("5- Nehir --> Ödül <Su>, dikkatli ol  karşına ayı çıkabilir !");
            System.out.println("6- Maden --> Yılanlardan Para, Silah ya da Zırh düşürebilirsin, dikkatli ol!");
            System.out.println("0- Çıkış Yap --> Oyunu Sonlandır !");
            System.out.print("Lütfen gitmek istediğiniz bölgeyi seçiniz: ");

            int selectLoc = getValidLocation();

            switch (selectLoc) {
                case 0:
                    location = null;
                    break;
                case 1:
                    if (player.getInventory().checkWinCase()) {
                        location = null;
                        break;
                    }
                    location = new SafeHouse(player);
                    break;
                case 2:
                    location = new ToolStore(player);
                    break;
                case 3:
                    location = new Cave(player);
                    break;
                case 4:
                    location = new Forest(player);
                    break;
                case 5:
                    location = new River(player);
                    break;
                case 6:
                    location = new Mine(player);
                    break;
                default:
                    System.out.println("Lütfen geçerli bir bölge giriniz.");
            }

            if (location == null) {
                System.out.println("Oyun bitti, yine bekleriz !");
                break;
            }

            if (!location.onLocation() && player.getHealth() == 0) {
                System.out.println("GAME OVER");
                break;
            }
        }
    }

    private int getValidLocation() {
        int selectLoc = -1;
        boolean isValid = false;

        while (!isValid) {
            String inputStr = input.nextLine();  // Girişi String olarak al
            if (isNumeric(inputStr)) {  // Eğer giriş sadece sayılardan oluşuyorsa
                selectLoc = Integer.parseInt(inputStr);  // Sayıya çevir
                if (selectLoc >= 0 && selectLoc <= 6) {  // Geçerli aralıkta mı kontrol et
                    isValid = true;
                } else {
                    System.out.print("Geçersiz değer, tekrar giriniz: ");
                }
            } else {
                System.out.print("Geçersiz giriş, lütfen bir sayı giriniz: ");
            }
        }

        return selectLoc;
    }

    private boolean isNumeric(String str) {
        return str.matches("\\d+");
    }
}
