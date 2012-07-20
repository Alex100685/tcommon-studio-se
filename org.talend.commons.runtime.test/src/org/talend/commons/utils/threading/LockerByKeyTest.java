// ============================================================================
//
// Copyright (C) 2006-2012 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.commons.utils.threading;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.talend.commons.utils.threading.lockerbykey.LockerValue;
import org.talend.commons.utils.threading.lockerbykeyoperators.AbstractLockerByKeyOperator;
import org.talend.commons.utils.threading.lockerbykeyoperators.LockThenUnlockOperator;
import org.talend.commons.utils.threading.lockerbykeyoperators.ResultContainer;
import org.talend.commons.utils.threading.lockerbykeyoperators.TryLockThenUnlockOperator;
import org.talend.commons.utils.threading.lockerbykeyoperators.TryLockWithTimeoutThenUnlockOperator;
import org.talend.commons.utils.threading.threadsafetester.AbstractThreadSafetyTester;

public class LockerByKeyTest {

    private static final int WAIT_THREAD_STARTED = 100;

    private static final int WAIT_JOIN = 100;

    protected LockerByKey createLockerInstance() {
        // default implementation when running this TestCase
        return new LockerByKey();
    }

    class LockInterruptiblyThread extends Thread {

        private LockerByKey locker;

        Integer key;

        public Throwable throwable;

        public LockInterruptiblyThread(LockerByKey locker, Integer key) {
            super();
            this.locker = locker;
            this.key = key;
        }

        @SuppressWarnings("unchecked")
        @Override
        public void run() {
            try {
                locker.lockInterruptibly(key);
            } catch (Throwable e) {
                throwable = e;
            }
        }

    };

    class LockInterruptiblyTwiceThread extends Thread {

        private LockerByKey locker;

        Integer key;

        public Throwable throwable;

        public LockInterruptiblyTwiceThread(LockerByKey locker, Integer key) {
            super();
            this.locker = locker;
            this.key = key;
        }

        @Override
        public void run() {
            try {
                locker.lockInterruptibly(key);
                locker.lockInterruptibly(key);
            } catch (Throwable e) {
                throwable = e;
            }
        }

    };

    class UnlockThread extends Thread {

        private LockerByKey locker;

        Integer key;

        public Throwable throwable;

        public UnlockThread(LockerByKey locker, Integer key) {
            super();
            this.locker = locker;
            this.key = key;
        }

        @SuppressWarnings("unchecked")
        @Override
        public void run() {
            try {
                locker.unlock(key);
            } catch (Throwable e) {
                throwable = e;
            }
        }

    };

    @SuppressWarnings("unchecked")
    @Test(timeout = 2000)
    // @Test()
    public void testIsLocked() throws Throwable {
        final LockerByKey locker = createLockerInstance();

        final int keyOne = 1;

        boolean isLocked = locker.isLocked(keyOne);
        assertThat(isLocked, is(false));

        locker.lockInterruptibly(keyOne);

        isLocked = locker.isLocked(keyOne);
        assertThat(isLocked, is(true));

        boolean hasUnlocked = locker.unlock(keyOne);
        assertThat(hasUnlocked, is(true));

        isLocked = locker.isLocked(keyOne);
        assertThat(isLocked, is(false));
    }

    @SuppressWarnings("unchecked")
    @Test(timeout = 2000)
    // @Test()
    public void testCleanEnabledDefault() throws Throwable {
        final LockerByKey locker = new LockerByKey();

        final int keyOne = 1;

        boolean isLocked = locker.isLocked(keyOne);
        assertThat(isLocked, is(false));

        for (int i = 0; i < 1000; i++) {
            locker.lockInterruptibly(i);

            isLocked = locker.isLocked(i);
            assertThat(isLocked, is(true));

            boolean hasUnlocked = locker.unlock(i);
            assertThat(hasUnlocked, is(true));

            isLocked = locker.isLocked(i);
            assertThat(isLocked, is(false));
        }

        /*
         * Be warn, the last is not removed by the auto clean, need to call clean again to clear all.
         */
        for (int i = 0; i < 999; i++) {
            assertThat(locker.getLockerValue(i), is(nullValue()));
        }

    }

