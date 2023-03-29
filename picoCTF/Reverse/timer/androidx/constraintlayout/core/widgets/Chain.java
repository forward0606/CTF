package androidx.constraintlayout.core.widgets;

import androidx.constraintlayout.core.ArrayRow;
import androidx.constraintlayout.core.LinearSystem;
import androidx.constraintlayout.core.SolverVariable;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import java.util.ArrayList;
/* loaded from: classes.dex */
public class Chain {
    private static final boolean DEBUG = false;
    public static final boolean USE_CHAIN_OPTIMIZATION = false;

    public static void applyChainConstraints(ConstraintWidgetContainer constraintWidgetContainer, LinearSystem system, ArrayList<ConstraintWidget> widgets, int orientation) {
        int offset;
        int chainsSize;
        ChainHead[] chainsArray;
        if (orientation == 0) {
            offset = 0;
            chainsSize = constraintWidgetContainer.mHorizontalChainsSize;
            chainsArray = constraintWidgetContainer.mHorizontalChainsArray;
        } else {
            offset = 2;
            chainsSize = constraintWidgetContainer.mVerticalChainsSize;
            chainsArray = constraintWidgetContainer.mVerticalChainsArray;
        }
        for (int i = 0; i < chainsSize; i++) {
            ChainHead first = chainsArray[i];
            first.define();
            if (widgets == null || (widgets != null && widgets.contains(first.mFirst))) {
                applyChainConstraints(constraintWidgetContainer, system, orientation, offset, first);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:318:0x069c  */
    /* JADX WARN: Removed duplicated region for block: B:321:0x06a8  */
    /* JADX WARN: Removed duplicated region for block: B:322:0x06ad  */
    /* JADX WARN: Removed duplicated region for block: B:325:0x06b3  */
    /* JADX WARN: Removed duplicated region for block: B:326:0x06b8  */
    /* JADX WARN: Removed duplicated region for block: B:328:0x06bb  */
    /* JADX WARN: Removed duplicated region for block: B:333:0x06d3  */
    /* JADX WARN: Removed duplicated region for block: B:335:0x06d7  */
    /* JADX WARN: Removed duplicated region for block: B:336:0x06e4  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    static void applyChainConstraints(ConstraintWidgetContainer container, LinearSystem system, int orientation, int offset, ChainHead chainHead) {
        boolean isChainSpread;
        boolean done;
        ConstraintWidget widget;
        boolean isChainSpreadInside;
        boolean isChainPacked;
        ConstraintWidget widget2;
        ArrayList<ConstraintWidget> listMatchConstraints;
        ConstraintWidget head;
        ConstraintWidget lastVisibleWidget;
        SolverVariable beginTarget;
        SolverVariable endTarget;
        ConstraintAnchor begin;
        ConstraintAnchor end;
        int endPointsStrength;
        ConstraintAnchor endTarget2;
        ConstraintAnchor end2;
        ConstraintWidget widget3;
        ConstraintWidget previousVisibleWidget;
        int i;
        ConstraintWidget next;
        ConstraintWidget next2;
        ConstraintAnchor beginNextAnchor;
        SolverVariable beginNextTarget;
        SolverVariable beginNext;
        int nextMargin;
        int strength;
        ConstraintWidget next3;
        SolverVariable beginTarget2;
        ConstraintAnchor beginNextAnchor2;
        SolverVariable beginNext2;
        int nextMargin2;
        ConstraintWidget next4;
        ConstraintWidget widget4;
        ConstraintWidget previousVisibleWidget2;
        int margin1;
        int margin2;
        int strength2;
        ConstraintWidget previousVisibleWidget3;
        ConstraintAnchor end3;
        ConstraintWidget head2;
        float bias;
        float totalWeights;
        ConstraintWidget widget5;
        ArrayList<ConstraintWidget> listMatchConstraints2;
        ConstraintWidget head3;
        int margin;
        ConstraintWidget previousMatchConstraintsWidget;
        ConstraintWidget firstMatchConstraintsWidget;
        ConstraintWidget next5;
        int strength3;
        int i2 = orientation;
        ChainHead chainHead2 = chainHead;
        ConstraintWidget first = chainHead2.mFirst;
        ConstraintWidget last = chainHead2.mLast;
        ConstraintWidget firstVisibleWidget = chainHead2.mFirstVisibleWidget;
        ConstraintWidget lastVisibleWidget2 = chainHead2.mLastVisibleWidget;
        ConstraintWidget head4 = chainHead2.mHead;
        float totalWeights2 = chainHead2.mTotalWeight;
        ConstraintWidget firstMatchConstraintsWidget2 = chainHead2.mFirstMatchConstraintWidget;
        ConstraintWidget previousMatchConstraintsWidget2 = chainHead2.mLastMatchConstraintWidget;
        boolean isWrapContent = container.mListDimensionBehaviors[i2] == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
        if (i2 == 0) {
            boolean isChainSpread2 = head4.mHorizontalChainStyle == 0;
            isChainSpread = isChainSpread2;
            boolean isChainSpreadInside2 = head4.mHorizontalChainStyle == 1;
            done = false;
            widget = first;
            isChainSpreadInside = isChainSpreadInside2;
            isChainPacked = head4.mHorizontalChainStyle == 2;
        } else {
            boolean isChainSpread3 = head4.mVerticalChainStyle == 0;
            isChainSpread = isChainSpread3;
            boolean isChainSpreadInside3 = head4.mVerticalChainStyle == 1;
            done = false;
            widget = first;
            isChainSpreadInside = isChainSpreadInside3;
            isChainPacked = head4.mVerticalChainStyle == 2;
        }
        while (!done) {
            ConstraintAnchor begin2 = widget.mListAnchors[offset];
            int strength4 = 4;
            if (isChainPacked) {
                strength4 = 1;
            }
            int margin3 = begin2.getMargin();
            boolean isSpreadOnly = widget.mListDimensionBehaviors[i2] == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && widget.mResolvedMatchConstraintDefault[i2] == 0;
            if (begin2.mTarget != null && widget != first) {
                margin = margin3 + begin2.mTarget.getMargin();
            } else {
                margin = margin3;
            }
            if (isChainPacked && widget != first && widget != firstVisibleWidget) {
                strength4 = 8;
            }
            float totalWeights3 = totalWeights2;
            if (begin2.mTarget == null) {
                previousMatchConstraintsWidget = previousMatchConstraintsWidget2;
                firstMatchConstraintsWidget = firstMatchConstraintsWidget2;
            } else {
                if (widget == firstVisibleWidget) {
                    previousMatchConstraintsWidget = previousMatchConstraintsWidget2;
                    firstMatchConstraintsWidget = firstMatchConstraintsWidget2;
                    system.addGreaterThan(begin2.mSolverVariable, begin2.mTarget.mSolverVariable, margin, 6);
                } else {
                    previousMatchConstraintsWidget = previousMatchConstraintsWidget2;
                    firstMatchConstraintsWidget = firstMatchConstraintsWidget2;
                    system.addGreaterThan(begin2.mSolverVariable, begin2.mTarget.mSolverVariable, margin, 8);
                }
                if (isSpreadOnly && !isChainPacked) {
                    strength4 = 5;
                }
                if (widget == firstVisibleWidget && isChainPacked && widget.isInBarrier(i2)) {
                    strength3 = 5;
                } else {
                    strength3 = strength4;
                }
                system.addEquality(begin2.mSolverVariable, begin2.mTarget.mSolverVariable, margin, strength3);
            }
            if (isWrapContent) {
                if (widget.getVisibility() != 8 && widget.mListDimensionBehaviors[i2] == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                    system.addGreaterThan(widget.mListAnchors[offset + 1].mSolverVariable, widget.mListAnchors[offset].mSolverVariable, 0, 5);
                }
                system.addGreaterThan(widget.mListAnchors[offset].mSolverVariable, container.mListAnchors[offset].mSolverVariable, 0, 8);
            }
            ConstraintAnchor nextAnchor = widget.mListAnchors[offset + 1].mTarget;
            if (nextAnchor != null) {
                ConstraintWidget next6 = nextAnchor.mOwner;
                next5 = (next6.mListAnchors[offset].mTarget == null || next6.mListAnchors[offset].mTarget.mOwner != widget) ? null : next6;
            } else {
                next5 = null;
            }
            if (next5 != null) {
                widget = next5;
            } else {
                done = true;
            }
            totalWeights2 = totalWeights3;
            previousMatchConstraintsWidget2 = previousMatchConstraintsWidget;
            firstMatchConstraintsWidget2 = firstMatchConstraintsWidget;
        }
        float totalWeights4 = totalWeights2;
        if (lastVisibleWidget2 != null && last.mListAnchors[offset + 1].mTarget != null) {
            ConstraintAnchor end4 = lastVisibleWidget2.mListAnchors[offset + 1];
            boolean isSpreadOnly2 = lastVisibleWidget2.mListDimensionBehaviors[i2] == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && lastVisibleWidget2.mResolvedMatchConstraintDefault[i2] == 0;
            if (isSpreadOnly2 && !isChainPacked && end4.mTarget.mOwner == container) {
                system.addEquality(end4.mSolverVariable, end4.mTarget.mSolverVariable, -end4.getMargin(), 5);
            } else if (isChainPacked && end4.mTarget.mOwner == container) {
                system.addEquality(end4.mSolverVariable, end4.mTarget.mSolverVariable, -end4.getMargin(), 4);
            }
            system.addLowerThan(end4.mSolverVariable, last.mListAnchors[offset + 1].mTarget.mSolverVariable, -end4.getMargin(), 6);
        }
        if (isWrapContent) {
            system.addGreaterThan(container.mListAnchors[offset + 1].mSolverVariable, last.mListAnchors[offset + 1].mSolverVariable, last.mListAnchors[offset + 1].getMargin(), 8);
        }
        ArrayList<ConstraintWidget> listMatchConstraints3 = chainHead2.mWeightedMatchConstraintsWidgets;
        if (listMatchConstraints3 == null) {
            widget2 = widget;
            listMatchConstraints = listMatchConstraints3;
            head = head4;
        } else {
            int count = listMatchConstraints3.size();
            if (count > 1) {
                ConstraintWidget lastMatch = null;
                float lastWeight = 0.0f;
                if (chainHead2.mHasUndefinedWeights && !chainHead2.mHasComplexMatchWeights) {
                    totalWeights = chainHead2.mWidgetsMatchCount;
                } else {
                    totalWeights = totalWeights4;
                }
                int i3 = 0;
                while (i3 < count) {
                    ConstraintWidget match = listMatchConstraints3.get(i3);
                    int count2 = count;
                    float currentWeight = match.mWeight[i2];
                    if (currentWeight >= 0.0f) {
                        widget5 = widget;
                        listMatchConstraints2 = listMatchConstraints3;
                    } else if (chainHead2.mHasComplexMatchWeights) {
                        widget5 = widget;
                        listMatchConstraints2 = listMatchConstraints3;
                        system.addEquality(match.mListAnchors[offset + 1].mSolverVariable, match.mListAnchors[offset].mSolverVariable, 0, 4);
                        head3 = head4;
                        i3++;
                        chainHead2 = chainHead;
                        head4 = head3;
                        count = count2;
                        widget = widget5;
                        listMatchConstraints3 = listMatchConstraints2;
                    } else {
                        widget5 = widget;
                        listMatchConstraints2 = listMatchConstraints3;
                        currentWeight = 1.0f;
                    }
                    if (currentWeight == 0.0f) {
                        head3 = head4;
                        system.addEquality(match.mListAnchors[offset + 1].mSolverVariable, match.mListAnchors[offset].mSolverVariable, 0, 8);
                    } else {
                        head3 = head4;
                        if (lastMatch != null) {
                            SolverVariable begin3 = lastMatch.mListAnchors[offset].mSolverVariable;
                            SolverVariable end5 = lastMatch.mListAnchors[offset + 1].mSolverVariable;
                            SolverVariable nextBegin = match.mListAnchors[offset].mSolverVariable;
                            SolverVariable nextEnd = match.mListAnchors[offset + 1].mSolverVariable;
                            ArrayRow row = system.createRow();
                            row.createRowEqualMatchDimensions(lastWeight, totalWeights, currentWeight, begin3, end5, nextBegin, nextEnd);
                            system.addConstraint(row);
                        }
                        lastMatch = match;
                        lastWeight = currentWeight;
                    }
                    i3++;
                    chainHead2 = chainHead;
                    head4 = head3;
                    count = count2;
                    widget = widget5;
                    listMatchConstraints3 = listMatchConstraints2;
                }
                widget2 = widget;
                listMatchConstraints = listMatchConstraints3;
                head = head4;
            } else {
                widget2 = widget;
                listMatchConstraints = listMatchConstraints3;
                head = head4;
            }
        }
        if (firstVisibleWidget == null) {
            lastVisibleWidget = lastVisibleWidget2;
        } else if (firstVisibleWidget == lastVisibleWidget2 || isChainPacked) {
            ConstraintAnchor begin4 = first.mListAnchors[offset];
            ConstraintAnchor end6 = last.mListAnchors[offset + 1];
            SolverVariable beginTarget3 = begin4.mTarget != null ? begin4.mTarget.mSolverVariable : null;
            SolverVariable endTarget3 = end6.mTarget != null ? end6.mTarget.mSolverVariable : null;
            ConstraintAnchor begin5 = firstVisibleWidget.mListAnchors[offset];
            if (lastVisibleWidget2 == null) {
                end3 = end6;
            } else {
                end3 = lastVisibleWidget2.mListAnchors[offset + 1];
            }
            if (beginTarget3 == null || endTarget3 == null) {
                lastVisibleWidget = lastVisibleWidget2;
            } else {
                if (i2 == 0) {
                    head2 = head;
                    float bias2 = head2.mHorizontalBiasPercent;
                    bias = bias2;
                } else {
                    head2 = head;
                    float bias3 = head2.mVerticalBiasPercent;
                    bias = bias3;
                }
                int beginMargin = begin5.getMargin();
                int endMargin = end3.getMargin();
                lastVisibleWidget = lastVisibleWidget2;
                system.addCentering(begin5.mSolverVariable, beginTarget3, beginMargin, bias, endTarget3, end3.mSolverVariable, endMargin, 7);
            }
            if ((!isChainSpread || isChainSpreadInside) && firstVisibleWidget != null && firstVisibleWidget != lastVisibleWidget) {
                ConstraintAnchor begin6 = firstVisibleWidget.mListAnchors[offset];
                if (lastVisibleWidget == null) {
                    lastVisibleWidget = firstVisibleWidget;
                }
                ConstraintAnchor end7 = lastVisibleWidget.mListAnchors[offset + 1];
                beginTarget = begin6.mTarget == null ? begin6.mTarget.mSolverVariable : null;
                SolverVariable endTarget4 = end7.mTarget == null ? end7.mTarget.mSolverVariable : null;
                if (last != lastVisibleWidget) {
                    endTarget = endTarget4;
                } else {
                    ConstraintAnchor realEnd = last.mListAnchors[offset + 1];
                    SolverVariable endTarget5 = realEnd.mTarget != null ? realEnd.mTarget.mSolverVariable : null;
                    endTarget = endTarget5;
                }
                if (firstVisibleWidget == lastVisibleWidget) {
                    begin = begin6;
                    end = end7;
                } else {
                    begin = firstVisibleWidget.mListAnchors[offset];
                    end = firstVisibleWidget.mListAnchors[offset + 1];
                }
                if (beginTarget == null && endTarget != null) {
                    int beginMargin2 = begin.getMargin();
                    int endMargin2 = lastVisibleWidget.mListAnchors[offset + 1].getMargin();
                    system.addCentering(begin.mSolverVariable, beginTarget, beginMargin2, 0.5f, endTarget, end.mSolverVariable, endMargin2, 5);
                }
            }
        } else {
            lastVisibleWidget = lastVisibleWidget2;
        }
        if (isChainSpread && firstVisibleWidget != null) {
            boolean applyFixedEquality = chainHead.mWidgetsMatchCount > 0 && chainHead.mWidgetsCount == chainHead.mWidgetsMatchCount;
            ConstraintWidget widget6 = firstVisibleWidget;
            ConstraintWidget previousVisibleWidget4 = firstVisibleWidget;
            while (widget6 != null) {
                ConstraintWidget next7 = widget6.mNextChainWidget[i2];
                while (next7 != null && next7.getVisibility() == 8) {
                    next7 = next7.mNextChainWidget[i2];
                }
                if (next7 != null || widget6 == lastVisibleWidget) {
                    ConstraintAnchor beginAnchor = widget6.mListAnchors[offset];
                    SolverVariable begin7 = beginAnchor.mSolverVariable;
                    SolverVariable beginTarget4 = beginAnchor.mTarget != null ? beginAnchor.mTarget.mSolverVariable : null;
                    if (previousVisibleWidget4 != widget6) {
                        beginTarget2 = previousVisibleWidget4.mListAnchors[offset + 1].mSolverVariable;
                    } else if (widget6 != firstVisibleWidget) {
                        beginTarget2 = beginTarget4;
                    } else {
                        beginTarget2 = first.mListAnchors[offset].mTarget != null ? first.mListAnchors[offset].mTarget.mSolverVariable : null;
                    }
                    int beginMargin3 = beginAnchor.getMargin();
                    int nextMargin3 = widget6.mListAnchors[offset + 1].getMargin();
                    if (next7 != null) {
                        ConstraintAnchor beginNextAnchor3 = next7.mListAnchors[offset];
                        SolverVariable beginNext3 = beginNextAnchor3.mSolverVariable;
                        beginNextAnchor2 = beginNextAnchor3;
                        beginNext2 = beginNext3;
                    } else {
                        ConstraintAnchor beginNextAnchor4 = last.mListAnchors[offset + 1].mTarget;
                        if (beginNextAnchor4 == null) {
                            beginNextAnchor2 = beginNextAnchor4;
                            beginNext2 = null;
                        } else {
                            SolverVariable beginNext4 = beginNextAnchor4.mSolverVariable;
                            beginNextAnchor2 = beginNextAnchor4;
                            beginNext2 = beginNext4;
                        }
                    }
                    SolverVariable beginNextTarget2 = widget6.mListAnchors[offset + 1].mSolverVariable;
                    if (beginNextAnchor2 == null) {
                        nextMargin2 = nextMargin3;
                    } else {
                        nextMargin2 = nextMargin3 + beginNextAnchor2.getMargin();
                    }
                    int beginMargin4 = beginMargin3 + previousVisibleWidget4.mListAnchors[offset + 1].getMargin();
                    if (begin7 == null || beginTarget2 == null || beginNext2 == null || beginNextTarget2 == null) {
                        next4 = next7;
                        widget4 = widget6;
                        previousVisibleWidget2 = previousVisibleWidget4;
                    } else {
                        if (widget6 != firstVisibleWidget) {
                            margin1 = beginMargin4;
                        } else {
                            int margin12 = firstVisibleWidget.mListAnchors[offset].getMargin();
                            margin1 = margin12;
                        }
                        int margin13 = nextMargin2;
                        if (widget6 != lastVisibleWidget) {
                            margin2 = margin13;
                        } else {
                            int margin22 = lastVisibleWidget.mListAnchors[offset + 1].getMargin();
                            margin2 = margin22;
                        }
                        if (!applyFixedEquality) {
                            strength2 = 5;
                        } else {
                            strength2 = 8;
                        }
                        next4 = next7;
                        widget4 = widget6;
                        previousVisibleWidget2 = previousVisibleWidget4;
                        system.addCentering(begin7, beginTarget2, margin1, 0.5f, beginNext2, beginNextTarget2, margin2, strength2);
                    }
                } else {
                    next4 = next7;
                    widget4 = widget6;
                    previousVisibleWidget2 = previousVisibleWidget4;
                }
                if (widget4.getVisibility() == 8) {
                    previousVisibleWidget3 = previousVisibleWidget2;
                } else {
                    previousVisibleWidget3 = widget4;
                }
                widget6 = next4;
                previousVisibleWidget4 = previousVisibleWidget3;
            }
        } else {
            int i4 = 8;
            if (isChainSpreadInside && firstVisibleWidget != null) {
                boolean applyFixedEquality2 = chainHead.mWidgetsMatchCount > 0 && chainHead.mWidgetsCount == chainHead.mWidgetsMatchCount;
                ConstraintWidget widget7 = firstVisibleWidget;
                ConstraintWidget previousVisibleWidget5 = firstVisibleWidget;
                while (widget7 != null) {
                    ConstraintWidget next8 = widget7.mNextChainWidget[i2];
                    while (next8 != null && next8.getVisibility() == i4) {
                        next8 = next8.mNextChainWidget[i2];
                    }
                    if (widget7 == firstVisibleWidget || widget7 == lastVisibleWidget || next8 == null) {
                        widget3 = widget7;
                        previousVisibleWidget = previousVisibleWidget5;
                        i = 8;
                        next = next8;
                    } else {
                        if (next8 != lastVisibleWidget) {
                            next2 = next8;
                        } else {
                            next2 = null;
                        }
                        ConstraintAnchor beginAnchor2 = widget7.mListAnchors[offset];
                        SolverVariable begin8 = beginAnchor2.mSolverVariable;
                        if (beginAnchor2.mTarget != null) {
                            SolverVariable solverVariable = beginAnchor2.mTarget.mSolverVariable;
                        }
                        SolverVariable beginTarget5 = previousVisibleWidget5.mListAnchors[offset + 1].mSolverVariable;
                        SolverVariable beginNext5 = null;
                        int beginMargin5 = beginAnchor2.getMargin();
                        int nextMargin4 = widget7.mListAnchors[offset + 1].getMargin();
                        if (next2 != null) {
                            ConstraintAnchor beginNextAnchor5 = next2.mListAnchors[offset];
                            SolverVariable beginNext6 = beginNextAnchor5.mSolverVariable;
                            beginNextTarget = beginNextAnchor5.mTarget != null ? beginNextAnchor5.mTarget.mSolverVariable : null;
                            beginNext = beginNext6;
                            beginNextAnchor = beginNextAnchor5;
                        } else {
                            ConstraintAnchor beginNextAnchor6 = lastVisibleWidget.mListAnchors[offset];
                            if (beginNextAnchor6 != null) {
                                beginNext5 = beginNextAnchor6.mSolverVariable;
                            }
                            beginNextAnchor = beginNextAnchor6;
                            beginNextTarget = widget7.mListAnchors[offset + 1].mSolverVariable;
                            beginNext = beginNext5;
                        }
                        if (beginNextAnchor == null) {
                            nextMargin = nextMargin4;
                        } else {
                            nextMargin = nextMargin4 + beginNextAnchor.getMargin();
                        }
                        int beginMargin6 = beginMargin5 + previousVisibleWidget5.mListAnchors[offset + 1].getMargin();
                        if (!applyFixedEquality2) {
                            strength = 4;
                        } else {
                            strength = 8;
                        }
                        if (begin8 == null || beginTarget5 == null || beginNext == null || beginNextTarget == null) {
                            next3 = next2;
                            widget3 = widget7;
                            previousVisibleWidget = previousVisibleWidget5;
                            i = 8;
                        } else {
                            next3 = next2;
                            widget3 = widget7;
                            previousVisibleWidget = previousVisibleWidget5;
                            i = 8;
                            system.addCentering(begin8, beginTarget5, beginMargin6, 0.5f, beginNext, beginNextTarget, nextMargin, strength);
                        }
                        next = next3;
                    }
                    if (widget3.getVisibility() == i) {
                        previousVisibleWidget5 = previousVisibleWidget;
                    } else {
                        previousVisibleWidget5 = widget3;
                    }
                    widget7 = next;
                    i2 = orientation;
                    i4 = 8;
                }
                ConstraintAnchor begin9 = firstVisibleWidget.mListAnchors[offset];
                ConstraintAnchor beginTarget6 = first.mListAnchors[offset].mTarget;
                ConstraintAnchor end8 = lastVisibleWidget.mListAnchors[offset + 1];
                ConstraintAnchor endTarget6 = last.mListAnchors[offset + 1].mTarget;
                if (beginTarget6 == null) {
                    endPointsStrength = 5;
                    endTarget2 = endTarget6;
                    end2 = end8;
                } else if (firstVisibleWidget != lastVisibleWidget) {
                    system.addEquality(begin9.mSolverVariable, beginTarget6.mSolverVariable, begin9.getMargin(), 5);
                    endPointsStrength = 5;
                    endTarget2 = endTarget6;
                    end2 = end8;
                } else if (endTarget6 != null) {
                    endPointsStrength = 5;
                    endTarget2 = endTarget6;
                    end2 = end8;
                    system.addCentering(begin9.mSolverVariable, beginTarget6.mSolverVariable, begin9.getMargin(), 0.5f, end8.mSolverVariable, endTarget6.mSolverVariable, end8.getMargin(), 5);
                } else {
                    endPointsStrength = 5;
                    endTarget2 = endTarget6;
                    end2 = end8;
                }
                ConstraintAnchor endTarget7 = endTarget2;
                if (endTarget7 != null && firstVisibleWidget != lastVisibleWidget) {
                    ConstraintAnchor end9 = end2;
                    system.addEquality(end9.mSolverVariable, endTarget7.mSolverVariable, -end9.getMargin(), endPointsStrength);
                }
            }
        }
        if (!isChainSpread) {
        }
        ConstraintAnchor begin62 = firstVisibleWidget.mListAnchors[offset];
        if (lastVisibleWidget == null) {
        }
        ConstraintAnchor end72 = lastVisibleWidget.mListAnchors[offset + 1];
        beginTarget = begin62.mTarget == null ? begin62.mTarget.mSolverVariable : null;
        if (end72.mTarget == null) {
        }
        if (last != lastVisibleWidget) {
        }
        if (firstVisibleWidget == lastVisibleWidget) {
        }
        if (beginTarget == null) {
        }
    }
}
