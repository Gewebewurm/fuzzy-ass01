import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * 
 * @author Jennifer Saalfeld, Norman Guenther
 *
 */

public class alpha_cuts {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		boolean abort = false;
		double double_in = 0;
		String string_in = "";
		
		//Task a) Read relevant degrees of membership
		ArrayList<Double> levels = new ArrayList<Double>();
		
		System.out.println("Enter finite subset L in [0,1] of relevant degrees of membership.");
		System.out.println("Abort input with -1");
		System.out.print("Read degree of membership: ");
		
		do{
			double_in = scan.nextDouble();
			if (double_in == -1){
				abort = true;
			}else{
				if (double_in <0 || double_in >1){
					System.out.println(double_in + " was rejected, because it is not in [0,1]");
					System.out.print("Read next degree of membership: ");
				}else{
					levels.add(double_in);
					System.out.print("Read next degree of membership: ");
				}
			}
				
		} while (!abort);
		System.out.println("Stop reading relevant degrees of membership");
		
		Collections.sort(levels, Collections.reverseOrder()); //Sort list of levels descending
		
		//Task b) specify alpha_cuts corresponding to levels
		// data structure as given in the lecture
		ArrayList<ArrayList<Double[]>> alpha_cuts = new ArrayList<ArrayList<Double[]>>();
		System.out.println("Now please specify the alpha_cuts for each member.");
		System.out.println("Enter each range as \"[a;b]\". Enter \"n\" to proceed with the next member.");
		
		abort = false;
		int member = 0;
		ArrayList<Double[]> alpha_cutsPerLevel = new ArrayList<Double[]>();
		
		System.out.println("Read range for " + levels.get(member) + " :");
		do{
			string_in = scan.next();
			if (string_in.equalsIgnoreCase("n")){
				alpha_cuts.add(alpha_cutsPerLevel); //add all associated ranges to the alpha_cut
				alpha_cutsPerLevel.clear(); //clear list of ranges
				member++; //next member
			}else{
				String[] parts = string_in.split(";");
				if (parts.length == 2){
					String lowerString = parts[0];
					lowerString = lowerString.substring(1);
					double lower = Double.parseDouble(lowerString);
					String upperString = parts[1];
					upperString = upperString.substring(0, upperString.length()-1);
					double upper = Double.parseDouble(upperString);
					Double[] range = {lower, upper};
					alpha_cutsPerLevel.add(range);
				}
				
			}
			if (member < levels.size()){
				System.out.println("Read range for " + levels.get(member) + " :");
			} else {
				abort = true;
			}
		}while (!abort);
		
		//Task c)
		double x = 0;
		System.out.print("Please enter x for determining the membership degree of x: ");
		x = scan.nextDouble();
		double mueOfX = mue(x, levels, alpha_cuts);
		System.out.println(x + " has a membership degree of " + mueOfX);
		
		scan.close();
	}

	private static double mue(double x, ArrayList<Double> levels,
			ArrayList<ArrayList<Double[]>> alpha_cuts) {
		for (ArrayList<Double[]> alpha_cutPerLevel : alpha_cuts){
			for (Double[] range : alpha_cutPerLevel){
				if (x >= range[0] && x <= range[1]){
					//found highest alpha_cut for x
					int index = alpha_cuts.indexOf(alpha_cutPerLevel);
					return levels.get(index);
				}
			}
		}
		return 0;
	}

}
