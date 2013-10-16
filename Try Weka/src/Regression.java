import weka.classifiers.functions.LinearRegression;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

public class Regression {
	public static void main(String[] args) throws Exception {
		Attribute a1 = new Attribute("houseSize", 0);
		Attribute a2 = new Attribute("lotSize", 1);
		Attribute a3 = new Attribute("bedrooms", 2);
		Attribute a4 = new Attribute("granite", 3);
		Attribute a5 = new Attribute("bathroom", 4);
		Attribute a6 = new Attribute("sellingPrice", 5);
		
		FastVector attrs = new FastVector();
		attrs.addElement(a1);
		attrs.addElement(a2);
		attrs.addElement(a3);
		attrs.addElement(a4);
		attrs.addElement(a5);
		attrs.addElement(a6);
		
		Instance i1 = new Instance(6);
		i1.setValue(a1, 3529);
		i1.setValue(a2, 9191);
		i1.setValue(a3, 6);
		i1.setValue(a4, 0);
		i1.setValue(a5, 0);
		i1.setValue(a6, 205000);

		Instance i2 = new Instance(6);
		i2.setValue(a1, 3247);
		i2.setValue(a2, 10061);
		i2.setValue(a3, 5);
		i2.setValue(a4, 1);
		i2.setValue(a5, 1);
		i2.setValue(a6, 224900);

		Instance i3 = new Instance(6);
		i3.setValue(a1, 4032);
		i3.setValue(a2, 10150);
		i3.setValue(a3, 5);
		i3.setValue(a4, 0);
		i3.setValue(a5, 1);
		i3.setValue(a6, 197900);

		Instance i4 = new Instance(6);
		i4.setValue(a1, 2397);
		i4.setValue(a2, 14156);
		i4.setValue(a3, 4);
		i4.setValue(a4, 1);
		i4.setValue(a5, 0);
		i4.setValue(a6, 189900);

		Instance i5 = new Instance(6);
		i5.setValue(a1, 2200);
		i5.setValue(a2, 9600);
		i5.setValue(a3, 4);
		i5.setValue(a4, 0);
		i5.setValue(a5, 1);
		i5.setValue(a6, 195000);

		Instance i6 = new Instance(6);
		i6.setValue(a1, 3536);
		i6.setValue(a2, 19994);
		i6.setValue(a3, 6);
		i6.setValue(a4, 1);
		i6.setValue(a5, 1);
		i6.setValue(a6, 325000);

		Instance i7 = new Instance(6);
		i7.setValue(a1, 2983);
		i7.setValue(a2, 9365);
		i7.setValue(a3, 5);
		i7.setValue(a4, 0);
		i7.setValue(a5, 1);
		i7.setValue(a6, 230000);
		
		Instances dataset=new Instances("houseprice", attrs, 7);
		dataset.add(i1);
		dataset.add(i2);
		dataset.add(i3);
		dataset.add(i4);
		dataset.add(i5);
		dataset.add(i6);
		dataset.add(i7);

		dataset.setClassIndex(dataset.numAttributes()-1);
		
		
		Instance test=new Instance(6);
		test.setValue(a1, 3198);
		test.setValue(a2, 9669);
		test.setValue(a3, 5);
		test.setValue(a4, 3);
		test.setValue(a5, 1);
		
		LinearRegression lr=new LinearRegression();
		lr.buildClassifier(dataset);
		double predictPrice=lr.classifyInstance(test);
		System.out.println(predictPrice);
	}
}
