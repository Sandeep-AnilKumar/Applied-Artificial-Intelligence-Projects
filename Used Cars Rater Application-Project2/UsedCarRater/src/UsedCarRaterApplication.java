import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import nrc.fuzzy.FuzzyRule;
import nrc.fuzzy.FuzzyValue;
import nrc.fuzzy.FuzzyValueVector;
import nrc.fuzzy.FuzzyVariable;
import nrc.fuzzy.IncompatibleFuzzyValuesException;
import nrc.fuzzy.IncompatibleRuleInputsException;
import nrc.fuzzy.InvalidDefuzzifyException;
import nrc.fuzzy.InvalidFuzzyVariableNameException;
import nrc.fuzzy.InvalidFuzzyVariableTermNameException;
import nrc.fuzzy.InvalidLinguisticExpressionException;
import nrc.fuzzy.InvalidUODRangeException;
import nrc.fuzzy.PIFuzzySet;
import nrc.fuzzy.XValueOutsideUODException;
import nrc.fuzzy.XValuesOutOfOrderException;

public class UsedCarRaterApplication {
	private static FuzzyValue carRater = null;
	private static FuzzyValueVector fvVector = null;
	private static FuzzyVariable mileage = null;
	private static FuzzyVariable cost = null;
	private static FuzzyVariable safety = null;
	private static FuzzyVariable reputation = null;
	private static FuzzyVariable rating = null;
	private static FuzzyRule carRatingAttribute[] = new FuzzyRule[36];
	private static int numRules = 0;
	private static FuzzyValue carMileageCrisp = null;
	private static FuzzyValue carCostCrisp = null;
	private static FuzzyValue carSafetyCrisp = null;
	private static FuzzyValue brandReputationCrisp = null;
	private static double carMileage = 0d;
	private static double carCost = 0d;
	private static double carSafety = 0d;
	private static double brandReputation = 0d;

	public static void takeInputs() throws NumberFormatException, IOException, XValueOutsideUODException, XValuesOutOfOrderException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		//Inputs from the user are taken and stored in the appropriate variables. I am using double as the datatype
		//since the values are fuzzified.

		System.out.println("Please enter value for car mileage:");
		System.out.println("low = 0.0 - 4.0, medium = 4.0 - 6.0, high = 6.0 - 10.0");
		carMileage= Double.parseDouble(br.readLine());

		System.out.println("Please enter value for car cost");
		System.out.println("costly = 0.0 - 4.0, affordable = 4.0 - 6.0, cheap = 6.0 - 10.0");
		carCost= Double.parseDouble(br.readLine());

		System.out.println("Please enter value for car safety");
		System.out.println("risky = 0.0 - 5.0, safe = 5.0 - 10.0");
		carSafety= Double.parseDouble(br.readLine());

