package com.google.android.material.bottomappbar;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.ActionMenuView;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.customview.view.AbsSavedState;
import com.google.android.material.R;
import com.google.android.material.animation.TransformationCallback;
import com.google.android.material.behavior.HideBottomViewOnScrollBehavior;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.shape.EdgeTreatment;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.MaterialShapeUtils;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
/* loaded from: classes.dex */
public class BottomAppBar extends Toolbar implements CoordinatorLayout.AttachedBehavior {
    private static final long ANIMATION_DURATION = 300;
    private static final int DEF_STYLE_RES = R.style.Widget_MaterialComponents_BottomAppBar;
    public static final int FAB_ALIGNMENT_MODE_CENTER = 0;
    public static final int FAB_ALIGNMENT_MODE_END = 1;
    public static final int FAB_ANIMATION_MODE_SCALE = 0;
    public static final int FAB_ANIMATION_MODE_SLIDE = 1;
    private static final int NO_MENU_RES_ID = 0;
    private int animatingModeChangeCounter;
    private ArrayList<AnimationListener> animationListeners;
    private Behavior behavior;
    private int bottomInset;
    private int fabAlignmentMode;
    AnimatorListenerAdapter fabAnimationListener;
    private int fabAnimationMode;
    private boolean fabAttached;
    private final int fabOffsetEndMode;
    TransformationCallback<FloatingActionButton> fabTransformationCallback;
    private boolean hideOnScroll;
    private int leftInset;
    private final MaterialShapeDrawable materialShapeDrawable;
    private boolean menuAnimatingWithFabAlignmentMode;
    private Animator menuAnimator;
    private Animator modeAnimator;
    private Integer navigationIconTint;
    private final boolean paddingBottomSystemWindowInsets;
    private final boolean paddingLeftSystemWindowInsets;
    private final boolean paddingRightSystemWindowInsets;
    private int pendingMenuResId;
    private int rightInset;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public interface AnimationListener {
        void onAnimationEnd(BottomAppBar bottomAppBar);

        void onAnimationStart(BottomAppBar bottomAppBar);
    }

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes.dex */
    public @interface FabAlignmentMode {
    }

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes.dex */
    public @interface FabAnimationMode {
    }

    public BottomAppBar(Context context) {
        this(context, null);
    }

