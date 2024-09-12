import java.util.Random;

public class Snake extends Obstacle {
    public Snake() {
        super(4, "Yılan", getRandomDamage(), 12, 0);

    }

    private static int getRandomDamage() {
        Random random = new Random();
        int min = 3;
        int max = 6;
        return random.nextInt((max - min) + 1) + min;
    }





}