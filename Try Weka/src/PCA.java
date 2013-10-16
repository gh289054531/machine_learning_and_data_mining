import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.PrincipalComponents;

public class PCA {
	protected static void pca(Instances data) throws Exception {
		PrincipalComponents pca = new PrincipalComponents();
		pca.setInputFormat(data);
		pca.setMaximumAttributes(4);

		Instances newData = Filter.useFilter(data, pca);
		System.out.println("newdata" + newData);

		ArffSaver saver = new ArffSaver();
		saver.setInstances(newData);
		saver.setFile(new File("PCAresult.arff"));
		saver.writeBatch();
	}

	public static void main(String[] args) throws Exception {
		BufferedReader reader = new BufferedReader(new FileReader("PCAin.arff"));
		Instances data = new Instances(reader);
		reader.close();
		if (data.classIndex() == -1) {
			data.setClassIndex(data.numAttributes() - 1);
		}
		pca(data);
	}
}
