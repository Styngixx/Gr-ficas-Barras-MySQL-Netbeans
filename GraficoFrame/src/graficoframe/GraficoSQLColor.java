package graficoframe;

import java.sql.*;
import org.jfree.chart.*;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.chart.ui.TextAnchor;
import org.jfree.chart.ChartPanel;

public class GraficoSQLColor extends javax.swing.JFrame {

    public GraficoSQLColor() {
        initComponents();
        mostrarGrafico();
    }
       

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelGrafico = new javax.swing.JPanel();

        setLocationByPlatform(true);

        javax.swing.GroupLayout panelGraficoLayout = new javax.swing.GroupLayout(panelGrafico);
        panelGrafico.setLayout(panelGraficoLayout);
        panelGraficoLayout.setHorizontalGroup(
            panelGraficoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 622, Short.MAX_VALUE)
        );
        panelGraficoLayout.setVerticalGroup(
            panelGraficoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 481, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelGrafico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelGrafico, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void mostrarGrafico() {
        String url = "jdbc:mysql://localhost:3306/componentes";
        String user = "root";
        String psword = "root";

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        try (Connection cnn = DriverManager.getConnection(url, user, psword)) {
            String sql = "SELECT * FROM ventas";
            Statement st = cnn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                dataset.addValue(rs.getInt("cantidad"), "ventas", rs.getString("producto"));
            }

            // Creación del gráfico
            JFreeChart chart = ChartFactory.createBarChart(
                "Ventas por Producto", "Producto", "Cantidad Vendida", dataset, 
                PlotOrientation.VERTICAL, true, true, false
            );

            CategoryPlot plot = chart.getCategoryPlot();
            BarRenderer renderer = new BarRenderer() {
                private final java.awt.Color[] colores = new java.awt.Color[]{
                    new java.awt.Color(79, 129, 189), // azul
                    new java.awt.Color(192, 80, 77), // rojo
                    new java.awt.Color(155, 187, 89), // verde
                    new java.awt.Color(128, 100, 162) // violeta
                };

                @Override
                public java.awt.Paint getItemPaint(int row, int column) {
                    return colores[column % colores.length];
                }
            };
            
            //Mostrar las etiquetas encima de las barras
            renderer.setDefaultItemLabelGenerator(new org.jfree.chart.labels.StandardCategoryItemLabelGenerator());
            renderer.setDefaultItemLabelsVisible(true);
            renderer.setDefaultItemLabelFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD,12));
            renderer.setDefaultPositiveItemLabelPosition(
                new org.jfree.chart.labels.ItemLabelPosition(
                        org.jfree.chart.labels.ItemLabelAnchor.OUTSIDE12, 
                        org.jfree.chart.ui.TextAnchor.CENTER
                )
            );
            
            plot.setRenderer(renderer);
            plot.setOutlineVisible(false);
            plot.setBackgroundPaint(java.awt.Color.WHITE);
            plot.setRangeGridlinePaint(java.awt.Color.LIGHT_GRAY);
            
            // Visualización del gráfico en el panel
            ChartPanel p = new ChartPanel(chart);
            p.setPreferredSize(new java.awt.Dimension(600, 400));
            
            panelGrafico.removeAll();
            panelGrafico.setLayout(new java.awt.BorderLayout());
            panelGrafico.add(p, java.awt.BorderLayout.CENTER);
            panelGrafico.validate();
            panelGrafico.repaint();
                
            
            
            
        } catch (SQLException e) {
            e.printStackTrace();  // Deberías manejar el error aquí.
        }
    }

    
    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GraficoSQLColor().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel panelGrafico;
    // End of variables declaration//GEN-END:variables
}
