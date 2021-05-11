package ClientAPP;

import ClientUI.UI;

public class clientUIThread extends Thread{

    @Override
    public void run() {
       final UI videoPlayerUI = new UI();
    }
}