    @SuppressWarnings("unchecked")
    @Test(timeout = 2000)
    // @Test()
    public void testCleanEnabledProvidedPeriod() throws Throwable {
        final LockerByKey locker = new LockerByKey(true, 600);

        final int keyOne = 1;

        boolean isLocked = locker.isLocked(keyOne);
        assertThat(isLocked, is(false));

        for (int i = 0; i < 1000; i++) {
            locker.lockInterruptibly(i);

            isLocked = locker.isLocked(i);
            assertThat(isLocked, is(true));

            boolean hasUnlocked = locker.unlock(i);
            assertThat(hasUnlocked, is(true));

            isLocked = locker.isLocked(i);
            assertThat(isLocked, is(false));
        }

        /*
         * Be warn, the last is not removed by the auto clean, need to call clean again to clear all.
         */
        for (int i = 0; i < 1000; i++) {
            if (i < 600) {
                assertThat(locker.getLockerValue(i), is(nullValue()));
            } else {
                assertThat(locker.getLockerValue(i), is(notNullValue()));
            }
        }

    }

    @SuppressWarnings("unchecked")
    @Test(timeout = 2000)
    // @Test()
    public void testShutdown_Lock() throws Throwable {
        final LockerByKey locker = createLockerInstance();

        ExecutorService poolRunnable1 = Executors.newCachedThreadPool();
        ExecutorService poolRunnable2 = Executors.newCachedThreadPool();

        final int keyOne = 1;
        final String expectedResult = "ended";
        Callable<String> runnable = new Callable<String>() {

            /*
             * (non-Javadoc)
             * 
             * @see java.util.concurrent.Callable#call()
             */
            @Override
            public String call() throws Exception {
                locker.lockInterruptibly(keyOne);
                return expectedResult;
            }
        };

        Future<String> future1 = poolRunnable1.submit(runnable);
        Thread.sleep(WAIT_THREAD_STARTED);
        String result1 = future1.get(2, TimeUnit.SECONDS);
        assertThat(result1, is(expectedResult));

        Future<String> future2 = poolRunnable2.submit(runnable);
        Thread.sleep(WAIT_THREAD_STARTED);

        locker.shutdown();

        try {
            future2.get(2, TimeUnit.SECONDS);
            fail("Should be interrupted by the shutdown");
        } catch (Exception e) {
            boolean isInterruptedException = e.getCause() instanceof InterruptedException;
            if (!isInterruptedException) {
                e.printStackTrace();
                fail("Should be an InterruptedException, please read logs");
            }
        }

    }

    @SuppressWarnings("unchecked")
    @Test(timeout = 2000)
    // @Test()
    public void testShutdown_TryLock() throws Throwable {
        final LockerByKey locker = createLockerInstance();

        ExecutorService poolRunnable1 = Executors.newCachedThreadPool();
        ExecutorService poolRunnable2 = Executors.newCachedThreadPool();

        final int keyOne = 1;
        final String expectedResult = "ended";
        Callable<String> runnable = new Callable<String>() {

            /*
             * (non-Javadoc)
             * 
             * @see java.util.concurrent.Callable#call()
             */
            @Override
            public String call() throws Exception {
                locker.tryLock(keyOne, 10000);
                return expectedResult;
            }
        };

        Future<String> future1 = poolRunnable1.submit(runnable);
        Thread.sleep(WAIT_THREAD_STARTED);
        String result1 = future1.get(2, TimeUnit.SECONDS);
        assertThat(result1, is(expectedResult));

        Future<String> future2 = poolRunnable2.submit(runnable);
        Thread.sleep(WAIT_THREAD_STARTED);

        locker.shutdown();

        try {
            future2.get(2, TimeUnit.SECONDS);
            fail("Should be interrupted by the shutdown");
        } catch (Exception e) {
            boolean isInterruptedException = e.getCause() instanceof InterruptedException;
            if (!isInterruptedException) {
                e.printStackTrace();
                fail("Should be an InterruptedException, please read logs");
            }
        }

    }

