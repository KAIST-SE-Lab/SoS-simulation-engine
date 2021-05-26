package property.pattern;/*package new_simvasos.property.pattern;

import new_simvasos.log.Log;
import new_simvasos.log.Snapshot;
import new_simvasos.property.Property;


import java.util.HashMap;

public abstract class ExistenceUntilChecker extends ExistenceChecker{
    @Override
    protected abstract boolean evaluateState(Snapshot state, Property verificationProperty);

    @Override
    public boolean check(Log log, Property verificationProperty, int until) {
        HashMap<Integer, Snapshot> snapshots = log.getSnapshotMap();
        int logSize = snapshots.size(); // 0 ... 10 => size: 11, endTime: 10

        for (int i = 0; i < until && i < logSize; i++) {
            if (evaluateState(snapshots.get(i), verificationProperty)) {
                return true;
            }
        }
        return false;
    }
}*/

