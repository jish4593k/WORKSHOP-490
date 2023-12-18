import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Arrays;

public class ParticleSnapshotSlicer {

    public static void main(String[] args) {
        int N_files = 1920;
        int box = 1000;
        String snap_id = "098";

        double[] xbins = linspace(0, box, box / (2 * 10) + 1);
        int totalParticles = 0;

        for (int i = 0; i < N_files; i++) {
            System.out.println(i);
a
            float[][] snapshotData = loadSnapshotData("_" + snap_id + "/snap_" + snap_id + "." + i);

            System.out.println(snapshotData.length);

            totalParticles += snapshotData.length;

            for (int c = 0; c < 50; c++) {
                double xMin = xbins[c];
                double xMax = xbins[c + 1];

                float[][] temp = Arrays.stream(snapshotData)
                        .filter(p -> p[0] >= xMin && p[0] < xMax)
                        .toArray(float[][]::new);

                if (temp.length > 0) {
                    saveToNpz("_" + snap_id + "/z", temp);
                    System.out.println(Arrays.deepToString(temp));
                }
            }
        }
    }

  

    private static void saveToNpz(String filePath, float[][] data) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(filePath)))) {
            oos.writeObject(data);
            System.out.println("Data saved to: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static double[] linspace(double start, double end, int numPoints) {
        double[] array = new double[numPoints];
        double step = (end - start) / (numPoints - 1);

        for (int i = 0; i < numPoints; i++) {
            array[i] = start + i * step;
        }

        return array;
    }
}
