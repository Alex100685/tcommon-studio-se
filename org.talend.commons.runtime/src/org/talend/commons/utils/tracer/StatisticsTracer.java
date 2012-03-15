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
package org.talend.commons.utils.tracer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Timer to measure elapsed time of any process or between steps.
 * 
 * Can write in a file by adding the JVM argument
 * 
 * -DstatsTracerPathFile=/myPathFile
 * 
 * and calling the method print()
 * 
 * Example of use:
 * 
 * <pre>
 * StatisticsTracer myTracerTest1 = StatisticsTracer.getTracer(&quot;myTracerTest1&quot;);
 * 
 * myTracerTest1.traceToFile(&quot;/myPathFileToTrace&quot;, false);
 * 
 * int sleepTime = 100;
 * int executionsCount = 10;
 * 
 * for (int i = 0; i &lt; 10; i++) {
 *     long id = myTracerTest1.start();
 *     Thread.sleep(sleepTime);
 *     myTracerTest1.stop(id);
 * }
 * 
 * long averageWorkTime = myTracerTest1.getAverageWorkTime();
 * long elapsedTimeSinceFirstStart = myTracerTest1.getElapsedTimeSinceFirstStart();
 * long countExecutions = myTracerTest1.getCountExecutions();
 * 
 * StatisticsTracer.removeTracer(&quot;myTracerTest1&quot;);
 * </pre>
 */
public final class StatisticsTracer {

    private static final String TRACE_FILE_HEADER = "date;tracerName;counter;averageTimeMs;elapsedTimeMs;totalWithStoppedTimeMs";

    private static Map<String, StatisticsTracer> tracers = new HashMap<String, StatisticsTracer>();

    private String name;

    private long startTime;

    private long elapsedTime;

    private static long idCounter;

    private long counter;

    private boolean testMode = false;

    private int testModeIndex = 0;

    private int[] timesForTest;

    private Integer displayWithModuloExecutions = defaultModulo;

    private static final Object[] STATIC_LOCK = new Object[0];

    private Writer tracerFileWriter;

    private static String staticTracerFilePath;

    private static int defaultModulo = 1;

    InternalTimer internalTimer = null;

    private boolean enablePrint = true;

    static {
        String statsTracerProperty = "statsTracerPathFile";
        String systemPropertyTracerPathFile = System.getProperty(statsTracerProperty);
        System.out.println("System property \"" + statsTracerProperty + "\" with value '" + systemPropertyTracerPathFile
                + "' used.");
        if (systemPropertyTracerPathFile != null) {
            staticTracerFilePath = systemPropertyTracerPathFile;
        }
    }

    public static void setDefaultModulo(int defaultModulo) {
        StatisticsTracer.defaultModulo = defaultModulo;
    }

    /**
     * 
     * DOC amaumont TimeMeasurer class global comment. Detailled comment
     */
    class InternalTimer {

        private long internalStartTime;

        private long id;

        private List<Long> stepsTime = new ArrayList<Long>();

        public InternalTimer(long id) {
            this.id = id;
        }

        public void start() {
            internalStartTime = getCurrentTime();
        }

        public long deltaTime() {
            return getCurrentTime() - internalStartTime;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + getOuterType().hashCode();
            result = prime * result + (int) (id ^ (id >>> 32));
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            InternalTimer other = (InternalTimer) obj;
            if (!getOuterType().equals(other.getOuterType()))
                return false;
            if (id != other.id)
                return false;
            return true;
        }

        private StatisticsTracer getOuterType() {
            return StatisticsTracer.this;
        }

    }

    private StatisticsTracer(String name) {
        this.name = name;
        if (staticTracerFilePath != null) {
            traceToFile(staticTracerFilePath, false);
        }
    }

    public void traceToFile(String pathFile, boolean append) {
        initFile(pathFile, append);
    }

    public static synchronized StatisticsTracer getTracer(String tracerId) {
        if (tracers.keySet().contains(tracerId)) {
            return tracers.get(tracerId);
        } else {
            StatisticsTracer timer = new StatisticsTracer(tracerId);
            tracers.put(tracerId, timer);
            return timer;
        }
    }

