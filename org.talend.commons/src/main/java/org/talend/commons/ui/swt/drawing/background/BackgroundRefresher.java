// ============================================================================
//
// Copyright (C) 2006-2007 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.commons.ui.swt.drawing.background;

import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ScrollBar;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Tree;
import org.talend.commons.ui.ws.WindowSystem;
import org.talend.commons.utils.performance.IPerformanceEvaluatorListener;
import org.talend.commons.utils.performance.PerformanceEvaluator;
import org.talend.commons.utils.performance.PerformanceEvaluatorEvent;
import org.talend.commons.utils.threading.AsynchronousThreading;
import org.talend.commons.utils.threading.ExecutionLimiter;

/**
 * DOC amaumont class global comment. Detailled comment <br/>
 * 
 * $Id$
 * 
 */
public class BackgroundRefresher implements IBackgroundRefresher {

    /**
     * in seconds.
     */
    private static final int TIME_BEFORE_REEVALUATE_PERFORMANCE = 30;

    protected Image bgImage1;

    protected Image bgImage2;

    private Image oldImage;

    private final PerformanceEvaluator performanceEvaluator = new PerformanceEvaluator();

    protected IBgDrawableComposite drawableComposite;

    boolean antialiasAllowed;

    Color backgroundColor;

    private Thread threadToEvaluatePerformance;

    /**
     * DOC amaumont Linker constructor comment.
     * 
     * @param drawableComposite
     */
    public BackgroundRefresher(IBgDrawableComposite drawableComposite) {
        super();
        this.drawableComposite = drawableComposite;
        init();
    }

    private ExecutionLimiter executionLimiter = new ExecutionLimiter(50, true) {

        /*
         * (non-Javadoc)
         * 
         * @see org.talend.commons.utils.threading.ExecutionLimiter#execute(boolean)
         */
        @Override
        protected void execute(final boolean isFinalExecution) {
            drawableComposite.getBgDrawableComposite().getDisplay().asyncExec(new Runnable() {

                public void run() {
                    // if (isFinalExecution) {
                    refreshBackground();
                    // }
                }

            });

        }

    };

    private void init() {
        initTimeLimitForBackgroundRefresh();
        drawableComposite.getBgDrawableComposite().addControlListener(new ControlListener() {

            public void controlMoved(ControlEvent e) {
            }

            public void controlResized(ControlEvent e) {
                createBgImages();
                refreshBackground();
                // updateBackground(true, false);
            }

        });
        
        drawableComposite.getBgDrawableComposite().addDisposeListener(new DisposeListener() {

            public void widgetDisposed(DisposeEvent e) {
                releaseBgImages();
            }
            
        });

    }

    private void initTimeLimitForBackgroundRefresh() {
        (new Object() {

            void init() {
                performanceEvaluator.addListener(new IPerformanceEvaluatorListener() {

                    public void handleEvent(PerformanceEvaluatorEvent event) {
                        boolean previousAntialiasAllowed = antialiasAllowed;
                        antialiasAllowed = event.getIndicePerformance() < PerformanceEvaluator.GOOD_PERFORMANCE_INDICE;
                        // System.out.println(event.getIndicePerformance());
                        if (previousAntialiasAllowed != antialiasAllowed && !drawableComposite.getBgDrawableComposite().isDisposed()
                                && drawableComposite.getBgDrawableComposite().getDisplay() != null) {

                            new AsynchronousThreading(0, false, drawableComposite.getBgDrawableComposite().getDisplay(), new Runnable() {

                                public void run() {
                                    // System.out.println(antialiasAllowed);
                                    refreshBackground();

                                }
                            }).start();

                        }
                    }
                });
            }
        }).init();

        new AsynchronousThreading(50, new Runnable() {

            public void run() {

                launchEvaluatingPerformanceLoop();

            }
        }).start();

    }

