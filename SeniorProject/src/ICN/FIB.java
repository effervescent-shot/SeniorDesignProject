package ICN;

import Helper.Path;

import java.util.HashMap;
import java.util.Map;

public class FIB {
    public class FIBRow {
        private Path firstPath;
        private Path secondPath;
        private Path thirdPath;

        public FIBRow() {
            firstPath = new Path();
            secondPath = new Path();
            thirdPath = new Path();
        }

        public FIBRow(Path p1, Path p2, Path p3) {
            this.firstPath = p1;
            this.secondPath = p2;
            this.thirdPath = p3;
        }
    }
    Map<Integer,FIBRow> forwardingTable = new HashMap<Integer,FIBRow>();

}
