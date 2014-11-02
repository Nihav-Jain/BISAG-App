/*
 * CGProjectView.java
 */

package cg_project;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.media.jai.*;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.Raster;
import java.awt.image.RenderedImage;
import java.awt.image.WritableRaster;
import java.awt.image.renderable.ParameterBlock;
import org.jdesktop.application.Action;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Vector;
import javax.media.jai.iterator.RandomIter;
import javax.media.jai.iterator.RandomIterFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.omg.CORBA.FREE_MEM;

/**
 * The application's main frame.
 */
public class CGProjectView extends FrameView
{
        static{
            System.setProperty("com.sun.media.jai.disableMediaLib", "true");
        }
        boolean paramsSet,onlyBumped=true;
        boolean plot_but = false;
        
        int arr[], clicked, img_id, newWidth, newHeight,epicenterIndex;
        int counter = 0, array[][];
        int index = 5, x0, y0, x1, y1, first_click = 0;
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize() ;
        
        double def_depth, def_mag, def_cenX, def_cenY, minLatitude, maxLatitude, minLongitude, maxLongitude;
        float normalVectors[][],verticalExaggeration=1.0f, scaleIndex = 1.0F;

        ArrayList<Point> list;                          //List of points to be passed to the getLinePixels() method
        ArrayList pathList;                             //List to store path of all the selected DEM images.
        ArrayList<Double> latitudeUL,latitudeLR;        //UL - Upper Left
        ArrayList<Double> longitudeUL,longitudeLR;      //LR - Lower Right

        Vector result_arr=new Vector();
        long t1=0, t2=0, t3=0, t4=0;
        
        String fName,outputPath,inputPath;
        WritableRaster writableRaster;
        Image mainimg;                                  //Originally loaded image.
        Image scaledImg;
        PlanarImage img;
        BufferedImage temp;                             //Smaller resized image.
        Raster dataimg;                                 //Raster data of original image.
        Raster temp_data;                               //Rasted data of smaller image.
        Point mousePos;
        Dimension new_dim;
        File farray[], inputFiles[];
        Dimension scaledDim;
        File logFile;                   //A txt file with x,y co-ordinates and respective intensities.
        PrintWriter logFileWriter;
       
        Bumped b;
        protected StringBuffer pixelInfo;
        protected double[] dpixel;
        protected RandomIter readIterator;
        protected PlanarImage surrogateImage; // Image to be displayed with smaller parameter block.
        protected double minValue,maxValue; // Range of the values of intensity of image.
        ImageIcon ic=new ImageIcon();
        PanelInScroll panel3; // panel to display loaded geotiff image
        
        int scale;
        boolean fileChoosersEnable;
        String mode;
        
        public CGProjectView(SingleFrameApplication app) throws Exception{
        super(app);


        initComponents();
         if(CGProjectApp.arg.length == 3){
            inputPath = CGProjectApp.arg[0];
            outputPath = CGProjectApp.arg[1];
            mode = CGProjectApp.arg[2];
            fileChoosersEnable = false;
        }
        latitudeUL = new ArrayList();
        longitudeUL = new ArrayList();
        latitudeLR = new ArrayList();
        longitudeLR = new ArrayList();

        pathList = new ArrayList();
        mousePos = new Point();
        clicked = 0;
        
        list = new ArrayList<Point>();
        new_dim = new Dimension();
        arr = new int[1];
        paramsSet = false;
        def_cenX = 0;
        def_cenY = 0;
        def_depth = 0;
        def_mag = 0;
        b = null;
    }
 
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jButton8 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        jDialog2 = new javax.swing.JDialog();
        jFileChooser1 = new javax.swing.JFileChooser();

        mainPanel.setName("mainPanel"); // NOI18N
        this.getFrame().setExtendedState(JFrame.MAXIMIZED_BOTH);

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(cg_project.CGProjectApp.class).getContext().getResourceMap(CGProjectView.class);
        jLabel1.setFont(resourceMap.getFont("jLabel1.font")); // NOI18N
        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jPanel1.setBackground(resourceMap.getColor("jPanel1.background")); // NOI18N
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setName("jPanel1"); // NOI18N

        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N