    /**
     * DOC amaumont Comment method "updateBackground".
     */
    public synchronized void refreshBackground() {
        // System.out.println("updateBackground");
        if (drawableComposite.getBgDrawableComposite().isDisposed()) {
            return;
        }

        oldImage = drawableComposite.getBgDrawableComposite().getBackgroundImage();
        Image newImage = null;
        if (oldImage == null || oldImage.isDisposed()
        // || bgImage1 == null || bgImage1.isDisposed()
        // || bgImage2 == null || bgImage2.isDisposed()
        ) {
            createBgImages();
            newImage = bgImage1;
        } else {
            if (oldImage == bgImage1) {
                newImage = bgImage2;
            } else {
                newImage = bgImage1;
            }
        }

        if (newImage != null && !newImage.isDisposed()) {

            GC gc = new GC(newImage);

            drawableComposite.setOffset(new Point(0, 0));

            drawableComposite.drawBackground(gc);

            gc.dispose();

            drawableComposite.getBgDrawableComposite().setBackgroundImage(newImage);

            clearImage(oldImage);
            oldImage = newImage;

        }

    }

    protected void createBgImages() {
        Rectangle clientArea = drawableComposite.getBgDrawableComposite().getBounds();
        Rectangle imageArea = new Rectangle(0, 0, clientArea.width, clientArea.height + 100);
        if (imageArea.width > 0 && imageArea.height > 0) {
            releaseBgImages();
            bgImage1 = new Image(drawableComposite.getBgDrawableComposite().getDisplay(), imageArea);
            bgImage2 = new Image(drawableComposite.getBgDrawableComposite().getDisplay(), imageArea);
            clearImage(bgImage1);
            clearImage(bgImage2);
        }
    }

    public void releaseBgImages() {
        if (bgImage1 != null) {
            bgImage1.dispose();
        }
        if (bgImage2 != null) {
            bgImage2.dispose();
        }
    }

    protected void clearImage(final Image image) {
        if (image != null && !image.isDisposed()) {
            GC gc = new GC(image);
            gc.setBackground(backgroundColor == null ? drawableComposite.getBgDrawableComposite().getBackground() : backgroundColor);
            gc.fillRectangle(drawableComposite.getBgDrawableComposite().getClientArea());
            gc.dispose();
        }
    }

    /**
     * This method must be call one time by shell opened.
     * 
     */
    protected void launchEvaluatingPerformanceLoop() {
        threadToEvaluatePerformance = new Thread() {

            @Override
            public void run() {
                performanceEvaluator.evaluate(); // first evaluation is not representative
                try {
                    // to start evaluation after window loaded
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    return;
                }
                while (!drawableComposite.getBgDrawableComposite().isDisposed()) {
                    performanceEvaluator.evaluate();
                    try {
                        // to start evaluation after window loaded
                        Thread.sleep(TIME_BEFORE_REEVALUATE_PERFORMANCE * 1000);
                    } catch (InterruptedException e) {
                        break;
                    }
                }
            }
        };
//        threadToEvaluatePerformance.start();
    }

    /**
     * Getter for backgroundColor.
     * 
     * @return the backgroundColor
     */
    public Color getBackgroundColor() {
        return this.backgroundColor;
    }

    /**
     * Sets the backgroundColor.
     * 
     * @param backgroundColor the backgroundColor to set
     */
    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public Point convertPointToCommonParentOrigin(Point point, Composite child) {
        Point returnedPoint = new Point(point.x, point.y);
        while (child != drawableComposite.getBgDrawableComposite()) {
            Rectangle bounds = child.getBounds();
            if (WindowSystem.isGTK() && child instanceof Table) {
                returnedPoint.y += ((Table) child).getHeaderHeight();
            }
            if (WindowSystem.isGTK() && child instanceof Tree) {
                returnedPoint.y += ((Tree) child).getHeaderHeight();
            }
            child = child.getParent();
            ScrollBar vScrollBar = child.getVerticalBar();
            if (vScrollBar != null) {
                returnedPoint.y += vScrollBar.getSelection();
            }
            returnedPoint.x += bounds.x;
            returnedPoint.y += bounds.y;

        }
        return returnedPoint;
    }

    /**
     * Getter for antialiasActivated.
     * 
     * @return the antialiasActivated
     */
    public boolean isAntialiasAllowed() {
        return this.antialiasAllowed;
    }

    /**
     * DOC amaumont Comment method "updateBackroundAsynchronous".
     */
    public void refreshBackgroundWithLimiter() {
        executionLimiter.startIfExecutable();
    }

}
