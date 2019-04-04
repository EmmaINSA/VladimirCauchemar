//===========================================================================================================================
// Description
//===========================================================================================================================


// 
// Elle est de type 


//===========================================================================================================================
// Imports
//===========================================================================================================================


import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainFourier extends JPanel {

//===========================================================================================================================
// Constructeur
//===========================================================================================================================

public MainFourier(String instrument, double frequence) {

    instrument instru = new instrument(instrument);

    List<Double> plot = new ArrayList<>();

    String rang = 1+"";

        for (int k = 0; k<15; k++){
        plot.add(instru.amplitudeTable.get(k));
        }

    setLayout(new BorderLayout());

    JLabel title = new JLabel("Analyse de Fourier");

    title.setFont(new Font("Arial", Font.BOLD, 25));
    title.setHorizontalAlignment(JLabel.CENTER);

    JPanel graphPanel = new GraphPanel(plot,frequence);

    MainFourier.VerticalPanel vertPanel = new MainFourier.VerticalPanel();

    MainFourier.HorizontalPanel horiPanel = new MainFourier.HorizontalPanel();

    add(title, BorderLayout.NORTH);
    add(horiPanel, BorderLayout.SOUTH);
    add(vertPanel, BorderLayout.WEST);
    add(graphPanel, BorderLayout.CENTER);
}

//===========================================================================================================================

//===========================================================================================================================

class VerticalPanel extends JPanel {

    public VerticalPanel() {
        setPreferredSize(new Dimension(25, 0));
    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D gg = (Graphics2D) g;
        gg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Font font = new Font("Arial", Font.PLAIN, 15);

        String string = "Amplitude (ua)";

        FontMetrics metrics = g.getFontMetrics(font);
        int width = metrics.stringWidth(string);
        int height = metrics.getHeight();

        gg.setFont(font);

        drawRotate(gg, getWidth(), (getHeight() + width) / 2, 270, string);
    }

    public void drawRotate(Graphics2D gg, double x, double y, int angle, String text) {
        gg.translate((float) x, (float) y);
        gg.rotate(Math.toRadians(angle));
        gg.drawString(text, 0, 0);
        gg.rotate(-Math.toRadians(angle));
        gg.translate(-(float) x, -(float) y);
    }

}

//===========================================================================================================================
// Sous-panel pour l'axe des abscisses
//===========================================================================================================================

class HorizontalPanel extends JPanel {

    public HorizontalPanel() {
        setPreferredSize(new Dimension(0, 25));
    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D gg = (Graphics2D) g;
        gg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Font font = new Font("Arial", Font.PLAIN, 15);

        String string = "n-ieme harmonique";

        FontMetrics metrics = g.getFontMetrics(font);
        int width = metrics.stringWidth(string);
        int height = metrics.getHeight();

        gg.setFont(font);

        gg.drawString(string, (getWidth() - width) / 2, 11);
    }

}

//===========================================================================================================================
// Sous-panel pour le graphique
//===========================================================================================================================

static class GraphPanel extends JPanel {

    private int width = 800;
    private int heigth = 400;
    private int padding = 25;
    private int labelPadding = 25;
    private Color[] lineColor = {new Color(0,0,255), new Color(155,0,191), new Color(191,0,74), new Color(150,191,0),
                                new Color(0,150,191), new Color(0,74,191), new Color(0,0,255), new Color(78,0,191), 
                                new Color(73,191,0), new Color(0,191,2), new Color(0,191,78), new Color(0,191,155), 
                                new Color(191,79,0), new Color(191,0,150), new Color(191,155,0)};
    private Color[] pointColor = {new Color(0,0,255), new Color(155,0,191), new Color(191,0,74), new Color(150,191,0),
                                new Color(0,150,191), new Color(0,74,191), new Color(0,0,255), new Color(78,0,191), 
                                new Color(73,191,0), new Color(0,191,2), new Color(0,191,78), new Color(0,191,155), 
                                new Color(191,79,0), new Color(191,0,150), new Color(191,155,0)};
    private Color gridColor = new Color(200, 200, 200, 200);
    private static final Stroke GRAPH_STROKE = new BasicStroke(2f);
    private int pointWidth = 4;
    private int numberYDivisions = 10;
    private List<Double> plot;
    private double frequence;

    public GraphPanel(List<Double> plot, double frequence) {
        this.plot = plot;
        this.frequence = frequence;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        double xScale = ((double) getWidth() - (2 * padding) - labelPadding) / (plot.size() - 1);
        double yScale = ((double) getHeight() - 2 * padding - labelPadding) / (getMaxScore() - getMinScore());

        List<Point> graphPoints = new ArrayList<>();
        for (int i = 0; i < plot.size(); i++) {
            int x1 = (int) (i * xScale + padding + labelPadding);
            int y1 = (int) ((getMaxScore() - plot.get(i)) * yScale + padding);
            graphPoints.add(new Point(x1, y1));
        }

        //===========================================================================================================================
        // Fond Blanc
        //===========================================================================================================================

        g2.setColor(Color.WHITE);
        g2.fillRect(padding + labelPadding, padding, getWidth() - (2 * padding) - labelPadding, getHeight() - 2 * padding - labelPadding);
        g2.setColor(Color.BLACK);

        //===========================================================================================================================
       
        //===========================================================================================================================
        for (int i = 0; i < numberYDivisions + 1; i++) {
            int x0 = padding + labelPadding;
            int x1 = pointWidth + padding + labelPadding;
            int y0 = getHeight() - ((i * (getHeight() - padding * 2 - labelPadding)) / numberYDivisions + padding + labelPadding);
            int y1 = y0;
            if (plot.size() > 0) {
                g2.setColor(gridColor);
                g2.drawLine(padding + labelPadding + 1 + pointWidth, y0, getWidth() - padding, y1);
                g2.setColor(Color.BLACK);
                String yLabel = ((int) ((getMinScore() + (getMaxScore() - getMinScore()) * ((i * 1.0) / numberYDivisions)) * 100)) / 100.0 + "";
                FontMetrics metrics = g2.getFontMetrics();
                int labelWidth = metrics.stringWidth(yLabel);
                g2.drawString(yLabel, x0 - labelWidth - 5, y0 + (metrics.getHeight() / 2) - 3);
            }
            g2.drawLine(x0, y0, x1, y1);
        }

        //===========================================================================================================================
      
        //===========================================================================================================================
        
        int graduation = (int)Math.round(plot.size());

        for (int i = 0; i < graduation; i++) {
                int x0 = i * (getWidth() - padding * 2 - labelPadding) / (graduation - 1) + padding + labelPadding;
                int x1 = x0;
                int y0 = getHeight() - padding - labelPadding;
                int y1 = y0 - pointWidth;
                if ((i % ((int) ((graduation / 20.0)) + 1)) == 0) {
                    g2.setColor(gridColor);
                    g2.drawLine(x0, getHeight() - padding - labelPadding - 1 - pointWidth, x1, padding);
                    g2.setColor(Color.BLACK);
                    String xLabel = i + "";
                    FontMetrics metrics = g2.getFontMetrics();
                    int labelWidth = metrics.stringWidth(xLabel);
                    g2.drawString(xLabel, x0 - labelWidth / 2, y0 + metrics.getHeight() + 3);
                }
                g2.drawLine(x0, y0, x1, y1);
        }

        //===========================================================================================================================
        // Axe X et Y
        //===========================================================================================================================

        g2.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, padding + labelPadding, padding);
        g2.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, getWidth() - padding, getHeight() - padding - labelPadding);

        Stroke oldStroke = g2.getStroke();
        g2.setStroke(GRAPH_STROKE);
        for (int i = 0; i < graphPoints.size() - 1; i++) {
            g2.setColor(lineColor[i]);
            int x1 = graphPoints.get(i).x;
            int y1 = graphPoints.get(i).y;
            int y0 = (int)(padding+(yScale*getMaxScore()));
            g2.drawLine(x1, y1, x1, y0);
        }

        g2.setStroke(oldStroke);
        for (int i = 0; i < graphPoints.size(); i++) {
            g2.setColor(pointColor[i]);
            int x = graphPoints.get(i).x - pointWidth / 2;
            int y = graphPoints.get(i).y - pointWidth / 2;
            int ovalW = pointWidth;
            int ovalH = pointWidth;
            g2.fillOval(x, y, ovalW, ovalH);
        }
    }

//===========================================================================================================================

//===========================================================================================================================

    private double getMinScore() {
        double minScore = Double.MAX_VALUE;
        for (Double score : plot) {
            minScore = Math.min(minScore, score);
        }
        return minScore;
    }

    private double getMaxScore() {
        double maxScore = Double.MIN_VALUE;
        for (Double score : plot) {
            maxScore = Math.max(maxScore, score);
        }
        return maxScore;
    }

    public void setScores(List<Double> plot) {
        this.plot = plot;
        invalidate();
        this.repaint();
    }

    public List<Double> getScores() {
        return plot;
    }

    public Dimension getPreferredSize() {
        return new Dimension(width, heigth);
    }

        public void drawRotate(Graphics2D gg, double x, double y, int angle, String text) {
            gg.translate((float) x, (float) y);
            gg.rotate(Math.toRadians(angle));
            gg.drawString(text, 0, 0);
            gg.rotate(-Math.toRadians(angle));
            gg.translate(-(float) x, -(float) y);
        }

    }

}