    @SuppressWarnings("unchecked")
    @Test(timeout = 2000)
    // @Test()
    public void testUnlock_withGetLockerValue() throws Throwable {
        final LockerByKey locker = createLockerInstance();

        final int keyOne = 1;

        LockerValue lockerValue = locker.getLockerValue(keyOne);
        assertThat(lockerValue, is(nullValue()));

        locker.lockInterruptibly(keyOne);

        LockerValue lockerValue1 = locker.getLockerValue(keyOne);
        assertThat(lockerValue1, is(notNullValue()));

        boolean hasUnlocked = locker.unlock(keyOne);
        assertThat(hasUnlocked, is(true));

        LockerValue lockerValue2 = locker.getLockerValue(keyOne);
        assertThat(lockerValue2, is(lockerValue1));

        locker.shutdown();
        LockerValue lockerValue3 = locker.getLockerValue(keyOne);
        assertThat(lockerValue3, is(nullValue()));

    }

    @Test(timeout = 2000)
    // @Test()
    public void testTryLock_LockTwiceWithSameKey() throws Throwable {
        final LockerByKey locker = createLockerInstance();

        final int keyOne = 1;
        LockInterruptiblyThread threadLockKeyOne_first = new LockInterruptiblyThread(locker, keyOne);
        threadLockKeyOne_first.start();
        threadLockKeyOne_first.join(WAIT_JOIN);
        assertThat(threadLockKeyOne_first.isAlive(), is(false));
        if (threadLockKeyOne_first.throwable != null) {
            throw threadLockKeyOne_first.throwable;
        }

        LockInterruptiblyThread threadLockKeyOne_second = new LockInterruptiblyThread(locker, keyOne);
        threadLockKeyOne_second.start();
        Thread.sleep(WAIT_THREAD_STARTED);
        assertThat(threadLockKeyOne_second.isAlive(), is(true));
        threadLockKeyOne_second.interrupt();
        threadLockKeyOne_second.join(WAIT_JOIN);
        assertThat(threadLockKeyOne_second.isAlive(), is(false));
        assertThat(threadLockKeyOne_second.throwable, is(notNullValue()));
        assertThat(threadLockKeyOne_second.throwable, is(instanceOf(InterruptedException.class)));
    }

    @Test(timeout = 2000)
    // @Test()
    public void testTryLock_LockTwiceWithSamePrimitiveValueButDifferentInteger() throws Throwable {
        final LockerByKey locker = createLockerInstance();

        final int keyOne = 1;
        final Integer keyOneInteger1 = new Integer(keyOne);
        LockInterruptiblyThread threadLockKeyOne_first = new LockInterruptiblyThread(locker, keyOneInteger1);
        threadLockKeyOne_first.start();
        threadLockKeyOne_first.join(WAIT_JOIN);
        assertThat(threadLockKeyOne_first.isAlive(), is(false));
        if (threadLockKeyOne_first.throwable != null) {
            throw threadLockKeyOne_first.throwable;
        }

        final Integer keyOneInteger2 = new Integer(keyOne);
        LockInterruptiblyThread threadLockKeyOne_second = new LockInterruptiblyThread(locker, keyOneInteger2);
        threadLockKeyOne_second.start();
        Thread.sleep(WAIT_THREAD_STARTED);
        assertThat(threadLockKeyOne_second.isAlive(), is(true));
        threadLockKeyOne_second.interrupt();
        threadLockKeyOne_second.join(WAIT_JOIN);
        assertThat(threadLockKeyOne_second.isAlive(), is(false));
        assertThat(threadLockKeyOne_second.throwable, is(notNullValue()));
        assertThat(threadLockKeyOne_second.throwable, is(instanceOf(InterruptedException.class)));
    }

