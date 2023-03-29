package androidx.fragment.app;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import androidx.collection.ArrayMap;
import androidx.core.app.SharedElementCallback;
import androidx.core.os.CancellationSignal;
import androidx.core.util.Preconditions;
import androidx.core.view.OneShotPreDrawListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewGroupCompat;
import androidx.fragment.app.FragmentAnim;
import androidx.fragment.app.SpecialEffectsController;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
/* loaded from: classes.dex */
class DefaultSpecialEffectsController extends SpecialEffectsController {
    /* JADX INFO: Access modifiers changed from: package-private */
    public DefaultSpecialEffectsController(ViewGroup container) {
        super(container);
    }

    @Override // androidx.fragment.app.SpecialEffectsController
    void executeOperations(List<SpecialEffectsController.Operation> operations, boolean isPop) {
        SpecialEffectsController.Operation firstOut = null;
        SpecialEffectsController.Operation lastIn = null;
        for (SpecialEffectsController.Operation operation : operations) {
            SpecialEffectsController.Operation.State currentState = SpecialEffectsController.Operation.State.from(operation.getFragment().mView);
            switch (AnonymousClass10.$SwitchMap$androidx$fragment$app$SpecialEffectsController$Operation$State[operation.getFinalState().ordinal()]) {
                case 1:
                case 2:
                case 3:
                    if (currentState == SpecialEffectsController.Operation.State.VISIBLE && firstOut == null) {
                        firstOut = operation;
                        break;
                    }
                    break;
                case 4:
                    if (currentState != SpecialEffectsController.Operation.State.VISIBLE) {
                        lastIn = operation;
                        break;
                    } else {
                        break;
                    }
            }
        }
        List<AnimationInfo> animations = new ArrayList<>();
        List<TransitionInfo> transitions = new ArrayList<>();
        final List<SpecialEffectsController.Operation> awaitingContainerChanges = new ArrayList<>(operations);
        Iterator<SpecialEffectsController.Operation> it = operations.iterator();
        while (true) {
            boolean z = true;
            if (it.hasNext()) {
                final SpecialEffectsController.Operation operation2 = it.next();
                CancellationSignal animCancellationSignal = new CancellationSignal();
                operation2.markStartedSpecialEffect(animCancellationSignal);
                animations.add(new AnimationInfo(operation2, animCancellationSignal, isPop));
                CancellationSignal transitionCancellationSignal = new CancellationSignal();
                operation2.markStartedSpecialEffect(transitionCancellationSignal);
                if (isPop) {
                    if (operation2 == firstOut) {
                        transitions.add(new TransitionInfo(operation2, transitionCancellationSignal, isPop, z));
                        operation2.addCompletionListener(new Runnable() { // from class: androidx.fragment.app.DefaultSpecialEffectsController.1
                            @Override // java.lang.Runnable
                            public void run() {
                                if (awaitingContainerChanges.contains(operation2)) {
                                    awaitingContainerChanges.remove(operation2);
                                    DefaultSpecialEffectsController.this.applyContainerChanges(operation2);
                                }
                            }
                        });
                    }
                    z = false;
                    transitions.add(new TransitionInfo(operation2, transitionCancellationSignal, isPop, z));
                    operation2.addCompletionListener(new Runnable() { // from class: androidx.fragment.app.DefaultSpecialEffectsController.1
                        @Override // java.lang.Runnable
                        public void run() {
                            if (awaitingContainerChanges.contains(operation2)) {
                                awaitingContainerChanges.remove(operation2);
                                DefaultSpecialEffectsController.this.applyContainerChanges(operation2);
                            }
                        }
                    });
                } else {
                    if (operation2 == lastIn) {
                        transitions.add(new TransitionInfo(operation2, transitionCancellationSignal, isPop, z));
                        operation2.addCompletionListener(new Runnable() { // from class: androidx.fragment.app.DefaultSpecialEffectsController.1
                            @Override // java.lang.Runnable
                            public void run() {
                                if (awaitingContainerChanges.contains(operation2)) {
                                    awaitingContainerChanges.remove(operation2);
                                    DefaultSpecialEffectsController.this.applyContainerChanges(operation2);
                                }
                            }
                        });
                    }
                    z = false;
                    transitions.add(new TransitionInfo(operation2, transitionCancellationSignal, isPop, z));
                    operation2.addCompletionListener(new Runnable() { // from class: androidx.fragment.app.DefaultSpecialEffectsController.1
                        @Override // java.lang.Runnable
                        public void run() {
                            if (awaitingContainerChanges.contains(operation2)) {
                                awaitingContainerChanges.remove(operation2);
                                DefaultSpecialEffectsController.this.applyContainerChanges(operation2);
                            }
                        }
                    });
                }
            } else {
                Map<SpecialEffectsController.Operation, Boolean> startedTransitions = startTransitions(transitions, awaitingContainerChanges, isPop, firstOut, lastIn);
                boolean startedAnyTransition = startedTransitions.containsValue(true);
                startAnimations(animations, awaitingContainerChanges, startedAnyTransition, startedTransitions);
                for (SpecialEffectsController.Operation operation3 : awaitingContainerChanges) {
                    applyContainerChanges(operation3);
                }
                awaitingContainerChanges.clear();
                return;
            }
        }
    }

