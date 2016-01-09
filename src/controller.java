import java.util.ArrayList;
import java.util.Scanner;

/**
 * 
 * @author Jennifer Saalfeld, Norman Guenther
 *
 */

public class controller {
	
	static int xDomain = 0;
	static int yDomain = 0;
	static int[] xElements; //TODO: Datentyp
	static int[] yElements;
	static int ruleNumber; 
	static ArrayList<Double[]> xRules = new ArrayList<Double[]>();
	static ArrayList<Double[]> yRules = new ArrayList<Double[]>();
	static ArrayList<Double[][]> setOfEachRhoBest = new ArrayList<Double[][]>();
	static Double[][] rhoBestForAll;
	static Scanner scan = new Scanner(System.in);
	
	public static void readSets(){
		//enter finite crisp sets X and Y with an arbitrary number of elements
		
		//TODO: Name der Domain einlesen??? - im Beispiel waren Geschwindigkeit und Autoklasse 
		
		//Read domain of X and Y
		System.out.print("Enter domain of X: "); //TODO: Check int
		int domX = scan.nextInt();
		//TODO: Read elements of X to xElements
		xDomain = domX;
		System.out.print("Enter domain of Y: "); //TODO Check int
		int domY = scan.nextInt();
		yDomain = domY;
		//TODO: Read elements of Y to yElements
	}
	
	public static void read1rule(int ruleNumber){
		Double[] x_rule = new Double[xDomain];
		Double[] y_rule = new Double[yDomain];
		//Read rule pair
		System.out.println("Enter Rule " + ruleNumber + ":");
		System.out.println("Read X");
		int j = 1;
		while(j<= xDomain){
			System.out.print("Enter value for x_" + j + ": "); // passt value? //TODO: x_i durch eingelesene Elemente ersetzen
			double value_in = scan.nextDouble(); //TODO: check ob zahl
			if (value_in>=0 && value_in<=1){
				x_rule[j-1]=value_in;
				j++;
			}else{
				System.out.println("Error: Value not in [0,1]"); //TODO: Exception aufhï¿½bschen
			}
		}
		xRules.add(x_rule);
		System.out.println("Read Y");
		int k = 1;
		while(k<= yDomain){
			System.out.print("Enter value for y_" + k + ": "); // passt value? //TODO: y_i durch eingelesene Elemente ersetzen
			double value_in = scan.nextDouble(); //TODO: check ob zahl
			if (value_in>=0 && value_in<=1){
				y_rule[k-1]=value_in;
				k++;
			}else{
				System.out.println("Error: Value not in [0,1]"); //TODO: Exception aufhuebschen
			}
		}
		yRules.add(y_rule);
	}
	

	public static void calculateRhoBest(int rule) {
		Double[][] rho_best = new Double[xDomain][yDomain];
		for (int x = 0; x < xDomain; x++) {
			for (int y = 0; y < yDomain; y++) {

				// see slide 42 in chapter 6
				rho_best[x][y] = Math.min(xRules.get(rule)[x],yRules.get(rule)[y]);

			}
		}
		setOfEachRhoBest.add(rho_best);
	}

	public static void calculateRhoBestForAll() {
		Double[][] rho_best = new Double[xDomain][yDomain];
		for (int x = 0; x < xDomain; x++) {
			for (int y = 0; y < yDomain; y++) {
				rho_best[x][y] = setOfEachRhoBest.get(0)[x][y];
				for(int i=1;i<ruleNumber;i++){
					if(setOfEachRhoBest.get(i)[x][y] < rho_best[x][y]){
						rho_best[x][y] = setOfEachRhoBest.get(i)[x][y];
					}
				}
			}
		}
		rhoBestForAll = rho_best;
		
	}

	public static void main(String[] args) {
		//Task a) enter finite crisp sets X and Y with an arbitrary number of elements
		readSets();
		
		//Task b) enter corresponding fuzzy sets mu_1,...,mu_r on X and v_1,...,v_r on Y
		
		System.out.print("Number of Rules: "); //TODO: Check if int
		ruleNumber = scan.nextInt();

		//TODO: alternativloesung: "weitere regel eingeben? (J/N)"
		
		for(int i=1; i<=ruleNumber; i++){
			read1rule(i);
		}
		
		//Regeln ausgeben
		showXY();
		
		//Task c) compute the greatest solution for each mu_i°rho=v_i
		
		for(int rule=0;rule<ruleNumber; rule++){
			calculateRhoBest(rule);
		}
		
		System.out.println("\n ----------------- \n");
		System.out.println("Rho_best:");
		
		for(Double[][] rho : setOfEachRhoBest){
			for(int i=0; i<xDomain; i++){
				for(int j=0; j<yDomain; j++){
					System.out.print(rho[i][j] + "\t");
				}
				System.out.println();
			}
			System.out.println("\n\n");
		}
		

		//Task d) output the greatest solution for all mu_i°rho=v_i
		
		calculateRhoBestForAll();
		
		System.out.println("Rho_best for all is: ");
		for(int i=0; i<xDomain; i++){
			for(int j=0; j<yDomain; j++){
				System.out.print(rhoBestForAll[i][j] + "\t");
			}
			System.out.println();
		}
	}	


	public static void showXY(){
		System.out.println("\n ---------------------- \n");
		System.out.println("X");
		System.out.println("Domain of X: " + xDomain);
		for(int i = 0; i< xRules.size(); i++){
			System.out.print("Rule " + (i+1) + ": ( ");
			for(int j = 0; j< xDomain; j++){
				System.out.print(xRules.get(i)[j] + "  ");
			}
			System.out.print(")");
		}
		System.out.println();
		
		System.out.println("\nY");
		System.out.println("Domain of Y: " + yDomain);
		for(int i = 0; i< yRules.size(); i++){
			System.out.print("Rule " + (i+1) + ": ( ");
			for(int j = 0; j< yDomain; j++){
				System.out.print(yRules.get(i)[j] + "  ");
			}
			System.out.print(")");
		}
		System.out.println();
	}
}