    @Test(timeout = 2000)
    public void testTryLock_LockTwiceWithDifferentKeys() throws Throwable {
        final LockerByKey locker = createLockerInstance();

        final int keyOne = 1;
        LockInterruptiblyThread threadLockKeyOne_first = new LockInterruptiblyThread(locker, keyOne);
        threadLockKeyOne_first.start();
        threadLockKeyOne_first.join(WAIT_JOIN);
        assertThat(threadLockKeyOne_first.isAlive(), is(false));
        if (threadLockKeyOne_first.throwable != null) {
            throw threadLockKeyOne_first.throwable;
        }

        final int keyTwo = 2;
        LockInterruptiblyThread threadLockKeyOne_second = new LockInterruptiblyThread(locker, keyTwo);
        threadLockKeyOne_second.start();
        threadLockKeyOne_second.join(WAIT_JOIN);
        assertThat(threadLockKeyOne_second.isAlive(), is(false));
        if (threadLockKeyOne_first.throwable != null) {
            throw threadLockKeyOne_first.throwable;
        }

    }

    @Test(timeout = 2000)
    // @Test
    public void testTryLock_ThenUnlockFromOtherThread() throws Throwable {
        final LockerByKey locker = createLockerInstance();

        final int keyOne = 1;
        LockInterruptiblyThread threadLockKeyOne = new LockInterruptiblyThread(locker, keyOne);
        threadLockKeyOne.start();
        threadLockKeyOne.join(WAIT_JOIN);
        assertThat(threadLockKeyOne.isAlive(), is(false));
        if (threadLockKeyOne.throwable != null) {
            throw threadLockKeyOne.throwable;
        }

        UnlockThread threadUnlockKeyOne = new UnlockThread(locker, keyOne);
        threadUnlockKeyOne.start();
        threadUnlockKeyOne.join(WAIT_JOIN);
        assertThat(threadUnlockKeyOne.isAlive(), is(false));
        assertThat(threadUnlockKeyOne.throwable, is(instanceOf(IllegalMonitorStateException.class)));
    }

    @Test(timeout = 2000)
    // @Test
    public void testTryLock_LockTwiceFromFirstLockerThread() throws Throwable {
        final LockerByKey locker = createLockerInstance();

        final int keyOne = 1;
        LockInterruptiblyTwiceThread threadLockTwiceKeyOne = new LockInterruptiblyTwiceThread(locker, keyOne);
        threadLockTwiceKeyOne.start();
        threadLockTwiceKeyOne.join(WAIT_JOIN);
        assertThat(threadLockTwiceKeyOne.isAlive(), is(false));
        if (threadLockTwiceKeyOne.throwable != null) {
            throw threadLockTwiceKeyOne.throwable;
        }

    }

    @Test(timeout = 2000)
    // @Test()
    public void testTryLock_LockTwiceFromDifferentThread() throws Throwable {
        final LockerByKey locker = createLockerInstance();

        final int keyOne = 1;
        LockInterruptiblyThread threadLock_threadOne = new LockInterruptiblyThread(locker, keyOne);
        threadLock_threadOne.start();
        threadLock_threadOne.join(WAIT_JOIN);
        assertThat(threadLock_threadOne.isAlive(), is(false));
        if (threadLock_threadOne.throwable != null) {
            throw threadLock_threadOne.throwable;
        }

        LockInterruptiblyThread threadLock_threadTwo = new LockInterruptiblyThread(locker, keyOne);
        threadLock_threadTwo.start();
        Thread.sleep(WAIT_THREAD_STARTED);
        threadLock_threadTwo.interrupt();
        assertThat(threadLock_threadTwo.isAlive(), is(true));
        threadLock_threadTwo.join(WAIT_JOIN);
        assertThat(threadLock_threadTwo.isAlive(), is(false));
        assertThat(threadLock_threadTwo.throwable, is(notNullValue()));
        assertThat(threadLock_threadTwo.throwable, is(instanceOf(InterruptedException.class)));
    }

