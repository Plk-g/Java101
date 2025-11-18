public class SpiralMatrix {

    public static int[] spiralMatrix(int[][] matrix) {
        if (matrix == null || matrix.length == 0) return new int[0];

        int rows = matrix.length;
        int cols = matrix[0].length;
        int[] result = new int[rows * cols];

        int top = 0, bottom = rows - 1, left = 0, right = cols - 1;
        int idx = 0;

        while (top <= bottom && left <= right) {
          
            for (int j = left; j <= right; j++) {
                result[idx++] = matrix[top][j];
            }
            top++;

           
            for (int i = top; i <= bottom; i++) {
                result[idx++] = matrix[i][right];
            }
            right--;

            
            if (top <= bottom) {
                for (int j = right; j >= left; j--) {
                    result[idx++] = matrix[bottom][j];
                }
                bottom--;
            }

          
            if (left <= right) {
                for (int i = bottom; i >= top; i--) {
                    result[idx++] = matrix[i][left];
                }
                left++;
            }
        }

        return result;
    }

    public static void main(String[] args) {

        int[][] matrix = {
            {1, 2, 3,  4},
            {5, 6, 7, 8},
            {9, 10, 11, 12},
            {13, 14, 15, 16},
            {17, 18, 19, 20}
        };

        // Print out the matrix using nested loops
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }

        // Call spiralMatrix and print out the result
        int[] spiral = spiralMatrix(matrix);
        System.out.print("Spiral order: ");
        for (int num : spiral) {
            System.out.print(num + " ");
        }
        System.out.println();
    }
}
