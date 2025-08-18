
import java.util.Random;
import java.util.concurrent.*;

class DungeonHunterParallel extends RecursiveTask<Integer> {
    static final boolean DEBUG = false;

    // timers
    static long startTime = 0;
    static long endTime = 0;
    private static void tick() { startTime = System.currentTimeMillis(); }
    private static void tock() { endTime = System.currentTimeMillis(); }

    // Fork/Join fields
    private static final int SEQUENTIAL_CUTOFF = 50;
    private HuntParallel[] searches;
    private int start, end;
    private DungeonMapParallel dungeon;
    static int max;

    // Constructor for parallel execution
    DungeonHunterParallel(HuntParallel[] searches,DungeonMapParallel dungeon,int start, int end) {
        this.searches = searches;
        this.start = start;
        this.end = end;
        this.dungeon=dungeon;
    }

    @Override
    protected Integer compute() {

        if (end - start <= SEQUENTIAL_CUTOFF) {
    	int finder = -1;
    	//do all the searches 	
    	int max =Integer.MIN_VALUE;
    	int localMax=Integer.MIN_VALUE;
     	for  (int i=start;i<end;i++) {
    		localMax=searches[i].findManaPeak();
    		if(localMax>max) {
    			max=localMax;
    			finder=i; //keep track of who found it
    		}
    		if(DEBUG) System.out.println("Shadow "+searches[finder].getID()+" finished at  "+localMax + " in " +searches[finder].getSteps());
    	}return finder;
        } 
            int mid = (start + end) / 2;
            DungeonHunterParallel right = new DungeonHunterParallel(searches,dungeon, mid, end);
            DungeonHunterParallel left = new DungeonHunterParallel(searches,dungeon,start, mid);
            left.fork();
            int rightResult = right.compute();
            int leftResult = left.join();

            if(dungeon.getManaLevel(searches[leftResult].getPosRow(),searches[leftResult].getPosCol())>dungeon.getManaLevel(searches[rightResult].getPosRow(),searches[rightResult].getPosCol())){
              return leftResult;
            }
            return rightResult;
        
        
    }

    public static void main(String[] args) {
        double xmin, xmax, ymin, ymax;
        DungeonMapParallel dungeon;

        int numSearches = 10, gateSize = 10;
        HuntParallel[] searches;
        Random rand = new Random();
        int randomSeed = 0;

        if (args.length != 3) {//guarantees that user inputs all required data into the program.
            System.out.println("Incorrect number of command line arguments provided.");
            System.exit(0);
        }

        try {
            gateSize = Integer.parseInt(args[0]);
            if (gateSize <= 0) throw new IllegalArgumentException("Grid size must be greater than 0.");

            numSearches = (int) (Double.parseDouble(args[1]) *
                    (gateSize * 2) * (gateSize * 2) * DungeonMapParallel.RESOLUTION);

            randomSeed = Integer.parseInt(args[2]);
            if (randomSeed < 0) throw new IllegalArgumentException("Random seed must be non-negative.");
            else if (randomSeed > 0) rand = new Random(randomSeed);
        } catch (NumberFormatException e) {
            System.err.println("Error: All arguments must be numeric.");
            System.exit(1);
        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(1);
        }

        xmin = -gateSize;
        xmax = gateSize;
        ymin = -gateSize;
        ymax = gateSize;
        dungeon = new DungeonMapParallel(xmin, xmax, ymin, ymax, randomSeed);

        int dungeonRows = dungeon.getRows();
        int dungeonColumns = dungeon.getColumns();
        searches = new HuntParallel[numSearches];

        for (int i = 0; i < numSearches; i++) {
            searches[i] = new HuntParallel(i + 1,
                    rand.nextInt(dungeonRows),
                    rand.nextInt(dungeonColumns),
                    dungeon);
        }

        ForkJoinPool hotTub =ForkJoinPool.commonPool();
        tick();
        int results = hotTub.invoke(new DungeonHunterParallel(searches, dungeon, 0, numSearches));
        tock();
        

        System.out.printf("\t dungeon size: %d,\n", gateSize);
        System.out.printf("\t rows: %d, columns: %d\n", dungeonRows, dungeonColumns);
        System.out.printf("\t x: [%f, %f], y: [%f, %f]\n", xmin, xmax, ymin, ymax);
        System.out.printf("\t Number searches: %d\n", numSearches);

        System.out.printf("\n\t time: %d ms\n", endTime - startTime);
        int tmp = dungeon.getGridPointsEvaluated();
        System.out.printf("\tnumber dungeon grid points evaluated: %d  (%2.0f%s)\n", tmp,
                (tmp * 1.0 / (dungeonRows * dungeonColumns * 1.0)) * 100.0, "%");

        System.out.printf("Dungeon Master (mana %d) found at:  ", dungeon.getManaLevel(searches[results].getPosRow(),searches[results].getPosCol()));
        System.out.printf("x=%.1f y=%.1f\n\n",
                dungeon.getXcoord(searches[results].getPosRow()),
                dungeon.getYcoord(searches[results].getPosCol()));

        dungeon.visualisePowerMap("visualiseSearch.png", false);
        dungeon.visualisePowerMap("visualiseSearchPath.png", true);
    }
}