    @Test(timeout = 2000)
    // @Test()
    public void testTryLock_LockTwiceFromSameThread_forbidReentrantLockFromOtherThread() throws Throwable {
        final LockerByKey locker = createLockerInstance();

        final int keyOne = 1;
        LockInterruptiblyTwiceThread threadLockTwiceKeyOne = new LockInterruptiblyTwiceThread(locker, keyOne);
        threadLockTwiceKeyOne.start();
        Thread.sleep(WAIT_THREAD_STARTED);
        assertThat(threadLockTwiceKeyOne.isAlive(), is(false));
        assertThat(threadLockTwiceKeyOne.throwable, is(nullValue()));

        // assertThat(threadLockTwiceKeyOne.throwable, is(notNullValue()));
        // assertThat(threadLockTwiceKeyOne.throwable, is(instanceOf(InterruptedException.class)));
    }

    @Test(timeout = 2000)
    // @Test()
    public void testTryLock_LockTwiceFromDifferentThread_forbidReentrantLockFromLockerThread() throws Throwable {
        final LockerByKey locker = createLockerInstance();

        final int keyOne = 1;
        LockInterruptiblyThread threadLock_threadOne = new LockInterruptiblyThread(locker, keyOne);
        threadLock_threadOne.start();
        threadLock_threadOne.join(WAIT_JOIN);
        assertThat(threadLock_threadOne.isAlive(), is(false));
        if (threadLock_threadOne.throwable != null) {
            throw threadLock_threadOne.throwable;
        }

        LockInterruptiblyThread threadLock_threadTwo = new LockInterruptiblyThread(locker, keyOne);
        threadLock_threadTwo.start();
        Thread.sleep(WAIT_THREAD_STARTED);
        assertThat(threadLock_threadTwo.isAlive(), is(true));
        threadLock_threadTwo.interrupt();
        threadLock_threadTwo.join(WAIT_JOIN);
        assertThat(threadLock_threadTwo.isAlive(), is(false));
        assertThat(threadLock_threadTwo.throwable, is(notNullValue()));
        assertThat(threadLock_threadTwo.throwable, is(instanceOf(InterruptedException.class)));
    }

    @Test(timeout = 20000)
    // @Test
    public void testThreadSafety_LockThenUnlock() throws Exception {
        final LockerByKey locker = new LockerByKey();
        // final LockerByKey locker = new LockerByKey(true, 100);
        // final LockerByKey locker = new LockerByKey(true, Integer.MAX_VALUE);
        final int nOperatorsByClassOperator = 30;
        final int nOperationsByOperator = 500;
        boolean assertEntriesLessThanCleanPeriod = true;
        launchThreadSafetyTestWithLockThenUnlock(locker, nOperatorsByClassOperator, nOperationsByOperator,
                assertEntriesLessThanCleanPeriod, LockThenUnlockOperator.class, TryLockWithTimeoutThenUnlockOperator.class,
                TryLockThenUnlockOperator.class);
    }

    // @Test(timeout = 30000)
    // // @Test
    // public void testThreadSafety_LockThenUnlock_2_operators() throws Exception {
    // final LockerByKey locker = createLockerInstance();
    // final int nOperatorsByClassOperator = 2;
    // final int nOperationsByOperator = 1;
    // boolean assertEntriesLessThanCleanPeriod = true;
    // launchThreadSafetyTestWithLockThenUnlock(locker, nOperatorsByClassOperator, nOperationsByOperator,
    // assertEntriesLessThanCleanPeriod, LockThenUnlockOperator.class, TryLockWithTimeoutThenUnlockOperator.class,
    // TryLockThenUnlockOperator.class);
    // }

