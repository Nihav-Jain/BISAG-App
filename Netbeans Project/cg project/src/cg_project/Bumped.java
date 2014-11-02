
package cg_project;

import javax.swing.JFrame;
import javax.swing.JScrollPane;


public class Bumped extends JFrame{
    CGProjectView bpv;
    JScrollPane scroller;
    BumpedPanel panel;
    Bumped(CGProjectView _bpv)
    {
        bpv = _bpv;
        if(bpv.onlyBumped){
            BumpedPanel p = new BumpedPanel(bpv);
            p.execute();
        }
    }
}
