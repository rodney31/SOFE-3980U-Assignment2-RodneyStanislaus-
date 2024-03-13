import net.sf.javaml.core.Instance;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.clustering.Clusterer;
import net.sf.javaml.clustering.KMeans;
import net.sf.javaml.clustering.FarthestFirst;
import net.sf.javaml.clustering.DensityBasedSpatialClustering;
import net.sf.javaml.clustering.evaluation.ClusterEvaluation;
import net.sf.javaml.clustering.evaluation.SumOfSquaredErrors;
import net.sf.javaml.tools.data.FileHandler;
import java.io.File;

import java.io.IOException;
import java.lang.Exception;


public class Main
{
    public static void main(String[] args)
    {
        int instanceCount = 1;
        Dataset data = null;

        // Get data from iris.data file
        try
        {
            String pathOfFile = "C:\\Users\\rodne\\OneDrive\\Documents\\University\\Winter 2024\\Software Quality\\Assignments\\Assignment 2\\MachineLearning\\iris.data";
            data = FileHandler.loadDataset(new File(pathOfFile), 4, ",");
        }
        catch (IOException exception)
        {
            exception.printStackTrace();
        }

        Clusterer km = new KMeans(); // New instance of KMeans algorithm, with default parameter k = 4
        Clusterer ff = new FarthestFirst(); // New instance of FarthestFirst algorithm, with default parameters numClusters = 4 and a random Euclidean distance
        Clusterer dbsc = new DensityBasedSpatialClustering(); // New instance of DensityBasedSpatialClustering algorithm, with default parameters epsilon = 0.1 and minPoints = 6

        Dataset[] clusters = km.cluster(data); // Cluster the data with KMeans
        Dataset[] clusters2 = ff.cluster(data); // Cluster the data with FarthestFirst
        Dataset[] clusters3 = dbsc.cluster(data); // Cluster the data with DensityBasedSpatialClustering

        // Use sum of squared errors as a way to measure and compare cluster quality
        ClusterEvaluation sse = new SumOfSquaredErrors();
        ClusterEvaluation sse2 = new SumOfSquaredErrors();
        ClusterEvaluation sse3 = new SumOfSquaredErrors();

        // Determine the quality score of the clustering
        double score = sse.score(clusters);
        double score2 = sse2.score(clusters2);
        double score3 = sse3.score(clusters3);

        printOriginal(data);

        System.out.println("");

        printClustersAndScore(clusters, "KMeans", score);
        printClustersAndScore(clusters2, "FarthestFirst", score2);
        printClustersAndScore(clusters3, "DensityBasedSpatialClustering", score3);
    }

    public static void printOriginal(Dataset data)
    {
        int instanceCount = 1;

        System.out.println("\nOriginal Data Set had instances with the following values:");

        for (Instance inst : data)
        {
            System.out.print("\nInstance #" + instanceCount + " has values ");

            for (int i = 0; i < inst.noAttributes(); i++)
            {
                System.out.print(inst.value(i) + " ");
            }

            instanceCount = instanceCount + 1;
        }
    }

    public static void printClustersAndScore(Dataset[] clusters, String clusterAlgorithm, double score)
    {
        int instanceCount = 1;

        System.out.println("\n=====" + clusterAlgorithm + "=====");

        for (int i = 0; i < clusters.length; i++)
        {
            System.out.println("\nCluster " + (i + 1) + " has instances with the following values:");

            for (Instance inst : clusters[i])
            {
                System.out.print("Instance #" + instanceCount + " has values ");

                for (int j = 0; j < inst.noAttributes(); j++)
                {
                    System.out.print(inst.value(j) + " ");
                }

                instanceCount++;

                System.out.println("");
            }
        }

        System.out.println("\nThe score for the cluster evaluation is " + score);
    }
}