    private void launchThreadSafetyTestWithLockThenUnlock(final LockerByKey locker, final int nOperatorsByClassOperator,
            final int nOperationsByOperator, boolean assertEntriesLessThanCleanPeriod,
            Class<? extends AbstractLockerByKeyOperator>... classOperators) throws Exception {
        final ResultContainer resultContainer = new ResultContainer();

        class LockerThreadSafetyTester extends AbstractThreadSafetyTester<AbstractLockerByKeyOperator> {

            private boolean assertEntriesLessThanCleanPeriod;

            public LockerThreadSafetyTester(int nOperatorsByClassOperator, boolean assertEntriesLessThanCleanPeriod,
                    Class<? extends AbstractLockerByKeyOperator>... classOperators) {
                super(nOperatorsByClassOperator, classOperators);
                this.assertEntriesLessThanCleanPeriod = assertEntriesLessThanCleanPeriod;
            }

            /*
             * (non-Javadoc)
             * 
             * @see
             * org.talend.commons.utils.threading.AbstractThreadSafetyTester#initInstance(org.talend.commons.utils.threading
             * .IThreadSafetyOperator)
             */
            protected void initInstance(AbstractLockerByKeyOperator operatorInstance) {
                operatorInstance.setLocker(locker);
                operatorInstance.setnOperationsByOperator(nOperationsByOperator);
                operatorInstance.setResultContainer(resultContainer);
            }

            /*
             * (non-Javadoc)
             * 
             * @see org.talend.commons.utils.threading.AbstractThreadSafetyTester#assertFinal()
             */
            @Override
            protected void assertFinal() {
                int actualSumLockedAtEnd = 0;
                int actualSumLockersAtEnd = 0;
                for (int i = 0; i < nOperationsByOperator; i++) {
                    LockerValue lockerValue = locker.getLockerValue(i);
                    if (lockerValue != null) {
                        int queueLength = lockerValue.getLock().getQueueLength();
                        if (lockerValue != null) {
                            actualSumLockedAtEnd += queueLength;
                        }
                        actualSumLockersAtEnd++;
                    }
                }
                assertThat(actualSumLockedAtEnd, is(0));
                if (assertEntriesLessThanCleanPeriod) {
                    assertTrue("actualSumLockersAtEnd > locker.getCleanPeriod() where actualSumLockersAtEnd="
                            + actualSumLockersAtEnd + " and locker.getCleanPeriod()=" + locker.getCleanPeriod(),
                            actualSumLockersAtEnd < locker.getCleanPeriod());
                }

                locker.shutdown();
                actualSumLockedAtEnd = 0;
                actualSumLockersAtEnd = 0;
                for (int i = 0; i < nOperationsByOperator; i++) {
                    LockerValue lockerValue = locker.getLockerValue(i);
                    if (lockerValue != null) {
                        int queueLength = lockerValue.getLock().getQueueLength();
                        if (lockerValue != null) {
                            actualSumLockedAtEnd += queueLength;
                        }
                        actualSumLockersAtEnd++;
                    }
                }
                assertThat(actualSumLockedAtEnd, is(0));
                assertThat(actualSumLockersAtEnd, is(0));

                assertThat(AbstractLockerByKeyOperator.getNotThreadSafeCounter(),
                        is(resultContainer.sumThreadSafeOperations.get()));
                System.out.println("Total of operations done: " + resultContainer.sumThreadSafeOperations.get());
            }

        }

        @SuppressWarnings("unchecked")
        LockerThreadSafetyTester lockerThreadSafetyTester = new LockerThreadSafetyTester(nOperatorsByClassOperator,
                assertEntriesLessThanCleanPeriod, classOperators);
        lockerThreadSafetyTester.start();
    }
}