    public static synchronized StatisticsTracer getTimerForTestOnly(String tracerId, int[] timesForTest) {
        if (tracers.keySet().contains(tracerId)) {
            return tracers.get(tracerId);
        } else {
            StatisticsTracer timer = new StatisticsTracer(tracerId);
            timer.timesForTest = timesForTest;
            tracers.put(tracerId, timer);
            return timer;
        }
    }

    public synchronized void increment() {
        counter++;
    }

    /**
     * 
     * Method "start".
     */
    public synchronized void start() {
        if (counter == 0) {
            this.startTime = getCurrentTime();
        }
        long id = 0;
        synchronized (STATIC_LOCK) {
            id = idCounter++;
        }
        internalTimer = new InternalTimer(id);
        internalTimer.start();
    }

    private long getCurrentTime() {
        if (testMode) {
            int time = timesForTest[testModeIndex++];
            return time;
        } else {
            return System.currentTimeMillis();
        }
    }

    public void start(PrintWriter printWriter) {
        start();
        printWriter.println("Start " + name); //$NON-NLS-1$
    }

    /**
     * 
     * Method "stop".
     * 
     * @param internalTimerId
     * @return the total time since the start
     */
    public synchronized long stop() {
        long deltaTime = 0;
        if (internalTimer != null) {
            deltaTime = internalTimer.deltaTime();
            elapsedTime += deltaTime;
            counter++;
        } else {
            new Exception("The " + StatisticsTracer.class.getSimpleName() + " '" + name + "' has not been started!")
                    .printStackTrace();
        }
        if (enablePrint && displayWithModuloExecutions != null) {
            print(displayWithModuloExecutions);
        }
        return deltaTime;
    }

    public void stop(PrintWriter printWriter) {
        stop();
        printWriter.println("Stop  " + name); //$NON-NLS-1$
    }

    public void print() {
        System.out.println(toString());
        writeDataRow();
    }

    public void print(PrintWriter printWriter) {
        printWriter.println(toString());
        writeDataRow();
    }

    private void writeDataRow() {
        if (tracerFileWriter != null) {
            try {
                tracerFileWriter.write("\n" + toDataRow());
                tracerFileWriter.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String toString() {
        long averageTime = getAverageWorkTime();
        long totalWithStoppedTime = getElapsedTimeSinceFirstStart();
        return "Print " + name + " : " + counter + " executions, average: " + averageTime + " ms, total execution: "
                + elapsedTime + " ms, total with stopped time " + totalWithStoppedTime + " ms)";
    }

    public String toDataRow() {
        long averageTime = getAverageWorkTime();
        long totalWithStoppedTime = getElapsedTimeSinceFirstStart();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = simpleDateFormat.format(new Date());
        return date + ";" + name + ";" + counter + ";" + averageTime + ";" + elapsedTime + ";" + totalWithStoppedTime;
    }

    public long getElapsedTimeSinceFirstStart() {
        return getCurrentTime() - this.startTime;
    }

    public long getAverageWorkTime() {
        long averageTime = 0;
        if (counter != 0) {
            averageTime = elapsedTime / counter;
        }
        return averageTime;
    }

    public long getCountExecutions() {
        return counter;
    }

    public long getElapsedTime() {
        return elapsedTime;
    }

    public void setModuloPrint(int displayWithModuloExecutions) {
        this.displayWithModuloExecutions = displayWithModuloExecutions;
    }

    public void enablePrint() {
        enablePrint = false;
    }

    public void disablePrint() {
        enablePrint = true;
    }

    public void print(int displayWithModuloExecutions) {
        if (displayWithModuloExecutions != 0 && (getCountExecutions() % displayWithModuloExecutions) == 0) {
            print();
        }
    }

    public static synchronized void removeTracer(String tracerId) {
        StatisticsTracer removed = tracers.remove(tracerId);
        if (removed.tracerFileWriter != null) {
            try {
                removed.tracerFileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void initFile(String filePath, boolean append) {
        try {
            File file = new File(filePath);
            boolean exists = file.exists();
            tracerFileWriter = new FileWriter(file, append);
            if (!exists)
                tracerFileWriter.write(TRACE_FILE_HEADER);
            tracerFileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
