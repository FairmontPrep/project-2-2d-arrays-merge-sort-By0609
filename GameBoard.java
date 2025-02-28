import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Random;
import java.util.Set;
import java.util.HashSet;


public class GameBoard extends JFrame {
    public static final int SIZE = 8;
    private JPanel[][] squares = new JPanel[SIZE][SIZE];
    private String[][] piecesArray;
    private void printPiecesArray() {
        System.out.println("Unsorted Pieces Array:");
        for (String[] piece : piecesArray) {
            System.out.println("Image: " + piece[0] + ", Name: " + piece[1] + ", Position: " + piece[2]);
        }
        System.out.println();
    }
    

    public GameBoard() {
        setTitle("Poke Board");
        setSize(750, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(SIZE, SIZE));

        // Initialize the 2D array of panels
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                squares[row][col] = new JPanel();
                if ((row + col) % 2 == 0) {
                    squares[row][col].setBackground(new Color(255, 251, 240));
                } else {
                    squares[row][col].setBackground(Color.LIGHT_GRAY);
                }
                add(squares[row][col]);
            }
        }

        // Initialize and load pieces
        piecesArray = new String[32][3];
        loadPieces();

        // Sort piecesArray using merge sort
        printPiecesArray();
        mergeSort(piecesArray, 0, piecesArray.length - 1);

        // Populate the board with pieces
        populateBoard();
    }

    // Merge Sort implementation
    private void mergeSort(String[][] array, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSort(array, left, mid);
            mergeSort(array, mid + 1, right);
            merge(array, left, mid, right);
        }
    }

    private void merge(String[][] array, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        String[][] leftArray = new String[n1][3];
        String[][] rightArray = new String[n2][3];

        for (int i = 0; i < n1; i++)
            leftArray[i] = array[left + i];
        for (int j = 0; j < n2; j++)
            rightArray[j] = array[mid + 1 + j];

        int i = 0, j = 0;
        int k = left;
        while (i < n1 && j < n2) {
            if (Integer.parseInt(leftArray[i][2]) <= Integer.parseInt(rightArray[j][2])) {
                array[k] = leftArray[i];
                i++;
            } else {
                array[k] = rightArray[j];
                j++;
            }
            k++;
        }

        while (i < n1) {
            array[k] = leftArray[i];
            i++;
            k++;
        }

        while (j < n2) {
            array[k] = rightArray[j];
            j++;
            k++;
        }
    }

    private void populateBoard() {
        for (String[] piece : piecesArray) {
            String imagePath = piece[0];
            String pieceName = piece[1];
            String color = imagePath.startsWith("B_") ? "Black" : "White";
            int row = -1;
            int col = -1;
    
            // Determine the position based on piece type and color
            if (pieceName.equals("Rook")) {
                if (color.equals("Black")) {
                    if (squares[0][0].getComponentCount() == 0) {
                        row = 0;
                        col = 0;
                    } else {
                        row = 0;
                        col = 7;
                    }
                } else {
                    if (squares[7][0].getComponentCount() == 0) {
                        row = 7;
                        col = 0;
                    } else {
                        row = 7;
                        col = 7;
                    }
                }
            } else if (pieceName.equals("Knight")) {
                if (color.equals("Black")) {
                    if (squares[0][1].getComponentCount() == 0) {
                        row = 0;
                        col = 1;
                    } else {
                        row = 0;
                        col = 6;
                    }
                } else {
                    if (squares[7][1].getComponentCount() == 0) {
                        row = 7;
                        col = 1;
                    } else {
                        row = 7;
                        col = 6;
                    }
                }
            } else if (pieceName.equals("Bishop")) {
                if (color.equals("Black")) {
                    if (squares[0][2].getComponentCount() == 0) {
                        row = 0;
                        col = 2;
                    } else {
                        row = 0;
                        col = 5;
                    }
                } else {
                    if (squares[7][2].getComponentCount() == 0) {
                        row = 7;
                        col = 2;
                    } else {
                        row = 7;
                        col = 5;
                    }
                }
            } else if (pieceName.equals("Queen")) {
                row = color.equals("Black") ? 0 : 7;
                col = 3;
            } else if (pieceName.equals("King")) {
                row = color.equals("Black") ? 0 : 7;
                col = 4;
            } else if (pieceName.equals("Pawn")) {
                row = color.equals("Black") ? 1 : 6;
                // Find the first empty column in the pawn row
                for (int c = 0; c < SIZE; c++) {
                    if (squares[row][c].getComponentCount() == 0) {
                        col = c;
                        break;
                    }
                }
            }
    
            if (row != -1 && col != -1) {
                ImageIcon icon = new ImageIcon(imagePath);
                Image scaledImage = icon.getImage().getScaledInstance(40, 42, Image.SCALE_SMOOTH);
                icon = new ImageIcon(scaledImage);
    
                JLabel pieceLabel = new JLabel(pieceName, icon, JLabel.CENTER);
                pieceLabel.setVerticalTextPosition(JLabel.BOTTOM);
                pieceLabel.setHorizontalTextPosition(JLabel.CENTER);
                pieceLabel.setForeground(Color.BLACK);
    
                squares[row][col].setLayout(new BorderLayout());
                squares[row][col].add(pieceLabel, BorderLayout.CENTER);
            }
        }
    
        revalidate();
        repaint();
    }


    private void loadPieces() {
    piecesArray = new String[32][3];
    Random rand = new Random();
    int index = 0;

    // Define piece types and their counts
    String[][] pieceTypes = {
        {"B_Rook.png", "Rook", "2"},
        {"B_Knight.png", "Knight", "2"},
        {"B_Bishop.png", "Bishop", "2"},
        {"B_Queen.png", "Queen", "1"},
        {"B_King.png", "King", "1"},
        {"B_Pawn.png", "Pawn", "8"},
        {"W_Rook.png", "Rook", "2"},
        {"W_Knight.png", "Knight", "2"},
        {"W_Bishop.png", "Bishop", "2"},
        {"W_Queen.png", "Queen", "1"},
        {"W_King.png", "King", "1"},
        {"W_Pawn.png", "Pawn", "8"}
    };

    // Track assigned positions to avoid duplicates
    Set<Integer> assignedPositions = new HashSet<>();

    for (String[] pieceType : pieceTypes) {
        String imagePath = pieceType[0];
        String pieceName = pieceType[1];
        int count = Integer.parseInt(pieceType[2]);

        for (int i = 0; i < count; i++) {
            int position;
            // Ensure the random position is unique
            do {
                position = rand.nextInt(64) + 1; // Positions 1 to 64
            } while (assignedPositions.contains(position));

            assignedPositions.add(position);
            piecesArray[index++] = new String[]{imagePath, pieceName, String.valueOf(position)};
        }
    }
}
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameBoard board = new GameBoard();
            board.setVisible(true);
        });
    }
}