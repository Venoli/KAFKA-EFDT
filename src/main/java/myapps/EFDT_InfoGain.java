import java.util.ArrayList;
import java.util.HashMap;

public class EFDT_InfoGain {
    /* Class for calculating the InformationGain within a node */

    public static double IG(ArrayList<Double> Attributevalue1, ArrayList<Double> Attributevalue2,ArrayList<Double> Label) {
        /* Method for calculating the InformationGain */
        double p0=Label.get(0)/(Label.get(0)+Label.get(1));
        double p1=Attributevalue1.get(0)/(Attributevalue1.get(0)+Attributevalue1.get(1));
        double p2=Attributevalue2.get(0)/(Attributevalue2.get(0)+Attributevalue2.get(1));
        double a= (Attributevalue1.get(0)+Attributevalue1.get(1))/(Label.get(0)+Label.get(1));
        double log2= Math.log(2);

        double HT=-p0*Math.log(p0)/log2-(1-p0)*Math.log(1-p0)/log2;
        double HTa =-a*(p1*Math.log(p1)/log2+(1-p1)*Math.log(1-p1)/log2)
                -(1-a)*(p2*Math.log(p2)/log2+(1-p2)*Math.log(1-p2)/log2);
        double IG = HT-HTa;
        return IG;
    }

    public static HashMap<String,Double> IG_Calc(HashMap<String,Double> map) {
        /* Method for preprocessing which returns InformationGain-Dict */
        ArrayList<String> AttributeList = new ArrayList<String>();
        ArrayList<Double> Attributevalue1 = new ArrayList<Double>();
        ArrayList<Double> Attributevalue2 = new ArrayList<Double>();
        ArrayList<Double> Label = new ArrayList<Double>();
        HashMap<String, Double> IG_calcs = new HashMap<String, Double>();
        String Splitter = new String();

        for (String key : map.keySet() ) {
            if (!AttributeList.contains(key.split("_")[1])  && !key.split("_")[1].equals("Label")){
                AttributeList.add(key.split("_")[1]);}}

        for (String Att: AttributeList) {
            Label.clear();
            Attributevalue1.clear();
            Attributevalue2.clear();
            int i=0;
            for (String key : map.keySet()) {
                if (key.split("_")[1].equals(Att)) {
                    if (i == 0) {
                        Splitter = key.split("_")[2];
                        Attributevalue1.add(map.get(key));
                    } else {
                        if (key.split("_")[2].equals(Splitter)) {
                            Attributevalue1.add(map.get(key));
                        } else {
                            Attributevalue2.add(map.get(key));
                        }
                    }
                    i++;
                }
                if (key.split("_")[1].equals("Label")) {
                    Label.add(map.get(key));
                }
            }
            double IG_value = IG(Attributevalue1,Attributevalue2,Label);
            IG_calcs.put(Att,IG_value);
        }
        return IG_calcs;
    }

    public static void main(String[] args) {
        /* main with example dict */
        
        HashMap<String, Double> map = new HashMap<String, Double>();
        map.put("node_Outlook_Sunny_0",2.0);
        map.put("node_Outlook_Sunny_1",3.0);
        map.put("node_Outlook_Rainy_0",3.0);
        map.put("node_Outlook_Rainy_1",6.0);
        map.put("node_Temp_Hot_0",2.0);
        map.put("node_Temp_Hot_1",2.0);
        map.put("node_Temp_Cold_0",3.0);
        map.put("node_Temp_Cold_1",7.0);
        map.put("node_Humidity_High_0",4.0);
        map.put("node_Humidity_High_1",3.0);
        map.put("node_Humidity_Normal_0",1.0);
        map.put("node_Humidity_Normal_1",6.0);
        map.put("node_Windy_True_0",3.0);
        map.put("node_Windy_True_1",3.0);
        map.put("node_Windy_False_0",2.0);
        map.put("node_Windy_False_1",6.0);
        map.put("node_Label_0",5.0);
        map.put("node_Label_1",9.0);

        System.out.println(IG_Calc(map));
        }
    }


