package com.jchen.graph;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class Graph {
    private int width, height;
    private List<List<Point>> points;
    private boolean renderLines;
    private int fontSize;
    private int strokeSize;
    private String title, xAxis, yAxis;

    public Graph(int width, int height) {
        points = new ArrayList<>();
        renderLines = false;
        this.width = width;
        this.height = height;
        strokeSize = 3;
        fontSize = 10;
    }

    public String getTitle() {
        return title;
    }

    public Graph setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getxAxis() {
        return xAxis;
    }

    public Graph setxAxis(String xAxis) {
        this.xAxis = xAxis;
        return this;
    }

    public String getyAxis() {
        return yAxis;
    }

    public Graph setyAxis(String yAxis) {
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

        graphics.setFont(new Font("Arial", Font.PLAIN, fontSize));
        graphics.setColor(Color.BLACK);
        double xInterval = maxX / 10;
        for (double x = 0; x <= maxX; x += xInterval) {
            int scaledX = (int) (((x / maxX) * 0.8 + 0.1) * width);
            int scaledY = height - (int) (height * 0.1 - fontSize * 4 / 3);
            String text = doubleString(x);
            graphics.drawString(text, scaledX - ((text.length() / 2f) * fontSize * 4 / 3) / 2, scaledY);
            graphics.setStroke(new BasicStroke(strokeSize / 2f));
            int topY = (int) (height * 0.1);
            graphics.drawLine(scaledX, (int) (scaledY - fontSize * 4f / 3), scaledX, topY);
        }

        double yInterval = maxY / 10;
        for (double y = 0; y <= maxY; y += yInterval) {
            int scaledX = (int) (width * 0.1);
            int scaledY = height - (int) (((y / maxY) * 0.8 + 0.1) * height);
            String text = doubleString(y);
            graphics.drawString(text, scaledX - (text.length() * fontSize * 4f / 3)/2, scaledY + (fontSize * 4 / 3f) / 4);
            graphics.setStroke(new BasicStroke(strokeSize / 2f));
            int topX = (int) (width * 0.9);
            graphics.drawLine(scaledX, scaledY, topX, scaledY);
        }

        if (title != null) {
            graphics.drawString(title, width / 2f - ((title.length() / 2f) * fontSize * 4 / 3)/2, fontSize * 4f / 3);
        }

        if (xAxis != null) {
            graphics.drawString(xAxis, width / 2f - ((xAxis.length() / 2f) * fontSize * 4 / 3)/2, height - fontSize * 4f / 3);
        }

        if (yAxis != null) {
            BufferedImage text = new BufferedImage((int) ((yAxis.length() * 4f/3 * fontSize)/2), (int) (4f/3 * fontSize * 1.5), BufferedImage.TYPE_INT_ARGB);
            Graphics2D textGraphics = (Graphics2D) text.getGraphics();
            textGraphics.setFont(new Font("Arial", Font.PLAIN, fontSize));
            textGraphics.setColor(Color.BLACK);
            textGraphics.setBackground(new Color(0, 0, 0, 0));
            textGraphics.drawString(yAxis, 0,  (int) (4f/3 * fontSize));
            textGraphics.rotate(-Math.PI/2);
            textGraphics.translate(-(height/2 + text.getWidth()/2), 0);
            graphics.drawImage(text, textGraphics.getTransform(), null);
        }

        for (int i = 0; i < points.size(); i++) {
            List<Point> group = points.get(i);
            graphics.setColor(Color.getHSBColor((i * 0.35f) % 1, 1, 0.75f));
            for (int j = 0; j < group.size(); j++) {
                Point point = group.get(j);
                int scaledX = (int) ((point.getX() / maxX) * width * 0.8 + width * 0.1);
                int scaledY = height - (int) ((point.getY() / maxY) * height * 0.8 + height * 0.1);
                graphics.fillOval(scaledX, scaledY, strokeSize, strokeSize);
                if (renderLines && j > 0) {
                    Point last = group.get(j - 1);
                    int lastScaledX = (int) ((last.getX() / maxX) * width * 0.8 + width * 0.1);
                    int lastScaledY = height - (int) ((last.getY() / maxY) * height * 0.8 + height * 0.1);
                    graphics.setStroke(new BasicStroke(strokeSize));
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

    private static String doubleString(double input) {
        input = new BigDecimal(input).setScale(3, RoundingMode.HALF_UP).doubleValue();
        if (((int) input) == input) {
            return String.valueOf((int) input);
        } else {
            return String.valueOf(input);
        }
    }
}
