package com.jchen.graph;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Graph {
    private int width, height;
    private List<List<Point>> points;
    private boolean renderLines;

    public Graph(int width, int height) {
        points = new ArrayList<>();
        renderLines = false;
        this.width = width;
        this.height = height;
    }

    public boolean isRenderLines() {
        return renderLines;
    }

    public void setRenderLines(boolean renderLines) {
        this.renderLines = renderLines;
    }

    public Graph addPoint(int group, Point point) {
        while (group >= points.size())
            points.add(new ArrayList<>());
        points.get(group).add(point);
        return this;
    }

    public Graph addPoint(int group, double x, double y) {
        return addPoint(group, new Point(x, y));
    }

    public Graph save(File file) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = image.getGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, width, height);
        double maxX = Double.NEGATIVE_INFINITY;
        double maxY = Double.NEGATIVE_INFINITY;
        for (List<Point> group : points) {
            for (Point point : group) {
                if (point.getX() > maxX)
                    maxX = point.getX();
                if (point.getY() > maxY)
                    maxY = point.getY();
            }
        }

        for (int i = 0; i < points.size(); i++) {
            List<Point> group = points.get(i);
            graphics.setColor(Color.getHSBColor((i * 0.35f) % 1, 1, 0.75f));
            for (int j = 0; j < group.size(); j++) {
                Point point = group.get(j);
                int scaledX = (int) ((point.getX() / maxX) * width);
                int scaledY = height - (int) ((point.getY() / maxY) * height);
                graphics.fillOval(scaledX, scaledY, 2, 2);
                if (renderLines && j > 0) {
                    Point last = group.get(j - 1);
                    int lastScaledX = (int) ((last.getX() / maxX) * width);
                    int lastScaledY = height - (int) ((last.getY() / maxY) * height);
                    graphics.drawLine(lastScaledX, lastScaledY, scaledX, scaledY);
                }
            }
        }
        try {
            ImageIO.write(image, "png", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public Graph save(String filepath) {
        return save(new File(filepath));
    }
}