    /* renamed from: androidx.fragment.app.DefaultSpecialEffectsController$10  reason: invalid class name */
    /* loaded from: classes.dex */
    static /* synthetic */ class AnonymousClass10 {
        static final /* synthetic */ int[] $SwitchMap$androidx$fragment$app$SpecialEffectsController$Operation$State;

        static {
            int[] iArr = new int[SpecialEffectsController.Operation.State.values().length];
            $SwitchMap$androidx$fragment$app$SpecialEffectsController$Operation$State = iArr;
            try {
                iArr[SpecialEffectsController.Operation.State.GONE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$androidx$fragment$app$SpecialEffectsController$Operation$State[SpecialEffectsController.Operation.State.INVISIBLE.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$androidx$fragment$app$SpecialEffectsController$Operation$State[SpecialEffectsController.Operation.State.REMOVED.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$androidx$fragment$app$SpecialEffectsController$Operation$State[SpecialEffectsController.Operation.State.VISIBLE.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    private void startAnimations(List<AnimationInfo> animationInfos, List<SpecialEffectsController.Operation> awaitingContainerChanges, boolean startedAnyTransition, Map<SpecialEffectsController.Operation, Boolean> startedTransitions) {
        final ViewGroup container = getContainer();
        Context context = container.getContext();
        ArrayList<AnimationInfo> animationsToRun = new ArrayList<>();
        View viewToAnimate = null;
        Iterator<AnimationInfo> it = animationInfos.iterator();
        while (it.hasNext()) {
            final AnimationInfo animationInfo = it.next();
            if (animationInfo.isVisibilityUnchanged()) {
                animationInfo.completeSpecialEffect();
            } else {
                FragmentAnim.AnimationOrAnimator anim = animationInfo.getAnimation(context);
                if (anim == null) {
                    animationInfo.completeSpecialEffect();
                } else {
                    final Animator animator = anim.animator;
                    if (animator == null) {
                        animationsToRun.add(animationInfo);
                    } else {
                        final SpecialEffectsController.Operation operation = animationInfo.getOperation();
                        Fragment fragment = operation.getFragment();
                        boolean startedTransition = Boolean.TRUE.equals(startedTransitions.get(operation));
                        if (startedTransition) {
                            if (FragmentManager.isLoggingEnabled(2)) {
                                Log.v("FragmentManager", "Ignoring Animator set on " + fragment + " as this Fragment was involved in a Transition.");
                            }
                            animationInfo.completeSpecialEffect();
                        } else {
                            final boolean isHideOperation = operation.getFinalState() == SpecialEffectsController.Operation.State.GONE;
                            if (isHideOperation) {
                                awaitingContainerChanges.remove(operation);
                            }
                            final View viewToAnimate2 = fragment.mView;
                            container.startViewTransition(viewToAnimate2);
                            animator.addListener(new AnimatorListenerAdapter() { // from class: androidx.fragment.app.DefaultSpecialEffectsController.2
                                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                                public void onAnimationEnd(Animator anim2) {
                                    container.endViewTransition(viewToAnimate2);
                                    if (isHideOperation) {
                                        operation.getFinalState().applyState(viewToAnimate2);
                                    }
                                    animationInfo.completeSpecialEffect();
                                }
                            });
                            animator.setTarget(viewToAnimate2);
                            animator.start();
                            CancellationSignal signal = animationInfo.getSignal();
                            signal.setOnCancelListener(new CancellationSignal.OnCancelListener() { // from class: androidx.fragment.app.DefaultSpecialEffectsController.3
                                @Override // androidx.core.os.CancellationSignal.OnCancelListener
                                public void onCancel() {
                                    animator.end();
                                }
                            });
                            viewToAnimate = 1;
                            it = it;
                        }
                    }
                }
            }
        }
        Iterator<AnimationInfo> it2 = animationsToRun.iterator();
        while (it2.hasNext()) {
            final AnimationInfo animationInfo2 = it2.next();
            SpecialEffectsController.Operation operation2 = animationInfo2.getOperation();
            Fragment fragment2 = operation2.getFragment();
            if (startedAnyTransition) {
                if (FragmentManager.isLoggingEnabled(2)) {
                    Log.v("FragmentManager", "Ignoring Animation set on " + fragment2 + " as Animations cannot run alongside Transitions.");
                }
                animationInfo2.completeSpecialEffect();
            } else if (viewToAnimate != null) {
                if (FragmentManager.isLoggingEnabled(2)) {
                    Log.v("FragmentManager", "Ignoring Animation set on " + fragment2 + " as Animations cannot run alongside Animators.");
                }
                animationInfo2.completeSpecialEffect();
            } else {
                final View viewToAnimate3 = fragment2.mView;
                Animation anim2 = (Animation) Preconditions.checkNotNull(((FragmentAnim.AnimationOrAnimator) Preconditions.checkNotNull(animationInfo2.getAnimation(context))).animation);
                SpecialEffectsController.Operation.State finalState = operation2.getFinalState();
                if (finalState != SpecialEffectsController.Operation.State.REMOVED) {
                    viewToAnimate3.startAnimation(anim2);
                    animationInfo2.completeSpecialEffect();
                } else {
                    container.startViewTransition(viewToAnimate3);
                    Animation animation = new FragmentAnim.EndViewTransitionAnimation(anim2, container, viewToAnimate3);
                    animation.setAnimationListener(new Animation.AnimationListener() { // from class: androidx.fragment.app.DefaultSpecialEffectsController.4
                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationStart(Animation animation2) {
                        }

                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationEnd(Animation animation2) {
                            container.post(new Runnable() { // from class: androidx.fragment.app.DefaultSpecialEffectsController.4.1
                                @Override // java.lang.Runnable
                                public void run() {
                                    container.endViewTransition(viewToAnimate3);
                                    animationInfo2.completeSpecialEffect();
                                }
                            });
                        }

                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationRepeat(Animation animation2) {
                        }
                    });
                    viewToAnimate3.startAnimation(animation);
                }
                CancellationSignal signal2 = animationInfo2.getSignal();
                signal2.setOnCancelListener(new CancellationSignal.OnCancelListener() { // from class: androidx.fragment.app.DefaultSpecialEffectsController.5
                    @Override // androidx.core.os.CancellationSignal.OnCancelListener
                    public void onCancel() {
                        viewToAnimate3.clearAnimation();
                        container.endViewTransition(viewToAnimate3);
                        animationInfo2.completeSpecialEffect();
                    }
                });
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:148:0x050f, code lost:
        if (r11 == r43) goto L196;
     */
    /* JADX WARN: Removed duplicated region for block: B:160:0x0531  */
    /* JADX WARN: Removed duplicated region for block: B:165:0x0564  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private Map<SpecialEffectsController.Operation, Boolean> startTransitions(List<TransitionInfo> transitionInfos, List<SpecialEffectsController.Operation> awaitingContainerChanges, final boolean isPop, final SpecialEffectsController.Operation firstOut, final SpecialEffectsController.Operation lastIn) {
        Object mergedNonOverlappingTransition;
        boolean involvedInSharedElementTransition;
        Iterator<TransitionInfo> it;
        Iterator<TransitionInfo> it2;
        ArrayList<View> sharedElementLastInViews;
        View nonExistentView;
        Map<SpecialEffectsController.Operation, Boolean> startedTransitions;
        ArrayList<View> sharedElementFirstOutViews;
        SpecialEffectsController.Operation operation;
        FragmentTransitionImpl transitionImpl;
        Rect lastInEpicenterRect;
        View firstOutEpicenterView;
        ArrayMap<String, String> sharedElementNameMapping;
        ArrayList<View> sharedElementLastInViews2;
        Map<SpecialEffectsController.Operation, Boolean> startedTransitions2;
        ArrayList<View> sharedElementFirstOutViews2;
        Rect lastInEpicenterRect2;
        View nonExistentView2;
        FragmentTransitionImpl transitionImpl2;
        SpecialEffectsController.Operation operation2;
        SpecialEffectsController.Operation operation3;
        View firstOutEpicenterView2;
        SharedElementCallback exitingCallback;
        SharedElementCallback exitingCallback2;
        ArrayList<String> exitingNames;
        SharedElementCallback exitingCallback3;
        Object enteringCallback;
        SharedElementCallback enteringCallback2;
        String key;
        ArrayList<String> exitingNames2;
        SharedElementCallback exitingCallback4;
        boolean z = isPop;
        SpecialEffectsController.Operation operation4 = firstOut;
        SpecialEffectsController.Operation operation5 = lastIn;
        Map<SpecialEffectsController.Operation, Boolean> startedTransitions3 = new HashMap<>();
        FragmentTransitionImpl transitionImpl3 = null;
        for (TransitionInfo transitionInfo : transitionInfos) {
            if (!transitionInfo.isVisibilityUnchanged()) {
                FragmentTransitionImpl handlingImpl = transitionInfo.getHandlingImpl();
                if (transitionImpl3 == null) {
                    transitionImpl3 = handlingImpl;
                } else if (handlingImpl != null && transitionImpl3 != handlingImpl) {
                    throw new IllegalArgumentException("Mixing framework transitions and AndroidX transitions is not allowed. Fragment " + transitionInfo.getOperation().getFragment() + " returned Transition " + transitionInfo.getTransition() + " which uses a different Transition  type than other Fragments.");
                }
            }
        }
        if (transitionImpl3 == null) {
            for (TransitionInfo transitionInfo2 : transitionInfos) {
                startedTransitions3.put(transitionInfo2.getOperation(), false);
                transitionInfo2.completeSpecialEffect();
            }
            return startedTransitions3;
        }
        View nonExistentView3 = new View(getContainer().getContext());
        Object sharedElementTransition = null;
        final Rect lastInEpicenterRect3 = new Rect();
        ArrayList<View> sharedElementFirstOutViews3 = new ArrayList<>();
        ArrayList<View> sharedElementLastInViews3 = new ArrayList<>();
        ArrayMap<String, String> sharedElementNameMapping2 = new ArrayMap<>();
        View firstOutEpicenterView3 = null;
        boolean hasLastInEpicenter = false;
        for (TransitionInfo transitionInfo3 : transitionInfos) {
            boolean hasSharedElementTransition = transitionInfo3.hasSharedElementTransition();
            if (hasSharedElementTransition && operation4 != null && operation5 != null) {
                Object sharedElementTransition2 = transitionImpl3.wrapTransitionInSet(transitionImpl3.cloneTransition(transitionInfo3.getSharedElementTransition()));
                ArrayList<String> exitingNames3 = lastIn.getFragment().getSharedElementSourceNames();
                ArrayList<String> firstOutSourceNames = firstOut.getFragment().getSharedElementSourceNames();
                ArrayList<String> firstOutTargetNames = firstOut.getFragment().getSharedElementTargetNames();
                int index = 0;
                while (true) {
                    firstOutEpicenterView2 = firstOutEpicenterView3;
                    if (index >= firstOutTargetNames.size()) {
                        break;
                    }
                    int nameIndex = exitingNames3.indexOf(firstOutTargetNames.get(index));
                    ArrayList<String> firstOutTargetNames2 = firstOutTargetNames;
                    if (nameIndex != -1) {
                        exitingNames3.set(nameIndex, firstOutSourceNames.get(index));
                    }
                    index++;
                    firstOutEpicenterView3 = firstOutEpicenterView2;
                    firstOutTargetNames = firstOutTargetNames2;
                }
                ArrayList<String> enteringNames = lastIn.getFragment().getSharedElementTargetNames();
                if (!z) {
                    SharedElementCallback exitingCallback5 = firstOut.getFragment().getExitTransitionCallback();
                    exitingCallback = exitingCallback5;
                    exitingCallback2 = lastIn.getFragment().getEnterTransitionCallback();
                } else {
                    SharedElementCallback exitingCallback6 = firstOut.getFragment().getEnterTransitionCallback();
                    exitingCallback = exitingCallback6;
                    exitingCallback2 = lastIn.getFragment().getExitTransitionCallback();
                }
                int numSharedElements = exitingNames3.size();
                int i = 0;
                while (i < numSharedElements) {
                    int numSharedElements2 = numSharedElements;
                    String exitingName = exitingNames3.get(i);
                    String enteringName = enteringNames.get(i);
                    sharedElementNameMapping2.put(exitingName, enteringName);
                    i++;
                    numSharedElements = numSharedElements2;
                }
                ArrayMap<String, View> firstOutViews = new ArrayMap<>();
                findNamedViews(firstOutViews, firstOut.getFragment().mView);
                firstOutViews.retainAll(exitingNames3);
                if (exitingCallback != null) {
                    exitingCallback.onMapSharedElements(exitingNames3, firstOutViews);
                    int i2 = exitingNames3.size() - 1;
                    while (i2 >= 0) {
                        String name = exitingNames3.get(i2);
                        View view = firstOutViews.get(name);
                        if (view == null) {
                            sharedElementNameMapping2.remove(name);
                            exitingNames2 = exitingNames3;
                            exitingCallback4 = exitingCallback;
                        } else {
                            exitingNames2 = exitingNames3;
                            if (name.equals(ViewCompat.getTransitionName(view))) {
                                exitingCallback4 = exitingCallback;
                            } else {
                                String targetValue = sharedElementNameMapping2.remove(name);
                                exitingCallback4 = exitingCallback;
                                sharedElementNameMapping2.put(ViewCompat.getTransitionName(view), targetValue);
                            }
                        }
                        i2--;
                        exitingNames3 = exitingNames2;
                        exitingCallback = exitingCallback4;
                    }
                    exitingNames = exitingNames3;
                    exitingCallback3 = exitingCallback;
                } else {
                    exitingNames = exitingNames3;
                    exitingCallback3 = exitingCallback;
                    sharedElementNameMapping2.retainAll(firstOutViews.keySet());
                }
                final ArrayMap<String, View> lastInViews = new ArrayMap<>();
                findNamedViews(lastInViews, lastIn.getFragment().mView);
                lastInViews.retainAll(enteringNames);
                lastInViews.retainAll(sharedElementNameMapping2.values());
                if (exitingCallback2 != null) {
                    exitingCallback2.onMapSharedElements(enteringNames, lastInViews);
                    int i3 = enteringNames.size() - 1;
                    while (i3 >= 0) {
                        String name2 = enteringNames.get(i3);
                        View view2 = lastInViews.get(name2);
                        if (view2 == null) {
                            enteringCallback2 = exitingCallback2;
                            String key2 = FragmentTransition.findKeyForValue(sharedElementNameMapping2, name2);
                            if (key2 != null) {
                                sharedElementNameMapping2.remove(key2);
                            }
                        } else {
                            enteringCallback2 = exitingCallback2;
                            if (!name2.equals(ViewCompat.getTransitionName(view2)) && (key = FragmentTransition.findKeyForValue(sharedElementNameMapping2, name2)) != null) {
                                sharedElementNameMapping2.put(key, ViewCompat.getTransitionName(view2));
                            }
                        }
                        i3--;
                        exitingCallback2 = enteringCallback2;
                    }
                    enteringCallback = exitingCallback2;
                } else {
                    enteringCallback = exitingCallback2;
                    FragmentTransition.retainValues(sharedElementNameMapping2, lastInViews);
                }
                retainMatchingViews(firstOutViews, sharedElementNameMapping2.keySet());
                retainMatchingViews(lastInViews, sharedElementNameMapping2.values());
                if (sharedElementNameMapping2.isEmpty()) {
                    sharedElementTransition = null;
                    sharedElementFirstOutViews3.clear();
                    sharedElementLastInViews3.clear();
                    sharedElementNameMapping = sharedElementNameMapping2;
                    sharedElementLastInViews2 = sharedElementLastInViews3;
                    startedTransitions2 = startedTransitions3;
                    sharedElementFirstOutViews2 = sharedElementFirstOutViews3;
                    lastInEpicenterRect2 = lastInEpicenterRect3;
                    nonExistentView2 = nonExistentView3;
                    transitionImpl2 = transitionImpl3;
                    firstOutEpicenterView3 = firstOutEpicenterView2;
                    operation2 = firstOut;
                    operation3 = lastIn;
                } else {
                    FragmentTransition.callSharedElementStartEnd(lastIn.getFragment(), firstOut.getFragment(), z, firstOutViews, true);
                    ArrayList<String> exitingNames4 = exitingNames;
                    Map<SpecialEffectsController.Operation, Boolean> startedTransitions4 = startedTransitions3;
                    sharedElementNameMapping = sharedElementNameMapping2;
                    View nonExistentView4 = nonExistentView3;
                    ArrayList<View> sharedElementLastInViews4 = sharedElementLastInViews3;
                    OneShotPreDrawListener.add(getContainer(), new Runnable() { // from class: androidx.fragment.app.DefaultSpecialEffectsController.6
                        @Override // java.lang.Runnable
                        public void run() {
                            FragmentTransition.callSharedElementStartEnd(lastIn.getFragment(), firstOut.getFragment(), isPop, lastInViews, false);
                        }
                    });
                    sharedElementFirstOutViews3.addAll(firstOutViews.values());
                    if (exitingNames4.isEmpty()) {
                        firstOutEpicenterView3 = firstOutEpicenterView2;
                    } else {
                        String epicenterViewName = exitingNames4.get(0);
                        firstOutEpicenterView3 = firstOutViews.get(epicenterViewName);
                        transitionImpl3.setEpicenter(sharedElementTransition2, firstOutEpicenterView3);
                    }
                    sharedElementLastInViews4.addAll(lastInViews.values());
                    if (!enteringNames.isEmpty()) {
                        String epicenterViewName2 = enteringNames.get(0);
                        final View lastInEpicenterView = lastInViews.get(epicenterViewName2);
                        if (lastInEpicenterView != null) {
                            hasLastInEpicenter = true;
                            final FragmentTransitionImpl impl = transitionImpl3;
                            OneShotPreDrawListener.add(getContainer(), new Runnable() { // from class: androidx.fragment.app.DefaultSpecialEffectsController.7
                                @Override // java.lang.Runnable
                                public void run() {
                                    impl.getBoundsOnScreen(lastInEpicenterView, lastInEpicenterRect3);
                                }
                            });
                        }
                    }
                    nonExistentView2 = nonExistentView4;
                    transitionImpl3.setSharedElementTargets(sharedElementTransition2, nonExistentView2, sharedElementFirstOutViews3);
                    sharedElementFirstOutViews2 = sharedElementFirstOutViews3;
                    lastInEpicenterRect2 = lastInEpicenterRect3;
                    sharedElementLastInViews2 = sharedElementLastInViews4;
                    transitionImpl2 = transitionImpl3;
                    transitionImpl3.scheduleRemoveTargets(sharedElementTransition2, null, null, null, null, sharedElementTransition2, sharedElementLastInViews2);
                    operation2 = firstOut;
                    startedTransitions2 = startedTransitions4;
                    startedTransitions2.put(operation2, true);
                    operation3 = lastIn;
                    startedTransitions2.put(operation3, true);
                    sharedElementTransition = sharedElementTransition2;
                }
            } else {
                sharedElementNameMapping = sharedElementNameMapping2;
                sharedElementLastInViews2 = sharedElementLastInViews3;
                startedTransitions2 = startedTransitions3;
                sharedElementFirstOutViews2 = sharedElementFirstOutViews3;
                lastInEpicenterRect2 = lastInEpicenterRect3;
                nonExistentView2 = nonExistentView3;
                transitionImpl2 = transitionImpl3;
                operation2 = operation4;
                operation3 = operation5;
                firstOutEpicenterView3 = firstOutEpicenterView3;
            }
            z = isPop;
            sharedElementFirstOutViews3 = sharedElementFirstOutViews2;
            operation5 = operation3;
            startedTransitions3 = startedTransitions2;
            operation4 = operation2;
            sharedElementLastInViews3 = sharedElementLastInViews2;
            sharedElementNameMapping2 = sharedElementNameMapping;
            lastInEpicenterRect3 = lastInEpicenterRect2;
            transitionImpl3 = transitionImpl2;
            nonExistentView3 = nonExistentView2;
        }
        View firstOutEpicenterView4 = firstOutEpicenterView3;
        ArrayMap<String, String> sharedElementNameMapping3 = sharedElementNameMapping2;
        ArrayList<View> sharedElementLastInViews5 = sharedElementLastInViews3;
        Map<SpecialEffectsController.Operation, Boolean> startedTransitions5 = startedTransitions3;
        Collection<?> sharedElementFirstOutViews4 = sharedElementFirstOutViews3;
        Rect lastInEpicenterRect4 = lastInEpicenterRect3;
        View nonExistentView5 = nonExistentView3;
        FragmentTransitionImpl transitionImpl4 = transitionImpl3;
        boolean z2 = false;
        SpecialEffectsController.Operation operation6 = operation4;
        SpecialEffectsController.Operation operation7 = operation5;
        ArrayList<View> enteringViews = new ArrayList<>();
        Object mergedTransition = null;
        Object mergedNonOverlappingTransition2 = null;
        Iterator<TransitionInfo> it3 = transitionInfos.iterator();
        while (it3.hasNext()) {
            TransitionInfo transitionInfo4 = it3.next();
            if (transitionInfo4.isVisibilityUnchanged()) {
                startedTransitions5.put(transitionInfo4.getOperation(), Boolean.valueOf(z2));
                transitionInfo4.completeSpecialEffect();
            } else {
                FragmentTransitionImpl transitionImpl5 = transitionImpl4;
                Object transition = transitionImpl5.cloneTransition(transitionInfo4.getTransition());
                SpecialEffectsController.Operation operation8 = transitionInfo4.getOperation();
                boolean involvedInSharedElementTransition2 = sharedElementTransition != null && (operation8 == operation6 || operation8 == operation7);
                if (transition == null) {
                    if (involvedInSharedElementTransition2) {
                        it2 = it3;
                    } else {
                        it2 = it3;
                        startedTransitions5.put(operation8, Boolean.valueOf(z2));
                        transitionInfo4.completeSpecialEffect();
                    }
                    nonExistentView = nonExistentView5;
                    sharedElementFirstOutViews = sharedElementFirstOutViews4;
                    startedTransitions = startedTransitions5;
                    operation = operation6;
                    sharedElementLastInViews = sharedElementLastInViews5;
                    lastInEpicenterRect = lastInEpicenterRect4;
                    transitionImpl = transitionImpl5;
                    firstOutEpicenterView = firstOutEpicenterView4;
                } else {
                    it2 = it3;
                    final ArrayList<View> transitioningViews = new ArrayList<>();
                    captureTransitioningViews(transitioningViews, operation8.getFragment().mView);
                    if (!involvedInSharedElementTransition2) {
                        sharedElementLastInViews = sharedElementLastInViews5;
                    } else if (operation8 == operation6) {
                        transitioningViews.removeAll(sharedElementFirstOutViews4);
                        sharedElementLastInViews = sharedElementLastInViews5;
                    } else {
                        sharedElementLastInViews = sharedElementLastInViews5;
                        transitioningViews.removeAll(sharedElementLastInViews);
                    }
                    if (transitioningViews.isEmpty()) {
                        transitionImpl5.addTarget(transition, nonExistentView5);
                        nonExistentView = nonExistentView5;
                        sharedElementFirstOutViews = sharedElementFirstOutViews4;
                        startedTransitions = startedTransitions5;
                        operation = operation6;
                        transitionImpl = transitionImpl5;
                    } else {
                        transitionImpl5.addTargets(transition, transitioningViews);
                        nonExistentView = nonExistentView5;
                        startedTransitions = startedTransitions5;
                        sharedElementFirstOutViews = sharedElementFirstOutViews4;
                        operation = operation6;
                        transitionImpl5.scheduleRemoveTargets(transition, transition, transitioningViews, null, null, null, null);
                        if (operation8.getFinalState() == SpecialEffectsController.Operation.State.GONE) {
                            operation8 = operation8;
                            awaitingContainerChanges.remove(operation8);
                            ArrayList<View> transitioningViewsToHide = new ArrayList<>(transitioningViews);
                            transitioningViewsToHide.remove(operation8.getFragment().mView);
                            transitionImpl = transitionImpl5;
                            transitionImpl.scheduleHideFragmentView(transition, operation8.getFragment().mView, transitioningViewsToHide);
                            OneShotPreDrawListener.add(getContainer(), new Runnable() { // from class: androidx.fragment.app.DefaultSpecialEffectsController.8
                                @Override // java.lang.Runnable
                                public void run() {
                                    FragmentTransition.setViewVisibility(transitioningViews, 4);
                                }
                            });
                        } else {
                            operation8 = operation8;
                            transitionImpl = transitionImpl5;
                        }
                    }
                    if (operation8.getFinalState() == SpecialEffectsController.Operation.State.VISIBLE) {
                        enteringViews.addAll(transitioningViews);
                        if (!hasLastInEpicenter) {
                            lastInEpicenterRect = lastInEpicenterRect4;
                            firstOutEpicenterView = firstOutEpicenterView4;
                        } else {
                            lastInEpicenterRect = lastInEpicenterRect4;
                            transitionImpl.setEpicenter(transition, lastInEpicenterRect);
                            firstOutEpicenterView = firstOutEpicenterView4;
                        }
                    } else {
                        lastInEpicenterRect = lastInEpicenterRect4;
                        firstOutEpicenterView = firstOutEpicenterView4;
                        transitionImpl.setEpicenter(transition, firstOutEpicenterView);
                    }
                    startedTransitions.put(operation8, true);
                    if (transitionInfo4.isOverlapAllowed()) {
                        mergedTransition = transitionImpl.mergeTransitionsTogether(mergedTransition, transition, null);
                    } else {
                        mergedNonOverlappingTransition2 = transitionImpl.mergeTransitionsTogether(mergedNonOverlappingTransition2, transition, null);
                    }
                }
                sharedElementLastInViews5 = sharedElementLastInViews;
                operation6 = operation;
                firstOutEpicenterView4 = firstOutEpicenterView;
                lastInEpicenterRect4 = lastInEpicenterRect;
                transitionImpl4 = transitionImpl;
                it3 = it2;
                sharedElementFirstOutViews4 = sharedElementFirstOutViews;
                z2 = false;
                operation7 = lastIn;
                startedTransitions5 = startedTransitions;
                nonExistentView5 = nonExistentView;
            }
        }
        ArrayList<View> sharedElementFirstOutViews5 = sharedElementFirstOutViews4;
        Map<SpecialEffectsController.Operation, Boolean> startedTransitions6 = startedTransitions5;
        SpecialEffectsController.Operation operation9 = operation6;
        ArrayList<View> sharedElementLastInViews6 = sharedElementLastInViews5;
        FragmentTransitionImpl transitionImpl6 = transitionImpl4;
        Object mergedTransition2 = transitionImpl6.mergeTransitionsInSequence(mergedTransition, mergedNonOverlappingTransition2, sharedElementTransition);
        Iterator<TransitionInfo> it4 = transitionInfos.iterator();
        while (it4.hasNext()) {
            final TransitionInfo transitionInfo5 = it4.next();
            if (!transitionInfo5.isVisibilityUnchanged()) {
                Object transition2 = transitionInfo5.getTransition();
                SpecialEffectsController.Operation operation10 = transitionInfo5.getOperation();
                if (sharedElementTransition != null) {
                    if (operation10 != operation9) {
                        mergedNonOverlappingTransition = mergedNonOverlappingTransition2;
                    } else {
                        mergedNonOverlappingTransition = mergedNonOverlappingTransition2;
                    }
                    involvedInSharedElementTransition = true;
                    if (transition2 != null && !involvedInSharedElementTransition) {
                        it = it4;
                    } else if (ViewCompat.isLaidOut(getContainer())) {
                        if (!FragmentManager.isLoggingEnabled(2)) {
                            it = it4;
                        } else {
                            StringBuilder sb = new StringBuilder();
                            it = it4;
                            sb.append("SpecialEffectsController: Container ");
                            sb.append(getContainer());
                            sb.append(" has not been laid out. Completing operation ");
                            sb.append(operation10);
                            Log.v("FragmentManager", sb.toString());
                        }
                        transitionInfo5.completeSpecialEffect();
                    } else {
                        it = it4;
                        transitionImpl6.setListenerForTransitionEnd(transitionInfo5.getOperation().getFragment(), mergedTransition2, transitionInfo5.getSignal(), new Runnable() { // from class: androidx.fragment.app.DefaultSpecialEffectsController.9
                            @Override // java.lang.Runnable
                            public void run() {
                                transitionInfo5.completeSpecialEffect();
                            }
                        });
                    }
                    mergedNonOverlappingTransition2 = mergedNonOverlappingTransition;
                    it4 = it;
                } else {
                    mergedNonOverlappingTransition = mergedNonOverlappingTransition2;
                }
                involvedInSharedElementTransition = false;
                if (transition2 != null) {
                }
                if (ViewCompat.isLaidOut(getContainer())) {
                }
                mergedNonOverlappingTransition2 = mergedNonOverlappingTransition;
                it4 = it;
            }
        }
        if (!ViewCompat.isLaidOut(getContainer())) {
            return startedTransitions6;
        }
        FragmentTransition.setViewVisibility(enteringViews, 4);
        ArrayList<String> inNames = transitionImpl6.prepareSetNameOverridesReordered(sharedElementLastInViews6);
        transitionImpl6.beginDelayedTransition(getContainer(), mergedTransition2);
        transitionImpl6.setNameOverridesReordered(getContainer(), sharedElementFirstOutViews5, sharedElementLastInViews6, inNames, sharedElementNameMapping3);
        FragmentTransition.setViewVisibility(enteringViews, 0);
        transitionImpl6.swapSharedElementTargets(sharedElementTransition, sharedElementFirstOutViews5, sharedElementLastInViews6);
        return startedTransitions6;
    }

    void retainMatchingViews(ArrayMap<String, View> sharedElementViews, Collection<String> transitionNames) {
        Iterator<Map.Entry<String, View>> iterator = sharedElementViews.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, View> entry = iterator.next();
            if (!transitionNames.contains(ViewCompat.getTransitionName(entry.getValue()))) {
                iterator.remove();
            }
        }
    }

    void captureTransitioningViews(ArrayList<View> transitioningViews, View view) {
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            if (ViewGroupCompat.isTransitionGroup(viewGroup)) {
                if (!transitioningViews.contains(view)) {
                    transitioningViews.add(viewGroup);
                    return;
                }
                return;
            }
            int count = viewGroup.getChildCount();
            for (int i = 0; i < count; i++) {
                View child = viewGroup.getChildAt(i);
                if (child.getVisibility() == 0) {
                    captureTransitioningViews(transitioningViews, child);
                }
            }
        } else if (!transitioningViews.contains(view)) {
            transitioningViews.add(view);
        }
    }

    void findNamedViews(Map<String, View> namedViews, View view) {
        String transitionName = ViewCompat.getTransitionName(view);
        if (transitionName != null) {
            namedViews.put(transitionName, view);
        }
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            int count = viewGroup.getChildCount();
            for (int i = 0; i < count; i++) {
                View child = viewGroup.getChildAt(i);
                if (child.getVisibility() == 0) {
                    findNamedViews(namedViews, child);
                }
            }
        }
    }

    void applyContainerChanges(SpecialEffectsController.Operation operation) {
        View view = operation.getFragment().mView;
        operation.getFinalState().applyState(view);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class SpecialEffectsInfo {
        private final SpecialEffectsController.Operation mOperation;
        private final CancellationSignal mSignal;

        SpecialEffectsInfo(SpecialEffectsController.Operation operation, CancellationSignal signal) {
            this.mOperation = operation;
            this.mSignal = signal;
        }

        SpecialEffectsController.Operation getOperation() {
            return this.mOperation;
        }

        CancellationSignal getSignal() {
            return this.mSignal;
        }

        boolean isVisibilityUnchanged() {
            SpecialEffectsController.Operation.State currentState = SpecialEffectsController.Operation.State.from(this.mOperation.getFragment().mView);
            SpecialEffectsController.Operation.State finalState = this.mOperation.getFinalState();
            return currentState == finalState || !(currentState == SpecialEffectsController.Operation.State.VISIBLE || finalState == SpecialEffectsController.Operation.State.VISIBLE);
        }

        void completeSpecialEffect() {
            this.mOperation.completeSpecialEffect(this.mSignal);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class AnimationInfo extends SpecialEffectsInfo {
        private FragmentAnim.AnimationOrAnimator mAnimation;
        private boolean mIsPop;
        private boolean mLoadedAnim;

        AnimationInfo(SpecialEffectsController.Operation operation, CancellationSignal signal, boolean isPop) {
            super(operation, signal);
            this.mLoadedAnim = false;
            this.mIsPop = isPop;
        }

        FragmentAnim.AnimationOrAnimator getAnimation(Context context) {
            if (this.mLoadedAnim) {
                return this.mAnimation;
            }
            FragmentAnim.AnimationOrAnimator loadAnimation = FragmentAnim.loadAnimation(context, getOperation().getFragment(), getOperation().getFinalState() == SpecialEffectsController.Operation.State.VISIBLE, this.mIsPop);
            this.mAnimation = loadAnimation;
            this.mLoadedAnim = true;
            return loadAnimation;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class TransitionInfo extends SpecialEffectsInfo {
        private final boolean mOverlapAllowed;
        private final Object mSharedElementTransition;
        private final Object mTransition;

        TransitionInfo(SpecialEffectsController.Operation operation, CancellationSignal signal, boolean isPop, boolean providesSharedElementTransition) {
            super(operation, signal);
            Object exitTransition;
            Object enterTransition;
            boolean allowEnterTransitionOverlap;
            if (operation.getFinalState() == SpecialEffectsController.Operation.State.VISIBLE) {
                if (isPop) {
                    enterTransition = operation.getFragment().getReenterTransition();
                } else {
                    enterTransition = operation.getFragment().getEnterTransition();
                }
                this.mTransition = enterTransition;
                if (isPop) {
                    allowEnterTransitionOverlap = operation.getFragment().getAllowReturnTransitionOverlap();
                } else {
                    allowEnterTransitionOverlap = operation.getFragment().getAllowEnterTransitionOverlap();
                }
                this.mOverlapAllowed = allowEnterTransitionOverlap;
            } else {
                if (isPop) {
                    exitTransition = operation.getFragment().getReturnTransition();
                } else {
                    exitTransition = operation.getFragment().getExitTransition();
                }
                this.mTransition = exitTransition;
                this.mOverlapAllowed = true;
            }
            if (providesSharedElementTransition) {
                if (isPop) {
                    this.mSharedElementTransition = operation.getFragment().getSharedElementReturnTransition();
                    return;
                } else {
                    this.mSharedElementTransition = operation.getFragment().getSharedElementEnterTransition();
                    return;
                }
            }
            this.mSharedElementTransition = null;
        }

        Object getTransition() {
            return this.mTransition;
        }

        boolean isOverlapAllowed() {
            return this.mOverlapAllowed;
        }

        public boolean hasSharedElementTransition() {
            return this.mSharedElementTransition != null;
        }

        public Object getSharedElementTransition() {
            return this.mSharedElementTransition;
        }

        FragmentTransitionImpl getHandlingImpl() {
            FragmentTransitionImpl transitionImpl = getHandlingImpl(this.mTransition);
            FragmentTransitionImpl sharedElementTransitionImpl = getHandlingImpl(this.mSharedElementTransition);
            if (transitionImpl == null || sharedElementTransitionImpl == null || transitionImpl == sharedElementTransitionImpl) {
                return transitionImpl != null ? transitionImpl : sharedElementTransitionImpl;
            }
            throw new IllegalArgumentException("Mixing framework transitions and AndroidX transitions is not allowed. Fragment " + getOperation().getFragment() + " returned Transition " + this.mTransition + " which uses a different Transition  type than its shared element transition " + this.mSharedElementTransition);
        }

        private FragmentTransitionImpl getHandlingImpl(Object transition) {
            if (transition == null) {
                return null;
            }
            if (FragmentTransition.PLATFORM_IMPL != null && FragmentTransition.PLATFORM_IMPL.canHandle(transition)) {
                return FragmentTransition.PLATFORM_IMPL;
            }
            if (FragmentTransition.SUPPORT_IMPL != null && FragmentTransition.SUPPORT_IMPL.canHandle(transition)) {
                return FragmentTransition.SUPPORT_IMPL;
            }
            throw new IllegalArgumentException("Transition " + transition + " for fragment " + getOperation().getFragment() + " is not a valid framework Transition or AndroidX Transition");
        }
    }
}
