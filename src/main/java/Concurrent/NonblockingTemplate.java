package Concurrent;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * 非阻塞算法模板
 *
 * Note:我不是非阻塞算法的专家，所以下面的模板可能有一些错误。不要将自己的非阻塞算法实现基于我的模板。该模板仅用于使您了解非阻塞算法的代码外观。如果要实现自己的非阻塞算法，请首先研究一些实际的，有效的非阻塞算法实现，以了解有关在实践中如何实现它们的更多信息。
 */
public class NonblockingTemplate {
    public static class IntendedModification {
        public AtomicBoolean completed = new AtomicBoolean(false);
    }

    private AtomicStampedReference<IntendedModification> ongoingMod
            = new AtomicStampedReference<>(null, 0);

    //declare the state of the data structure here.

    public void modify() {
        while(!attemptModifyASR());
    }

    public boolean attemptModifyASR() {
        boolean modified = false;

        IntendedModification currentlyOngoingMod =
                ongoingMod.getReference();
        int stamp = ongoingMod.getStamp();

        if(currentlyOngoingMod == null) {
            //copy data structure state - for use in intended modification

            //prepare intended modification
            IntendedModification newMod = new IntendedModification();

            boolean modSubmitted = ongoingMod.compareAndSet(null, newMod, stamp, stamp + 1);

            if(modSubmitted) {

                //complete modification via a series of compare-and-swap operations.
                //note: other threads may assist in completing the compare-and-swap operations, so some CAS may fail

                modified = true;
            }
        } else {
            //attempt to complete ongoing modification,so the data structure is freed up to
            //allow access from this thread.

            modified = false;
        }
        return modified;

    }
}
