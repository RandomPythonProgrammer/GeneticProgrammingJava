package com.jchen.graph;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class Graph {
    private int width, height;
    private List<List<Point>> points;
    private List<String> legend;
    private boolean renderLines;
    private int fontSize;
    private int strokeSize;
    private String title, xAxis, yAxis;
    private int vLines, hLines;
    private int precision;

    public Graph() {
        points = new ArrayList<>();
        legend = new ArrayList<>();
        renderLines = false;
        this.width = 750;
        this.height = 500;
        strokeSize = 3;
        fontSize = 10;
        vLines = hLines = 10;
        precision = 3;
    }

    public Graph setSize(int width, int height) {
        this.width = width;
        this.height = height;
        return this;
    }

    public int getPrecision() {
        return precision;
    }

    public Graph setPrecision(int precision) {
        this.precision = precision;
        return this;
    }

    public List<String> getLegend() {
        return legend;
    }

    public Graph setLegend(List<String> legend) {
        this.legend = legend;
        return this;
    }

    public Graph setLegend(int index, String legend) {
        this.legend.set(index, legend);
        return this;
    }

    public Graph addLegend(String legend) {
        this.legend.add(legend);
        return this;
    }

    public int getWidth() {
        return width;
    }

    public Graph setWidth(int width) {
        this.width = width;
        return this;
    }

    public int getHeight() {
        return height;
    }

    public Graph setHeight(int height) {
        this.height = height;
        return this;
    }

    public int getVLines() {
        return vLines;
    }

    public Graph setVLines(int vLines) {
        this.vLines = vLines;
        return this;
    }

    public int getHLines() {
        return hLines;
    }

    public Graph setHLines(int hLines) {
        this.hLines = hLines;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Graph setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getXAxis() {
        return xAxis;
    }

    public Graph setXAxis(String xAxis) {
        this.xAxis = xAxis;
        return this;
    }

    public String getYAxis() {
        return yAxis;
    }

    public Graph setYAxis(String yAxis) {
        this.yAxis = yAxis;
        return this;
    }

    public int getFontSize() {
        return fontSize;
    }

    public Graph setFontSize(int fontSize) {
        this.fontSize = fontSize;
        return this;
    }

    public int getStrokeSize() {
        return strokeSize;
    }

    public Graph setStrokeSize(int strokeSize) {
        this.strokeSize = strokeSize;
        return this;
    }

    public boolean isRenderLines() {
        return renderLines;
    }

    public Graph setRenderLines(boolean renderLines) {
        this.renderLines = renderLines;
        return this;
    }

    public Graph addPoint(int group, Point point) {
        while (group >= points.size()) {
            points.add(new ArrayList<>());
            if (group >= legend.size()) {
                legend.add(String.format("Line %d", points.size()));
            }
        }
        points.get(group).add(point);
        return this;
    }

    public Graph addPoint(int group, double x, double y) {
        return addPoint(group, new Point(x, y));
    }

    public Graph save(File file) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = (Graphics2D) image.getGraphics();
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

        graphics.setFont(new Font("Courier", Font.PLAIN, fontSize));
        graphics.setColor(Color.BLACK);
        double xInterval = maxX / vLines;
        for (double x = 0; x <= maxX || doubleString(x, precision).equals(doubleString(maxX, precision)); x += xInterval) {
            int scaledX = (int) (((x / maxX) * 0.8 + 0.1) * width);
            int scaledY = height - (int) (height * 0.1 - fontSize * 4 / 3);
            String text = doubleString(x, precision);
            graphics.drawString(text, scaledX - ((text.length() / 2f) * fontSize * 4 / 3) / 2, scaledY);
            graphics.setStroke(new BasicStroke(strokeSize / 2f));
            int topY = (int) (height * 0.1);
            graphics.drawLine(scaledX, (int) (scaledY - fontSize * 4f / 3), scaledX, topY);
        }

        double yInterval = maxY / hLines;
        for (double y = 0; y <= maxY || doubleString(y, precision).equals(doubleString(maxY, precision)); y += yInterval) {
            int scaledX = (int) (width * 0.1);
            int scaledY = height - (int) (((y / maxY) * 0.8 + 0.1) * height);
            String text = doubleString(y, precision);
            graphics.drawString(text, scaledX - (text.length() * fontSize * 4f / 3) / 2, scaledY + (fontSize * 4 / 3f) / 4);
            graphics.setStroke(new BasicStroke(strokeSize / 2f));
            int topX = (int) (width * 0.9);
            graphics.drawLine(scaledX, scaledY, topX, scaledY);
        }

        if (title != null) {
            graphics.drawString(title, width / 2f - ((title.length() / 2f) * fontSize * 4 / 3) / 2, fontSize * 4f / 3);
        }

        if (xAxis != null) {
            graphics.drawString(xAxis, width / 2f - ((xAxis.length() / 2f) * fontSize * 4 / 3) / 2, height - (fontSize * 4f / 3) / 2);
        }

        if (yAxis != null) {
            BufferedImage text = new BufferedImage((int) ((yAxis.length() * 4f / 3 * fontSize) / 2), (int) (4f / 3 * fontSize * 1.5), BufferedImage.TYPE_INT_ARGB);
            Graphics2D textGraphics = (Graphics2D) text.getGraphics();
            textGraphics.setFont(new Font("Arial", Font.PLAIN, fontSize));
            textGraphics.setColor(Color.BLACK);
            textGraphics.setBackground(new Color(0, 0, 0, 0));
            textGraphics.drawString(yAxis, 0, (int) (4f / 3 * fontSize));
            textGraphics.rotate(-Math.PI / 2);
            textGraphics.translate(-(height / 2 + text.getWidth() / 2), 0);
            graphics.drawImage(text, textGraphics.getTransform(), null);
        }

        for (int i = 0; i < points.size(); i++) {
            List<Point> group = points.get(i);
            graphics.setColor(Color.getHSBColor((i * 0.35f) % 1, 1, 0.75f));
            for (int j = 0; j < group.size(); j++) {
                Point point = group.get(j);
                int scaledX = (int) ((point.getX() / maxX) * width * 0.8 + width * 0.1);
                int scaledY = height - (int) ((point.getY() / maxY) * height * 0.8 + height * 0.1);
                graphics.fillOval(scaledX - strokeSize, scaledY - strokeSize, strokeSize * 2, strokeSize * 2);
                if (renderLines && j > 0) {
                    Point last = group.get(j - 1);
                    int lastScaledX = (int) ((last.getX() / maxX) * width * 0.8 + width * 0.1);
                    int lastScaledY = height - (int) ((last.getY() / maxY) * height * 0.8 + height * 0.1);
                    graphics.setStroke(new BasicStroke(strokeSize));
                    graphics.drawLine(lastScaledX, lastScaledY, scaledX, scaledY);
                }
            }
        }

        for (int i = 0; i < legend.size(); i++) {
            String label = legend.get(i);
            double scaledY = height * 0.9 - (legend.size() - i - 1 + 0.5) * (fontSize * 4f / 3);
            double scaledX = width * 0.9 - (label.length() * (fontSize * 4f / 3) / 2);
            graphics.setColor(Color.getHSBColor((i * 0.35f) % 1, 1, 0.75f));
            graphics.drawString(label, (int) scaledX, (int) scaledY);
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

    private static String doubleString(double input, int precision) {
        input = new BigDecimal(input).setScale(precision, RoundingMode.HALF_UP).doubleValue();
        if (((int) input) == input) {
            return String.valueOf((int) input);
        } else {
            return String.valueOf(input);
        }
    }

    public Graph addPoints(int group, List<Double> x, List<Double> y) {
        for (int i = 0; i < x.size() && i < y.size(); i++) {
            points.get(group).add(new Point(x.get(i), y.get(i)));
        }
        return this;
    }
}
