package Main;

import Tools.Filters;
import Tools.Huffman.Huffman;
import Tools.LZ77.LZ77;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class Compression {
    FileInputStream input;

    public void Compress(File IMAGE, String outputDir) {
        try {
            int value;

            showInfo(IMAGE);

            /** Reading BMP & Separating to Channels **/
            BufferedImage img = ImageIO.read(IMAGE);
            int width = img.getWidth();
            int height = img.getHeight();
            long size = IMAGE.length();
            long dataStart = size - width * height * 3;
            String name = IMAGE.getName().replace(".bmp", "");
            FileInputStream input = new FileInputStream(IMAGE);
            String output = outputDir + name + ".txt";

            StringBuilder toEncode = new StringBuilder();

            int cnt = 0;
            for (int i = 0; i < dataStart; i++) {           // copy header
                toEncode.append(input.read());
            }

            int[][] red = new int[height][width];
            int[][] green = new int[height][width];
            int[][] blue = new int[height][width];

            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    blue[i][j] = input.read();
                    green[i][j] = input.read();
                    red[i][j] = input.read();
                }
            }

            input.close();

            /** Filtering **/
            System.out.println("\nFiltering");
            Filters filters = new Filters(red, green, blue);
            toEncode = filters.filter();


            /** LZ77 **/
            System.out.println("\nCompressing with LZ77");
            LZ77 lz = new LZ77();
            toEncode = lz.Compress(toEncode);

            /** Huffman **/
            System.out.println("\nCompressing with Huffman code");
            Huffman huff = new Huffman();
            huff.Compress(toEncode, output);

            /** Creating to newPNG **/
            System.out.println("\nCreating NewPNG");


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showInfo(File IMAGE) {
        try {
            System.out.println("Reading image information");
            BufferedImage img = ImageIO.read(IMAGE);
            int width = img.getWidth();
            int height = img.getHeight();
            int type = img.getType();
            long size = IMAGE.length();
            long dataStart = size - width * height * 3;
            String name = IMAGE.getName().replace(".bmp", "");
            int px = img.getColorModel().getPixelSize();
            System.out.println("File name = " + name);
            System.out.println("Size = " + size);
            System.out.println("width = " + width + ", height = " + height);
            System.out.println("Pixel size = " + px);
            System.out.println("Data start = " + dataStart);
            System.out.println("Type = " + type);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
