import javax.swing.*;
import java.awt.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Random rand = new Random();

        int[] testCase1 = new int[100];
        int[] testCase2 = new int[100];
        int[] testCase3 = new int[100];
        int[] testCase4 = new int[100];

        for (int i = 0; i < testCase1.length; i++) {
            int value = rand.nextInt(100);
            testCase1[i] = value;
            testCase2[i] = value;
            testCase3[i] = value;
            testCase4[i] = value;
        }

        JFrame frame = new JFrame("Sorting Algorithm Visualizations");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        frame.setLayout(new GridLayout(2, 2));

        SortingPanel mergeSortPanel = new SortingPanel(testCase1, "Merge Sort");
        SortingPanel quickSortPanel = new SortingPanel(testCase2, "Quick Sort");
        SortingPanel bubbleSortPanel = new SortingPanel(testCase3, "Bubble Sort");
        SortingPanel insertionSortPanel = new SortingPanel(testCase4, "Insertion Sort");

        frame.add(mergeSortPanel);
        frame.add(quickSortPanel);
        frame.add(bubbleSortPanel);
        frame.add(insertionSortPanel);

        frame.setVisible(true);

        new Thread(() -> mergeSort(testCase1, 0, testCase1.length - 1, mergeSortPanel)).start();
        new Thread(() -> quickSort(testCase2, 0, testCase2.length - 1, quickSortPanel)).start();
        new Thread(() -> bubbleSort(testCase3, bubbleSortPanel)).start();
        new Thread(() -> insertionSort(testCase4, insertionSortPanel)).start();
    }

    public static void mergeSort(int[] arr, int left, int right, SortingPanel panel) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            mergeSort(arr, left, mid, panel);
            mergeSort(arr, mid + 1, right, panel);
            merge(arr, left, mid, right, panel);
        }
    }

    private static void merge(int[] arr, int left, int mid, int right, SortingPanel panel) {
        int[] temp = new int[right - left + 1];
        int i = left, j = mid + 1, k = 0;

        while (i <= mid && j <= right) {
            panel.currentIndex = (arr[i] < arr[j]) ? i : j;
            panel.repaint();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (arr[i] <= arr[j]) {
                temp[k++] = arr[i++];
            } else {
                temp[k++] = arr[j++];
            }
        }

        while (i <= mid) {
            panel.currentIndex = i;
            panel.repaint();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            temp[k++] = arr[i++];
        }

        while (j <= right) {
            panel.currentIndex = j;
            panel.repaint();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            temp[k++] = arr[j++];
        }

        for (i = 0; i < temp.length; i++) {
            arr[left + i] = temp[i];
            panel.currentIndex = left + i;
            panel.repaint();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void quickSort(int[] arr, int low, int high, SortingPanel panel) {
        if (low < high) {
            int pi = partition(arr, low, high, panel);
            quickSort(arr, low, pi - 1, panel);
            quickSort(arr, pi + 1, high, panel);
        }
    }

    private static int partition(int[] arr, int low, int high, SortingPanel panel) {
        int pivot = arr[high];
        int i = (low - 1);

        for (int j = low; j < high; j++) {
            if (arr[j] < pivot) {
                i++;
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
                panel.currentIndex = j;
                panel.repaint();
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;
        panel.currentIndex = high;
        panel.repaint();
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return i + 1;
    }

    public static void bubbleSort(int[] arr, SortingPanel panel) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
                panel.currentIndex = j;
                panel.repaint();
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void insertionSort(int[] arr, SortingPanel panel) {
        int n = arr.length;
        for (int i = 1; i < n; i++) {
            int key = arr[i];
            int j = i - 1;

            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j = j - 1;
                panel.currentIndex = j;
                panel.repaint();
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            arr[j + 1] = key;
            panel.currentIndex = i;
            panel.repaint();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class SortingPanel extends JPanel {
        private int[] arr;
        private int currentIndex = -1;
        private String title;

        public SortingPanel(int[] arr, String title) {
            this.arr = arr;
            this.title = title;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            int width = getWidth() / arr.length;
            for (int i = 0; i < arr.length; i++) {
                if (i == currentIndex) {
                    g.setColor(new Color(213, 105, 105));
                } else {
                    g.setColor(new Color(113, 196, 91));
                }
                g.fillRect(i * width, getHeight() - arr[i] * 5, width - 2, arr[i] * 5);
            }

            // Draw title
            g.setColor(Color.BLACK);
            g.setFont(new Font("Agave", Font.BOLD, 16));
            g.drawString(title, 10, 20);
        }
    }
}
