package Tools;

public class Filters {
    int[][] red, green, blue;
    int[][] filteredRed, filteredGreen, filteredBlue;
    int[] checkRed, checkGreen, checkBlue;
    int rows, cols, sum = Integer.MAX_VALUE;
    int[] selector;
    int[][] sums;

    public Filters(int[][] red, int[][] green, int[][] blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        rows = red.length;
        cols = red[0].length;
        filteredRed = new int[rows][cols];
        filteredGreen = new int[rows][cols];
        filteredBlue = new int[rows][cols];
        checkRed = new int[cols];
        checkGreen = new int[cols];
        checkBlue = new int[cols];
        sums = new int[rows][5];
        selector = new int[rows];
    }

    public StringBuilder filter() {
        StringBuilder filtered = new StringBuilder();

//        for (int i = 0; i < rows; i++) {
//            for (int j = 0; j < cols; j++) {
//                System.out.print(red[i][j] + "\t");
//            }
//            System.out.println();
//        }

        for (int i = 0; i < red.length; i++) {
            sum = Integer.MAX_VALUE;
            none(i);
            sub(i);
            up(i);
            avg(i);
            paeth(i);
        }

//        for (int i = 0; i < sums.length; i++) {
//            System.out.print(i + ": \t\t");
//            for (int j = 0; j < sums[0].length; j++) {
//                System.out.print(sums[i][j] + "\t\t");
//            }
//            System.out.println("\t\t\t\t\t\t selected: " + selector[i]);
//        }

//        System.out.println("\n\n\nFILTERED:");
//        for (int i = 0; i < rows; i++) {
//            for (int j = 0; j < cols; j++) {
//                System.out.print(filteredRed[i][j] + "\t");
//            }
//            System.out.println();
//        }

        filtered.append(rows);
        for (int i = 0; i < rows; i++) {
            filtered.append(selector[i]);
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                filtered.append(filteredRed[i][j]);
                filtered.append(filteredGreen[i][j]);
                filtered.append(filteredBlue[i][j]);
            }
        }
        return filtered;
    }

    public void none(int i) {
        copyArr(checkRed, red[i]);
        copyArr(checkGreen, green[i]);
        copyArr(checkBlue, blue[i]);

//        int sum =
                rowSum(i, checkRed, checkGreen, checkBlue, 0);
//        sums[i][0] = sum;
    }

    public void sub(int i) {
        checkRed[0] = red[i][0];
        checkGreen[0] = green[i][0];
        checkBlue[0] = blue[i][0];

        for (int j = 1; j < cols; j++) {
            checkRed[j] = (red[i][j] - red[i][j - 1] + 256) % 256;
            checkGreen[j] = (green[i][j] - green[i][j - 1] + 256) % 256;
            checkBlue[j] = (blue[i][j] - blue[i][j - 1] + 256) % 256;
        }

//        int sum =
                rowSum(i, checkRed, checkGreen, checkBlue, 1);
//        sums[i][1] = sum;
    }

    public void up(int i) {
        if (i != 0) {
            for (int j = 0; j < cols; j++) {
                checkRed[j] = (red[i][j] - red[i - 1][j] + 256) % 256;
                checkGreen[j] = (green[i][j] - green[i - 1][j] + 256) % 256;
                checkBlue[j] = (blue[i][j] - blue[i - 1][j] + 256) % 256;
            }

//            int sum =
                    rowSum(i, checkRed, checkGreen, checkBlue, 2);
//            sums[i][2] = sum;
        } else {
//            sums[i][2] = Integer.MAX_VALUE;
        }
    }

    public void avg(int i) {
        checkRed[0] = red[i][0];
        checkGreen[0] = green[i][0];
        checkBlue[0] = blue[i][0];

        if (i != 0) {
            for (int j = 1; j < cols; j++) {
                checkRed[j] = (red[i][j] - Math.round((red[i][j - 1] + red[i - 1][j]) / 2) + 256) % 256;
                checkGreen[j] = (green[i][j] - Math.round((green[i][j - 1] + green[i - 1][j]) / 2) + 256) % 256;
                checkBlue[j] = (blue[i][j] - Math.round((blue[i][j - 1] + blue[i - 1][j]) / 2) + 256) % 256;
            }
        } else {
            for (int j = 1; j < cols; j++) {
                checkRed[j] = (red[i][j] - Math.floorDiv(red[i][j - 1], 2) + 256) % 256;
                checkGreen[j] = (green[i][j] - Math.floorDiv(green[i][j - 1], 2) + 256) % 256;
                checkBlue[j] = (blue[i][j] - Math.floorDiv(blue[i][j - 1], 2) + 256) % 256;
            }
        }

//        int sum =
                rowSum(i, checkRed, checkGreen, checkBlue, 3);
//        sums[i][3] = sum;
    }

    public void paeth(int i) {
        paethFilter(i, red, checkRed);
        paethFilter(i, green, checkGreen);
        paethFilter(i, blue, checkBlue);

//        int sum =
                rowSum(i, checkRed, checkGreen, checkBlue, 4);
//        sums[i][4] = sum;
    }

    private void paethFilter(int i, int[][] color, int[] checkColor) {
        int up, left, upLeft;
        int V, VL, VU, VUL;     // v=u+l-ul, vl=v-l, vu=v-u, vul=v-ul || u=up, l=left, ul=up left (diagonally)

        for (int j = 0; j < cols; j++) {
            if (i == 0) up = 0;
            else up = color[i - 1][j];
            if (j == 0) left = 0;
            else left = color[i][j - 1];
            if (i == 0 || j == 0) upLeft = 0;
            else upLeft = color[i - 1][j - 1];

            V = Math.abs(up + left - upLeft);
            VL = Math.abs(V - left);
            VU = Math.abs(V - up);
            VUL = Math.abs(V - upLeft);

            checkColor[j] = Math.min(VL, Math.min(VU, VUL));        // choose min(VL, VU, VUL)
        }


    }

    public void rowSum(int i, int[] checkRed, int[] checkGreen, int[] checkBlue, int filter) {
        int currSum = 0;
        for (int j = 0; j < cols; j++) {
            currSum = currSum + checkRed[j] + checkGreen[j] + checkBlue[j];
        }
        if (currSum < sum) {
            copyArr(filteredRed[i], checkRed);
            copyArr(filteredGreen[i], checkGreen);
            copyArr(filteredBlue[i], checkBlue);
            selector[i] = filter;
            sum = currSum;
        }
//        return currSum;
    }

    protected void copyArr(int[] dest, int[] src) {
        System.arraycopy(src, 0, dest, 0, cols);
    }
}


