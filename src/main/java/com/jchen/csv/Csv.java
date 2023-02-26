package com.jchen.csv;

import com.jchen.graph.Graph;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Csv {

    ArrayList<ArrayList<String>> data;

    public Csv() {
        data = new ArrayList<>();
    }

    public Csv parse(String path) {
        return parse(new File(path));
    }

    public Csv parse(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                data.add(new ArrayList<>(List.of(line.split(","))));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public String get(int row, int column) {
        return data.get(row).get(column);
    }

    public ArrayList<String> get(int row) {
        return data.get(row);
    }

    public Csv write(String path) {
        return write(new File(path));
    }

    public Csv write(File file) {
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (int i = 0; i < data.size(); i++) {
                ArrayList<String> line = data.get(i);
                for (int j = 0; j < line.size(); j++) {
                    String value = line.get(j);
                    writer.write(value);
                    if (j < line.size() - 1) {
                        writer.write(',');
                    }
                }
                if (i < data.size() - 1) {
                    writer.write('\n');
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public Csv addLine(List<String> line) {
        data.add(new ArrayList<>(line));
        return this;
    }

    public int getRows() {
        return data.size();
    }

    public int getColumns() {
        return data.get(0).size();
    }

    public List<String> getHeaders() {
        return data.get(0);
    }

    public Graph toGraph(Graph graph, int xColumn, List<Integer> yColumns) {
        graph.setXAxis(getHeaders().get(xColumn));
        for (int y : yColumns)
            graph.addLegend(getHeaders().get(y));
        for (int i = 1; i < data.size() - 1; i++) {
            for (int j = 0; j < yColumns.size(); j++) {
                graph.addPoint(j, Double.parseDouble(data.get(i).get(xColumn)), Double.parseDouble(data.get(i).get(yColumns.get(j))));
            }
        }
        return graph;
    }

    public Graph toGraph(int xColumn, List<Integer> yColumns) {
        return toGraph(new Graph(), xColumn, yColumns);
    }

    public Graph toGraph(int xColumn, int yColumn) {
        return toGraph(xColumn, List.of(yColumn));
    }
}