		System.out.println("Please enter value for brand reputation");
		System.out.println("insignificant = 0.0 - 5.0, reputed = 5.0 - 10.0");
		brandReputation= Double.parseDouble(br.readLine());
		br.close();
	}

	public static void buildFuzzyVariables() throws InvalidFuzzyVariableNameException, InvalidUODRangeException, XValueOutsideUODException, InvalidFuzzyVariableTermNameException, XValuesOutOfOrderException {

		//Mileage is a fuzzy variable having 3 terms: - low, medium and high. The two attributes in the PIFuzzySet(2.0, 2.0)
		//are first is the mid-point of the pi curve, second term is the curve width. So 2.0 is the centre and spans
		//2.0 on left and 2.0 on right. Similarly for each other terms.

		mileage = new FuzzyVariable("mileage", 0.0, 10.0, "On a scale 1-10 high, medium, high mileage");
		mileage.addTerm("low", new PIFuzzySet(2.0, 2.0));
		mileage.addTerm("medium", new PIFuzzySet(5.0, 2.0));
		mileage.addTerm("high", new PIFuzzySet(8.0, 2.0));

		//Cost is a fuzzy variable having three terms: - costly, affordable, and cheap.

		cost = new FuzzyVariable("cost", 0.0, 10.0, "On a Scale of 1-10 costly, affordable, cheap cost");
		cost.addTerm("costly", new PIFuzzySet(2.0, 2.0));
		cost.addTerm("affordable", new PIFuzzySet(5.0, 2.0));
		cost.addTerm("cheap", new PIFuzzySet(8.0, 2.0));

		//Safety is a fuzzy variable having 2 terms: - risky and safe.

		safety = new FuzzyVariable("safety", 0.0, 10.0, "On a Scale of 1-10 risky and safe");
		safety.addTerm("risky", new PIFuzzySet(4.0, 4.0));
		safety.addTerm("safe", new PIFuzzySet(6.0, 4.0));

		//Brand reputation is the last fuzzy variable having two terms: - insignificant and reputed.

		reputation = new FuzzyVariable("reputation", 0.0, 10.0, "On a Scale of 1-10 insignificant " +
		"and reputed");
		reputation.addTerm("insignificant", new PIFuzzySet(4.0, 4.0));
		reputation.addTerm("reputed", new PIFuzzySet(6.0, 4.0));

		//Rating is the fuzzy variable which is the conclusion, having three terms: - bad, somewhat-good, and good.

		rating = new FuzzyVariable("car rating", 0.0, 10.0, "On a Scale of 1-10 car ratings of bad, " +
		"somewhat-good, good");
		rating.addTerm("bad", new PIFuzzySet(2.0, 2.0));
		rating.addTerm("somewhat-good", new PIFuzzySet(5.0, 2.0));
		rating.addTerm("good", new PIFuzzySet(8.0, 2.0));
	}

	public static void buildRules() throws InvalidLinguisticExpressionException {
		int count = 0;

		//I have 36 rules in my system. Each rule takes in 4 antecedents and one conclusion. The antecedents
		//correspond to the fuzzy variables: - mileage, cost, safety, reputation. And conclusion corresponds to the
		//fuzzy variable rating. There are a total of 3*3*2*2 values/terms in each of my antecedent fuzzy variables
		//as such there are 36 rules.

		//Rule 1
		carRatingAttribute[count] = new FuzzyRule();
		carRatingAttribute[count].addAntecedent(new FuzzyValue(mileage,"low"));
		carRatingAttribute[count].addAntecedent(new FuzzyValue(cost,"costly"));
		carRatingAttribute[count].addAntecedent(new FuzzyValue(safety,"risky"));
		carRatingAttribute[count].addAntecedent(new FuzzyValue(reputation,"insignificant"));
		carRatingAttribute[count++].addConclusion(new FuzzyValue(rating,"bad"));

		//Rule 2
		carRatingAttribute[count] = new FuzzyRule();
		carRatingAttribute[count].addAntecedent(new FuzzyValue(mileage,"low"));
		carRatingAttribute[count].addAntecedent(new FuzzyValue(cost,"costly"));
		carRatingAttribute[count].addAntecedent(new FuzzyValue(safety,"risky"));
		carRatingAttribute[count].addAntecedent(new FuzzyValue(reputation,"reputed"));
		carRatingAttribute[count++].addConclusion(new FuzzyValue(rating,"bad"));

		//Rule 3
		carRatingAttribute[count] = new FuzzyRule();
		carRatingAttribute[count].addAntecedent(new FuzzyValue(mileage,"low"));
		carRatingAttribute[count].addAntecedent(new FuzzyValue(cost,"costly"));
		carRatingAttribute[count].addAntecedent(new FuzzyValue(safety,"safe"));
		carRatingAttribute[count].addAntecedent(new FuzzyValue(reputation,"insignificant"));
		carRatingAttribute[count++].addConclusion(new FuzzyValue(rating,"bad"));

		//Rule 4
		carRatingAttribute[count] = new FuzzyRule();
		carRatingAttribute[count].addAntecedent(new FuzzyValue(mileage,"low"));
		carRatingAttribute[count].addAntecedent(new FuzzyValue(cost,"costly"));
		carRatingAttribute[count].addAntecedent(new FuzzyValue(safety,"safe"));
		carRatingAttribute[count].addAntecedent(new FuzzyValue(reputation,"reputed"));
		carRatingAttribute[count++].addConclusion(new FuzzyValue(rating,"bad"));

		//Rule 5
		carRatingAttribute[count] = new FuzzyRule();
		carRatingAttribute[count].addAntecedent(new FuzzyValue(mileage,"low"));
		carRatingAttribute[count].addAntecedent(new FuzzyValue(cost,"affordable"));
		carRatingAttribute[count].addAntecedent(new FuzzyValue(safety,"risky"));
		carRatingAttribute[count].addAntecedent(new FuzzyValue(reputation,"insignificant"));
		carRatingAttribute[count++].addConclusion(new FuzzyValue(rating,"bad"));

		//Rule 6
		carRatingAttribute[count] = new FuzzyRule();
		carRatingAttribute[count].addAntecedent(new FuzzyValue(mileage,"low"));
		carRatingAttribute[count].addAntecedent(new FuzzyValue(cost,"affordable"));
		carRatingAttribute[count].addAntecedent(new FuzzyValue(safety,"risky"));
		carRatingAttribute[count].addAntecedent(new FuzzyValue(reputation,"reputed"));
		carRatingAttribute[count++].addConclusion(new FuzzyValue(rating,"somewhat-good"));

		//Rule 7
		carRatingAttribute[count] = new FuzzyRule();
		carRatingAttribute[count].addAntecedent(new FuzzyValue(mileage,"low"));
		carRatingAttribute[count].addAntecedent(new FuzzyValue(cost,"affordable"));
		carRatingAttribute[count].addAntecedent(new FuzzyValue(safety,"safe"));
		carRatingAttribute[count].addAntecedent(new FuzzyValue(reputation,"insignificant"));
		carRatingAttribute[count++].addConclusion(new FuzzyValue(rating,"bad"));

		//Rule 8
		carRatingAttribute[count] = new FuzzyRule();
		carRatingAttribute[count].addAntecedent(new FuzzyValue(mileage,"low"));
		carRatingAttribute[count].addAntecedent(new FuzzyValue(cost,"affordable"));
		carRatingAttribute[count].addAntecedent(new FuzzyValue(safety,"safe"));
		carRatingAttribute[count].addAntecedent(new FuzzyValue(reputation,"reputed"));
		carRatingAttribute[count++].addConclusion(new FuzzyValue(rating,"somewhat-good"));

		//Rule 9
		carRatingAttribute[count] = new FuzzyRule();
		carRatingAttribute[count].addAntecedent(new FuzzyValue(mileage,"low"));
		carRatingAttribute[count].addAntecedent(new FuzzyValue(cost,"cheap"));
		carRatingAttribute[count].addAntecedent(new FuzzyValue(safety,"risky"));
		carRatingAttribute[count].addAntecedent(new FuzzyValue(reputation,"insignificant"));
		carRatingAttribute[count++].addConclusion(new FuzzyValue(rating,"bad"));

		//Rule 10
		carRatingAttribute[count] = new FuzzyRule();
		carRatingAttribute[count].addAntecedent(new FuzzyValue(mileage,"low"));
		carRatingAttribute[count].addAntecedent(new FuzzyValue(cost,"cheap"));
		carRatingAttribute[count].addAntecedent(new FuzzyValue(safety,"risky"));
		carRatingAttribute[count].addAntecedent(new FuzzyValue(reputation,"reputed"));
		carRatingAttribute[count++].addConclusion(new FuzzyValue(rating,"somewhat-good"));

		//Rule 11
		carRatingAttribute[count] = new FuzzyRule();
		carRatingAttribute[count].addAntecedent(new FuzzyValue(mileage,"low"));
		carRatingAttribute[count].addAntecedent(new FuzzyValue(cost,"cheap"));
		carRatingAttribute[count].addAntecedent(new FuzzyValue(safety,"safe"));
		carRatingAttribute[count].addAntecedent(new FuzzyValue(reputation,"insignificant"));
		carRatingAttribute[count++].addConclusion(new FuzzyValue(rating,"bad"));

		//Rule 12
		carRatingAttribute[count] = new FuzzyRule();
		carRatingAttribute[count].addAntecedent(new FuzzyValue(mileage,"low"));
		carRatingAttribute[count].addAntecedent(new FuzzyValue(cost,"cheap"));
		carRatingAttribute[count].addAntecedent(new FuzzyValue(safety,"safe"));
		carRatingAttribute[count].addAntecedent(new FuzzyValue(reputation,"reputed"));
		carRatingAttribute[count++].addConclusion(new FuzzyValue(rating,"somewhat-good"));

		//Rule 13
		carRatingAttribute[count] = new FuzzyRule();
		carRatingAttribute[count].addAntecedent(new FuzzyValue(mileage,"medium"));
		carRatingAttribute[count].addAntecedent(new FuzzyValue(cost,"costly"));
		carRatingAttribute[count].addAntecedent(new FuzzyValue(safety,"risky"));
		carRatingAttribute[count].addAntecedent(new FuzzyValue(reputation,"insignificant"));
		carRatingAttribute[count++].addConclusion(new FuzzyValue(rating,"bad"));

		//Rule 14
		carRatingAttribute[count] = new FuzzyRule();
		carRatingAttribute[count].addAntecedent(new FuzzyValue(mileage,"medium"));
		carRatingAttribute[count].addAntecedent(new FuzzyValue(cost,"costly"));
		carRatingAttribute[count].addAntecedent(new FuzzyValue(safety,"risky"));
		carRatingAttribute[count].addAntecedent(new FuzzyValue(reputation,"reputed"));
		carRatingAttribute[count++].addConclusion(new FuzzyValue(rating,"somewhat-good"));

		//Rule 15
		carRatingAttribute[count] = new FuzzyRule();
		carRatingAttribute[count].addAntecedent(new FuzzyValue(mileage,"medium"));
		carRatingAttribute[count].addAntecedent(new FuzzyValue(cost,"costly"));
		carRatingAttribute[count].addAntecedent(new FuzzyValue(safety,"safe"));
		carRatingAttribute[count].addAntecedent(new FuzzyValue(reputation,"insignificant"));
		carRatingAttribute[count++].addConclusion(new FuzzyValue(rating,"somewhat-good"));

		//Rule 16
		carRatingAttribute[count] = new FuzzyRule();
		carRatingAttribute[count].addAntecedent(new FuzzyValue(mileage,"medium"));
		carRatingAttribute[count].addAntecedent(new FuzzyValue(cost,"costly"));
		carRatingAttribute[count].addAntecedent(new FuzzyValue(safety,"safe"));
		carRatingAttribute[count].addAntecedent(new FuzzyValue(reputation,"reputed"));
		carRatingAttribute[count++].addConclusion(new FuzzyValue(rating,"somewhat-good"));

		//Rule 17
		carRatingAttribute[count] = new FuzzyRule();
		carRatingAttribute[count].addAntecedent(new FuzzyValue(mileage,"medium"));
		carRatingAttribute[count].addAntecedent(new FuzzyValue(cost,"affordable"));
		carRatingAttribute[count].addAntecedent(new FuzzyValue(safety,"risky"));
		carRatingAttribute[count].addAntecedent(new FuzzyValue(reputation,"insignificant"));
		carRatingAttribute[count++].addConclusion(new FuzzyValue(rating,"somewhat-good"));

		//Rule 18
		carRatingAttribute[count] = new FuzzyRule();
		carRatingAttribute[count].addAntecedent(new FuzzyValue(mileage,"medium"));
		carRatingAttribute[count].addAntecedent(new FuzzyValue(cost,"affordable"));
		carRatingAttribute[count].addAntecedent(new FuzzyValue(safety,"risky"));
		carRatingAttribute[count].addAntecedent(new FuzzyValue(reputation,"reputed"));
		carRatingAttribute[count++].addConclusion(new FuzzyValue(rating,"somewhat-good"));

		//Rule 19
		carRatingAttribute[count] = new FuzzyRule();
		carRatingAttribute[count].addAntecedent(new FuzzyValue(mileage,"medium"));
		carRatingAttribute[count].addAntecedent(new FuzzyValue(cost,"affordable"));
		carRatingAttribute[count].addAntecedent(new FuzzyValue(safety,"safe"));
		carRatingAttribute[count].addAntecedent(new FuzzyValue(reputation,"insignificant"));
		carRatingAttribute[count++].addConclusion(new FuzzyValue(rating,"somewhat-good"));

		//Rule 20
		carRatingAttribute[count] = new FuzzyRule();
		carRatingAttribute[count].addAntecedent(new FuzzyValue(mileage,"medium"));
		carRatingAttribute[count].addAntecedent(new FuzzyValue(cost,"affordable"));
		carRatingAttribute[count].addAntecedent(new FuzzyValue(safety,"safe"));
		carRatingAttribute[count].addAntecedent(new FuzzyValue(reputation,"reputed"));
		carRatingAttribute[count++].addConclusion(new FuzzyValue(rating,"good"));

		//Rule 21
		carRatingAttribute[count] = new FuzzyRule();
		carRatingAttribute[count].addAntecedent(new FuzzyValue(mileage,"medium"));
		carRatingAttribute[count].addAntecedent(new FuzzyValue(cost,"cheap"));
		carRatingAttribute[count].addAntecedent(new FuzzyValue(safety,"risky"));
		carRatingAttribute[count].addAntecedent(new FuzzyValue(reputation,"insignificant"));
		carRatingAttribute[count++].addConclusion(new FuzzyValue(rating,"somewhat-good"));

		//Rule 22
		carRatingAttribute[count] = new FuzzyRule();
		carRatingAttribute[count].addAntecedent(new FuzzyValue(mileage,"medium"));
		carRatingAttribute[count].addAntecedent(new FuzzyValue(cost,"cheap"));
		carRatingAttribute[count].addAntecedent(new FuzzyValue(safety,"risky"));
		carRatingAttribute[count].addAntecedent(new FuzzyValue(reputation,"reputed"));
		carRatingAttribute[count++].addConclusion(new FuzzyValue(rating,"somewhat-good"));

		//Rule 23
		carRatingAttribute[count] = new FuzzyRule();
		carRatingAttribute[count].addAntecedent(new FuzzyValue(mileage,"medium"));
		carRatingAttribute[count].addAntecedent(new FuzzyValue(cost,"cheap"));
		carRatingAttribute[count].addAntecedent(new FuzzyValue(safety,"safe"));
		carRatingAttribute[count].addAntecedent(new FuzzyValue(reputation,"insignificant"));
		carRatingAttribute[count++].addConclusion(new FuzzyValue(rating,"somewhat-good"));

		//Rule 24
		carRatingAttribute[count] = new FuzzyRule();
		carRatingAttribute[count].addAntecedent(new FuzzyValue(mileage,"medium"));
		carRatingAttribute[count].addAntecedent(new FuzzyValue(cost,"cheap"));
		carRatingAttribute[count].addAntecedent(new FuzzyValue(safety,"safe"));
		carRatingAttribute[count].addAntecedent(new FuzzyValue(reputation,"reputed"));
		carRatingAttribute[count++].addConclusion(new FuzzyValue(rating,"good"));

		//Rule 25
		carRatingAttribute[count] = new FuzzyRule();
		carRatingAttribute[count].addAntecedent(new FuzzyValue(mileage,"high"));
		carRatingAttribute[count].addAntecedent(new FuzzyValue(cost,"costly"));
		carRatingAttribute[count].addAntecedent(new FuzzyValue(safety,"risky"));
		carRatingAttribute[count].addAntecedent(new FuzzyValue(reputation,"insignificant"));
		carRatingAttribute[count++].addConclusion(new FuzzyValue(rating,"bad"));

		//Rule 26
		carRatingAttribute[count] = new FuzzyRule();
		carRatingAttribute[count].addAntecedent(new FuzzyValue(mileage,"high"));
		carRatingAttribute[count].addAntecedent(new FuzzyValue(cost,"costly"));
		carRatingAttribute[count].addAntecedent(new FuzzyValue(safety,"risky"));
		carRatingAttribute[count].addAntecedent(new FuzzyValue(reputation,"reputed"));
		carRatingAttribute[count++].addConclusion(new FuzzyValue(rating,"somewhat-good"));

		//Rule 27
		carRatingAttribute[count] = new FuzzyRule();
		carRatingAttribute[count].addAntecedent(new FuzzyValue(mileage,"high"));
		carRatingAttribute[count].addAntecedent(new FuzzyValue(cost,"costly"));
		carRatingAttribute[count].addAntecedent(new FuzzyValue(safety,"safe"));
		carRatingAttribute[count].addAntecedent(new FuzzyValue(reputation,"insignificant"));
		carRatingAttribute[count++].addConclusion(new FuzzyValue(rating,"somewhat-good"));

		//Rule 28
		carRatingAttribute[count] = new FuzzyRule();
		carRatingAttribute[count].addAntecedent(new FuzzyValue(mileage,"high"));
		carRatingAttribute[count].addAntecedent(new FuzzyValue(cost,"costly"));
		carRatingAttribute[count].addAntecedent(new FuzzyValue(safety,"safe"));
		carRatingAttribute[count].addAntecedent(new FuzzyValue(reputation,"reputed"));
		carRatingAttribute[count++].addConclusion(new FuzzyValue(rating,"somewhat-good"));

		//Rule 29
		carRatingAttribute[count] = new FuzzyRule();
		carRatingAttribute[count].addAntecedent(new FuzzyValue(mileage,"high"));
		carRatingAttribute[count].addAntecedent(new FuzzyValue(cost,"affordable"));
		carRatingAttribute[count].addAntecedent(new FuzzyValue(safety,"risky"));
		carRatingAttribute[count].addAntecedent(new FuzzyValue(reputation,"insignificant"));
		carRatingAttribute[count++].addConclusion(new FuzzyValue(rating,"somewhat-good"));

		//Rule 30
		carRatingAttribute[count] = new FuzzyRule();
		carRatingAttribute[count].addAntecedent(new FuzzyValue(mileage,"high"));
		carRatingAttribute[count].addAntecedent(new FuzzyValue(cost,"affordable"));
		carRatingAttribute[count].addAntecedent(new FuzzyValue(safety,"risky"));
		carRatingAttribute[count].addAntecedent(new FuzzyValue(reputation,"reputed"));
		carRatingAttribute[count++].addConclusion(new FuzzyValue(rating,"somewhat-good"));

		//Rule 31
		carRatingAttribute[count] = new FuzzyRule();
		carRatingAttribute[count].addAntecedent(new FuzzyValue(mileage,"high"));
		carRatingAttribute[count].addAntecedent(new FuzzyValue(cost,"affordable"));
		carRatingAttribute[count].addAntecedent(new FuzzyValue(safety,"safe"));
		carRatingAttribute[count].addAntecedent(new FuzzyValue(reputation,"insignificant"));
		carRatingAttribute[count++].addConclusion(new FuzzyValue(rating,"somewhat-good"));

		//Rule 32
		carRatingAttribute[count] = new FuzzyRule();
		carRatingAttribute[count].addAntecedent(new FuzzyValue(mileage,"high"));
		carRatingAttribute[count].addAntecedent(new FuzzyValue(cost,"affordable"));
		carRatingAttribute[count].addAntecedent(new FuzzyValue(safety,"safe"));
		carRatingAttribute[count].addAntecedent(new FuzzyValue(reputation,"reputed"));
		carRatingAttribute[count++].addConclusion(new FuzzyValue(rating,"good"));

		//Rule 33
		carRatingAttribute[count] = new FuzzyRule();
		carRatingAttribute[count].addAntecedent(new FuzzyValue(mileage,"high"));
		carRatingAttribute[count].addAntecedent(new FuzzyValue(cost,"cheap"));
		carRatingAttribute[count].addAntecedent(new FuzzyValue(safety,"risky"));
		carRatingAttribute[count].addAntecedent(new FuzzyValue(reputation,"insignificant"));
		carRatingAttribute[count++].addConclusion(new FuzzyValue(rating,"somewhat-good"));

		//Rule 34
		carRatingAttribute[count] = new FuzzyRule();
		carRatingAttribute[count].addAntecedent(new FuzzyValue(mileage,"high"));
		carRatingAttribute[count].addAntecedent(new FuzzyValue(cost,"cheap"));
		carRatingAttribute[count].addAntecedent(new FuzzyValue(safety,"risky"));
		carRatingAttribute[count].addAntecedent(new FuzzyValue(reputation,"reputed"));
		carRatingAttribute[count++].addConclusion(new FuzzyValue(rating,"somewhat-good"));

		//Rule 35
		carRatingAttribute[count] = new FuzzyRule();
		carRatingAttribute[count].addAntecedent(new FuzzyValue(mileage,"high"));
		carRatingAttribute[count].addAntecedent(new FuzzyValue(cost,"cheap"));
		carRatingAttribute[count].addAntecedent(new FuzzyValue(safety,"safe"));
		carRatingAttribute[count].addAntecedent(new FuzzyValue(reputation,"insignificant"));
		carRatingAttribute[count++].addConclusion(new FuzzyValue(rating,"somewhat-good"));

		//Rule 36
		carRatingAttribute[count] = new FuzzyRule();
		carRatingAttribute[count].addAntecedent(new FuzzyValue(mileage,"high"));
		carRatingAttribute[count].addAntecedent(new FuzzyValue(cost,"cheap"));
		carRatingAttribute[count].addAntecedent(new FuzzyValue(safety,"safe"));
		carRatingAttribute[count].addAntecedent(new FuzzyValue(reputation,"reputed"));
		carRatingAttribute[count++].addConclusion(new FuzzyValue(rating,"good"));

		//numRules is the total number of rules.
		numRules = count;
	}

	public static void execute() throws XValueOutsideUODException, IncompatibleFuzzyValuesException, InvalidDefuzzifyException, XValuesOutOfOrderException, IncompatibleRuleInputsException {

		//The fuzzy values passed in by the user of the application are converted into corresponding crisp values.
		//So if the user has entered a value between 0 and 10 then the same value is used. But if the user has entered
		//0 then the value is incremented by 0.5 and used. Similarly if the user has entered 10, then the value is
		//decremented by 0.5 and used. Again I am building a PIFuzzySet for each of the crisp variables, and curve width 
		//as 0.5

		carMileageCrisp =  new FuzzyValue(mileage, new PIFuzzySet((carMileage - 0.5) >= 0.0 ? 
				((carMileage + 0.5) <= 10.0 ? carMileage : carMileage - 0.5) : carMileage + 0.5, 0.5));

		carCostCrisp =  new FuzzyValue(cost, new PIFuzzySet((carCost - 0.5) >= 0.0 ? 
				((carCost + 0.5) <= 10.0 ? carCost : carCost - 0.5) : carCost + 0.5, 0.5));

		carSafetyCrisp =  new FuzzyValue(safety, new PIFuzzySet((carSafety - 0.5) >= 0.0 ? 
				((carSafety + 0.5) <= 10.0 ? carSafety : carSafety - 0.5) : carSafety + 0.5, 0.5));

		brandReputationCrisp =  new FuzzyValue(reputation, new PIFuzzySet((brandReputation - 0.5) >= 0.0 ? 
				((brandReputation + 0.5) <= 10.0 ? brandReputation : brandReputation - 0.5) : brandReputation + 0.5, 0.5));

		for(int rule=0;rule<numRules;rule++)
			carRatingAttribute[rule].removeAllInputs();

		int currRule = 0;

		//Each of the rule in the system is checked for a match. If it is a match then the union is taken.

		for(int rule = 0; rule < numRules; ++rule) {
			carRatingAttribute[rule].addInput(carMileageCrisp);
			carRatingAttribute[rule].addInput(carCostCrisp);
			carRatingAttribute[rule].addInput(carSafetyCrisp);
			carRatingAttribute[rule].addInput(brandReputationCrisp);
			currRule = rule;
			if (carRatingAttribute[rule].testRuleMatching()) {
				fvVector = carRatingAttribute[rule].execute();
				//At first the carRater is null, so it is given a value at 0.
				if (carRater == null)
					carRater = fvVector.fuzzyValueAt(0);

				//When the carRater is not null, a union of present rule vector is created and stored in carRater.
				else
					carRater = carRater.fuzzyUnion(fvVector.fuzzyValueAt(0));
			}
		}

		//The carRater value is again defuzzified, and this carRating is used to gauge the condition of the car.

		double carRating = carRater.momentDefuzzify();

		//I am rounding of the carRating.

		carRating = Math.round(carRating);
		System.out.println("The car rating on a scale of 10 is : " +
				carRating+ "");

		System.out.print("\n\nMESSAGE: - ");
		if(carRating >= 0.0 && carRating <= 4.0) {
			System.out.println("The car is in bad condition, do not waste hard earned money!");
		} else  if(carRating > 4.0 && carRating < 8.0) {
			System.out.println("The car is in somewhat-good condition, can buy!");
		} else  if(carRating >= 8.0 && carRating <= 10.0) {
			System.out.println("The car is in very good condition, throw the money and run!");
		}

		System.out.println("\n\nOverall Car Assessment: -\n" + carRatingAttribute[currRule].getConclusions());
		System.out.println(carRater.plotFuzzyValue("*"));
	}

	public static void main(String args[]) {
		try {
			//Inputs is taken form the user.
			takeInputs();

			//Fuzzy variables are built.
			buildFuzzyVariables();

			//Rules for the domain are built.
			buildRules();

			//Lastly the rules are executed for the values provided to test for a match.
			execute();
		} 
		catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();	
		} catch (InvalidFuzzyVariableNameException e) {
			e.printStackTrace();
		} catch (InvalidUODRangeException e) {
			e.printStackTrace();
		} catch (XValueOutsideUODException e) {
			e.printStackTrace();
		} catch (InvalidFuzzyVariableTermNameException e) {
			e.printStackTrace();
		} catch (XValuesOutOfOrderException e) {
			e.printStackTrace();
		} catch (InvalidLinguisticExpressionException e) {
			e.printStackTrace();
		} catch (IncompatibleFuzzyValuesException e) {
			e.printStackTrace();
		} catch (InvalidDefuzzifyException e) {
			e.printStackTrace();
		} catch (IncompatibleRuleInputsException e) {
			e.printStackTrace();
		}
	}
}