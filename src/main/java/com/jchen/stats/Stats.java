package com.jchen.stats;

import com.jchen.csv.Csv;

import java.util.Collection;
import java.util.List;

public class Stats {
    public static double std(Collection<Double> input) {
        return Math.sqrt(variance(input));
    }

    public static double variance(Collection<Double> input){
        double average = mean(input);
        double variance = 0;
        for (double number : input)
            variance += Math.pow((number - average), 2) / input.size();
        return variance;
    }

    public static double mean(Collection<Double> input) {
        double average = 0;
        for (double number: input)
            average += number/input.size();
        return average;
    }

    public static double sem(Collection<Double> input) {
        return std(input)/Math.sqrt(input.size());
    }

    public static double wtTest(Collection<Double> sample1, Collection<Double> sample2) {
        double t = (mean(sample1) - mean(sample2))/Math.sqrt(Math.pow(sem(sample1), 2) + Math.pow(sem(sample2), 2));
        double s1 = std(sample1); double s2 = std(sample2);
        double n1 = sample1.size(); double n2 = sample2.size();
        double v1 = n1-1; double v2 = n2-1;
        int v = (int) Math.ceil(Math.pow(Math.pow(s1, 2)/n1 + Math.pow(s2, 2)/n2, 2)/(Math.pow(s1, 4)/(Math.pow(n1, 2) * v1) + Math.pow(s2, 4)/(Math.pow(n2, 2) * v2)));
        return tTable(t, v);
    }

    public static double tTable(double t, int v) {
        Csv table = new Csv().parse(Stats.class.getClassLoader().getResource("t_table.csv").getPath());
        List<String> row = table.get(v + 1);
        for (int i = 1; i < row.size(); i++) {
            if (Double.parseDouble(row.get(i)) > t) {
                return Double.parseDouble(table.getHeaders().get(i-1));
            }
        }
        return -1;
    }
}
