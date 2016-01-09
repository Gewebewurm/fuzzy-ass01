import java.util.ArrayList;
import java.util.Scanner;

/**
 * 
 * @author Jennifer Saalfeld, Norman Guenther
 *
 */

public class controller {
	
	static int x_domain = 0;
	static int y_domain = 0;
	static int[] x_elements; //TODO: Datentyp
	static int[] y_elements;
	static int rules_number;
	static ArrayList<Double[]> x_rules = new ArrayList<Double[]>();
	static ArrayList<Double[]> y_rules = new ArrayList<Double[]>();
	static ArrayList<Double[][]> set_of_each_rho_best = new ArrayList<Double[][]>();
	static Double[][] rho_best_for_all;
	static Scanner scan = new Scanner(System.in);
	
	public static void read_sets(){
		//enter finite crisp sets X and Y with an arbitrary number of elements
		
		//TODO: Name der Domain einlesen??? - im Beispiel waren Geschwindigkeit und Autoklasse 
		
		//Read domain of X and Y
		System.out.print("Enter domain of X: "); //TODO: Check int
		int dom_x = scan.nextInt();
		//TODO: Read elements of X to x_elements
		x_domain = dom_x;
		System.out.print("Enter domain of Y: "); //TODO Check int
		int dom_y = scan.nextInt();
		y_domain = dom_y;
		//TODO: Read elements of Y to y_elements
	}
	
	public static void read1rule(int ruleNumber){
		Double[] x_rule = new Double[x_domain];
		Double[] y_rule = new Double[y_domain];
		//Read rule pair
		System.out.println("Enter Rule " + ruleNumber + ":");
		System.out.println("Read X");
		int j = 1;
		while(j<=x_domain){
			System.out.print("Enter value for x_" + j + ": "); // passt value? //TODO: x_i durch eingelesene Elemente ersetzen
			double value_in = scan.nextDouble(); //TODO: check ob zahl
			if (value_in>=0 && value_in<=1){
				x_rule[j-1]=value_in;
				j++;
			}else{
				System.out.println("Error: Value not in [0,1]"); //TODO: Exception aufhübschen
			}
		}
		x_rules.add(x_rule);
		System.out.println("Read Y");
		int k = 1;
		while(k<=y_domain){
			System.out.print("Enter value for y_" + k + ": "); // passt value? //TODO: y_i durch eingelesene Elemente ersetzen
			double value_in = scan.nextDouble(); //TODO: check ob zahl
			if (value_in>=0 && value_in<=1){
				y_rule[k-1]=value_in;
				k++;
			}else{
				System.out.println("Error: Value not in [0,1]"); //TODO: Exception aufhuebschen
			}
		}
		y_rules.add(y_rule);
	}
	
	public static void calculateRhoBest(int rule) {
		Double[][] rho_best = new Double[x_domain][y_domain];
		for (int x = 0; x < x_domain; x++) {
			for (int y = 0; y < y_domain; y++) {

				// see slide 42 in chapter 6
				rho_best[x][y] = Math.min(x_rules.get(rule)[x],y_rules.get(rule)[y]);

			}
		}
		set_of_each_rho_best.add(rho_best);
	}
	
	public static void calculateRhoBestForAll() {
		Double[][] rho_best = new Double[x_domain][y_domain];
		for (int x = 0; x < x_domain; x++) {
			for (int y = 0; y < y_domain; y++) {
				rho_best[x][y] = set_of_each_rho_best.get(0)[x][y];
				for(int i=1;i<rules_number;i++){
					if(set_of_each_rho_best.get(i)[x][y] < rho_best[x][y]){
						rho_best[x][y] = set_of_each_rho_best.get(i)[x][y];
					}
				}
			}
		}
		rho_best_for_all = rho_best;
		
	}

	public static void main(String[] args) {
		//Task a) enter finite crisp sets X and Y with an arbitrary number of elements
		
		read_sets();
		
		//Task b) enter corresponding fuzzy sets mu_1,...,mu_r on X and v_1,...,v_r on Y
		
		System.out.print("Number of Rules: "); //TODO: Check if int
		rules_number = scan.nextInt();
		//TODO: alternativloesung: "weitere regel eingeben? (J/N)"
		
		for(int i=1; i<=rules_number; i++){
			read1rule(i);
		}
		
		//Regeln ausgeben
		showXY();
		
		//Task c) compute the greatest solution for each mu_i°rho=v_i
		
		for(int rule=0;rule<rules_number; rule++){
			calculateRhoBest(rule);
		}
		
		
		System.out.println("\n ----------------- \n");
		System.out.println("Rho_best:");
		
		for(Double[][] rho : set_of_each_rho_best){
			for(int i=0; i<x_domain; i++){
				for(int j=0; j<y_domain; j++){
					System.out.print(rho[i][j] + "\t");
				}
				System.out.println();
			}
			System.out.println("\n\n");
		}
		
		//Task d) output the greatest solution for all mu_i°rho=v_i
		
		calculateRhoBestForAll();
		
		System.out.println("Rho_best for all is: ");
		for(int i=0; i<x_domain; i++){
			for(int j=0; j<y_domain; j++){
				System.out.print(rho_best_for_all[i][j] + "\t");
			}
			System.out.println();
		}
	}	

	public static void showXY(){
		System.out.println("\n ---------------------- \n");
		System.out.println("X");
		System.out.println("Domain of X: " + x_domain);
		for(int i = 0;i<x_rules.size();i++){
			System.out.print("Rule " + (i+1) + ": ( ");
			for(int j=0;j<x_domain;j++){
				System.out.print(x_rules.get(i)[j] + "  ");
			}
			System.out.print(")");
		}
		System.out.println();
		
		System.out.println("\nY");
		System.out.println("Domain of Y: " + y_domain);
		for(int i = 0;i<y_rules.size();i++){
			System.out.print("Rule " + (i+1) + ": ( ");
			for(int j=0;j<y_domain;j++){
				System.out.print(y_rules.get(i)[j] + "  ");
			}
			System.out.print(")");
		}
		System.out.println();
	}

}
