import Main.Compression;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class Runner {
    /**  8-bit  **/
    static String IN_FILE_PATH1 = "C:\\Users\\Tom\\Desktop\\programing\\DataCompression\\PNG Project\\NewPNG\\BMPs\\LAND.bmp";
    static String IN_FILE_PATH2 = "C:\\Users\\Tom\\Desktop\\programing\\DataCompression\\PNG Project\\NewPNG\\BMPs\\lena.bmp";

    /**  24-bit  **/
    static String IN_FILE_PATH3 = "C:\\Users\\Tom\\Desktop\\programing\\DataCompression\\PNG Project\\NewPNG\\BMPs\\snail.bmp";
    static String IN_FILE_PATH4 = "C:\\Users\\Tom\\Desktop\\programing\\DataCompression\\PNG Project\\NewPNG\\BMPs\\RAY.bmp";
    static String IN_FILE_PATH5 = "C:\\Users\\Tom\\Desktop\\programing\\DataCompression\\PNG Project\\NewPNG\\BMPs\\MARBLES.bmp";
    static String IN_FILE_PATH6 = "C:\\Users\\Tom\\Desktop\\programing\\DataCompression\\PNG Project\\NewPNG\\BMPs\\greenland_grid_velo.bmp";
    static String IN_FILE_PATH7 = "C:\\Users\\Tom\\Desktop\\programing\\DataCompression\\PNG Project\\NewPNG\\BMPs\\FLAG_B24.bmp";
    static String IN_FILE_PATH8 = "C:\\Users\\Tom\\Desktop\\programing\\DataCompression\\PNG Project\\NewPNG\\BMPs\\bmp_24.bmp";
    static String IN_FILE_PATH9 = "C:\\Users\\Tom\\Desktop\\programing\\DataCompression\\PNG Project\\NewPNG\\BMPs\\blackbuck.bmp";
    static String RED = "C:\\Users\\Tom\\Desktop\\programing\\DataCompression\\PNG Project\\NewPNG\\BMPs\\RED.bmp";
    static String GRN = "C:\\Users\\Tom\\Desktop\\programing\\DataCompression\\PNG Project\\NewPNG\\BMPs\\GRN.bmp";
    static String BLU = "C:\\Users\\Tom\\Desktop\\programing\\DataCompression\\PNG Project\\NewPNG\\BMPs\\BLU.bmp";

    /** Output **/
    static String OUT_FILE_PATH = "C:\\Users\\Tom\\Desktop\\programing\\DataCompression\\PNG Project\\NewPNG\\Compressed\\";


    public static void main(String[] args) {
        try {
            File IMAGE = new File(IN_FILE_PATH5);
//            File IMAGE = new File(IN_FILE_PATH8);
            Compression compress = new Compression();
            compress.Compress(IMAGE, OUT_FILE_PATH);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
