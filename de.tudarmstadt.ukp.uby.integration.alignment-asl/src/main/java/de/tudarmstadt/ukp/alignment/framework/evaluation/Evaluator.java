/*******************************************************************************
 * Copyright 2016
 * Ubiquitous Knowledge Processing (UKP) Lab
 * Technische Universität Darmstadt
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package de.tudarmstadt.ukp.alignment.framework.evaluation;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;


public class Evaluator
{


	public static void main(String[] args)
	{

		performEvaluation("target/wn_wp_alignment_babelnet", "target/wn_wp_goldstandard_extref", false);

	}

	/**
	 * Here  we can perform the evaluation if we have the alignment and goldstandards are available
	 */
	public static void performEvaluation(String alignment, String goldstandard, boolean pos)
	{

		try {
			HashSet<String> current =new HashSet<String>();
			HashSet<String> gold_pos =new HashSet<String>();
			HashSet<String> gold_neg =new HashSet<String>();

			System.out.println("   * Configuration: ");

			FileReader in = new FileReader(alignment);
			FileReader in2 = new FileReader(goldstandard);


			 BufferedReader input =  new BufferedReader(in);
			 String line;
			 while((line =input.readLine())!=null)
			 {
				 if(line.startsWith("f")) {
					continue;
				}

				 String[] info = line.split("\t");
				 current.add(info[0]+"\t"+info[1]);
			  }
			 input.close();
             in.close();

			 input =  new BufferedReader(in2);
			 while((line =input.readLine())!=null) {
				 String[] info = line.split("\t");
				 if(info[2].equals("1")) {
					gold_pos.add(info[0]+"\t"+info[1]);
				}
				else {
					gold_neg.add(info[0]+"\t"+info[1]);
				}

			 }
			 input.close();
			 in2.close();
			 /*TODO* for later use*/
			 String[] poses = {"noun","adjective","adverb","verb"};
			 if(!pos)
			 {
				 poses = new String[1];
				 poses[0]="";
			 }

			 for(String pos_string : poses)
			 {
			 double tp_1 = 0.0;
			 double fn_1 = 0.0;
			 double fp_1 = 0.0;
			 double tp_0 = 0.0;
			 double fn_0 = 0.0;
			 double fp_0 = 0.0;
			 double tn_0 = 0.0;
			 for(String gp : gold_pos)
			 {

				 if(gp.contains(pos_string)) {
					if(current.contains(gp))
					 {
						tp_1++;
					}
					else
						{
						fn_1++;
						fp_0++;
					}
				}
			 }

			 for(String gn : gold_neg)
			 {
				 if(gn.contains(pos_string)) {
					if(current.contains(gn) )
					 {
						fp_1++;
						fn_0++;
					}
					else {
						tp_0++;
						}
				}
			 }

			 double precision_1 = tp_1 / (tp_1+fp_1);
			 double recall_1 = tp_1 / (tp_1+fn_1);
			 double precision_0 = tp_0 / (tp_0+fp_0);
			 double recall_0 = tp_0/ (tp_0+fn_0);
			 double accuracy = (tp_1+tp_0) / (tp_1+tp_0+fn_0+fn_1);
			 double f_1_1 = (precision_1 * recall_1 *2) / (precision_1 + recall_1);
			 double f_1_0 = (precision_0 * recall_0 *2) / (precision_0 + recall_0);
			 double overall_size=  gold_neg.size()+gold_pos.size();
			 double weight_1 = gold_pos.size() / overall_size;
			 double weight_0 = gold_neg.size() / overall_size;
			 System.out.println("Class 1 Size: "+gold_pos.size()+" Weight: "+weight_1);
			 System.out.println("Class 0 Size: "+gold_neg.size()+" Weight: "+weight_0);
			 System.out.println("Overall Size: "+overall_size);
			 System.out.println("TP_1: "+tp_1);
			 System.out.println("FP_1: "+fp_1);
			 System.out.println("FN_1: "+fn_1);
			 System.out.println("TN_1: "+tp_0);
			 System.out.println("FP_0: "+fp_0);
			 System.out.println("FN_0: "+fn_0);
			 System.out.println("TP_0: "+tp_0);
			 System.out.println("TN_0: "+tn_0);
			 System.out.print("   * Results "+pos_string+ " *Precision Class 1: "+precision_1);
			 System.out.print(" Recall Class 1: "+recall_1);
			 System.out.println(" F1-Measure: "+f_1_1+"*");
			 System.out.print("   * Results "+pos_string+ " *Precision Class 0: "+precision_0);
			 System.out.print(" Recall Class 0: "+recall_0);
			 System.out.println(" F1-Measure: "+f_1_0+"*");
			 System.out.print("   * Results "+pos_string+ " *Precision Avg. "+(weight_0 *precision_0+weight_1 *precision_1));
			 System.out.print(" Recall Avg.: "+(weight_0 *recall_0+weight_1 *recall_1));
			 System.out.println(" F1-Measure Avg: "+(weight_0 *f_1_0+weight_1 *f_1_1)+"*");
			 System.out.println("   * Accuracy: "+accuracy+"*");
			 System.out.println();

		}
		}
		catch (FileNotFoundException e) {

			 System.out.println("File not found");
		}
		catch (IOException e) {

			e.printStackTrace();
		}

	}

}
