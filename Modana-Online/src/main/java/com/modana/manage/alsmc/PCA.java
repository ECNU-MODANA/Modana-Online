package com.modana.manage.alsmc;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import Jama.Matrix;

public class PCA {
	public static int pcaN = 0;
	Map<String, String> map = new HashMap<String, String>();
	public double[][] changeAverageToZero(double[][] primary) {
		int n = primary.length;
		int m = primary[0].length;
		double[] sum = new double[m];
		double[] average = new double[m];
		double[][] averageArray = new double[n][m];
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				sum[i] += primary[j][i];
			}
			average[i] = sum[i] / n;
		}
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				averageArray[j][i] = primary[j][i] - average[i];
			}
		}
		return averageArray;
	}

	/**
	 * 
	 * ����Э�������
	 * 
	 * @param matrix
	 *            ���Ļ���ľ���
	 * @return result Э�������
	 */
	public double[][] getVarianceMatrix(double[][] matrix) {
		int n = matrix.length;// ����
		int m = matrix[0].length;// ����
		double[][] result = new double[m][m];// Э�������
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < m; j++) {
				double temp = 0;
				for (int k = 0; k < n; k++) {
					temp += matrix[k][i] * matrix[k][j];
				}
				result[i][j] = temp / (n - 1);
			}
		}
		return result;
	}

	public double[][] getEigenvalueMatrix(double[][] matrix) {
		Matrix A = new Matrix(matrix);
		// ������ֵ��ɵĶԽǾ���,eig()��ȡ����ֵ
		//A.eig().getD().print(10, 6);
		double[][] result = A.eig().getD().getArray();
		return result;
	}

	public double[][] getEigenVectorMatrix(double[][] matrix) {
		Matrix A = new Matrix(matrix);
		//A.eig().getV().print(6, 2);
		double[][] result = A.eig().getV().getArray();
		return result;
	}

	public Matrix getPrincipalComponent(double[][] primaryArray,
			double[][] eigenvalue, double[][] eigenVectors) {
		Matrix A = new Matrix(eigenVectors);// ����һ��������������
		double[][] tEigenVectors = A.transpose().getArray();// ��������ת��
		Map<Integer, double[]> principalMap = new HashMap<Integer, double[]>();// key=���ɷ�����ֵ��value=������ֵ��Ӧ����������
		TreeMap<Double, double[]> eigenMap = new TreeMap<Double, double[]>(
				Collections.reverseOrder());// key=����ֵ��value=��Ӧ��������������ʼ��Ϊ��ת����ʹmap��keyֵ��������
		double total = 0;// �洢����ֵ�ܺ�
		int index = 0, n = eigenvalue.length;
		double[] eigenvalueArray = new double[n];// ������ֵ����Խ����ϵ�Ԫ�طŵ�����eigenvalueArray��
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (i == j)
					eigenvalueArray[index] = eigenvalue[i][j];
			}
			index++;
		}

		for (int i = 0; i < tEigenVectors.length; i++) {
			double[] value = new double[tEigenVectors[0].length];
			value = tEigenVectors[i];
			eigenMap.put(eigenvalueArray[i], value);
		}

		// �������ܺ�
		for (int i = 0; i < n; i++) {
			total += eigenvalueArray[i];
		}
		// ѡ��ǰ�������ɷ�
		double temp = 0;
		int principalComponentNum = 0;// ���ɷ���
		List<Double> plist = new ArrayList<Double>();// ���ɷ�����ֵ
		for (double key : eigenMap.keySet()) {
			if (temp / total <= UserFile.PCAthreshold) {
				temp += key;
				plist.add(key);
				principalComponentNum++;
			}
		}
		pcaN = principalComponentNum;
		/*System.out.println("\n" + "��ǰ��ֵ: " + threshold);
		System.out.println("ȡ�õ����ɷ���: " + principalComponentNum + "\n");*/

		// �����ɷ�map���������
		for (int i = 0; i < plist.size(); i++) {
			if (eigenMap.containsKey(plist.get(i))) {
				principalMap.put(i, eigenMap.get(plist.get(i)));
			}
		}

		// ��map���ֵ�浽��ά������
		double[][] principalArray = new double[principalMap.size()][];
		Iterator<Entry<Integer, double[]>> it = principalMap.entrySet()
				.iterator();
		for (int i = 0; it.hasNext(); i++) {
			principalArray[i] = it.next().getValue();
		}

		Matrix principalMatrix = new Matrix(principalArray);

		return principalMatrix;
	}
	public Matrix getResult(double[][] primary, Matrix matrix) {
		Matrix primaryMatrix = new Matrix(primary);
		Matrix result = primaryMatrix.times(matrix.transpose());
		return result;
	}
}
