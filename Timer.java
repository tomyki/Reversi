import javax.swing.JLabel;


public class Timer implements Runnable{

   JLabel t;

   public Timer(JLabel time){
      this.t=time;
   }

   @Override
   public void run(){
   while(true){

      try{
         Thread.sleep(1000);
      }catch(InterruptedException e){}
      int current =Integer.parseInt(t.getText());
      t.setText((current+1)+"");
     
   }

}

}