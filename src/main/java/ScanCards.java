package main.java;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ScanCards {

    final int x = 139;
    final int y = 139;
    final int width = 728; //867
    final int height = 1069;//1208;
    final int gape = 3;

    final int x2 = 69;
    final int y2 = 69;
    final int width2 = 366;//435;
    final int height2 = 537;//606;
    final int gape2 = 1;

    final int x3 = 66;
    final int y3 = 66;
    final int width3 = 351;//417;
    final int height3 = 515;//581;
    final int gape3 = 1;

    final int x4 = 66;
    final int y4 = 57;
    final int width4 = 353;//419;
    final int height4 = 523;//580;
    final int gape4 = 0;

    public void scan() {
        File[] bases = new File("./components/pnp").listFiles();

        for (File base: bases) {
            if (base.getName().contains("0")) {
                cutLists(x, y, width, height, gape, base);
            }
            if (base.getName().contains("1")) {
                cutLists(x, y, width, height, gape, base);
            }
            if (base.getName().contains("2")) {
                cutLists(x2, y2, width2, height2, gape2, base);
            }
            if (base.getName().contains("3")) {
                cutLists(x2, y2, width2, height2, gape2, base);
            }
            if (base.getName().contains("4")) {
                cutLists(x2, y2, width2, height2, gape2, base);
            }
            if (base.getName().contains("5")) {
                cutLists(x2, y2, width2, height2, gape2, base);
            }
            if (base.getName().contains("6")) {
                cutLists(x3, y3, width3, height3, gape3, base);
            }
            if (base.getName().contains("7")) {
                cutLists(x4, y4, width4, height4, gape4, base);
            }
        }

    }

    private void cutLists(int x, int y, int width, int height, int gape, File base) {
        File[] lists = base.listFiles();

        for (File list: lists) {
            cutCard(x, y, width, height, list);
        }
    }

    private void cutCard(int x, int y, int width, int height, File list) {
        BufferedImage listFile = null;
        try {
            listFile = ImageIO.read(list);

            int x2 = x + width + 3;
            int x3 = x + width + 3 + width + 3;
            int y2 = y + height + 3;
            int y3 = y + height + 3 + height + 3;

            ArrayList<BufferedImage> cards = new ArrayList<>();
            cards.add(listFile.getSubimage(x, y, width, height));
            cards.add(listFile.getSubimage(x2, y, width, height));
            cards.add(listFile.getSubimage(x3, y, width, height));

            cards.add(listFile.getSubimage(x, y2, width, height));
            cards.add(listFile.getSubimage(x2, y2, width, height));
            cards.add(listFile.getSubimage(x3, y2, width, height));

            cards.add(listFile.getSubimage(x, y3, width, height));
            cards.add(listFile.getSubimage(x2, y3, width, height));
            cards.add(listFile.getSubimage(x3, y3, width, height));

            for (int i = 0; i < cards.size(); i ++) {
                extracted(cards.get(i), i, list.getPath());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void extracted(BufferedImage subImage, int i, String path) throws IOException {
        ImageIO.write(subImage, "jpg", new File(path + "_" + i + ".jpg"));
    }

}