    public BottomAppBar(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.bottomAppBarStyle);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public BottomAppBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(MaterialThemeOverlay.wrap(context, attrs, defStyleAttr, r6), attrs, defStyleAttr);
        int i = DEF_STYLE_RES;
        MaterialShapeDrawable materialShapeDrawable = new MaterialShapeDrawable();
        this.materialShapeDrawable = materialShapeDrawable;
        this.animatingModeChangeCounter = 0;
        this.pendingMenuResId = 0;
        this.menuAnimatingWithFabAlignmentMode = false;
        this.fabAttached = true;
        this.fabAnimationListener = new AnimatorListenerAdapter() { // from class: com.google.android.material.bottomappbar.BottomAppBar.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animation) {
                if (!BottomAppBar.this.menuAnimatingWithFabAlignmentMode) {
                    BottomAppBar bottomAppBar = BottomAppBar.this;
                    bottomAppBar.maybeAnimateMenuView(bottomAppBar.fabAlignmentMode, BottomAppBar.this.fabAttached);
                }
            }
        };
        this.fabTransformationCallback = new TransformationCallback<FloatingActionButton>() { // from class: com.google.android.material.bottomappbar.BottomAppBar.2
            @Override // com.google.android.material.animation.TransformationCallback
            public void onScaleChanged(FloatingActionButton fab) {
                BottomAppBar.this.materialShapeDrawable.setInterpolation(fab.getVisibility() == 0 ? fab.getScaleY() : 0.0f);
            }

            @Override // com.google.android.material.animation.TransformationCallback
            public void onTranslationChanged(FloatingActionButton fab) {
                float horizontalOffset = fab.getTranslationX();
                if (BottomAppBar.this.getTopEdgeTreatment().getHorizontalOffset() != horizontalOffset) {
                    BottomAppBar.this.getTopEdgeTreatment().setHorizontalOffset(horizontalOffset);
                    BottomAppBar.this.materialShapeDrawable.invalidateSelf();
                }
                float verticalOffset = Math.max(0.0f, -fab.getTranslationY());
                if (BottomAppBar.this.getTopEdgeTreatment().getCradleVerticalOffset() != verticalOffset) {
                    BottomAppBar.this.getTopEdgeTreatment().setCradleVerticalOffset(verticalOffset);
                    BottomAppBar.this.materialShapeDrawable.invalidateSelf();
                }
                BottomAppBar.this.materialShapeDrawable.setInterpolation(fab.getVisibility() == 0 ? fab.getScaleY() : 0.0f);
            }
        };
        Context context2 = getContext();
        TypedArray a = ThemeEnforcement.obtainStyledAttributes(context2, attrs, R.styleable.BottomAppBar, defStyleAttr, i, new int[0]);
        ColorStateList backgroundTint = MaterialResources.getColorStateList(context2, a, R.styleable.BottomAppBar_backgroundTint);
        if (a.hasValue(R.styleable.BottomAppBar_navigationIconTint)) {
            setNavigationIconTint(a.getColor(R.styleable.BottomAppBar_navigationIconTint, -1));
        }
        int elevation = a.getDimensionPixelSize(R.styleable.BottomAppBar_elevation, 0);
        float fabCradleMargin = a.getDimensionPixelOffset(R.styleable.BottomAppBar_fabCradleMargin, 0);
        float fabCornerRadius = a.getDimensionPixelOffset(R.styleable.BottomAppBar_fabCradleRoundedCornerRadius, 0);
        float fabVerticalOffset = a.getDimensionPixelOffset(R.styleable.BottomAppBar_fabCradleVerticalOffset, 0);
        this.fabAlignmentMode = a.getInt(R.styleable.BottomAppBar_fabAlignmentMode, 0);
        this.fabAnimationMode = a.getInt(R.styleable.BottomAppBar_fabAnimationMode, 0);
        this.hideOnScroll = a.getBoolean(R.styleable.BottomAppBar_hideOnScroll, false);
        this.paddingBottomSystemWindowInsets = a.getBoolean(R.styleable.BottomAppBar_paddingBottomSystemWindowInsets, false);
        this.paddingLeftSystemWindowInsets = a.getBoolean(R.styleable.BottomAppBar_paddingLeftSystemWindowInsets, false);
        this.paddingRightSystemWindowInsets = a.getBoolean(R.styleable.BottomAppBar_paddingRightSystemWindowInsets, false);
        a.recycle();
        this.fabOffsetEndMode = getResources().getDimensionPixelOffset(R.dimen.mtrl_bottomappbar_fabOffsetEndMode);
        EdgeTreatment topEdgeTreatment = new BottomAppBarTopEdgeTreatment(fabCradleMargin, fabCornerRadius, fabVerticalOffset);
        ShapeAppearanceModel shapeAppearanceModel = ShapeAppearanceModel.builder().setTopEdge(topEdgeTreatment).build();
        materialShapeDrawable.setShapeAppearanceModel(shapeAppearanceModel);
        materialShapeDrawable.setShadowCompatibilityMode(2);
        materialShapeDrawable.setPaintStyle(Paint.Style.FILL);
        materialShapeDrawable.initializeElevationOverlay(context2);
        setElevation(elevation);
        DrawableCompat.setTintList(materialShapeDrawable, backgroundTint);
        ViewCompat.setBackground(this, materialShapeDrawable);
        ViewUtils.doOnApplyWindowInsets(this, attrs, defStyleAttr, i, new ViewUtils.OnApplyWindowInsetsListener() { // from class: com.google.android.material.bottomappbar.BottomAppBar.3
            @Override // com.google.android.material.internal.ViewUtils.OnApplyWindowInsetsListener
            public WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat insets, ViewUtils.RelativePadding initialPadding) {
                boolean leftInsetsChanged = false;
                boolean rightInsetsChanged = false;
                if (BottomAppBar.this.paddingBottomSystemWindowInsets) {
                    BottomAppBar.this.bottomInset = insets.getSystemWindowInsetBottom();
                }
                if (BottomAppBar.this.paddingLeftSystemWindowInsets) {
                    leftInsetsChanged = BottomAppBar.this.leftInset != insets.getSystemWindowInsetLeft();
                    BottomAppBar.this.leftInset = insets.getSystemWindowInsetLeft();
                }
                if (BottomAppBar.this.paddingRightSystemWindowInsets) {
                    rightInsetsChanged = BottomAppBar.this.rightInset != insets.getSystemWindowInsetRight();
                    BottomAppBar.this.rightInset = insets.getSystemWindowInsetRight();
                }
                if (leftInsetsChanged || rightInsetsChanged) {
                    BottomAppBar.this.cancelAnimations();
                    BottomAppBar.this.setCutoutState();
                    BottomAppBar.this.setActionMenuViewPosition();
                }
                return insets;
            }
        });
    }

    @Override // androidx.appcompat.widget.Toolbar
    public void setNavigationIcon(Drawable drawable) {
        super.setNavigationIcon(maybeTintNavigationIcon(drawable));
    }

    public void setNavigationIconTint(int navigationIconTint) {
        this.navigationIconTint = Integer.valueOf(navigationIconTint);
        Drawable navigationIcon = getNavigationIcon();
        if (navigationIcon != null) {
            setNavigationIcon(navigationIcon);
        }
    }

    public int getFabAlignmentMode() {
        return this.fabAlignmentMode;
    }

    public void setFabAlignmentMode(int fabAlignmentMode) {
        setFabAlignmentModeAndReplaceMenu(fabAlignmentMode, 0);
    }

    public void setFabAlignmentModeAndReplaceMenu(int fabAlignmentMode, int newMenu) {
        this.pendingMenuResId = newMenu;
        this.menuAnimatingWithFabAlignmentMode = true;
        maybeAnimateMenuView(fabAlignmentMode, this.fabAttached);
        maybeAnimateModeChange(fabAlignmentMode);
        this.fabAlignmentMode = fabAlignmentMode;
    }

    public int getFabAnimationMode() {
        return this.fabAnimationMode;
    }

    public void setFabAnimationMode(int fabAnimationMode) {
        this.fabAnimationMode = fabAnimationMode;
    }

    public void setBackgroundTint(ColorStateList backgroundTint) {
        DrawableCompat.setTintList(this.materialShapeDrawable, backgroundTint);
    }

    public ColorStateList getBackgroundTint() {
        return this.materialShapeDrawable.getTintList();
    }

    public float getFabCradleMargin() {
        return getTopEdgeTreatment().getFabCradleMargin();
    }

    public void setFabCradleMargin(float cradleMargin) {
        if (cradleMargin != getFabCradleMargin()) {
            getTopEdgeTreatment().setFabCradleMargin(cradleMargin);
            this.materialShapeDrawable.invalidateSelf();
        }
    }

    public float getFabCradleRoundedCornerRadius() {
        return getTopEdgeTreatment().getFabCradleRoundedCornerRadius();
    }

    public void setFabCradleRoundedCornerRadius(float roundedCornerRadius) {
        if (roundedCornerRadius != getFabCradleRoundedCornerRadius()) {
            getTopEdgeTreatment().setFabCradleRoundedCornerRadius(roundedCornerRadius);
            this.materialShapeDrawable.invalidateSelf();
        }
    }

    public float getCradleVerticalOffset() {
        return getTopEdgeTreatment().getCradleVerticalOffset();
    }

    public void setCradleVerticalOffset(float verticalOffset) {
        if (verticalOffset != getCradleVerticalOffset()) {
            getTopEdgeTreatment().setCradleVerticalOffset(verticalOffset);
            this.materialShapeDrawable.invalidateSelf();
            setCutoutState();
        }
    }

    public boolean getHideOnScroll() {
        return this.hideOnScroll;
    }

    public void setHideOnScroll(boolean hide) {
        this.hideOnScroll = hide;
    }

    public void performHide() {
        performHide(true);
    }

    public void performHide(boolean animate) {
        getBehavior().slideDown(this, animate);
    }

    public void performShow() {
        performShow(true);
    }

    public void performShow(boolean animate) {
        getBehavior().slideUp(this, animate);
    }

    public boolean isScrolledDown() {
        return getBehavior().isScrolledDown();
    }

    public boolean isScrolledUp() {
        return getBehavior().isScrolledUp();
    }

    @Override // android.view.View
    public void setElevation(float elevation) {
        this.materialShapeDrawable.setElevation(elevation);
        int topShadowHeight = this.materialShapeDrawable.getShadowRadius() - this.materialShapeDrawable.getShadowOffsetY();
        getBehavior().setAdditionalHiddenOffsetY(this, topShadowHeight);
    }

    public void replaceMenu(int newMenu) {
        if (newMenu != 0) {
            this.pendingMenuResId = 0;
            getMenu().clear();
            inflateMenu(newMenu);
        }
    }

    void addAnimationListener(AnimationListener listener) {
        if (this.animationListeners == null) {
            this.animationListeners = new ArrayList<>();
        }
        this.animationListeners.add(listener);
    }

    void removeAnimationListener(AnimationListener listener) {
        ArrayList<AnimationListener> arrayList = this.animationListeners;
        if (arrayList == null) {
            return;
        }
        arrayList.remove(listener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchAnimationStart() {
        ArrayList<AnimationListener> arrayList;
        int i = this.animatingModeChangeCounter;
        this.animatingModeChangeCounter = i + 1;
        if (i == 0 && (arrayList = this.animationListeners) != null) {
            Iterator<AnimationListener> it = arrayList.iterator();
            while (it.hasNext()) {
                AnimationListener listener = it.next();
                listener.onAnimationStart(this);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchAnimationEnd() {
        ArrayList<AnimationListener> arrayList;
        int i = this.animatingModeChangeCounter - 1;
        this.animatingModeChangeCounter = i;
        if (i == 0 && (arrayList = this.animationListeners) != null) {
            Iterator<AnimationListener> it = arrayList.iterator();
            while (it.hasNext()) {
                AnimationListener listener = it.next();
                listener.onAnimationEnd(this);
            }
        }
    }

    boolean setFabDiameter(int diameter) {
        if (diameter != getTopEdgeTreatment().getFabDiameter()) {
            getTopEdgeTreatment().setFabDiameter(diameter);
            this.materialShapeDrawable.invalidateSelf();
            return true;
        }
        return false;
    }

    void setFabCornerSize(float radius) {
        if (radius != getTopEdgeTreatment().getFabCornerRadius()) {
            getTopEdgeTreatment().setFabCornerSize(radius);
            this.materialShapeDrawable.invalidateSelf();
        }
    }

    private void maybeAnimateModeChange(int targetMode) {
        if (this.fabAlignmentMode == targetMode || !ViewCompat.isLaidOut(this)) {
            return;
        }
        Animator animator = this.modeAnimator;
        if (animator != null) {
            animator.cancel();
        }
        List<Animator> animators = new ArrayList<>();
        if (this.fabAnimationMode == 1) {
            createFabTranslationXAnimation(targetMode, animators);
        } else {
            createFabDefaultXAnimation(targetMode, animators);
        }
        AnimatorSet set = new AnimatorSet();
        set.playTogether(animators);
        this.modeAnimator = set;
        set.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.material.bottomappbar.BottomAppBar.4
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animation) {
                BottomAppBar.this.dispatchAnimationStart();
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                BottomAppBar.this.dispatchAnimationEnd();
                BottomAppBar.this.modeAnimator = null;
            }
        });
        this.modeAnimator.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public FloatingActionButton findDependentFab() {
        View view = findDependentView();
        if (view instanceof FloatingActionButton) {
            return (FloatingActionButton) view;
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:8:0x001e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public View findDependentView() {
        if (getParent() instanceof CoordinatorLayout) {
            List<View> dependents = ((CoordinatorLayout) getParent()).getDependents(this);
            for (View v : dependents) {
                if ((v instanceof FloatingActionButton) || (v instanceof ExtendedFloatingActionButton)) {
                    return v;
                }
                while (r2.hasNext()) {
                }
            }
            return null;
        }
        return null;
    }

    private boolean isFabVisibleOrWillBeShown() {
        FloatingActionButton fab = findDependentFab();
        return fab != null && fab.isOrWillBeShown();
    }

    protected void createFabDefaultXAnimation(final int targetMode, List<Animator> animators) {
        FloatingActionButton fab = findDependentFab();
        if (fab == null || fab.isOrWillBeHidden()) {
            return;
        }
        dispatchAnimationStart();
        fab.hide(new FloatingActionButton.OnVisibilityChangedListener() { // from class: com.google.android.material.bottomappbar.BottomAppBar.5
            @Override // com.google.android.material.floatingactionbutton.FloatingActionButton.OnVisibilityChangedListener
            public void onHidden(FloatingActionButton fab2) {
                fab2.setTranslationX(BottomAppBar.this.getFabTranslationX(targetMode));
                fab2.show(new FloatingActionButton.OnVisibilityChangedListener() { // from class: com.google.android.material.bottomappbar.BottomAppBar.5.1
                    @Override // com.google.android.material.floatingactionbutton.FloatingActionButton.OnVisibilityChangedListener
                    public void onShown(FloatingActionButton fab3) {
                        BottomAppBar.this.dispatchAnimationEnd();
                    }
                });
            }
        });
    }

    private void createFabTranslationXAnimation(int targetMode, List<Animator> animators) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(findDependentFab(), "translationX", getFabTranslationX(targetMode));
        animator.setDuration(ANIMATION_DURATION);
        animators.add(animator);
    }

    private Drawable maybeTintNavigationIcon(Drawable navigationIcon) {
        if (navigationIcon != null && this.navigationIconTint != null) {
            Drawable wrappedNavigationIcon = DrawableCompat.wrap(navigationIcon.mutate());
            DrawableCompat.setTint(wrappedNavigationIcon, this.navigationIconTint.intValue());
            return wrappedNavigationIcon;
        }
        return navigationIcon;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void maybeAnimateMenuView(int targetMode, boolean newFabAttached) {
        if (!ViewCompat.isLaidOut(this)) {
            this.menuAnimatingWithFabAlignmentMode = false;
            replaceMenu(this.pendingMenuResId);
            return;
        }
        Animator animator = this.menuAnimator;
        if (animator != null) {
            animator.cancel();
        }
        List<Animator> animators = new ArrayList<>();
        if (!isFabVisibleOrWillBeShown()) {
            targetMode = 0;
            newFabAttached = false;
        }
        createMenuViewTranslationAnimation(targetMode, newFabAttached, animators);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(animators);
        this.menuAnimator = set;
        set.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.material.bottomappbar.BottomAppBar.6
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animation) {
                BottomAppBar.this.dispatchAnimationStart();
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                BottomAppBar.this.dispatchAnimationEnd();
                BottomAppBar.this.menuAnimatingWithFabAlignmentMode = false;
                BottomAppBar.this.menuAnimator = null;
            }
        });
        this.menuAnimator.start();
    }

    private void createMenuViewTranslationAnimation(final int targetMode, final boolean targetAttached, List<Animator> animators) {
        final ActionMenuView actionMenuView = getActionMenuView();
        if (actionMenuView == null) {
            return;
        }
        Animator fadeIn = ObjectAnimator.ofFloat(actionMenuView, "alpha", 1.0f);
        float translationXDifference = actionMenuView.getTranslationX() - getActionMenuViewTranslationX(actionMenuView, targetMode, targetAttached);
        if (Math.abs(translationXDifference) > 1.0f) {
            Animator fadeOut = ObjectAnimator.ofFloat(actionMenuView, "alpha", 0.0f);
            fadeOut.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.material.bottomappbar.BottomAppBar.7
                public boolean cancelled;

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationCancel(Animator animation) {
                    this.cancelled = true;
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animation) {
                    if (!this.cancelled) {
                        boolean replaced = BottomAppBar.this.pendingMenuResId != 0;
                        BottomAppBar bottomAppBar = BottomAppBar.this;
                        bottomAppBar.replaceMenu(bottomAppBar.pendingMenuResId);
                        BottomAppBar.this.translateActionMenuView(actionMenuView, targetMode, targetAttached, replaced);
                    }
                }
            });
            AnimatorSet set = new AnimatorSet();
            set.setDuration(150L);
            set.playSequentially(fadeOut, fadeIn);
            animators.add(set);
        } else if (actionMenuView.getAlpha() < 1.0f) {
            animators.add(fadeIn);
        }
    }

    private float getFabTranslationY() {
        return -getTopEdgeTreatment().getCradleVerticalOffset();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public float getFabTranslationX(int fabAlignmentMode) {
        boolean isRtl = ViewUtils.isLayoutRtl(this);
        if (fabAlignmentMode == 1) {
            int systemEndInset = isRtl ? this.leftInset : this.rightInset;
            int totalEndInset = this.fabOffsetEndMode + systemEndInset;
            return ((getMeasuredWidth() / 2) - totalEndInset) * (isRtl ? -1 : 1);
        }
        return 0.0f;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public float getFabTranslationX() {
        return getFabTranslationX(this.fabAlignmentMode);
    }

    private ActionMenuView getActionMenuView() {
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            if (view instanceof ActionMenuView) {
                return (ActionMenuView) view;
            }
        }
        return null;
    }

    private void translateActionMenuView(ActionMenuView actionMenuView, int fabAlignmentMode, boolean fabAttached) {
        translateActionMenuView(actionMenuView, fabAlignmentMode, fabAttached, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void translateActionMenuView(final ActionMenuView actionMenuView, final int fabAlignmentMode, final boolean fabAttached, boolean shouldWaitForMenuReplacement) {
        Runnable runnable = new Runnable() { // from class: com.google.android.material.bottomappbar.BottomAppBar.8
            @Override // java.lang.Runnable
            public void run() {
                ActionMenuView actionMenuView2 = actionMenuView;
                actionMenuView2.setTranslationX(BottomAppBar.this.getActionMenuViewTranslationX(actionMenuView2, fabAlignmentMode, fabAttached));
            }
        };
        if (shouldWaitForMenuReplacement) {
            actionMenuView.post(runnable);
        } else {
            runnable.run();
        }
    }

    protected int getActionMenuViewTranslationX(ActionMenuView actionMenuView, int fabAlignmentMode, boolean fabAttached) {
        int max;
        if (fabAlignmentMode != 1 || !fabAttached) {
            return 0;
        }
        boolean isRtl = ViewUtils.isLayoutRtl(this);
        int toolbarLeftContentEnd = isRtl ? getMeasuredWidth() : 0;
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            boolean isAlignedToStart = (view.getLayoutParams() instanceof Toolbar.LayoutParams) && (((Toolbar.LayoutParams) view.getLayoutParams()).gravity & GravityCompat.RELATIVE_HORIZONTAL_GRAVITY_MASK) == 8388611;
            if (isAlignedToStart) {
                if (isRtl) {
                    max = Math.min(toolbarLeftContentEnd, view.getLeft());
                } else {
                    max = Math.max(toolbarLeftContentEnd, view.getRight());
                }
                toolbarLeftContentEnd = max;
            }
        }
        int actionMenuViewStart = isRtl ? actionMenuView.getRight() : actionMenuView.getLeft();
        int systemStartInset = isRtl ? this.rightInset : -this.leftInset;
        int end = actionMenuViewStart + systemStartInset;
        return toolbarLeftContentEnd - end;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cancelAnimations() {
        Animator animator = this.menuAnimator;
        if (animator != null) {
            animator.cancel();
        }
        Animator animator2 = this.modeAnimator;
        if (animator2 != null) {
            animator2.cancel();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.appcompat.widget.Toolbar, android.view.ViewGroup, android.view.View
    public void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed) {
            cancelAnimations();
            setCutoutState();
        }
        setActionMenuViewPosition();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public BottomAppBarTopEdgeTreatment getTopEdgeTreatment() {
        return (BottomAppBarTopEdgeTreatment) this.materialShapeDrawable.getShapeAppearanceModel().getTopEdge();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setCutoutState() {
        getTopEdgeTreatment().setHorizontalOffset(getFabTranslationX());
        View fab = findDependentView();
        this.materialShapeDrawable.setInterpolation((this.fabAttached && isFabVisibleOrWillBeShown()) ? 1.0f : 0.0f);
        if (fab != null) {
            fab.setTranslationY(getFabTranslationY());
            fab.setTranslationX(getFabTranslationX());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setActionMenuViewPosition() {
        ActionMenuView actionMenuView = getActionMenuView();
        if (actionMenuView != null && this.menuAnimator == null) {
            actionMenuView.setAlpha(1.0f);
            if (!isFabVisibleOrWillBeShown()) {
                translateActionMenuView(actionMenuView, 0, false);
            } else {
                translateActionMenuView(actionMenuView, this.fabAlignmentMode, this.fabAttached);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addFabAnimationListeners(FloatingActionButton fab) {
        fab.addOnHideAnimationListener(this.fabAnimationListener);
        fab.addOnShowAnimationListener(new AnimatorListenerAdapter() { // from class: com.google.android.material.bottomappbar.BottomAppBar.9
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animation) {
                BottomAppBar.this.fabAnimationListener.onAnimationStart(animation);
                FloatingActionButton fab2 = BottomAppBar.this.findDependentFab();
                if (fab2 != null) {
                    fab2.setTranslationX(BottomAppBar.this.getFabTranslationX());
                }
            }
        });
        fab.addTransformationCallback(this.fabTransformationCallback);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getBottomInset() {
        return this.bottomInset;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getRightInset() {
        return this.rightInset;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getLeftInset() {
        return this.leftInset;
    }

    @Override // androidx.appcompat.widget.Toolbar
    public void setTitle(CharSequence title) {
    }

    @Override // androidx.appcompat.widget.Toolbar
    public void setSubtitle(CharSequence subtitle) {
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.AttachedBehavior
    public Behavior getBehavior() {
        if (this.behavior == null) {
            this.behavior = new Behavior();
        }
        return this.behavior;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        MaterialShapeUtils.setParentAbsoluteElevation(this, this.materialShapeDrawable);
        if (getParent() instanceof ViewGroup) {
            ((ViewGroup) getParent()).setClipChildren(false);
        }
    }

    /* loaded from: classes.dex */
    public static class Behavior extends HideBottomViewOnScrollBehavior<BottomAppBar> {
        private final Rect fabContentRect;
        private final View.OnLayoutChangeListener fabLayoutListener;
        private int originalBottomMargin;
        private WeakReference<BottomAppBar> viewRef;

        public Behavior() {
            this.fabLayoutListener = new View.OnLayoutChangeListener() { // from class: com.google.android.material.bottomappbar.BottomAppBar.Behavior.1
                @Override // android.view.View.OnLayoutChangeListener
                public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                    BottomAppBar child = (BottomAppBar) Behavior.this.viewRef.get();
                    if (child == null || !(v instanceof FloatingActionButton)) {
                        v.removeOnLayoutChangeListener(this);
                        return;
                    }
                    FloatingActionButton fab = (FloatingActionButton) v;
                    fab.getMeasuredContentRect(Behavior.this.fabContentRect);
                    int height = Behavior.this.fabContentRect.height();
                    child.setFabDiameter(height);
                    float cornerSize = fab.getShapeAppearanceModel().getTopLeftCornerSize().getCornerSize(new RectF(Behavior.this.fabContentRect));
                    child.setFabCornerSize(cornerSize);
                    CoordinatorLayout.LayoutParams fabLayoutParams = (CoordinatorLayout.LayoutParams) v.getLayoutParams();
                    if (Behavior.this.originalBottomMargin == 0) {
                        int bottomShadowPadding = (fab.getMeasuredHeight() - height) / 2;
                        int bottomMargin = child.getResources().getDimensionPixelOffset(R.dimen.mtrl_bottomappbar_fab_bottom_margin);
                        int minBottomMargin = bottomMargin - bottomShadowPadding;
                        fabLayoutParams.bottomMargin = child.getBottomInset() + minBottomMargin;
                        fabLayoutParams.leftMargin = child.getLeftInset();
                        fabLayoutParams.rightMargin = child.getRightInset();
                        boolean isRtl = ViewUtils.isLayoutRtl(fab);
                        if (isRtl) {
                            fabLayoutParams.leftMargin += child.fabOffsetEndMode;
                        } else {
                            fabLayoutParams.rightMargin += child.fabOffsetEndMode;
                        }
                    }
                }
            };
            this.fabContentRect = new Rect();
        }

        public Behavior(Context context, AttributeSet attrs) {
            super(context, attrs);
            this.fabLayoutListener = new View.OnLayoutChangeListener() { // from class: com.google.android.material.bottomappbar.BottomAppBar.Behavior.1
                @Override // android.view.View.OnLayoutChangeListener
                public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                    BottomAppBar child = (BottomAppBar) Behavior.this.viewRef.get();
                    if (child == null || !(v instanceof FloatingActionButton)) {
                        v.removeOnLayoutChangeListener(this);
                        return;
                    }
                    FloatingActionButton fab = (FloatingActionButton) v;
                    fab.getMeasuredContentRect(Behavior.this.fabContentRect);
                    int height = Behavior.this.fabContentRect.height();
                    child.setFabDiameter(height);
                    float cornerSize = fab.getShapeAppearanceModel().getTopLeftCornerSize().getCornerSize(new RectF(Behavior.this.fabContentRect));
                    child.setFabCornerSize(cornerSize);
                    CoordinatorLayout.LayoutParams fabLayoutParams = (CoordinatorLayout.LayoutParams) v.getLayoutParams();
                    if (Behavior.this.originalBottomMargin == 0) {
                        int bottomShadowPadding = (fab.getMeasuredHeight() - height) / 2;
                        int bottomMargin = child.getResources().getDimensionPixelOffset(R.dimen.mtrl_bottomappbar_fab_bottom_margin);
                        int minBottomMargin = bottomMargin - bottomShadowPadding;
                        fabLayoutParams.bottomMargin = child.getBottomInset() + minBottomMargin;
                        fabLayoutParams.leftMargin = child.getLeftInset();
                        fabLayoutParams.rightMargin = child.getRightInset();
                        boolean isRtl = ViewUtils.isLayoutRtl(fab);
                        if (isRtl) {
                            fabLayoutParams.leftMargin += child.fabOffsetEndMode;
                        } else {
                            fabLayoutParams.rightMargin += child.fabOffsetEndMode;
                        }
                    }
                }
            };
            this.fabContentRect = new Rect();
        }

        @Override // com.google.android.material.behavior.HideBottomViewOnScrollBehavior, androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
        public boolean onLayoutChild(CoordinatorLayout parent, BottomAppBar child, int layoutDirection) {
            this.viewRef = new WeakReference<>(child);
            View dependentView = child.findDependentView();
            if (dependentView != null && !ViewCompat.isLaidOut(dependentView)) {
                CoordinatorLayout.LayoutParams fabLayoutParams = (CoordinatorLayout.LayoutParams) dependentView.getLayoutParams();
                fabLayoutParams.anchorGravity = 49;
                this.originalBottomMargin = fabLayoutParams.bottomMargin;
                if (dependentView instanceof FloatingActionButton) {
                    FloatingActionButton fab = (FloatingActionButton) dependentView;
                    if (fab.getShowMotionSpec() == null) {
                        fab.setShowMotionSpecResource(R.animator.mtrl_fab_show_motion_spec);
                    }
                    if (fab.getHideMotionSpec() == null) {
                        fab.setHideMotionSpecResource(R.animator.mtrl_fab_hide_motion_spec);
                    }
                    fab.addOnLayoutChangeListener(this.fabLayoutListener);
                    child.addFabAnimationListeners(fab);
                }
                child.setCutoutState();
            }
            parent.onLayoutChild(child, layoutDirection);
            return super.onLayoutChild(parent, (CoordinatorLayout) child, layoutDirection);
        }

        @Override // com.google.android.material.behavior.HideBottomViewOnScrollBehavior, androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
        public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, BottomAppBar child, View directTargetChild, View target, int axes, int type) {
            return child.getHideOnScroll() && super.onStartNestedScroll(coordinatorLayout, (CoordinatorLayout) child, directTargetChild, target, axes, type);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.appcompat.widget.Toolbar, android.view.View
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState savedState = new SavedState(superState);
        savedState.fabAlignmentMode = this.fabAlignmentMode;
        savedState.fabAttached = this.fabAttached;
        return savedState;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.appcompat.widget.Toolbar, android.view.View
    public void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }
        SavedState savedState = (SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());
        this.fabAlignmentMode = savedState.fabAlignmentMode;
        this.fabAttached = savedState.fabAttached;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class SavedState extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator<SavedState>() { // from class: com.google.android.material.bottomappbar.BottomAppBar.SavedState.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.ClassLoaderCreator
            public SavedState createFromParcel(Parcel in, ClassLoader loader) {
                return new SavedState(in, loader);
            }

            @Override // android.os.Parcelable.Creator
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in, null);
            }

            @Override // android.os.Parcelable.Creator
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
        int fabAlignmentMode;
        boolean fabAttached;

        public SavedState(Parcelable superState) {
            super(superState);
        }

        public SavedState(Parcel in, ClassLoader loader) {
            super(in, loader);
            this.fabAlignmentMode = in.readInt();
            this.fabAttached = in.readInt() != 0;
        }

        @Override // androidx.customview.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(this.fabAlignmentMode);
            out.writeInt(this.fabAttached ? 1 : 0);
        }
    }
}
