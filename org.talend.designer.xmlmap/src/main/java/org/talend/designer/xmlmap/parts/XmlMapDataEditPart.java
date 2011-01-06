// ============================================================================
//
// Copyright (C) 2006-2010 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.designer.xmlmap.parts;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.GridData;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.ScrollPane;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.talend.designer.xmlmap.figures.layout.EqualWidthLayout;
import org.talend.designer.xmlmap.model.emf.xmlmap.XmlMapData;

/**
 * wchen class global comment. Detailled comment
 */
public class XmlMapDataEditPart extends BaseEditPart {

    private IFigure leftFigure;

    private IFigure centerFigure;

    private IFigure rightFigure;

    @Override
    protected IFigure createFigure() {
        // Figure mainFigure = new FreeformLayer();
        // figure.setBackgroundColor(ColorConstants.red);
        // figure.setLayoutManager(new FreeformLayout());
        Figure mainFigure = new Figure();

        mainFigure.setLayoutManager(new EqualWidthLayout());
        // input
        ScrollPane scrollPane = new ScrollPane();
        leftFigure = new RectangleFigure();
        leftFigure.setBorder(new LineBorder(ColorConstants.darkBlue));
        ToolbarLayout subManager = new ToolbarLayout();
        subManager.setSpacing(20);
        subManager.setVertical(true);
        leftFigure.setLayoutManager(subManager);
        leftFigure.setBorder(new MarginBorder(5));
        scrollPane.getViewport().setContents(leftFigure);
        mainFigure.add(scrollPane);

        // var
        scrollPane = new ScrollPane();
        centerFigure = new RectangleFigure();
        centerFigure.setBorder(new LineBorder(ColorConstants.darkBlue));
        subManager = new ToolbarLayout();
        subManager.setSpacing(20);
        subManager.setVertical(true);
        centerFigure.setLayoutManager(subManager);
        centerFigure.setBorder(new MarginBorder(5));
        scrollPane.getViewport().setContents(centerFigure);
        mainFigure.add(scrollPane);

        // output
        scrollPane = new ScrollPane();
        rightFigure = new RectangleFigure();
        rightFigure.setBorder(new LineBorder(ColorConstants.darkBlue));
        subManager = new ToolbarLayout();
        subManager.setSpacing(20);
        subManager.setVertical(true);
        rightFigure.setLayoutManager(subManager);
        rightFigure.setBorder(new MarginBorder(5));
        scrollPane.getViewport().setContents(rightFigure);
        mainFigure.add(scrollPane);

        // GridLayout mainFigureLayout = new GridLayout();
        //
        // mainFigureLayout.numColumns = 2;
        // mainFigureLayout.horizontalSpacing = 300;
        // mainFigureLayout.marginWidth = 10;
        // mainFigureLayout.marginHeight = 10;
        // mainFigureLayout.verticalSpacing = 50;
        //
        // GridLayout subFigureLayout = new GridLayout();
        // Figure inputTablesFigure = new Figure();
        // inputTablesFigure.setBorder(new LineBorder(ColorConstants.darkBlue));
        // inputTablesFigure.setLayoutManager(subFigureLayout);
        // subFigureLayout.verticalSpacing = 50;
        // subFigureLayout.marginHeight = 5;
        // subFigureLayout.marginWidth = 5;
        //
        // Figure outputTablesFigure = new Figure();
        // outputTablesFigure.setLayoutManager(subFigureLayout);
        // outputTablesFigure.setBorder(new LineBorder(ColorConstants.darkBlue));
        // mainFigure.add(inputTablesFigure);
        // mainFigure.add(outputTablesFigure);
        // mainFigure.setLayoutManager(mainFigureLayout);
        // GridData inputOutputTablesFigureGD = new GridData(400, 700);
        // mainFigureLayout.setConstraint(inputTablesFigure, inputOutputTablesFigureGD);
        // mainFigureLayout.setConstraint(outputTablesFigure, inputOutputTablesFigureGD);
        mainFigure.setOpaque(true);
        mainFigure.setBackgroundColor(ColorConstants.menuBackground);
        return mainFigure;
    }

    @Override
    protected void addChildVisual(EditPart childEditPart, int index) {
        GridData everyTableGD = new GridData(380, 300);
        IFigure child = ((GraphicalEditPart) childEditPart).getFigure();
        if (childEditPart instanceof InputXmlTreeEditPart) {
            /* get first figure to put all input tables figures in */
            // GridLayout inputFigureLayout = (GridLayout) ((IFigure)
            // getContentPane().getChildren().get(0)).getLayoutManager();
            // inputFigureLayout.setConstraint(child, everyTableGD);
            // ((IFigure) getContentPane().getChildren().get(0)).add(child);
            leftFigure.add(child);
        }
        if (childEditPart instanceof OutputXmlTreeEditPart) {
            /* get second figure to put all output tables figures in */
            // GridLayout outputFigureLayout = (GridLayout) ((IFigure)
            // getContentPane().getChildren().get(1)).getLayoutManager();
            // outputFigureLayout.setConstraint(child, everyTableGD);
            // ((IFigure) getContentPane().getChildren().get(1)).add(child);
            rightFigure.add(child);
        }

        // getContentPane().add(child, index);
    }

    @Override
    protected List getModelChildren() {
        List children = new ArrayList();
        children.addAll(((XmlMapData) getModel()).getInputTrees());
        children.addAll(((XmlMapData) getModel()).getOutputTrees());
        return children;
    }

}
