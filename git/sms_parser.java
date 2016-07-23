mport java.lang.Math; // header stuff MUST go above the first class
import java.util.HashMap;

// our main class becomes a file but the main method is still found

/*
PNR:4841195289,TRAIN:18646,DOJ:27-10-16,3A,HYB-HWH,Dep:N.A.,
TAPAN KUMAR PAUL+2,B3 57,B3 63,B3 64,
Fare:4295,SC:46.0+PG CHGS
*/


public class HelloWorld
{
  public static void main(String[] args)
  {
    OtherClass myObject = new OtherClass("Hello World!");
    //System.out.print(myObject);
    
    // Station code - location name
    HashMap<String, String> staionCodeToName = new HashMap<>();
    // destname, start date, start time, end date, end time
    HashMap<String,HashMap<String,String>> tripdata = new HashMap<>();
    staionCodeToName.put("HYD","Hyderabad");
    staionCodeToName.put("MUM","Mumbai");
    staionCodeToName.put("BLR","Bangalore");
    staionCodeToName.put("KER","Kerala");
    staionCodeToName.put("HWH","Kolkata");
    
    String aa = staionCodeToName.get("HYD");
    
    
    String[] smses = new String[7];
    
    smses[0] = "PNR:4841195289,TRAIN:18646,DOJ:27-10-16,3A,HYB-HWH,Dep:15-30,TravelTime:26,TAPAN KUMAR PAUL+2,B3 57,B3 63,B3 64,Fare:4295,SC:46.0+PG CHGS";
    smses[1] = "Hi How are you";
    smses[2] = "PNR:4841295289,TRAIN:18646,DOJ:04-11-16,3A,HWH-HYD,Dep:17-45,TravelTime:26,TAPAN KUMAR PAUL+2,B3 57,B3 63,B3 64,Fare:4295,SC:46.0+PG CHGS";
    smses[3] = "PNR:4841395289,TRAIN:18646,DOJ:10-11-16,3A,BLR-KER,Dep:10-45,TravelTime:12,TAPAN KUMAR PAUL+2,B3 57,B3 63,B3 64,Fare:4295,SC:46.0+PG CHGS";
    smses[4] = "Hey babe!!";
    smses[5] = "PNR:4841495289,TRAIN:18646,DOJ:15-11-16,3A,KER-BLR,Dep:21-56,TravelTime:12,TAPAN KUMAR PAUL+2,B3 57,B3 63,B3 64,Fare:4295,SC:46.0+PG CHGS";
    smses[6] = "PNR:4841595289,TRAIN:18646,DOJ:27-10-16,3A,HYB-MUM,Dep:12-30,TravelTime:10,TAPAN KUMAR PAUL+2,B3 57,B3 63,B3 64,Fare:4295,SC:46.0+PG CHGS";
    
    for (int i = 0; i < 7; i++)
    {
      //Check if it sms need to be processed further
      if (smses[i].contains("PNR:") && smses[i].contains("TRAIN:") && smses[i].contains("DOJ:"))
      {
        //System.out.println(smses[i]);
        String[] parts = smses[i].split(",");
        //Do a recheck
        if (parts[2].contains("DOJ:") && parts[4].contains("-") && parts[5].contains("Dep:") && parts[6].contains("TravelTime"))
        {
          String[] fromTo = parts[4].split("-");
          String srcName = staionCodeToName.get(fromTo[0]);
          String destName = staionCodeToName.get(fromTo[1]);
          String[] doj = parts[2].split(":");
          String[] ddmmyy = doj[1].split("-"); // dd, mm, yy
          String[] depTime = parts[5].split(":");
          String[] hhmm = depTime[1].split("-"); // hh, mm
          String[] time = parts[6].split(":");
          String travelTime = time[1]; // time in hours
          
          // This means return journey from destination
          if (tripdata.containsKey(srcName))
          {
            String endDate = doj[1];
            String endTime = depTime[1];
            
            HashMap<String,String> details = tripdata.get(srcName);
            details.put("endDate", endDate);
            details.put("endTime", endTime);
            
            tripdata.put(srcName,details);
          }
          else
          {
            //means new destination
            int hh = Integer.parseInt(hhmm[0]);
            int ttime = Integer.parseInt(travelTime);
            int[] ddmmyyArray = new int[3];
            for(int ii = 0; ii < 3;ii++)
            {
              ddmmyyArray[ii] = Integer.parseInt(ddmmyy[ii]);
            }
            
            int newhh = hh + ttime;
            if (newhh > 24)
            {
              newhh -= 24;
              ddmmyyArray[0]++;
              // TODO: Check for 30/28 day month
              if (ddmmyyArray[0] == 32)
              {
                ddmmyyArray[0] -= 31;
                ddmmyyArray[1]++;
                if (ddmmyyArray[1] == 13)
                {
                  ddmmyyArray[1] -= 12;
                  ddmmyyArray[2] ++;
                }
              }
            }
            
            String startDate = Integer.toString(ddmmyyArray[0]) + "-" + Integer.toString(ddmmyyArray[1]) + "-" + Integer.toString(ddmmyyArray[2]);
            String startTime = Integer.toString(newhh) + "-" + hhmm[1];
            HashMap<String,String> details = new HashMap<>();
            details.put("destName",destName);
            details.put("startDate",startDate);
            details.put("startTime", startTime);
            
            tripdata.put(destName,details);
          }         
        }
      }
      
      // see tripdata after each sms
      System.out.println("After reading new sms...");
      for (String key : tripdata.keySet()) {
        HashMap<String,String> data = tripdata.get(key);
        for(String dataKey : data.keySet()) {
          System.out.print(dataKey);System.out.print(":");System.out.print(data.get(dataKey));System.out.print("\n");
        }
      }
    }
  }
}
// this will become its own file too (and these can be in any order)
public class OtherClass
{
  private String message;
  private boolean answer = false;
  public OtherClass(String input)
  {
    message = "Why, " + input + " Isn't this something?";
  }
  public String toString()
  {
    return message;
  }
}