        jLabel5.setText(resourceMap.getString("jLabel5.text")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N

        jLabel6.setText(resourceMap.getString("jLabel6.text")); // NOI18N
        jLabel6.setName("jLabel6"); // NOI18N

        jLabel7.setText(resourceMap.getString("jLabel7.text")); // NOI18N
        jLabel7.setName("jLabel7"); // NOI18N

        jLabel8.setText(resourceMap.getString("jLabel8.text")); // NOI18N
        jLabel8.setName("jLabel8"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(cg_project.CGProjectApp.class).getContext().getActionMap(CGProjectView.class, this);
        jButton8.setAction(actionMap.get("selectOutDirectory")); // NOI18N
        jButton8.setText(resourceMap.getString("jButton8.text")); // NOI18N
        jButton8.setActionCommand(resourceMap.getString("jButton8.actionCommand")); // NOI18N
        jButton8.setName("jButton8"); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5))
                        .addGap(52, 52, 52)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel7)
                            .addComponent(jLabel6))))
                .addGap(167, 167, 167))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(92, 92, 92)
                .addComponent(jLabel2)
                .addContainerGap(225, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel6))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel7))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel8))
                .addGap(69, 69, 69)
                .addComponent(jButton8)
                .addContainerGap(460, Short.MAX_VALUE))
        );

        jPanel2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPanel2.setName("jPanel2"); // NOI18N

        jScrollPane1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jScrollPane1.setName("jScrollPane1"); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1063, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 628, Short.MAX_VALUE)
        );

        jButton1.setAction(actionMap.get("zoomOut")); // NOI18N
        jButton1.setIcon(resourceMap.getIcon("jButton1.icon")); // NOI18N
        jButton1.setText(resourceMap.getString("jButton1.text")); // NOI18N
        jButton1.setName("jButton1"); // NOI18N

        jButton2.setAction(actionMap.get("zoomIn")); // NOI18N
        jButton2.setIcon(resourceMap.getIcon("jButton2.icon")); // NOI18N
        jButton2.setName("jButton2"); // NOI18N

        this.getFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(38, 38, 38)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(882, 882, 882))
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(19, 19, 19)))
                .addContainerGap())
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        menuBar.setName("menuBar"); // NOI18N

        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        jMenuItem1.setAction(actionMap.get("OpenFileChooser")); // NOI18N
        jMenuItem1.setText(resourceMap.getString("jMenuItem1.text")); // NOI18N
        jMenuItem1.setName("jMenuItem1"); // NOI18N
        fileMenu.add(jMenuItem1);

        exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        jDialog2.setMinimumSize(new java.awt.Dimension(425, 245));
        jDialog2.setName("jDialog2"); // NOI18N

        jFileChooser1.setName("jFileChooser1"); // NOI18N
        jFileChooser1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFileChooser1ActionPerformed(evt);
            }
        });
        jFileChooser1.setFileSelectionMode(jFileChooser1.DIRECTORIES_ONLY);

        javax.swing.GroupLayout jDialog2Layout = new javax.swing.GroupLayout(jDialog2.getContentPane());
        jDialog2.getContentPane().setLayout(jDialog2Layout);
        jDialog2Layout.setHorizontalGroup(
            jDialog2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jFileChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jDialog2Layout.setVerticalGroup(
            jDialog2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jFileChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        setComponent(mainPanel);
        setMenuBar(menuBar);
    }// </editor-fold>//GEN-END:initComponents

    private void jFileChooser1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFileChooser1ActionPerformed
        if(evt.getActionCommand()=="CancelSelection"){
            this.getApplication().exit();
        }
        else if(evt.getActionCommand()=="ApproveSelection"){
            outputPath = jFileChooser1.getSelectedFile().toString();

            jDialog2.setVisible(false);
            try {
                bumpImages();
            } catch (Exception ex) {
                Logger.getLogger(CGProjectView.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }//GEN-LAST:event_jFileChooser1ActionPerformed

    @Action
    public void OpenFileChooser() {
        FileChooser fc=new FileChooser(this);
        fc.setLocationRelativeTo(this.getFrame());
        fc.setVisible(true);
        this.getFrame().setVisible(false);
    }
    public void loadImage(File fin) throws Exception {
        inputPath = fin.getAbsolutePath();
        this.getFrame().setVisible(true);
        String s=fin.getAbsoluteFile().toString();
        settingImageParams(s);

        jLabel6.setText(fin.getName());
        jLabel7.setText(img.getWidth()+" x "+img.getHeight()+" Pixels");
        t1=System.currentTimeMillis();
        DisplayDEM(img);
        t2=System.currentTimeMillis();
        jLabel8.setText((t2-t1)+" Milliseconds");
}

    public void settingImageParams(String s){
        img=JAI.create("fileload",s);
        settingImageDataParams(img);
}
    public void settingImageDataParams(RenderedImage image){
        ParameterBlock pbMaxMin = new ParameterBlock();
	pbMaxMin.addSource(image);
	RenderedOp extrema = JAI.create("extrema", pbMaxMin);
	double[] allMins = (double[])extrema.getProperty("minimum");
	double[] allMaxs = (double[])extrema.getProperty("maximum");
	minValue = allMins[0];
	maxValue = allMaxs[0];
        scaling(scaleIndex);
    }
    public void DisplayDEM(RenderedImage image){

        readIterator = RandomIterFactory.create(image, null);
	dpixel = new double[image.getSampleModel().getNumBands()];
	ParameterBlock pbMaxMin = new ParameterBlock();
	pbMaxMin.addSource(image);
	RenderedOp extrema = JAI.create("extrema", pbMaxMin);
	double[] allMins = (double[])extrema.getProperty("minimum");
	double[] allMaxs = (double[])extrema.getProperty("maximum");
	minValue = allMins[0];
	maxValue = allMaxs[0];
	double[] multiplyByThis = new double[1];
	multiplyByThis[0] = (255.0/(maxValue-minValue));
	double[] addThis = new double[1];
	addThis[0] = -minValue/3;
		//rescale the pixels levels:
        
	ParameterBlock pbRescale = new ParameterBlock();
	pbRescale.add(multiplyByThis);
        pbRescale.add(addThis);
		//pbRescale.add(addThis);
	pbRescale.addSource(image);
	surrogateImage = (PlanarImage)JAI.create("rescale", pbRescale);
		//converting the data type for displaying.
	ParameterBlock pbConvert = new ParameterBlock();
	pbConvert.addSource(surrogateImage);
	pbConvert.add(DataBuffer.TYPE_BYTE);
      

	surrogateImage = JAI.create("format", pbConvert);
        temp = surrogateImage.getAsBufferedImage();
        temp_data = temp.getData();
        mainimg = temp;
        scaledDim = new Dimension();
        scaledDim.setSize(0.2 * temp.getHeight(), 0.2 * temp.getWidth());
        scaledImg = temp.getScaledInstance(scaledDim.width, scaledDim.height, BufferedImage.TYPE_BYTE_GRAY);
        new_dim = scaledDim;
        //scale = temp.getHeight() / scaledDim.height;
        System.out.println(scale);
        index = 1;
        panel3 = new PanelInScroll(this);
        panel3.setCursor(new java.awt.Cursor(java.awt.Cursor.CROSSHAIR_CURSOR));
        panel3.setName("panel3");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(panel3);
        panel3.setLayout(jPanel3Layout);
        jScrollPane1.setViewportView(panel3);
        jScrollPane1.updateUI();
        jButton1.setEnabled(true);
        jButton2.setEnabled(true);
	}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton8;
    private javax.swing.JDialog jDialog2;
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    // End of variables declaration//GEN-END:variables


    private JDialog aboutBox;

    

class PanelInScroll extends JPanel
{
    CGProjectView bpv;
    PanelInScroll(CGProjectView _bpv)
    {
        bpv = _bpv;
    }
    
    public void setBPV(CGProjectView _bpv)
    {
        bpv = _bpv;
    }
    
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        if(bpv != null)
        {
            g.drawImage(bpv.scaledImg, 0, 0, null);
            this.setPreferredSize(bpv.new_dim);
            //System.out.println(bpv.new_dim.getWidth() + " " + bpv.new_dim.getHeight());
        }
            int i;
            for(i=0;i<bpv.list.size()-1;i++)
            {
                g.drawLine(bpv.list.get(i).x, bpv.list.get(i).y, bpv.list.get(i+1).x, bpv.list.get(i+1).y);
            }
            if(bpv.clicked > 0)
                g.drawLine(bpv.list.get(bpv.list.size()-1).x, bpv.list.get(bpv.list.size()-1).y, bpv.mousePos.x, bpv.mousePos.y);
  
    }
    }

    @Action
    public void zoomOut() {
        if(index>1)
        {
            for(int i=0;i<list.size();i++)
            {
                Point p = list.get(i);
                p.setLocation(p.getX()*5/index, p.getY()*5/index);
                list.set(i, p);
            }
            index--;
            changedDisplay(index);
        }
    }

    @Action
    public void zoomIn() {
        if(index<=5)
        {
            for(int i=0;i<list.size();i++)
            {
                Point p = list.get(i);
                p.setLocation(p.getX()*5/index, p.getY()*5/index);
                list.set(i, p);
            }
            index++;
            changedDisplay(index);
        }
    }
    
    public void changedDisplay(int index){
        new_dim.setSize((img.getWidth()*index)/5,(img.getHeight()*index)/5);
        scaledDim = new_dim;
        scaledImg = temp.getScaledInstance(new_dim.width,new_dim.height,BufferedImage.TYPE_BYTE_GRAY);
        
            for(int i=0;i<list.size();i++){
                Point p = list.get(i);
                p.setLocation(p.getX()*index/5, p.getY()*index/5);
                list.set(i, p);
            }

        panel3.setSize(new_dim);
        panel3.updateUI();
        panel3.revalidate();
    }

  
   
    @Action
    public void bumpImages() throws Exception {

        if(this.onlyBumped){
            farray = new File[1];
            System.out.println(inputPath);
            farray[0] = new File(inputPath);
        }
        
        
        t3 = System.currentTimeMillis();
        for(int i=0;i<farray.length;i++){
            if(farray[i] == null){}
            else if((farray[i].getPath())!="####"){
                for(int k=0;k<pathList.size();k++){
                    if(farray[i].getPath() == pathList.get(k)){
                        img_id = k;
                        break;
                    }
                }
                
            settingImageParams(farray[i].getPath());
            fName = farray[i].getName().substring(0,5);
            
            b = new Bumped(this);
            FREE_MEM f=new FREE_MEM();}
    }
        t4 = System.currentTimeMillis();
        System.out.println("Time taken for images = "+(t4-t3) +" milliseconds." );
        System.exit(0);
        
    }

    public void scaling(float scaleIndex){
        
         ParameterBlock pb = new ParameterBlock();
         pb.addSource(img);      // The source image
         pb.add(scaleIndex);
         pb.add(scaleIndex);
         pb.add(0.0F);           // The x scale factor
         pb.add(0.0F);           // The y scale factor
         pb.add(new InterpolationNearest());         // The interpolation method
         //pb.add(new InterpolationBicubic(2));
         PlanarImage scaledImage = JAI.create("scale",pb);
         dataimg=scaledImage.getData();
         temp_data=dataimg;
         newWidth = dataimg.getWidth();
         newHeight = dataimg.getHeight();
         
    }
    
    @Action
    public void selectOutDirectory() {
        jDialog2.setTitle("Select output directory");
        jDialog2.setSize(610,435);
        jDialog2.setLocationRelativeTo(this.getFrame());
        jDialog2.setVisible(true);
    }


}

